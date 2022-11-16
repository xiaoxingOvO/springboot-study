package com.xx.craw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xx.craw.mapper.MarketTradeDetailsMapper;
import com.xx.craw.mapper.MarketTransactionMapper;
import com.xx.craw.domain.vo.MarketTradeDetails;
import com.xx.craw.domain.dto.MarketTradeDetailsDTO;
import com.xx.craw.domain.vo.MarketTransaction;
import com.xx.craw.domain.dto.MarketTransactionDTO;
import com.xx.craw.service.MarketTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
* @author xiaoxing
* @description 针对表【t_market_transaction(市场交易详情表)】的数据库操作Service实现
* @createDate 2022-11-14 17:59:23
*/
@Service
public class MarketTransactionServiceImpl extends ServiceImpl<MarketTransactionMapper, MarketTransaction>
    implements MarketTransactionService {

    @Autowired
    private MarketTransactionMapper marketTransactionMapper;
    @Autowired
    private MarketTradeDetailsMapper marketTransactionDetailsMapper;


    @Override
    public void updateMarket(MarketTransactionDTO dto) {
        MarketTransaction marketTransaction = new MarketTransaction();
        marketTransaction.setHighestPrice(dto.getHighestPrice());
        marketTransaction.setLowestPrice(dto.getLowestPrice());
        marketTransaction.setOpeningPrice(dto.getOpeningPrice());
        marketTransaction.setClosingPrice(dto.getClosingPrice());
        marketTransaction.setDate(dto.getDate());
        marketTransaction.setType(dto.getType());
        marketTransaction.setVolume(dto.getVolume());
        marketTransaction.setTurnover(dto.getTurnover());
        marketTransaction.setTotalVolume(dto.getTotalVolume());
        marketTransaction.setTotalTurnover(dto.getTotalTurnover());
        LambdaUpdateWrapper<MarketTransaction> wrapper = Wrappers
                .lambdaUpdate(MarketTransaction.class)
                .eq(MarketTransaction::getDate, dto.getDate())
                .eq(MarketTransaction::getType, dto.getType());
        marketTransactionMapper.update(marketTransaction, wrapper);
    }

    @Override
    public String insertMarketForCraw(MarketTransactionDTO dto) {
        LambdaQueryWrapper<MarketTransaction> eq = Wrappers
                .lambdaQuery(MarketTransaction.class)
                .eq(MarketTransaction::getType, dto.getType())
                .eq(MarketTransaction::getDate, dto.getDate());
        List<MarketTransaction> list = marketTransactionMapper.selectList(eq);
        if (!CollectionUtils.isEmpty(list)) {
            updateMarket(dto);
//            throw new BizException("该天已经填入数据，无法重复补录，只能修改");
        }else {
            MarketTransaction marketTransaction = new MarketTransaction();
            marketTransaction.setHighestPrice(dto.getHighestPrice());
            marketTransaction.setLowestPrice(dto.getLowestPrice());
            marketTransaction.setOpeningPrice(dto.getOpeningPrice());
            marketTransaction.setClosingPrice(dto.getClosingPrice());
            marketTransaction.setDate(dto.getDate());
            marketTransaction.setType(dto.getType());
            marketTransaction.setVolume(dto.getVolume());
            marketTransaction.setTurnover(dto.getTurnover());
            marketTransaction.setTotalVolume(dto.getTotalVolume());
            marketTransaction.setTotalTurnover(dto.getTotalTurnover());
            marketTransaction.setTradingVariety(dto.getTradingVariety());
            marketTransactionMapper.insert(marketTransaction);
        }

        return "成功啦!";
    }


    @Override
    public String insertMarketDetailsForCraw(MarketTradeDetailsDTO dto) {
        LambdaQueryWrapper<MarketTradeDetails> eq = Wrappers
                .lambdaQuery(MarketTradeDetails.class)
                .eq(MarketTradeDetails::getDate, dto.getDate())
                .eq(MarketTradeDetails::getCurrentTime,dto.getCurrentTime());
        List<MarketTradeDetails> list = marketTransactionDetailsMapper.selectList(eq);

        MarketTradeDetails details = new MarketTradeDetails();
        details.setCurrentTime(dto.getCurrentTime());
        details.setLatestPrice(dto.getLatestPrice());
        details.setVolume(dto.getVolume());
        details.setTurnover(dto.getTurnover());
        details.setAvgTradePrice(dto.getAvgTradePrice());
        details.setQuoteChange(dto.getQuoteChange());
        details.setDate(dto.getDate());

        if (!CollectionUtils.isEmpty(list)) {
            LambdaUpdateWrapper<MarketTradeDetails> wrapper = Wrappers
                    .lambdaUpdate(MarketTradeDetails.class)
                    .eq(MarketTradeDetails::getDate, dto.getDate())
                    .eq(MarketTradeDetails::getCurrentTime,dto.getCurrentTime());
            marketTransactionDetailsMapper.update(details, wrapper);
//            throw new BizException("该天已经填入数据，无法重复补录，只能修改");
        }else {
            marketTransactionDetailsMapper.insert(details);
        }
        return details.getId();
    }



}




