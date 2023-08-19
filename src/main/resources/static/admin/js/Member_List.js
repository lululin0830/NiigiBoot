const search = function () {

    const searchdata = JSON.stringify({
        searchcase: document.getElementById("Searchcase").value,
        searchway: document.getElementById("SearchSelect").value,
        StartDate: document.getElementById("StartDate").value,
        EndDate: document.getElementById("EndDate").value,
        DateSelect: document.getElementById("DateSelect").value
    })

    fetch('/MemberSelect', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: searchdata
    }).then(r => r.json()).then(data => {
        const tbody = document.querySelector('#searchResult');
        tbody.innerHTML = "";
        for (let i = 0; i < data.length; i++) {
            let banStatus;
            if (data[i].banStatus === '0') {
                banStatus = '正常';
            } else if (data[i].banStatus === '3') {
                banStatus = '停權3天';
            } else if (data[i].banStatus === '7') {
                banStatus = '停權7天';
            } else if (data[i].banStatus === '30') {
                banStatus = '停權30天';
            } else {
                banStatus = '永久停權';
            }

            const row = `<tr>
                <td>${data[i].memberAcct}</td>
                <td>${data[i].memberId}</td>
                <td>${data[i].regTime}</td>
                <td>${data[i].name}</td>
                <td>${data[i].phone}</td>
                <td>${banStatus}</td>
            </tr>`;

            tbody.innerHTML += row;
        }
    })

}
document.getElementById("search").addEventListener("click", search);

// 顯示畫面
const init = function () {
    fetch('/MemberSelect', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }
    }).then(r => r.json()).then(data => {
        const tbody = document.querySelector('#searchResult');
        tbody.innerHTML = "";

        for (let i = 0; i < data.length; i++) {
            let banStatus;
            if (data[i].banStatus === '0') {
                banStatus = '正常';
            } else if (data[i].banStatus === '3') {
                banStatus = '停權3天';
            } else if (data[i].banStatus === '7') {
                banStatus = '停權7天';
            } else if (data[i].banStatus === '30') {
                banStatus = '停權30天';
            } else {
                banStatus = '永久停權';
            }

            const row = `<tr>
                <td>${data[i].memberAcct}</td>
                <td>${data[i].memberId}</td>
                <td>${data[i].regTime}</td>
                <td>${data[i].name}</td>
                <td>${data[i].phone}</td>
                <td>${banStatus}</td>
            </tr>`;

            tbody.innerHTML += row;
        }
    })
}

init();
