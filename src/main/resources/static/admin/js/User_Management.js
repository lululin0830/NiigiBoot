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