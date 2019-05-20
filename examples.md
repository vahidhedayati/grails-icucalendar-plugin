1 page loading in various different calendars and converting a date from 1 calendar to another calendar

```gsp

<%@ page import="grails.utils.enums.IncrementMethod" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'demo.label', default: 'demo runCommand')}" />
		<title><g:message code="default.create.label" args="[entityName]" /></title>
			<icu:jqueryui/>
	</head>
	<body>
	
	FromDate
	
	<icu:calendar name="fromDate" fromLang='fa' lang="en"   
	  date='۱/۱/۱۳۹۸'
	  selectDate="${true}" 
	  reverseBy="${-1}" 
	  
	  forwardBy="${2}"
	  popupCalendar="${true}" 
	  incrementMethod="${IncrementMethod.YEAR}" />
	  
	  <br/>
	  
	  To date
	  
	  <icu:calendar name="toDate" fromLang='en' lang="fa"   
	  date='21/3/2019'
	  selectDate="${true}" 
	  reverseBy="${-1}" 
	  
	  forwardBy="${2}"
	  popupCalendar="${true}" 
	  incrementMethod="${IncrementMethod.YEAR}" />
	  
	  <br/>
	  
	 Thai date
	  
	  <icu:calendar name="otherDate" fromLang='en' lang="th"   
	  date='21/3/2019'
	  selectDate="${true}" 
	  reverseBy="${-1}" 
	  
	  forwardBy="${2}"
	  popupCalendar="${true}" 
	  incrementMethod="${IncrementMethod.YEAR}" />
	  	  <br/>
	  
	  
	  	  Hindu date
	  
	  <icu:calendar name="hinduDate" fromLang='en' lang="hi"   
	  date='21/3/2019'
	  selectDate="${true}" 
	  reverseBy="${-1}" 
	  
	  forwardBy="${2}"
	  popupCalendar="${true}" 
	  incrementMethod="${IncrementMethod.YEAR}" />
	  	  <br/>
	  
	  
	   	  Egyptian date
	  
	  <icu:calendar name="egyptDate" fromLang='en' lang="ar_EG"   
	  date='21/3/2019'
	  selectDate="${true}" 
	  reverseBy="${-1}" 
	  
	  forwardBy="${2}"
	  popupCalendar="${true}" 
	  incrementMethod="${IncrementMethod.YEAR}" />
	  	  <br/>
	  
	  
	  	   	  Hebrew date
	  
	  <icu:calendar name="hebrewDate" fromLang='en' lang="he"   
	  date='21/3/2019'
	  selectDate="${true}" 
	  reverseBy="${-1}" 
	  
	  forwardBy="${2}"
	  popupCalendar="${true}" 
	  incrementMethod="${IncrementMethod.YEAR}" />
	 </body>
</html>

```