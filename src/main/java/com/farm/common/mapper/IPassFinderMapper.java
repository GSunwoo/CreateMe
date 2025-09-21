package com.farm.common.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.farm.common.dto.PassFindDTO;

@Mapper
public interface IPassFinderMapper {
	public Long findUser(PassFindDTO passFindDTO);
	public int sendNewPw(@Param("member_id") Long member_id, 
						 @Param("passwd") String passwd);
	public PassFindDTO selectEmail(@Param("member_id") Long member_id); 
}
