document.addEventListener("DOMContentLoaded", function(){
    const loginButton = document.getElementById("loginButton");
    const newUserButton = document.getElementById("newUserButton");
    const submitButton = document.getElementById("submit-login");
    var email = document.getElementById("email");
    var password = document.getElementById("password");
    var passwordRepeat = document.getElementById("password-repeat");

    loginButton.addEventListener("click", function(){
        email.value = "";
        password.value = "";
        passwordRepeat.value = "";
    });

    newUserButton.addEventListener("click", function(){
        window.location.href = "createUser.html";
    })
    
   	submitButton.addEventListener("click", function(){
        var xhr = new XMLHttpRequest();
		var url = "LoginServlet";
		var encodeEmail = encodeURIComponent(email.value);
		var encodePassword = encodeURIComponent(password.value);
		var params = "email=" + encodeEmail + "&password=" + encodePassword;
	
		xhr.open("POST", url, true);
		xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
		xhr.onreadystatechange = function() {
			if (xhr.readyState == XMLHttpRequest.DONE) {
				if (xhr.status == 200) {
					//valid response
					window.location.href = "loginUser.html";
				} else {
					alert("Error: " + xhr.status);
				}
			}
		};
		xhr.send(params);
    })
    
    registerSubmitButton.addEventLister("click", function()){
		
	}
});