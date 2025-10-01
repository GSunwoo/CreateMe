package com.inquiry.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.dto.PageDTO;
import com.inquiry.dto.InquiryDTO;
import com.inquiry.mapper.IInquiryMapper;

@Service
public class InquiryService {
	
	@Autowired
	IInquiryMapper inqDAO;
	
	// 문의 작성
	public int insertInquiry(InquiryDTO inquiryDTO, Long memberId, String userId) {
		// 문의 작성용 DTO에 멤버 id와 사용자의 계정을 지정해준다.
		inquiryDTO.setMember_id(memberId);
		inquiryDTO.setUser_id(userId);
		// 문의를 db에 삽입
		int result = inqDAO.insertInq(inquiryDTO);
		return result;
	}
	
	// 총 문의 수 구하기
	public int inquiryTotal(String type, PageDTO pageDTO, Long memberId) {
		
		int result;
		
		// 구매자일 경우 pageDTO를 통해 가져오기
		if(type.equals("buyer")) {
			result = inqDAO.getTotalCount1(pageDTO);			
		}
		// 판매자일 경우 멤버 id를 통해 가져오기
		else {
			result = inqDAO.getTotalCount2(memberId);
		}
		
		return result;
	}
	
	public ArrayList<InquiryDTO> inquiryList(String type, PageDTO pageDTO, Long memberId){
		ArrayList<InquiryDTO> result;
		
		if(type.equals("buyer")) {
			result = inqDAO.selectInq1(pageDTO);
			
		}
		else {
			result = inqDAO.selectInq2(memberId);
		}
		
		return result;
	}
}
