package com.farm.product.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.farm.product.dto.ProductDTO;
import com.farm.product.mapper.IProductMapper;

public class ProductService {
	
	@Autowired
	private IProductMapper proDao;
	
	public Map<String, Object> Write(ProductDTO productDTO) {
		Map<String, Object> results = new HashMap<>();
		
		try {
			Long prodResult = proDao.productWrite(productDTO);
			Long prod_id = productDTO.getProd_id();
			results.put("prod_id", prod_id);
			if(prodResult < 1) {
				results.put("error","등록에 실패하였습니다.");
				return results;
			}
			else results.put("prodResult", prodResult);
			
		}
		catch (Exception e) {
			results.put("Error", e.fillInStackTrace());
		}
		
		
		
		
	}
}
