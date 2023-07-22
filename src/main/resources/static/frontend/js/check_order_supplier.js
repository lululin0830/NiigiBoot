
const init = function () {

    const orderData = document.getElementById("suborderlist"); //const = 常數型別
    const api = "http://localhost:8080/Niigi/SubOrder?getsubOrderId=t3826501846251486-001"

    document.getElementById("suborderlist").innerHTML = "";


    var re = fetch(api).then(function (res) {

        return res.json()

    }).then(function (data) {
        console.log(data)

        document.getElementById("orderid").innerHTML = data.orderCreateTime;

        // let list_html = ""

        // list_html += `<li>`;
        // list_html += `<div class="orderhead">`;
        // list_html += `<table class="orderhead">`;
        // list_html += `<th class="checkbox"><input type="checkbox"></th>`;
        // list_html += `<th class="memberid">`;
        // list_html += `會員帳號`;
        // list_html += `</th>`;
        // list_html += `<th class="orderid">`;
        // list_html += `訂單編號：` + res.subOrderId
        // list_html += `</th>`;
        // list_html += `<th class="date">`
        // list_html += `訂單創立日期:` + res.orderCreateTime
        // list_html += `</th>`
        // list_html += `</table>`
        // list_html += `</div>`
        // list_html += `<div class="table-responsive text-nowrap">`
        // list_html += `<table class="table">`
        // list_html += `<thead>`
        // list_html += `<tr class="text-nowrap">`
        // list_html += `</tr>`
        // list_html += `</thead>`
        // list_html += `<tbody>`
        // list_html += `<tr>`
        // list_html += `<td>`
        // list_html += `<img src="/image/photo_2023-03-22_18-13-48.jpg" width="150" height="150" alt="">`
        // list_html += `</td>`
        // list_html += `<td width="140" hight="150">商品名稱</td>`
        // list_html += `<td width="140">規格編號</td>`
        // list_html += `<td width="70px">12</td>`
        // list_html += `<td width="70px">1000</td>`
        // list_html += `<td width="70px">待出貨</td>`
        // list_html += `<td width="90px">宅配</td>`
        // list_html += `<td width="100px"><button`
        // list_html += `class="btn_SCM-Center">取消訂單</button></td>`
        // list_html += `</tr>`
        // list_html += `</tbody>`
        // list_html += `</table>`
        // list_html += `</div>`
        // list_html += `</li>`

        // // console.log(list_html)
        // return list_html;
    })


}
window.addEventListener('load', init)

// $(button.btn_SCM - Center).addEventListener("click", init);