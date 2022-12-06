package com.xx.craw.util;

import com.xx.craw.domain.dto.MarketTradeDetailsDTO;
import com.xx.craw.domain.dto.MarketTransactionDTO;
import com.xx.craw.service.MarketTransactionService;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xx
 * @date 2022/12/2
 */
@Service
public class CarbonUtil {

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(CarbonUtil.class);

    @Autowired
    private MarketTransactionService marketTransactionService;


    /**
     * 爬取碳交易每日数据
     */
    public ResponseEntity<String> dailyTransactionData() {
        try {
            int pageNum = getPageNum();
            log.info("总共有"+pageNum+"页");
            for (int i = 1; i < pageNum; i++) {
                //1.打开浏览器，HTTPClient对象
                CloseableHttpClient httpClient = HttpClients.createDefault();
                //2.输入网址,发起get请求创建HTTPGet对象
                HttpGet httpGet = null;
                if (i == 1) {
                    httpGet = new HttpGet("https://www.cneeex.com/qgtpfqjy/mrgk/2022n/index.shtml");
                } else if (i < 6) {
                    httpGet = new HttpGet("https://www.cneeex.com/qgtpfqjy/mrgk/2022n/index_" + i + ".shtml");
                } else {
                    httpGet = new HttpGet("https://www.cneeex.com/cneeex/catalog/15369/pc/index_" + i + ".shtml");
                }
                //3.按回车，发起请求，返回响应
                CloseableHttpResponse response = httpClient.execute(httpGet);
                //4.解析响应，获取数据，
                //判断状态码是否是200
                if (response.getStatusLine().getStatusCode() == 200) {
                    log.info("----------------------------------第" + i + "页--------------------------------------");

                    HttpEntity httpEntity = response.getEntity();
                    String content = EntityUtils.toString(httpEntity, "utf8");
                    // Jsoup解析html
                    Document document = Jsoup.parse(content);
                    Elements elements = document.select("div.right-con > ul > li.text-ellipsis > a[href]");
                    // 遍历获取每天的href
                    List<String> list = new ArrayList<>();
                    for (Element e : elements){
                        list.add(e.attr("href"));
                    }
                    list.stream().map(s -> {
                        //得到每一天的时间
                        String day = StringUtils.substringBetween(s, "/c/", "/");
                        //拼接url
                        HttpGet dayDateUrl = null;
                        if (s.contains("https")) {
                            dayDateUrl = new HttpGet(s);
                        } else {
                            dayDateUrl = new HttpGet("https://www.cneeex.com/" + s);
                        }

                        try {
                            //发送请求，获取每一天的响应
                            CloseableHttpResponse dayResponse = httpClient.execute(dayDateUrl);
                            //解析响应，获取每一天的数据
                            if (dayResponse.getStatusLine().getStatusCode() == 200) {
                                //将html转换成string
                                HttpEntity dayHttpEntity = dayResponse.getEntity();
                                String dayContent = EntityUtils.toString(dayHttpEntity, "utf8");

                                log.info("开始爬取第"+day+"详情");
                                //用Jsoup解析html
                                Document document2 = Jsoup.parse(dayContent);
                                List<String> allMaths = new ArrayList();
                                //css选择器匹配对应信息
                                Elements dailyDetail = document2.select("div.article-con > p:matches(今日全国碳市场碳排放配额（CEA）挂牌协议交易)");
                                for (Element e:dailyDetail) {
                                    allMaths.add(e.text());
                                }
                                Elements dailyDetailTwo = document2.select("div.article-con > p:matches(截至今日，全国碳市场碳排放配额（CEA）)");
                                for (Element e:dailyDetailTwo){
                                    allMaths.add(e.text());
                                }
                                //通过句号分割成四句话
                                String[] array = allMaths.size() != 2 ? new String[2] : allMaths.toArray(new String[2]);
                                String[] strDayDetail = (array[0] + array[1]).split("。");
                                //取值
                                if (strDayDetail.length == 4) {
                                    // 获取挂牌交易数据
                                    MarketTransactionDTO marketTransactionDTO = getMarketTransactionDTO(day, strDayDetail);
//                                    System.out.println("挂牌交易对象：  " + marketTransactionDTO);
                                    // 插入挂牌交易数据
                                    String message = marketTransactionService.insertMarketForCraw(marketTransactionDTO);
                                    log.info("插入挂牌交易数据->"+message);

                                    if (strDayDetail[1].contains("成交量") || strDayDetail[1].contains("成交额")){
                                        //获取大宗协议交易
                                        MarketTransactionDTO marketTransactionDTO2 = getMarketTransactionDTO2(day, strDayDetail);
                                        // 插入大宗交易数据
                                        String message2 = marketTransactionService.insertMarketForCraw(marketTransactionDTO2);
                                        log.info("大宗交易数据->"+message2);
                                    }
                                }else {
                                    log.info("-------------------第"+day+"爬取失败！！！");
                                }
                            }
                            return null;
                        } catch (IOException e) {
                            e.printStackTrace();
                            throw new RuntimeException(e.getMessage());
                        }
                    }).collect(Collectors.toList());

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(null);
    }

    /**
     * 爬取碳交易每日每分钟交易数据
     * @return
     */
    public ResponseEntity<String> daylyMinuteMarketData() {

        //爬取每分钟市场行情数据
        try {
            //1.打开浏览器，HTTPClient对象
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //2.输入网址,发起get请求创建HTTPGet对象
            //获取当前时间毫秒数,拼接请求
            HttpGet httpGet = new HttpGet("https://www.cneeex.com/sshqt/jsonData/kline.json?" + System.currentTimeMillis());
            //3.按回车，发起请求，返回响应
            CloseableHttpResponse response = httpClient.execute(httpGet);
            //4.解析响应，获取数据，
            //判断状态码是否是200
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = response.getEntity();
                String content = EntityUtils.toString(httpEntity, "utf8");
                System.out.println(content);

                //切割字符串
                String[] strs = content.split("],");
                //对数组中每个字符串进行处理
                List<List<String>> lists = Arrays.stream(strs).map(s -> {
                    //处理每个字符串中每个字段
                    return Arrays.stream(s.split("\",")).map(s1 -> {
                        String s2 = s1.substring(s1.indexOf("\"") + 1, s1.length());
                        return s2;
                    }).collect(Collectors.toList());

                }).collect(Collectors.toList());

                //遍历lists入库
                lists.stream().map(s -> {
                    MarketTradeDetailsDTO marketTradeDetailsDTO = getMarketTradeDetails(s);
                    //插入
//                    marketTransactionService.insertMarketDetailsForCraw(marketTradeDetailsDTO);
                    //输出
//                    System.out.println("每分钟交易详情：   "+marketTradeDetailsDTO);
                    return null;
                }).collect(Collectors.toList());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(null);
    }

    //每分钟交易数据
    private MarketTradeDetailsDTO getMarketTradeDetails(List<String> s) {
        String[] array = s.toArray(new String[9]);
        MarketTradeDetailsDTO marketTradeDetailsDTO = new MarketTradeDetailsDTO();
        // TODO market_id值的设置
        marketTradeDetailsDTO.setCurrentTime(array[0]);
        marketTradeDetailsDTO.setDate(LocalDate.now());
        marketTradeDetailsDTO.setLatestPrice(new BigDecimal(array[1].replace(",", "")));
        marketTradeDetailsDTO.setAvgTradePrice(new BigDecimal(array[5].replace(",", "")));
        marketTradeDetailsDTO.setVolume(new BigDecimal(array[6].replace(",", "")));
        marketTradeDetailsDTO.setTurnover(new BigDecimal(array[7].replace(",", "")));
        marketTradeDetailsDTO.setQuoteChange(new BigDecimal(StringUtils.stripEnd(array[8], "%\"]")));
        return marketTradeDetailsDTO;
    }


    //大宗协议交易
    private MarketTransactionDTO getMarketTransactionDTO2(String day, String[] strDayDetail) {

        MarketTransactionDTO marketTransactionDTO = new MarketTransactionDTO();
        marketTransactionDTO.setVolume(new BigDecimal(StringUtils.substringBetween(strDayDetail[1], "成交量", "吨").replace(",", "")));
        marketTransactionDTO.setTurnover(new BigDecimal(StringUtils.substringBetween(strDayDetail[1], "成交额", "元").replace(",", "")));
        marketTransactionDTO.setDate(LocalDate.parse(day));
        marketTransactionDTO.setType(1);
        return marketTransactionDTO;
    }

    //挂牌交易数据
    private MarketTransactionDTO getMarketTransactionDTO(String day, String[] strDayDetail) {
        //获取挂牌协议交易数据
        MarketTransactionDTO marketTransactionDTO = new MarketTransactionDTO();
        //处理第一句(type=0)
        if (strDayDetail[0].contains("成交量")) {
            BigDecimal volume = new BigDecimal(StringUtils.substringBetween(strDayDetail[0], "成交量", "吨").replace(",", ""));
            marketTransactionDTO.setVolume(volume);
        }
        if (strDayDetail[0].contains("成交额")) {
            BigDecimal turnover = new BigDecimal(StringUtils.substringBetween(strDayDetail[0], "成交额", "元").replace(",", ""));
            marketTransactionDTO.setTurnover(turnover);
        }
        if (strDayDetail[0].contains("开盘价")) {
            BigDecimal openingPrice = new BigDecimal(StringUtils.substringBetween(strDayDetail[0], "开盘价", "元").replace(",", ""));
            marketTransactionDTO.setOpeningPrice(openingPrice);
        }
        if (strDayDetail[0].contains("最高价")) {
            BigDecimal highestPrice = new BigDecimal(StringUtils.substringBetween(strDayDetail[0], "最高价", "元").replace(",", ""));
            marketTransactionDTO.setHighestPrice(highestPrice);
        }
        if (strDayDetail[0].contains("最低价")) {
            BigDecimal lowestPrice = new BigDecimal(StringUtils.substringBetween(strDayDetail[0], "最低价", "元").replace(",", ""));
            marketTransactionDTO.setLowestPrice(lowestPrice);
        }
        if (strDayDetail[0].contains("收盘价")) {
            BigDecimal closingPrice = new BigDecimal(StringUtils.substringBetween(strDayDetail[0], "收盘价", "元").replace(",", ""));
            marketTransactionDTO.setClosingPrice(closingPrice);
        }
        //处理第四句
        if (strDayDetail[3].contains("累计成交量")) {
            BigDecimal totalVolume = new BigDecimal(StringUtils.substringBetween(strDayDetail[3], "成交量", "吨").replace(",", ""));
            marketTransactionDTO.setTotalVolume(totalVolume);
        }
        if (strDayDetail[3].contains("累计成交额")) {
            BigDecimal totalTurnover = new BigDecimal(StringUtils.substringBetween(strDayDetail[3], "成交额", "元").replace(",", ""));
            marketTransactionDTO.setTotalTurnover(totalTurnover);
        }
        marketTransactionDTO.setDate(LocalDate.parse(day));
        marketTransactionDTO.setType(0);
        return marketTransactionDTO;
    }


    public int getPageNum(){
        int i = 15;
        //爬取每分钟市场行情数据
        try {
            //1.打开浏览器，HTTPClient对象
            CloseableHttpClient httpClient = HttpClients.createDefault();
            //2.输入网址,发起get请求创建HTTPGet对象
            HttpGet httpGet = null;
            httpGet = new HttpGet("https://www.cneeex.com/qgtpfqjy/mrgk/2022n/index.shtml");
            //3.按回车，发起请求，返回响应
            CloseableHttpResponse response = httpClient.execute(httpGet);
            //4.解析响应，获取数据，
            //判断状态码是否是200
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity httpEntity = response.getEntity();
                String content = EntityUtils.toString(httpEntity, "utf8");
                Document document = Jsoup.parse(content);
                Elements elements = document.select("div.pageBar td:matches(页)");
                for (Element e:elements){
                    i = Integer.valueOf(StringUtils.substringBetween(e.text(), "/", "页"));
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return i;
    }





}
