$(document).ready(function () {
$('.menu-item-wrap').click(function (e) { 
	var element = $(this);
	element.siblings().children().removeClass('active');
	
	element.children(":first").toggleClass("active");
	element.children('.menu-sub').toggle("slow", function() {
  });
	element.siblings().children('.menu-sub').hide("slow", function() {
  });
});


$('.cinema-item').click(function (e) { 
	var element =$(this).children('.inner').children(':first').children('.cinema-checkbox');
	if(element.is(":checked")){
		element.prop("checked", false);
		$(this).children('.inner').removeClass('activeborder');
		}
	else {
		element.prop("checked", true);
		$(this).siblings().children('.inner').removeClass('activeborder');
		$(this).children('.inner').addClass('activeborder');
		}
});

$('#button-confirm-cinema').click(function (e) { 
	var id =$('input[name="cinema"]:checked').val();
	if(id == null){
		
		$('#cinema-suggest').hide();
		$('#cinema-notify').show();
		}
	else
		window.location.href = "/showtime/create/"+id;
});

$('.filter-item').click(function (e) {
	$(this).siblings().removeClass('clicked');
	$(this).addClass('clicked');
	var text = $(this).children(':first').text();
	if(text === 'Tất cả'){
		doFilter('');
		
}else if(text === 'Đang chiếu'){
		doFilter('Đang chiếu');
}else if(text === 'Sắp chiếu'){
		doFilter('Sắp chiếu');
}else
		doFilter('Đã chiếu');
});

function doFilter(input){
	 var  filter, table, tr, td, i, txtValue;
	  filter = input.toUpperCase();
	  table = document.getElementById("list-all");
	  tr = table.getElementsByTagName("tr");
	  for (i = 0; i < tr.length; i++) {
	    td = tr[i].getElementsByTagName("td")[5];
	    if (td) {
	      txtValue = td.textContent || td.innerText;
	      if (txtValue.toUpperCase().indexOf(filter) > -1) {
	        tr[i].style.display = "";
	      } else {
	        tr[i].style.display = "none";
	      }
    }       
  }
}


});




function checkDate(){
		/*$("#showtime-add-form").submit(function(e){
                e.preventDefault(e);
            });*/
	
		// lay thoi gian cua input Date
		var date = $('#txtDate').val().split("-");
		console.log(date, $('#txtDate').val())
		inDay = date[2];
		inMonth = date[1];
		inYear = date[0];
		/*alert(inYear+"-"+inMonth+"-"+inDay );*/
		inputDate = inYear+"-"+inMonth+"-"+inDay;
		
		//lay thoi gian hien tai (nam, ngay, thang)
		d = new Date();
		month = d.getMonth()+1;
		day = d.getDate();
		curentDate = d.getFullYear() + '-' +
				(month<10 ? '0' : '') + month + '-' +
				(day<10 ? '0' : '') + day;
		
		/*alert(document.getElementById("start").value);*/
		
		
		//lay gio phut hien tai
		var curentTime = (d.getHours()<10 ? '0' : '')+d.getHours() + ":" + 
				(d.getMinutes()<10 ? '0' : '')+d.getMinutes();
		
		 var inputTime = onTimeChange('start');
		 //alert(curentTime +"   "+inputTime);
		 
		 // kiem tra thoi gian nhap co nho hon thoi gian hien tai ko
		 if(inputDate == curentDate && inputTime <= curentTime){
			if(!$(".alert.alert-warning").is(":visible")){
				$(".alert.alert-warning").toggle('show');
		 			window.setTimeout(function() {
					$(".alert.alert-warning").toggle('hide'); 
					}, 6000);
			}
			return false;
		 }else return true;
		 	
}

function onTimeChange(id) {
		var inputEle = document.getElementById(id);
		  var timeSplit = inputEle.value.split(':'),
		    hours,
		    minutes,
		    meridian;
		  hours = timeSplit[0];
		  minutes = timeSplit[1];
		  return hours +":"+minutes;
		  /*if (hours > 12) {
		    meridian = 'PM';
		    hours -= 12;
		  } else if (hours < 12) {
		    meridian = 'AM';
		    if (hours == 0) {
		      hours = 12;
		    }
		  } else {
		    meridian = 'PM';
		  }
		  alert(hours + ':' + minutes + ' ' + meridian);*/
}



function numberWithCommas(x) {
	var parts = x.toString().split(",");
	parts[0] = parts[0].replace(/\B(?=(\d{3})+(?!\d))/g, ",");
	return parts.join(",");
}

function currency_format(){
   var x =$('#showtime-curency').val();
   
   var temp2 = x.replace(/[^a-z0-9\s]/gi, '').replace(/[_\s]/g, '-').replace(/\D/g,'') ;
   var temp = temp2.replace(/\./g, '');
	$('#showtime-curency').val(numberWithCommas(temp));
}









