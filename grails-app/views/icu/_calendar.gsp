<%@ page import="grails.utils.enums.IncrementMethod" %>

<style>


</style>
<body>
<g:textField name="fromDate" value="${params.fromDate }"/>
	  
	  <div class="date-picker"  id="cal">
	  </div>

<script>
	
	var jsonObject =  <%=jsonObject.encodeAsJSON()%>;
	   
	   $('#fromDate').on('click', function(event) {
		   drawCalendar('#fromDate','date-picker');

		});

		function drawCalendar(divId,className) {
			document.getElementsByClassName(className)[0].innerHTML = "";
			document.getElementsByClassName(className)[0].style.display = "block";
			var header = document.createElement('div');
			header.classList.add('ui-datepicker-header');
			header.classList.add('ui-widget-header');
			header.classList.add('ui-helper-clearfix');
			header.classList.add('ui-corner-all');
			
			//header.setAttribute('id', 'header');
			var dataSet = jsonObject.dataSet;
			
			var header1 = document.createElement('div');
			header1.classList.add('ui-datepicker-title');
			
			let select1 = document.createElement('select');
			select1.setAttribute('id', 'yearSelection');
			select1.classList.add('ui-datepicker-year');
			//header.setAttribute('id', 'header');
			Object.keys(jsonObject.dataSet.results).forEach(function(entry) {
				console.log(' '+entry)
				let option1 = document.createElement('option');
				option1.innerHTML = entry;
				select1.appendChild(option1);
			});
			header1.appendChild(select1);
			
			
			let select2 = document.createElement('select');
			select2.setAttribute('id', 'monthSelection');
			select2.classList.add('ui-datepicker-year');
			jsonObject.monthsOfYear.forEach(function(entry) {
				let newElem1 = document.createElement('option');
				newElem1.classList.add('ui-datepicker-month');
				//newElem1.setAttribute('id', 'label');
				//# .ui-datepicker-year
				newElem1.setAttribute('data-id', entry.month);
				newElem1.innerHTML = entry.value ;
				select2.appendChild(newElem1);
			})
			header1.appendChild(select2);
			//ui-datepicker-calendar
			
			//calendar.appendChild(newElem);
			console.log('cal '+header)
			header.appendChild(header1);
			document.getElementsByClassName(className)['0'].appendChild(header);
		}
	    
</script>	  
</body>
