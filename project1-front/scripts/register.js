document.getElementById('submit').addEventListener('click', register);

function register(){
    let username = document.getElementById('username').value;
    let password = document.getElementById('password').value;
    let first = document.getElementById('first').value;
    let last = document.getElementById('last').value;
    let email = document.getElementById('email').value;
    let role = getRole();

    let userRole = {
        roleId: 1,
        role: role
    }

    if (role == "employee"){
        userRole.roleId = 2;
    }

    let user = {
        username: username,
        password: password,
        firstName: first,
        lastName: last,
        email: email,
        role: userRole
    }

    let url = 'http://localhost:8080/project1/auth/register'

    let xhr = new XMLHttpRequest();

    xhr.open("POST", url);

    xhr.onreadystatechange = function(){
        if (xhr.readyState === 4 && xhr.status >= 200 && xhr.status < 300){

            let authToken = xhr.getResponseHeader("Authorization");

            sessionStorage.setItem('token', authToken);

            window.location.href = 'dashboard.html';
        }else if (xhr.readyState === 4){
            let error = xhr.getResponseHeader("Authorization");
            let errorField = document.getElementById('error');
            if (error == "username"){
                errorField.innerHTML = 'Username or email has been taken';
                //document.getElementById("loginForm").reset();
            } else if (error == "role"){
                errorField.innerHTML = 'Invalid role';
                //document.getElementById("loginForm").reset();
            }
        }
    }

    let reqBody = JSON.stringify(user);

    console.log(reqBody);

    xhr.send(reqBody);
}

function getRole(){
    let buttons = document.getElementsByName("role");

    for (i = 0;i<buttons.length;i++){
        if (buttons[i].checked){
            return buttons[i].value;
        }
    }
    return "noRole";
}