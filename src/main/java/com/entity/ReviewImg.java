package com.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Entity
public class ReviewImg {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long reviewimg_id;
	@Column(nullable = false)
	private String filename ; // 서버에 저장될 파일의 이름
	@Column(nullable = false)
    private Integer idx;
	private String main;

	
	@ManyToOne
	@JoinColumn(name = "review_id", nullable = false)
    private Review review; // 리뷰
}

