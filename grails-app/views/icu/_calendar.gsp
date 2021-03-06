<%@ page import="grails.utils.enums.IncrementMethod" %>

<g:if test="${bean.loadStyle }">
	<g:if test="${!bean.loadJqueryUi}">
		<asset:stylesheet href="jquery-ui-1.9.2.custom.min.css" />
	</g:if>
	<asset:stylesheet href="calendar.css" />
	<asset:javascript src="calendar.js" />
</g:if>
<g:if test="${bean.loadJqueryUi}">
	<asset:stylesheet href="jquery-ui-1.9.2.custom.min.css" />
	<asset:javascript src="jquery-ui-1.9.2.custom.min.js" />
	<g:if test="${!bean.loadStyle }">
		<asset:stylesheet href="calendar.css" />
		<asset:javascript src="calendar.js" />
	</g:if>
</g:if>



<g:textField name="${bean.name}"  onclick="${bean.popupCalendar ?'datePickerPopup_'+bean.name+'(this.id);' : 'datePicker_'+bean.name+'(this.id);'}"/>
<g:hiddenField name="${bean.name}Real"  />
<g:hiddenField name="${bean.name}Alternative"  />
<g:if test="${bean.popupCalendar}">
	<div id="datePickerDialog_${bean.name}"  class="dialogPicker" title="${g.message(code:'datePicker.choose' , args:[bean.name])}">
		<div id="calendarDatePicker_${bean.name}" class="${bean.name}_dpo ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all ">
		</div>
	</div>
</g:if>
<g:else>
	<div id="calendarDatePicker_${bean.name}" class="${bean.name}_dpo ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all ">
	</div>
</g:else>

<script>

var icu4jCalendar_${bean.name}= new Icu4jCalendar();

icu4jCalendar_${bean.name}.createCalendar(${raw(jsonObject as String)},'${bean.name}_dpo','${bean.name}');

//var context = document.getElementById('${bean.name}Context');

<g:if test="${bean.popupCalendar}">
function getDialog() {
	$( "#datePickerDialog_${bean.name}").dialog({
		autoOpen: false,
	    height: 230,
	    width: 390,
	    position: {
	        my: "top center",
	        at: "top center"
		},
		close: function(event, ui) {
			$(this).dialog("close");
		}
	});
	
}
getDialog();
//Using Jquery in this segment since there is a requirement to popup dates which relies on jquery-ui
function datePickerPopup_${bean.name}(divId) {
	//close all dialog boxes
	$(".ui-dialog-content").dialog("close");
	//open up dialog
	$( "#datePickerDialog_${bean.name}").dialog('open')
	
	//call calendar.js.datePicker(currentDivId)
	icu4jCalendar_${bean.name}.datePicker(divId);
}
</g:if>


function datePicker_${bean.name}(divId) {
	icu4jCalendar_${bean.name}.datePicker(divId);
}
var selectDate${bean.name} = icu4jCalendar_${bean.name}.jsonObject.selectDate;

if (selectDate${bean.name}===true) {
	var monthData${bean.name} = icu4jCalendar_${bean.name}.jsonObject.dataSet.monthData;
    var calendar${bean.name}=icu4jCalendar_${bean.name}.drawCalendar(monthData${bean.name}.year,monthData${bean.name}.month,monthData${bean.name}.day)
    document.getElementById('${bean.name}').value=calendar${bean.name}.currentSelection;
}

var base${bean.name} = document.querySelector('#calendarDatePicker_${bean.name}');
var selector${bean.name} = '.chooseDate_${bean.name}';
base${bean.name}.addEventListener('click', function(event) {
var closest${bean.name} = event.target.closest(selector${bean.name});
if (closest${bean.name} && base${bean.name}.contains(closest${bean.name})) {
	document.getElementById('${bean.name}').value=closest${bean.name}.dataset.humanDate;
    document.getElementById('${bean.name}Real').value=closest${bean.name}.dataset.date;
    document.getElementById('${bean.name}Alternative').value=closest${bean.name}.dataset.alternativeDate;
    document.getElementsByClassName('${bean.name}_dpo')[0].innerHTML = "";
    document.getElementsByClassName('${bean.name}_dpo')[0].style.display = "none";
    icu4jCalendar_${bean.name}.jsonObject.dataSet.monthData.month=closest${bean.name}.dataset.currentMonth
	icu4jCalendar_${bean.name}.jsonObject.dataSet.monthData.year=closest${bean.name}.dataset.currentYear
	icu4jCalendar_${bean.name}.jsonObject.dataSet.monthData.day=closest${bean.name}.dataset.currentDay
		
	// Below checks through any iterations that needs to be updated and attempts to set as an example:
	// toDate = fromDate value when fromDate is selected it needs a few options to be enabled 
    <g:if test="${bean.popupCalendar}">
      <g:if test="${bean.updateSelectionFor}">
	   		<g:each in="${bean.updateSelectionFor}" var="fieldName">
	   			 <g:if test="${bean.updateOnlyIfEmpty}">
	   			 	if (icu4jCalendar_${fieldName}.jsonObject.selectDate===false ) {
	   			 		//document.getElementById('${fieldName}').setAttribute('data-current-year',closest${bean.name}.dataset.currentYear);
	   					//document.getElementById('${fieldName}').setAttribute('data-current-month',closest${bean.name}.dataset.currentMonth);
	   					//document.getElementById('${fieldName}').setAttribute('data-current-day',closest${bean.name}.dataset.currentDay);
	   					//document.getElementById('${fieldName}').setAttribute('data-select-date',true);
	   			 		//icu4jCalendar_${fieldName}.jsonObject.selectDate=true
		   				icu4jCalendar_${fieldName}.jsonObject.dataSet.monthData.month=closest${bean.name}.dataset.currentMonth
		   				icu4jCalendar_${fieldName}.jsonObject.dataSet.monthData.year=closest${bean.name}.dataset.currentYear
		   				icu4jCalendar_${fieldName}.jsonObject.dataSet.monthData.day=closest${bean.name}.dataset.currentDay
		   			}
	   			 </g:if>
	   			 <g:else>
	   			$('#${fieldName}').data('data-current-year',closest${bean.name}.dataset.currentYear); 
					
	   				//document.getElementById('${fieldName}').setAttribute('data-current-year',closest${bean.name}.dataset.currentYear);
   					//document.getElementById('${fieldName}').setAttribute('data-current-month',closest${bean.name}.dataset.currentMonth);
   					//document.getElementById('${fieldName}').setAttribute('data-current-day',closest${bean.name}.dataset.currentDay);
   					//document.getElementById('${fieldName}').setAttribute('data-select-date',true);
		   		 	//icu4jCalendar_${fieldName}.jsonObject.selectDate=true
		   			icu4jCalendar_${fieldName}.jsonObject.dataSet.monthData.month=closest${bean.name}.dataset.currentMonth
		   			icu4jCalendar_${fieldName}.jsonObject.dataSet.monthData.year=closest${bean.name}.dataset.currentYear
		   			icu4jCalendar_${fieldName}.jsonObject.dataSet.monthData.day=closest${bean.name}.dataset.currentDay
	   			</g:else>
	   				document.getElementById('${fieldName}').value=closest${bean.name}.dataset.humanDate;
		   		    document.getElementById('${fieldName}Real').value=closest${bean.name}.dataset.date;
		   		 	document.getElementById('${fieldName}Alternative').value=closest${bean.name}.dataset.alternativeDate;
	   		</g:each>
	    </g:if>
	    getDialog();
	    $("#datePickerDialog_${bean.name}").dialog("close");
   </g:if>
  }
});
 
</script>
</body>
