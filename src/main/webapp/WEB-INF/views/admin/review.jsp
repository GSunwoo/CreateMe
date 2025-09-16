<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>리뷰 관리</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">

<style>
/* 기본 레이아웃 스타일 */
body {
	font-family: 'Noto Sans KR', sans-serif;
	padding: 40px;
	background: #f0f2f5;
}

table {
	width: 100%;
	border-collapse: collapse; /* 셀 간 경계선 합치기 */
	background: #fff;
}


th, td {
	border: 1px solid #ddd; /* 격자무늬 테두리 */
	padding: 10px;
	text-align: center;
	background: #fff;
}

thead th {
	background-color: #f0f0f0;
	font-weight: bold;
}

.review-thumb {
	width: 80px;
	height: 80px;
	object-fit: cover;
}

.no-data {
	text-align: center;
	color: #999;
}
 /* 헤더 기준으로 우측 상단 고정 */
.admin-topbar { position: sticky; top: 0; z-index: 10; }
.admin-topbar { position: relative; }  /* absolute 기준점 */

.logout-area{
  position: absolute;
  right: 16px;
  top: 50%;
  transform: translateY(-50%);
  margin: 0;
  padding: 0;
  list-style: none;        /* • 제거 */
}

.logout-area li{ display:inline-block; }

/* 로그아웃 버튼 – 테두리 또렷하게 */
.logout-area a{
  display:inline-flex; align-items:center; gap:6px;
  padding:8px 14px;
  border-radius:999px;
  text-decoration:none;
  font-weight:600; font-size:13px;
  background:#fff;                /* 흰 배경 */
  color:#111;                     /* 진한 텍스트 */
  border:1px solid #d1d5db;       /* 진한 회색 테두리(slate-300) */
  box-shadow: 0 1px 2px rgba(16,24,40,.06); /* 살짝 음영으로 경계 강화 */
  transition: background .2s, border-color .2s, color .2s, transform .1s;
}

.logout-area a:hover{
  background:#f9fafb;
  border-color:#9ca3af;           /* 조금 더 진하게 */
  transform: translateY(-1px);
}
.logout-area a:active{ transform: translateY(0); }    
</style>
</head>
<body>
<header class="admin-topbar">
    <ul class="logout-area">
      <li><a href="/myLogout.do">로그아웃</a></li>
    </ul>
  <div class="topbar-inner">
    <h1 class="welcome-msg">환영합니다 관리자님</h1>
  </div>
</header>

	<div class="admin-wrap">
		<!-- 좌측 사이드바: 네가 가진 mypage.jsp 그대로 include -->
		<aside class="admin-sidebar">
			<jsp:include page="/WEB-INF/views/admin/mypage.jsp" />
		</aside>
		<div class="container">
			<h2>리뷰 관리</h2>

			<div class="review-table-container">
				<table>
					<colgroup>
						<col style="width: 10%;">
						<col style="width: 10%;">
						<col style="width: 10%;">
						<col style="width: 15%;">
						<col style="width: 45%;">
						<col style="width: 5%;">
					</colgroup>
					<thead>
						<tr>
							<th>주문번호</th>
							<th>날짜</th>
							<th>상품명</th>
							<th>리뷰제목</th>
							<th>내용</th>
							<th></th>
						</tr>
					</thead>
					<tbody>
						<c:if test="${not empty reviewList}">
							<c:forEach var="rvs" items="${reviewList}">
								<tr>
									<td><c:out value="${rvs.purc_id}" /></td>
									<td><time>
											<c:out value="${rvs.postdate}" />
										</time></td>
									<td><c:out value="${rvs.prod_name}" /></td>

									<td><c:out value="${rvs.title}" /></td>

									<td class="review-content"><c:out value="${rvs.content}" /></td>
									
									<td><a href="/admin/review/delete.do">삭제</a></td>
								</tr>
							</c:forEach>
						</c:if>

						<c:if test="${empty reviewList}">
							<tr>
								<td colspan="5" class="no-data">리뷰가 없습니다.</td>
							</tr>
						</c:if>
					</tbody>
				</table>
			</div>
		</div>
	</div>
	</div>
</body>
</html>