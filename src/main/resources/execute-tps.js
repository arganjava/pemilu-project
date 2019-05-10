let startIdex = 0;
let notGenerateAt = ''
let provinsi = ''
function callEachTpsByProvinsi(provinsi, notGenerateAt, startIdex) {
    let requestCallAllTps = new XMLHttpRequest();
    requestCallAllTps.open('GET', 'http://localhost:2000/tps/list-notInGenerate?collection=' + provinsi + "&notGenerateAt=" + notGenerateAt + "&page=" + page, true);
    requestCallAllTps.send();
    let no = 0;
    requestCallAllTps.onload = function () {
        let dataPerTpses = JSON.parse(this.response)
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
                        }).catch(error => {
                                console.error('Error:', error)
                            });
                    }
                }
            }
            no++;
            console.log('count', no, "of", dataPerTpses.length, provinsi, notGenerateAt, startIdex);
            if(no === dataPerTpses.length){
                dataPerTpses = null
                startIdex++;
                callEachTpsByProvinsi(provinsi, notGenerateAt, startIdex);
            }
        }
    }
}