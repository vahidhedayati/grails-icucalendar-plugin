package grails.utils

import com.ibm.icu.text.NumberFormat
import com.ibm.icu.util.Calendar
import com.ibm.icu.util.ULocale

class Icu4jHelper {

	Date date = new Date()
	Locale locale = Locale.UK
	ULocale ulocale 
	int yearsForward=3
	int yearsReverse=-3
	Calendar calendar  // = Calendar.getInstance(convertLocale(locale))
	
	ULocale convertLocale(Locale local) {
		return new ULocale(locale.language,locale.country,locale.variant)
	}
	
	def init() {
		//binds java locale to icu4j locale internally
		ulocale=convertLocale(locale)
		
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
		
		
		println " ${monthsOfYear.collect{it.value}} ${daysOfWeek.collect{it.longName}} ${daysOfMonth.collect{it.value}}"
	}
	
	List formMonth() {
		
		int currentYear=date.format('YYYY') as int
		int currentMonth=date.format('MM') as int
		int currentDay=date.format('dd') as int
		List results = []
		([yearsReverse..yearsForward])?.eachWithIndex { workingYear, i ->
			Date currentDate = plusYears(date,workingYear)
			EnumSet<MonthsOfYear> monthsOfYear
			if (i==0) {
				monthsOfYear=MonthsOfYear.getAvailableMonths(date.format('MM') as int,true)
			} else if (workingYear==yearsForward) {
				monthsOfYear=MonthsOfYear.getAvailableMonths(date.format('MM') as int,false)
			} else {
				monthsOfYear=EnumSet.noneOf( MonthsOfYear.class )
			}
			Map resultSet=[:]
			resultSet."${currentDate.format('YYYY')}"=[]
			monthsOfYear?.each {MonthsOfYear monthOfYear->
				
				currentDate = setMonth(monthOfYear.month)
				calendar.set(Calendar.DAY_OF_MONTH, 1)
				calendar.setTime(currentDate)
			
				resultSet.monthStart=calendar.getTime()
				calendar.set(Calendar.DAY_OF_MONTH, endMonthDay)
				
				resultSet.monthEnd=calendar.getTime()
				
				resultSet.month=monthOfYear.month
				
				resultSet.currentYear=resultSet.year==currentYear 
				resultSet.currentMonth=monthOfYear.month==currentMonth
					
				resultSet.currentDay=resultSet.day==currentDay
				
			}
			
		}
		
		
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
		Calendar cal = Calendar.getInstance()
		cal.setTime(date)
		cal.set(Calendar.DAY_OF_MONTH, 1)
		return cal.getTime()
	}
	
	public static int endMonthDay(Date date) {
		Calendar cal = Calendar.getInstance()
		cal.setTime(date)
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH)
	}
	
	public  int endMonthDay() {
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
	}
	
	public static Date plusYears(Date date, int years) {
		Calendar cal= date.toCalendar()
		cal.add(Calendar.YEAR, years)
		return new Date(cal.getTimeInMillis()).clearTime()
	}
	
	public static Date minusYears(Date date, int years) {
		return plusYears(date, -years)
	}
	
	public static Date setMonth(Date date, int months) {
		Calendar cal= date.toCalendar()
		cal.set(Calendar.MONTH, months)
		return new Date(cal.getTimeInMillis()).clearTime()
	}
	public static Date plusMonths(Date date, int months) {
		Calendar cal= date.toCalendar()
		cal.add(Calendar.MONTH, months)
		return new Date(cal.getTimeInMillis()).clearTime()
	}
	
	public static Date minusMonths(Date date, int months) {
		return plusMonths(date, -months)
	}
	
	public static Date endOfMonth(Date date) {
		Calendar cal = Calendar.getInstance()
		cal.setTime(date)
		int endDay = endMonthDay(cal)
		cal.set(Calendar.DAY_OF_MONTH, endDay)
		return cal.getTime()
	}
	
}
