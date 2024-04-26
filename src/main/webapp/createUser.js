document.addEventListener("DOMContentLoaded", function(){
    const loginButton = document.getElementById("loginButton");
    const newUserButton = document.getElementById("newUserButton");
    var firstName = document.getElementById("first-name");
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
});