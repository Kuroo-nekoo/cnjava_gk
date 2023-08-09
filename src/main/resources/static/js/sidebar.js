$("#btn-sidebar").on("click", function (e) {
  if ($("#sidebar").hasClass("d-none") == true) {
    $("#sidebar").removeClass("d-none");
  } else {
    $("#sidebar").addClass("d-none");
  }
});
$("#outsidebar").on("click", function (e) {
  if ($("#sidebar").hasClass("d-none") == false) {
    $("#sidebar").addClass("d-none");
  }
});
