package com.review.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.dto.PageDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.login.config.CustomUserDetails;
import com.member.dto.MemberDTO;
import com.review.dto.ReviewBoardDTO;
import com.review.dto.ReviewImgDTO;
import com.review.mapper.ReviewBoardMapper;
import com.review.mapper.ReviewImgMapper;
import com.review.mapper.ReviewLikeMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import utils.UploadUtils;


@Service
public class ReviewBoardService {

	@Autowired
	ReviewBoardMapper dao;
	
	@Autowired
	ReviewImgMapper Imgdao;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private ReviewLikeMapper reviewLikeMapper;
	
	//무한스크롤
	public List<JSONObject> reviewInfinity(PageDTO pageDTO, Long member_id) {

		List<JSONObject> result = new ArrayList<>();
		List<ReviewBoardDTO> selectReviewList = dao.listPage(pageDTO);
		for(int i = 0 ; i < selectReviewList.size() ; i ++) {
			ReviewBoardDTO review = selectReviewList.get(i);
			review.setReview_like(dao.countLike(review.getReview_id()));
			if(member_id!=null) {
				Long logindata = member_id;
				review.setReview_liked(dao.existsLike(review.getReview_id(), logindata)==1 ? true : false);
				System.out.println(review.isReview_liked() + "memberId : " + logindata);				
			}
			else {
				review.setReview_liked(false);
			}
		}

		for (ReviewBoardDTO review : selectReviewList) {
			Map<String, Object> reviewMap = objectMapper.convertValue(review, Map.class);
			JSONObject reviewJSON = new JSONObject(reviewMap);
			result.add(reviewJSON);
		}
		
		return result;

	}
	
	//열람
	public ReviewBoardDTO viewDetails(Long reviewId) {
		//글 상세
		ReviewBoardDTO dto = dao.selectView(reviewId);
		
		return dto;
	}
	
	//리뷰 좋아요 
	public Map<String, Object> reivewLike(Long reviewId, Long member_Id) {
		
		//상위클래스 -> Map, 하위클래스 -> HashMap
		Map<String, Object> map = new HashMap<String, Object>();
		
		//비로그인 처리
		//userDetails객체 자체가 null인지 확인
		//|| : OR연산자 - 둘 중 하나라도 true면 전체가 true
		//userDetails.getMemberDTO() == null -> 의미 : 로그인하지 않았거나 사용자 정보가 없는 상태
		if (member_Id == null) {
            map.put("success", false);
            map.put("message", "로그인이 필요합니다.");
            //로그인이 필요한 기능의 결과값들을 null로 설정
            map.put("liked", null);
            map.put("count", null);
            return map;
        }
		
		
		try {
			//2) 토글 실행 (true=좋아요 추가, false=좋아요 취소)
            boolean liked = reviewLikeMapper.toggleLike(reviewId, member_Id);

            //3) 현재 좋아요 수 재조회
            int count = reviewLikeMapper.countLike(reviewId);

            map.put("success", true);
            //boolean 변수로 현재 좋아요 상태
            map.put("liked", liked);
            //해당 게시물 총 좋아요 개수
            map.put("count", count);
            map.put("message", liked ? "좋아요가 추가되었습니다." : "좋아요가 취소되었습니다.");
            return map;
            
        } catch (DataIntegrityViolationException e) {
            int count = reviewLikeMapper.countLike(reviewId);
            map.put("success", false);
            map.put("liked", null);
            map.put("count", count);
            map.put("message", "처리 중 충돌이 발생했습니다. 다시 시도해 주세요.");
            return map;

        } catch (Exception e) {
            map.put("success", false);
            map.put("liked", null);
            map.put("count", null);
            map.put("message", "알 수 없는 오류가 발생했습니다.");
		}
		return map;
				
	}
	
	//좋아요 삽입
	public int likeInsert(Long review_Id, Long member_Id) {
		 //DAO를 통해 DB에서 (review_Id, member_Id) 조건으로 좋아요를 삽입
		 int result = dao.insertLike(review_Id, member_Id);
		 if(result > 0) {
			 return 200;
		 }
		 else {
			 return 0;
		 }
	}
	
	//좋아요 삭제 
	public ResponseEntity<Void> likeDelete(Long review_Id, Long member_Id) {
		System.out.println("딜리트 들어옴?");
		 //DAO를 통해 DB에서 (review_Id, member_Id) 조건으로 좋아요를 삭제
		 int inserted = dao.deleteLike(review_Id, member_Id);
		 
		 return ResponseEntity.ok().build();
	}
	
	//쓰기
	public int reviewWrite(ReviewBoardDTO reviewboardDTO) {

		int result = dao.write(reviewboardDTO);
		System.out.println("글쓰기결과 : " + result);

		return result;
	}
	
	//이미지 업로드
		public int insertImg(Long review_id, HttpServletRequest req) {

			// 업로드 과정에서 에러가 날 수 있으니 예외처리 시작
			try {
				// 물리적 경로 얻어오기
				String uploadDir = ResourceUtils.getFile("classpath:static/uploads/reviewimg/").toPath().toString();
				System.out.println("저장경로 : " + uploadDir);
				String sep = File.separator;
				File dir = new File(uploadDir + sep + review_id);
				if (!dir.exists()) {
					dir.mkdirs();
				}

				long idx = 1;
				Collection<Part> parts = req.getParts();
				for (Part part : parts) {
					if (!part.getName().equals("uploadFile")) {
						continue;
					}
					ReviewImgDTO reviewimgDTO = new ReviewImgDTO();
					String partHeader = part.getHeader("content-disposition");
					String[] phArr = partHeader.split("filename=");
					String originalFileName = phArr[1].trim().replace("\"", "");
					String saveFile = UploadUtils.getNewFileName(originalFileName);

					if (!saveFile.isEmpty()) {
						part.write(uploadDir + sep + review_id + sep + saveFile);
					}
					reviewimgDTO.setFilename(saveFile);
					reviewimgDTO.setIdx(idx);
					reviewimgDTO.setReview_id(review_id);

					if (idx == 1) {
						reviewimgDTO.setMain("main");
					}

					int Result = Imgdao.insertImg(reviewimgDTO);

					if (Result == 1) {
						System.out.printf("전체 파일 %d 중 %d번째 파일업로드 성공", parts.size(), idx);
					} else {
						System.err.printf("전체 파일 %d 중 %d번째 파일업로드 실패", parts.size(), idx);
					}
					idx++;
				}

			} catch (Exception e) {
				System.out.println("업로드 실패");
				e.printStackTrace();
			}

			int result = 1;
			return result;
		}
}
