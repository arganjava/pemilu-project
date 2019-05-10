var requestProvinsi = new XMLHttpRequest();
    requestProvinsi.open('GET', 'https://pemilu2019.kpu.go.id/static/json/wilayah/0.json', true);
    requestProvinsi.send();
    requestProvinsi.onload = function () {
        var dataProvinsi = JSON.parse(this.response)
        Object.keys(dataProvinsi).forEach(function (keyProv, idxProv) {
            console.info(keyProv, dataProvinsi[keyProv]['nama']);

            fetch('http://localhost:2000/tps/generate?collection='+dataProvinsi[keyProv]['nama'],).then(res => res)
            .then(response => {
                console.info(response)
            })
            .catch(error => console.error('Error:', error));

        });
    }