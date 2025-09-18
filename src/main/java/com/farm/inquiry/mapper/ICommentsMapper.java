package com.farm.inquiry.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.farm.inquiry.dto.CommentsDTO;

@Mapper
public interface ICommentsMapper {

	public int insertComments(CommentsDTO commentsDTO);
	public ArrayList<CommentsDTO> selectComments(@Param("inquiry_id") Long inquiry_id);
	public CommentsDTO getComments(@Param("com_id") Long com_id);
	public int updateComments(CommentsDTO commentsDTO);
	public int deleteComments(@Param("com_id") Long com_id);
	public int hascomments(@Param("inquiry_id") Long inquiry_id);
	
}
