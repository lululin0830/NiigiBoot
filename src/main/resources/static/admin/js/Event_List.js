
const search = function () {

    console.log("進來了");

    const searchdata = JSON.stringify({
        searchcase: document.getElementById("Searchcase").value,
        searchway: document.getElementById("SearchSelect").value,
        StartDate: document.getElementById("StartDate").value,
        EndDate: document.getElementById("EndDate").value,
    })

    fetch('http://localhost:8080/Niigi/EventSingleThreshold', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: searchdata
    }).then(r => r.json()).then(data => {
        const tbody = document.querySelector('#searchResult');
        tbody.innerHTML = "";
        data.forEach(element => {

            const row = `<tr>
                <td> ${element.eventId}</td>
                <td> ${element.eventRegisterSupplier}</td>
                <td> ${element.eventName}</td>
                <td> ${element.eventInfo}</td>
                <td> ${element.eventStart}</td>
                <td> ${element.eventEnd}</td>
                <td> ${element.thresholdType}</td>
                <td> ${element.minPurchaseQuantity}</td>
                <td> ${element.minPurchaseAmount}</td>
                <td> ${element.discountRate}</td>
                <td> ${element.giftProductSpecId}</td>
                    </tr>`;
            let rowData = "<tr>"

            tbody.innerHTML += row;

            console.log(data)
        });

    })




}
console.log("讀到了")
document.getElementById("search").addEventListener("click", search);

const init = function () {
    fetch('http://localhost:8080/EventSingleThreshold', {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        }

    }).then(r => r.json()).then(data => {
        const tbody = document.querySelector('#searchResult');
        tbody.innerHTML = "";
        console.log(data)

        data.forEach(element => {

            const row = `<tr>
                <td> ${element.eventId}</td>
                <td> ${element.eventRegisterSupplier}</td>
                <td> ${element.eventName}</td>
                <td> ${element.eventInfo}</td>
                <td> ${element.eventStart}</td>
                <td> ${element.eventEnd}</td>
                <td> ${element.thresholdType}</td>
                <td> ${element.minPurchaseQuantity}</td>
                <td> ${element.minPurchaseAmount}</td>
                <td> ${element.discountRate}</td>
                <td> ${element.giftProductSpecId}</td>                
            </tr>`;
            let rowData = "<tr>"

            tbody.innerHTML += row;

            console.log(data)
        });

    })
}
// window.addEventListener("load", init);
init();
