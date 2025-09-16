$(document).ready(function() {
    let intervalId;

    // 상태 점 색상 변경 함수
    function updateStatusDot(status) {
        const $dot = $("#statusDot");
        $dot.removeClass("idle live stopped");
        if(status === "live") $dot.addClass("live");
        else if(status === "stopped") $dot.addClass("stopped");
        else $dot.addClass("idle");
    }

	// 이미지와 텍스트를 분리해서 출력
	function renderImages(data) {
	    const $wrap = $("#imageResult");

	    // 처음 한 번만 imageResult 내부를 2개로 나눔
	    if ($wrap.find("#imageResultImg").length === 0) {
	        $wrap.empty()
	             .append('<div id="imageResultImg" class="img-col"></div>')
	             .append('<div id="imageResultTxt" class="txt-col"></div>');
	    }

	    const $imgDiv = $("#imageResultImg");
	    const $txtDiv = $("#imageResultTxt");

	    // 갱신할 때마다 기존 데이터 지우고 새로 채움
	    $imgDiv.empty();
	    $txtDiv.empty();

	    data.forEach(item => {
	        // AI 결과 텍스트 분기
	        let aiText;
	        if (item.aiResult === "서버와의 연결이 끊겼습니다.") {
	            aiText = item.aiResult;
	        } else if (item.aiResult.includes("빨강")) {
	            aiText = "수확 가능!";
	        } else {
	            aiText = "아직 성장중이에요";
	        }

	        // 파일명
	        const fileName = item.file_path
	            ? item.file_path.split("/").pop()
	            : (item.fileName || "unknown.jpg");

	        // 이미지 전용 카드
	        const imgCard = `
	            <div class="image-card">
	                <div class="image-box">
	                    <img src="${item.file_path}" alt="crop-image"/>
	                </div>
	                <div class="image-info">${fileName}</div>
	            </div>
	        `;

	        // 텍스트 전용 카드
	        const txtCard = `
	            <div class="ai-result ${aiText === "수확 가능!" ? "ok" : "ng"}">
	                ${aiText}
	            </div>
	        `;

	        $imgDiv.append(imgCard);
	        $txtDiv.append(txtCard);
	    });
	}



    // 모니터링 시작
    $(document).on("click", "#startBtn", function() {
        $("#startBtn").hide();
        $("#stopBtn").show();
        $("#status").text("모니터링 중입니다...");
        updateStatusDot("live");

        // 즉시 한번 호출
        fetchData();

        // 10초마다 갱신
        intervalId = setInterval(fetchData, 10000);
    });

    // 모니터링 중단
    $(document).on("click", "#stopBtn", function() {
        clearInterval(intervalId);
        $("#stopBtn").hide();
        $("#startBtn").show();
        $("#status").text("대기중...");
        updateStatusDot("stopped");
    });

    // 서버에서 데이터 가져오기
    function fetchData() {
        $.getJSON(contextPath + "/seller/monitor-images.do", function(res) {
            if(res.result === "success") {
                renderImages(res.data);
                $("#update-time").show().text("최종 갱신: " + new Date().toLocaleTimeString());
            } else {
                $("#status").text("에러: " + res.error);
                updateStatusDot("stopped");
            }
        });
    }
});
