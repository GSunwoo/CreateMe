package com.common.dto;

import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ParameterDTO {

	private String searchWord;
	private List<String> searchWords;
	
//	수정필요?
	private int start;
	private int end;
	
	private int pageSize;
	private int offset;
	
	private Date lastWeek;
}
