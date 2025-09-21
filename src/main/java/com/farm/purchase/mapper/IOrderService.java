package com.farm.purchase.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.farm.common.dto.ParameterDTO;
import com.farm.purchase.dto.OrderDTO;

@Mapper
public interface IOrderService {
	public List<OrderDTO> selectBuyerOrdersAll(@Param("param") ParameterDTO parameterDTO,
											@Param("member_id") Long member_id);
	public List<OrderDTO> selectSellerOrdersAll(@Param("member_id") Long member_id);
}
