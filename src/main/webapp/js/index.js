var host;

$(document).ready(function(){
	init();
	host = window.location.protocol+"//"+window.location.host + "/";
});

function init(){
	$( "#find" ).click(function() {
		if($("#uuid").val() !== ''){
			parseData();
		}else{
			alert("uuid cannot be empty")
		}
	});
}

function parseData(){
	var uuid = $("#uuid").val();
	  $.getJSON( host+"test/resources/contacts/"+uuid, function( data ) {
		  $("#person").remove();
		  var items = [];
		  var uuid;
		  $.each( data, function( key, val ) {
			  if(key !== 'uuid'){
				  items.push( '<TR><TD>'+key+'</TD><TD><INPUT TYPE="TEXT" NAME="'+key+'" SIZE="20" value="'+val+'"></TD></TR>');
			  }else{
				  uuid = val;
			  }
		  });
		  $("body").append('<div id="person"/>');
		  $("#person").attr("uuid", uuid);
		  $( "<TABLE/>", {
			    "class": "my-new-list",
			    html: items.join( "" )
			  }).appendTo( "#person" );
		  $("#person").append('<input id="update" type="button" value="Update"/>');
		  $( "#update" ).click(function() {
			  updateData();
		  });
		  $("input[name='birthdate']").datepicker({ dateFormat: 'yy-mm-dd' });
	});
}

function updateData(){
	var item = {};
	item ["name"] = $("input[name='name']").val();
	item ["surname"] = $("input[name='surname']").val();
	item ["patronymic"] = $("input[name='patronymic']").val();
	item ["birthdate"] = $("input[name='birthdate']").val();
	$.ajax({
	    type: "PUT",
	    url: host+"test/resources/contacts/"+$("#person").attr("uuid"),
	    contentType: "application/json",
	    data: JSON.stringify(item),
	    dataType:'json',
	    success: function (response) {
	        alert("The data has been updated");
	      },
	    error: function (xhr, ajaxOptions, thrownError) {
	        alert(thrownError);
	      }
	});
}

