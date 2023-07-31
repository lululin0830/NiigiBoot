
// 數量加總
function increaseQuantity() {
    var quantityInput = document.getElementById('quantity');
    quantityInput.value = parseInt(quantityInput.value) + 1;
}

function decreaseQuantity() {
    var quantityInput = document.getElementById('quantity');
    var currentValue = parseInt(quantityInput.value);
    if (currentValue > 1) {
        quantityInput.value = currentValue - 1;
    }
}


//大小圖更換
//javascipt寫法
/*
function change_img(e) {
    var newSrc = e.target.getAttribute("src");
    document.querySelector("#show_L > img").setAttribute("src", newSrc);
}
function init() {
    document.querySelectorAll(".all_img > img").forEach(function(element) {
        element.addEventListener("click", change_img);
    });
}
window.addEventListener('load', init);
*/

//大小圖更換
//ajax寫法
$(document).ready(function () {
    $(".small_img").click(function () {
        var newSrc = $(this).attr("src");
        $("#show_L img").attr("src", newSrc);
    });
});


//關注鈕
$(".join_store").click(function () {
    if ($(this).text() == "加入關注") {
        $(this).text("關注中");
    } else {
        $(this).text("加入關注");
    }
})

const productId = 10000001;
const productInfo = document.querySelector("#productInfo")

const init = function () {

    fetch("http://localhost:8080/Niigi/product/" + productId)
        .then(r => r.json())
        .then(data => {
            console.log(data);

            let html = `
            <h2 id="productName">${data[0].product.productName}</h2>
            <p class="card-text star one">
                <div class="product-star">
                    <span class="star ${data[0].product.avgRating >= 2 ? "-on" : ""}" data-star="2"><img src="./image/Star_zero.svg" alt=""></span>
                    <span class="star ${data[0].product.avgRating >= 2 ? "-on" : ""}" data-star="2"><img src="./image/Star_zero.svg" alt=""></span>
                    <span class="star ${data[0].product.avgRating >= 3 ? "-on" : ""}" data-star="3"><img src="./image/Star_zero.svg" alt=""></span>
                    <span class="star ${data[0].product.avgRating >= 4 ? "-on" : ""}" data-star="4"><img src="./image/Star_zero.svg" alt=""></span>
                    <span class="star ${data[0].product.avgRating >= 5 ? "-on" : ""}" data-star="5"><img src="./image/Star_zero.svg" alt=""></span>
                    <span class="star_avg">${data[0].product.avgRating}</span>
                    <span class="star_totoal">(187)</span>
                </div> 
            </p>
            <p>
                <span class="priceone">NT$${data[0].product.productPrice}</span>
                <span class="pricetwo">NT$${data[0].product.productPrice}</span>
            </p>
            <p>
            <div class="btn-group">
                <button class="btn btn-secondary dropdown-toggle" type="button" id="dropdownMenuButton"
                    data-bs-toggle="dropdown" aria-expanded="false">
                    請選擇商品規格
                </button>
                <ul class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                    <li><a class="dropdown-item" href="#">Menu item</a></li>
                    <li><a class="dropdown-item" href="#">Menu item</a></li>
                    <li><a class="dropdown-item" href="#">Menu item</a></li>
                </ul>
            </div>
            <span class="stock" style="font-weight:bold;padding: 0 0 0 10px;">庫存1,000</span>
            </p>
            <div style="display: flex; align-items: center;padding: 10px 0 10px 0;">
                <span style="font-size: 16px;">數量</span>
                <button type="button" class="col-sm-1 plus" onclick="increaseQuantity()"><img
                        src="./image/square-plus-regular.svg"></button>
                <input type="number" id="quantity" name="quantity" class="custom-input" value="1" />
                <button type="button" class="col-sm-1 minus" onclick="decreaseQuantity()"><img
                        src="./image/square-minus-regular.svg"></button>
                <button class="join_car_btn btn btn-primary">加入購物車</button>
                <button class="heart_product_btn btn btn-primary">收藏此商品</button>
            </div>

            <div class="text store_illustrate">
                <p>付款後，從備貨到寄出商品為 5 個工作天（不包含假日)</p>
                <p>由商家提供統一發票或免用統一發票收據</p>
                <p>活動贈品數量有限，以購物車結帳畫面為主</p>
            </div>
            <hr>
            <div class="text discount">
                <h5 style="font-weight: bold;">優惠活動</h5>
                <ul class="promo_messages">
                    <li class="msg">活動1</li>
                    <li class="msg">活動1</li>
                    <li class="msg">活動1</li>
                </ul>
            </div>
            <hr>
            <div class="text product_illustrate">
                商品特色、商品規格.....等說明文字的區塊
            </div>`;

            productInfo.insertAdjacentHTML("beforeend", html);
        })


}

// init();

document.addEventListener("DOMContentLoaded", function () {
    const eventList = document.getElementById("eventList");
    const expandButton = document.getElementById("expandButton");
    let isExpanded = false; // 初始状态为未展开

    // 监听展开按钮的点击事件
    expandButton.addEventListener("click", function () {
        // 切换超过第三个 li 元素的显示状态
        const hiddenItems = eventList.querySelectorAll("li:nth-child(n+4)");
        hiddenItems.forEach(item => {
            item.style.display = isExpanded ? "none" : "list-item";
        });

        // 根据 isExpanded 变量切换按钮文字
        expandButton.textContent = isExpanded ? "顯示更多優惠活動" : "收起";
        isExpanded = !isExpanded; // 切换展开状态
    });
});
