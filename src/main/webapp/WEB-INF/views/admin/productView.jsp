<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>상품 상세 정보</title>

<style>
/* 기본 레이아웃 스타일 */
    body {
      font-family: 'Noto Sans KR', sans-serif;
      padding: 40px;
      background: #f0f2f5;
    }
/* 엑셀처럼 격자무늬 표 스타일 */
table {
	width: 100%;
	border-collapse: collapse;
}

th, td {
	border: 1px solid #ddd;
	padding: 8px;
	text-align: left;
}

th {
	background-color: #f2f2f2;
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
      <jsp:include page="/WEB-INF/views/admin/mypage.jsp"/>
    </aside>
    
	<h2>상품 상세 정보</h2>
	<table>
		<tbody>
			<tr>
				<th>상품번호</th>
				<td>${product.prod_id}</td>
			</tr>
			<tr>
				<th>상품명</th>
				<td>${product.prod_name}</td>
			</tr>
			<tr>
				<th>상품등록일</th>
				<%-- <td><fmt:formatDate value="${product.prod_date}" pattern="yyyy-MM-dd" /></td> --%>
			</tr>
			<tr>
				<th>상품가격</th>
				<td>${product.prod_price}</td>
			</tr>
			<tr>
				<th>상품재고</th>
				<td>${product.prod_stock}</td>
			</tr>
			<tr>
				<th>품목</th>
				<td>${product.prod_cate}</td>
			</tr>
			<tr>
				<th>상품설명</th>
				<td>${product.prod_content}</td>
			</tr>
		</tbody>
	</table>

	<p>
		<a href="/admin/product/delete.do?prod_id=${product.prod_id}"
			onclick="return confirm('삭제하시겠습니까?');">삭제</a>
	</p>
	</div>
</body>
</html>