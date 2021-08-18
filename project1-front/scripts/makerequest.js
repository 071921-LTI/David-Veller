let token = sessionStorage.getItem("token");

if (!token) {
    window.location.href = "login.html";
}

let amount = document.getElementById("amount");
let description = document.getElementById("description");
let receipt = document.getElementById("receipt");
let type = document.getElementById("type");
let success = document.getElementById("success");
let error = document.getElementById("error");

receipt.addEventListener('change', fileCheck);
let receiptImg;

document.getElementById('submit').addEventListener('click', submit);

function fileCheck() {
    let file = receipt.files[0];
    var reader = new FileReader();
    reader.onload = function () {

        var arrayBuffer = this.result,
            array = new Uint8Array(arrayBuffer),
            binaryString = String.fromCharCode.apply(null, array);
            receiptImg = toUTF8Array(binaryString);
            //document.getElementById("output").src = 'data:image/jpeg;base64,' + btoa(binaryString);
            console.log(typeof(receiptImg));
    }
    reader.readAsArrayBuffer(file);
}

function submit() {

    type = type.value;

    let typeId = 1;
    switch (type) {
        case "Lodging":
            typeId = 1;
            break;
        case "Travel":
            typeId = 2;
            break;
        case "Food":
            typeId = 3;
            break;
        case "Other":
            typeId = 4;
            break;
    }

    reimbType = {
        typeId: typeId,
        type: type
    }

    reimb = {
        amount: parseFloat(amount.value).toFixed(2),
        description: description.value,
        receipt: receiptImg,
        type: reimbType
    }

    if (isNaN(reimb.amount)) {
        error.innerHTML = "Please enter a valid amount."
    } else {

        let url = "http://localhost:8080/project1/reimb/request";

        let xhr = new XMLHttpRequest();

        xhr.open("POST", url);

        xhr.onreadystatechange = function () {

            if (xhr.readyState === 4 && xhr.status >= 200 && xhr.status < 300) {
                success.innerHTML = "Request made!";

                setTimeout(function () { window.location.href = 'view.html'; }, 500);

            } else if (xhr.readyState === 4) {
                error.innerHTML = "Something went wrong"
            }

        }

        xhr.setRequestHeader("Authorization", token);
        xhr.send(JSON.stringify(reimb));


    }
}

function toUTF8Array(str) {
    var utf8 = [];
    for (var i=0; i < str.length; i++) {
        var charcode = str.charCodeAt(i);
        if (charcode < 0x80) utf8.push(charcode);
        else if (charcode < 0x800) {
            utf8.push(0xc0 | (charcode >> 6), 
                      0x80 | (charcode & 0x3f));
        }
        else if (charcode < 0xd800 || charcode >= 0xe000) {
            utf8.push(0xe0 | (charcode >> 12), 
                      0x80 | ((charcode>>6) & 0x3f), 
                      0x80 | (charcode & 0x3f));
        }
        // surrogate pair
        else {
            i++;
            // UTF-16 encodes 0x10000-0x10FFFF by
            // subtracting 0x10000 and splitting the
            // 20 bits of 0x0-0xFFFFF into two halves
            charcode = 0x10000 + (((charcode & 0x3ff)<<10)
                      | (str.charCodeAt(i) & 0x3ff));
            utf8.push(0xf0 | (charcode >>18), 
                      0x80 | ((charcode>>12) & 0x3f), 
                      0x80 | ((charcode>>6) & 0x3f), 
                      0x80 | (charcode & 0x3f));
        }
    }
    return utf8;
}