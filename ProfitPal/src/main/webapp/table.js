username = "test";

window.onload = function() {
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'LoadItemsServlet', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onload = function(){
        if(this.status == 200){
            response = JSON.parse(this.responseText);
            console.log(response);
            for(let i = 0; i < response.length; i++){
                container = document.getElementById(response[i].item + 'Spending');
                containter.value = response[i].value;
            }
        }
    }
    xhr.send();
}