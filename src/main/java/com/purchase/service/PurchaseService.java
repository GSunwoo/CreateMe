package com.purchase.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etc.CustomRandomGenerator;
import com.purchase.dto.PayDTO;
import com.purchase.mapper.IOrderCacheService;
import com.purchase.mapper.IPayService;
import com.purchase.mapper.IPurchaseService;
import com.wishlist.dto.WishlistDTO;
import com.wishlist.mapper.IWishlistMapper;

@Service
public class PurchaseService {
	
	@Autowired
	IPayService payDAO;
	@Autowired
	IOrderCacheService orderDAO;
	@Autowired
	IPurchaseService purDAO;
	@Autowired
	IWishlistMapper wishDAO;
	
	// 장바구니 안의 상품 불러오기
	public List<WishlistDTO> getProducts(List<Long> wishlist){
		List<WishlistDTO> result = purDAO.selectProducts(wishlist);
		return result;
	}
	
	// 구매자 정보 불러오기
	public Map<String, String> setOrderInfo(Long memberId, String orderName, int totalPrice) {
		// 주문정보 가공 및 전달
		PayDTO payDTO = payDAO.getOrderInfoMember(memberId); // 구매자정보 가져오기
		payDTO.setMember_id(memberId);
		// 파라미터로 받은 주문정보
		payDTO.setProd_name(orderName);
		payDTO.setProd_price(totalPrice);
		
		// 주문명과 회원번호, 현재시간을 seed로 주문번호 생성
		LocalDateTime now = LocalDateTime.now();
		String nowTime = now.toString();
		String orderId = payDTO.getProd_name() + payDTO.getMember_id().toString() + nowTime;
		String encOrderId = CustomRandomGenerator.generateRandomString(orderId, 20);
		
		// toss payments API 형식에 맞춰 Map 생성
		Map<String, String> result = new HashMap<>();
		result.put("orderId", encOrderId);     // 주문번호
		result.put("orderName", payDTO.getProd_name()); // 주문이름
		result.put("successUrl", "/buyer/pay/success.do"); // 성공 url
		result.put("failUrl", "/buyer/pay/fail.do"); 	  // 실패 url
		result.put("customerEmail", payDTO.getEmailid()+"@"+payDTO.getEmaildomain()); // email
		result.put("customerName", payDTO.getName()); // 구매자명
		result.put("customerMobilePhone", payDTO.getPhone_num()); // 구매자 핸드폰번호
		return result;
	}
	
}
