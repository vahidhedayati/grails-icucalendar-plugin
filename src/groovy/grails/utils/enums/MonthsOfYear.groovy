package grails.utils.enums


import grails.utils.Icu4jHelper

import com.ibm.icu.text.SimpleDateFormat
import com.ibm.icu.util.ULocale
import com.ibm.icu.util.Calendar
/**
 *  DaysOfMonth is a representation of each month day 
 *  which according to number formatting of icu4j the numeric presentation of that number is provided
 *  so in short draws out 1..31 either by string based on locale and gives us the day numbering per local
 *  Arabic numbers of 1..31 as an example
 */
public enum MonthsOfYear {

	JAN(1,'January'),
	FEB(2,'February'),
	MAR(3,'March'),
	APR(4,'April'),
	MAY(5,'May'),
	JUN(6,'June'),
	JUL(7,'July'),
	AUG(8,'August'),
	SEP(9,'September'),
	OCT(10,'October'),
	NOV(11,'November'),
	DEC(12,'December'),
	  
    int month
	
	String value
	
	
    MonthsOfYear(int month, String monthName) {
        this.month=month
		this.value=monthName	
    }

    public String getValue(){
        return value
    }
    public int getDom(){
        return month
    }
		
	public void setValue(String s){
		this.value=s
	}
	
	/**
	 * Dynamically  set data of the enum on call 
	 * 
	 *  1. Attempts to override isWeekend ENUM value set in current ENUM to match the end locale
	 *  2. set the correct short/long name of the day of the week based on provided locale
	 *  
	 * @param locale
	 */
	public static  void initialiseEnumByLocale(Locale locale) {
		initialiseEnumByLocale(new ULocale(locale.language,locale.country,locale.variant))
	}
	
	public static EnumSet<MonthsOfYear> getAvailableMonths(int month, boolean forwardMonths=true) {
		EnumSet<MonthsOfYear> collection =  EnumSet.noneOf( MonthsOfYear.class )
		values().each{MonthsOfYear val ->
			if (forwardMonths && val.month>=month||!forwardMonths && val.month<=month ) {
				collection.add(val)
			}
		}
		return collection
	}
	public static EnumSet<MonthsOfYear> getMonthsBeforeAndAfter(int currentMonth, int before,int after) {
		EnumSet<MonthsOfYear> collection =  EnumSet.noneOf( MonthsOfYear.class )
		values().each{MonthsOfYear val ->
			if (val.month <= currentMonth &&  ((currentMonth+before) <=val.month) ||
				 val.month >= currentMonth &&  ((val.month-currentMonth) <= after) ) {
				collection.add(val)
			}
		}
		return collection
	}
		
	public static void initialiseEnumByLocale(ULocale ulocale) {
		java.text.DateFormat format = new  java.text.SimpleDateFormat("dd/MM/yyyy")
		Date date = format.parse("1/1/2018")
		Calendar cal = Calendar.createInstance(ulocale)
		String longMonthFormat='MMMM'
		SimpleDateFormat lmf = new SimpleDateFormat(longMonthFormat, ulocale)
		MonthsOfYear first = values()[0]
		if (first.value!=lmf.format(date)) {
			MonthsOfYear.values().each{MonthsOfYear val ->
				date = Icu4jHelper.plusMonths(cal, date,1)
				val.setValue(lmf.format(date))
			}
		}
	}
	
	/**
	 * Called like DaysOfMonth dayOfMonth =  DaysOfMonth.byDom(1)
	 * dayOfMonth.value will return string value of locale formatted number
	 * @param val
	 * @return
	 */
	
	static MonthsOfYear byMonth(int val) {
		return values().find { it.month == val }
	}
	
	static MonthsOfYear byDom(Locale locale, int val) {
		initialiseEnumByLocale(locale)
		return values().find { it.month == val }
		//return ((EnumSet<DaysOfMonth>)initialiseEnumByLocale(locale)).find { it.dom == val }
	}
}

