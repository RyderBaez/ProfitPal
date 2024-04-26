document.addEventListener("DOMContentLoaded", function(){
    const loginButton = document.getElementById("loginButton");
    const newUserButton = document.getElementById("newUserButton");
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
});