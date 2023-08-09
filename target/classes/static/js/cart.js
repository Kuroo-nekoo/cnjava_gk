$('#input').keypress(function(event){
  var keycode = (event.keyCode ? event.keyCode : event.which);
  if(keycode == '13'){
		$('#formSearch').submit();

    }
});
console.log("chạy đi chứ");

