const init = function () {
    fetch('http://localhost:8080/Niigi/SubOrder', {
        method: 'GET',
        headers: {
            'Content-type': 'application/json'
        }
    }).then(r => r.json).then(data => {
        data.forEach(element => {
            `<li class="sub-order row">
        <div class="col-sm-4 order-select">
            <input type="checkbox" class="select me-3">
            <span class="memberAccount">${data.memberAcct}</span>
        </div>
        <div class="col-sm-6">
            <span>訂單編號：<span class="sub-order-id">${data.subOrderId}</span></span>
        </div>
        <div class="col d-flex justify-content-end">
            <span class="order-date">${data.orderCreateTime}</span>
        </div>
        
        <ul class="order-detail">
            <li class="order-item row">
                <div class="col-sm-2">
                    <img src="../image/product.svg" alt="">
                </div>
                <div class=" col-sm-2">
                    <p class="product-name">商品名稱XXXX</p>
                </div>
                <div class="col-sm-2">
                    <p>10000001001</p>
                </div>
                <div class="col-sm-1">
                    <p>1</p>
                </div>
                <div class="col-sm-1">
                    <p>100,000</p>
                </div>
                <div class="col-sm-1">
                    <p>待處理</p>
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
            </li>
            <li class="order-item row">
                <div class="col-sm-2">
                    <img src="../image/product.svg" alt="">
                </div>
                <div class=" col-sm-2">
                    <p class="product-name">商品名稱XXXX</p>
                </div>
                <div class="col-sm-2">
                    <p>10000001001</p>
                </div>
                <div class="col-sm-1">
                    <p>1</p>
                </div>
                <div class="col-sm-1">
                    <p>100,000</p>
                </div>
                <div class="col-sm-1">
                    <p>待處理</p>
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
            </li>
        </ul>
    </li>`
        })
    })
}