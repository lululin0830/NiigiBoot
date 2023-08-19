
const productId = new URLSearchParams(window.location.search).get("productId");
const productPicture = document.querySelector("div.productPicture")
const productInfo = document.querySelector("#productInfoBox")
const commentList = document.querySelector("#commentList")
const shopInfoBox = document.querySelector("#shopInfoBox")
const commentListTitle = document.querySelector(".commentListBox > h3")

let product = '';
let productSpecs = '';

// 圖片轉換
function createImageURL(byteArray) {
    const blob = new Blob([new Uint8Array(byteArray)], { type: 'image/jpeg' });
    return URL.createObjectURL(blob);
}

// 數量加減
function increaseQuantity() {
    let quantityInput = document.querySelector("#quantity");
    if (!quantityInput.value) {
        quantityInput.value = 1;
    } else {
        quantityInput.value = parseInt(quantityInput.value) + 1;
    }
}

function decreaseQuantity() {
    let quantityInput = document.querySelector("#quantity");
    let currentValue = parseInt(quantityInput.value);
    if (currentValue > 1) {
        quantityInput.value = currentValue - 1;
    }
    if (!currentValue) {
        quantityInput.value = 1;
    }
}

//大小圖更換
function changeImage(element) {

    if (element.getAttribute("src") !== "./image/square.svg") {

        document.querySelector("#show_L > img").setAttribute("src", element.getAttribute("src"));
    }
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


const init = function () {

    fetch("../product/" + productId)
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
                        <img class="small_img" src="${product.picture2 ? createImageURL(product.picture2) : './image/square.svg'}" onclick="changeImage(this);" alt="">
                    </div>
                    <div>
                        <img class="small_img" src="${product.picture3 ? createImageURL(product.picture3) : './image/square.svg'}" onclick="changeImage(this);" alt="">
                    </div>
                    <div>
                        <img class="small_img" src="${product.picture4 ? createImageURL(product.picture4) : './image/square.svg'}" onclick="changeImage(this);" alt="">
                    </div>
                    <div>
                        <img class="small_img" src="${product.picture5 ? createImageURL(product.picture5) : './image/square.svg'}" onclick="changeImage(this);" alt="">
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
                    <span class="star_totoal">(${data[2] !== "noComment" ? data[2].length : 15})</span>
                </div>

                <p class="price">
                    <span id="eventPrice">NT$${data[5].toLocaleString()}</span>
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
                            src="./image/Plus.svg"></button>
                    <input type="number" id="quantity" name="quantity" class="custom-input form-control"
                        value="1" />
                    <button type="button" class="col-sm-1 minus" onclick="decreaseQuantity()"><img
                            src="./image/Minus.svg"></button>
                    <button class="btn btn-primary btn-M mx-3" onclick="addToCart();">加入購物車</button>
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
                    infoBody += `<li class="eventInfo">${data[1][i][1].eventName + data[1][i][1].eventInfo}</li>`
                }
                if (data[1].length >= 3) {
                    infoBody += `<button id="expandButton" onclick="expandEventList();">顯示更多優惠活動</button>`
                }
                infoBody += `</ul></div><hr>`
            }


            infoBody += `                                                                   
                
                <div class="text pb-3" id="productInfo">
                    <p>${product.productInfo}</p>
                </div> `;

            productInfo.innerHTML = infoBody


            // ========================= 商品評論區 ===============================

            commentListTitle.innerText = '商品評價';

            if (data[2] !== "noComment") {

                data[2].forEach(commnet => {

                    const dateString = commnet[2];
                    const dateParts = dateString.match(/(\d{1,2})月 (\d{1,2}), (\d{4})/);
                    const year = dateParts[3];
                    const month = dateParts[1].padStart(2, '0');
                    const day = dateParts[2].padStart(2, '0');

                    let commentBody = `
                    <li class="commentContent">
                        <div class="memberInfo row align-items-center">
                            <div class="memberPhoto col-sm-1">
                                <img src="${commnet[4] ? createImageURL(commnet[4]) : './image/Profile1.svg'}">
                            </div>
                            <span class="col-sm-4">${commnet[3] ? commnet[3].split("@")[0] : ''}</span>
                            <div class="col-sm-4 ratingStar">`;

                    // 星星
                    for (let i = 1; i <= 5; i++) {

                        if (commnet[0] >= i) {
                            commentBody += `<span class="star -on" ><img src="./image/Star-on.svg" alt=""></span>`
                        } else {
                            commentBody += `<span class="star" ><img src="./image/Star_zero.svg" alt=""></span>`
                        }

                    }

                    commentBody +=
                        `</div>
                            <span class="col">${year}-${month}-${day}</span>
                        </div>
                        <p class="comment col-sm-10">${commnet[1]}</p>
                        <hr>
                    </li>`;

                    commentList.insertAdjacentHTML("afterbegin", commentBody)

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
                    <a href="./product.html?productId=${product[0]}" class="product-S" data-id="${product[0]}">
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
            getCartCount();
        })

}

init();

// 展開優惠活動

let isExpanded = false;


function expandEventList() {

    const hiddenItems = eventList.querySelectorAll("li:nth-child(n+3)");
    hiddenItems.forEach(item => {
        item.style.display = isExpanded ? "none" : "list-item";
    });

    expandButton.textContent = isExpanded ? "顯示更多優惠活動" : "收起";
    isExpanded = !isExpanded;
};


function addToCart () {
	
    let productSpecIds = JSON.parse(sessionStorage.getItem("NiigiCart"))
    let productSpecId = document.querySelector("#specSelector").selectedOptions[0].value
    let count = document.querySelector("#quantity").value

    if (productSpecId.trim().length !== 0) {
        if (!productSpecIds) {
            productSpecIds = [];
        }

        for (let i = 0; i < count; i++) {
            productSpecIds.push(productSpecId);
        }
        sessionStorage.setItem("NiigiCart", JSON.stringify(productSpecIds));

         if (jwtToken) {

        fetch("../shoppingCart/add", {
            method: 'PUT',
            headers: {
                'Content-type': 'application/json',
                'Authorization': `Bearer ${jwtToken}`
            },
            body: JSON.stringify({
                'memberId': memberId,
                'productSpecIds': JSON.parse(sessionStorage.getItem("NiigiCart"))
            })
        }).then(resp => {

            if (!resp.ok) {
                throw new Error("系統繁忙中...請稍後再試")
            }

            return resp.text();
        }).then(msg => {


            alert(msg);
            sessionStorage.removeItem("NiigiCart");
            getCartCount();
        }).catch(error => alert(error))


         } else{
			 alert("已加入購物車");
			 getCartCount();
		 }
    } else {
        alert("請選擇商品規格")
    }




}

