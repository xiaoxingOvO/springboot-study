package com.xx.craw.controller;

import com.xx.craw.scheduled.DataMarketCarbonCrawController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <h1> CarbonCrawController </h1>
 * <pre>
 *  TODO DES
 * </pre>
 *
 * @author xiaoxing
 * @date 2022/11/15
 */
@Api("爬虫相关接口")
@RestController
public class CarbonCrawController {

    @Autowired
    DataMarketCarbonCrawController CarbonCrawController;

    @ApiOperation("每日交易爬取")
    @GetMapping("/dailyTransactionData")
    public void CarbonCraw(){
        CarbonCrawController.getDailyTransactionData();
    }

    @ApiOperation("每日分钟爬取")
    @GetMapping("/dailyDetailData")
    public void CarbonDetailCraw(){
        CarbonCrawController.getDailyTransactionData();
    }

}
