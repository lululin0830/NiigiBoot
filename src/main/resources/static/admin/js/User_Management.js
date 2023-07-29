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
        data.forEach(element => {

            const row = `<tr>
                <td>${element.userName}</td>
                <td>${element.userAcct}</td>
                <td>${element.password}</td>
                <td>${element.hrAuthority}</td>
                <td>${element.financialAuthority}</td>
                <td>${element.marketingAuthority}</td>
                <td>${element.customerServiceAuthority}</td>
                <td>
                    <button type="button" id="accountSettings" class="btn-primary" data-bs-toggle="modal"
                        data-bs-target="#account_settings">帳號設定</button>
                </td>
                <td>
                    <button type="button" id="deleteUser" class="btn-primary" data-bs-toggle="modal"
                        data-bs-target="#delete_user">刪除成員</button>
                </td>
            </tr>`;
            let rowData = "<tr>"

            tbody.innerHTML += row;

            console.log(data)
        })
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
		// if (accLength < 5 || accLength > 50) {
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
				} else {
					msg.className = 'error';
					msg.textContent = '註冊失敗';
				}
			});
	});

})();
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
        console.log(data)

        data.forEach(element => {

            const row = `<tr>
                <td>${element.userName}</td>
                <td>${element.userAcct}</td>
                <td>${element.password}</td>
                <td>${element.hrAuthority}</td>
                <td>${element.financialAuthority}</td>
                <td>${element.marketingAuthority}</td>
                <td>${element.customerServiceAuthority}</td>
                <td><button type="button" id="accountSettings" class="btn-primary" data-bs-toggle="modal"
                        data-bs-target="#account_settings">帳號設定</button></td>
                <td><button type="button" id="deleteUser" class="btn-primary" data-bs-toggle="modal"
                        data-bs-target="#delete_user">刪除成員</button></td>
            </tr>`;
            let rowData = "<tr>"

            tbody.innerHTML += row;



            console.log(data)
        });
    })
}
// window.addEventListener("load", init);
init();