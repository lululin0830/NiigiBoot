document.write(`<script src="./vendors/jquery/jquery-3.7.0.min.js"></script>`)

let searchdata = null;

// {
//     supplierId: supplierId,
//     searchcase: '',
//     searchway: document.getElementById("selectCriteria-home").value,
//     StartDate: document.getElementById("startDate").value,
//     EndDate: document.getElementById("EndDate").value
// }

//待處理分頁
const orderList = document.querySelector("#navs-top-home>ul.order-list");
//配送中分頁
const orderList2 = document.querySelector("#navs-top-inprogress>ul.order-list");
//待退貨/款分頁
const orderList3 = document.querySelector("#navs-top-refund>ul.order-list");
//已完成分頁
const orderList4 = document.querySelector("#navs-top-complete>ul.order-list");
//已取消分頁
const orderList5 = document.querySelector("#navs-top-cancel>ul.order-list");

function createImageURL(byteArray) {
    const blob = new Blob([new Uint8Array(byteArray)], { type: 'image/jpeg' });
    return URL.createObjectURL(blob);
}

let EIF = null;
let BodyHtml = null;
const SubOrderBody = function () {

    const dateString = EIF[0].orderCreateTime;
    const date = new Date(dateString);
    const year = date.getFullYear(); // 獲得年份
    const month = date.getMonth() + 1; // 獲得月份（注意 JavaScript 中的月份是從 0 開始的，所以需要加 1）
    const day = date.getDate();

    let html =
        `<li class="sub-order row" data-id="${EIF[0].subOrderId}">
                <div class="col-sm-4 order-select">
                    <input type="checkbox" class="select me-3">
                    <span class="memberAccount">${EIF[1].memberAcct}</span>
                </div>
                <div class="col-sm-6">
                    <span>訂單編號：<span class="sub-order-id">${EIF[0].subOrderId}</span></span>
                </div>
                <div class="col d-flex justify-content-end">
                    <span class="order-date">${year + "-" + month + "-" + day}</span>
                </div>

                <ul class="order-detail">`

    // orderList.insertAdjacentHTML("beforeend", html);

    for (i = 2; i < EIF.length; i += 2) {

        console.log(EIF)

        let orderStatus;
        switch (EIF[i - 2].subOrderStatus) {

            case '0':
                orderStatus = '待處理';
                break;
            case '1':
                orderStatus = '配送中';
                break;
            case '2':
                orderStatus = '已送達';
                break;
            case '3':
                orderStatus = '已完成';
                break;
            case '4':
                orderStatus = '已退貨/退款';
                break;
            case '5':
                orderStatus = '已取消';
                break;
        }
        const imageElement = createImageURL(EIF[3].picture1)
//        console.log("picture1", EIF[3].picture1)
        html +=
            `<li class="order-item row">
                            <div class="col-sm-2">
                                <img src="${imageElement}" alt="">
                            </div>
                            <div class=" col-sm-2">
                                <p class="product-name">${EIF[i + 1].productName}</p>
                            </div>
                            <div class="col-sm-2">
                                <p>${EIF[i].productSpecId}</p>
                            </div>
                            <div class="col-sm-1">
                                <p>1</p>
                            </div>
                            <div class="col-sm-1">
                                <p>${EIF[i].productPrice}</p>
                            </div>
                            <div class="col-sm-1">
                                <p>${orderStatus}</p>
                            </div>
                            <div class="col-sm-1">
                                <p>宅配</p>
                            </div>
                            <div class="col-sm-2">
                            <button type="button" class="btn btn-primary btn-M cancelSubOrder"
                                data-bs-toggle="modal" data-bs-target="#cancelOrderModal">
                                取消訂單
                            </button>
                            </div>
                            <hr>
                        </li>`;

        BodyHtml = html;
    };
}
const cleanBody = function (orderlist) {
    let clean = orderlist.querySelectorAll('.sub-order')
    clean.forEach(function (element) {
        orderlist.removeChild(element);
    });
}
const resetSearch = function () {
    $(this).closest("div.order-selector").find("input").each(function (e) {
        $(this).val("");
    })
}

let tab1Count = 0;
let tab2Count = 0;
let tab3Count = 0;
let tab4Count = 0;
let tab5Count = 0;


