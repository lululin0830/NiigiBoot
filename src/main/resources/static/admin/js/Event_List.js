
const search = function () {

    console.log("進來了");

    const searchdata = JSON.stringify({
        searchcase: document.getElementById("Searchcase").value,
        searchway: document.getElementById("SearchSelect").value,
        StartDate: document.getElementById("StartDate").value,
        EndDate: document.getElementById("EndDate").value,
    })

    fetch('http://localhost:8080/Niigi/Event', {
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
                <td> ${element.eventId || 'N/A'}</td>
                <td> ${element.eventRegisterSupplier || 'N/A'}</td>
                <td> ${element.eventName || 'N/A'}</td>
                <td> ${element.eventInfo || 'N/A'}</td>
                <td> ${element.eventStart || 'N/A'}</td>
                <td> ${element.eventEnd || 'N/A'}</td>
                <td> ${element.thresholdType || 'N/A'}</td>
                <td> ${element.minPurchaseQuantity || 'N/A'}</td>
                <td> ${element.minPurchaseAmount || 'N/A'}</td>
                <td> ${element.discountRate || 'N/A'}</td>
                <td> ${element.giftProductSpecId || 'N/A'}</td>
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
    fetch('http://localhost:8080/Niigi/Event', {
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
                <td> ${element.eventId || 'N/A'}</td>
                <td> ${element.eventRegisterSupplier || 'N/A'}</td>
                <td> ${element.eventName || 'N/A'}</td>
                <td> ${element.eventInfo || 'N/A'}</td>
                <td> ${element.eventStart || 'N/A'}</td>
                <td> ${element.eventEnd || 'N/A'}</td>
                <td> ${element.thresholdType || 'N/A'}</td>
                <td> ${element.minPurchaseQuantity || 'N/A'}</td>
                <td> ${element.minPurchaseAmount || 'N/A'}</td>
                <td> ${element.discountRate || 'N/A'}</td>
                <td> ${element.giftProductSpecId || 'N/A'}</td>                
            </tr>`;
            let rowData = "<tr>"

            tbody.innerHTML += row;

            console.log(data)
        });

    })
}
// window.addEventListener("load", init);
init();

const startDateInput = document.getElementById('StartDate');
const endDateInput = document.getElementById('EndDate');
const searchCaseInput = document.getElementById('Searchcase');
const searchSelectInput = document.getElementById('SearchSelect');
const clearSearchButton = document.getElementById('clearSearch');

clearSearchButton.addEventListener('click', function() {
  startDateInput.value = '';
  endDateInput.value = '';
  searchCaseInput.value = '';
  searchSelectInput.selectedIndex = 0;
});
