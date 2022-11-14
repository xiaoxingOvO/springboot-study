package com.xx.craw.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * 市场交易单日详情
 * @TableName t_market_trade_details
 */
@TableName(value ="t_market_trade_details")
@Data
public class TMarketTradeDetails implements Serializable {
    /**
     * 单日市场交易详情id
     */
    @TableId
    private String id;

    /**
     * 市场交易主表id
     */
    private String marketId;

    /**
     * 日期
     */
    private Date date;

    /**
     * 当前时间
     */
    private String currentTime;

    /**
     * 最新价
     */
    private BigDecimal latestPrice;

    /**
     * 成交量
     */
    private Long volume;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

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
        TMarketTradeDetails other = (TMarketTradeDetails) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getMarketId() == null ? other.getMarketId() == null : this.getMarketId().equals(other.getMarketId()))
            && (this.getDate() == null ? other.getDate() == null : this.getDate().equals(other.getDate()))
            && (this.getCurrentTime() == null ? other.getCurrentTime() == null : this.getCurrentTime().equals(other.getCurrentTime()))
            && (this.getLatestPrice() == null ? other.getLatestPrice() == null : this.getLatestPrice().equals(other.getLatestPrice()))
            && (this.getVolume() == null ? other.getVolume() == null : this.getVolume().equals(other.getVolume()))
            && (this.getTurnover() == null ? other.getTurnover() == null : this.getTurnover().equals(other.getTurnover()))
            && (this.getAvgTradePrice() == null ? other.getAvgTradePrice() == null : this.getAvgTradePrice().equals(other.getAvgTradePrice()))
            && (this.getQuoteChange() == null ? other.getQuoteChange() == null : this.getQuoteChange().equals(other.getQuoteChange()))
            && (this.getCreatedById() == null ? other.getCreatedById() == null : this.getCreatedById().equals(other.getCreatedById()))
            && (this.getCreatedBy() == null ? other.getCreatedBy() == null : this.getCreatedBy().equals(other.getCreatedBy()))
            && (this.getCreatedDate() == null ? other.getCreatedDate() == null : this.getCreatedDate().equals(other.getCreatedDate()))
            && (this.getLastModifiedById() == null ? other.getLastModifiedById() == null : this.getLastModifiedById().equals(other.getLastModifiedById()))
            && (this.getLastModifiedBy() == null ? other.getLastModifiedBy() == null : this.getLastModifiedBy().equals(other.getLastModifiedBy()))
            && (this.getLastModifiedDate() == null ? other.getLastModifiedDate() == null : this.getLastModifiedDate().equals(other.getLastModifiedDate()))
            && (this.getDelete() == null ? other.getDelete() == null : this.getDelete().equals(other.getDelete()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMarketId() == null) ? 0 : getMarketId().hashCode());
        result = prime * result + ((getDate() == null) ? 0 : getDate().hashCode());
        result = prime * result + ((getCurrentTime() == null) ? 0 : getCurrentTime().hashCode());
        result = prime * result + ((getLatestPrice() == null) ? 0 : getLatestPrice().hashCode());
        result = prime * result + ((getVolume() == null) ? 0 : getVolume().hashCode());
        result = prime * result + ((getTurnover() == null) ? 0 : getTurnover().hashCode());
        result = prime * result + ((getAvgTradePrice() == null) ? 0 : getAvgTradePrice().hashCode());
        result = prime * result + ((getQuoteChange() == null) ? 0 : getQuoteChange().hashCode());
        result = prime * result + ((getCreatedById() == null) ? 0 : getCreatedById().hashCode());
        result = prime * result + ((getCreatedBy() == null) ? 0 : getCreatedBy().hashCode());
        result = prime * result + ((getCreatedDate() == null) ? 0 : getCreatedDate().hashCode());
        result = prime * result + ((getLastModifiedById() == null) ? 0 : getLastModifiedById().hashCode());
        result = prime * result + ((getLastModifiedBy() == null) ? 0 : getLastModifiedBy().hashCode());
        result = prime * result + ((getLastModifiedDate() == null) ? 0 : getLastModifiedDate().hashCode());
        result = prime * result + ((getDelete() == null) ? 0 : getDelete().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", marketId=").append(marketId);
        sb.append(", date=").append(date);
        sb.append(", currentTime=").append(currentTime);
        sb.append(", latestPrice=").append(latestPrice);
        sb.append(", volume=").append(volume);
        sb.append(", turnover=").append(turnover);
        sb.append(", avgTradePrice=").append(avgTradePrice);
        sb.append(", quoteChange=").append(quoteChange);
        sb.append(", createdById=").append(createdById);
        sb.append(", createdBy=").append(createdBy);
        sb.append(", createdDate=").append(createdDate);
        sb.append(", lastModifiedById=").append(lastModifiedById);
        sb.append(", lastModifiedBy=").append(lastModifiedBy);
        sb.append(", lastModifiedDate=").append(lastModifiedDate);
        sb.append(", delete=").append(delete);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}