function resetForm() {
  document.getElementById("form").reset();
}

function checkPass() {
  let pass = document.getElementById("password-confirm").value;
  let passConfirm = document.getElementById("password").value;
  if (pass.length < 8) {
    alert("Password must be at least 8 characters");
    return false;
  }
  if (pass.length > 30) {
    alert("Password must not exceed 15 characters");
    return false;
  }

  if (pass != passConfirm) {
    console.log(pass);
    console.log(passConfirm);
    alert("The confirm password does not match");
    return false;
  }
  if ((pass = "" || pass == null)) {
    alert("Password must be at least 8 characters");
    return false;
  }
  return true;
}
