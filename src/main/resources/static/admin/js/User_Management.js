const search = function () {
    console.log("來了");

    const searchdata = JSON.stringify({
        searchcase: document.getElementById("Searchcase").value,
        searchway: document.getElementById("SearchSelect").value,
    })

    fetch('http://localhost:8080/Niigi/Users', {
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
                <td>${element[0].userName}</td>
                <td>${element[0].userAcct}</td>
                <td>${element[0].password}</td>
                <td>${element[0].hrAuthority}</td>
                <td>${element[0].financialAuthority}</td>
                <td>${element[0].marketingAuthority}</td>
                <td>${element[0].customerServiceAuthority}</td>
                <td>
                    <button type="button" id="account_settings" class="btn-primary" data-bs-toggle="modal"
                        data-bs-target="#account_settings">帳號設定</button>
                </td>
                <td>
                    <button type="button" id="delete_user" class="btn-primary" data-bs-toggle="modal"
                        data-bs-target="#delete_user">刪除成員</button>
                </td>
            </tr>`;
            let rowData = "<tr>"

            tbody.innerHTML += row;

            console.log(data)
        })
    })
}

document.getElementById("search").addEventListener("click", search);


const init = function () {
    fetch('http://localhost:8080/Niigi/Users', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }

    }).then(r => r.json()).then(data => {
        const tbody = document.querySelector('searchResult');
        tbody.innerHTML = "";
        data.forEach(element => {

            const row = `<tr>
                <td>${element[0].userName}</td>
                <td>${element[0].userAcct}</td>
                <td>${element[0].password}</td>
                <td>${element[0].hrAuthority}</td>
                <td>${element[0].financialAuthority}</td>
                <td>${element[0].marketingAuthority}</td>
                <td>${element[0].customerServiceAuthority}</td>
                <td>
                    <button type="button" id="account_settings" class="btn-primary" data-bs-toggle="modal"
                        data-bs-target="#account_settings">帳號設定</button>
                </td>
                <td>
                    <button type="button" id="delete_user" class="btn-primary" data-bs-toggle="modal"
                        data-bs-target="#delete_user">刪除成員</button>
                </td>
            </tr>`;
            let rowData = "<tr>"

            tbody.innerHTML += row;

            console.log(data)
        });
    })
}
init();