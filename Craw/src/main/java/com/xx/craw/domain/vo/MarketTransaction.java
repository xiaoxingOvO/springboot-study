package com.xx.craw.domain.vo;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 市场交易详情表
 *
 * @TableName t_market_transaction
 */
@TableName(value = "t_market_transaction")
public class MarketTransaction extends Model<MarketTransaction> {
    /**
     * 市场交易详情id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 日期
     */
    private LocalDate date;

    /**
     * 交易类型,0挂牌协议交易,1大宗协议交易
     */
    private Integer type;

    /**
     * 开盘价(元)
     */
    private BigDecimal openingPrice;

    /**
     * 收盘价(元)
     */
    private BigDecimal closingPrice;

    /**
     * 最高价(元)
     */
    private BigDecimal highestPrice;

    /**
     * 最低价(元)
     */
    private BigDecimal lowestPrice;

    /**
     * 成交量(t)
     */
    private BigDecimal volume;

    /**
     * 成交金额(万元)
     */
    private BigDecimal turnover;

    /**
     * 截止当日累计成交量(t)
     */
    private BigDecimal totalVolume;

    /**
     * 截止当日累计成交金额(万元)
     */
    private BigDecimal totalTurnover;

    /**
     * 交易品种,目前只有CEA
     */
    private String tradingVariety;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 交易品种,目前只有CEA
     */
    public String getTradingVariety() {
        return tradingVariety;
    }

    /**
     * 交易品种,目前只有CEA
     */
    public void setTradingVariety(String tradingVariety) {
        this.tradingVariety = tradingVariety;
    }

    /**
     * 市场交易详情id
     */
    public String getId() {
        return id;
    }

    /**
     * 市场交易详情id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 日期
     */
    public LocalDate getDate() {
        return date;
    }

    /**
     * 日期
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * 交易类型,0挂牌协议交易,1大宗协议交易
     */
    public Integer getType() {
        return type;
    }

    /**
     * 交易类型,0挂牌协议交易,1大宗协议交易
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * 开盘价(元)
     */
    public BigDecimal getOpeningPrice() {
        return openingPrice;
    }

    /**
     * 开盘价(元)
     */
    public void setOpeningPrice(BigDecimal openingPrice) {
        this.openingPrice = openingPrice;
    }

    /**
     * 收盘价(元)
     */
    public BigDecimal getClosingPrice() {
        return closingPrice;
    }

    /**
     * 收盘价(元)
     */
    public void setClosingPrice(BigDecimal closingPrice) {
        this.closingPrice = closingPrice;
    }

    /**
     * 最高价(元)
     */
    public BigDecimal getHighestPrice() {
        return highestPrice;
    }

    /**
     * 最高价(元)
     */
    public void setHighestPrice(BigDecimal highestPrice) {
        this.highestPrice = highestPrice;
    }

    /**
     * 最低价(元)
     */
    public BigDecimal getLowestPrice() {
        return lowestPrice;
    }

    /**
     * 最低价(元)
     */
    public void setLowestPrice(BigDecimal lowestPrice) {
        this.lowestPrice = lowestPrice;
    }

    /**
     * 成交量(t)
     */
    public BigDecimal getVolume() {
        return volume;
    }

    /**
     * 成交量(t)
     */
    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    /**
     * 成交金额(万元)
     */
    public BigDecimal getTurnover() {
        return turnover;
    }

    /**
     * 成交金额(万元)
     */
    public void setTurnover(BigDecimal turnover) {
        this.turnover = turnover;
    }

    /**
     * 截止当日累计成交量(t)
     */
    public BigDecimal getTotalVolume() {
        return totalVolume;
    }

    /**
     * 截止当日累计成交量(t)
     */
    public void setTotalVolume(BigDecimal totalVolume) {
        this.totalVolume = totalVolume;
    }

    /**
     * 截止当日累计成交金额(万元)
     */
    public BigDecimal getTotalTurnover() {
        return totalTurnover;
    }

    /**
     * 截止当日累计成交金额(万元)
     */
    public void setTotalTurnover(BigDecimal totalTurnover) {
        this.totalTurnover = totalTurnover;
    }

    /**
     * 创建人id
     */
    public String getCreatedById() {
        return createdById;
    }

    /**
     * 创建人id
     */
    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }

    /**
     * 创建人姓名
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * 创建人姓名
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 创建时间
     */
    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    /**
     * 创建时间
     */
    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    /**
     * 最后一次修改人id
     */
    public String getLastModifiedById() {
        return lastModifiedById;
    }

    /**
     * 最后一次修改人id
     */
    public void setLastModifiedById(String lastModifiedById) {
        this.lastModifiedById = lastModifiedById;
    }

    /**
     * 最后一次修改人姓名
     */
    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    /**
     * 最后一次修改人姓名
     */
    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    /**
     * 最后一次修改时间
     */
    public LocalDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * 最后一次修改时间
     */
    public void setLastModifiedDate(LocalDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    /**
     * 删除标记,0为未删除，1为删除
     */
    public Integer getDelete() {
        return delete;
    }

    /**
     * 删除标记,0为未删除，1为删除
     */
    public void setDelete(Integer delete) {
        this.delete = delete;
    }

    @Override
    public String toString() {
        return "MarketTransaction{" +
                "id='" + id + '\'' +
                ", date=" + date +
                ", type=" + type +
                ", openingPrice=" + openingPrice +
                ", closingPrice=" + closingPrice +
                ", highestPrice=" + highestPrice +
                ", lowestPrice=" + lowestPrice +
                ", volume=" + volume +
                ", turnover=" + turnover +
                ", totalVolume=" + totalVolume +
                ", totalTurnover=" + totalTurnover +
                ", tradingVariety='" + tradingVariety + '\'' +
                ", createdById='" + createdById + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", createdDate=" + createdDate +
                ", lastModifiedById='" + lastModifiedById + '\'' +
                ", lastModifiedBy='" + lastModifiedBy + '\'' +
                ", lastModifiedDate=" + lastModifiedDate +
                ", delete=" + delete +
                '}';
    }
}