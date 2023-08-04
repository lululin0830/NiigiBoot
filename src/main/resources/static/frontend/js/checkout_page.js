const createOrderBtn = document.querySelector("#createOrder")


function createOrder() {

    const memberId = 'M000000001';
    const itemList = [
        {
            'productSpecId': '10000001001',
            'price': 250,
            'eventPrice': 250,
            'eventId': '2023072000000001'
        },
        {
            'productSpecId': '10000020001',
            'price': 900,
            'eventPrice': 900,
            'eventId': '2023072000000003'
        }];

    const totalAmount = 1150;
    const pointsDiscount = 100;
    const couponDiscount = 200;
    const paymentType = '1';
    const recipient = '王小明';
    const phoneNum = '0912-123-456';
    const deliveryAddress = '104 台北市 中山區 南京東路三段219號5樓';

    const body = {
        'memberId': 'M000000001',
        // 'itemList': [
        //     {
        //         'productSpecId': '10000001001',
        //         'price': 250,
        //         'eventPrice': 250,
        //         'eventId': ['2023072000000001']
        //     },
        //     {
        //         'productSpecId': '10000020001',
        //         'price': 900,
        //         'eventPrice': 900,
        //         'eventId': ['2023072000000003']
        //     },],
        // 'totalAmount': 1150,
        'pointsDiscount': 100,
        'couponDiscount': 200,
        'paymentType': '1',
        'recipient': '王小明',
        'phoneNum': '0912-123-456',
        'deliveryAddress': '104 台北市 中山區 南京東路三段219號5樓',
    };

    console.log(body)

    fetch('http://localhost:8080/Niigi/CreateOrder', {
        method: 'post',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(body)

    })
        .then(function (r) {
            console.log("回來了")
            console.log(r);
            return r.text(); // 解析回應的純文字
        })
        .then(function (data) {
            console.log(data); // 處理回應的純文字資料
            alert(data);
        })
        .catch(function (error) {
            console.error(error); // 處理錯誤
        });


}

function init() {
    createOrderBtn.addEventListener("click", createOrder);
}

window.addEventListener("load", init);