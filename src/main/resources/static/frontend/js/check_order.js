document.write(`<script src="./vendors/jquery/jquery-3.7.0.min.js"></script>`)

//先依照付款狀態reduce成兩組 0(待付款的)丟去跑迴圈新增至第一頁
//第二組再reduce一次 丟去跑回圈新增至該去的分頁

const memberId = document.querySelector('.memberId').innerHTML

//尚未付款分頁
const mainorder1 = document.querySelector("#navs-top-home>ul.order-list")
//處理中分頁
const mainorder2 = document.querySelector("#navs-top-inprogress>ul.sub-order-list")
//待收貨分頁
const mainorder3 = document.querySelector("#navs-top-transport>ul.sub-order-list");
//已完成分頁
const mainorder4 = document.querySelector("#navs-top-complete>ul.sub-order-list");
//已取消分頁
const mainorder5 = document.querySelector("#navs-top-cancel>ul.sub-order-list");
//去評價彈窗
const comment = document.querySelector('.commentbox');
//退貨談窗
const cancelProductBlock = document.getElementById('cancelProductBlock');

let EIF = null;
let bodyHtml = null;
let updateCommentlist = []
let refundSubOrderId = null;
const paymentPendingBody = function (arr) {

	console.log("我是arr", arr)
	arr.forEach(arr => {

		let dateString = arr[0][3];
		let date = new Date(dateString);
		let year = date.getFullYear(); // 獲得年份
		let month = date.getMonth() + 1; // 獲得月份（注意 JavaScript 中的月份是從 0 開始的，所以需要加 1）
		let day = date.getDate();

		let html =
			`<li class="order">
            <div class="navs-top">
                <div class="col-sm-2">
                    <h4 class="order-date">${year + "-" + month + "-" + day}</h4>
                </div>
                <div class="col-sm-7">
                    <h4 class="order-id">訂單編號：<span class="order-id">${arr[0][4]}</span></h4>
                    <h4 class="order-amount">訂購金額：<span>${arr[0][5]}</span><span
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
                        <button type="button" class="btn btn-primary btn-XL cancelMainOrder"
                            data-bs-toggle="modal" data-bs-target="#cancelOrderModal">
                            取消訂單
                        </button>
                    </div>
                </div>
                <hr>
            </div>`

		//可能重複結構(多個子訂單)
		for (i = 0; i < arr.length; i++) {

			const imageElement = createImageURL(arr[i][9])
			html +=
				`< div class="navs-top-content" >
            <ul class="sub-order-list">
                <li class="sub-order home">
                    <div class="col-sm-2">
                        <div class="order_item_priview">
                            <img class="order_item_priview" src="${imageElement}" 
                                alt="">
                        </div>
                    </div>
                    <div class="col-sm-7 ps-3">
                        <h4 class="shop-name">店家名稱：<span>${arr[i][6]}</span></h4>
                        <h4 class="order-id">訂單編號：<span class="sub-order-id">${arr[i][7]}</span>
                        </h4>
                        <h4 class="order-amount">訂購金額：<span>${arr[i][8]}</span></h4>
                    </div>
                    <div class="col-sm-3 position-relative ">

                        <!-- Button trigger modal -->
                        <div class="navs-top-content-btn">
                            <button type="button" class="btn btn-primary btn-XL checkDetail"
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
		bodyHtml += html;
	});

}


const inprogressBody = function (element) {

	let dateString = element[3];
	let date = new Date(dateString);
	let year = date.getFullYear(); // 獲得年份
	let month = date.getMonth() + 1; // 獲得月份（注意 JavaScript 中的月份是從 0 開始的，所以需要加 1）
	let day = date.getDate();

	let orderStatus;
	switch (element[2]) {

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
	const imageElement = createImageURL(element[9])
	let html =
		`<li class="sub-order inprogress">
        <div class="col-sm-2">
            <h4 class="order-date">${year + "-" + month + "-" + day}</h4>
            <div class="order_item_priview">
                <img class="order_item_priview" src="${imageElement}" alt="">
            </div>
        </div>
        <div class="col-sm-7 ps-3">
            <h4 class="shop-name">店家名稱：<span>${element[6]}</span></h4>
            <h4 class="order-id">訂單編號：<span class="sub-order-id">${element[7]}</span>
            </h4>
            <h4 class="order-amount">訂購金額：<span>${element[8]}</span></h4>
            <h4 class="order_status">物流狀態：<span>${orderStatus}</span></h4>
        </div>
        <div class="col-sm-3 position-relative ">

            <!-- Button trigger modal -->
            <div class="navs-top-btn">
                <button type="button" class="btn btn-primary btn-XL cancelSubOrder"
                    data-bs-toggle="modal" data-bs-target="#cancelOrderModal">
                    取消訂單
                </button>
            </div>
            <div class="navs-top-btn">
                <button type="button" class="btn btn-primary btn-XL checkDetail"
                    data-bs-toggle="modal" data-bs-target="#orderDetilModal">
                    查看訂單明細
                </button>
            </div>

        </div>
        <hr>
    </li>`
	bodyHtml += html;



}
const transportBody = function (element) {

	let dateString = element[3];
	let date = new Date(dateString);
	let year = date.getFullYear(); // 獲得年份
	let month = date.getMonth() + 1; // 獲得月份（注意 JavaScript 中的月份是從 0 開始的，所以需要加 1）
	let day = date.getDate();

	let orderStatus;
	switch (element[2]) {

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

	const imageElement = createImageURL(element[9])

	let html =
		`<li class="sub-order transport">
        <div class="col-sm-2">
            <h4 class="order-date">${year + "-" + month + "-" + day}</h4>
            <div class="order_item_priview">
                <img class="order_item_priview" src=${imageElement} alt="">
            </div>
        </div>
        <div class="col-sm-7 ps-3">
            <h4 class="shop-name">店家名稱：<span>${element[6]}</span></h4>
            <h4 class="order-id">訂單編號：<span class="sub-order-id">${element[7]}</span>
            </h4>
            <h4 class="order-amount">訂購金額：<span>${element[8]}</span></h4>
            <h4 class="order_status">物流狀態：<span>${orderStatus}</span></h4>
            <h4 class="refund_deadline">退貨期限：<span>${year + "-" + month + "-" + (day + 7)}</span></h4>
        </div>
        <div class="col-sm-3 position-relative ">

            <!-- Button trigger modal -->
            <div class="navs-top-btn">
                <button type="button" class="btn btn-primary btn-XL cancelProduct"
                    data-bs-toggle="modal" data-bs-target="#refundModal">
                    退貨
                </button>
            </div>
            <div class="navs-top-btn">
                <button type="button" class="btn btn-primary btn-XL submitReceipt"
                    data-bs-toggle="modal" data-bs-target="#closeOrderModal">
                    確認收貨
                </button>
            </div>
            <div class="navs-top-btn">
                <button type="button" class="btn btn-primary btn-XL checkDetail"
                    data-bs-toggle="modal" data-bs-target="#orderDetilModal">
                    查看訂單明細
                </button>
            </div>

        </div>
        <hr>
    </li>`
	bodyHtml += html;
}

const completeBody = function (element) {

	let dateString = element[3];
	let date = new Date(dateString);
	let year = date.getFullYear(); // 獲得年份
	let month = date.getMonth() + 1; // 獲得月份（注意 JavaScript 中的月份是從 0 開始的，所以需要加 1）
	let day = date.getDate();

	const imageElement = createImageURL(element[9])
	let html =
		`<li class="sub-order complete">
        <div class="col-sm-2">
            <h4 class="order-date">${year + "-" + month + "-" + day}</h4>
            <div class="order_item_priview">
                <img class="order_item_priview" src=${imageElement} alt="">
            </div>
        </div>
        <div class="col-sm-7 ps-3">
            <h4 class="shop-name">店家名稱：<span>${element[6]}</span></h4>
            <h4 class="order-id">訂單編號：<span class="sub-order-id">${element[7]}</span>
            </h4>
            <h4 class="order-amount">訂購金額：<span>${element[8]}</span></h4>
        </div>
        <div class="col-sm-3 position-relative ">

            <!-- Button trigger modal -->
            <div class="navs-top-btn">
                <button type="button" class="btn btn-primary btn-XL checkDetail"
                    data-bs-toggle="modal" data-bs-target="#orderDetilModal">
                    查看訂單明細
                </button>
            </div>
            <div class="navs-top-btn">
                <button type="button" class="btn btn-primary btn-XL evaluate ${element[10] == '1' ? 'disable' : ''}"
                    data-bs-toggle="modal" data-bs-target="#commentModal" ${element[10] == '1' ? 'disabled ' : ''}>
                    ${element[10] == '1' ? '已評價' : '去評價'}
                </button>
            </div>

        </div>
        <hr>
    </li>`
	bodyHtml += html;
}

const cancelBody = function (arr) {
	console.log("已取消arr", arr)
	arr.forEach(arr => {
		let dateString = arr[3];
		let date = new Date(dateString);
		let year = date.getFullYear(); // 獲得年份
		let month = date.getMonth() + 1; // 獲得月份（注意 JavaScript 中的月份是從 0 開始的，所以需要加 1）
		let day = date.getDate();
		console.log(arr[9])
		const imageElement = createImageURL(arr[9])
		console.log(imageElement)

		let html =
			`<li class="sub-order cancel">
                <div class="col-sm-2">
                    <h4 class="order-date">${year + "-" + month + "-" + day}</h4>
                    <div class="order_item_priview">                        
						<img class="order_item_priview" src="${imageElement}" alt="">
                    </div>
                </div>
                <div class="col-sm-7 ps-3">
                    <h4 class="shop-name">店家名稱：<span>${arr[6]}</span></h4>
                    <h4 class="order-id">訂單編號：<span class="sub-order-id">${arr[7]}</span>
                    </h4>
                    <h4 class="order-amount">訂購金額：<span>${arr[8]}</span></h4>
                </div>
                <div class="col-sm-3 position-relative ">

                    <!-- Button trigger modal -->
                    <div class="navs-top-btn">
                        <button type="button" class="btn btn-primary btn-XL"
                            data-bs-toggle="modal" data-bs-target="#cancelOrderModal">
                            再買一次
                        </button>
                    </div>
                    <div class="navs-top-btn">
                        <button type="button" class="btn btn-primary btn-XL checkDetail"
                            data-bs-toggle="modal" data-bs-target="#orderDetilModal">
                            查看訂單明細
                        </button>
                    </div>

                </div>
                <hr>
            </li>`
		bodyHtml += html;
	})

}
const cancelPayOrder = function (element) {
	console.log("element", element)
	let dateString = element[3];
	let date = new Date(dateString);
	let year = date.getFullYear(); // 獲得年份
	let month = date.getMonth() + 1; // 獲得月份（注意 JavaScript 中的月份是從 0 開始的，所以需要加 1）
	let day = date.getDate();
	const imageElement = createImageURL(element[9])
	let html =
		`<li class="sub-order">
                <div class="col-sm-2">
                    <h4 class="order-date">${year + "-" + month + "-" + day}</h4>
                    <div class="order_item_priview">
                        <img class="order_item_priview" src="${imageElement}" alt="">
                    </div>
                </div>
                <div class="col-sm-7 ps-3">
                    <h4 class="shop-name">店家名稱：<span>${element[6]}</span></h4>
                    <h4 class="order-id">訂單編號：<span class="sub-order-id">${element[7]}</span>
                    </h4>
                    <h4 class="order-amount">訂購金額：<span>${element[8]}</span></h4>
                </div>
                <div class="col-sm-3 position-relative ">

                    <!-- Button trigger modal -->
                    <div class="navs-top-btn">
                        <button type="button" class="btn btn-primary btn-XL"
                            data-bs-toggle="modal" data-bs-target="#cancelOrderModal">
                            再買一次
                        </button>
                    </div>
                    <div class="navs-top-btn">
                        <button type="button" class="btn btn-primary btn-XL checkDetail"
                            data-bs-toggle="modal" data-bs-target="#orderDetilModal">
                            查看訂單明細
                        </button>
                    </div>

                </div>
                <hr>
            </li>`
	bodyHtml += html;
}
const commentbox = function (arr) {

	const imageElement = createImageURL(arr[3])

	let html =
		`<li class="comment-item row" data-id="${arr[1]}">
			<div class="item-review col-sm-4">
				<img src=${imageElement} alt="" id="imageElement">
			</div>
			<div class="col-sm-8">
				<h4 class="product-name" id="comment-product-name">${arr[2]}</h4>

				<div class="product-star col-sm-7">
					<span class="star" data-star="1"><img
					src="./image/Star-off.svg" alt=""></span>
					<span class="star" data-star="2"><img
						src="./image/Star-off.svg" alt=""></span>
					<span class="star" data-star="3"><img
						src="./image/Star-off.svg" alt=""></span>
					<span class="star" data-star="4"><img
						src="./image/Star-off.svg" alt=""></span>
					<span class="star" data-star="5"><img
						src="./image/Star-off.svg" alt=""></span>
				</div>

				<textarea class="comment form-control mt-3 commentArea" rows="2"
					placeholder="請留下你的評論"></textarea>
			</div>
			<hr>
		</li>`
	bodyHtml = ""
	bodyHtml += html

}
const cancelProductbody = function (arr) {
	const imageElement = createImageURL(arr[6])
	bodyHtml = null;
	let html =
		`<li class="order-item-detail row" data-id="${arr[8]}">
			<div class="col">
			</div>
			<div class="col-sm-1">
				<img src=${imageElement} alt="">
			</div>
			<div class="col-sm-7">
				<h5 class="product-id">
					#<span>${arr[0]}</span></h5>
				<h4 class="product-name">${arr[1]}</h4>
			</div>
			<div class="col-sm-3">
				<h4 class="product-price">
					NT$<span>${arr[2]}</span>
				</h4>
			</div>
			<hr>
		</li>`
	bodyHtml += html
}

// 圖片轉換
function createImageURL(byteArray) {
	const blob = new Blob([new Uint8Array(byteArray)], { type: 'image/jpeg' });
	return URL.createObjectURL(blob);
}
function groupByField(data, index) {
	return data.reduce((result, item) => {
		// 確保 array[index] 有效且為字串或數字型態
		// console.log(item[3])
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


const init = function () {
	console.log(mainorder1)
	console.log("memberId", memberId)

	fetch('http://localhost:8080/Niigi/MemberCheckOrder/orderAll', {
		method: 'POST',
		headers: {
			'Content-type': 'application/json',
		},
		body: memberId
	}).then(r => r.json()).then(data => {

		console.log("我是data", data)

		//===================================尚未付款區===================================
		let paymentPending = groupByField(data[0], 4)
		// console.log("我是pay")s
		console.log("尚未付款", paymentPending)
		paymentPendingBody(paymentPending)
		mainorder1.insertAdjacentHTML("beforeend", bodyHtml);
		bodyHtml = null;
		//===================================處理中區===================================
		let paymentsuccess = data[0]
		console.log("我是ps", paymentsuccess)
		// let orderStatuscheck = groupByField(paymentsuccess, 1)
		// console.log("主訂單狀態", orderStatuscheck)

		paymentsuccess.forEach(element => {
			// console.log("element", element)
			if (element[2] == 0 || Element[2] == 1) {
				inprogressBody(element)
				mainorder2.insertAdjacentHTML("beforeend", bodyHtml);
				bodyHtml = null
				//===================================待收貨區===================================
			} if (element[2] == 2) {
				transportBody(element)
				mainorder3.insertAdjacentHTML("beforeend", bodyHtml);
				bodyHtml = null
				//===================================已完成區===================================
			} if (element[2] == 3) {
				completeBody(element)
				mainorder4.insertAdjacentHTML("beforeend", bodyHtml);
				bodyHtml = null
				//===================================已取消區===================================
			} if (element[2] == 4 || element[2] == 5) {
				cancelPayOrder(element)
				mainorder5.insertAdjacentHTML("beforeend", bodyHtml);
				bodyHtml = null
			};
		})


		if (data[1]) {

			let cancelOrder = data[1]
			cancelBody(cancelOrder)
		}

		mainorder5.insertAdjacentHTML("beforeend", bodyHtml);
		// =====================各分頁按鈕區=====================
		//查看訂單綁定事件
		document.querySelectorAll("button.checkDetail").forEach(function (e) {
			e.addEventListener("click", checkOrderDetail);
		})
		//確認收貨綁定事件
		document.querySelectorAll("button.submitReceipt").forEach(function (e) {
			e.addEventListener("click", confirmReceipt);
		})
		//取消主訂單按鈕
		document.querySelectorAll("button.cancelMainOrder").forEach(function (e) {
			e.addEventListener("click", cancelMainOrder);
		})
		//取消子訂單按鈕
		document.querySelectorAll("button.cancelSubOrder").forEach(function (e) {
			e.addEventListener("click", cancelSubOrder);
		})
		//去評價按鈕
		document.querySelectorAll("button.evaluate").forEach(function (e) {
			e.addEventListener("click", subOrderDetailcomment);
		})
		//退貨按鈕
		document.querySelectorAll("button.cancelProduct").forEach(function (e) {
			e.addEventListener("click", cancelProduct);
		})

	})
	bodyHtml = null;
}
init();

//查看訂單方法
const checkOrderDetail = function () {

	const subOrderId = $(this).closest('li.sub-order').find('span.sub-order-id').text()
	// console.log("subOrderId", subOrderId)

	fetch('http://localhost:8080/Niigi/MemberCheckOrder/subOrderDetail', {
		method: 'POST',
		headers: {
			'Content-type': 'application/json',
		},
		body: subOrderId
	}).then(r => r.json()).then(data => {
		console.log("查看訂單", data)

		let detailSpectId = document.getElementById('detailSpectId');
		detailSpectId.innerHTML = data[0][0];

		let detailproductName = document.getElementById('detailproductName');
		detailproductName.innerHTML = data[0][1];

		let detailproductPrice = document.getElementById('detailproductPrice');
		detailproductPrice.innerHTML = data[0][2];

		let detailrecipient = document.getElementById('detailrecipient');
		detailrecipient.innerHTML = data[0][3];

		let detailPhoneNumber = document.getElementById('detailPhoneNumber');
		detailPhoneNumber.innerHTML = data[0][4];

		let detailDeliveryAddress = document.getElementById('detailDeliveryAddress');
		detailDeliveryAddress.innerHTML = data[0][5];

		let detailpicture = document.getElementById('detailpicture');
		detailpicture.src = createImageURL(data[0][6]);
		//
		//        let detaileventprice = document.getElementById('detaileventprice');
		//        detaileventprice.innerHTML = data[0][7];

	})
}
//確認收貨方法
const confirmReceipt = function () {
	const subOrderId = $(this).closest('li.sub-order').find('span.sub-order-id').text()
	// console.log(subOrderId)
	// console.log(document.querySelector("button.confirmReceipt"))

	async function updateReceipt() {
		if (document.querySelector("button.confirmReceipt") !== null) {
			await fetch('http://localhost:8080/Niigi/MemberCheckOrder/subOrderConfirmReceipt', {
				method: 'PATCH',
				headers: {
					'Content-type': 'application/json',
				},
				body: subOrderId
			});
			document.querySelector("#closeOrderModal button.btn-close").click();
			document.querySelector("#navs-top-home>ul.order-list").innerHTML = ''
			document.querySelector("#navs-top-inprogress>ul.sub-order-list").innerHTML = ''
			document.querySelector("#navs-top-transport>ul.sub-order-list").innerHTML = ''
			document.querySelector("#navs-top-complete>ul.sub-order-list").innerHTML = ''
			document.querySelector("#navs-top-cancel>ul.sub-order-list").innerHTML = ''
			init();
		}
	}

	document.querySelector("button.confirmReceipt").addEventListener("click", function () {
		updateReceipt();
	})
}
//取消主訂單
const cancelMainOrder = function () {
	const OrderId = $(this).closest('li.order').find('span.order-id').text()

	async function updateMainOrderStatus() {

		if (document.querySelector("button.confirmCancelOrder")) {

			await fetch('http://localhost:8080/Niigi/MemberCheckOrder/cancelMainOrder', {
				method: 'POST',
				headers: {
					'Content-type': 'application/json',
				},
				body: OrderId
			})

			document.querySelector("#cancelOrderModal .btn-close").click();
			// $('#cancelOrderModal').modal('hide');
			mainorder1.innerHTML = ''
			mainorder2.innerHTML = ''
			mainorder3.innerHTML = ''
			mainorder4.innerHTML = ''
			mainorder5.innerHTML = ''
			bodyHtml = null;
			init();

		}

	}

	document.querySelector("button.confirmCancelOrder").addEventListener("click", function () {
		updateMainOrderStatus();
	})


}
//取消子訂單
const cancelSubOrder = function () {
	const subOrderId = $(this).closest('li.sub-order').find('span.sub-order-id').text()
	console.log("subOrderId", subOrderId)

	async function updatesubOrderStatus() {

		if (document.querySelector("button.confirmCancelOrder")) {

			await fetch('http://localhost:8080/Niigi/MemberCheckOrder/cancelSubOrder', {
				method: 'POST',
				headers: {
					'Content-type': 'application/json',
				},
				body: subOrderId
			})

			document.querySelector("#cancelOrderModal .btn-close").click();
			// $('#cancelOrderModal').modal('hide');
			mainorder1.innerHTML = ''
			mainorder2.innerHTML = ''
			mainorder3.innerHTML = ''
			mainorder4.innerHTML = ''
			mainorder5.innerHTML = ''
			bodyHtml = null;
			init();

		}
	}
	document.querySelector("button.confirmCancelOrder").addEventListener("click", function () {
		updatesubOrderStatus();
	})
}
//評價視窗資訊
const subOrderDetailcomment = function () {
	const subOrderId = $(this).closest('li.sub-order').find('span.sub-order-id').text()
	comment.innerHTML = ''
	let starvalues = null
	fetch('http://localhost:8080/Niigi/MemberCheckOrder/subOrderDetailcomment', {
		method: 'POST',
		headers: {
			'Content-type': 'application/json',
		},
		body: subOrderId
	}).then(r => r.json()).then(data => {
		console.log(data[0])
		for (i = 0; i < data.length; i++) {
			commentbox(data[i])
			comment.insertAdjacentHTML("beforeend", bodyHtml);
			createImageURL(data[i][3])
		}
		//=============星星=============
		const stars = document.querySelectorAll('.star')

		stars.forEach(star => {
			star.addEventListener("click", function (e) {
				var starValue = this.getAttribute("data-star");
				starvalues = starValue
				this.classList.add("-on");
				const siblings = Array.from(this.parentNode.children).filter(sibling => sibling !== this);
				siblings.forEach(sibling => {
					if (sibling.getAttribute("data-star") <= starValue) {
						sibling.classList.add("-on");
					} else {
						sibling.classList.remove("-on");
					}
				});
			});
		});
		//=============星星=============

	})

	document.querySelector('.submitComment').onclick = () => {
		const orderDetailIdList = document.querySelectorAll("#commentModal div.modal-body>ul>li")

		orderDetailIdList.forEach(detail => {

			const detailResult = {
				orderDetailId: document.querySelector("#commentModal div.modal-body>ul>li").dataset.id,
				ratingStar: document.querySelectorAll('.-on').length,
				comment: document.querySelector('.commentArea').value
			}
			updateCommentlist.push(detailResult)
		})
		console.log(JSON.stringify(updateCommentlist));

		submitComment();
	}
}


//送出評價
const submitComment = function () {
	fetch('http://localhost:8080/Niigi/MemberCheckOrder/updateSubOrderDetailComment', {
		method: 'POST',
		headers: {
			'Content-type': 'application/json',
		},
		body: JSON.stringify(updateCommentlist)
	}).then(function (resp) {

		if (!resp.ok) {
			throw new error("系統繁忙中...請稍後再試")
		}

		return resp.text();

	}).then(function (data) {
		console.log(data);

		document.querySelector("div#commentModal button.btn-outline-secondary").click();

		setTimeout(function () { alert(data); }, 800);


	})
}

//退貨視窗
const cancelProduct = function () {

	const subOrderId = $(this).closest('li.sub-order').find('span.sub-order-id').text()
	console.log("subOrderId", subOrderId)
	cancelProductBlock.innerHTML = ""
	refundSubOrderId = subOrderId
	fetch('http://localhost:8080/Niigi/MemberCheckOrder/subOrderDetail', {
		method: 'POST',
		headers: {
			'Content-type': 'application/json',
		},
		body: subOrderId
	}).then(r => r.json()).then(data => {
		console.log("查看退貨訂單", data)
		data.forEach(function (element) {
			console.log("查看退貨element", element)
			cancelProductbody(element)
			cancelProductBlock.insertAdjacentHTML("beforeend", bodyHtml);
			document.getElementById('submitCancelProduct').addEventListener('click', function () {
				submitCancelProduct();
			})
		})
	})
}

const submitCancelProduct = function () {

	const refundData = {
		refundSubOrderId: refundSubOrderId,
		refundReason: document.getElementById("refundReason").value,
		refundMark: document.getElementById("refundRemark").value
	}
	console.log(refundData)

	fetch('http://localhost:8080/Niigi/MemberCheckOrder/updateRefund', {
		method: 'POST',
		headers: {
			'Content-type': 'application/json',
		},
		body: JSON.stringify(refundData)
	})
	document.getElementById("btnClose").click();
}
