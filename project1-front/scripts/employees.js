let token = sessionStorage.getItem("token");

if (!token) {
    window.location.href = "login.html";
}


getEmployees();

function getEmployees() {

    let xhr = new XMLHttpRequest();

    let url = "http://localhost:8080/project1/user/employees";

    xhr.open("GET", url);

    xhr.setRequestHeader("Authorization", token);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status >= 200 && xhr.status < 300) {
            let response = xhr.responseText;
            response = JSON.parse(response);
            let tbody = document.getElementById("tbody");

            for (i=0;i<response.length;i++){
                let tr = document.createElement('tr');
                let user = document.createElement('td');
                let first = document.createElement('td');
                let last = document.createElement('td');
                let email = document.createElement('td');
                
                user.innerHTML = response[i].username;
                first.innerHTML = response[i].firstName;
                last.innerHTML = response[i].lastName;
                email.innerHTML = response[i].email;

                tr.appendChild(user);
                tr.appendChild(first);
                tr.appendChild(last);
                tr.appendChild(email);
                tbody.appendChild(tr);
            }

        } else if (xhr.readyState === 4) {
            console.log("Something went wrong");
        }
    }

    xhr.send();

}