package com.inquiry.service;

import java.util.HashMap;
import java.util.Map;

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
	ICommentsMapper comDAO;
	@Autowired
	IProductMapper proDAO;
	@Autowired
	IInquiryMapper inqDAO;
	
	@Transactional
	public int insertComment(CommentsDTO commentsDTO, Long memberId) {
		try {
			// 답변을 작성할 문의 id 가져오기
	        Long inquiry_id = commentsDTO.getInquiry_id();
	        // 해당 문의의 상품의 id 가져오기
	        Long prod_id = inqDAO.selectProd_id(inquiry_id);
	        // 불러온 상품의 판매자의 id 가져오기
	        Long prod_mem_id = proDAO.selectMember_id(prod_id);
	        
	        // 현재 작성자와 상품의 판매자가 같을 경우 답변 입력 가능
	        if (memberId == prod_mem_id) {
	            commentsDTO.setMember_id(memberId);
	            // 답변 작성
	            int result = comDAO.insertComments(commentsDTO);
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
	
	public Map<String, String> loadComment(Long comId, Long memberId) {
		// 반환할 HashMap 선언
		Map<String, String> map = new HashMap<>();
		
		// 답변 불러오기
		CommentsDTO commentsDTO = comDAO.getComments(comId);
		
		// 답변의 작성자 id
		Long writer_id = commentsDTO.getMember_id();
		
		// 현재 사용자와 작성자가 같은지 확인
		if(memberId == writer_id) {
			// 정상적인 사용일 경우, 200과 답변내용, 답변의 id를 map으로 만들어 반환
			map.put("code", "200");
			map.put("content", commentsDTO.getCom_content());
			map.put("com_id", comId.toString());
			return map;			
		}
		else {
			// 현재 사용자와 작성자가 다를 경우 권한 부족으로 코드 403 반환
			map.put("code", "403");
			return map; 
		}
	}
	
	public int updateComment(CommentsDTO commentsDTO, Long memberId) {
		try {
			// 현재 사용자와 답변 작성자가 같을 경우 update
			if(memberId == commentsDTO.getMember_id()) {
				comDAO.updateComments(commentsDTO);
				return 200;
			}
			// 다르면 권한 부족으로 코드 403 반환
			else {
				return 403;
			}
		}
		catch(Exception e) {
			// 예외가 발생할 경우 코드 500 반환
			return 500;	
		}
	}
	
	public int deleteComment(CommentsDTO commentsDTO, Long memberId) {
		try {
			// 현재 사용자와 답변 작성자가 같을 경우 delete
			if(memberId == commentsDTO.getMember_id()) {
				comDAO.deleteComments(commentsDTO.getCom_id());
				return 200;
			}
			// 다르면 권한 부족으로 코드 403 반환
			else {
				return 403;
			}
		}
		catch(Exception e) {
			// 예외가 발생할 경우 코드 500 반환
			return 500;	
		}
	}
	
	
}
