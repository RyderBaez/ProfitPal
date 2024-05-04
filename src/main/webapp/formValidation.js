// 	F1 - Note:When a user submits a form we must use the id from the entire form 
		//	and not specificly the button that says submit 
		// Event handler for Submitting the Form
document.querySelector("#signup-form").onsubmit = function() {
    console.log("Form Submitted")
    let validForm = true

    // First Name Validation
    const firstName = document.querySelector("#first-name").value.trim()

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


    // Website Validation
    const link = document.querySelector("#website").value.trim()

    console.log(link)

    if(link.length === 0){
        validForm = false
        // E1 - Note: Tag "#email-error" displays the email errors checked in JS -->
        document.querySelector("#website-error").innerHTML = "Website cannot be empty."
    }
    // Check for http and https at the start. 
    if(/^(http:\/\/|https:\/\/).+$/.test(link) == false){
        validForm = false;
        document.querySelector("#website-error").innerHTML = "Must start with http:// or https://."
    }
    // Check for a valid url
    if(/^(https?:\/\/)?([\da-z\.-]+)\.([a-z\.]{2,6})([\/\w \.-]*)*\/?$/.test(link) == false){
        validForm = false;
        document.querySelector("#website-error").innerHTML = "Invalid URL."
    }
    else{
        document.querySelector("#website-error").innerHTML = ""
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




    if(validForm){
        let formattedPhoneNumber = document.querySelector("#phone").value.trim();
        const digits = formattedPhoneNumber.match(/\d+/g); // Match all digit groups
        if (digits && digits.length === 3) {
            formattedPhoneNumber = `${digits[0]}-${digits[1]}-${digits[2]}`; // Format as '888-888-8888'
        }
        let fullName = firstName + " " + lastName;
        const phone = document.querySelector("#userInfo").value;
        const emailPrefix = email.split('@')[0];
        document.getElementById('userInfo').innerHTML = `
            <ul id="userInfoList">
                <li><strong>Full Name:</strong> </li>
                <ul>
                    <li><i>${fullName}</i></li>
                </ul>
                <li><strong>Username (Email Prefix):</strong></li>
                <ul>
                    <li><i>${emailPrefix}</i></li>
                </ul>
                <li><strong>Phone Number:</strong></li>
                <ul>
                    <li><i>${formattedPhoneNumber}</i></li>
                </ul>
            </ul>`;
    }

    // Return False because we don't have a confirmation page
    return false
}

const EMAIL_REGEX = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

// console.log("Hello from validate email")

function isEmailValid(email) {
	return EMAIL_REGEX.test(email)
}
