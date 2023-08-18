function eventType(Str) {
    switch (Str) {
      case '1':
        return '商品折價券';
      case '2':
        return '全站折價券';
      case '3':
        return '商品折扣活動';
      case '4':
        return '商品贈品活動';
    }
}

function thresholdType(Str) {
    switch (Str) {
      case '1':
        return '滿額';
      case '2':
        return '滿件';
      case '3':
        return '滿件且滿額';
    }
}


const search = function () {

    console.log("進來了");

    const searchdata = JSON.stringify({
        searchcase: document.getElementById("Searchcase").value,
        searchway: 'eventId',
        StartDate: document.getElementById("StartDate").value,
        EndDate: document.getElementById("EndDate").value,
        RegisterSupplier: supplierId,
    })

    fetch('/EventM', {
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
                <td> ${element.eventName}</td>
                <td> ${element.eventStart}</td>
                <td> ${element.eventEnd}</td>
                <td> ${eventType(element.eventType)}</td>
                <td> ${thresholdType(element.thresholdType)}</td>
                    </tr>`;
            let rowData = "<tr>"

            tbody.innerHTML += row;

            console.log(data)
        });

    })




}
console.log("讀到了")
document.getElementById("search").addEventListener("click", search);

const add = function () {

    const searchdata = JSON.stringify({
        searchcase: document.getElementById("Searchcase").value,
        searchway: 'eventId',
        StartDate: document.getElementById("StartDate").value,
        EndDate: document.getElementById("EndDate").value,
        RegisterSupplier: supplierId,
    })

    fetch('/EventM', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: searchdata
    }).then(r => r.json()).then(data => {
        const tbody = document.querySelector('#searchResult');
        tbody.innerHTML = "";
        console.log(data)
        data.forEach(element => {

            const row = `<tr>
                <td> ${element.eventId}</td>
                <td> ${element.eventName}</td>
                <td> ${element.eventStart}</td>
                <td> ${element.eventEnd}</td>
                <td> ${eventType(element.eventType)}</td>
                <td> ${thresholdType(element.thresholdType)}</td>
                    </tr>`;
            let rowData = "<tr>"

            tbody.innerHTML += row;

            console.log(data)
        });

    })
}

document.addEventListener("coreDone", add);


