
function callEachTpsByProvinsi(provinsi, notGenerateAt, startIdex) {
    let requestCallAllTps = new XMLHttpRequest();
    requestCallAllTps.open('GET', 'http://localhost:2000/tps/list-notInGenerate?collection=' + provinsi + "&notGenerateAt=" + notGenerateAt + "&page=" + startIdex, true);
    requestCallAllTps.send();
    let no = 0;
    console.log("index start", startIdex);
    let dataPerTpses = [];
    requestCallAllTps.onload = function () {
        dataPerTpses = JSON.parse(this.response)
        for (let i = 0; i < dataPerTpses.length; i++) {
            let eachTps = dataPerTpses[i];
            if (eachTps['url'] !== undefined) {
                let requestCallEachTps = new XMLHttpRequest();
                requestCallEachTps.open('GET', eachTps['url'], true);
                requestCallEachTps.send();
                requestCallEachTps.onload = function () {
                    let tpsReal = JSON.parse(this.response)
                    if (tpsReal['ts'] !== undefined) {
                        let result = Object.assign({}, eachTps, tpsReal);
                        result.generateAt = notGenerateAt;
                        fetch('http://localhost:2000/tps/update-tps', {
                            method: 'POST',
                            body: JSON.stringify(result), // data can be `string` or {object}!
                            headers: {
                                'Content-Type': 'application/json'
                            }
                        }).then(res => res).then(response => {
                            console.info(response.status)
                            console.log("index end", startIdex);
                        }).catch(error => {
                                console.error('Error:', error)
                            });
                    }
                }
            }
            no++;
            console.log('count', no, "of", dataPerTpses.length, provinsi, notGenerateAt, startIdex);
            if(no === dataPerTpses.length){
                startIdex++;
                no = 0;
                callEachTpsByProvinsi(provinsi, notGenerateAt, startIdex);
            }
        }
    }
}