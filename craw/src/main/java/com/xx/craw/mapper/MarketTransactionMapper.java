package com.xx.craw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xx.craw.domain.dto.MarketTransactionDTO;
import org.springframework.stereotype.Repository;

/**
* @description 针对表【t_market_transaction(市场交易详情表)】的数据库操作Mapper
*/
@Repository
public interface MarketTransactionMapper extends BaseMapper<MarketTransactionDTO> {

}




