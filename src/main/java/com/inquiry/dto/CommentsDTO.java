package com.inquiry.dto;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;

@Data
public class CommentsDTO {

	private Long com_id;
	private String com_content;
	private Date com_date;
	private Long member_id;
	private Long inquiry_id;
	
}
