
    /*
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
    var updatedata = JSON.stringify(json);

    xhr.send(updatedata);
    */
  //document.getElementById('sharedTextbox').value = message;

document.addEventListener('DOMContentLoaded', function() {
	
	var socket = new WebSocket('ws://localhost:8181/ProfitPalTest/SharedServlet');
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
		window.location.href = "table.html";
	});

socket.onopen = function(event) {
        console.log("WebSocket connection opened");
    };
    
socket.onmessage = function(event) {
	
  console.log(event.data);
  const dataRead = JSON.parse((event.data));
  //read the formatted data and rerun the table check to update other values :)
  console.log(dataRead);
  if(dataRead.grocery != null && dataRead.grocery != 'blank'){
    document.querySelector("input.groceryInput").value = dataRead.grocery;
  }
  if(dataRead.restauraunt != null && dataRead.restaurant != 'blank'){
	
    document.querySelector("input.restaurantInput").value = dataRead.restaurant;
    }
   if(dataRead.shopping != null && dataRead.shopping != 'blank'){
	
    document.querySelector("input.shoppingInput").value = dataRead.shopping;
    }
    if(dataRead.gas != null && dataRead.gas != 'blank'){
	
    document.querySelector("input.gasInput").value = dataRead.gas;
    }
    };
    
     socket.onclose = function(event) {
        console.log("WebSocket connection closed");
    };
    
    const Grocery = document.querySelector('input.groceryInput');
    const Restaurant = document.querySelector('input.restaurantInput');
    const Shopping = document.querySelector('input.shoppingInput');
    const Gas = document.querySelector('input.gasInput');
    const Code = document.querySelector('Code');

    Grocery.addEventListener('input', function() {
		console.log('hello');
            //parentDiv.querySelector("input.groceryInput").value = "$" + json.budget.grocery;
    let json = {
		grocery: document.querySelector("input.groceryInput").value,
		restaurant: "blank",
		shopping: "blank",
		gas: "blank",
	    	code: document.querySelector("Code").value,
	}
    var data = JSON.stringify(json);

    if (socket.readyState === WebSocket.OPEN) {
		console.log(data);
    	socket.send(data);
    	console.log('nuts');
} 	else {
    console.log("WebSocket is not open. Current state:", socket.readyState);
}
    });

    Restaurant.addEventListener('input', function() {
		 //parentDiv.querySelector("input.restaurantInput").value = "$" + json.budget.restaurant;
		 let json = {
		grocery: "blank",
		restaurant: document.querySelector("input.restaurantInput").value,
		shopping: "blank",
		gas: "blank",
		code: document.querySelector("Code").value,
	}
    var data = JSON.stringify(json);

    socket.send(data);
    });

    Shopping.addEventListener('input', function() {
            //parentDiv.querySelector("input.shoppingInput").value = "$" + json.budget.shopping;
            let json = {
		grocery: "blank",
		restaurant: "blank",
		shopping: document.querySelector("input.shoppingInput").value,
		gas: "blank",
		code: document.querySelector("Code").value,
	}
    var data = JSON.stringify(json);

    socket.send(data);
    });

    Gas.addEventListener('input', function() {
            //parentDiv.querySelector("input.gasInput").value = "$" + json.budget.gas;
            let json = {
		grocery: "blank",
		restaurant: "blank",
		shopping: "blank",
		gas: document.querySelector("input.gasInput").value,
		code: document.querySelector("Code").value,
	}
    var data = JSON.stringify(json);

    socket.send(data);
    });
})

/*
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
*/


