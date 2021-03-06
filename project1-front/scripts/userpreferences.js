let token = sessionStorage.getItem("token");

if(!token){
    window.location.href="login.html";
}


let username = document.getElementById("username");
let password = document.getElementById("password");
let first = document.getElementById("first");
let last = document.getElementById("last");
let email = document.getElementById("email");

document.getElementById("update").addEventListener("click", update)
document.getElementById("reset").addEventListener("click", getUser)

let url = "http://localhost:8080/project1/user/prefs";

user = getUser();

function update() {


    let newUser = {
        username: username.value,
        password: password.value,
        firstName: first.value,
        lastName: last.value,
        email: email.value,
    }

    let xhr = new XMLHttpRequest();

    xhr.open('PUT', url);

    xhr.onreadystatechange = function(){
        if (xhr.readyState === 4 && xhr.status >= 200 && xhr.status < 300){

            let authToken = xhr.getResponseHeader("Authorization");

            sessionStorage.removeItem('token');
            sessionStorage.setItem('token', authToken);
            document.getElementById("success").innerHTML = "Update success!";

        }else if (xhr.readyState === 4){
           document.getElementById("error").innerHTML = "There was an error.";
        }
    }

    let reqBody = JSON.stringify(newUser);

    xhr.setRequestHeader("Authorization", token);

    xhr.send(reqBody);


}

function getUser() {

    let xhr = new XMLHttpRequest();

    xhr.open("GET", url);

    xhr.setRequestHeader("Authorization", token);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status >= 200 && xhr.status < 300) {

            let response = xhr.responseText;
            let user = JSON.parse(response);
            setValues(user);

        } else if (xhr.readyState === 4) {
            console.log("Something went wrong");
            return null;
        }
    }

    xhr.send();

}

function setValues(user) {
    username.value = user.username;
    password.value = user.password;
    first.value = user.firstName;
    last.value = user.lastName;
    email.value = user.email;
}
