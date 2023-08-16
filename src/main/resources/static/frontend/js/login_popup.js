const backToPreviousModalBtn = document.getElementById("backToPreviousModal");

//Niigi登入回到選擇登入
backToPreviousModalBtn.addEventListener("click", function () {
    const currentModal = document.getElementById("login_Niigi");
    const previousModal = document.getElementById("login_popup");

    currentModal.style.display = "none";
    previousModal.style.display = "block";

    // 重新打開上一個彈窗
    const previousModalInstance = new bootstrap.Modal(previousModal);
    previousModalInstance.show();

    // 關閉當前彈窗
    const currentModalInstance = bootstrap.Modal.getInstance(currentModal);
    currentModalInstance.hide();
});
//忘記密碼回到Niigi登入
const backToPreviousModalBtn2 = document.getElementById("backToPreviousModal-2");
backToPreviousModalBtn2.addEventListener("click", function () {
    const currentModal = document.getElementById("forget_password");
    const previousModal = document.getElementById("login_Niigi");

    currentModal.style.display = "none";
    previousModal.style.display = "block";

    // 重新打開上一個彈窗
    const previousModalInstance = new bootstrap.Modal(previousModal);
    previousModalInstance.show();

    // 關閉當前彈窗
    const currentModalInstance = bootstrap.Modal.getInstance(currentModal);
    currentModalInstance.hide();
});
//重寄認證信回到Niigi登入
const backToPreviousModalBtn3 = document.getElementById("backToPreviousModal-3");
backToPreviousModalBtn3.addEventListener("click", function () {
    const currentModal = document.getElementById("resend_letter");
    const previousModal = document.getElementById("login_Niigi");

    currentModal.style.display = "none";
    previousModal.style.display = "block";

    // 重新打開上一個彈窗
    const previousModalInstance = new bootstrap.Modal(previousModal);
    previousModalInstance.show();

    // 關閉當前彈窗
    const currentModalInstance = bootstrap.Modal.getInstance(currentModal);
    currentModalInstance.hide();
});
//註冊回到Niigi登入
const backToPreviousModalBtn4 = document.getElementById("backToPreviousModal-4");
backToPreviousModalBtn4.addEventListener("click", function () {
    const currentModal = document.getElementById("register_account");
    const previousModal = document.getElementById("login_Niigi");

    currentModal.style.display = "none";
    previousModal.style.display = "block";

    // 重新打開上一個彈窗
    const previousModalInstance = new bootstrap.Modal(previousModal);
    previousModalInstance.show();

    // 關閉當前彈窗
    const currentModalInstance = bootstrap.Modal.getInstance(currentModal);
    currentModalInstance.hide();
});

//登入
(() => {
    const memberAcct = document.querySelector('#memberAcct');
    const currentPassword = document.querySelector('#currentPassword');
    const errMsg = document.querySelector('#errMsg');
    document.getElementById('login').addEventListener('click', () => {
        fetch('http://localhost:8080/Niigi/member/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                memberAcct: memberAcct.value,
                password: currentPassword.value
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

                    // 登錄成功，執行跳轉操作
                    window.location.href = 'customer_Information.html';
                } catch (error) {
                    // JWT 解析失敗，顯示錯誤訊息
                    errMsg.textContent = '登錄失敗，請檢查帳號和密碼。';
                }
            })
            .catch((error) => {
                // 登錄失敗，顯示錯誤訊息
                console.log(error)
                errMsg.textContent = '登錄失敗，請檢查帳號和密碼。';
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

const memberCaptchaImg = document.getElementById('company-yzm_img');
const captchaInput = document.getElementById('company-captchaInput');
const errMsg = document.getElementById('errMsg');
const registerButton = document.getElementById('register');

function initializeCaptchaImage() {
    changeYZM(memberCaptchaImg);
}

document.getElementById('company-yzm_img').addEventListener('click', function(){
    changeYZM(this);
});

function changeYZM(img) {
    fetch("http://localhost:8080/Niigi/generate-captcha")
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

function fetchCaptchaValidation() {
    return fetch("http://localhost:8080/Niigi/check-captcha", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify({
            userInput: captchaInput.value,
        }),
    })
    .then(response => response.json())
    .catch(error => {
        console.error("Error checking captcha:", error);
    });
}

registerButton.addEventListener('click', async () => {
    const isCaptchaValid = checkCaptchaAndNull(captchaInput.value, 'company-valistr_msg');

    if (!isCaptchaValid) {
        return;
    }

    const captchaValidationResult = await fetchCaptchaValidation();

    if (captchaValidationResult.valid) {
        const newMember = {
            memberAcct: document.getElementById('newmemberAcct').value,
            password: document.getElementById('newPassword').value,
            name: document.getElementById('fullname').value,
            phone: document.getElementById('phone').value,
            gender: document.querySelector('input[name="quantity"]:checked')?.value === 'male' ? '0' : '1',
            birthday: document.getElementById('birthday').value,
        };

        const newPassword = document.getElementById('newPassword').value;
        const confirmPassword = document.getElementById('confirmPassword').value;

        const errorElements = document.querySelectorAll('.error');
        errorElements.forEach(error => error.textContent = '');

        document.getElementById('passwordError').textContent = '';
        document.getElementById('confirmPasswordError').textContent = '';

        let isValid = true;

        if (!newMember.memberAcct) {
            document.getElementById('acctError').textContent = '請輸入帳號';
            isValid = false;
        }

        if (!newMember.password) {
            document.getElementById('passwordError').textContent = '請輸入密碼';
            isValid = false;
        }

        if (!newMember.name) {
            document.getElementById('fullnameError').textContent = '請輸入姓名';
            isValid = false;
        }
    
        if (!newMember.phone) {
            document.getElementById('phoneError').textContent = '請輸入電話';
            isValid = false;
        }
    
        if (!newMember.gender) {
            document.getElementById('genderError').textContent = '請選擇性別';
            isValid = false;
        }
    
        if (!newMember.birthday) {
            document.getElementById('birthdayError').textContent = '請輸入生日';
            isValid = false;
        }
    

        if (!isValid) {
            return;
        }

        if (newPassword !== confirmPassword) {
            document.getElementById('passwordError').textContent = '兩次輸入的密碼不相同。';
            document.getElementById('confirmPasswordError').textContent = '兩次輸入的密碼不相同。';
            return;
        }

        const response = await fetch('http://localhost:8080/Niigi/member/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(newMember),
        });

        const data = await response.text();

        if (response.ok) {
            alert(data); // 註冊成功
            window.location.replace('home_pop_ups.html'); // 導向到home_pop_ups.html
        } else {
            alert(data); // 註冊失敗，顯示錯誤訊息
        }
    } else {
        document.getElementById('company-valistr_msg').textContent = "驗證碼不正確！";
        document.getElementById('company-valistr_msg').style.color = "red";
    }
});
