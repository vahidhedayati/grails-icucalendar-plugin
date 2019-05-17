<%@ page import="grails.utils.enums.IncrementMethod" %>

<body>
	<g:textField name="${bean.name}" value="${bean.currentDate?:''}"  onclick="datePicker(this.id,'date-picker');"/>
	<g:hiddenField name="actualFromDate" value="" />
	<div id="calendarDatePicker" class="ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all">
	</div>


<script>
	var jsonObject =  <%=instance%>;
	function datePicker(divId,className) {
        if (document.getElementsByClassName(className)[0].innerHTML.trim()==='') {
        	var monthData = jsonObject.dataSet.monthData;
            document.getElementsByClassName(className)[0].innerHTML = "";
            document.getElementsByClassName(className)[0].style.display = "block";
         
            var header = document.createElement('div');
            header.classList.add('ui-datepicker-header');
            header.classList.add('ui-widget-header');
            header.classList.add('ui-helper-clearfix');
            header.classList.add('ui-corner-all');
            
            var header1 = document.createElement('div');
            header1.classList.add('ui-datepicker-title');
            header1.setAttribute('id', 'header-content');
            
            var yearSelection  = drawYear(monthData.year);
            var currentYear =yearSelection.year;
            var currentMonthSelect = drawMonth(currentYear , monthData.month)
            var currentMonth = currentMonthSelect.month
            header1.appendChild(yearSelection.selection);
            header1.appendChild(currentMonthSelect.selection);
            
            var header2 = document.createElement('div');
            header2.setAttribute('id', 'calendarDays');
            header2.appendChild(drawCalendar(currentYear,monthData.month,monthData.day).table);
            
            header.appendChild(header1);
            header.appendChild(header2);
            
            document.getElementsByClassName(className)['0'].appendChild(header);
        } else {
        	 document.getElementsByClassName(className)[0].innerHTML = "";
             document.getElementsByClassName(className)[0].style.display = "none";
        }
    	
    }

    function drawCalendar(currentYear,currentMonth, providedDay) {
        var currentSelection;
        var calendarResults = jsonObject.dataSet.results;
        var monthDays = calendarResults[currentYear].formation[currentMonth]
        var weekDays = jsonObject.daysOfWeek;
        var daysOfMonth = jsonObject.daysOfMonth;
        
        var table =document.createElement('table')
        table.classList.add('ui-datepicker-calendar');
        table.setAttribute('id', 'calendar-content');
        var row = table.insertRow(0); 
        var weekMap = new Object();
        var weekClass = new Object();
        weekDays.forEach(function(entry,i) {
            var cell = row.insertCell(i);
            var className='ui-datepicker-week'
            if (entry.weekend===true) {
                className='ui-datepicker-week-end'
            }
            
            cell.classList.add(className);
            cell.innerHTML = entry.value;
            weekMap[entry.dow] = i;
            weekClass[i] = className;
        })
        var monthMap = new Object();
        daysOfMonth.forEach(function(entry) {
            monthMap[entry.day] = entry.value;
        })
        var currentDay=monthDays.start;
        var currentCellId =monthDays.startDay;
        var rowCounter=1;
        row = table.insertRow(rowCounter);
        var j = 0;
        for (var i =0;  i  <= monthDays.end; i++) {
            if (i==0) {
                currentDay = monthDays.start;
                currentCellId=monthDays.startDay;
            }
            var cellId = weekMap[currentCellId];
            
            if (i==0 && cellId >0) {
                for (var q=0;  q  < cellId; q++) {
                    var cell = row.insertCell(q);
                    cell.innerHTML ='';
                    cell.classList.add(weekClass[j]);
                    j++;
                }
            }
            var cell = row.insertCell(j);
            var dayName = monthMap[currentDay]
            if (currentDay <= monthDays.end && dayName) {
            	cell.innerHTML =dayName;
            	cell.classList.add('chooseDate');
            	var yearName = calendarResults[currentYear].name;
            	var monthName = monthDays.name;
            	if (currentDay==providedDay) {
            		cell.classList.add('today');
            		currentSelection=dayName+' '+monthName+' '+yearName;
                } else {
                	cell.classList.add(weekClass[j]);
                }
            	cell.classList.add('selectable');
            	cell.setAttribute('data-human-date',dayName+' '+monthName+' '+yearName);
            	cell.setAttribute('data-date',currentDay+'/'+currentMonth+'/'+currentYear);
            }
            if (j==6) {
                j=0;
                rowCounter++;
                row = table.insertRow(rowCounter);
                currentCellId=0;
            } else {
                j++;
            }
            currentDay++;
            currentCellId++;
        }
        //div.appendChild(table);
        return {table:table, currentSelection:currentSelection};
    }

    function updateYear(currentYear) {
        var id='header-content';
        var header1 = document.getElementById(id);
        header1.innerHTML = "";
        var yearSelection  = drawYear(currentYear)
        var currentMonthSelect = drawMonth(currentYear,undefined)
        var currentMonth = currentMonthSelect.month
        header1.appendChild(yearSelection.selection);
        header1.appendChild(currentMonthSelect.selection);
        
        var header2 = document.getElementById('calendarDays')
        header2.innerHTML = "";
        header2.appendChild(drawCalendar(currentYear,currentMonth,undefined).table);
        
    }
    function updateMonth(currentMonth) {
        var yearSelection = document.getElementById("yearSelection");
        var currentYear = yearSelection.options[yearSelection.selectedIndex].value;
        var header2 = document.getElementById('calendarDays');
        header2.innerHTML = "";
        header2.appendChild(drawCalendar(currentYear,currentMonth,undefined).table);		
    }

    function drawYear(currentYear) {
        var calendarResults = jsonObject.dataSet.results;
        let select1 = document.createElement('select');
        select1.setAttribute('id', 'yearSelection');
        select1.classList.add('ui-datepicker-year');
        select1.addEventListener('change',function() { updateYear(this.value); },false);
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
    
    function drawMonth(currentYear, currentMonth) {
        var calendarResults = jsonObject.dataSet.results;
        var monthResults = calendarResults[currentYear].availableMonths
        let select2 = document.createElement('select');
        select2.setAttribute('id', 'monthSelection');
        select2.classList.add('ui-datepicker-year');
        select2.addEventListener('change',function() { updateMonth(this.value); },false);
        var selectedMonth;
        monthResults.forEach(function(entry,i) {
            if (i===0) {
                selectedMonth=entry.month;
            }
            let newElem1 = document.createElement('option');
            newElem1.classList.add('ui-datepicker-month');
            newElem1.value = entry.month;
            newElem1.innerHTML = entry.value ;
            if (currentMonth == entry.month ) {
            	newElem1.setAttribute('selected', true);
            	selectedMonth=entry
            }
            select2.appendChild(newElem1);
        })
        return {selection: select2, month:selectedMonth}
    }
    window.onload = function() {

    	var selectDate = jsonObject.selectDate;
    	if (selectDate===true) {
    		var monthData = jsonObject.dataSet.monthData;
            var calendar=drawCalendar(monthData.year,monthData.month,monthData.day)
            document.getElementById('fromDate').value=calendar.currentSelection;
        }
    	var base = document.querySelector('#calendarDatePicker');
    	var selector = '.chooseDate';
    	base.addEventListener('click', function(event) {
    	  var closest = event.target.closest(selector);
    	  if (closest && base.contains(closest)) {
        	  document.getElementById('fromDate').value=closest.dataset.humanDate;
        	  //document.getElementById('fromDate').value=closest.dataset.date;
              document.getElementById('actualFromDate').value=closest.dataset.date;
              document.getElementsByClassName('ui-datepicker')[0].innerHTML = "";
              document.getElementsByClassName('ui-datepicker')[0].style.display = "none";
    	  }
    	});
    }
</script>
</body>
