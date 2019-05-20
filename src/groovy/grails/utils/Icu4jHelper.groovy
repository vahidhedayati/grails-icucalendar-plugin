package grails.utils

import java.util.Locale;

import grails.converters.JSON
import grails.utils.enums.DaysOfMonth
import grails.utils.enums.DaysOfWeek
import grails.utils.enums.IncrementMethod
import grails.utils.enums.MonthsOfYear

import com.ibm.icu.text.DateFormatSymbols
import com.ibm.icu.text.NumberFormat
import com.ibm.icu.text.SimpleDateFormat
import com.ibm.icu.util.Calendar
import com.ibm.icu.util.ULocale

/**
 * Icu4jHelper requires :
 * -> date            -  date to work from 
 * -> fromLocale      -  the locale date is provided from
 * -> locale          -  the to locale date is being converted to 
 * -> forwardDateBy   -  amount of days/months years to forward calendar dates from date provided
 * -> reverseDateBy   -  amount of days/months years to reverse calendar dates from date provided
 * -> incrementMethod -  method in which to increment by year/month/day 
 * 
 * 
 * Will produce :
 * 
 * init() -> JSON dataset containing full calendar for that period defined 
 * initMap() -> MAP dataset containing full calendar for that period defined
 * - it only includes the months of the year and start/end days 
 * 
 * The month contents is generated via frontend javascripts
 * Within the dataset includes basics such as month names/day names/week day names for
 * defined locale that is set to return data for -
 * 
 * The getInstance of calendar auto works out which calendar it needs to load up
 * Arabic/Hebrew/Gregorian/Persian/Russian etc 
 *   
 * @author Vahid Hedayati May 2019 
 * 
 *
 */
class Icu4jHelper {

	Date date = new Date()
	
	boolean selectDate
	
	Date currentDate = date
	Calendar currentCalendar
	Locale locale = Locale.UK
	
	Locale fromLocale
	
	ULocale ulocale
	
	ULocale fromUlocale
	 
	int forwardDateBy=3
	int reverseDateBy=-3
	Calendar calendar
	Calendar fromCalendar
	IncrementMethod incrementMethod
	
	public static ULocale convertToULocale(Locale local) {
		return new ULocale(local.language,local.country,local.variant)
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
		this.selectDate=bean.selectDate
		generateLocaleAndCalendar()
	}

	Icu4jHelper(Locale locale, Locale2, Date date, int backwardYears,int forwardYears,IncrementMethod incrementMethod) {
		this.locale=locale
		this.fromLocale=Locale2
		this.date=date
		this.forwardDateBy=forwardYears
		this.reverseDateBy=backwardYears
		this.incrementMethod=incrementMethod
		generateLocaleAndCalendar()
	}
	
	Icu4jHelper(Locale locale, Date date, int backwardYears,int forwardYears,IncrementMethod incrementMethod) {
		this.locale=locale
		this.fromLocale=locale
		this.date=date
		this.forwardDateBy=forwardYears
		this.reverseDateBy=backwardYears
		this.incrementMethod=incrementMethod
		generateLocaleAndCalendar()
	}
	
	void generateLocaleAndCalendar() {
		this.ulocale=convertToULocale(locale)
		this.fromUlocale=convertToULocale(fromLocale)
		//sets up calendar as per locale instance depending on what type of calendar is required
		this.calendar = Calendar.getInstance(ulocale)
		this.fromCalendar = Calendar.getInstance(fromUlocale)
		this.currentCalendar= Calendar.getInstance(ulocale)
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
		
		// initialise set up days of month once for given local 
		DaysOfMonth.initialiseEnumByLocale(ulocale)
		
		// month day string value of 1..31 in English or Arabic based etc 
		EnumSet<DaysOfMonth> daysOfMonth =  EnumSet.allOf(DaysOfMonth.class)
		
		// set up week day names per locale - this is the day names i.e. Monday in given locale 
		//DaysOfWeek.initialiseEnumByLocale(ulocale)
		
		//Collect EnumSet of days of week for given locale
		//wealthy information such as week day name per locale and which days are considered weekend
		List<DaysOfWeek> daysOfWeek = DaysOfWeek.daysByLocale(locale)
		
		// set up week day names per locale - this is the day names i.e. Monday in given locale
		//MonthsOfYear.initialiseEnumByLocale(ulocale)
		/**
		 * Months must be set by locale:
		 * Month 1 of Julian Calendar is Jan 
		 * Month 1 of Persian calendar: https://en.wikipedia.org/wiki/Iranian_calendars Frawardīn = March Julian calendar:
		 * 21/3/2019 = 1/1/1398 in Persian
		 * 
		 * This sets months up to align correctly with date conversion
		 */
		EnumSet<MonthsOfYear> monthsOfYear = MonthsOfYear.monthsByLocale(ulocale)
		
		Map results= [ dataSet:formMonth, selectDate:selectDate,
			daysOfWeek:daysOfWeek.collect{[dow:it.dow,value:it.longName, weekend:it.isWeekend]},
			daysOfMonth:daysOfMonth.collect{[day:it.dom,value:it.value]},
			monthsOfYear:monthsOfYear.collect{[month:it.month,value:it.value, monthNumber:it.monthNumber]}
		]
		
		return results
	}
	
