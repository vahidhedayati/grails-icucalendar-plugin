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
	
	JSON init() {
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
		
		Map results= [monthsOfYear:monthsOfYear.collect{[month:it.month,value:it.value]},
			daysOfWeek:daysOfWeek.collect{[month:it.value,value:it.longName]},daysOfMonth:daysOfMonth.collect{[day:it.dom,value:it.value]},
			 dataSet:formMonth]
		
		JSON json = results as JSON
		json.prettyPrint = true
		return json
	}
	
	List getFormMonth() {
		
		int currentYear=date.format('YYYY') as int
		int currentMonth=date.format('MM') as int
		int currentDay=date.format('dd') as int
		List results = []
		EnumSet<MonthsOfYear> monthsOfYear=EnumSet.noneOf(MonthsOfYear.class)
		Map resultSet=[:]
		int reverseBy,forwardBy
		switch(incrementMethod) {
			case IncrementMethod.YEAR:
				reverseBy=reverseDateBy
				forwardBy=forwardDateBy
				break
			case IncrementMethod.MONTH:
				reverseBy=(plusMonths(date,reverseDateBy)?.format('YYYY') as int)-currentYear
				forwardBy=(plusMonths(date,forwardDateBy)?.format('YYYY') as int)-currentYear
				if (forwardBy == 0 && reverseBy==0) {
					
					monthsOfYear=MonthsOfYear.getMonthsBeforeAndAfter(currentMonth,reverseDateBy,forwardDateBy)
					List internalList=formMonths(monthsOfYear,currentYear,currentYear+1, currentMonth, currentDay)
					resultSet."${currentYear}"=[ name: translateYear(currentDate) , months:internalList]
				}
				break
			default:
				Date preDate = plusMonths(date,reverseDateBy)
				Date postDate = plusMonths(date,forwardDateBy)
				reverseBy=(preDate?.format('YYYY') as int)-currentYear
				forwardBy=(postDate?.format('YYYY') as int)-currentYear
				int reverseMonthBy = (preDate?.format('MM') as int)-currentMonth
				int forwardMonthBy = (postDate?.format('MM') as int)-currentMonth
				
				if (forwardBy == 0 && reverseBy==0) {
					if (reverseMonthBy==0 &&forwardMonthBy==0 ) {
						monthsOfYear.add(MonthsOfYear.byMonth(currentMonth))
					} else {
					
						monthsOfYear=MonthsOfYear.getMonthsBeforeAndAfter(currentMonth,reverseMonthBy,forwardMonthBy)
					}
					List internalList=formMonths(monthsOfYear,currentYear, currentYear+1, currentMonth, currentDay)
					resultSet."${currentYear}"=[ name: translateYear(currentDate) , months:internalList]
				}
				break
		}
		if (reverseBy || forwardBy) {
			(reverseBy..forwardBy)?.eachWithIndex { workingYear, i ->
				Date currentDate = modifyDate(date,workingYear,incrementMethod)
				int workingOnYear = currentDate.format('YYYY') as int
				
				if (i==0) {
					monthsOfYear=MonthsOfYear.getAvailableMonths(currentMonth,true)
				} else if (workingYear==forwardDateBy) {
					monthsOfYear=MonthsOfYear.getAvailableMonths(currentMonth,false)
				} else {
					monthsOfYear=EnumSet.allOf( MonthsOfYear.class )
				}
				List internalList=formMonths(monthsOfYear,workingYear, currentYear, currentMonth, currentDay)
				resultSet."${workingOnYear}"=[ name: translateYear(currentDate) , months:internalList]
			}
		
		}
		results << resultSet
		return results
	}
	
	List formMonths(EnumSet<MonthsOfYear> monthsOfYear,int workingYear, int currentYear, int currentMonth, int currentDay) {
		List internalList =[]
		monthsOfYear?.each {MonthsOfYear monthOfYear->
			Map internalMap=[:]
			internalMap.month=monthOfYear.month
			internalMap.name=monthOfYear.value
			internalMap.start=1
			internalMap.end=endMonthDay
			
			if (workingYear==currentYear && monthOfYear.month==currentMonth) {
				internalMap.currentMonth==true
				internalMap.currentDay=currentDay
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
	String translateYear(Date CurrentDate) {
		String yearFormat='YYYY'
		SimpleDateFormat sdf = new SimpleDateFormat(yearFormat, ulocale)
		return sdf.format(CurrentDate)
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
	
	public static int endMonthDay(Date date) {
		java.util.Calendar cal = Calendar.getInstance()
		cal.setTime(date)
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH)
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
	public static Date plusMonths(Date date, int months) {
		java.util.Calendar cal= date.toCalendar()
		cal.add(Calendar.MONTH, months)
		return new Date(cal.getTimeInMillis()).clearTime()
	}
	
	public static Date minusMonths(Date date, int months) {
		return plusMonths(date, -months)
	}
	
	public static Date endOfMonth(Date date) {
		java.util.Calendar cal = Calendar.getInstance()
		cal.setTime(date)
		int endDay = endMonthDay(cal)
		cal.set(Calendar.DAY_OF_MONTH, endDay)
		return cal.getTime()
	}
	
}
