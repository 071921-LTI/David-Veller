let token = sessionStorage.getItem("token");

if(!token){
    window.location.href="login.html";
}

let amount = document.getElementById("amount");
let description = document.getElementById("description");
let type = document.getElementById("type");
let success = document.getElementById("success");
let error = document.getElementById("error");

document.getElementById('submit').addEventListener('click', submit);

function submit() {
    
    type = type.value;

    let typeId = 1;
    switch(type){
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
        type: reimbType
    }

    if (isNaN(reimb.amount)){
        error.innerHTML = "Please enter a valid amount."
    }else{

        let url = "http://localhost:8080/project1/reimb/request";

        let xhr = new XMLHttpRequest();

        xhr.open("POST", url);

        xhr.onreadystatechange = function(){

            if (xhr.readyState === 4 && xhr.status >= 200 && xhr.status < 300){
                success.innerHTML = "Request made!";

                setTimeout(function(){window.location.href = 'view.html';}, 500);

            }else if (xhr.readyState === 4){
                error.innerHTML = "Something went wrong"
            }

        }

        xhr.setRequestHeader("Authorization", token);
        xhr.send(JSON.stringify(reimb));


    }
}