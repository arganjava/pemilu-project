fetch('http://localhost:1000/vote/list?collection=jabar',).then(res => res)
    .then(response => {
        console.info(response)

    })
    .catch(error => console.error('Error:', error));


var data = {
    "voteId": "5cc26782b1432c0316af7639",
    "provinsi": "jabar",
    "kota": "bandung",
    "kecamatan": "dago",
    "kelurahan": "sekeloa",
    "url": "https://pemilu2019.kpu.go.id/static/json/wilayah/25823/25989/25998/25999.json",
    "_class": "com.journaldev.bootifulmongodb.model.Vote",
    "urlTpss": ["8", "0"]
}


function getUrlTPS(provinsi) {
    let requestCollection = new XMLHttpRequest();
    requestCollection.open('GET', 'http://localhost:2000/vote/list?collection=' + provinsi, true);
    requestCollection.send();
    let count = 0;
    requestCollection.onload = function () {
        let data = JSON.parse(this.response)
        for (i = 0; i < data.length; i++) {
            let voteId = data[i]['voteId'];
            let kota = data[i]['kota'];
            let provinsi = data[i]['provinsi'];
            let kecamatan = data[i]['kecamatan'];
            let kelurahan = data[i]['kelurahan'];
            let url = data[i]['url'];

            // console.log(voteId, url)
            let requestTps = new XMLHttpRequest();
            requestTps.open('GET', url, true);
            requestTps.send();
            requestTps.onload = function () {
                console.log('count hit', count++)
                let dataTps = JSON.parse(this.response)
                let dataUpdate = {
                    "voteId": voteId,
                    "provinsi": provinsi,
                    "kota": kota,
                    "kecamatan": kecamatan,
                    "kelurahan": kelurahan,
                    "url": url,
                    "urlTpss": Array.from(Object.keys(dataTps))
                }
                fetch('http://localhost:2000/vote/update', {
                    method: 'POST',
                    body: JSON.stringify(dataUpdate), // data can be `string` or {object}!
                    headers: {
                        'Content-Type': 'application/json'
                    }
                }).then(res => res)
                    .then(response => console.info('update'))
                    .catch(error => console.error('Error:', error));
                //Array.from(dataTps.keys())
            }
        }
    };
}


function getUrlTPSNew(provinsi) {
    let requestCollection = new XMLHttpRequest();
    requestCollection.open('GET', 'http://localhost:2000/vote/list-detail?collection=' + provinsi, true);
    requestCollection.send();
    let count = 0;
    requestCollection.onload = function () {
        let data = JSON.parse(this.response)
        for (i = 0; i < data.length; i++) {
            let voteId = data[i]['voteId'];
            let kota = data[i]['kota'];
            let provinsi = data[i]['provinsi'];
            let kecamatan = data[i]['kecamatan'];
            let kelurahan = data[i]['kelurahan'];
            let url = data[i]['url'];

            // console.log(voteId, url)
            let requestTps = new XMLHttpRequest();
            requestTps.open('GET', url, true);
            requestTps.send();
            requestTps.onload = function () {
                let dataTps = JSON.parse(this.response)
                if (dataTps !== undefined) {
                    let dataUpdate = {
                        "voteId": voteId,
                        "provinsi": provinsi,
                        "kota": kota,
                        "kecamatan": kecamatan,
                        "kelurahan": kelurahan,
                        "url": url,
                       // "urlTpss": Array.from(Object.keys(dataTps)),
                        "detailTpsMap": dataTps
                    }
                    count++;
                    console.log('count', count, "of", data.length);
                    fetch('http://localhost:2000/vote/update-detail', {
                        method: 'POST',
                        body: JSON.stringify(dataUpdate), // data can be `string` or {object}!
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    }).then(res => res)
                        .then(response => {
                            console.log(response.status)
                            response = null;
                            dataUpdate = null;
                            dataTps =null;
                        })
                        .catch(error => {
                            console.error('Error:', error)
                            error = null;
                            dataUpdate = null;
                            dataTps = null;
                        });
                }
            }
        }
    };
}


function loopBli(cou) {
    let count = 0;
    for (i = 0; i < cou; i++) {
        fetch('https://www.blibli.com/backend/search/autocomplete?searchTermPrefix=baju',).then(res => res)
            .then(response => {
                console.info(count++)
                console.info(response)
            })
            .catch(error => console.error('Error:', error));
    }

}
