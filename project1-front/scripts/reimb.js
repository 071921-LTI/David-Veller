let token = sessionStorage.getItem("token");

if (!token) {
    window.location.href = "login.html";
}

document.getElementById("approve").addEventListener('click', approve);
document.getElementById("deny").addEventListener('click', deny);

getReimb();
let approvedReimb;
let deniedReimb;

function approve() {



        let xhr = new XMLHttpRequest();

        let url = "http://localhost:8080/project1/reimb/update";

        xhr.open("PUT", url);

        xhr.setRequestHeader("Authorization", token);

        xhr.onreadystatechange = function () {

            if (xhr.readyState === 4 && xhr.status >= 200 && xhr.status < 300) {
                //window.close();
                console.log(approvedReimb);
            } else if (xhr.readyState === 4) {
                console.log("Something went wrong");
            }

        }

        xhr.send(JSON.stringify(approvedReimb));


}

function deny() {


    let xhr = new XMLHttpRequest();

    let url = "http://localhost:8080/project1/reimb/update";

    xhr.open("PUT", url);

    xhr.setRequestHeader("Authorization", token);

    xhr.onreadystatechange = function () {

        if (xhr.readyState === 4 && xhr.status >= 200 && xhr.status < 300) {
            window.close();
        } else if (xhr.readyState === 4) {
            console.log() = "Something went wrong"
        }

    }

    xhr.send(JSON.stringify(deniedReimb));

}

function checkManager() {

    if (token.split(":")[1] == "manager") {
        document.getElementById("manageroptions").style.display = "";
    }

}

function getReimb() {

    let xhr = new XMLHttpRequest();

    let url = "http://localhost:8080/project1/reimb/view/";

    let reimbId = sessionStorage.getItem("reimbId");

    xhr.open("GET", url + reimbId);

    xhr.setRequestHeader("Authorization", token);

    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status >= 200 && xhr.status < 300) {
            let response = xhr.responseText;
            response = JSON.parse(response);
            let tbody = document.getElementById("tbody");

            let tr = document.createElement('tr');
            let descp = document.createElement('td');
            let receipt = document.createElement('td');
            descp.innerHTML = response.description;
            tr.appendChild(descp);
            tr.appendChild(receipt);
            tbody.appendChild(tr);
            //approveReimb(response);
            let reimbStatus = {
                statusId : 2,
                status: "approved"
            }

            approvedReimb = response;
            approvedReimb.status = reimbStatus;
            console.log("this is in get reimb");
            console.log(approvedReimb);
            denyReimb(response);
            console.log("after deny");
            console.log(approvedReimb);


        } else if (xhr.readyState === 4) {
            console.log("Something went wrong");
        }
    }

    xhr.send();

}

function approveReimb(reimb){

    console.log("in approve" + reimb.status.status);

    approvedReimb = reimb;

    let reimbStatus = {
        statusId : 2,
        status: "approved"
    }

    approvedReimb.status = reimbStatus;

    console.log("set status" + approvedReimb.status.status);

}

function denyReimb(reimb){

    deniedReimb = reimb;

    let reimbStatus = {
        statusId : 3,
        status: "denied"
    }

    deniedReimb.status = reimbStatus;

}

checkManager();
/*
window.unload = refreshParent;
function refreshParent(){
    window.opener.location.reload();
}*/