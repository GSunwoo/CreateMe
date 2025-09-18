package com.farm.member.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.farm.member.dto.MemberDTO;

@Mapper
public interface IMemberFormMapper {
	// 회원가입
	public int registMember(MemberDTO memberDTO);
	public int registAddr(MemberDTO memberDTO);
	public int registFarm(MemberDTO memberDTO);
	
	public int updateMember(MemberDTO memberDTO);
}
