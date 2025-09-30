package com.common.service;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import com.common.dto.MailInfoDTO;
import com.common.dto.PassFindDTO;
import com.common.mapper.IPassFinderMapper;
import com.etc.PassFindMail;
import com.etc.RandomPasswordGenerator;

import jakarta.transaction.Transactional;

@Service
public class PassFinderService {
	
	@Autowired
	IPassFinderMapper passDAO;
	@Autowired
	PassFindMail findPw;
    
    @Transactional
    public boolean sendingNewPassword(PassFindDTO passFindDTO) {
        Long memberId = passDAO.findUser(passFindDTO);
        if (memberId == null) {
            return false;
        }
        String email = passFindDTO.getEmailid() + "@" + passFindDTO.getEmaildomain();
        String newPw = RandomPasswordGenerator.generatePass(12);
        String encodedPw = PasswordEncoderFactories.createDelegatingPasswordEncoder()
                .encode(newPw)
                .replace("{bcrypt}", "");

        MailInfoDTO mailInfoDTO = new MailInfoDTO();
        mailInfoDTO.setTo(email);
        mailInfoDTO.setSubject("[Farm To You] 새로운 비밀번호가 발급되었습니다.");
        mailInfoDTO.setContent(newPw);
        findPw.newPassSender(mailInfoDTO);

        passDAO.sendNewPw(memberId, encodedPw);

        return true;
    }
	
}
