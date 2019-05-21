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
	
	
	<!--  bound from/to test -->
	Bound From date:
	<icu:calendar name="fromDate" fromLang='en'  lang="en"   
	  date="${new Date()}"
	  selectDate="${false}" 
	  reverseBy="${-1}" 
	  updateSelectionFor="${['toDate']}"
	  updateOnlyIfEmpty="${true}"
	  forwardBy="${2}"
	  popupCalendar="${true}" 
	  incrementMethod="${IncrementMethod.YEAR}" />
	  
	  
	  
	  Bound To date:
	  
	  <icu:calendar name="toDate"  fromLang='en' lang="en"   
	  date="${new Date()}"
	  selectDate="${false}" 
	  reverseBy="${-1}" 
	  triggerEvent=""
	  forwardBy="${2}"
	  popupCalendar="${true}" 
	  incrementMethod="${IncrementMethod.YEAR}" />
	
	<!--  end bound test -->
	
	
	
	
	<br>
	<hr>
	FromDate
	
	<icu:calendar name="fromDate1" fromLang='fa' lang="en"   
	  date='۱/۱/۱۳۹۸'
	  selectDate="${true}" 
	  reverseBy="${-1}" 
	  
	  forwardBy="${2}"
	  popupCalendar="${true}" 
	  incrementMethod="${IncrementMethod.YEAR}" />
	  
	  <br/>
	  
	  To date
	  
	  <icu:calendar name="toDate2" fromLang='en' lang="fa"   
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
	  	   Vietnamese Date
	  
	  <icu:calendar name="vietDate" fromLang='en' lang="vi"   
	  date='21/3/2019'
	  selectDate="${true}" 
	  reverseBy="${-1}" 
	  
	  forwardBy="${2}"
	  popupCalendar="${true}" 
	  incrementMethod="${IncrementMethod.YEAR}" />
	   <br/>
	  	  Hindu date
	  
	  <icu:calendar name="hinduDate" fromLang='en' lang="hi_IN"   
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
	  
	  	  
	   	  Urdu date
	  
	  <icu:calendar name="pakDate" fromLang='en' lang="ur_PK"   
	  date='21/3/2019'
	  selectDate="${true}" 
	  reverseBy="${-1}" 
	  
	  forwardBy="${2}"
	  popupCalendar="${true}" 
	  incrementMethod="${IncrementMethod.YEAR}" />
	  	  <br/>
	  	   	  Hebrew date
	  
	  <icu:calendar name="hebrewDate" fromLang='en' lang="he_IL"   
	  date='21/3/2019'
	  selectDate="${true}" 
	  reverseBy="${-1}" 
	  
	  forwardBy="${2}"
	  popupCalendar="${true}" 
	  incrementMethod="${IncrementMethod.YEAR}" />
	  	  	  <br/>
	    
	  Chinese date
	  
	  <icu:calendar name="chineseDate" fromLang='en' lang="zh_CN"   
	  date='21/3/2019'
	  selectDate="${true}" 
	  reverseBy="${-1}" 
	  
	  forwardBy="${2}"
	  popupCalendar="${true}" 
	  incrementMethod="${IncrementMethod.YEAR}" />
	  
	  	  	  <br/>
	   Korean Date
	  
	  <icu:calendar name="koreanDate" fromLang='en' lang="ko_KR"   
	  date='21/3/2019'
	  selectDate="${true}" 
	  reverseBy="${-1}" 
	  
	  forwardBy="${2}"
	  popupCalendar="${true}" 
	  incrementMethod="${IncrementMethod.YEAR}" />
	  
	  
	    	  	  <br/>
	   Japanese Date
	  
	  <icu:calendar name="japaneseDate" fromLang='en' lang="ja"   
	  date='21/3/2019'
	  selectDate="${true}" 
	  reverseBy="${-1}" 
	  
	  forwardBy="${2}"
	  popupCalendar="${true}" 
	  incrementMethod="${IncrementMethod.YEAR}" />
	  
	  
	  
	  	  <br/>
	  	Russian Date
	  
	  <icu:calendar name="ruDate" fromLang='en' lang="ru"   
	  date='21/3/2019'
	  selectDate="${true}" 
	  reverseBy="${-1}" 
	  
	  forwardBy="${2}"
	  popupCalendar="${true}" 
	  incrementMethod="${IncrementMethod.YEAR}" />
	  
	  
	 </body>
</html>

```