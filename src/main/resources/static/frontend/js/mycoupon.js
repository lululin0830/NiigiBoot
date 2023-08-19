
const search = function () {

    console.log("進來了");

    const searchdata = JSON.stringify({
        MemberId: document.getElementById("member_id").value,
        searchcase: document.getElementById("Searchcase").value,
        StartDate: document.getElementById("StartDate").value,
        EndDate: document.getElementById("EndDate").value,
    })

    fetch('http://localhost:8080/Niigi/Event', {
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
                <td> ${element.discount_rate || element.discount_amount}</td>
                <td> ${element.eventStart}</td>
                <td> ${element.eventEnd}</td>
                <td> ${element.couponCode}</td>
                <td> 
                    ${element.eventName}<br>
                    商用活動類型:${element.eventType}<br>
                    適用商品:<input type="button" id="active" value="查看適用商品">
                </td>
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
    const searchdata = JSON.stringify({
        MemberId: document.getElementById("member_id").value,
    })
    fetch('http://localhost:8080/Niigi/Event', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
        body: searchdata
    }).then(r => r.json()).then(data => {
        const tbody = document.querySelector('#searchResult');
        tbody.innerHTML = "";
        console.log(data)

        data.forEach(element => {

            const row = `<tr>
                <td> ${element.discount_rate || element.discount_amount}</td>
                <td> ${element.eventStart}</td>
                <td> ${element.eventEnd}</td>
                <td> ${element.couponCode}</td>
                <td> 
                    ${element.eventName}<br>
                    商用活動類型:${element.eventType}<br>
                    適用商品:<input type="button" id="active" value="查看適用商品">
                </td>                
                    </tr>`;
            let rowData = "<tr>"

            tbody.innerHTML += row;

            console.log(data)
        });

    })
}
// window.addEventListener("load", init);
init();
