let token = sessionStorage.getItem("token");

if(!token){
    window.location.href="login.html";
}

document.getElementById("status").addEventListener('change', filterAll);
document.getElementById("type").addEventListener('change', filterAll);
document.getElementById("submitted").addEventListener('keyup', filterAll);
document.getElementById("resolved").addEventListener('keyup', filterAll);
document.getElementById("viewempl").addEventListener('click', openEmployees);

checkManager();
getReimbs();

function checkManager() {

    if (token.split(":")[1] == "manager") {
        document.getElementById("viewempl").style.display = "";
    }

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
                tr.style.display = "";
                tr.onclick = openReimb;
                tr.className = "clickRows";
                tr.setAttribute("id", response[i].reimbId);
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

function openReimb(event){
    sessionStorage.setItem("reimbId",(event.target.parentElement.id));
    windowRef = window.open("reimb.html",null, "location=no");
}

function openEmployees(){
    windowRef = window.open("employees.html",null, "location=no");
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

function filterAll(){
    let status = document.getElementById("status").value.toUpperCase();
    let type = document.getElementById("type").value.toUpperCase();
    let submitted = document.getElementById("submitted").value.toUpperCase();
    let resolved = document.getElementById("resolved").value.toUpperCase();

    let table = document.getElementById("reimbTable");
    let rows = table.getElementsByTagName("tr");
    for (i=1; i < rows.length;i++){
        rows[i].style.display = "";

        let subData = rows[i].getElementsByTagName("td")[0].innerHTML.toUpperCase();
        let resData = rows[i].getElementsByTagName("td")[4].innerHTML.toUpperCase();
        let statusData = rows[i].getElementsByTagName("td")[5].innerHTML.toUpperCase();
        let typeData = rows[i].getElementsByTagName("td")[6].innerHTML.toUpperCase();

        if ("RESOLVED" == status){
            if (statusData == "PENDING"){
                rows[i].style.display = "none"; 
            }
        } else if(status != statusData && status != '--'){
            rows[i].style.display = "none";
        }

        if(type != typeData && type != '--'){
            rows[i].style.display = "none";
        }

        if(!(subData.indexOf(submitted) > -1)){
            rows[i].style.display = "none";
        }
        
        if(!(resData.indexOf(resolved) > -1)){
            rows[i].style.display = "none";
        }
    }
}
