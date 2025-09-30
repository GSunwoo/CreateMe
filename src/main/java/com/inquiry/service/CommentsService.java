package com.inquiry.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inquiry.dto.CommentsDTO;
import com.inquiry.mapper.ICommentsMapper;
import com.inquiry.mapper.IInquiryMapper;
import com.product.mapper.IProductMapper;

import jakarta.transaction.Transactional;

@Service
public class CommentsService {
	
	@Autowired
	ICommentsMapper comDao;
	@Autowired
	IProductMapper proDao;
	@Autowired
	IInquiryMapper inqDao;
	
	@Transactional
	public int insertComment(CommentsDTO commentsDTO, Long memberId) {
		try {
	        Long inquiry_id = commentsDTO.getInquiry_id();
	        // 문의를 작성할 상품의 id 가져오기
	        Long prod_id = inqDao.selectProd_id(inquiry_id);
	        // 불러온 상품의 판매자의 id 가져오기
	        Long prod_mem_id = proDao.selectMember_id(prod_id);
	        
	        // 현재 작성자와 상품의 판매자가 같을 경우 답변 입력 가능
	        if (memberId == prod_mem_id) {
	            commentsDTO.setMember_id(memberId);
	            // 답변 작성
	            int result = comDao.insertComments(commentsDTO);
	            if(result > 0) {
	            	// 정상 작동
	                return 200;
	            }
	         // insert 성공이지만 다른 서버 오류 발생
	            return 500;  
	        }
	        // 권한 없음
	        return 403;      
	    } catch (Exception e) {
	        // insert 실패 시
	        return 500;
	    }
	}
}
