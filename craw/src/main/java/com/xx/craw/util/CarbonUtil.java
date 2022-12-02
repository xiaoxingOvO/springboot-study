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

    @Autowired
    private MarketTransactionService marketTransactionService;

    /**
     * 爬取碳交易每日数据
     * @return
     */
    public ResponseEntity<String> dailyTransactionData() {
        try {
            for (int i = 1; i < 15; i++) {
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
                    System.out.println("----------------------------------第" + i + "页--------------------------------------");

                    HttpEntity httpEntity = response.getEntity();
                    String content = EntityUtils.toString(httpEntity, "utf8");
//                    log.info(content);
                    // 处理字符串,截取出右侧每日成交列表
                    String strDays = StringUtils.substringBetween(content, "<ul class=\"list-unstyled articel-list\">", "</ul>");
                    //截取每一个li标签，遍历获取每天的href
                    String[] strDayLi = strDays.split("</li>");
                    List<String> list = new ArrayList<>();
                    for (String s : strDayLi) {
                        if (s.contains("<span class=\"pull-right m-l-sm\">")) {
                            list.add(StringUtils.substringBetween(s, "href=\"", "\""));
                        }
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
                                HttpEntity dayHttpEntity = dayResponse.getEntity();
                                String dayContent = EntityUtils.toString(dayHttpEntity, "utf8");
                                System.out.println("第" + day + "详情");
                                //先截取出信息所在的div标签
                                String strDayDiv = StringUtils.substringBetween(dayContent, "<div class=\"article-con font16\">", "</dvi>");
//                                System.out.println(strDayDiv);
                                //根据p标签进行切割
                                String[] strDayP = strDayDiv.split("</p>");
                                List<String> allMaths = new ArrayList();
                                //对含有目标信息的p进行切割
                                Arrays.stream(strDayP).map(p -> {
                                    if (p.contains("今日全国碳市场碳排放配额（CEA）挂牌协议交易")) {

                                        if (p.contains("<span style=\"font-family: 宋体, SimSun; font-size: 16px; text-indent: 2em;\">")) {
                                            String between = StringUtils.substringBetween(p, "<span style=\"font-family: 宋体, SimSun; font-size: 16px; color: rgb(0, 0, 0);\">", "</span>");
                                            allMaths.add(between);
                                        } else {
                                            String str1 = p.substring(p.indexOf(">") + 1, p.length());
                                            String str2 = str1.substring(str1.indexOf(">") + 1, str1.indexOf("<", str1.indexOf("<") + 1));
                                            allMaths.add(str2);
                                        }
                                    }
                                    if (p.contains("截至今日")) {
                                        String str3 = p.substring(p.indexOf(">") + 1, p.length());
                                        String str4 = str3.substring(str3.indexOf(">") + 1, str3.indexOf("<", str3.indexOf("<") + 1));
                                        allMaths.add(str4);
                                    }
                                    return null;
                                }).collect(Collectors.toList());

                                String[] array = allMaths.toArray(new String[2]);
                                String[] strDayDetail = (array[0] + array[1]).split("。");

                                if (strDayDetail.length == 4) {
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

                                    System.out.println("挂牌交易对象：  " + marketTransactionDTO);
                                    // 插入挂牌交易数据
//                                    marketTransactionService.insertMarketForCraw(marketTransactionDTO);

                                    //获取大宗协议交易，处理第二句
                                    if (strDayDetail[1].contains("成交量") || strDayDetail[1].contains("成交额")) {
                                        MarketTransactionDTO marketTransactionDTO1 = new MarketTransactionDTO();
                                        marketTransactionDTO1.setVolume(new BigDecimal(StringUtils.substringBetween(strDayDetail[1], "成交量", "吨").replace(",", "")));
                                        marketTransactionDTO1.setTurnover(new BigDecimal(StringUtils.substringBetween(strDayDetail[1], "成交额", "元").replace(",", "")));
                                        marketTransactionDTO1.setDate(LocalDate.parse(day));
                                        marketTransactionDTO1.setType(1);

                                        // 插入大宗交易数据
                                        System.out.println("大宗交易对象：  "+marketTransactionDTO1);
//                                        marketTransactionService.insertMarketForCraw(marketTransactionDTO1);

                                    }
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
                    String[] array = s.toArray(new String[9]);
                    MarketTradeDetailsDTO marketTradeDetailsDTO = new MarketTradeDetailsDTO();
                    // TODO market_id值的设置
                    marketTradeDetailsDTO.setCurrentTime(array[0]);//当前时间
                    marketTradeDetailsDTO.setDate(LocalDate.now());//日期
                    marketTradeDetailsDTO.setLatestPrice(new BigDecimal(array[1].replace(",", "")));//最新价
                    marketTradeDetailsDTO.setAvgTradePrice(new BigDecimal(array[5].replace(",", "")));//成交均价
                    marketTradeDetailsDTO.setVolume(new BigDecimal(array[6].replace(",", "")));//成交量
                    marketTradeDetailsDTO.setTurnover(new BigDecimal(array[7].replace(",", "")));//成交金额
                    marketTradeDetailsDTO.setQuoteChange(new BigDecimal(StringUtils.stripEnd(array[8], "%\"]")));//涨跌幅
                    //插入
                    marketTransactionService.insertMarketDetailsForCraw(marketTradeDetailsDTO);

//                    System.out.println("每分钟交易详情：   "+marketTradeDetailsDTO);
                    return null;
                }).collect(Collectors.toList());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok(null);
    }
}
