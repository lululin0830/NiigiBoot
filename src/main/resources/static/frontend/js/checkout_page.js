// 表單內容定位
const memberPointBalance = document.querySelector("#memberPointBalance");
const pointsDiscountBox = document.querySelector("#pointsDiscount");
const memberName = document.querySelector("#name");
const memberPhone = document.querySelector("#phone");
const twzipcodeMember = new TWzipcode(document.querySelector("#memberAddress"));
const memberAddress = document.querySelector("#addressShort")
const recipient = document.querySelector("div.recipient-info span.name");
const phoneNum = document.querySelector("div.recipient-info span.phone");
const address = document.querySelector("div.recipient-info span.address");
const twzipcodeRecipient = new TWzipcode(document.querySelector("#recipientAddress"))
const recipientInfo = document.querySelector("div.recipient-info");
const recipientForm = document.querySelector("div.recipient-form");
const createOrderBtn = document.querySelector("#createOrder")

// 收件人地址暫存
let lastRecipient; let lastPhoneNum; let lastDeliveryAddress;

let recipientName; let recipientPhone; let deliveryAddress;

// 資料格式驗證
const phoneNumberPattern = /^(09\d{8}|09\d{2}-\d{3}-\d{3}|0\d{1,2}-\d{6,8})$/;

function createOrder() {

	// 資料收集
	const pointsDiscount = pointsDiscountBox.value ? pointsDiscountBox.value : 0;
	const couponDiscount = 0;
	const buyerName = memberName.value.trim();
	const buyerPhone = memberPhone.value.trim();
	const result = twzipcodeMember.get()
	const buyerAddress = result.zipcode + " " + result.county + result.district + memberAddress.value;
	const paymentType = document.querySelector("input[name='payment']:checked").value;
	const recipientType = document.querySelector("input[name='recipient']:checked").value;


	if (recipientType === 'new-shipping') {
		let result = twzipcodeRecipient.get()
		recipientName = document.querySelector("#newRecipient").value.trim();
		recipientPhone = document.querySelector("#newPhone").value.trim();
		deliveryAddress = result.zipcode + " " + result.county + result.district + document.querySelector("#newAddress").value;;
	}

	// 購買人資料驗證
	if (buyerName.length == 0) {

		showRequired();
		return;
	}
	if (buyerPhone.length == 0) {

		showRequired();
		return;

	} else if (!phoneNumberPattern.test(buyerPhone)) {

		alert("購買人電話號碼格式錯誤，請重新輸入")
		return;
	}
	if (memberAddress.value.length == 0) {
		showRequired();
		return;
	}

	// 收件人資料驗證
	if (recipientName.length == 0) {

		showRequired();
		return;
	}
	if (recipientPhone.length == 0) {

		showRequired();
		return;

	} else if (!phoneNumberPattern.test(recipientPhone)) {

		alert("收件人電話號碼格式錯誤，請重新輸入")
		return;
	}
	if (deliveryAddress.length == 0) {
		showRequired();
		return;
	}

	// 資料處理
	if (recipientPhone.length == 10) {
		recipientPhone = recipientPhone.substring(0, 4) + "-" + recipientPhone.substring(5, 7) + "-" + recipientPhone.substring(8, 10);
	}
	if (buyerPhone.length == 10) {
		buyerPhone = buyerPhone.substring(0, 4) + "-" + buyerPhone.substring(5, 7) + "-" + buyerPhone.substring(8, 10);
	}


	fetch('/createOrder', {
		method: 'post',
		headers: {
			'Content-Type': 'application/json',
			'Authorization': `Bearer ${jwtToken}`
		},
		body: JSON.stringify({
			'memberId': memberId,
			'pointsDiscount': pointsDiscount,
			'couponDiscount': couponDiscount,
			'paymentType': paymentType,
			'recipient': recipientName,
			'phoneNum': recipientPhone,
			'deliveryAddress': deliveryAddress,
			'buyerName': buyerName,
			'buyerPhone': buyerPhone,
			'buyerAddress': buyerAddress
		})

	})
		.then(function(resp) {

			if (!resp.ok) { throw new Error("系統忙碌中") }

			return r.text();
		})
		.then(function(data) {
			console.log(data);
			alert(data);
			clearCart();
		})
		.catch(function(error) {
			alert(error)
		});

}

