document.addEventListener("DOMContentLoaded", function(){
    const loginButton = document.getElementById("loginButton");
    const newUserButton = document.getElementById("newUserButton");
    const submitButton = document.getElementById("submit-login");
    var email = document.getElementById("email");
    var password = document.getElementById("password");

    loginButton.addEventListener("click", function(){
        email.value = "";
        password.value = "";
    });

    newUserButton.addEventListener("click", function(){
        window.location.href = "createUser.html";
    });

	document.querySelector("#signup-form").onsubmit = function() {
		
		// Validate information for empty fields and rules before sending to the server
		let validForm = true;

		// Email validation
		// Trim leading and trailed whitespaces.
		const email = document.querySelector("#email").value.trim()

		// Print to console
		console.log(email)

		// If string is empty
		if (email.length === 0) {
			validForm = false
			// E1 - Note: Tag "#email-error" displays the email errors checked in JS -->
			document.querySelector("#email-error").innerHTML = "Email cannot be empty."
		}
		// // Checks for usc.edu ending 
		// else if (/usc\.edu$/.test(email) == false) {
		//     validForm = false
		//     document.querySelector("#email-error").innerHTML = 'Must be usc.edu email.'
		// } 
		// Checks for valid email with external JS
		else if (!isEmailValid(email)) {
			validForm = false 
			document.querySelector("#email-error").innerHTML = 'Invalid Email'
		} else {
			// Else the email is correct, and clear the error message.
			document.querySelector("#email-error").innerHTML = ''
		}

		// Password validation
		const password = document.querySelector("#password").value;
		console.log(password)
		// Empty String
		if (/^$/.test(password)) {
			validForm = false
			document.querySelector("#password-error").innerHTML = "Password cannot be empty."
		} 
		// Check security
		else if (/(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[!@#$%^&*]).*/.test(password) == false) {
			validForm = false
			document.querySelector("#password-error").innerHTML = 'Insecure password'
		} 
		// valid
		else {
			document.querySelector("#password-error").innerHTML = ''
		}
		
		console.log("is form valid: " + validForm);
		// All checks passed then send to Servlet
		if(validForm){
			var xhr = new XMLHttpRequest();
			var url = "LoginServlet";
			var encodeEmail = encodeURIComponent(email);
			var encodePassword = encodeURIComponent(password);
		    console.log("email entered " + email);
		    console.log("password entered " +password);
			var params = "email=" + encodeEmail + "&password=" + encodePassword;
		
			xhr.open("POST", url, true);
			xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
			xhr.onreadystatechange = function() {
				if (xhr.readyState == XMLHttpRequest.DONE) {
					if (xhr.status == 200) {
						//valid response
						console.log("success");
						window.location.href = "loginUser.html";
					} else {
						alert("Error: " + xhr.status);
					}
				}
			};
			xhr.send(params);
		}
		return false;
    }
});