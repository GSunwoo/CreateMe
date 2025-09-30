package com.common.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;

import com.common.dto.PassFindDTO;
import com.common.mapper.IPassFinderMapper;

public class PassFinderService {
	
	@Autowired
	IPassFinderMapper passDAO;
	
	public Long getUser(PassFindDTO passFindDTO) {
		return passDAO.findUser(passFindDTO);
	}
	
	public int sendingNewPassword(Long memberId, String passwd) {
		System.out.println("새로운 패스워드 보내기");
		int result = passDAO.sendNewPw(memberId, passwd);
		if (result == 1) {
			System.out.println("전송 성공");
		}
		else {
			System.out.println("전송 실패");
		}
		return result;
	}
	
	public PassFindDTO getEmail(Long member_id) {
		return passDAO.selectEmail(member_id);
	}
	
}
