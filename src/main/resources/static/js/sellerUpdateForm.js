document.addEventListener("DOMContentLoaded", () => {
  const root = document.querySelector(".seller-update-section");
  if (!root) return;

  const formData = {
    bizNum: "", ceoName: "", companyName: "", zipcode: "",
    address: "", detailAddress: "", depositor: "",
    accountNumber: "", bank: ""
  };
  let isVerified = false;

  const form = root.querySelector("#sellerUpdateForm");
  const el = {
    bizNum: root.querySelector("#bizNum"),
    ceoName: root.querySelector("#ceoName"),
    companyName: root.querySelector("#companyName"),
    zipcode: root.querySelector("#zipcode"),
    address: root.querySelector("#address"),
    detailAddress: root.querySelector("#detailAddress"),
    depositor: root.querySelector("#depositor"),
    accountNumber: root.querySelector("#accountNumber"),
    bank: root.querySelector("#bank")
  };
  const btnVerify = root.querySelector("#btnVerify");
  const submitBtn = root.querySelector("#submitBtn");

  function render() {
    el.bizNum.value = formData.bizNum;
    el.ceoName.value = formData.ceoName;
    el.companyName.value = formData.companyName;
    el.zipcode.value = formData.zipcode;
    el.address.value = formData.address;
    el.detailAddress.value = formData.detailAddress;
    el.depositor.value = formData.depositor;
    el.accountNumber.value = formData.accountNumber;
    el.bank.value = formData.bank;
    updateValidity();
  }

  function updateValidity() {
    const valid =
      isVerified &&
      formData.ceoName !== "" &&
      formData.companyName !== "" &&
      formData.address !== "" &&
      formData.depositor !== "" &&
      formData.accountNumber !== "" &&
      formData.bank !== "";
    submitBtn.disabled = !valid;
  }

  function handleChange(e) {
    const { id, value } = e.target;
    formData[id] = value;
    render();
  }

  el.bizNum.addEventListener("input", (e) => {
    e.target.value = e.target.value.replace(/[^0-9]/g, "");
    formData.bizNum = e.target.value;
  });

  function execDaumPostcode() {
    if (window.daum && window.daum.Postcode) {
      new window.daum.Postcode({
        oncomplete: (data) => {
          formData.zipcode = data.zonecode || "";
          formData.address = data.address || "";
          render();
          el.detailAddress.focus();
        }
      }).open();
    } else {
      alert("다음 우편번호 API 없음. 더미 주소 입력");
      formData.zipcode = "04524";
      formData.address = "서울 중구 세종대로 110";
      render();
      el.detailAddress.focus();
    }
  }
  el.zipcode.addEventListener("click", execDaumPostcode);
  el.address.addEventListener("click", execDaumPostcode);

  ["detailAddress", "bank"].forEach((id) => {
    root.querySelector("#" + id).addEventListener("input", handleChange);
    root.querySelector("#" + id).addEventListener("change", handleChange);
  });

  btnVerify.addEventListener("click", () => {
    const biz = el.bizNum.value.trim();

    if (biz === "1234567890") {
      Object.assign(formData, {
        ceoName: "홍길동", companyName: "길동상회",
        zipcode: "12345", address: "서울시 종로구 종로1가",
        detailAddress: "101호", depositor: "홍길동",
        accountNumber: "110-123-456789"
      });
      isVerified = true; render(); return;
    }

    if (biz === "1234") {
      Object.assign(formData, {
        ceoName: "이몽룡", companyName: "남원상사",
        zipcode: "04524", address: "서울 중구 세종대로 110",
        detailAddress: "본관 3층", depositor: "이몽룡",
        accountNumber: "333-22-444555"
      });
      isVerified = true; render(); return;
    }
	
	if (biz === "1029384756") {
	  Object.assign(formData, {
	    ceoName: "김민수", companyName: "한빛테크",
	    zipcode: "06164", address: "서울특별시 강남구 테헤란로 123",
	    detailAddress: "한빛타워 12층", depositor: "김민수",
	    accountNumber: "110-321-654321"
	  });
	  isVerified = true; render(); return;
	}
	if (biz === "5849201376") {
	  Object.assign(formData, {
	    ceoName: "이서연", companyName: "블루스톤",
	    zipcode: "48093", address: "부산광역시 해운대구 센텀동로 25",
	    detailAddress: "센텀파크 701호", depositor: "이서연",
	    accountNumber: "302-001-123456"
	  });
	  isVerified = true; render(); return;
	}
	
	if (biz === "7391028456") {
	  Object.assign(formData, {
	    ceoName: "박지훈", companyName: "그린팜",
	    zipcode: "21633", address: "인천광역시 남동구 인주대로 401",
	    detailAddress: "그린스퀘어 5층", depositor: "박지훈",
	    accountNumber: "110-482-938475"
	  });
	  isVerified = true; render(); return;
	}
	
	if (biz === "4501928374") {
	  Object.assign(formData, {
	    ceoName: "최유진", companyName: "스마일로지스",
	    zipcode: "42177", address: "대구광역시 수성구 달구벌대로 2450",
	    detailAddress: "스마일빌딩 803호", depositor: "최유진",
	    accountNumber: "333-210-567890"
	  });
	  isVerified = true; render(); return;
	}
	
	if (biz === "9182736450") {
	  Object.assign(formData, {
	    ceoName: "정현우", companyName: "오션아이티",
	    zipcode: "34168", address: "대전광역시 유성구 대학로 99",
	    detailAddress: "오션타워 17층", depositor: "정현우",
	    accountNumber: "110-775-112233"
	  });
	  isVerified = true; render(); return;
	}
	
	if (biz === "5678901234") {
	  Object.assign(formData, {
	    ceoName: "오지훈", companyName: "금강커머스",
	    zipcode: "61253", address: "광주광역시 북구 무등로 260",
	    detailAddress: "금강프라자 301호", depositor: "오지훈",
	    accountNumber: "301-005-667788"
	  });
	  isVerified = true; render(); return;
	}
	
	if (biz === "3141592653") {
	  Object.assign(formData, {
	    ceoName: "장미경", companyName: "한결식품",
	    zipcode: "16490", address: "경기도 수원시 팔달구 정조로 825",
	    detailAddress: "한결센터 B동 2층", depositor: "장미경",
	    accountNumber: "110-159-265358"
	  });
	  isVerified = true; render(); return;
	}
	
	if (biz === "2718281828") {
	  Object.assign(formData, {
	    ceoName: "윤태호", companyName: "네오팜존",
	    zipcode: "30151", address: "세종특별자치시 나성북로 20",
	    detailAddress: "네오스퀘어 912호", depositor: "윤태호",
	    accountNumber: "352-828-182845"
	  });
	  isVerified = true; render(); return;
	}
	
	if (biz === "1357924680") {
	  Object.assign(formData, {
	    ceoName: "서지민", companyName: "라이트하우스",
	    zipcode: "63122", address: "제주특별자치도 제주시 중앙로 217",
	    detailAddress: "라이트타워 9층", depositor: "서지민",
	    accountNumber: "110-357-924680"
	  });
	  isVerified = true; render(); return;
	}
	
	if (biz === "2468135790") {
	  Object.assign(formData, {
	    ceoName: "한상우", companyName: "에코마켓",
	    zipcode: "05612", address: "서울특별시 송파구 올림픽로 300",
	    detailAddress: "에코몰 A-1204", depositor: "한상우",
	    accountNumber: "290-468-135790"
	  });
	  isVerified = true; render(); return;
	}
	
    alert("사업자등록번호가 유효하지 않습니다.");
    isVerified = false; render();
  });

/*  form.addEventListener("submit", (e) => {
    e.preventDefault();
    if (submitBtn.disabled) return;
    // 서버 전송 대신 콘솔로 확인 (백엔드 연동 시 fetch로 교체)
    const payload = {
      farm_id: formData.bizNum,
      owner_name: formData.ceoName,
      brand_name: formData.companyName,
      com_zip: formData.zipcode,
      com_addr1: formData.address,
      com_addr2: formData.detailAddress,
      entryman: formData.depositor,
      account: formData.accountNumber,
      bank: formData.bank
    };
    console.log("제출 데이터:", payload);
    alert("사업자 정보 등록 완료!");
  });*/

  render();
});


