/**
 * 前台-會員中心共用JS
 */

/* 取得側邊欄資訊 */
const memberAccountBlock = document.querySelector('h4.memberAccount');
const memberIdBlock = document.querySelector('h6.memberId');


// 取出Cookie中指定名稱的值
function getCookie(name) {
	const value = `; ${document.cookie}`;
	const parts = value.split(`; ${name}=`);
	if (parts.length === 2) return parts.pop().split(';').shift();
}
const jwtToken = getCookie('jwt')

console.log(jwtToken);

function init() {



	fetch('../member/customerCenter', {
		method: "POST",
		headers: {
			'Content-type': 'application/json',
			'Authorization': jwtToken
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

		console.log(data);
		
		memberAccountBlock.innerText = data.username.split("@")[0];
		memberIdBlock.innerText = data.userId;

	}).catch(function(error) {

		console.log(error);
	})
}

init();

/* 側邊欄會員資訊文字大小設定 */
// 獲取.member_account元素


function fontSizeAdjust(element) {
	// 取得文字长度
	let textLength = element.textContent.trim().length;

	// 設定文字長度與字體大小的映射關係
	let lengthToFontSize = {
		5: '1.6rem',
		10: '1.4rem',
		15: '1.2rem',
		20: '1rem',
		25: '0.8rem',
	};

	// 找到最接近但小于等于当前文字长度的映射关系的字体大小
	let fontSize = '1.6rem'; // 默认字体大小，若文字长度小于5，则使用最小字体大小
	for (let length in lengthToFontSize) {
		if (textLength >= length) {
			fontSize = lengthToFontSize[length];
		}
	}

	// 設置對應的字體大小
	element.style.setProperty('--font-size', fontSize);
}


// 暫時沒有下連結的a標籤，停止預設行為(回到頁面頂端)
document.querySelectorAll("a[href='#']").forEach(function(a) {
	a.onclick = function(e) { e.preventDefault(); }
})


fontSizeAdjust(memberAccountBlock);