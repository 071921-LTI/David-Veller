function getSizes(){
    let color = document.getElementById("sizePicker").value;
    color = color.substring(1);
    let blur = Math.floor(parseInt(color.substring(4), 16) / 25);
    let height = parseInt(color.substring(0,2), 16)*3;
    let width = parseInt(color.substring(2, 4), 16)*3;
    return [height, width, blur];
}


var submit = document.getElementById("submit");
submit.onclick = getData;

var apiURL = 'http://picsum.photos/';

async function getData(){
    let sizes = getSizes();
    let URL = '';

    if (sizes[2] === 0){
        URL = apiURL + sizes[1] + '/' + sizes[0];
    } else{
        URL = apiURL + sizes[1] + '/' + sizes[0] + '/?blur='+ sizes[2];
    }   

    let response = await fetch(URL);

    
    if (response.status >= 200 && response.status <= 300){
        printImg(response);
    }
    else{
        console.log('something went wrong');
    }

}

function printImg(data){

    let imgArea = document.getElementById('data');

    imgArea.innerText = '';

    let img = document.createElement('img');
    img.setAttribute('src', data.url);

    imgArea.appendChild(img);

}