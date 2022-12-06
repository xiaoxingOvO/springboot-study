package com.xx.craw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xx.craw.mapper.MarketTradeDetailsMapper;
import com.xx.craw.domain.dto.MarketTradeDetailsDTO;
import com.xx.craw.service.MarketTradeDetailsService;
import org.springframework.stereotype.Service;

/**
* @description 针对表【t_market_trade_details(市场交易单日详情)】的数据库操作Service实现
*/
@Service
public class MarketTradeDetailsServiceImpl extends ServiceImpl<MarketTradeDetailsMapper, MarketTradeDetailsDTO>
    implements MarketTradeDetailsService {

}




