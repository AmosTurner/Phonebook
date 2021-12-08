/* TODO add name? Verifies that the two password values entered match*/
function passwordValidation() {
    var password1 = document.forms['form']['password'].value;
    var password2 = document.forms['form']['verifyPassword'].value;
    if (password1 === null || password2 === "" || password1 !== password2) {
        document.getElementById("error").innerHTML= "Please check your passwords.";
        return false;
    }
    else{
        document.getElementById("error").innerHTML= "User successfully added!";
        return true;
    }
}