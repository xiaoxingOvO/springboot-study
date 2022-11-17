package com.xx.craw.domain.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * 市场交易模块详情(分钟)dto数据
 */
@ApiModel("市场交易模块详情dto数据")
@Data
public class MarketTradeDetailsDTO implements Serializable {

    /**
     * 单日市场交易详情id
     */
    @ApiModelProperty("单日市场交易详情id")
    private String id;

    /**
     * 市场交易主表id
     */
    @ApiModelProperty("市场交易主表id")
    private String marketId;

    /**
     * 当前时间
     */
    @ApiModelProperty("当前时间")
    private String currentTime;

    /**
     * 日期
     */
    @ApiModelProperty("日期")
    private LocalDate date;

    /**
     * 最新价
     */
    @ApiModelProperty("最新价")
    private BigDecimal latestPrice;

    /**
     * 成交量
     */
    @ApiModelProperty("成交量")
    private BigDecimal volume;

    /**
     * 成交金额
     */
    @ApiModelProperty("成交金额")
    private BigDecimal turnover;

    /**
     * 成交均价
     */
    @ApiModelProperty("成交均价")
    private BigDecimal avgTradePrice;

    /**
     * 涨跌幅
     */
    @ApiModelProperty("涨跌幅")
    private BigDecimal quoteChange;

}
