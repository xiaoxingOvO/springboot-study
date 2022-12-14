package com.xx.craw.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 市场交易详情表
 * @TableName t_market_transaction
 */
@TableName(value ="t_market_transaction")
public class MarketTransaction implements Serializable {
    /**
     * 市场交易详情id
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 日期
     */
    private Date date;

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
    private Long volume;

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
     * 创建人id
     */
    private String createdById;

    /**
     * 创建人姓名
     */
    private String createdBy;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 最后一次修改人id
     */
    private String lastModifiedById;

    /**
     * 最后一次修改人姓名
     */
    private String lastModifiedBy;

    /**
     * 最后一次修改时间
     */
    private Date lastModifiedDate;

    /**
     * 删除标记,0为未删除，1为删除
     */
    private Integer delete;

    /**
     * 交易品种,目前只有CEA
     */
    private String tradingVariety;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

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
    public Date getDate() {
        return date;
    }

    /**
     * 日期
     */
    public void setDate(Date date) {
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
    public Long getVolume() {
        return volume;
    }

    /**
     * 成交量(t)
     */
    public void setVolume(Long volume) {
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
    public Date getCreatedDate() {
        return createdDate;
    }

    /**
     * 创建时间
     */
    public void setCreatedDate(Date createdDate) {
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
    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    /**
     * 最后一次修改时间
     */
    public void setLastModifiedDate(Date lastModifiedDate) {
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

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        MarketTransaction other = (MarketTransaction) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getDate() == null ? other.getDate() == null : this.getDate().equals(other.getDate()))
            && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
            && (this.getOpeningPrice() == null ? other.getOpeningPrice() == null : this.getOpeningPrice().equals(other.getOpeningPrice()))
            && (this.getClosingPrice() == null ? other.getClosingPrice() == null : this.getClosingPrice().equals(other.getClosingPrice()))
            && (this.getHighestPrice() == null ? other.getHighestPrice() == null : this.getHighestPrice().equals(other.getHighestPrice()))
            && (this.getLowestPrice() == null ? other.getLowestPrice() == null : this.getLowestPrice().equals(other.getLowestPrice()))
            && (this.getVolume() == null ? other.getVolume() == null : this.getVolume().equals(other.getVolume()))
            && (this.getTurnover() == null ? other.getTurnover() == null : this.getTurnover().equals(other.getTurnover()))
            && (this.getTotalVolume() == null ? other.getTotalVolume() == null : this.getTotalVolume().equals(other.getTotalVolume()))
            && (this.getTotalTurnover() == null ? other.getTotalTurnover() == null : this.getTotalTurnover().equals(other.getTotalTurnover()))
            && (this.getCreatedById() == null ? other.getCreatedById() == null : this.getCreatedById().equals(other.getCreatedById()))
            && (this.getCreatedBy() == null ? other.getCreatedBy() == null : this.getCreatedBy().equals(other.getCreatedBy()))
            && (this.getCreatedDate() == null ? other.getCreatedDate() == null : this.getCreatedDate().equals(other.getCreatedDate()))
            && (this.getLastModifiedById() == null ? other.getLastModifiedById() == null : this.getLastModifiedById().equals(other.getLastModifiedById()))
            && (this.getLastModifiedBy() == null ? other.getLastModifiedBy() == null : this.getLastModifiedBy().equals(other.getLastModifiedBy()))
            && (this.getLastModifiedDate() == null ? other.getLastModifiedDate() == null : this.getLastModifiedDate().equals(other.getLastModifiedDate()))
            && (this.getDelete() == null ? other.getDelete() == null : this.getDelete().equals(other.getDelete()))
            && (this.getTradingVariety() == null ? other.getTradingVariety() == null : this.getTradingVariety().equals(other.getTradingVariety()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getDate() == null) ? 0 : getDate().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getOpeningPrice() == null) ? 0 : getOpeningPrice().hashCode());
        result = prime * result + ((getClosingPrice() == null) ? 0 : getClosingPrice().hashCode());
        result = prime * result + ((getHighestPrice() == null) ? 0 : getHighestPrice().hashCode());
        result = prime * result + ((getLowestPrice() == null) ? 0 : getLowestPrice().hashCode());
        result = prime * result + ((getVolume() == null) ? 0 : getVolume().hashCode());
        result = prime * result + ((getTurnover() == null) ? 0 : getTurnover().hashCode());
        result = prime * result + ((getTotalVolume() == null) ? 0 : getTotalVolume().hashCode());
        result = prime * result + ((getTotalTurnover() == null) ? 0 : getTotalTurnover().hashCode());
        result = prime * result + ((getCreatedById() == null) ? 0 : getCreatedById().hashCode());
        result = prime * result + ((getCreatedBy() == null) ? 0 : getCreatedBy().hashCode());
        result = prime * result + ((getCreatedDate() == null) ? 0 : getCreatedDate().hashCode());
        result = prime * result + ((getLastModifiedById() == null) ? 0 : getLastModifiedById().hashCode());
        result = prime * result + ((getLastModifiedBy() == null) ? 0 : getLastModifiedBy().hashCode());
        result = prime * result + ((getLastModifiedDate() == null) ? 0 : getLastModifiedDate().hashCode());
        result = prime * result + ((getDelete() == null) ? 0 : getDelete().hashCode());
        result = prime * result + ((getTradingVariety() == null) ? 0 : getTradingVariety().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", date=").append(date);
        sb.append(", type=").append(type);
        sb.append(", openingPrice=").append(openingPrice);
        sb.append(", closingPrice=").append(closingPrice);
        sb.append(", highestPrice=").append(highestPrice);
        sb.append(", lowestPrice=").append(lowestPrice);
        sb.append(", volume=").append(volume);
        sb.append(", turnover=").append(turnover);
        sb.append(", totalVolume=").append(totalVolume);
        sb.append(", totalTurnover=").append(totalTurnover);
        sb.append(", createdById=").append(createdById);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", createdDate=").append(createdDate);
        sb.append(", lastModifiedById=").append(lastModifiedById);
        sb.append(", lastModifiedBy=").append(lastModifiedBy);
        sb.append(", lastModifiedDate=").append(lastModifiedDate);
        sb.append(", delete=").append(delete);
        sb.append(", tradingVariety=").append(tradingVariety);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}