package com.xx.craw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xx.craw.mapper.MarketTradeDetailsMapper;
import com.xx.craw.mapper.MarketTransactionMapper;
import com.xx.craw.domain.dto.MarketTradeDetailsDTO;
import com.xx.craw.domain.vo.MarketTradeDetailsVO;
import com.xx.craw.domain.dto.MarketTransactionDTO;
import com.xx.craw.domain.vo.MarketTransactionVO;
import com.xx.craw.service.MarketTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
* @description 针对表【t_market_transaction(市场交易详情表)】的数据库操作Service实现
*/
@Service
public class MarketTransactionServiceImpl extends ServiceImpl<MarketTransactionMapper, MarketTransactionDTO>
    implements MarketTransactionService {

    @Autowired
    private MarketTransactionMapper marketTransactionMapper;
    @Autowired
    private MarketTradeDetailsMapper marketTransactionDetailsMapper;


    /**
     * 更新
     * @param dto 数据
     */
    @Override
    public void updateMarket(MarketTransactionDTO dto) {
        LambdaUpdateWrapper<MarketTransactionDTO> wrapper = Wrappers
                .lambdaUpdate(MarketTransactionDTO.class)
                .eq(MarketTransactionDTO::getDate, dto.getDate())
                .eq(MarketTransactionDTO::getType, dto.getType());
        marketTransactionMapper.update(dto, wrapper);
    }

    /**
     * 插入市场交易数据
     * @param dto 数据
     * @return
     */
    @Override
    public String insertMarketForCraw(MarketTransactionDTO dto) {
        LambdaQueryWrapper<MarketTransactionDTO> eq = Wrappers
                .lambdaQuery(MarketTransactionDTO.class)
                .eq(MarketTransactionDTO::getType, dto.getType())
                .eq(MarketTransactionDTO::getDate, dto.getDate());
        List<MarketTransactionDTO> list = marketTransactionMapper.selectList(eq);
        if (!CollectionUtils.isEmpty(list)) {
            updateMarket(dto);
        }else {
            marketTransactionMapper.insert(dto);
        }
        return "成功啦!";
    }

    /**
     * 插入每分钟交易详情
     * @param dto 日数据具体信息
     * @return
     */
    @Override
    public String insertMarketDetailsForCraw(MarketTradeDetailsDTO dto) {
        LambdaQueryWrapper<MarketTradeDetailsDTO> eq = Wrappers
                .lambdaQuery(MarketTradeDetailsDTO.class)
                .eq(MarketTradeDetailsDTO::getDate, dto.getDate())
                .eq(MarketTradeDetailsDTO::getCurrentTime,dto.getCurrentTime());
        List<MarketTradeDetailsDTO> list = marketTransactionDetailsMapper.selectList(eq);

        if (!CollectionUtils.isEmpty(list)) {
            LambdaUpdateWrapper<MarketTradeDetailsDTO> wrapper = Wrappers
                    .lambdaUpdate(MarketTradeDetailsDTO.class)
                    .eq(MarketTradeDetailsDTO::getDate, dto.getDate())
                    .eq(MarketTradeDetailsDTO::getCurrentTime,dto.getCurrentTime());
            marketTransactionDetailsMapper.update(dto, wrapper);
        }else {
            marketTransactionDetailsMapper.insert(dto);
        }
        return dto.getId();
    }



}




