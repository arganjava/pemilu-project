var requestProvinsi = new XMLHttpRequest();
requestProvinsi.open('GET', 'https://pemilu2019.kpu.go.id/static/json/wilayah/0.json', true);
requestProvinsi.send();
requestProvinsi.onload = function() {
    var dataProvinsi = JSON.parse(this.response)

    Object.keys(dataProvinsi).forEach(function (keyProv) {
            console.info(keyProv, dataProvinsi[keyProv]['nama']);
    });
}