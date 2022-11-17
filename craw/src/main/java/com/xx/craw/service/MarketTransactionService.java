package com.xx.craw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xx.craw.domain.dto.MarketTradeDetailsDTO;
import com.xx.craw.domain.dto.MarketTransactionDTO;
import com.xx.craw.domain.vo.MarketTransaction;

/**
* @description 针对表【t_market_transaction(市场交易详情表)】的数据库操作Service
*/
public interface MarketTransactionService extends IService<MarketTransaction> {


    /**
     * 更新数据
     * @param dto 数据
     */
    void updateMarket(MarketTransactionDTO dto);

    /**
     * 插入数据-市场交易爬虫专用
     * @param dto 数据
     * @return 信息
     */
    String insertMarketForCraw(MarketTransactionDTO dto);

    /**
     * 插入日数据具体信息-市场交易爬虫专用
     * @param dto 日数据具体信息
     * @return 信息
     */
    String insertMarketDetailsForCraw(MarketTradeDetailsDTO dto);


}
