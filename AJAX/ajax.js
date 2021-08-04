// document.getElementById('getData').onclick = getData;
document.getElementById('getData').addEventListener("click", getData);

let apiURL = 'https://pokeapi.co/api/v2/pokemon/';

function getData() {

    let userInput = document.getElementById('dataInput').value;

    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = receiveData;

    xhr.open('GET', apiURL + userInput);

    xhr.send();

    function receiveData(){
        if(xhr.readyState === 4 && xhr.status >= 200 && xhr.status < 300){
            let response = xhr.responseText;
            response = JSON.parse(response);
            populateData(response);
        }
    }
  
}

function populateData(response) {
    console.log(response);
    console.log(response.abilities[0].ability.name);
    
    let name = response.species.name;
    let abilitiesString = '<p style="margin-left: 40px">';
    for (let i = 0; i < response.abilities.length; i++){
        abilitiesString += response.abilities[i].ability.name;
        abilitiesString += '<br>';
    }
    abilitiesString += '</p>';
    
    let experience = response.base_experience;
    let height = response.height;
    let weight = response.weight;


    let dataSection = document.getElementById("data");

    dataSection.innerHTML=`Name: ${name} <br>
    Abilities: 
    ${abilitiesString}
    Base Experience: ${experience} <br>
    Height: ${height} <br>
    Weight: ${weight} <br>`;

}