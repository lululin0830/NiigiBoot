function getCookie(name) {
    const cookieValue = document.cookie.match('(^|;)\\s*' + name + '\\s*=\\s*([^;]+)');
    return cookieValue ? cookieValue.pop() : '';
}

function parseJwt(token) {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const decoded = JSON.parse(atob(base64));

    // 解析 sub 字段中的 JSON 字符串
    decoded.sub = JSON.parse(decoded.sub);

    return decoded;
}

const jwtToken = getCookie('jwt');

if (!jwtToken) {
    // 如果 JWT 不存在，導向登入頁面或其他處理方式
    window.location.href = 'Login_Page.html'; 
}

const decodedToken = parseJwt(jwtToken);

const userName = decodedToken.sub.userName; // 使用 sub.userName

const userNameLink = document.getElementById('userNameLink');
userNameLink.textContent = `${userName}`;

/* 清掉cookie */
function clearCookie(name) {
	const expires = new Date();
	expires.setTime(expires.getTime() - 1);
	document.cookie = `${name}= ;expires=${expires.toUTCString()};path=/`;
}

/* 登出並回到首頁 */
function logout(){
	clearCookie('jwt');
	window.location.href = './Login_Page.html'
}