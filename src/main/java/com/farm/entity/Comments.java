package com.farm.entity;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
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
public class Comments {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false)
	private Long com_id;
	@Column(nullable = false)
	private String com_content;
	@Column(nullable = false, columnDefinition = "DATE DEFAULT NOW()")
	private LocalDate com_date;
	
	@ManyToOne
	@JoinColumn(name = "member_id", nullable = false)
    private Member member; // 판매자
	@ManyToOne
	@JoinColumn(name = "inquiry_id", nullable = false)
	private Inquiry inquiry; //
	
	@PrePersist
	protected void onPrePersist() {
		this.com_date = LocalDate.now();
	}
	
}
