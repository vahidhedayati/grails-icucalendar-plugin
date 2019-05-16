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
	
	Locale fromLocale
	
	ULocale ulocale
	
	ULocale fromUlocale
	 
	int forwardDateBy=3
	int reverseDateBy=-3
	Calendar calendar  // = Calendar.getInstance(convertLocale(locale))
	Calendar fromCalendar  // = Calendar.getInstance(convertLocale(locale))
	IncrementMethod incrementMethod
	
	ULocale convertLocale(Locale local) {
		return new ULocale(locale.language,locale.country,locale.variant)
	}
	
	Icu4jHelper() {
		
	}
	
	Icu4jHelper(IcuBean bean) {
		this.locale=bean.locale
		this.fromLocale=bean.fromLocale
		this.date=bean.date
		this.forwardDateBy=bean.forwardBy
		this.reverseDateBy=bean.reverseBy
		this.incrementMethod=bean.incrementMethod
		println " ${locale} vs ${fromLocale}"
		this.ulocale=convertLocale(locale)
		this.fromUlocale=convertLocale(fromLocale)
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
		JSON json = initMap() as JSON
		json.prettyPrint = true
		return json
	}
	
	Map initMap() {
		
		//sets up calendar as per locale instance depending on what type of calendar is required
		calendar = Calendar.createInstance(ulocale)
		fromCalendar = Calendar.createInstance(fromUlocale)
		// initialise set up days of month once for given local 
		DaysOfMonth.initialiseEnumByLocale(ulocale)
		
		// month day string value of 1..31 in English or Arabic based etc 
		EnumSet<DaysOfMonth> daysOfMonth =  EnumSet.allOf(DaysOfMonth.class)
		
		// set up week day names per locale - this is the day names i.e. Monday in given locale 
		DaysOfWeek.initialiseEnumByLocale(ulocale)
		
		//Collect EnumSet of days of week for given locale
		//wealthy information such as week day name per locale and which days are considered weekend
		List<DaysOfWeek> daysOfWeek = DaysOfWeek.daysByLocale(locale)
		
		// set up week day names per locale - this is the day names i.e. Monday in given locale
		MonthsOfYear.initialiseEnumByLocale(ulocale)
		EnumSet<MonthsOfYear> monthsOfYear = EnumSet.allOf(MonthsOfYear.class)

		Map results= [ dataSet:formMonth,
			daysOfWeek:daysOfWeek.collect{[dow:it.dow,value:it.longName, weekend:it.isWeekend]},
			daysOfMonth:daysOfMonth.collect{[day:it.dom,value:it.value]},
			monthsOfYear:monthsOfYear.collect{[month:it.month,value:it.value]}
		]
		
		return results
	}
	
	Map getFormMonth() {
		Map monthFormation = [:]
		//TODO - able to convert between GregorianCalendar and other calendars / issues converting back 
		/*
		lang:"en_GB",  fromLang:'fa_IR', date:'1/1/1398',
		lang:"fa_IR",  fromLang:'en_GB', date:'21/3/2019'
		lang:"en_GB",  fromLang:'fa_IR', date:'۱/۱/۱۳۹۸', 
		 */
		
		//We first set the date provided according to the locale of the given user 
		fromCalendar.setTime(date)
		//fromCalendar.set(date.format('yyyyy') as int,(date.format('MM') as int)-1,(date.format('dd') as int))
		//println " ${fromCalendar.getTime()} --2  ${fromCalendar.get(Calendar.DATE)}/${fromCalendar.get(Calendar.MONTH)+1}/${fromCalendar.get(Calendar.YEAR)} "
		calendar.setTime(fromCalendar.getTime());
		calendar.setTimeZone(fromCalendar.getTimeZone());
		
		//calendar.setTime(fromCalendar.getTime())
		calendar.set(fromCalendar.get(Calendar.YEAR),fromCalendar.get(Calendar.MONTH),fromCalendar.get(Calendar.DATE))
		//com.ibm.icu.text.SimpleDateFormat df = new com.ibm.icu.text.SimpleDateFormat()
		//df.applyPattern('dd/MM/yyyy')
		
		//date=calendar.getTime()
		//println " ${df.format(calendar)} ${date} --2  ${calendar.get(Calendar.DATE)}/${calendar.get(Calendar.MONTH)+1}/${calendar.get(Calendar.YEAR)} "
		
		monthFormation.currentYear=calendar.get(Calendar.YEAR)
		monthFormation.currentMonth=calendar.get(Calendar.MONTH)+1
		monthFormation.currentDay=calendar.get(Calendar.DATE)
		monthFormation.workingYear=monthFormation.currentYear
		EnumSet<MonthsOfYear> monthsOfYear=EnumSet.noneOf(MonthsOfYear.class)
		Map resultSet=[:]
		int reverseBy,forwardBy
		monthFormation.date=date
		monthFormation.today=date.format('dd/MMM/yyyy')
		
		switch(incrementMethod) {
			case IncrementMethod.YEAR:
				Date preDate = plusYears(calendar,date,reverseDateBy)
				Date postDate = plusYears(calendar,date,forwardDateBy)
				//Date preDate = plusYears(reverseDateBy)
				//Date postDate = plusYears(forwardDateBy)
				monthFormation.preYear=(preDate?.format('yyyy') as int)
				monthFormation.preMonth=(preDate?.format('MM') as int)
				monthFormation.preDay=(preDate?.format('dd') as int)
				monthFormation.postYear=(postDate?.format('yyyy') as int)
				monthFormation.postMonth=(postDate?.format('MM') as int)
				monthFormation.postDay=(postDate?.format('dd') as int)
				monthFormation.preDate=preDate.format('dd/MMM/yyyy')
				monthFormation.postDate=postDate.format('dd/MMM/yyyy')
				reverseBy=reverseDateBy
				forwardBy=forwardDateBy
				
				break
			case IncrementMethod.MONTH:
				Date preDate = plusMonths(calendar,date,reverseDateBy)
				Date postDate = plusMonths(calendar,date,forwardDateBy)
				//Date preDate = plusMonths(reverseDateBy)
				//Date postDate = plusMonths(forwardDateBy)
				monthFormation.preYear=(preDate?.format('yyyy') as int)
				monthFormation.preMonth=(preDate?.format('MM') as int)
				monthFormation.preDay=(preDate?.format('dd') as int)
				monthFormation.postYear=(postDate?.format('yyyy') as int)
				monthFormation.postMonth=(postDate?.format('MM') as int)
				monthFormation.postDay=(postDate?.format('dd') as int)
				monthFormation.preDate=preDate.format('dd/MMM/yyyy')
				monthFormation.postDate=postDate.format('dd/MMM/yyyy')
				reverseBy=monthFormation.preYear - monthFormation.currentYear
				forwardBy=monthFormation.postYear - monthFormation.currentYear
				monthFormation.date=date
				if (forwardBy == 0 && reverseBy==0) {
					monthsOfYear=MonthsOfYear.getMonthsBeforeAndAfter(monthFormation.currentMonth,reverseDateBy,forwardDateBy)
					resultSet."${monthFormation.currentYear}"=[ name: translateYear(date) , formation:formMonths(monthsOfYear,monthFormation)]
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
				monthFormation.preDate=preDate.format('dd/MMM/yyyy')
				monthFormation.postDate=postDate.format('dd/MMM/yyyy')
				reverseBy=monthFormation.preYear - monthFormation.currentYear
				forwardBy=monthFormation.postYear - monthFormation.currentYear
				int reverseMonthBy=monthFormation.preMonth - monthFormation.currentMonth
				int forwardMonthBy=monthFormation.postMonth - monthFormation.currentMonth
				monthFormation.date=date
				if (forwardBy == 0 && reverseBy==0) {
					if (reverseMonthBy==0 &&forwardMonthBy==0 ) {
						monthsOfYear.add(MonthsOfYear.byMonth(monthFormation.currentMonth))
					} else {
						monthsOfYear=MonthsOfYear.getMonthsBeforeAndAfter(monthFormation.currentMonth,reverseMonthBy,forwardMonthBy)
					}
					resultSet."${monthFormation.currentYear}"=[name: translateYear(date) , formation:formMonths(monthsOfYear,monthFormation)]
				}
				break
		}
		boolean allMonths
		if (reverseBy || forwardBy) {
			(reverseBy..forwardBy)?.eachWithIndex { workingYear, i ->
				Date cDate = modifyDate(calendar,date,workingYear,incrementMethod)
				//Date currentDate = modifyDate(workingYear,incrementMethod)
				calendar.setTime(cDate)
				monthFormation.workingYear = calendar.get(Calendar.YEAR) //cDate.format('yyyy') as int		
				monthFormation.date=cDate
				if (i==0) {
					monthsOfYear=MonthsOfYear.getAvailableMonths(monthFormation.currentMonth,true)
				} else if (workingYear==forwardDateBy) {
					monthsOfYear=MonthsOfYear.getAvailableMonths(monthFormation.currentMonth,false)
				} else {
					monthsOfYear=EnumSet.allOf( MonthsOfYear.class )
				}
				//Available months move to each year block returns exact specific range per year available
				resultSet."${monthFormation.workingYear}"=[ name: translateYear(cDate), 
					availableMonths:monthsOfYear.collect{[month:it.month,value:it.value]}, formation:formMonths(monthsOfYear,monthFormation)]
			}
		}
		return [monthData:[today:monthFormation.today,  year:monthFormation.currentYear,
			month:monthFormation.currentMonth,day:monthFormation.currentDay,
			startDate:monthFormation.preDate, endDate:monthFormation.postDate ],results:resultSet]
	}
	
	Map formMonths(EnumSet<MonthsOfYear> monthsOfYear, Map input) {
		Map returnMap=[:]
		//21 march 1 far
		monthsOfYear?.each {MonthsOfYear monthOfYear->
			Map internalMap=[:]
			int currentDay = calendar.get(Calendar.DATE) //input.date.format('dd') as int
			calendar.set(input.workingYear,monthOfYear.month-1,currentDay)
			Date cDate = calendar.getTime() 
			int currentYear =calendar.get(Calendar.YEAR) // cDate.format('yyyy') as int
			internalMap.month=monthOfYear.month
			internalMap.name=monthOfYear.value
			internalMap.start=1
			
			internalMap.end=endMonthDay(cDate)
			
			if (currentYear==input.currentYear && monthOfYear.month==input.currentMonth) {
				internalMap.currentMonth=true
				internalMap.active=input.currentDay
			}
			if (currentYear==input.preYear && monthOfYear.month==input.preMonth) {
				internalMap.firstMonth=true
				internalMap.start=input.preDay
			}
			if (currentYear==input.postYear && monthOfYear.month==input.postMonth) {
				internalMap.lastMonth=true
				internalMap.end=input.postDay
			}
			
			calendar.setTime(cDate)
			calendar.set(currentYear,monthOfYear.month-1,internalMap.start)
			internalMap.startDay = calendar.get(Calendar.DAY_OF_WEEK)
			returnMap."${monthOfYear.month}"=internalMap
		}
		
		return returnMap
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
	
	public Date startOfMonth(Date date) {
		calendar.set(Calendar.DAY_OF_MONTH, 1)
		return calendar.getTime()
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
	
	int getEndMonthDay() {
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
	}
	
	public static Date modifyDate(Calendar cal, Date date,int value,IncrementMethod incrementMethod) {
		if (incrementMethod==IncrementMethod.DAY) {
			return date+value
		} else {
			cal.setTime(date)
			///java.util.Calendar cal= date.toCalendar()
			cal.add(Calendar."${incrementMethod}", value)
			return new Date(cal.getTimeInMillis()).clearTime()
		}
	}
	
	Date modifyDate(int value,IncrementMethod incrementMethod) {
		if (incrementMethod==IncrementMethod.DAY) {
			return date+value
		} else {
			
			calendar.add(Calendar."${incrementMethod}", value)
			return calendar.getTime()
		}
	}
	
	public static Date plusYears(Calendar cal, Date date, int years) {
		cal.setTime(date)
		cal.add(Calendar.YEAR, years)
		return new Date(cal.getTimeInMillis()).clearTime()
	}
	
	public static Date minusYears(Calendar cal, Date date, int years) {
		return plusYears(cal, date, -years)
	}
	Date plusYears(int years) {
		calendar.add(Calendar.YEAR, years)
		return calendar.getTime()
	}
	Date minusYears(int years) {
		return plusYears(-years)
	}
	Date plusMonths(int months) {
		calendar.add(Calendar.MONTH, months)
		return calendar.getTime()
	}
	Date minusMonths( int months) {
		return plusMonths(-months)
	}
	
	public static Date plusMonths(Calendar cal, Date date, int months) {
		cal.setTime(date)
		cal.add(Calendar.MONTH, months)
		return new Date(cal.getTimeInMillis()).clearTime()
	}
	
	public static Date minusMonths(Calendar cal, Date date, int months) {
		return plusMonths(cal, date, -months)
	}
	
	Date endOfMonth(Date date) {
		calendar.setTime(date)
		int endDay = endMonthDay(calendar)
		calendar.set(Calendar.DAY_OF_MONTH, endDay)
		return calendar.getTime()
	}
	
	public static Locale convertLocale(String language) {
		if (language.contains('_')) {
			List<String> lang=language.split('_')
			return Locale.getInstance(lang[0].toLowerCase(),lang[1].toUpperCase(),'')
		}
		return Locale.getInstance(language,'','')
	}
	
}
