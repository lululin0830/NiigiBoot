
const productId = 10000001;
const productPicture = document.querySelector("div.productPicture")
const productInfo = document.querySelector("#productInfoBox")
const commentList = document.querySelector("#commentList")
const shopInfoBox = document.querySelector("#shopInfoBox")


let product = '';
let productSpecs = '';


// JavaScript
function createImageURL(byteArray) {
    const blob = new Blob([new Uint8Array(byteArray)], { type: 'image/jpeg' });
    return URL.createObjectURL(blob);
}



const init = function () {

    fetch("http://localhost:8080/Niigi/product/" + productId)
        .then(resp => {
            if (!resp.ok) {
                throw new Error('系統繁忙中...請稍後再試');
            }
            return resp.json();
        })
        .then(data => {
            console.log(data);

            product = data[0][0].product;
            productSpecs = data[0]
            console.log(product)

            // ========================== 商品圖片區 ============================

            let pictureBody = `
            <div class="box">
                <div id='show_L'>
                    <img class="big_img" src="${product.picture1 ? createImageURL(product.picture1) : './image/square.svg'}" alt="">
                </div>
                <div class="all_img">
                    <div>
                        <img class="small_img" src="${product.picture1 ? createImageURL(product.picture1) : './image/square.svg'}" onclick="changeImage(this);" alt="">
                    </div>
                    <div>
                        <img class="small_img" src="${product.picture2 ? createImageURL(product.picture1) : './image/square.svg'}" onclick="changeImage(this);" alt="">
                    </div>
                    <div>
                        <img class="small_img" src="${product.picture3 ? createImageURL(product.picture1) : './image/square.svg'}" onclick="changeImage(this);" alt="">
                    </div>
                    <div>
                        <img class="small_img" src="${product.picture4 ? createImageURL(product.picture1) : './image/square.svg'}" onclick="changeImage(this);" alt="">
                    </div>
                    <div>
                        <img class="small_img" src="${product.picture5 ? createImageURL(product.picture1) : './image/square.svg'}" onclick="changeImage(this);" alt="">
                    </div>
                </div>
            </div>
            `;

            productPicture.innerHTML = pictureBody;

            // ========================== 商品資訊區 ============================
            let infoBody = `
                <h2 id="productName">${product.productName}</h2>
                <div class="product-star col-sm-6">`

            // 星星
            for (let i = 1; i <= 5; i++) {

                if (product.avgRating >= i) {
                    infoBody += `<span class="star -on" ><img src="./image/Star_zero.svg" alt=""></span>`
                } else if (product.avgRating > (i - 1) & product.avgRating % 1 < 1) {
                    infoBody += `<span class="star -halfon" ><img src="./image/Star_zero.svg" alt=""></span>`
                } else {
                    infoBody += `<span class="star" ><img src="./image/Star_zero.svg" alt=""></span>`
                }

            }

            // 價格
            infoBody +=
                `
                    <span id="avgRating">${product.avgRating}</span>
                    <span class="star_totoal">(187)</span>
                </div>

                <p class="price">
                    <span id="eventPrice">NT$${product.productPrice.toLocaleString()}</span>
                    <span id="productPrice">NT$${product.productPrice.toLocaleString()}</span>
                </p>`;

            // 規格
            infoBody += `
            <select name="productSpec" id="specSelector" class="form-select" onchange="showStock(this)">
                <option value="">請選擇商品規格</option>
            `;
            for (let i = 0; i < productSpecs.length; i++) {
                infoBody += `<option value="${productSpecs[i].productSpecId}" data-index="${i}">${productSpecs[i].specInfo1 + productSpecs[i].specInfo2}</option>`;
            }

            infoBody += `
                </select>
                <span class="stock ps-3 fw-bold" id="specStock"></span>
                </p>                                                           
                <div class="col-sm-11 d-flex align-items-center py-3">
                    <span style="font-size: 1.6rem;">數量</span>
                    <button type="button" class="col-sm-1 plus" onclick="increaseQuantity()"><img
                            src="./image/square-plus-regular.svg"></button>
                    <input type="number" id="quantity" name="quantity" class="custom-input form-control"
                        value="1" />
                    <button type="button" class="col-sm-1 minus" onclick="decreaseQuantity()"><img
                            src="./image/square-minus-regular.svg"></button>
                    <button class="btn btn-primary btn-M mx-3">加入購物車</button>
                    <button class="btn btn-primary btn-M mx-3">收藏此商品</button>
                </div>
                <div class="text store_illustrate">
                    <p>付款後，從備貨到寄出商品為 5 個工作天（不包含假日)</p>
                    <p>由商家提供統一發票或免用統一發票收據</p>
                </div>
                <hr>
                `;

            if (data[1] !== "noEvent") {

                infoBody += `
                <div class="eventInfo">
                <h4>優惠活動</h4>
                <ul id="eventList">`;

                for (let i = 0; i < data[1].length; i++) {
                    infoBody += `<li class="eventInfo">【6/30-8/30】全館不限金額結帳9折</li>`
                }
                if (data[1].length > 3) {
                    infoBody += `<button id="expandButton">顯示更多優惠活動</button>`
                }
                infoBody += `</ul></div><hr>`
            }


            infoBody += `                                                                   
                
                <div class="text pb-3" id="productInfo">
                    <p>${product.productInfo}</p>
                </div> `;

            productInfo.innerHTML = infoBody


            // ========================= 商品評論區 ===============================

            if (data[2] !== "noComment") {

                data[2].forEach(commnet => {

                    const dateString = commnet.commentDate;
                    const date = new Date(dateString);
                    const year = date.getFullYear();
                    const month = date.getMonth() + 1;
                    const day = date.getDate();

                    let commentBody = `
                    <li class="commentContent">
                        <div class="memberInfo row align-items-center">
                            <div class="memberPhoto col-sm-1">
                                <img src="${commnet.memPhoto ? createImageURL(commnet.memPhoto) : './image/Profile1.svg'}">
                            </div>
                            <span class="col-sm-4">${commnet.memberAcct.split("@")[0]}</span>
                            <div class="col-sm-4 ratingStar">`;

                    // 星星
                    for (let i = 1; i <= 5; i++) {

                        if (commnet.ratingStar >= i) {
                            commentBody += `<span class="star -on" ><img src="./image/Star_zero.svg" alt=""></span>`
                        } else if (commnet.ratingStar > (i - 1) & commnet.ratingStar % 1 < 1) {
                            commentBody += `<span class="star -halfon" ><img src="./image/Star_zero.svg" alt=""></span>`
                        } else {
                            commentBody += `<span class="star" ><img src="./image/Star_zero.svg" alt=""></span>`
                        }

                    }

                    commentBody +=
                        `</div>
                            <span class="col">${year + "-" + month + "-" + day}</span>
                        </div>
                        <p class="comment col-sm-10">${commnet.comment}</p>
                        <hr>
                    </li>`;

                    commentList.insertAdjacentHTML("beforebegin", commentBody)

                })

            } else {
                commentList.innerHTML = '暫無評論';
            }

            // =========================== 商家資訊區===============================

            let supplier = product.suppliers
            console.log(supplier)

            let shopInfoBody = `
            <a id="shopLink" class="d-flex align-items-baseline col-sm-4" href="${'商家那一頁?' + supplier.supplierId}">
                <h3 class="me-3">關於商家</h3>進店逛逛
            </a>

            <div class="card col-sm-12" id="shopInfoCard">
                <div id="shopBackground" class="col-sm-12">
                    <img src="${supplier.shopBackground ? createImageURL(supplier.shopBackground) : './image/shopBackground.png'}" >
                </div>

                <div class="card-body">
                    <div class="row align-items-center">
                        <div class="col-sm-2">
                            <div id="shopLogo">
                                <img src="${supplier.logo ? createImageURL(supplier.logo) : './image/AE404A5E-7B08-4A2A-B1A1-8D3F8FEE9CB8.png'}">
                            </div>
                        </div>
                        <div class="col-sm-5">
                            <div>
                                <h4 id="shopName">${supplier.shopName}</h4>
                            </div>
                            <div class="d-flex align-items-end">
                                <span class="location-icon"><img src="./image/LocationB.svg" alt=""></span>
                                <span class="card-text item__loction">${supplier.supplierAddress.substring(4, 7)}</span>
                                <span class="star-icons">`;

            for (let i = 1; i <= 5; i++) {

                if (data[3].shopAvgRating >= i) {
                    shopInfoBody += `<img class="star_store" src="./image/Star-on.svg">`
                } else if (data[3].shopAvgRating > (i - 1) & data[3].shopAvgRating % 1 < 1) {
                    shopInfoBody += `<img class="star_store" src="./image/Star-halfon.svg">`
                } else {
                    shopInfoBody += `<img class="star_store" src="./image/Star_zero.svg">`
                }

            }

            shopInfoBody +=
                `               </span>
                            </div>
                        </div>
                        <div class="col-sm-5">
                            <button class="join_store btn btn-primary btn-S">加入關注</button>
                            <button class="talk_store btn btn-primary btn-S">聊聊</button>
                        </div>
                    </div>
                </div>
            </div>`;

            let sameShopProducts = data[4]
            shopInfoBody += `<div class="row my-5" id="sameShopProduct">`;

            sameShopProducts.forEach(product => {
                shopInfoBody +=
                    `<div class="col-sm-4">
                    <a href="#" class="product-S" data-id="${product[0]}">
                        <div class="card product-S">
                            <img src="${product[3] ? createImageURL(product[3]) : './image/square.svg'}" class="card-img-top" >
                            <div class="card-body">
                                <div class="row">
                                    <p class="col-sm-9 productName">${product[1]} </p>
                                    <span class="col-sm-3">
                                        <img class="heart" src="./image/heart_store.svg" alt="" onclick="event.stopPropagation(); console.log('這裡要放加入收藏喔');">
                                    </span>
                                </div>

                                <p class="card-text item__price">$${product[2].toLocaleString()}</p>
                            </div>
                        </div>
                    </a>
                    </div>`;
            })

            shopInfoBody += `</div>`;
            shopInfoBox.innerHTML = shopInfoBody;
        })

}

init();

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


// 數量加總
function increaseQuantity() {
    let quantityInput = document.querySelector("#quantity");
    quantityInput.value = parseInt(quantityInput.value) + 1;
}

function decreaseQuantity() {
    let quantityInput = document.querySelector("#quantity");
    let currentValue = parseInt(quantityInput.value);
    if (currentValue > 1) {
        quantityInput.value = currentValue - 1;
    }
}


//大小圖更換
function changeImage(element) {
    document.querySelector("#show_L > img").setAttribute("src", element.getAttribute("src"));
}

// 庫存顯示
function showStock(element) {
    let index = element.selectedOptions[0].dataset.index;
    if (index) {
        document.querySelector("#specStock").innerText = "庫存 " + productSpecs[index].specStock
    } else {
        document.querySelector("#specStock").innerText = "";
    }
}

//關注鈕
$(".join_store").click(function () {
    if ($(this).text() == "加入關注") {
        $(this).text("關注中");
    } else {
        $(this).text("加入關注");
    }
})