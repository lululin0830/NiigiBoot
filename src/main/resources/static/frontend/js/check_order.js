document.write(`<script src="./vendors/jquery/jquery-3.7.0.min.js"></script>`)

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
    let html =
        `<li class="order">
            <div class="navs-top">
                <div class="col-sm-2">
                    <h4 class="order-date">${EIF[2]}</h4>
                </div>
                <div class="col-sm-7">
                    <h4 class="order-id">訂單編號：<span>${EIF[3]}</span></h4>
                    <h4 class="order-amount">訂購金額：<span>${EIF[4]}</span><span
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
            </div>
            </li>`
    //可能重複結構(多個子訂單)

    //         `< div class="navs-top-content" >
    //         <ul class="sub-order-list">
    //             <li class="sub-order">
    //                 <div class="col-sm-2">
    //                     <div class="order_item_priview">
    //                         <img class="order_item_priview" src="./image/product.svg"
    //                             alt="">
    //                     </div>
    //                 </div>
    //                 <div class="col-sm-7 ps-3">
    //                     <h4 class="shop-name">店家名稱：<span>XXXXXXXXXX</span></h4>
    //                     <h4 class="order-id">訂單編號：<span>20230630000000001-001</span>
    //                     </h4>
    //                     <h4 class="order-amount">訂購金額：<span>100,000,000</span></h4>
    //                 </div>
    //                 <div class="col-sm-3 position-relative ">

    //                     <!-- Button trigger modal -->
    //                     <div class="navs-top-content-btn">
    //                         <button type="button" class="btn btn-primary btn-XL"
    //                             data-bs-toggle="modal"
    //                             data-bs-target="#orderDetilModal">
    //                             查看訂單明細
    //                         </button>
    //                     </div>

    //                 </div>
    //                 <hr>
    //             </li>
    //             <li class="sub-order">
    //                 <div class="col-sm-2">
    //                     <div class="order_item_priview">
    //                         <img class="order_item_priview" src="./image/product.svg"
    //                             alt="">
    //                     </div>
    //                 </div>
    //                 <div class="col-sm-7 ps-3">
    //                     <h4 class="shop-name">店家名稱：<span>XXXXXXXXXX</span></h4>
    //                     <h4 class="order-id">訂單編號：<span>20230630000000001-001</span>
    //                     </h4>
    //                     <h4 class="order-amount">訂購金額：<span>100,000,000</span></h4>
    //                 </div>
    //                 <div class="col-sm-3 position-relative ">

    //                     <!-- Button trigger modal -->
    //                     <div class="navs-top-content-btn">
    //                         <button type="button" class="btn btn-primary btn-XL"
    //                             data-bs-toggle="modal"
    //                             data-bs-target="#orderDetilModal">
    //                             查看訂單明細
    //                         </button>
    //                     </div>

    //                 </div>
    //                 <hr>
    //             </li>
    //         </ul>
    //         </div >
    // <hr>

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
        data.forEach(element => {
            EIF = element
            mainOrderBody();
            mainorder1.insertAdjacentHTML("beforeend", bodyhtml)

        });
    })
}
init();
