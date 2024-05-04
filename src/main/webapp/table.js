username = "test";


/* window.onload = function() {
	if(localStorage.length != 0){
		console.log("logged in");
		document.getElementById("logoutButton").innerHTML = "Log out";
	} else {
		document.getElementById("logoutButton").innerHTML = "Login";
	}
} */

function logHandler() {
	console.log("hi");
	if(localStorage.length == 0) {
		console.log("logged out");
		window.location.href = "loginUser.html"; 
	} else {
		localStorage.clear();
		window.location.href = "home.html";
	}
}

window.onload = function() {
	if(localStorage.length != 0){
		document.getElementById("logoutButton").innerHTML = "Log out";
	} else {
		document.getElementById("logoutButton").innerHTML = "Login";
	}
	
    var xhr = new XMLHttpRequest();
    xhr.open('POST', 'LoadItemsServlet', true);
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.onload = function(){
        if(this.status == 200){
            console.log(this.responseText);
            response = JSON.parse(this.responseText);
            console.log(response);
            
            document.querySelector("input.groceryInput").value = response.grocery;
            document.querySelector("input.restaurantInput").value = response.restaurant;
            document.querySelector("input.shoppingInput").value = response.shopping;
            document.querySelector("input.gasInput").value = response.gas;
            
            console.log("loaded");
            
        }
    }
    
    let json = {
		user: localStorage.user,
	}
    var data = JSON.stringify(json);

    xhr.send(data);    
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
            
            parentDiv.querySelector("input.groceryInput").value = json.budget.grocery;
            parentDiv.querySelector("input.restaurantInput").value = json.budget.restaurant;
            parentDiv.querySelector("input.shoppingInput").value = json.budget.shopping;
            parentDiv.querySelector("input.gasInput").value = json.budget.gas;
            
            parentDiv.querySelector("td.groceryMonetaryReduction").innerHTML = "$" + json.monetaryGroceryReduction;
            parentDiv.querySelector("td.restaurantMonetaryReduction").innerHTML = "$" + json.monetaryRestaurantReduction;
            parentDiv.querySelector("td.shoppingMonetaryReduction").innerHTML = "$" + json.monetaryShoppingReduction;
            parentDiv.querySelector("td.gasMonetaryReduction").innerHTML = "$" + json.monetaryGasReduction;
            
            parentDiv.querySelector("td.groceryPercentReduction").innerHTML = json.groceryChange + "%";
            parentDiv.querySelector("td.restaurantPercentReduction").innerHTML =  json.restaurantChange + "%";
            parentDiv.querySelector("td.shoppingPercentReduction").innerHTML =  json.shoppingChange + "%";
            parentDiv.querySelector("td.gasPercentReduction").innerHTML =  json.gasChange + "%";
            
            //Added code
			var previousTotalSaved = button.parentElement.querySelector('.total-saved');
			if (previousTotalSaved) {
				previousTotalSaved.remove();
			}
    		var totalSavedDiv = document.createElement('div');
    		totalSavedDiv.className = 'total-saved';
    		totalSavedDiv.innerHTML = "Total Saved: $" + json.totalSaved; // Assuming totalSaved is in dollars
    		button.parentElement.appendChild(totalSavedDiv);
        }
    }
    
    let parentDiv = button.parentElement;
    console.log(localStorage.user);
    let json = {
		grocery: parentDiv.querySelector("input.groceryInput").value,
		restaurant: parentDiv.querySelector("input.restaurantInput").value,
		shopping: parentDiv.querySelector("input.shoppingInput").value,
		gas: parentDiv.querySelector("input.gasInput").value,
		user: localStorage.user,
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
	guestViewButton.addEventListener("click", function(){
		window.location.href = "tableShared.html";
	})
	
	if(localStorage.length != 0){
		setOverlayVisible(false);
	} else {
		setOverlayVisible(true);
	}
	// setOverlayVisible(true);
});

function setOverlayVisible(visible) {
    var overlay = document.querySelector('.overlay');
    overlay.style.display = visible ? 'flex' : 'none';
}
