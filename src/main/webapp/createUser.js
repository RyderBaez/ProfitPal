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

	document.querySelector("#signup-form").onsubmit = function() {
		
		// Validate information for empty fields and rules before sending to the server
		let validForm = true;
		
		// First Name Validation
		const firstName = document.querySelector("#first-name").value.trim();
		console.log(firstName)
		// Check empty string
		if(firstName.length === 0){
			validForm = false
			// E1 - Note: Tag "#email-error" displays the email errors checked in JS -->
			document.querySelector("#first-name-error").innerHTML = "First Name cannot be empty."
		}
		// Must start with capital followed by only lowercase
		if(/^[A-Z][a-z]*$/.test(firstName) == false){
			validForm = false;
			document.querySelector("#first-name-error").innerHTML = "Invalid First Name."
		}
		// valid
		else{
			document.querySelector("#first-name-error").innerHTML = ""
		}

		// Last Name Validation
		const lastName = document.querySelector("#last-name").value.trim()

		console.log(lastName)
		// Check empty string
		if(lastName.length === 0){
			validForm = false
			document.querySelector("#last-name-error").innerHTML = "Last name cannot be empty."
		}
		// If there is a number RegEx returns false
		if(/^[A-Z][a-z]*$/.test(lastName) == false){
			validForm = false;
			document.querySelector("#last-name-error").innerHTML = "Invalid Last Name."
		}
		// valid
		else{
			document.querySelector("#last-name-error").innerHTML = ""
		}

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

		// Repeat Password Validation.
		const passwordCopy = document.querySelector("#password-repeat").value;
		console.log(passwordCopy)
		// Empty String
		if (/^$/.test(passwordCopy)) {
			validForm = false
			document.querySelector("#password-error-2").innerHTML = "Password cannot be empty."
		} 
		// Check security
		else if (password != passwordCopy) {
			validForm = false
			document.querySelector("#password-error-2").innerHTML = 'Password\'s do not match.'
		} 
		// valid
		else {
			document.querySelector("#password-error-2").innerHTML = ''
		}
		
		// All checks passed then send to Servlet
		if (validForm) {
		  var xhr = new XMLHttpRequest();
		  var url = "SignupServlet";
		
		  var encodeFname = encodeURIComponent(firstName);
		  var encodeLname = encodeURIComponent(lastName);
		  var encodeEmail = encodeURIComponent(email);
		  var encodePassword = encodeURIComponent(password);
		  var encodePasswordRepeat = encodeURIComponent(passwordRepeat);
		
		  console.log(email);
		  console.log(encodeEmail);
		
		  var params = "email=" + encodeEmail + "&password=" + encodePassword + "&firstName=" + encodeFname + "&lastName=" + encodeLname + "&passwordRepeat=" + encodePasswordRepeat;
		
		  console.log("Data Pushed to Server");
		
		  xhr.open("POST", url, true);
		  xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		  xhr.onreadystatechange = function() {
		    if (xhr.readyState == XMLHttpRequest.DONE) {
		      if (xhr.status == 200) {
		        //valid response
		        localStorage.setItem("user", email);
		        window.location.href = "home.html";
		      } else {
		        console.error("Error: " + xhr.status + " - " + xhr.responseText);
		      }
		    }
		  };
		  xhr.send(params);
		}
		return false;
	} // End of submitButton Event

}); // END of DOM

// Email regex for usc.edu email
const EMAIL_REGEX = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

// console.log("Hello from validate email")
// Email validation function.
function isEmailValid(email) {
	return EMAIL_REGEX.test(email)
}
