document.addEventListener("DOMContentLoaded", function(){
	const loginButton = document.getElementById("loginButton");
	const newUserButton = document.getElementById("newUserButton");
	const trackerButton = document.getElementById("trackerButton");
	loginButton.addEventListener("click", function(){
		window.location.href = "loginUser.html";
	});
	newUserButton.addEventListener("click", function(){
		window.location.href = "createUser.html";
	})
})