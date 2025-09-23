package com.entity;

import java.sql.Date;
import java.util.List;

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
public class ProductImg {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long prodimg_id;
	private String filename;
	private Long idx;
	private int main_idx;
	
	@ManyToOne
	@JoinColumn(name = "prod_id", nullable = false)
    private Product product; // 상품
}
