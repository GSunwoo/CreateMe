package com.farm.product.prod_img.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import com.farm.product.prod_img.dto.ProductImgDTO;
import com.farm.product.prod_img.mapper.IProductImgMapper;

import utils.UploadUtils;

public class ProductImgController {

	@Autowired
	private IProductImgMapper imgDao;
	
	public void insertImg(Long prod_id, Long main_idx,
			List<MultipartFile> files, Long last_idx) {
		
		try {
			
			String uploadDir = ResourceUtils.getFile(
					"classpath:static/uploads/prodimg/prod_id").toPath().toString();
			System.out.println("저장경로 : " + uploadDir);
			File dir = new File(uploadDir, String.valueOf(prod_id));

			if(!dir.exists()) {
				dir.mkdirs();
			}

			
			Long i = (last_idx != null) ? last_idx + 1 : 1L;
			for(MultipartFile file : files) {
				if (file.isEmpty()) {
					continue;
				}
	
				String savedFileName =
						UploadUtils.getNewFileName(file.getOriginalFilename());
				File dest = new File(dir, savedFileName);
				file.transferTo(dest);
				
				 System.out.println("저장 성공: " + dest.getAbsolutePath());
				
				ProductImgDTO productImgDTO = new ProductImgDTO();
				
				productImgDTO.setFilename(savedFileName);
				productImgDTO.setIdx(i);
				productImgDTO.setProd_id(prod_id);
				
				
				if(i == main_idx) {
					productImgDTO.setMain_idx(1);
				}
				else {
					productImgDTO.setMain_idx(0);
				}
				
				int insertResult = imgDao.insertImg(productImgDTO);
				
				if(insertResult == 1) {
					System.out.printf("전체 파일 %d 중 %d번째 파일업로드 성공", files.size(), i);
				}
				else {
					System.err.printf("전체 파일 %d 중 %d번째 파일업로드 실패", files.size(), i);
				}
				i++;
			}
			
		}
		catch (Exception e) {
			System.out.println("이미지 업로드 실패 : ");
			e.printStackTrace();
		}
		
		

	}
}
