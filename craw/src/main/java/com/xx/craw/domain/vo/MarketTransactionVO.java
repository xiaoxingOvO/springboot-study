package com.xx.craw.domain.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;


/**
* 市场交易详情
*/
@Data
@ApiModel("市场交易详情")
public class MarketTransactionVO implements Serializable {

    private static final long serialVersionUID = -8534726297047944778L;

    /**
    * 日期
    */
    @ApiModelProperty("日期")
    private LocalDate date;
    /**
    * 交易类型,0挂牌协议交易,1大宗协议交易
    */
    @ApiModelProperty("交易类型,0挂牌协议交易,1大宗协议交易")
    private Integer type;
    /**
     * 交易品种,目前只有CEA
     */
    @ApiModelProperty("交易品种,目前只有CEA")
    private String tradingVariety = "CEA";
    /**
    * 开盘价(元)
    */
    @ApiModelProperty("开盘价(元)")
    private BigDecimal openingPrice;
    /**
    * 收盘价(元)
    */
    @ApiModelProperty("收盘价(元)")
    private BigDecimal closingPrice;
    /**
    * 最高价(元)
    */
    @ApiModelProperty("最高价(元)")
    private BigDecimal highestPrice;
    /**
    * 最低价(元)
    */
    @ApiModelProperty("最低价(元)")
    private BigDecimal lowestPrice;
    /**
    * 成交量(t)
    */
    @ApiModelProperty("成交量(t)")
    private BigDecimal volume;
    /**
    * 成交金额(万元)
    */
    @ApiModelProperty("成交金额(万元)")
    private BigDecimal turnover;

    /**
     * 截止当日累计成交量(t)
     */
    @ApiModelProperty("截止当日累计成交量(t)")
    private BigDecimal totalVolume;

    /**
     * 截止当日累计成交金额(万元)
     */
    @ApiModelProperty("截止当日累计成交金额(万元)")
    private BigDecimal totalTurnover;

    /**
     * 涨跌幅
     */
    @ApiModelProperty("涨跌幅")
    private BigDecimal quoteChange;

    /**
     * 成交均价(元/t)
     */
    @ApiModelProperty("成交均价(元/t)")
    private BigDecimal avgTransactionPrice;

}
