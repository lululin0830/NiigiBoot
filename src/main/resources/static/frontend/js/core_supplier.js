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


/* 登出並回到首頁 */
function logout() {
	clearCookie('jwt');
	clearCookie('supplierJwt')
	window.location.href = '/'
}

/* ------------------------------ 共用方法區結束 ------------------------------------ */

// 取得cookie中的jwtToken	
const jwtToken = getCookie('jwt')

let memberId; let memberAcct; let supplierId;

let isLoggedIn = false;

const done = new CustomEvent("coreDone")


/* 頁面初始化 */
function loginCheck() {

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

	}).then(function() {
		fetch('../supplier/login', {
			method: "POST",
			headers: {
				'Content-type': 'application/json',
				'Authorization': `Bearer ${jwtToken}`
			}, body: JSON.stringify({
				'memberId': memberId,
				'memberAcct': memberAcct
			})
		}).then(function(resp) {

			console.log(resp)

			if (!resp.ok) {
				return resp.text().then(function(errorMessage) {
					throw new Error(errorMessage);
				})
			}

			isLoggedIn = true;
			return resp.text();

		}).then(function(jwtToken) {


			setCookie('supplierJwt', jwtToken, 24);


		}).then(function() {

			const supplierJwt = getCookie('supplierJwt')

			fetch('../supplier/supplierCenter', {
				method: "POST",
				headers: {
					'Content-type': 'application/json',
					'Authorization': `Bearer ${supplierJwt}`
				}
			}).then(function(resp) {

				console.log(resp)
				if (!resp.ok) {
					return resp.text().then(function(errorMessage) {
						throw new Error(errorMessage);
					})
				}

				return resp.json();

			}).then(function(data) {
				supplierId = data.userId;
				console.log(supplierId);
				showUserInfo();
				document.dispatchEvent(done)
			})


		})
	}).catch(function(error) {

		console.log(error);
		clearCookie('jwt');
		clearCookie('supplierJwt');

		if (loginRequired) {
			sessionStorage.setItem("loginRequired", "true");
			alert("登入已過期，請重新登入")
			history.back();
		}
		if (error == "尚未成為商家") {
			alert("尚未成為商家")
			history.back();
		}

//		alert("登入已過期，請重新登入")
//		history.back();


	})
}

/* 渲染側邊欄 */
function showUserInfo() {

	const memberAccountBlock = document.querySelector('h4.memberAccount');
	const supplierIdBlock = document.getElementById("supplierId");
	const breadSupplierAcct = document.querySelector('#breadSupplierAcct a');

	if (memberAccountBlock) {
		memberAccountBlock.innerText = memberAcct.split("@")[0];
		supplierIdBlock.innerText = supplierId;
		breadSupplierAcct.innerText = memberAcct.split("@")[0];
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

loginCheck();

stopDefaultAction("a[href='#']");