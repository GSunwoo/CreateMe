package com.farm.member.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.farm.common.dto.PageDTO;
import com.farm.member.dto.AddressDTO;
import com.farm.member.dto.MemberDTO;

@Mapper
public interface IMemberMapper {

	public MemberDTO loginCheck(String userId);
	// user_id로 회원 정보를 조회하는 쿼리
	public MemberDTO selectBuyerData (MemberDTO memberDTO);
	public MemberDTO selectSellerData (MemberDTO memberDTO);
	
	public List<AddressDTO> selectAddress(@Param("member_id") Long member_id);
	public AddressDTO selectAddressMain(@Param("member_id") Long member_id);
	public int insertAddress(AddressDTO addressDTO);
	public int updateMainToZero(@Param("member_id") Long member_id);
	public int updateMain(@Param("addr_id") Long addr_id);
	public int deleteAddress(@Param("addr_id") Long addr_id);
	
	public List<MemberDTO> getAllMember(PageDTO pageDTO);
}