function init() {

	fetch('/checkout', {
		method: 'POST',
		headers: {
			'Content-type': 'application/json',
			'Authorization': `Bearer ${jwtToken}`
		},
		body: memberId
	})
		.then(resp => {

			if (!resp.ok) { throw new Error(resp.statusText) }
			return resp.json()

		}).then(data => {
			console.log(data)

			// 處理地址資料
			let zipcode; let county; let district; let shortAddress;
			if (data.memberAddress) {
				zipcode = data.memberAddress.substring(0, 3);
				county = data.memberAddress.substring(4, 7);
				district = data.memberAddress.substring(8, 11);
				shortAddress = data.memberAddress.substring(12);
			}

			// 渲染所有資料
			memberPointBalance.innerText = data.memPointBalance ? data.memPointBalance : 0;
			pointsDiscountBox.setAttribute("max", memberPointBalance.innerText);
			memberName.value = data.name;
			memberPhone.value = data.phone;

			twzipcodeMember.set({
				"county": county,
				"district": district
			});
			memberAddress.value = shortAddress;

			recipient.innerText = data.name ? data.name : '';
			phoneNum.innerText = data.phone;
			address.innerText = zipcode ? zipcode + " " + county + district + shortAddress : '';

			// 設定暫存資料
			lastRecipient = data.lastRecipient;
			lastPhoneNum = data.lastPhoneNum;
			lastDeliveryAddress = data.lastDeliveryAddress;
			recipientName = data.name;
			recipientPhone = data.phone;
			deliveryAddress = data.memberAddress ? data.memberAddress : '';

		}).catch(error => alert(error))


	// 限制點數的最大值
	pointsDiscountBox.addEventListener("input", function() {
		const value = parseInt(pointsDiscountBox.value);
		const max = parseInt(pointsDiscountBox.max);

		if (value > max) {
			pointsDiscountBox.value = max;
		}
	});

	// 載入同購買人的收件資訊
	document.querySelectorAll("input[name='buyerInfo']").forEach(input => {
		input.addEventListener('change', loadRecipient)
	})

	// 切換收件人方式
	document.querySelectorAll("input[name='recipient']").forEach(button => {
		button.addEventListener('change', changeRecipient)
	})

	// 回上一頁
	document.querySelector("div.back-to-cart>a").addEventListener("click", event => {
		event.preventDefault();
		history.back()
	})
	createOrderBtn.addEventListener("click", createOrder);

}

window.addEventListener("load", init);

function loadRecipient() {

	const result = twzipcodeMember.get()

	recipient.innerText = memberName.value;
	phoneNum.innerText = memberPhone.value;
	address.innerText = result.zipcode + " " + result.county + result.district + memberAddress.value;

	recipientName = memberName.value;
	recipientPhone = memberPhone.value;
	deliveryAddress = result.zipcode + " " + result.county + result.district + memberAddress.value;
}

function changeRecipient() {
	const recipientType = this.value;

	switch (recipientType) {
		case 'same-as-buyer':
			recipientInfo.classList.remove("hidden");
			recipientForm.classList.add("hidden");

			loadRecipient();

			break;
		case 'select-last-shipping':
			recipientInfo.classList.remove("hidden");
			recipientForm.classList.add("hidden");

			const zipcode = lastDeliveryAddress.substring(0, 3);
			const county = lastDeliveryAddress.substring(4, 7);
			const district = lastDeliveryAddress.substring(8, 11);
			const shortAddress = lastDeliveryAddress.substring(12);

			if (lastRecipient) {
				recipient.innerText = lastRecipient;
				phoneNum.innerText = lastPhoneNum;
				address.innerText = zipcode + " " + county + district + shortAddress;
				recipientName = lastRecipient;
				recipientPhone = lastPhoneNum;
				deliveryAddress = lastDeliveryAddress;

			} else {
				alert("尚無常用收件地址，請選擇其他方式");
				document.querySelector("input[value='same-as-buyer']").checked = true;
			}

			break;
		default:
			recipientInfo.classList.add("hidden");
			recipientForm.classList.remove("hidden");
			break;
	}

}

function showRequired() {

	document.querySelectorAll("[required]").forEach(field => {

		if (field.value.trim() == '') {
			field.classList.add("required-field");
		}
	})

}

function clearCart() {
	fetch('/clearCart', {
		method: 'POST',
		headers: {
			'Content-type': 'application/json',
			'Authorization': `Bearer ${jwtToken}`
		},
		body: memberId
	}).then(() => {

		getCartCount();

	})

}
