package com.review.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Mapper;

import com.review.dto.ReviewImgDTO;

@Mapper
public interface ReviewImgMapper {

	public int insertImg(ReviewImgDTO reviewimgDTO);
	public ArrayList<ReviewImgDTO> selectImg(ReviewImgDTO reviewimgDTO);
	public int deleteImg(ReviewImgDTO reviewimgDTO);
	public void setIdx(Long idx);

	
}
