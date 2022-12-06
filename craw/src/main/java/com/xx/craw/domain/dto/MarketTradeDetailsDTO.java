package com.xx.craw.domain.dto;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 市场交易单日详情
 * @TableName t_market_trade_details
 */
@TableName(value ="t_market_trade_details")
public class MarketTradeDetailsDTO implements Serializable {
    /**
     * 单日市场交易详情id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 市场交易主表id
     */
    private String marketId;

    /**
     * 当前时间
     */
    @TableField("`current_time`")
    private String currentTime;

    /**
     * 日期
     */
    private LocalDate date;

    /**
     * 最新价
     */
    private BigDecimal latestPrice;

    /**
     * 成交量
     */
    private BigDecimal volume;

    /**
     * 成交金额
     */
    private BigDecimal turnover;

    /**
     * 成交均价
     */
    private BigDecimal avgTradePrice;

    /**
     * 涨跌幅
     */
    private BigDecimal quoteChange;

    /**
     * 创建人id
     */
    @TableField(fill = FieldFill.INSERT)
    private String createdById;

    /**
     * 创建人姓名
     */
    @TableField(fill = FieldFill.INSERT)
    private String createdBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdDate;

    /**
     * 最后一次修改人id
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String lastModifiedById;

    /**
     * 最后一次修改人姓名
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String lastModifiedBy;

    /**
     * 最后一次修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime lastModifiedDate;

    /**
     * 删除标记,0为未删除，1为删除
     */
    @TableField("`delete`")
    private Integer delete;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMarketId() {
        return marketId;
    }

    public void setMarketId(String marketId) {
        this.marketId = marketId;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public BigDecimal getLatestPrice() {
        return latestPrice;
    }

    public void setLatestPrice(BigDecimal latestPrice) {
        this.latestPrice = latestPrice;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public BigDecimal getTurnover() {
        return turnover;
    }

    public void setTurnover(BigDecimal turnover) {
        this.turnover = turnover;
    }

    public BigDecimal getAvgTradePrice() {
        return avgTradePrice;
    }

    public void setAvgTradePrice(BigDecimal avgTradePrice) {
        this.avgTradePrice = avgTradePrice;
    }

    public BigDecimal getQuoteChange() {
        return quoteChange;
    }

    public void setQuoteChange(BigDecimal quoteChange) {
        this.quoteChange = quoteChange;
    }

    public String getCreatedById() {
        return createdById;
    }

    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedById() {
        return lastModifiedById;
    }

    public void setLastModifiedById(String lastModifiedById) {
        this.lastModifiedById = lastModifiedById;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Integer getDelete() {
        return delete;
    }

    public void setDelete(Integer delete) {
        this.delete = delete;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "MarketTradeDetails{" +
                "id='" + id + '\'' +
                ", marketId='" + marketId + '\'' +
                ", currentTime='" + currentTime + '\'' +
                ", date=" + date +
                ", latestPrice=" + latestPrice +
                ", volume=" + volume +
                ", turnover=" + turnover +
                ", avgTradePrice=" + avgTradePrice +
                ", quoteChange=" + quoteChange +
                ", createdById='" + createdById + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedById='" + lastModifiedById + '\'' +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", lastModifiedDate=" + lastModifiedDate +
                ", delete=" + delete +
                '}';
    }

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}