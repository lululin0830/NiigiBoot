const memberId = 'M000000001';
const shoppintList = document.querySelector("#shoppintList");
const checkoutBox = document.querySelector("div.checkout");

// 取出Cookie中指定名稱的值
function getCookie(name) {
	const value = `; ${document.cookie}`;
	const parts = value.split(`; ${name}=`);
	if (parts.length === 2) return parts.pop().split(';').shift();
}

const htmlBody = function(element) {

	let specStock;
	let stockStatus;
	let price;

	if (element.specStock > 5) {
		specStock = "庫存充足";
		stockStatus = "instock";
	} else {
		specStock = "庫存緊張";
		stockStatus = "soldout";
	}
	if (element.shopVacation == '1') {
		specStock = "商家休假中";
		stockStatus = "vacation";
	}
	if (element.eventPrice) {
		price = element.eventPrice;
	} else if (element.couponPrice) {
		price = element.couponPrice;
	} else {
		price = element.productPrice;
	}

	let htmlBody = `
         <div class="col-sm-1">
             <input type="checkbox" class="select-cart-item">
         </div>

         <div class="col-sm-6">
             <h5 class="product-id">
                 #<span>${element.productSpecId}</span></h5>
             <h4 class="product-name">
                 ${element.productName}
             </h4>
             <p class="product-spec"><span class="spec-1">${element.specInfo1}</span> <span
                     class="spec-2">${element.specInfo2}</span></p>
         </div>
         <div class="col-sm-2">
             <h4 class="product-stock ${stockStatus}">${specStock}</h4>
         </div>
         <div class="col-sm-2">
             <h4 class="product-event-price">
                 NT$ <span>${price}</span>
             </h4>
         </div>
         <div class="col-sm-1">
             <button type="button" class="btn-close" onclick="removeItem(this);"></button>
         </div>`;

	return htmlBody;
}

const gotoCheckOut = function() {

	window.location.href = './checkout_page.html'
}

const init = function() {

	const jwtToken = getCookie('jwt')

	// if (jwtToken) {

	fetch("http://localhost:8080/Niigi/shoppingCart", {
		method: 'POST',
		headers: {
			'Content-type': 'application/json',
			'Authorization': `Bearer ${jwtToken}`
		},
		body: memberId

	}).then(resp => {

		if (!resp.ok) {
			throw new Error("系統繁忙中...請稍後再試")
		}

		return resp.json();

	}).then(data => {


		if (typeof data !== 'string') {

			shoppintList.innerHTML = '';
			let totalAmount = 0; let totalDiscount = 0; let checkAmount = 0;
			data.forEach(element => {

				console.log("element", element);

				let html = ''; let price = 0;

				if (element.eventIds || element.couponCode) {

					html += `<li class="cart-item event row" data-id="${element.productSpecId}">`;

					html += htmlBody(element);

					html +=
						`<ul class="event-list col-sm-11 row">
                <hr>
                <div class="col-sm-1">
                    <img src="./image/Line_in_alt.svg" alt="" class="arrow">
                </div>
                <div class="col-sm-11">`;

					if (element.couponCode) {
						html +=
							`<li class="event-item row">
                           <p class="discount-name">${element.couponName + element.couponInfo}</p>
                         </li>`;

						price = element.couponPrice;
						totalDiscount += element.productPrice - element.couponPrice;
					}

					if (element.eventIds) {
						for (let i = 0; i < element.eventIds.length; i++) {

							html +=
								`<li class="event-item row">
                                  <p class="discount-name">${element.eventName[i] + element.eventInfo[i]}</p>
                            </li>`;

							totalDiscount += element.eventDiscounts[i];
						}

						price = element.eventPrice;
					}

					html += `</div></ul></li>`;


				} else {

					html += `<li class="cart-item row" data-id="${element.productSpecId}">`;
					html += htmlBody(element);
					html += `</li>`;

					price = element.productPrice;

				}

				totalAmount += element.productPrice;
				checkAmount += price;
				shoppintList.insertAdjacentHTML("beforeend", html);



			});

			checkoutBox.innerHTML = `
        <div class="col-sm-5 p-5 row">
            <span class="col-sm-4">
                <p>商品總金額</p>
                <p>活動折抵</p>
            </span>
            <span class="col-sm-8 payment">
                <p>NT$<span id="totalAmount">${totalAmount.toString().toLocaleString()}</span></p>
                <p>-NT$<span id="totalDiscount">${totalDiscount.toString().toLocaleString()}</span></p>
            </span>
            <hr>
            <span class="col-sm-4">
                <h4>結帳金額</h4>
            </span>
            <span class="col-sm-8 payment">
                <h4>NT$<span id="checkAmount">${checkAmount.toString().toLocaleString()}</span></h4>
            </span>
            <button type="submit" class="btn btn-primary btn-XXL m-2" onclick="gotoCheckOut();">前往下單</button>
        </div>
        `;

		} else {

			shoppintList.innerHTML =  `
			<div class="col-sm-12 d-flex justify-content-center">
			<img src="./image/Empty_cart.svg" alt="" class="Empty_cart">
			</div>
			`;

			checkoutBox.innerHTML = '';
		}



	}).catch(error => alert(error))


	// } else {



	// }


}

const removeItem = function(element) {

	const removedItem = element.closest("li.cart-item");
	const productSpecId = removedItem.dataset.id;

	const jwtToken = getCookie('jwt')

	fetch('../shoppingCart/remove', {
		method: "PUT",
		headers: {
			'Content-type': 'application/json',
			'Authorization': `Bearer ${jwtToken}`
		},
		body: JSON.stringify({
			'memberId': memberId,
			'productSpecId': productSpecId
		})
	}).then(resp => {
		if (!resp.ok) {
			throw new Error("系統繁忙中...請稍後再試")
		}

		init();
	}).catch(error => alert(error))

}

init();