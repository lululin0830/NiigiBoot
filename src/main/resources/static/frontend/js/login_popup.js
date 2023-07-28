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

// 設定Cookie的函式
function setCookie(name, value, days) {
    const expirationDate = new Date();
    expirationDate.setDate(expirationDate.getDate() + days);
    const cookieValue = encodeURIComponent(value) + (days ? `; expires=${expirationDate.toUTCString()}` : '');
    document.cookie = `${name}=${cookieValue}; path=/`;
}

// 取出Cookie中指定名稱的值
function getCookie(name) {
    const value = `; ${document.cookie}`;
    const parts = value.split(`; ${name}=`);
    if (parts.length === 2) return parts.pop().split(';').shift();
}

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
            .then((d) => {
                // 將JWT寫入Cookie，這裡假設有效期為1小時
                setCookie('jwt', d, 24); // 1/24代表1小時，若要設定其他時間，可以調整這個數值

                // 假設您的JWT存儲在名為"jwt"的Cookie中
                const jwtToken = getCookie('jwt');
                console.log(jwtToken)

            })
    });
})();

