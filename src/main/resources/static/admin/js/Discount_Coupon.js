

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


let eventId = "";
let eventType = "2";
let couponUsedAmount = "0";


const addProduct = function () {

    console.log("進來了");

        const registerSupplier = "S000000001";

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
addProduct();

const registerButton = document.getElementById('send');
registerButton.addEventListener('click', async () => {
    let discountRate = document.getElementById("discountRate").value
    let disrate = discountRate/10;
  const newEvent = JSON.stringify({
      eventId: eventId,
      eventRegisterSupplier: 'S000000001',
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

    const data = await response.text();

    if (response.ok) {
      alert(data); // 註冊成功
      window.location.replace('Discount_Coupon.html');
    } else {
      alert(data); // 註冊失敗，顯示錯誤訊息
    }

});

