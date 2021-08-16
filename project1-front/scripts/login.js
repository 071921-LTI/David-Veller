document.getElementById('submit').addEventListener('click', login);
sessionStorage.removeItem('token');

function login(){

    let username = document.getElementById('username').value;
    let password = document.getElementById('password').value;

    let user = {
        username: username,
        password: password
    }

    let url = 'http://localhost:8080/project1/auth/login';

    let xhr = new XMLHttpRequest();

    xhr.open("POST", url);

    xhr.onreadystatechange = function(){
        if (xhr.readyState === 4 && xhr.status >= 200 && xhr.status < 300){

            let authToken = xhr.getResponseHeader("Authorization");

            sessionStorage.setItem('token', authToken);

            window.location.href = 'view.html';

        } else if (xhr.readyState === 4){
            let error = xhr.getResponseHeader("Authorization");
            let errorField = document.getElementById('error');

            if (error == "username"){
                errorField.innerHTML = 'Wrong username';
                document.getElementById("loginForm").reset();
            } else if (error == "password"){
                errorField.innerHTML = 'Wrong password';
                document.getElementById("loginForm").reset();
            }
        }
    }

    xhr.send(JSON.stringify(user));

    
}