package grails.utils

import grails.converters.JSON
import grails.utils.enums.DaysOfMonth
import grails.utils.enums.DaysOfWeek
import grails.utils.enums.IncrementMethod
import grails.utils.enums.MonthsOfYear

import com.ibm.icu.text.NumberFormat
import com.ibm.icu.text.SimpleDateFormat
import com.ibm.icu.util.Calendar
import com.ibm.icu.util.ULocale

class Icu4jHelper {

	Date date = new Date()
	Locale locale = Locale.UK
	ULocale ulocale 
	int forwardDateBy=3
	int reverseDateBy=-3
	Calendar calendar  // = Calendar.getInstance(convertLocale(locale))
	IncrementMethod incrementMethod
	
	ULocale convertLocale(Locale local) {
		return new ULocale(locale.language,locale.country,locale.variant)
	}
	
	Icu4jHelper() {
		
	}
	
	Icu4jHelper(Locale locale, Date date, int backwardYears,int forwardYears,IncrementMethod incrementMethod) {
		this.locale=locale
		this.date=date
		this.forwardDateBy=forwardYears
		this.reverseDateBy=backwardYears
		this.incrementMethod=incrementMethod
		this.ulocale=convertLocale(locale)
	}
	
	/**
	 * 
	 * Icu4jHelper icuj = new Icu4jHelper(new Locale("fa","IR"),new Date(),1,-2)
		icuj.init()
	 * @return
	 */
	
	def init(boolean printJSON=true) {
		//binds java locale to icu4j locale internally
		//ulocale=convertLocale(locale)
		
		//sets up calendar as per locale instance depending on what type of calendar is required
		calendar = Calendar.createInstance(ulocale)
		
		// initialise set up days of month once for given local 
		DaysOfMonth.initialiseEnumByLocale(ulocale)
		
		// month day string value of 1..31 in English or Arabic based etc 
		EnumSet<DaysOfMonth> daysOfMonth =  EnumSet.allOf(DaysOfMonth.class)
		
		// set up week day names per locale - this is the day names i.e. Monday in given locale 
		DaysOfWeek.initialiseEnumByLocale(ulocale)
		
		//Collect EnumSet of days of week for given locale
		//wealthy information such as week day name per locale and which days are considered weekend
		EnumSet<DaysOfWeek> daysOfWeek = EnumSet.allOf(DaysOfWeek.class)
		
		// set up week day names per locale - this is the day names i.e. Monday in given locale
		MonthsOfYear.initialiseEnumByLocale(ulocale)
		EnumSet<MonthsOfYear> monthsOfYear = EnumSet.allOf(MonthsOfYear.class)
		//monthsOfYear:monthsOfYear.collect{[month:it.month,value:it.value]},
		Map results= [ dataSet:formMonth,
			daysOfWeek:daysOfWeek.collect{[month:it.dow,value:it.longName]},
			daysOfMonth:daysOfMonth.collect{[day:it.dom,value:it.value]},
		]
		if (printJSON) {
			JSON json = results as JSON
			json.prettyPrint = true
			return json
		} else {
			return results
		}
	}
	
