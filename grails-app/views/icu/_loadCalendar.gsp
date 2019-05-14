<%@ page import="grails.utils.enums.IncrementMethod" %>

<g:set var="jsonObject" value="${icu.calendarJson(lang:"en_GB",date:(new Date()-14), reverseBy:-3, forwardBy:2 , incrementMethod:IncrementMethod.MONTH ) }" />
	  
	  <g:textField name="fromDate" value="${params.fromDate }" onclick="datePicker(this.id,'date-picker');"/>
	  
	  <div class="date-picker"  id="cal">
	  </div>
	  
	  
	  
<script>
var jsonObject =  <%=jsonObject.encodeAsJSON()%>;

function datePicker(divId,className) {
	document.getElementsByClassName(className)[0].innerHTML = "";
	document.getElementsByClassName(className)[0].style.display = "block";
	var header = document.createElement('div');
	header.setAttribute('id', 'header');
	header.classList.add('ui-datepicker-header');
	header.classList.add('ui-widget-header');
	header.classList.add('ui-helper-clearfix');
	header.classList.add('ui-corner-all');
	//var dataSet = jsonObject.dataSet;
	
	var header1 = document.createElement('div');
	header1.classList.add('ui-datepicker-title');
	header1.setAttribute('id', 'header-content');
	var yearSelection  = drawYear(undefined)
	var currentYear =yearSelection.year;
	var currentMonthSelect = drawMonth(currentYear)
	var currentMonth = currentMonthSelect.month
	header1.appendChild(yearSelection.selection);
	header1.appendChild(currentMonthSelect.selection);
	
	var header2 = document.createElement('div');
	header2.setAttribute('id', 'calendarDays');
	header2.appendChild(drawCalendar(currentYear,currentMonth));
	
	header.appendChild(header1);
	header.appendChild(header2);
	document.getElementsByClassName(className)['0'].appendChild(header);
}

function drawCalendar(currentYear,currentMonth) {
	var calendarResults = jsonObject.dataSet.results;
	var monthDays = calendarResults[currentYear].formation[currentMonth]
	var weekDays = jsonObject.daysOfWeek;
	var daysOfMonth = jsonObject.daysOfMonth;

	var div =document.createElement('div')
	div.innerHTML=monthDays.name
	var table =document.createElement('table')
	table.classList.add('ui-datepicker-calendar');
	
	table.setAttribute('id', 'calendar-content');
	var row = table.insertRow(0); 
	var weekMap = new Object();
	weekDays.forEach(function(entry,i) {
    	var cell = row.insertCell(i);
    	cell.innerHTML = entry.value;
    	// cell.setAttribute('id', 'dow_'+entry.dow);
    	weekMap[entry.dow] = i;
	})
	var monthMap = new Object();
	daysOfMonth.forEach(function(entry) {
		monthMap[entry.day] = entry.value;
    })
	var currentDay=1
	var rowCounter=1
	row = table.insertRow(rowCounter);
	 var j = monthDays.start;
		console.log(' cell'+j)
	for (var i =j;  i  <= monthDays.end; i++) {
		if (i==monthDays.start) {
        	currentDay = monthDays.startDay
        }
		var cellId = weekMap[currentDay]
		console.log(' cell'+cellId+'i > '+i+" -- j "+ j+' ends:'+ monthDays.end+' '+monthDays.name)
		var cell = row.insertCell(cellId);
		cell.innerHTML = monthMap[monthDays.value]
		if (currentDay===7) {
    		rowCounter++
			row = table.insertRow(rowCounter)
            currentDay=0
        }
		currentDay++
	}
	div.appendChild(table);
	return div;
}

function updateYear(currentYear) {
    var id='header-content';
    var header1 = document.getElementById(id);
    header1.innerHTML = "";
	var yearSelection  = drawYear(currentYear)
	var currentMonthSelect = drawMonth(currentYear)
	var currentMonth = currentMonthSelect.month
	header1.appendChild(yearSelection.selection);
	header1.appendChild(currentMonthSelect.selection);
	
	var header2 = document.getElementById('calendarDays')
	header2.innerHTML = "";
	header2.appendChild(drawCalendar(currentYear,currentMonth));
	
	//header.appendChild(header1);
	//header.appendChild(header2);
}
function updateMonth(currentMonth) {
	console.log(' c'+currentMonth)
	var yearSelection = document.getElementById("yearSelection");
	var currentYear = yearSelection.options[e.selectedIndex].value;
	
	/*
    var id='header-content';
    var header1 = document.getElementById(id);
    header1.innerHTML = "";
	var yearSelection  = drawYear(currentYear)
	header1.appendChild(yearSelection.selection);
	header1.appendChild(drawMonth(currentYear));
	*/
}

function drawYear(currentYear) {
	var calendarResults = jsonObject.dataSet.results;
	let select1 = document.createElement('select');
	select1.setAttribute('id', 'yearSelection');
	select1.classList.add('ui-datepicker-year');
	select1.addEventListener('change',function() { updateYear(this.value); },false)
	var selectedYear;
	Object.keys(calendarResults).forEach(function(entry,i) {
		if (i===0) {
			selectedYear=entry
		}
		let option1 = document.createElement('option');
		option1.value = entry;
		if (currentYear == entry ) {
			option1.setAttribute('selected', true);
			selectedYear=entry
		}
		option1.innerHTML = calendarResults[entry].name;
		select1.appendChild(option1);
	});
	return {selection: select1, year:selectedYear}
}
function drawMonth(currentYear) {
	var calendarResults = jsonObject.dataSet.results;
	var monthResults = calendarResults[currentYear].availableMonths
	let select2 = document.createElement('select');
	select2.setAttribute('id', 'monthSelection');
	select2.classList.add('ui-datepicker-year');
	select2.addEventListener('change',function() { updateMonth(this.value); },false)
	var selectedMonth;
	monthResults.forEach(function(entry,i) {
		if (i===0) {
			selectedMonth=entry.month
		}
		let newElem1 = document.createElement('option');
		newElem1.classList.add('ui-datepicker-month');
		newElem1.value = entry.month;
		newElem1.innerHTML = entry.value ;
		select2.appendChild(newElem1);
	})
	return {selection: select2, month:selectedMonth}
}


window.onload = function() {


}
</script>	  