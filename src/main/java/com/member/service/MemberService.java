package com.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import com.member.dto.AddressDTO;
import com.member.dto.MemberDTO;
import com.member.mapper.IMemberFormMapper;
import com.member.mapper.IMemberMapper;

@Service
public class MemberService {
	@Autowired
	private IMemberFormMapper formDAO;
	@Autowired
	private IMemberMapper memDAO;

	// 회원가입(멤버등록)
	public int insertMember(MemberDTO memberDTO) {
		// 입력받은 패스워드를 bcrypt로 인코딩
		String passwd = PasswordEncoderFactories.createDelegatingPasswordEncoder()
				.encode(memberDTO.getUser_pw());
		memberDTO.setUser_pw(passwd.replace("{bcrypt}", ""));
		int result = formDAO.registMember(memberDTO);
		
		return result;
	}
	
	// 주소불러오기(main)
	public AddressDTO getMainAddress(Long memberId) {
		AddressDTO result = memDAO.selectAddressMain(memberId);
		return result;
		
	}
	
	// 주소불러오기(전체)
	public List<AddressDTO> getAllAddress(Long memberId){
		List<AddressDTO> result = memDAO.selectAddress(memberId);
		return result;
	}
}