	List getFormMonth() {
		Map monthFormation = [:]
		monthFormation.currentYear=date.format('yyyy') as int
		monthFormation.currentMonth=date.format('MM') as int
		monthFormation.currentDay=date.format('dd') as int
		
		List results = []
		EnumSet<MonthsOfYear> monthsOfYear=EnumSet.noneOf(MonthsOfYear.class)
		Map resultSet=[:]
		int reverseBy,forwardBy
		monthFormation.date=date
		switch(incrementMethod) {
			case IncrementMethod.YEAR:
				Date preDate = plusYears(date,reverseDateBy)
				Date postDate = plusYears(date,forwardDateBy)
				monthFormation.preYear=(preDate?.format('yyyy') as int)
				monthFormation.preMonth=(preDate?.format('MM') as int)
				monthFormation.preDay=(preDate?.format('dd') as int)
				monthFormation.postYear=(postDate?.format('yyyy') as int)
				monthFormation.postMonth=(postDate?.format('MM') as int)
				monthFormation.postDay=(postDate?.format('dd') as int)
				
				reverseBy=reverseDateBy
				forwardBy=forwardDateBy
				
				break
			case IncrementMethod.MONTH:
				Date preDate = plusMonths(date,reverseDateBy)
				Date postDate = plusMonths(date,forwardDateBy)
				monthFormation.preYear=(preDate?.format('yyyy') as int)
				monthFormation.preMonth=(preDate?.format('MM') as int)
				monthFormation.preDay=(preDate?.format('dd') as int)
				monthFormation.postYear=(postDate?.format('yyyy') as int)
				monthFormation.postMonth=(postDate?.format('MM') as int)
				monthFormation.postDay=(postDate?.format('dd') as int)
				
				reverseBy=monthFormation.preYear - monthFormation.currentYear
				forwardBy=monthFormation.postYear - monthFormation.currentYear
				
				if (forwardBy == 0 && reverseBy==0) {
					monthsOfYear=MonthsOfYear.getMonthsBeforeAndAfter(monthFormation.currentMonth,reverseDateBy,forwardDateBy)
					List internalList=formMonths(monthsOfYear,monthFormation)
					resultSet."${monthFormation.currentYear}"=[ name: translateYear(currentDate) , formation:internalList]
				}
				break
			default:
				Date preDate = date+reverseDateBy
				Date postDate = date+forwardDateBy
				monthFormation.preYear=(preDate?.format('yyyy') as int)
				monthFormation.preMonth=(preDate?.format('MM') as int)
				monthFormation.preDay=(preDate?.format('dd') as int)
				monthFormation.postYear=(postDate?.format('yyyy') as int)
				monthFormation.postMonth=(postDate?.format('MM') as int)
				monthFormation.postDay=(postDate?.format('dd') as int)
				reverseBy=monthFormation.preYear - monthFormation.currentYear
				forwardBy=monthFormation.postYear - monthFormation.currentYear
				int reverseMonthBy=monthFormation.preMonth - monthFormation.currentMonth
				int forwardMonthBy=monthFormation.postMonth - monthFormation.currentMonth
				
				
				if (forwardBy == 0 && reverseBy==0) {
					if (reverseMonthBy==0 &&forwardMonthBy==0 ) {
						monthsOfYear.add(MonthsOfYear.byMonth(monthFormation.currentMonth))
					} else {
				
						monthsOfYear=MonthsOfYear.getMonthsBeforeAndAfter(monthFormation.currentMonth,reverseMonthBy,forwardMonthBy)
					}
					List internalList=formMonths(monthsOfYear,monthFormation)
					resultSet."${monthFormation.currentYear}"=[name: translateYear(currentDate) , formation:internalList]
				}
				break
		}
		if (reverseBy || forwardBy) {
			(reverseBy..forwardBy)?.eachWithIndex { workingYear, i ->
				Date currentDate = modifyDate(date,workingYear,incrementMethod)
				int workingOnYear = currentDate.format('yyyy') as int
				monthFormation.date=currentDate
				if (i==0) {
					monthsOfYear=MonthsOfYear.getAvailableMonths(monthFormation.currentMonth,true)
				} else if (workingYear==forwardDateBy) {
					monthsOfYear=MonthsOfYear.getAvailableMonths(monthFormation.currentMonth,false)
				} else {
					monthsOfYear=EnumSet.allOf( MonthsOfYear.class )
				}
				List internalList=formMonths(monthsOfYear,monthFormation)
				resultSet."${workingOnYear}"=[ name: translateYear(currentDate) , formation:internalList]
			}
		
		}
		results << [availableMonths:monthsOfYear]+resultSet
		return results
	}
	
