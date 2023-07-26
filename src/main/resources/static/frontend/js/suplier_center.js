const searchdata = JSON.stringify({
    supplierId: document.getElementById("supplierId").innerText,
    searchcase: document.getElementById("selectKey-home").value,
    searchway: document.getElementById("selectCriteria-home").value,
    StartDate: document.getElementById("startDate").value,
    EndDate: document.getElementById("EndDate").value
})




const init = function () {

    const jsonData = JSON.stringify(searchdata);

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


    console.log(searchdata)
    fetch('http://localhost:8080/Niigi/SupplierSubOrder', {
        method: 'POST',
        headers: {
            'Content-type': 'application/json'
        },
        body: searchdata
    }).then(r => r.json()).then(data => {
        console.log(data)
        data.forEach(element => {

            const dateString = element[0].orderCreateTime;
            const date = new Date(dateString);
            const year = date.getFullYear(); // 獲得年份
            const month = date.getMonth() + 1; // 獲得月份（注意 JavaScript 中的月份是從 0 開始的，所以需要加 1）
            const day = date.getDate(); // 獲得日期

            //待處理分頁
            if (element[0].subOrderStatus == 0) {
                let html =
                    `<li class="sub-order row" data-id="${element[0].subOrderId}">
                <div class="col-sm-4 order-select">
                    <input type="checkbox" class="select me-3">
                    <span class="memberAccount">${element[1].memberAcct}</span>
                </div>
                <div class="col-sm-6">
                    <span>訂單編號：<span class="sub-order-id">${element[0].subOrderId}</span></span>
                </div>
                <div class="col d-flex justify-content-end">
                    <span class="order-date">${year + "-" + month + "-" + day}</span>
                </div>

                <ul class="order-detail">`

                // orderList.insertAdjacentHTML("beforeend", html);

                for (i = 2; i < element.length; i += 2) {

                    console.log(element[i + 1].productName)

                    let orderStatus;
                    switch (element[i - 2].subOrderStatus) {

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

                    html +=
                        `<li class="order-item row">
                            <div class="col-sm-2">
                                <img src="../image/product.svg" alt="">
                            </div>
                            <div class=" col-sm-2">
                                <p class="product-name">${element[i + 1].productName}</p>
                            </div>
                            <div class="col-sm-2">
                                <p>${element[i].productSpecId}</p>
                            </div>
                            <div class="col-sm-1">
                                <p>1</p>
                            </div>
                            <div class="col-sm-1">
                                <p>${element[i].productPrice}</p>
                            </div>
                            <div class="col-sm-1">
                                <p>${orderStatus}</p>
                            </div>
                            <div class="col-sm-1">
                                <p>宅配</p>
                            </div>
                            <div class="col-sm-2">
                            <button type="button" class="btn btn-primary btn-M"
                                data-bs-toggle="modal" data-bs-target="#cancelOrderModal">
                                取消訂單
                            </button>
                            </div>
                            <hr>
                        </li>`;

                    // orderList.insertAdjacentHTML("beforeend", html);
                };
                orderList.insertAdjacentHTML("beforeend", html + `</ul></li>`);
            }
            //配送中分頁
            if (element[0].subOrderStatus == 1 || element[0].subOrderStatus == 2) {
                let html =
                    `<li class="sub-order row" data-id="${element[0].subOrderId}">
                <div class="col-sm-4 order-select">
                    <input type="checkbox" class="select me-3">
                    <span class="memberAccount">${element[1].memberAcct}</span>
                </div>
                <div class="col-sm-6">
                    <span>訂單編號：<span class="sub-order-id">${element[0].subOrderId}</span></span>
                </div>
                <div class="col d-flex justify-content-end">
                    <span class="order-date">${year + "-" + month + "-" + day}</span>
                </div>
        
                <ul class="order-detail">`

                // orderList.insertAdjacentHTML("beforeend", html);

                for (i = 2; i < element.length; i += 2) {

                    console.log(element[i + 1].productName)
                    console.log(element)
                    let orderStatus;
                    switch (element[i - 2].subOrderStatus) {

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

                    html +=
                        `<li class="order-item row">
                            <div class="col-sm-2">
                                <img src="../image/product.svg" alt="">
                            </div>
                            <div class=" col-sm-2">
                                <p class="product-name">${element[i + 1].productName}</p>
                            </div>
                            <div class="col-sm-2">
                                <p>${element[i].productSpecId}</p>
                            </div>
                            <div class="col-sm-1">
                                <p>1</p>
                            </div>
                            <div class="col-sm-1">
                                <p>${element[i].productPrice}</p>
                            </div>
                            <div class="col-sm-1">
                                <p>${orderStatus}</p>
                            </div>
                            <div class="col-sm-1">
                                <p>宅配</p>
                            </div>
                            <div class="col-sm-2">
                            <button type="button" class="btn btn-primary btn-M"
                                data-bs-toggle="modal" data-bs-target="#cancelOrderModal">
                                取消訂單
                            </button>
                            </div>
                            <hr>
                        </li>`;

                    // orderList.insertAdjacentHTML("beforeend", html);
                };
                orderList2.insertAdjacentHTML("beforeend", html + `</ul></li>`);
            }
            //待退貨/款
            if (element[0].subOrderStatus == 3) {
                let html =
                    `<li class="sub-order row" data-id="${element[0].subOrderId}">
                <div class="col-sm-4 order-select">
                    <input type="checkbox" class="select me-3">
                    <span class="memberAccount">${element[1].memberAcct}</span>
                </div>
                <div class="col-sm-6">
                    <span>訂單編號：<span class="sub-order-id">${element[0].subOrderId}</span></span>
                </div>
                <div class="col d-flex justify-content-end">
                    <span class="order-date">${year + "-" + month + "-" + day}</span>
                </div>
        
                <ul class="order-detail">`

                // orderList.insertAdjacentHTML("beforeend", html);

                for (i = 2; i < element.length; i += 2) {

                    console.log(element[i + 1].productName)
                    console.log(element)
                    let orderStatus;
                    switch (element[i - 2].subOrderStatus) {

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

                    html +=
                        `<li class="order-item row">
                            <div class="col-sm-2">
                                <img src="../image/product.svg" alt="">
                            </div>
                            <div class=" col-sm-2">
                                <p class="product-name">${element[i + 1].productName}</p>
                            </div>
                            <div class="col-sm-2">
                                <p>${element[i].productSpecId}</p>
                            </div>
                            <div class="col-sm-1">
                                <p>1</p>
                            </div>
                            <div class="col-sm-1">
                                <p>${element[i].productPrice}</p>
                            </div>
                            <div class="col-sm-1">
                                <p>${orderStatus}</p>
                            </div>
                            <div class="col-sm-1">
                                <p>宅配</p>
                            </div>
                            <div class="col-sm-2">
                            <button type="button" class="btn btn-primary btn-M"
                                data-bs-toggle="modal" data-bs-target="#cancelOrderModal">
                                取消訂單
                            </button>
                            </div>
                            <hr>
                        </li>`;

                    // orderList.insertAdjacentHTML("beforeend", html);
                };
                orderList3.insertAdjacentHTML("beforeend", html + `</ul></li>`);
            }
            //已完成分頁
            if (element[0].subOrderStatus == 4) {
                let html =
                    `<li class="sub-order row" data-id="${element[0].subOrderId}">
                <div class="col-sm-4 order-select">
                    <input type="checkbox" class="select me-3">
                    <span class="memberAccount">${element[1].memberAcct}</span>
                </div>
                <div class="col-sm-6">
                    <span>訂單編號：<span class="sub-order-id">${element[0].subOrderId}</span></span>
                </div>
                <div class="col d-flex justify-content-end">
                    <span class="order-date">${year + "-" + month + "-" + day}</span>
                </div>
        
                <ul class="order-detail">`

                // orderList.insertAdjacentHTML("beforeend", html);

                for (i = 2; i < element.length; i += 2) {

                    console.log(element[i + 1].productName)
                    console.log(element)
                    let orderStatus;
                    switch (element[i - 2].subOrderStatus) {

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

                    html +=
                        `<li class="order-item row">
                            <div class="col-sm-2">
                                <img src="../image/product.svg" alt="">
                            </div>
                            <div class=" col-sm-2">
                                <p class="product-name">${element[i + 1].productName}</p>
                            </div>
                            <div class="col-sm-2">
                                <p>${element[i].productSpecId}</p>
                            </div>
                            <div class="col-sm-1">
                                <p>1</p>
                            </div>
                            <div class="col-sm-1">
                                <p>${element[i].productPrice}</p>
                            </div>
                            <div class="col-sm-1">
                                <p>${orderStatus}</p>
                            </div>
                            <div class="col-sm-1">
                                <p>宅配</p>
                            </div>
                            <div class="col-sm-2">
                            <button type="button" class="btn btn-primary btn-M"
                                data-bs-toggle="modal" data-bs-target="#cancelOrderModal">
                                取消訂單
                            </button>
                            </div>
                            <hr>
                        </li>`;

                    // orderList.insertAdjacentHTML("beforeend", html);
                };
                orderList4.insertAdjacentHTML("beforeend", html + `</ul></li>`);
            }
            //已取消分頁
            if (element[0].subOrderStatus == 5) {
                let html =
                    `<li class="sub-order row" data-id="${element[0].subOrderId}">
                <div class="col-sm-4 order-select">
                    <input type="checkbox" class="select me-3">
                    <span class="memberAccount">${element[1].memberAcct}</span>
                </div>
                <div class="col-sm-6">
                    <span>訂單編號：<span class="sub-order-id">${element[0].subOrderId}</span></span>
                </div>
                <div class="col d-flex justify-content-end">
                    <span class="order-date">${year + "-" + month + "-" + day}</span>
                </div>
        
                <ul class="order-detail">`

                // orderList.insertAdjacentHTML("beforeend", html);

                for (i = 2; i < element.length; i += 2) {

                    console.log(element[i + 1].productName)
                    console.log(element)
                    let orderStatus;
                    switch (element[i - 2].subOrderStatus) {

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

                    html +=
                        `<li class="order-item row">
                            <div class="col-sm-2">
                                <img src="../image/product.svg" alt="">
                            </div>
                            <div class=" col-sm-2">
                                <p class="product-name">${element[i + 1].productName}</p>
                            </div>
                            <div class="col-sm-2">
                                <p>${element[i].productSpecId}</p>
                            </div>
                            <div class="col-sm-1">
                                <p>1</p>
                            </div>
                            <div class="col-sm-1">
                                <p>${element[i].productPrice}</p>
                            </div>
                            <div class="col-sm-1">
                                <p>${orderStatus}</p>
                            </div>
                            <div class="col-sm-1">
                                <p>宅配</p>
                            </div>
                            <div class="col-sm-2">
                            <button type="button" class="btn btn-primary btn-M"
                                data-bs-toggle="modal" data-bs-target="#cancelOrderModal">
                                取消訂單
                            </button>
                            </div>
                            <hr>
                        </li>`;

                    // orderList.insertAdjacentHTML("beforeend", html);
                };
                orderList5.insertAdjacentHTML("beforeend", html + `</ul></li>`);
            }
        });


    })
};
init();