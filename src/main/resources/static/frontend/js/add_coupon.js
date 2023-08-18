

const selectElement2 = document.getElementById('discount');
const input1Element21 = document.getElementById('Amount');
const input1Element22 = document.getElementById('discountAmount');
const input2Element21 = document.getElementById('Rate');
const input2Element22 = document.getElementById('discountRate');

selectElement2.addEventListener('change', function() {
    if (selectElement2.value === 'discountAmount') {
      input1Element21.hidden = false;
      input1Element22.hidden = false;
      input2Element21.hidden = true;
      input2Element22.hidden = true;
    } else if (selectElement2.value === 'discountRate') {
      input1Element21.hidden = true;
      input1Element22.hidden = true;
      input2Element21.hidden = false;
      input2Element22.hidden = false;
    }
  });

const selectElement3 = document.getElementById('thresholdType');
const input1Element31 = document.getElementById('t11');
const input1Element32 = document.getElementById('minPurchaseQuantity');
const input1Element33 = document.getElementById('t12');
const input1Element34 = document.getElementById('t13');
const input2Element31 = document.getElementById('t21');
const input2Element32 = document.getElementById('minPurchaseAmount');
const input2Element33 = document.getElementById('t22');

selectElement3.addEventListener('change', function() {
    if (selectElement3.value === '1') {
      input1Element31.hidden = true;
      input1Element32.hidden = true;
      input1Element33.hidden = true;
      input1Element34.hidden = true;
      input2Element31.hidden = false;
      input2Element32.hidden = false;
      input2Element33.hidden = false;
    } else if (selectElement3.value === '2') {
      input1Element31.hidden = false;
      input1Element32.hidden = false;
      input1Element33.hidden = false;
      input1Element34.hidden = true;
      input2Element31.hidden = true;
      input2Element32.hidden = true;
      input2Element33.hidden = true;
    } else if (selectElement3.value === '3') {
      input1Element31.hidden = false;
      input1Element32.hidden = false;
      input1Element33.hidden = false;
      input1Element34.hidden = false;
      input2Element31.hidden = false;
      input2Element32.hidden = false;
      input2Element33.hidden = false;
    }
  });
  
function displayImageFromBase64(base64ImageData) {
			const imageBinaryStr = atob(base64ImageData);
			let len = imageBinaryStr.length;
			const uint8Array = new Uint8Array(len);
			while (len--) {
				uint8Array[len] = imageBinaryStr.charCodeAt(len);
			}
			const blob = new Blob([uint8Array]);
			const blobUrl = URL.createObjectURL(blob); // 產生 Blob URL
			// document.querySelector('#avatar').src = blobUrl; // 設定圖片的 src 屬性
			return blobUrl; // 返回 Blob URL
}

function shelvesStatusName(Str) {
  switch (Str) {
    case '0':
      return '上架中';
    case '1':
      return '下架中';
  }
}


let eventId = "";
let eventType = "1";
let couponUsedAmount = "0";


