/**
 * 前台-會員共用JS
 */

/* ------------------------------ 共用方法區開始 ------------------------------------ */

/* 設定cookie */
function setCookie(name, value, hours) {
	const expires = new Date();
	expires.setTime(expires.getTime() + hours * 60 * 60 * 1000);
	document.cookie = `${name}=${value};expires=${expires.toUTCString()};path=/`;
}

/* 取得指定的cookie */
function getCookie(name) {
	const value = `; ${document.cookie}`;
	const parts = value.split(`; ${name}=`);
	if (parts.length === 2) return parts.pop().split(';').shift();
}

/* 清掉cookie */
function clearCookie(name) {
	const expires = new Date();
	expires.setTime(expires.getTime() - 1);
	document.cookie = `${name}= ;expires=${expires.toUTCString()};path=/`;
}

/* 停止點擊時的預設行為 */
function stopDefaultAction(selector) {
	document.querySelectorAll(`${selector}`).forEach(function(a) {
		a.onclick = function(e) { e.preventDefault(); }
	})

}

/* 驗證必填欄位是否都有填 */
function validateForm(document) {

	document.querySelectorAll('input[required]').forEach(function(e) {

		if (e.value.trim() == '') {
			return false;
		}
		return true;
	})

}

/* 將文字傳遞給下一頁 */
function passTextToNextPage(text) {
	localStorage.setItem('textData', text);
}

/* 登出並回到首頁 */
function logout() {
	clearCookie('jwt');
	window.location.href = '../index.html'
}

/* ------------------------------ 共用方法區結束 ------------------------------------ */


/* --------------------------- 登入驗證相關方法開始 --------------------------------- */

/* 取得使用者資訊 */

// 取得cookie中的jwtToken	
const jwtToken = getCookie('jwt')

// 取得側邊欄的使用者資訊區塊


let memberId; let memberAcct;

let isLoggedIn = false;

/* 頁面初始化 */
function init() {

	fetch('../member/customerCenter', {
		method: "POST",
		headers: {
			'Content-type': 'application/json',
			'Authorization': `Bearer ${jwtToken}`
		}
	}).then(function(resp) {

		console.log(resp)

		if (!resp.ok) {
			return resp.text().then(function(errorMessage) {
				throw new Error(errorMessage);
			})
		}

		isLoggedIn = true;
		return resp.json();

	}).then(function(data) {
		memberId = data.userId;
		memberAcct = data.username;

		showHeader();
		showUserInfo();

	}).catch(function(error) {

		console.log(error);
		clearCookie('jwt');

		if (loginRequired) {
			sessionStorage.setItem("loginRequired", "true");
			alert("登入已過期，請重新登入")
			history.back();
		}
		showHeader();
	})
}

/* Header渲染 */
function showHeader() {

	let html = `
		<header id="main-header" class="header">

        <div class="container-sm">

            <div class="header-top row">

                <div class="col-sm-2">
                    <a class="header-logo" href="/"><img class="logo" src="./image/logo.svg" alt=""></a>
                </div>

                <div class="col-sm-6">
                    <form action="KeyWordSerch.html" method="get" class="header-search row" onsubmit="return validateForm(this)" >
                        <input type="text" name="search" class="header-search-input col-sm-10" placeholder="Search"
                            maxlength="50" value="" required>

                        <button type="submit" class="header-search-submit col-sm-1"><img
                                src="./image/search.svg"></button>
                    </form>
                </div>

               `;

	if (isLoggedIn) {
		html += `
	 			<nav class="fixed-nav col-sm-4">
                    <div class="fixed-nav-list login row">
                    	<a href="" class="SCM-Center col">
                            <div class="SCM_icon">
                                <button class="btn_SCM-Center">商家中心</button>
                            </div>
                        </a>

                        <a href="check_order.html" class="User-Center col">
                            <div class="User_icon">
                                <img src="./image/Profile.svg" alt="">
                            </div>
                        </a>
                         <a href="collect_box.html" class="Favorite col">
                            <div class="header_icon">
                                <img src="./image/heart.svg" alt="">
                            </div>
                        </a>

                        <a href="shopping_cart.html" class="Shopping-Cart col">
                            <div class="header_icon">
                                <img src="./image/Buy.svg" alt="">
                            </div>
                        </a>

                        <a href="#" class="Notification col">
                            <div class="header_icon">
                                <img src="./image/Notification.svg" alt="">
                            </div>
                        </a>`;
	} else {
		html += `
	<div class="col-sm-1"></div>
	<nav class="fixed-nav col-sm-3">
         <div class="fixed-nav-list notLogin row">
			<a href="#" class="SCM-Center col">
                <div class="login_icon col">
                    <button class="btn-login" data-bs-toggle="modal"
                                data-bs-target="#login_popup">登入/註冊</button>
                </div>
            </a>
            <a href="shopping_cart.html" class="Shopping-Cart col">
                <div class="header_icon">
                    <img src="./image/Buy.svg" alt="">
                </div>
            </a>`;
	}

	html += `</div></nav></div></div></header>`;

	document.querySelector("body").insertAdjacentHTML("afterbegin", html)

}

/* 渲染側邊欄 */
function showUserInfo() {

	const memberAccountBlock = document.querySelector('h4.memberAccount');
	const memberIdBlock = document.querySelector('h6.memberId');

	if (memberAccountBlock) {
		memberAccountBlock.innerText = memberAcct.split("@")[0];
		memberIdBlock.innerText = memberId;
		fontSizeAdjust(memberAccountBlock);
	}

}

/* 側邊欄會員資訊文字大小設定 */
function fontSizeAdjust(element) {
	// 取得文字长度
	let textLength = element.textContent.trim().length;

	// 設定文字長度與字體大小的映射關係
	let lengthToFontSize = {
		10: '1.4rem',
		15: '1.2rem',
		20: '1rem',
		25: '0.8rem',
	};

	// 設定預設大小
	let fontSize = '1.6rem';

	// 取得最接近的映射關係
	for (let length in lengthToFontSize) {
		if (textLength >= length) {
			fontSize = lengthToFontSize[length];
		}
	}

	// 設置對應的字體大小
	element.style.setProperty('--font-size', fontSize);
}


/* ------------------------- 方法呼叫區 ------------------------- */
if (jwtToken) {
	
	init();
} else if (loginRequired) {
	
	sessionStorage.setItem("loginRequired", "true");
	alert("請先登入")

	if (document.referrer === '') { // 直接通過網址的請求，導回首頁
		window.location.href = '/'
		
	} else {
		history.back();
	}

} else {
	showHeader();
}

document.addEventListener('DOMContentLoaded', function() {

	if (sessionStorage.getItem("loginRequired")) {
		document.querySelector("button.btn-login").click();
		sessionStorage.removeItem("loginRequired");
	}
});


stopDefaultAction("a[href='#']");