function init() {
    searchdata = {
        supplierId: supplierId,
        searchcase: '',
        searchway: document.getElementById("selectCriteria-home").value,
        StartDate: document.getElementById("startDate").value,
        EndDate: document.getElementById("EndDate").value
    }

    const jsonData = JSON.stringify(searchdata);


    console.log("searchdata", searchdata)
    fetch('../SupplierSubOrder/init', {
        method: 'POST',
        headers: {
            'Content-type': 'application/json',
            'Authorization': `Bearer ${jwtToken}`
        },
        body: JSON.stringify(searchdata)
    }).then(r => r.json()).then(data => {
        console.log(data)

        data.forEach(element => {

            EIF = element;
            //待處理分頁
            if (element[0].subOrderStatus == 0) {
                SubOrderBody();
                orderList.insertAdjacentHTML("beforeend", BodyHtml + `</ul></li>`);
                tab1Count++;
            }
            //配送中分頁
            if (element[0].subOrderStatus == 1 || element[0].subOrderStatus == 2) {
                SubOrderBody();
                orderList2.insertAdjacentHTML("beforeend", BodyHtml + `</ul></li>`);
                tab2Count++;
            }
            //待退貨/款
            if (element[0].subOrderStatus == 3) {
                SubOrderBody();
                orderList3.insertAdjacentHTML("beforeend", BodyHtml + `</ul></li>`);
                tab3Count++;
            }
            //已完成分頁
            if (element[0].subOrderStatus == 4) {
                SubOrderBody();
                orderList4.insertAdjacentHTML("beforeend", BodyHtml + `</ul></li>`);
                tab4Count++;
            }
            //已取消分頁
            if (element[0].subOrderStatus == 5) {
                SubOrderBody();
                orderList5.insertAdjacentHTML("beforeend", BodyHtml + `</ul></li>`);
                tab5Count++;
            }
        });

        document.querySelector("button[aria-controls='navs-top-home']>span").innerText = tab1Count;
        document.querySelector("button[aria-controls='navs-top-inprogress']>span").innerText = tab2Count;
        document.querySelector("button[aria-controls='navs-top-refund']>span").innerText = tab3Count;
        document.querySelector("button[aria-controls='navs-top-complete']>span").innerText = tab4Count;
        document.querySelector("button[aria-controls='navs-top-cancel']>span").innerText = tab5Count;
        document.querySelectorAll("button.cancelSubOrder").forEach(function (e) {
            e.addEventListener("click", cancelSubOrder);
        })

    })
};

// window.addEventListener("DOMContentLoaded", init);



const search = function () {
    //取出搜尋框內的文字
    let searchcase1 = $(this).closest("div").find("#selectKey").val();
    //將它塞入傳入參數
    searchdata.searchcase = searchcase1;

    let searchwaycurrent = $(this).closest("div").find(".form-select").val();
    searchdata.searchway = searchwaycurrent;

    fetch('../SupplierGetSubOrderBySearch', {
        method: 'POST',
        headers: {
            'Content-type': 'application/json',
            'Authorization': `Bearer ${jwtToken}`
        },
        body: JSON.stringify(searchdata)
    }).then(r => r.json()).then(data => {

        data.forEach(element => {
            console.log(searchdata)
            EIF = element;
            //待處理分頁
            if (element[0].subOrderStatus == 0) {
                cleanBody(orderList);
                SubOrderBody();
                orderList.insertAdjacentHTML("beforeend", BodyHtml + `</ul></li>`);
            }
            //配送中分頁
            if (element[0].subOrderStatus == 1 || element[0].subOrderStatus == 2) {
                cleanBody(orderList2);
                SubOrderBody();
                orderList2.insertAdjacentHTML("beforeend", BodyHtml + `</ul></li>`);
            }
            //待退貨/款
            if (element[0].subOrderStatus == 3) {
                cleanBody(orderList3);
                SubOrderBody();
                orderList3.insertAdjacentHTML("beforeend", BodyHtml + `</ul></li>`);
            }
            //已完成分頁
            if (element[0].subOrderStatus == 4) {
                cleanBody(orderList4);
                SubOrderBody();
                orderList4.insertAdjacentHTML("beforeend", BodyHtml + `</ul></li>`);
            }
            //已取消分頁
            if (element[0].subOrderStatus == 5) {
                cleanBody(orderList5);
                SubOrderBody();
                orderList5.insertAdjacentHTML("beforeend", BodyHtml + `</ul></li>`);
            }
        });


    })
}

document.querySelectorAll(".search").forEach(function (e) {
    e.addEventListener("click", search);
})

document.querySelectorAll(".clearSearch").forEach(function (e) {
    e.addEventListener("click", resetSearch);
})



const cancelSubOrder = function () {

    const subOrderId = $(this).closest('li.sub-order').find('span.sub-order-id').text()
    console.log("subOrderId", subOrderId)

    async function updateSubOrder() {
        if (document.querySelector("button.confirmCancel") !== null) {
            await fetch('../SupplierSubOrder/cnacelSubOrder', {
                method: 'POST',
                headers: {
                    'Content-type': 'application/json',
                    'Authorization': `Bearer ${jwtToken}`
                },
                body: subOrderId
            });
            document.querySelector("button.confirmCancel").classList.remove("confirmCancel");
            document.querySelector("#cancelOrderModal button.btn-close").click();
            orderList.innerHTML = ""
            orderList2.innerHTML = ""
            orderList3.innerHTML = ""
            orderList4.innerHTML = ""
            orderList5.innerHTML = ""
            tab1Count = 0
            tab2Count = 0
            tab3Count = 0
            tab4Count = 0
            tab5Count = 0
            BodyHtml = null;
            init();
        }
    }

    document.querySelector("button.submitCancel").addEventListener("click", function () {
        this.classList.add("confirmCancel");
        updateSubOrder();
    })

}





document.querySelectorAll("button.cancelSubOrder").forEach(function (e) {
    e.addEventListener("click", cancelSubOrder);
})


document.addEventListener("coreDone", init)

