<%@ page import="grails.utils.enums.IncrementMethod" %>
<!doctype html>
<html>
	<head>
		<meta name="layout" content="main" />
	

	<asset:stylesheet href="jquery-ui-1.9.2.custom.min.css" />
	<asset:stylesheet href="calendar.css" />
	<asset:javascript src="jquery-ui-1.9.2.custom.min.js" />
	<asset:javascript src="calendar.js" />

	</head>
	<body>

	
	
	
	<!--  popup -->
<g:textField name="fromDate"  onclick="datePickerPopup_fromDate(this.id);"/>
<g:hiddenField name="fromDateReal"  />
<g:hiddenField name="fromDateAlternative"  />

    <g:set var="jsonObject" value="${icu.calendarJson(
lang:"en_GB",  fromLang:'fa', date:'۱/۱/۱۳۹۸',, selectDate:true, reverseBy:-1, forwardBy:2 , incrementMethod:IncrementMethod.YEAR ) }" />
        
	<div id="datePickerDialog_fromDate"  class="dialogPicker" title="${g.message(code:'datePicker.choose' , args:['fromDate'])}">
		<div id="calendarDatePicker_fromDate" class="fromDate_dpo ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all ">
		</div>
	</div>
	
	
	<!-- non popup -->
	
    <g:set var="jsonObject2" value="${icu.calendarJson(
lang:"th",  fromLang:'en', date:'1/1/2019',, selectDate:true, reverseBy:-1, forwardBy:2 , incrementMethod:IncrementMethod.YEAR ) }" />
        
<g:textField name="toDate"  onclick="datePicker_toDate(this.id);"/>
<g:hiddenField name="toDateReal"  />
	<div id="calendarDatePicker_toDate" class="toDate_dpo ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all ">
	</div>


	<script>

var icu4jCalendar_fromDate= new Icu4jCalendar();

icu4jCalendar_fromDate.createCalendar(<%=jsonObject%>,'fromDate_dpo','fromDate');

var icu4jCalendar_toDate= new Icu4jCalendar();

icu4jCalendar_toDate.createCalendar(<%=jsonObject2%>,'fromDate_dpo','fromDate');

function getDialog() {
	$( "#datePickerDialog_fromDate").dialog({
		autoOpen: false,
	    height: 230,
	    width: 390,
	    position: {
	        my: "top center",
	        at: "top center"
		},
		close: function(event, ui) {
			$(this).dialog("close");
	     	//$(this).dialog("destroy");
	     	//$(this).hide();
		}
	});
	
}
getDialog();
//Using Jquery in this segment since there is a requirement to popup dates which relies on jquery-ui
function datePickerPopup_fromDate(divId) {
	//close all dialog boxes
	$(".ui-dialog-content").dialog("close");
	$( "#datePickerDialog_fromDate").dialog('open')
	icu4jCalendar_fromDate.datePicker(divId);
}
$('.ui-dialog-titlebar-close').on('click',function() {
	 $("#datePickerDialog_fromDate").dialog("close");
})



function datePicker_toDate(divId) {
	icu4jCalendar_toDate.datePicker(divId);
}
window.onload = function() {    
}
	
	var selectDate = icu4jCalendar_fromDate.jsonObject.selectDate;
	if (selectDate===true) {
		var monthData = icu4jCalendar_fromDate.jsonObject.dataSet.monthData;
        var calendar=icu4jCalendar_fromDate.drawCalendar(monthData.year,monthData.month,monthData.day)
        document.getElementById('fromDate').value=calendar.currentSelection;
    }
	
	var base = document.querySelector('#calendarDatePicker_fromDate');
	var selector = '.chooseDate_fromDate';
	base.addEventListener('click', function(event) {
	  var closest = event.target.closest(selector);
	  if (closest && base.contains(closest)) {
		  document.getElementById('fromDate').value=closest.dataset.humanDate;
    	  //document.getElementById('fromDate').value=closest.dataset.date;
          document.getElementById('fromDateReal').value=closest.dataset.date;
          document.getElementsByClassName('fromDate_dpo')[0].innerHTML = "";
          document.getElementsByClassName('fromDate_dpo')[0].style.display = "none";
         
          getDialog();
          $("#datePickerDialog_fromDate").dialog("close");
        
	  }
	});


	selectDate = icu4jCalendar_toDate.jsonObject.selectDate;
	if (selectDate===true) {
		var monthData = icu4jCalendar_toDate.jsonObject.dataSet.monthData;
        var calendar=icu4jCalendar_toDate.drawCalendar(monthData.year,monthData.month,monthData.day)
        document.getElementById('toDate').value=calendar.currentSelection;
    }
	base = document.querySelector('#calendarDatePicker_toDate');
	selector = '.chooseDate_toDate';
	base.addEventListener('click', function(event) {
	  var closest = event.target.closest(selector);
	  if (closest && base.contains(closest)) {
		  document.getElementById('toDate').value=closest.dataset.humanDate;
    	  //document.getElementById('fromDate').value=closest.dataset.date;
          document.getElementById('toDateReal').value=closest.dataset.date;
          document.getElementsByClassName('toDate_dpo')[0].innerHTML = "";
          document.getElementsByClassName('toDate_dpo')[0].style.display = "none";
      
	  }
	});


</script>
	
	
	</body>
</html>	