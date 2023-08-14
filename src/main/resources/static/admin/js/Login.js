(() => {
    const userAcct = document.querySelector('#userAcct');
    const password = document.querySelector('#password');
    const errMsg = document.querySelector('#errMsg');
    document.getElementById('login').addEventListener('click', () => {
        fetch('http://localhost:8080/Niigi/users/Login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                userAcct: userAcct.value,
                password: password.value
            }),
         // body to text
        }).then(r => r.text())
        .then((jwtToken) => {
            // 解析 JWT 令牌
            try {
                const decodedToken = parseJwt(jwtToken);

                // 將JWT寫入Cookie，這裡假設有效期為1小時
                setCookie('jwt', jwtToken, 24); // 1/24代表1小時，若要設定其他時間，可以調整這個數值

                // 假設您的JWT存儲在名為"jwt"的Cookie中
                console.log(jwtToken);

                // 登錄成功，執行跳轉操作
                window.location.href = 'User_Management.html';
            } catch (error) {
                // JWT 解析失敗，顯示錯誤訊息
                errMsg.textContent = '登錄失敗，請檢查帳號和密碼。';
            }
        })
        .catch((error) => {
            // 登錄失敗，顯示錯誤訊息
            errMsg.textContent = '登錄失敗1，請檢查帳號和密碼。';
        });
});

// 解析 JWT 令牌
function parseJwt(token) {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(atob(base64).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
    return JSON.parse(jsonPayload);
}

// 設置 Cookie
function setCookie(name, value, hours) {
    const expires = new Date();
    expires.setTime(expires.getTime() + hours * 60 * 60 * 1000);
    document.cookie = `${name}=${value};expires=${expires.toUTCString()};path=/`;
}
})();