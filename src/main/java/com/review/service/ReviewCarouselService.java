package com.review.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.review.dto.ReviewBoardDTO;
import com.review.mapper.CarouselReviewMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReviewCarouselService {

	private final CarouselReviewMapper carouselReviewMapper;
	
	public List<ReviewBoardDTO> getTopLikedReviews(int reviewPage) {
		return carouselReviewMapper.selectTopLiked(reviewPage);
	}
}
