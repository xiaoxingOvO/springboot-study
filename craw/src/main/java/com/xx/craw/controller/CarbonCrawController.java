package com.xx.craw.controller;

import com.xx.craw.scheduled.DataMarketCarbonCrawController;
import com.xx.craw.util.CarbonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@Api("爬虫相关接口")
@RestController
public class CarbonCrawController {

    @Autowired
    private DataMarketCarbonCrawController CarbonCrawController;

    @Autowired
    private CarbonUtil carbonUtil;

    @ApiOperation("每日交易爬取")
    @GetMapping("/dailyTransactionData")
    public void CarbonCraw(){
        carbonUtil.dailyTransactionData();
    }

    @ApiOperation("每日分钟交易爬取")
    @GetMapping("/dailyDetailData")
    public void CarbonDetailCraw(){
        carbonUtil.daylyMinuteMarketData();
    }

}
