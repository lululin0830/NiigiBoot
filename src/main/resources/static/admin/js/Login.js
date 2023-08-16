const memberCaptchaImg = document.getElementById('company-yzm_img');
const userAcct = document.querySelector('#userAcct');
const password = document.querySelector('#password');
const captchaInput = document.querySelector('#company-captchaInput');
const errMsg = document.querySelector('#errMsg');

function initializeCaptchaImage() {
    changeYZM(memberCaptchaImg);
}

document.getElementById('company-yzm_img').addEventListener('click', function(){
    changeYZM(this);
});

function changeYZM(img) {
    fetch("/generate-captcha")
        .then(response => response.blob())
        .then(blob => {
            const imgUrl = URL.createObjectURL(blob);
            img.src = imgUrl;
        })
        .catch(error => console.error("Error fetching captcha:", error));
}

initializeCaptchaImage();
function checkCaptchaAndNull(inputValue, errorMsgId) {
    const valistrMsg = document.getElementById(errorMsgId);
    if (inputValue === "") {
        valistrMsg.textContent = "驗證碼不能為空！";
        valistrMsg.style.color = "red";
        return false;
    } else {
        valistrMsg.textContent = "";
        return true;
    }
}

document.getElementById('login').addEventListener('click', () => {
    const isCaptchaValid = checkCaptchaAndNull(captchaInput.value, 'company-valistr_msg');
    
    if (!isCaptchaValid) {
        return;
    }

    fetch("/check-captcha", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            userInput: captchaInput.value,
        }),
    })
    .then((response) => response.json())
    .then((data) => {
        if (data.valid) {
            fetch("/users/Login", {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({
                    userAcct: userAcct.value,
                    password: password.value
                }),
            })
            .then(r => r.text())
            .then((jwtToken) => {
                try {
                    const decodedToken = parseJwt(jwtToken);
                    setCookie('jwt', jwtToken, 1 / 24);
                    console.log(jwtToken);
                    window.location.href = 'User_Management.html';
                } catch (error) {
                    errMsg.textContent = '登錄失敗，請檢查帳號和密碼。';
                }
            })
            .catch((error) => {
                errMsg.textContent = '登錄失敗，請檢查帳號和密碼。';
            });
        } else {
            document.getElementById('company-valistr_msg').textContent = "驗證碼不正確！";
            document.getElementById('company-valistr_msg').style.color = "red";
        }
    })
    .catch((error) => {
        console.error("Error checking captcha:", error);
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

// initializeCaptchaImage();
