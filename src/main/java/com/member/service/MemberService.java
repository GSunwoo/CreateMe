package com.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import com.member.dto.MemberDTO;
import com.member.mapper.IMemberFormMapper;

@Service
public class MemberService {
	@Autowired
	private IMemberFormMapper formDAO;

	public int insertMember(MemberDTO memberDTO) {
		String passwd = PasswordEncoderFactories.createDelegatingPasswordEncoder()
				.encode(memberDTO.getUser_pw());
		memberDTO.setUser_pw(passwd.replace("{bcrypt}", ""));
		int result = formDAO.registMember(memberDTO);
		
		return result;
	}
}
