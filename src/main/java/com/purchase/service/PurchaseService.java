package com.purchase.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etc.CustomRandomGenerator;
import com.purchase.dto.OrderCacheDTO;
import com.purchase.dto.PayDTO;
import com.purchase.dto.PurchaseDTO;
import com.purchase.mapper.IOrderCacheService;
import com.purchase.mapper.IPayService;
import com.purchase.mapper.IPurchaseService;
import com.wishlist.dto.WishlistDTO;
import com.wishlist.mapper.IWishlistMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

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
	
	
	// 주문정보 임시저장
	public void saveOrderCaches(List<Long> prodIds, String encOrderId, List<Integer> qtys, HttpServletRequest req) {
		// 주문정보 임시저장
		for(int i=0; i<prodIds.size(); i++) {
			OrderCacheDTO orderCache = new OrderCacheDTO();
			orderCache.setOrder_num(encOrderId);
			orderCache.setProd_id(prodIds.get(i));
			orderCache.setQty(qtys.get(i));
			if(req.getParameterValues("wish_id")!=null) {
				orderCache.setWish_id(Long.parseLong(req.getParameterValues("wish_id")[i]));
			} 
			else orderCache.setWish_id(0L);
			
			orderDAO.insertOrderCache(orderCache);
		}
	}
	
	// 임시저장된 주문정보 불러오기
	public List<OrderCacheDTO> loadOrderCaches(String orderNum){
		List<OrderCacheDTO> result = orderDAO.selectOrderCache(orderNum);
		return result;
	}
	
	// 주문하기
	@Transactional
	public void purchase(OrderCacheDTO cache, Long memberId, String orderNum) {
		PurchaseDTO purchaseDTO = new PurchaseDTO();
		purchaseDTO.setMember_id(memberId);
		purchaseDTO.setOrder_num(orderNum);
		purchaseDTO.setProd_id(cache.getProd_id());
		purchaseDTO.setQty(cache.getQty());
		purchaseDTO.setPurc_request("부재시 경비실에 맡겨주세요");
		
		// 구매테이블에 추가
		purDAO.insertPurchase(purchaseDTO);
		// 구매 후 남은 재고 감소
		purDAO.updateQty(purchaseDTO.getProd_id(), cache.getQty());
		// 캐시테이블의 임시정보 삭제
		orderDAO.deleteOrderCache(orderNum);
		// 장바구니에서 삭제
		if(cache.getWish_id()!=null) {
			wishDAO.deleteWishlist(cache.getWish_id());
		}		
	}
	
}
