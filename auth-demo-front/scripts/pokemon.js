

let token = sessionStorage.getItem("token");


if(!token){
    window.location.href="login.html";
}

document.getElementById("submit").addEventListener("click", getpoke);

async function getpoke(){

    console.log(token);


    let myHeaders = new Headers();
    myHeaders.append('Authorize', token);
    myHeaders.append("Access-Control-Request-Headers", "Authorize");

    let id = document.getElementById("pokenum").value;

    let pokemon = {
        id: id
    }

    let url = "http://localhost:8080/auth-demo/pokemons";

    if (id != 0){
        url += `/${id}`;
    }

    
    var reponse = await fetch(url, {
        headers: myHeaders,
    });

    if(response.status >= 200 && response.status < 300){
        let data = await response.json();
        document.getElementById('data').innerHTML(data);
    } else{
        console.log('Unable to retrieve data.')
    }

}