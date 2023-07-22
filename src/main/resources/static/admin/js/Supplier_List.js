
const search = function () {

    console.log("進來了");

    const searchdata = JSON.stringify({
        searchcase: document.getElementById("Searchcase").value,
        searchway: document.getElementById("SearchSelect").value,
        StartDate: document.getElementById("StartDate").value,
        EndDate: document.getElementById("EndDate").value,
        DateSelect: document.getElementById("DateSelect").value
    })

    fetch('http://localhost:8080/Niigi/Supplier', {
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
                <td> ${element.shopName}</td>
                <td> ${element.supplierId}</td>
                <td> ${element.supplierMemberAcct}</td>
                <td> ${element.enableTime}</td>
                <td> ${element.businessId}</td>
                <td> ${element.supplierAddress}</td>
                <td> ${element.bankCode}</td>
                <td> ${element.bankAcct}</td>
                <td> ${element.ownerId}</td>
                <td> ${element.supplierBanStatus}</td>       
                <td> <input type="button" id="active" value="操作"></td>
                    </tr>`;
            let rowData = "<tr>"

            tbody.innerHTML += row;

            console.log(data)
        });

    })




}
console.log("讀到了")
document.getElementById("search").addEventListener("click", search);

const init = function () {
    fetch('http://localhost:8080/Niigi/Supplier', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }

    }).then(r => r.json()).then(data => {
        const tbody = document.querySelector('#searchResult');
        tbody.innerHTML = "";
        console.log(data)

        data.forEach(element => {

            let banStatus;
            switch (element.supplierBanStatus) {

                case '0':
                    banStatus = '正常';
                    break;
                case '3':
                    banStatus = '停權3天';
                    break;
                case '7':
                    banStatus = '停權7天';
                    break;
                case '30':
                    banStatus = '停權30天';
                    break;
                default:
                    banStatus = '永久停權';
            }

            const row = `<tr>
                <td> ${element.shopName}</td>
                <td> ${element.supplierId}</td>
                <td> ${element.supplierMemberAcct}</td>
                <td> ${element.enableTime}</td>
                <td> ${element.businessId}</td>
                <td> ${element.supplierAddress}</td>
                <td> ${element.bankCode}</td>
                <td> ${element.bankAcct}</td>
                <td> ${element.ownerId}</td>
                <td> ${banStatus}</td>                
                <td> <input type="button" id="active" value="操作"></td>
            </tr>`;
            let rowData = "<tr>"

            tbody.innerHTML += row;

            console.log(data)
        });

    })
}
// window.addEventListener("load", init);
init();
