package com.xx.craw.controller;

import com.xx.craw.util.CarbonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carbonCraw")
@Api(value = "碳交易数据爬取", tags = "碳交易数据爬取")
public class CarbonCrawController {

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
