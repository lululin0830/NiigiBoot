// 查詢
const search = function () {

    const searchdata = JSON.stringify({
        searchcase: document.getElementById("Searchcase").value,
        searchway: document.getElementById("SearchSelect").value,
    })

    fetch('/UserController', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: searchdata
    }).then(r => r.json()).then(data => {
        const tbody = document.querySelector('#searchResult');
        tbody.innerHTML = "";
        for (let i = 0; i < data.length; i++) {

            const row = `<tr>
                <td>${data[i].userId}</td>
                <td>${data[i].userName}</td>
                <td>${data[i].userAcct}</td>
                <td>${data[i].hrAuthority}</td>
                <td>${data[i].financialAuthority}</td>
                <td>${data[i].marketingAuthority}</td>
                <td>${data[i].customerServiceAuthority}</td>
                <td><button type="button" id="accountSettings${i}" class="btn-primary" data-bs-toggle="modal"
                data-bs-target="#account_settings" data-user-id='${data[i].userId}' onclick="updateUser(this)">帳號設定</button></td>
                <td><button type="button" id="deleteUser${i}" class="btn-primary" data-bs-toggle="modal"
                        data-bs-target="#delete_user" data-user-id='${data[i].userId}' onclick="removeUser(this)">刪除成員</button></td>
            </tr>`;
            let rowData = "<tr>"

            tbody.innerHTML += row;

        }
    })
}

document.getElementById("search").addEventListener("click", search);
// 新增
(() => {
    const add = document.querySelector('#add');
    const msg = document.querySelector('#msg');
    const userName = document.querySelector('#userName');
    const userAcct = document.querySelector('#userAcct');
    const password = document.querySelector('#password');
    const inputs = document.querySelectorAll('input');
    add.addEventListener('click', () => {

        msg.textContent = '';
        fetch('/Register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                userName: userName.value,
                password: password.value,
                userAcct: userAcct.value,
            }),
        })
            .then(resp => resp.json())
            .then(body => {
                const { successful } = body;
                if (successful) {
                    for (let input of inputs) {
                        input.disabled = true;
                    }
                    add.disabled = true;
                    msg.className = 'info';
                    msg.textContent = '註冊成功';

                    location.reload();
                } else {
                    msg.className = 'error';
                    msg.textContent = '註冊失敗';
                }
            });
    });

})();

function removeUser(element) {
    // 取得按鈕元素

    const userId = element.dataset.userId;

    // 使用 Fetch API 來發送 POST 請求，進行會員刪除操作
    fetch('/Remove', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ userId: userId })
    })
        .then(resp => resp.json())
        .then(body => {

            if (body) {
                location.reload();
            }
        });
};

// 声明一个变量来存储当前用户的 userId
let currentUserUserId = '';

// 帳號設定按鈕的點擊事件處理程序
function updateUser(element) {

    currentUserUserId = element.dataset.userId; // 获取当前用户的 userId
}

document.addEventListener('DOMContentLoaded', () => {
    const saveButton = document.getElementById('saveButton');

    saveButton.addEventListener('click', () => {
        const hrAuthority = document.getElementById('hrAuthority');
        const financialAuthority = document.getElementById('financialAuthority');
        const marketingAuthority = document.getElementById('marketingAuthority');
        const customerServiceAuthority = document.getElementById('customerServiceAuthority');
        const changePassword = document.getElementById('changePassword');

        const data = {
            userId: currentUserUserId,
            hrAuthority: hrAuthority.checked ? '1' : '0',
            financialAuthority: financialAuthority.checked ? '1' : '0',
            marketingAuthority: marketingAuthority.checked ? '1' : '0',
            customerServiceAuthority: customerServiceAuthority.checked ? '1' : '0',
            password: changePassword.value,
        };

        return fetch('/user/update', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        })
            .then(response => {
                if (response.ok) {
                    alert("儲存成功");
                    location.reload(); // 重新載入頁面
                } else {
                }
            })
            .catch(error => {
                console.error('Error updating permissions and password:', error);
            });
    });
});

// 顯示畫面
const init = function () {
    fetch('/UserController', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }

    }).then(r => r.json()).then(data => {
        const tbody = document.querySelector('#searchResult');
        tbody.innerHTML = "";

        for (let i = 0; i < data.length; i++) {

            const row = `<tr>
                <td>${data[i].userId}</td>
                <td>${data[i].userName}</td>
                <td>${data[i].userAcct}</td>
                <td>${data[i].hrAuthority}</td>
                <td>${data[i].financialAuthority}</td>
                <td>${data[i].marketingAuthority}</td>
                <td>${data[i].customerServiceAuthority}</td>
                <td><button type="button" id="accountSettings${i}" class="btn-primary" data-bs-toggle="modal"
                        data-bs-target="#account_settings" data-user-id='${data[i].userId}' onclick="updateUser(this)">帳號設定</button></td>
                <td><button type="button" id="deleteUser${i}" class="btn-primary" data-bs-toggle="modal"
                        data-bs-target="#delete_user" data-user-id='${data[i].userId}' onclick="removeUser(this)">刪除成員</button></td>
            </tr>`;
            let rowData = "<tr>"

            tbody.innerHTML += row;

        }
    })
}
// window.addEventListener("load", init);
init();

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