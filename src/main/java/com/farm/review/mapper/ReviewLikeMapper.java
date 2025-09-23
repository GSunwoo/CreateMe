package com.farm.review.mapper;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class ReviewLikeMapper {
	
		ReviewBoardMapper reviewboardservice;
	    public ReviewLikeMapper(ReviewBoardMapper reviewboardservice) {
	        this.reviewboardservice = reviewboardservice;
	    }

	    @Transactional
	    public boolean toggleLike(Long reviewId, Long memberId) {
	        int exists = reviewboardservice.existsLike(reviewId, memberId);
	        if (exists > 0) {
	        	reviewboardservice.deleteLike(reviewId, memberId);
	            return false; // 좋아요 취소됨
	        } else {
	        	reviewboardservice.insertLike(reviewId, memberId);
	            return true; // 좋아요 추가됨
	        }
	    }
	    
	    public int countLike(Long reviewId) {
	        return reviewboardservice.countLike(reviewId);
	    }
	    
	 
}
