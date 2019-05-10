function automate(noWilayah) {

    var urlWilayah = "https://pemilu2019.kpu.go.id/static/json/wilayah/0.json";
    var urlAppend = "https://pemilu2019.kpu.go.id/static/json/wilayah";
    var urlTPSAppend = "https://pemilu2019.kpu.go.id/static/json/hhcw/ppwp";
    var jsonString = ".json";
    var no = 0;


    var requestProvinsi = new XMLHttpRequest();
    requestProvinsi.open('GET', urlWilayah, true);
    requestProvinsi.send();
    requestProvinsi.onload = function () {
        var dataProvinsi = JSON.parse(this.response)
        Object.keys(dataProvinsi).forEach(function (keyProv, idxProv) {
            if (keyProv === noWilayah) {
                console.info(keyProv, dataProvinsi[keyProv]['nama']);
                var urlKotas = urlAppend + "/" + keyProv;
                var requestKota = new XMLHttpRequest();
                requestKota.open('GET', urlKotas + jsonString, true);
                requestKota.send();
                requestKota.onload = function () {
                    var dataKota = JSON.parse(this.response)
                    Object.keys(dataKota).forEach(function (keyKota, idxKota) {
                        console.info(keyKota, dataKota[keyKota]['nama']);

                        var urlKecamatan = urlAppend + "/" + keyProv + "/" + keyKota;
                        var requestKecamatan = new XMLHttpRequest();
                        requestKecamatan.open('GET', urlKecamatan + jsonString, true);
                        requestKecamatan.send();
                        requestKecamatan.onload = function () {
                            var dataKecamatan = JSON.parse(this.response)
                            Object.keys(dataKecamatan).forEach(function (keyKecamatan, idxKecamatana) {
                                console.info(keyKecamatan, dataKecamatan[keyKecamatan]['nama']);

                                var urlKelurahan = urlAppend + "/" + keyProv + "/" + keyKota + "/" + keyKecamatan;
                                var requestKelurahan = new XMLHttpRequest();
                                requestKelurahan.open('GET', urlKelurahan + jsonString, true);
                                requestKelurahan.send();
                                requestKelurahan.onload = function () {
                                    var dataKelurahan = JSON.parse(this.response)
                                    Object.keys(dataKelurahan).forEach(function (keyKelurahan, idxKelurahan) {
                                        console.info(keyKelurahan, dataKelurahan[keyKelurahan]['nama']);
                                        var urlTps = urlAppend + "/" + keyProv + "/" + keyKota + "/" + keyKecamatan + "/" + keyKelurahan;
                                        var provinsi = dataProvinsi[keyProv]['nama'];
                                        var kota = dataKota[keyKota]['nama']
                                        var kecamatan = dataKecamatan[keyKecamatan]['nama'];
                                        var kelurahan = dataKelurahan[keyKelurahan]['nama'];
                                        console.log("calling ", provinsi, kota, kecamatan, kelurahan, urlTps + jsonString)

                                        var dataUpdate = {
                                            "voteId": "5cc26782b1432c0316af7639",
                                            "provinsi": "jabar",
                                            "kota": "bandung",
                                            "kecamatan": "dago",
                                            "kelurahan": "sekeloa",
                                            "url": "https://aaa/aaa/aaa/11/22/33/44/555.json", 
                                            "urlTpss": ["https://feff/fefe/11/22/33/44.json", "https://feff/fefe/11/22/33/44.json"],
                                            "tpsList": [{"ts": "2019-04-26 02:45:05", "chart": {"21": 207, "22": 9}, "images": ["1-53258-01-C-XXX-X5.jpg", "1-53258-01-C-XXX-X6.jpg"], "pemilih_j": 267, "suara_sah": 216, "pengguna_j": 221, "suara_total": 221, "suara_tidak_sah": 5},
                                            {"ts": "2019-04-26 02:45:05", "chart": {"21": 207, "22": 9}, "images": ["1-53258-01-C-XXX-X5.jpg", "1-53258-01-C-XXX-X6.jpg"], "pemilih_j": 267, "suara_sah": 216, "pengguna_j": 221, "suara_total": 221, "suara_tidak_sah": 5}] }
                                        fetch('http://localhost:8102/vote/update', {
                                            method: 'POST',
                                            body: JSON.stringify(dataUpdate), // data can be `string` or {object}!
                                            headers:{
                                                'Content-Type': 'application/json'
                                            }
                                        }).then(res => res)
                                            .then(response => console.log('Success:', JSON.stringify(response)))
                                            .catch(error => console.error('Error:', error));
                                    })
                                }

                            })
                        }
                    })
                }
            }
        })
    }
}
