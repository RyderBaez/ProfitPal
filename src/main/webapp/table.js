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

function updateAndCalculate(button) {
	 var xhr = new XMLHttpRequest();
    xhr.open('POST', 'ReductionServlet', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onload = function(){
        if(this.status == 200){
			json = JSON.parse(this.responseText);
            let parentDiv = button.parentElement;
            console.log(json);
            
            parentDiv.querySelector("input.groceryInput").value = "$" + json.budget.grocery;
            parentDiv.querySelector("input.restaurantInput").value = "$" + json.budget.restaurant;
            parentDiv.querySelector("input.shoppingInput").value = "$" + json.budget.shopping;
            parentDiv.querySelector("input.gasInput").value = "$" + json.budget.gas;
            
            parentDiv.querySelector("td.groceryMonetaryReduction").innerHTML = "$" + json.monetaryGroceryReduction;
            parentDiv.querySelector("td.restaurantMonetaryReduction").innerHTML = "$" + json.monetaryRestaurantReduction;
            parentDiv.querySelector("td.shoppingMonetaryReduction").innerHTML = "$" + json.monetaryShoppingReduction;
            parentDiv.querySelector("td.gasMonetaryReduction").innerHTML = "$" + json.monetaryGasReduction;
            
            parentDiv.querySelector("td.groceryPercentReduction").innerHTML = json.groceryChange + "%";
            parentDiv.querySelector("td.restaurantPercentReduction").innerHTML =  json.restaurantChange + "%";
            parentDiv.querySelector("td.shoppingPercentReduction").innerHTML =  json.shoppingChange + "%";
            parentDiv.querySelector("td.gasPercentReduction").innerHTML =  json.gasChange + "%";
        }
    }
    
    let parentDiv = button.parentElement;
    let json = {
		grocery: parentDiv.querySelector("input.groceryInput").value.substring(1),
		restaurant: parentDiv.querySelector("input.restaurantInput").value.substring(1),
		shopping: parentDiv.querySelector("input.shoppingInput").value.substring(1),
		gas: parentDiv.querySelector("input.gasInput").value.substring(1),
	}
    var data = JSON.stringify(json);

    xhr.send(data);
}

document.addEventListener("DOMContentLoaded", function(){
	const homeButton = document.getElementById("homeButton");
	const guestViewButton = document.getElementById("guestViewButton");
	const logoutButton = document.getElementById("logoutButton");
	homeButton.addEventListener("click", function(){
		window.location.href = "home.html";
	});
	logoutButton.addEventListener("click", function(){
		// Perform logout functionality (update local storage/cookie)
		window.location.href = "home.html";
	});
})



