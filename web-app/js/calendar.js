function Icu4jCalendar() {
	
	var jsonObject;
	var className;
	var actualClass;
	var current = this;
	
	
	 this.createCalendar = function(jsonObject,className,actualClass ){
       
		 this.jsonObject=jsonObject;
		 this.className=className;
		 this.actualClass=actualClass;
	 };
	
	 
	 this.datePicker=function(divId) {
        	var monthData = this.jsonObject.dataSet.monthData;
        	document.getElementsByClassName(this.className)[0].innerHTML = "";
        	document.getElementsByClassName(this.className)[0].style.display = "block";    	        	
         
            var header = document.createElement('div');
            header.classList.add('ui-datepicker-header');
            header.classList.add('ui-widget-header');
            header.classList.add('ui-helper-clearfix');
            header.classList.add('ui-corner-all');
            
            var header1 = document.createElement('div');
            header1.classList.add('ui-datepicker-title');
            header1.setAttribute('id', 'header-content_'+this.actualClass);
            
            var yearSelection  = this.drawYear(monthData.year);
            var currentYear =yearSelection.year;
            var currentMonthSelect = this.drawMonth(currentYear , monthData.month)
            var currentMonth = currentMonthSelect.month
            header1.appendChild(yearSelection.selection);
            header1.appendChild(currentMonthSelect.selection);
            
            var header2 = document.createElement('div');
            header2.setAttribute('id', 'calendarDays_'+this.actualClass);
            header2.appendChild(this.drawCalendar(currentYear,monthData.month,monthData.day).table);
            
            header.appendChild(header1);
            header.appendChild(header2);
            
            document.getElementsByClassName(this.className)['0'].appendChild(header);
	    }

	    this.drawCalendar=function(currentYear,currentMonth, providedDay) {
	        var currentSelection;
	        var calendarResults = this.jsonObject.dataSet.results;
	        var monthDays = calendarResults[currentYear].formation[currentMonth]
	        var weekDays = this.jsonObject.daysOfWeek;
	        var daysOfMonth = this.jsonObject.daysOfMonth;
	        var monthsOfYear = this.jsonObject.monthsOfYear;
	        
	        var monthNumberMap = new Object();
	        monthsOfYear.forEach(function(entry) {
	        	monthNumberMap[entry.month] = entry.monthNumber;
	        })
	        var table =document.createElement('table')
	        table.classList.add('ui-datepicker-calendar');
	        table.setAttribute('id', 'calendar-content_'+this.actualClass);
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
	            	cell.classList.add('chooseDate_'+this.actualClass);
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
	            	cell.setAttribute('data-alternative-date',dayName+'/'+monthNumberMap[currentMonth]+'/'+yearName);
	            	cell.setAttribute('data-current-year',currentYear);
	            	cell.setAttribute('data-current-month',currentMonth);
	            	cell.setAttribute('data-current-day',currentDay);
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

	    this.updateYear=function(currentYear) {
	        var id='header-content_'+this.actualClass;
	        var header1 = document.getElementById(id);
	        header1.innerHTML = "";
	        var yearSelection  = this.drawYear(currentYear)
	        var currentMonthSelect = this.drawMonth(currentYear,undefined)
	        var currentMonth = currentMonthSelect.month
	        header1.appendChild(yearSelection.selection);
	        header1.appendChild(currentMonthSelect.selection);
	        
	        var header2 = document.getElementById('calendarDays_'+this.actualClass)
	        header2.innerHTML = "";
	        header2.appendChild(this.drawCalendar(currentYear,currentMonth,undefined).table);
	        
	    }
	    this.updateMonth=function(currentMonth) {
	        var yearSelection = document.getElementById('yearSelection_'+this.actualClass);
	        var currentYear = yearSelection.options[yearSelection.selectedIndex].value;
	        var header2 = document.getElementById('calendarDays_'+this.actualClass);
	        header2.innerHTML = "";
	        header2.appendChild(this.drawCalendar(currentYear,currentMonth,undefined).table);		
	    }

	   this.drawYear= function(currentYear) {
	        var calendarResults = this.jsonObject.dataSet.results;
	        let select1 = document.createElement('select');
	        select1.setAttribute('id', 'yearSelection_'+this.actualClass);
	        select1.classList.add('ui-datepicker-year');
	        select1.addEventListener('change',function() { current.updateYear(this.value); },false);
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
	    
	    this.drawMonth = function(currentYear, currentMonth) {
	        var calendarResults = this.jsonObject.dataSet.results;
	        var monthResults = calendarResults[currentYear].availableMonths
	        let select2 = document.createElement('select');
	        select2.setAttribute('id', 'monthSelection_'+this.actualClass);
	        select2.classList.add('ui-datepicker-year');
	        select2.addEventListener('change',function() { current.updateMonth(this.value); },false);
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
}