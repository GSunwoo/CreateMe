<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>문의 상세 보기</title>
  <style>
    body {
      font-family: 'Noto Sans KR', sans-serif;
      padding: 40px;
      background: #f8f9fa;
    }

    .view-container {
      max-width: 800px;
      margin: auto;
      background: #fff;
      padding: 30px;
      border: 1px solid #ddd;
      border-radius: 10px;
    }

    h2 {
      margin-bottom: 20px;
    }

    .view-row {
      display: flex;
      margin-bottom: 15px;
    }

    .view-label {
      width: 150px;
      font-weight: bold;
      color: #333;
    }

    .view-value {
      flex: 1;
      color: #555;
    }

    .back-btn {
      display: inline-block;
      margin-top: 30px;
      padding: 8px 16px;
      background: #4CAF50;
      color: white;
      text-decoration: none;
      border-radius: 5px;
    }

    .back-btn:hover {
      background: #45a049;
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
  <div class="view-container">
    <h2>문의 상세 보기</h2>

    <div class="view-row">
      <div class="view-label">문의번호</div>
      <div class="view-value">${inquiry.inquiry_id}</div>
    </div>

    <div class="view-row">
      <div class="view-label">상품번호</div>
      <div class="view-value">${inquiry.prod_id}</div>
    </div>

    <div class="view-row">
      <div class="view-label">제목</div>
      <div class="view-value">${inquiry.title}</div>
    </div>

    <div class="view-row">
      <div class="view-label">내용</div>
      <div class="view-value">${inquiry.content}</div>
    </div>

    <div class="view-row">
      <div class="view-label">작성자 ID</div>
      <div class="view-value">${inquiry.user_id}</div>
    </div>
	
		<p>
		<a href="/admin/inquiry/delete.do?prod_id=${inquiry.prod_id}">삭제</a>
	</p>
    <a href="/admin/inquiry/list.do" class="back-btn">← 목록으로</a>
  </div>

</body>
</html>