	List formMonths(EnumSet<MonthsOfYear> monthsOfYear, Map input) {
		List internalList =[]
		monthsOfYear?.each {MonthsOfYear monthOfYear->
			Map internalMap=[:]
			Date currentDate = setMonthAndYear(input.date,monthOfYear.month,input.currentYear)
			
			internalMap.month=monthOfYear.month
			internalMap.name=monthOfYear.value
			internalMap.start=1
			
			
			internalMap.end=endMonthDay(currentDate)
			
			if (input.workingYear==input.currentYear && monthOfYear.month==input.currentMonth) {
				internalMap.currentMonth==true
				internalMap.currentDay=input.currentDay
			}
			if (input.workingYear==input.preYear && monthOfYear.month==input.preMonth) {
				internalMap.firstMonth==true
				internalMap.firstDay=input.preDay
			}
			if (input.workingYear==input.postYear && monthOfYear.month==input.postMonth) {
				internalMap.lastMonth==true
				internalMap.lastDay=input.postDay
			}
			
			/*
			currentDate = setMonth(currentDate, monthOfYear.month)
			calendar.set(Calendar.DAY_OF_MONTH, 1)
			calendar.setTime(currentDate)
		
			internalMap.monthStart=calendar.getTime()
			calendar.set(Calendar.DAY_OF_MONTH, internalMap.end)
			
			internalMap.monthEnd=calendar.getTime()
			
			internalMap.month=monthOfYear.month
			internalMap.currentYear=resultSet.year==currentYear
			internalMap.currentMonth=monthOfYear.month==currentMonth
				
			internalMap.currentDay=resultSet.day==currentDay
			*/
			internalList << internalMap
			//monthSet."${monthOfYear.month}"=internalMap
		}
		
		return internalList
	}
	String translateYear(Date currentDate) {
		String yearFormat='yyyy'
		SimpleDateFormat sdf = new SimpleDateFormat(yearFormat, ulocale)
		return sdf.format(currentDate)
		//NumberFormat nf = NumberFormat.getInstance(ulocale)
		//return nf.format(sdf.format(CurrentDate) as int)
	}
	 
	
	String formatNumber(Number number) {
		NumberFormat nf = NumberFormat.getInstance(ulocale)
		return nf.format(number)
	}

	static Date getCurrentDateAndTime() {
		return new Date()
	}
	
	static getCurrentDate() {
		return currentDateAndTime.clearTime()
	}
	
	public static Date startOfMonth(Date date) {
		java.util.Calendar cal = Calendar.getInstance()
		cal.setTime(date)
		cal.set(Calendar.DAY_OF_MONTH, 1)
		return cal.getTime()
	}
	
	Date setStartOfMonth(Date date) {
		calendar.setTime(date)
		calendar.set(Calendar.DAY_OF_MONTH, 1)
		return calendar.getTime()
	}
	
	int endMonthDay(Date date) {
		//java.util.Calendar cal = Calendar.getInstance()
		calendar.setTime(date)
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
	}
	
	public  int getEndMonthDay() {
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
	}
	
	public static modifyDate(Date date,int value,IncrementMethod incrementMethod) {
		if (incrementMethod==IncrementMethod.DAY) {
			return date+value
		} else {
			java.util.Calendar cal= date.toCalendar()
			cal.add(Calendar."${incrementMethod}", value)
			return new Date(cal.getTimeInMillis()).clearTime()
		}
	}
	
	
	public static Date plusYears(Date date, int years) {
		java.util.Calendar cal= date.toCalendar()
		cal.add(Calendar.YEAR, years)
		return new Date(cal.getTimeInMillis()).clearTime()
	}
	
	public static Date minusYears(Date date, int years) {
		return plusYears(date, -years)
	}
	
	public static Date setMonth(Date date, int months) {
		java.util.Calendar cal= date.toCalendar()
		cal.set(Calendar.MONTH, months)
		return new Date(cal.getTimeInMillis()).clearTime()
	}
	public static Date setMonthYear(Date date, int month, int year) {
		java.util.Calendar cal= date.toCalendar()
		cal.set(Calendar.MONTH, month)
		cal.set(Calendar.YEAR, year)
		return new Date(cal.getTimeInMillis()).clearTime()
	}
	
	Date setMonthAndYear(Date date, int month, int year) {
		calendar.set(Calendar.MONTH, month)
		calendar.set(Calendar.YEAR, year)
		return new Date(calendar.getTimeInMillis()).clearTime()
	}
	
	public static Date plusMonths(Date date, int months) {
		java.util.Calendar cal= date.toCalendar()
		cal.add(Calendar.MONTH, months)
		return new Date(cal.getTimeInMillis()).clearTime()
	}
	
	public static Date minusMonths(Date date, int months) {
		return plusMonths(date, -months)
	}
	
	Date endOfMonth(Date date) {
		calendar.setTime(date)
		int endDay = endMonthDay(calendar)
		calendar.set(Calendar.DAY_OF_MONTH, endDay)
		return calendar.getTime()
	}
	
}
