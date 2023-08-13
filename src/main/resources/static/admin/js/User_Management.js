// 查詢
const search = function () {
    console.log("來了");

    const searchdata = JSON.stringify({
        searchcase: document.getElementById("Searchcase").value,
        searchway: document.getElementById("SearchSelect").value,
    })

    fetch('http://localhost:8080/Niigi/UserController', {
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
                <td><button type="button" id="accountSettings" class="btn-primary" data-bs-toggle="modal"
                        data-bs-target="#account_settings" >帳號設定</button></td>
                <td><button type="button" id="deleteUser${i}" class="btn-primary" data-bs-toggle="modal"
                        data-bs-target="#delete_user" data-user-id='${data[i].userId}' onclick="removeUser(this)">刪除成員</button></td>
            </tr>`;
            let rowData = "<tr>"

            tbody.innerHTML += row;

        }
    })
}

console.log("讀到了")
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
        // const accLength = userName.value.length;
        // if (accLength < 5 || accLength > 50) {x
        // 	msg.textContent = '帳號長度須介於5~50字元';
        // 	return;
        // }

        // const pwdLength = password.value.length;
        // if (pwdLength < 6 || pwdLength > 12) {
        // 	msg.textContent = '密碼長度須介於6~12字元';
        // 	return;
        // }
        msg.textContent = '';
        fetch('http://localhost:8080/Niigi/Register', {
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
    console.log("Hi")

    const userId = element.dataset.userId;
    console.log(userId)

    // 使用 Fetch API 來發送 POST 請求，進行會員刪除操作
    fetch('http://localhost:8080/Niigi/Remove', {
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

function updateUser(element){
    // 取得按鈕元素
    console.log("Hi")
    
    const userId = element.dataset.userId;
    console.log(userId)
    
    const saveButton = document.getElementById('saveButton');
    
    saveButton.addEventListener('click', () => {
        const hrAuthority = document.getElementById('hrAuthority');
        const financialAuthority = document.getElementById('financialAuthority');
        const marketingAuthority = document.getElementById('marketingAuthority');
        const customerServiceAuthority = document.getElementById('customerServiceAuthority');
        const changePassword = document.getElementById('changePassword'); // 新的設定新密碼的輸入框
        
        const data = {
            hrAuthority: hrAuthority.checked ? '1' : '0',
            financialAuthority: financialAuthority.checked ? '1' : '0',
            marketingAuthority: marketingAuthority.checked ? '1' : '0',
            customerServiceAuthority: customerServiceAuthority.checked ? '1' : '0',
            changePassword: changePassword.value, // 取得新密碼輸入框的值
        };
        
        fetch('http://localhost:8080/Niigi/User/Update', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        })
        .then(response => response.json())
        .then(result => {
            // 根據後端返回的結果進行處理
            if (result.successful) {
                alert('權限及密碼更新成功');
            } else {
                alert('權限及密碼更新失敗');
            }
        })
        .catch(error => {
            console.error('Error updating permissions and password:', error);
        });
    });
}


// 顯示畫面
const init = function () {
    fetch('http://localhost:8080/Niigi/UserController', {
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

