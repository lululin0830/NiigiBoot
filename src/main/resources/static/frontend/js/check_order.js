document.write(`<script src="./vendors/jquery/jquery-3.7.0.min.js"></script>`)

//先依照付款狀態reduce成兩組 0(待付款的)丟去跑迴圈新增至第一頁
//第二組再reduce一次 丟去跑回圈新增至該去的分頁

const memberId = document.querySelector('.memberId').innerHTML

//尚未付款分頁
const mainorder1 = document.querySelector("#navs-top-home>ul.order-list")
//處理中分頁
const mainorder2 = document.querySelector("#navs-top-inprogress>ul.order-list")
//待收貨分頁
const orderList3 = document.querySelector("#navs-top-refund>ul.order-list");
//已完成分頁
const orderList4 = document.querySelector("#navs-top-complete>ul.order-list");
//已取消分頁
const orderList5 = document.querySelector("#navs-top-cancel>ul.order-list");

let EIF = null;
let bodyhtml = null;
const mainOrderBody = function () {

    const dateString = EIF[0][2];
    const date = new Date(dateString);
    const year = date.getFullYear(); // 獲得年份
    const month = date.getMonth() + 1; // 獲得月份（注意 JavaScript 中的月份是從 0 開始的，所以需要加 1）
    const day = date.getDate();

    let html =
        `<li class="order">
            <div class="navs-top">
                <div class="col-sm-2">
                    <h4 class="order-date">${year + "-" + month + "-" + day}</h4>
                </div>
                <div class="col-sm-7">
                    <h4 class="order-id">訂單編號：<span>${EIF[0][3]}</span></h4>
                    <h4 class="order-amount">訂購金額：<span>${EIF[0][4]}</span><span
                        style="font-size: 1.4rem"> (已折抵)</span>
                    </h4>
                </div>
                <div class="col-sm-3">
                    <div class="navs-top-btn">
                        <!-- Button trigger modal -->
                        <button type="button" class="btn btn-primary btn-XL"
                            data-bs-toggle="modal" data-bs-target="#checkoutModal">
                            去付款
                        </button>
                    </div>

                    <div class="navs-top-btn">
                        <!-- Button trigger modal -->
                        <button type="button" class="btn btn-primary btn-XL"
                            data-bs-toggle="modal" data-bs-target="#cancelOrderModal">
                            取消訂單
                        </button>
                    </div>
                </div>
                <hr>
            </div>`

    //可能重複結構(多個子訂單)
    for (i = 0; i < EIF.length; i++) {

        html +=
            `< div class="navs-top-content" >
            <ul class="sub-order-list">
                <li class="sub-order">
                    <div class="col-sm-2">
                        <div class="order_item_priview">
                            <img class="order_item_priview" src="./image/product.svg"
                                alt="">
                        </div>
                    </div>
                    <div class="col-sm-7 ps-3">
                        <h4 class="shop-name">店家名稱：<span>${EIF[i][5]}</span></h4>
                        <h4 class="order-id">訂單編號：<span>${EIF[i][6]}</span>
                        </h4>
                        <h4 class="order-amount">訂購金額：<span>${EIF[i][4]}</span></h4>
                    </div>
                    <div class="col-sm-3 position-relative ">

                        <!-- Button trigger modal -->
                        <div class="navs-top-content-btn">
                            <button type="button" class="btn btn-primary btn-XL"
                                data-bs-toggle="modal"
                                data-bs-target="#orderDetilModal">
                                查看訂單明細
                            </button>
                        </div>
                    </div>
                    <hr>
                </li>                
            </ul>
            </div >
        <hr>
    </li>`
    }


    bodyhtml = html;
}

const init = function () {
    console.log(mainorder1)
    fetch('http://localhost:8080/Niigi/MemberCheckOrder?memberId=' + memberId, {
        method: 'GET',
        headers: {
            'Content-type': 'application/json',
        }
    }).then(r => r.json()).then(data => {
        console.log(data)

        function groupByField(data, index) {
            return data.reduce((result, item) => {
                // 確保 array[index] 有效且為字串或數字型態
                console.log(item[3])
                const groupBy = item[index];
                if (groupBy !== undefined && (typeof groupBy === 'string' || typeof groupBy === 'number')) {
                    // 檢查 groupBy 是否已經有符合該 field 值的 arraylist
                    const grouplist = result.find((arr) => arr.length > 0 && arr[0][index] === item[index]);
                    if (grouplist) {
                        // 如果已存在，將 item 加入該 arraylist
                        grouplist.push(item);
                    } else {
                        // 如果不存在，創建新的 arraylist 並將 item 加入
                        result.push([item]);
                    }
                } else {
                    console.warn(`Invalid field value at index ${index} for item:`, item);
                }
                return result;
            }, []);
        }
        const groupedData = groupByField(data, 3);
        console.log(groupedData)

        groupedData.forEach(element => {

            EIF = element


            mainOrderBody();
            mainorder1.insertAdjacentHTML("beforeend", bodyhtml)

        });
    })
}
init();
