let token = sessionStorage.getItem("token");

if(!token){
    window.location.href="login.html";
}


function getReimbs() {

    let xhr = new XMLHttpRequest();

    let url = "http://localhost:8080/project1/reimb/view";

    xhr.open("GET", url);

    xhr.setRequestHeader("Authorization", token);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status >= 200 && xhr.status < 300) {
            let response = xhr.responseText;
            response = JSON.parse(response);
            let tbody = document.getElementById("tableBody");

            for (i = response.length-1; i >= 0; i--) {
                let tr = document.createElement('tr');
                let subBy = document.createElement('td');
                let amount = document.createElement('td');
                let timeSub = document.createElement('td');
                let timeRes = document.createElement('td');
                let resBy = document.createElement('td');
                let status = document.createElement('td');
                let type = document.createElement('td');
                subBy.innerHTML = response[i].author.firstName + " " + response[i].author.lastName;
                amount.innerHTML = response[i].amount;
                timeSub.innerHTML = getTimestamp(response[i].submitted);
                timeRes.innerHTML = getTimestamp(response[i].resolved);
                resBy.innerHTML = getResolver(response[i].resolver);
                status.innerHTML = response[i].status.status;
                type.innerHTML = response[i].type.type;
                tr.appendChild(subBy);
                tr.appendChild(amount);
                tr.appendChild(timeSub);
                tr.appendChild(timeRes);
                tr.appendChild(resBy);
                tr.appendChild(status);
                tr.appendChild(type);
                tbody.appendChild(tr);
            }



        } else if (xhr.readyState === 4) {
            console.log("Something went wrong");
        }
    }

    xhr.send();

}

function getResolver(resolver) {
    if (resolver == null) {
        return "Not Resolved";
    } else {
        return resolver.firstName + " " + resolver.lastName;
    }
}

function getTimestamp(timestamp) {
    if (timestamp == null) {
        return "Not Resolved";
    } else {
        var d = new Date(timestamp);
        var formattedDate = d.getMonth() + "-" + d.getDate() + "-" + d.getFullYear();
        var hours = (d.getHours() < 10) ? "0" + d.getHours() : d.getHours();
        if (hours > 12) { hours -= 12; }
        if (hours === 0) { hours = 12; }
        var minutes = (d.getMinutes() < 10) ? "0" + d.getMinutes() : d.getMinutes();
        var formattedTime = hours + ":" + minutes;
        formattedDate = formattedDate + " " + formattedTime;
        return formattedDate;
    }
}

getReimbs();
