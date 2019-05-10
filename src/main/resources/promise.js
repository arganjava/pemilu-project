const FETCH_TIMEOUT = 5000;
let didTimeOut = false;

new Promise(function(resolve, reject) {
    const timeout = setTimeout(function() {
        didTimeOut = true;
        reject(new Error('Request timed out'));
    }, FETCH_TIMEOUT);

    fetch('https://pemilu2019.kpu.go.id/static/json/wilayah/0.json')
        .then(function(response) {
            // Clear the timeout as cleanup
            clearTimeout(timeout);
            if(!didTimeOut) {
                console.log('fetch good! ', response);
                resolve(response);
            }
        })
        .catch(function(err) {
            console.log('fetch failed! ', err);

            // Rejection already happened with setTimeout
            if(didTimeOut) return;
            // Reject with error
            reject(err);
        });
})
    .then(function() {
        // Request success and no timeout
        console.log('good promise, no timeout! ');
    })
    .catch(function(err) {
        // Error: response error, request timeout or runtime error
        console.log('promise error! ', err);
    });

fetch('https://ace.tokopedia.com/universe/v9?callback=callback&q=laptop&unique_id=9d0cb06fb2c54d699fde16e9dde9e566&source=search&device=desktop&user_id=0&official=false&safe_search=true&_=1556378475004')
    .then(function(response) {
        return response.json();
    })
    .then(function(myJson) {
        console.log(JSON.stringify(myJson));
    });

fetch('https://pemilu2019.kpu.go.id/static/json/wilayah/0.json').then(res => res.json())
    .then(response => console.log('Success:', JSON.stringify(response)))
    .catch(error => console.error('Error:', error));


var vount = 0;
for(i =0 ; i < 100000; i++){
    fetch('https://ace.tokopedia.com/universe/v9?callback=callback&q=laptop&unique_id=9d0cb06fb2c54d699fde16e9dde9e566&source=search&device=desktop&user_id=0&official=false&safe_search=true&_=1556378475004')
    .then(function(response) {
        return console.info(response);
        console.log(vount++)
    })
    .then(function(myJson) {
        console.log(myJson);
    });
}