	Map getFormMonth() {
		Map monthFormation = [:]
		
		
		/**
		 * Below 3 lines do date conversions between the two locales if there is a variation 
		 * in which date stored was from lets say Hebrew Calendar to Julian calendar and so on
		 
			lang:"en_GB",  fromLang:'fa', date:'1/1/1398',
			lang:"fa_IR",  fromLang:'en', date:'21/3/2019'
			lang:"en_GB",  fromLang:'fa', date:'۱/۱/۱۳۹۸',
			
			All above equate to 21/31/2019 which ever calendar if:
			>  21/3/2019 English  =  (1/1/1398 or ۱/۱/۱۳۹۸) in Persian
			> (1/1/1398 or ۱/۱/۱۳۹۸) Persian = 21/3/2019 English
		 */
		
		//set the date of fromCalendar which ever locale to be provided date this way 
		fromCalendar.set(date.format('yyyyy') as int,(date.format('MM') as int)-1,(date.format('dd') as int))
		
		//When converting back to current viewing locale use this technique:
		calendar.setTime(fromCalendar.getTime())
		//set current date to be converted dates between the two calendars if any difference
		date = calendar.getTime()
		
		// end conversion of dates
		
		monthFormation.currentYear=(selectDate ? calendar.get(Calendar.YEAR) : currentCalendar.get(Calendar.YEAR))
		monthFormation.currentMonth=(selectDate ? calendar.get(Calendar.MONTH)+1 : currentCalendar.get(Calendar.MONTH)+1)
		monthFormation.currentDay=(selectDate ? calendar.get(Calendar.DATE) : currentCalendar.get(Calendar.DATE))
		monthFormation.workingYear=monthFormation.currentYear
		EnumSet<MonthsOfYear> monthsOfYear=EnumSet.noneOf(MonthsOfYear.class)
		Map resultSet=[:]
		int reverseBy,forwardBy
		monthFormation.date=date
		monthFormation.today=date.format('dd/MMM/yyyy')
		
		switch(incrementMethod) {
			case IncrementMethod.YEAR:
			
				Date preDate = plusYears(reverseDateBy)
				Date postDate = plusYears(forwardDateBy)
		
				calendar.setTime(preDate)
				monthFormation.preYear=calendar.get(Calendar.YEAR)
				monthFormation.preMonth=calendar.get(Calendar.MONTH)+1
				monthFormation.preDay=calendar.get(Calendar.DATE)
				calendar.setTime(postDate)
				monthFormation.postYear=calendar.get(Calendar.YEAR)
				monthFormation.postMonth=calendar.get(Calendar.MONTH)+1
				monthFormation.postDay=calendar.get(Calendar.DATE)
				calendar.setTime(date)

				monthFormation.preDate=preDate.format('dd/MMM/yyyy')
				monthFormation.postDate=postDate.format('dd/MMM/yyyy')
				reverseBy=reverseDateBy
				forwardBy=forwardDateBy
				
				break
			case IncrementMethod.MONTH:		
				Date preDate = plusMonths(reverseDateBy)
				calendar.setTime(preDate)
				monthFormation.preYear=calendar.get(Calendar.YEAR)
				monthFormation.preMonth=calendar.get(Calendar.MONTH)+1
				monthFormation.preDay=calendar.get(Calendar.DATE)
				
				Date postDate = plusMonths(forwardDateBy)
				calendar.setTime(postDate)
				monthFormation.postYear=calendar.get(Calendar.YEAR)
				monthFormation.postMonth=calendar.get(Calendar.MONTH)+1
				monthFormation.postDay=calendar.get(Calendar.DATE)
		
				
				monthFormation.preDate=preDate.format('dd/MMM/yyyy')
				monthFormation.postDate=postDate.format('dd/MMM/yyyy')
				
				reverseBy=monthFormation.preYear - monthFormation.currentYear
				forwardBy=monthFormation.postYear - monthFormation.currentYear
				
				calendar.setTime(date)
				monthFormation.date=date
				if (forwardBy == 0 && reverseBy==0) {
					monthsOfYear=MonthsOfYear.getMonthsBeforeAndAfter(monthFormation.currentMonth,reverseDateBy,forwardDateBy)
					resultSet."${monthFormation.currentYear}"=[ name: translateYear(date) , formation:formMonths(monthsOfYear,monthFormation)]
				}
				break
			default:
				Date preDate = date+reverseDateBy
				Date postDate = date+forwardDateBy
				calendar.setTime(preDate)
				monthFormation.preYear=calendar.get(Calendar.YEAR)
				monthFormation.preMonth=calendar.get(Calendar.MONTH)+1
				monthFormation.preDay=calendar.get(Calendar.DATE)
				calendar.setTime(postDate)
				monthFormation.postYear=calendar.get(Calendar.YEAR)
				monthFormation.postMonth=calendar.get(Calendar.MONTH)+1
				monthFormation.postDay=calendar.get(Calendar.DATE)
				calendar.setTime(date)
		
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
				Date cDate = modifyDate(workingYear,incrementMethod)
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
					availableMonths:monthsOfYear.collect{[month:it.month,value:it.value]},
					formation:formMonths(monthsOfYear,monthFormation)]
				calendar.setTime(date)
			}
		}
		return [monthData:[today:monthFormation.today,  year:monthFormation.currentYear,
			month:monthFormation.currentMonth,day:monthFormation.currentDay,
			startDate:monthFormation.preDate, endDate:monthFormation.postDate ],results:resultSet]
	}
	
	Map formMonths(EnumSet<MonthsOfYear> monthsOfYear, Map input) {
		Map returnMap=[:]
		monthsOfYear?.each {MonthsOfYear monthOfYear->
			Map internalMap=[:]
			int currentDay = calendar.get(Calendar.DATE)
			calendar.set(input.workingYear,monthOfYear.month-1,currentDay)
			Date cDate = calendar.getTime() 
			int currentYear =calendar.get(Calendar.YEAR)
			internalMap.month=monthOfYear.month
			internalMap.name=monthOfYear.value
			internalMap.start=1
			
			internalMap.end=endMonthDay
			
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
	}
	 
	
	String formatNumber(Number number) {
		NumberFormat nf = NumberFormat.getInstance(ulocale)
		return nf.format(number)
	}
		
	int getEndMonthDay() {
		return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
	}
	
	Date modifyDate(int value,IncrementMethod incrementMethod) {
		if (incrementMethod==IncrementMethod.DAY) {
			return date+value
		} else {
			calendar.add(Calendar."${incrementMethod}", value)
			return calendar.getTime()
		}
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
	
	
	/**
	 *
	 * Helper method added useful for internal use of your application
	 * to simply convert a calendar date between 1 calendar in the world to another
	 *
	 * @param date
	 * @param fromLocale
	 * @param toLocale
	 * @return a map to get your new date run:
	 *
	 * Date mynewDate = Icu4jHelper.convertDate(new Date(),'en','fa').date
	 */

	public static Map convertDate(Date date, String fromLang,String toLang) {
		return convertDate(date,convertToULocale(convertLocale(fromLang)),convertToULocale(convertLocale(toLang)))
	}

	
	public static Map convertDate(Date date, Locale fromLocale,Locale toLocale ) {
		return convertDate(date,convertToULocale(fromLocale),convertToULocale(toLocale))
	}

	
	public static Map convertDate(Date date, ULocale fromLocale,ULocale toLocale ) {
	
		Calendar calendar = Calendar.getInstance(toLocale)
		Calendar fromCalendar = Calendar.getInstance(fromLocale)
		
		//set the date of fromCalendar which ever locale to be provided date this way
		fromCalendar.set(date.format('yyyyy') as int,(date.format('MM') as int)-1,(date.format('dd') as int))
		//When converting back to current viewing locale use this technique:
		calendar.setTime(fromCalendar.getTime())
		//set current date to be converted dates between the two calendars if any difference
		Date newDate = calendar.getTime()

		return [date:newDate, 
				locale:toLocale.country+'_'+toLocale.language, 
				from:[date:date, locale:fromLocale.country+'_'+fromLocale.language],
			]
	}
	
	public static Locale convertLocale(String language) {
		if (language.contains('_')) {
			List<String> lang=language.split('_')
			return Locale.getInstance(lang[0].toLowerCase(),lang[1].toUpperCase(),'')
		}
		return Locale.getInstance(language,'','')
	}
}
