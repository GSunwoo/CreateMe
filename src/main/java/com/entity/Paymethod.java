package com.entity;

import java.sql.Date;

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
public class Paymethod {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pay_id;
	@Column(nullable = false)
	private String method;
	private String card_com;
	private String card_num;
	private String acc_com;
	private String acc_num;
	private String bank;
	
	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;
}
