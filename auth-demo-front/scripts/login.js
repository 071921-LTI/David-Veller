document.getElementById("submitButton").addEventListener("click", login);

function login(){


    let username = document.getElementById("username").value;
    let password = document.getElementById("password").value;

    let user = {
        username: username,
        password: password
    }

    let xhr = new XMLHttpRequest();
    
    xhr.open("POST", "http://localhost:8080/auth-demo/auth/login");

    xhr.onreadystatechange = function(){
        if(xhr.readyState === 4 && xhr.status === 200){
            let authToken = xhr.getResponseHeader("Authorize");
            
            sessionStorage.setItem("token", authToken);

            window.location.href="pokemon.html";

        } else if (xhr.readyState === 4){
            console.log('Something went wrong...');
        }
    } 


    //xhr.setRequestHeader("Access-Control-Allow-Origin", "*")
    /*
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.setRequestHeader("Accept", "application/json");
    */
    //xhr.setRequestHeader("Access-Control-Allow-Headers", "Authorize");
    let requestBody = JSON.stringify(user);
    xhr.send(requestBody);
}