window.onload = function() {
	if(localStorage.length != 0){
		console.log("logged in");
		//document.getElementById("loginButton").onclick = "logout()";
		document.getElementById("loginButton").innerHTML = "Log out";
	} else {
		document.getElementById("loginButton").innerHTML = "Login";
	}
}

function tracker() {
	window.location.href = "table.html";
}

/* function logout() {
	localStorage.clear();
	window.location.href = "home.html";
} */

function loginPage() {
	if(localStorage.length == 0) {
		window.location.href = "loginUser.html"; 
	} else {
		localStorage.clear();
		window.location.href = "home.html";
	}
	
}

function newUserPage() {
	window.location.href = "createUser.html"; 
}

/* document.addEventListener("DOMContentLoaded", function(){
	const loginButton = document.getElementById("loginButton");
	const newUserButton = document.getElementById("newUserButton");
	const trackerButton = document.getElementById("trackerButton");
	loginButton.addEventListener("click", function(){
		window.location.href = "loginUser.html";
	});
	newUserButton.addEventListener("click", function(){
		window.location.href = "createUser.html";
	})
}) */