username = "test";

window.onload = function() {
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'LoadItemsServlet', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onload = function(){
        if(this.status == 200){
            console.log(this.responseText);
            response = JSON.parse(this.responseText);
            console.log(response);
            for(let category in response){
                catString = category.toString();
                container = document.getElementById(catString + 'Spending');
                container.value = response[category];
            }
        }
    }
    xhr.send();
}