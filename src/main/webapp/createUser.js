document.addEventListener("DOMContentLoaded", function(){
    const loginButton = document.getElementById("loginButton");
    const newUserButton = document.getElementById("newUserButton");
    const submitButton = document.getElementById("submitButton");
    var firstName = document.getElementById("first-name");
    var lastName = document.getElementById("last-name");
    var email = document.getElementById("email");
    var password = document.getElementById("password");
    var passwordRepeat = document.getElementById("password-repeat");

    newUserButton.addEventListener("click", function(){
        firstName.value = "";
        email.value = "";
        password.value = "";
        passwordRepeat.value = "";
    });

    loginButton.addEventListener("click", function(){
        window.location.href = "loginUser.html";
    });
	submitButton.addEventListener("click", function() {
		var xhr = new XMLHttpRequest();
		var url = "SignupServlet";
		var encodeEmail = encodeURIComponent(email.value);
		var encodePassword = encodeURIComponent(password.value);
		var encodePasswordRepeat = encodeURIComponent(passwordRepeat.value);
		var encodeFname = encodeURIComponent(firstName.value);
		var encodeLname = encodeURIComponent(lastName.value);
		var params = "email=" + encodeEmail + "&password=" + encodePassword + "&firstName=" + encodeFname + "&lastName=" + encodeLname
		 + "&passwordRepeat=" + encodePasswordRepeat;
	
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
	
});

});