const addProduct = function () {

    console.log("進來了");


    fetch('/AllProductSpecManage', {
				method: 'POST',
				body: JSON.stringify({registerSupplier: supplierId}),
				headers: {
					'Content-Type': 'application/json'
				}
			})
				.then((response) => {
					// 檢查回應的狀態碼
					if (!response.ok) {
						throw new Error('Network response was not ok');
					}
					// 解析JSON格式的回應
					return response.json();
				})
				.then((data) => {
          const tbody = document.querySelector('#AllProduct');
          tbody.innerHTML = "";
					//console.log(data);
					data.forEach(function (productSpec) {
						// 在這裡對每個產品進行處理
            //商品編號
            const productId = productSpec.productId;
            // 取得產品圖片的 Base64 字串
						const base64Image = displayImageFromBase64(productSpec.specPicture);
						//取得產品規格id
						const productSpecId = productSpec.productSpecId;
						// 取得產品名稱
						const productName = productSpec.productName;
						//取得對規格1
						const specType1 = productSpec.specType1;
						const specInfo1 = productSpec.specInfo1;
						//取得對規格2
						const specType2 = productSpec.specType2;
						const specInfo2 = productSpec.specInfo2;
						// 取得產品價格
						const productPrice = productSpec.productPrice;
						//取得即時庫存
						const specStock = productSpec.specStock;
						//取得已售出
						const soldStock = productSpec.soldStock;
						//取得狀態
						const shelvesStatus = shelvesStatusName(productSpec.shelvesStatus);


						// 也可以將其他產品資料顯示在網頁上
						const row = `
										<tr>
											<td class="check">
												<input type="checkbox" id="productId" class="selectRow" data-page="#navs-top-home" data-product-spec-id="${productSpecId}" value="${productId}">
											</td>
                      <td><img class="productImg" src="${base64Image}" alt=""></td>
											<td class="productNameTd">
												<p class="productName">${productName}</p>
												<p class="productId">${productSpecId}</p>
											</td>
											<td class="spec1">${specInfo1}</td>
											<td class="type1" style="display: none;">${specType1}</td>
											<td class="spec2">${specInfo2}</td>
											<td class="type2" style="display: none;">${specType2}</td>
											<td class="price">NT$${productPrice}</td>
											<td class="stock">${specStock}</td>
											<td class="soldNum">${soldStock}</td>
											<td class="status">${shelvesStatus}</td>
										</tr>
									`;
                  tbody.innerHTML += row;
					});

				})
				.catch((error) => {
					console.error('Fetch error:', error);
				});

        // 假設你的後端生成訂單編號的 API 路徑是 /generateOrderId
        fetch('/AddEvent/generateOrderId')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text(); // 解析回應的文字內容
        })
        .then(eventIdNew => {
            // 在這裡處理取得的 eventId
            console.log('取得的 eventIdNew：', eventIdNew);
            // 將 eventId 填入你的表單或相關的元素中
            eventId = eventIdNew;
            console.log('取得的 eventIdNEW：', eventId);
        })
        .catch(error => {
            console.error('Fetch error:', error);
        });
}
document.addEventListener("coreDone", addProduct);

const registerButton = document.getElementById('send');
registerButton.addEventListener('click', async () => {
  let discountRate = document.getElementById("discountRate").value
    let disrate = discountRate/10;
  const newEvent = JSON.stringify({
      eventId: eventId,
      eventRegisterSupplier: supplierId,
      eventName: document.getElementById("eventName").value,
      eventInfo: document.getElementById("eventInfo").value,
      couponCode: document.getElementById("couponCode").value,
      eventStart: document.getElementById("StartDate").value,
      eventEnd: document.getElementById("EndDate").value,
      eventType: eventType,
      couponAvailableAmount: document.getElementById("couponAvailableAmount").value,
      couponAvailablePerPurchase: document.getElementById("couponAvailablePerPurchase").value,
      couponUsedAmount: couponUsedAmount,
      discountRate: disrate,
      discountAmount: document.getElementById("discountAmount").value,
      thresholdType: document.getElementById("thresholdType").value,
      minPurchaseQuantity: document.getElementById("minPurchaseQuantity").value,
      minPurchaseAmount: document.getElementById("minPurchaseAmount").value, 
  });

    const response = await fetch('/AddEvent/addEvent', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: newEvent
    });


    const checkboxes = document.querySelectorAll('.selectRow');

    const sendCheckboxData = async (eventId, productId) => {
      try {
        await fetch('/AddEvent/addProduct', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify({ eventId, productId })
        });
      } catch (error) {
        throw error;
      }
    };

    const promises = [];

    checkboxes.forEach(checkbox => {
      if (checkbox.checked) {
        const productId = checkbox.value; // 勾選的產品ID
        promises.push(sendCheckboxData(eventId, productId)); // 傳送事件ID和產品ID
      }
    });

    const data = await response.text();

    if (response.ok) {
      alert(data); // 註冊成功
      window.location.replace('add_coupon.html');
    } else {
      alert(data); // 註冊失敗，顯示錯誤訊息
    }

});

