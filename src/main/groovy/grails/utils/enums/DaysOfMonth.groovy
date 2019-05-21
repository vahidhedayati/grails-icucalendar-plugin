package grails.utils.enums


import com.ibm.icu.text.NumberFormat
import com.ibm.icu.util.ULocale

/**
 *  DaysOfMonth is a representation of each month day 
 *  which according to number formatting of icu4j the numeric presentation of that number is provided
 *  so in short draws out 1..31 either by string based on locale and gives us the day numbering per local
 *  Arabic numbers of 1..31 as an example
 *  
 *  mapped once and reused for each month called in view when iterating through month days per locale
 */
public enum DaysOfMonth {
	ONE(1,'1'),
	TWO(2,'2'),
	THREE(3,'3'),
	FOUR(4,'4'),
	FIVE(5,'5'),
	SIX(6,'6'),
	SEVEN(7,'7'),
	EIGHT(8,'8'),
	NINE(9,'9'),
	TEN(10,'10'),
	ELEVEN(11,'11'),
	TWELVE(12,'12'),
	THIRTEEN(13,'13'),
	FOURTEEN(14,'14'),
	FIFTEEN(15,'15'),
	SIXTEEN(16,'16'),
	SEVENTEEN(17,'17'),
	EIGHTEEN(18,'18'),
	NINETEEN(19,'19'),
	TWENTY(20,'20'),
	TWENTYONE(21,'21'),
	TWENTYTWO(22,'22'),
	TWENTYTHREE(23,'23'),
	TWENTYFOUR(24,'24'),
	TWENTYFIVE(25,'25'),
	TWENTYSIX(26,'26'),
	TWENTYSEVEN(27,'27'),
	TWENTYEIGHT(28,'28'),
	TWENTYNINE(29,'29'),
	THIRTY(30,'30'),
	THIRTYONE(31,'31')
	
	  
	//Actual day of month 1..3	
    int dom
	
	//set by setValue below. via Number formatting number in initialiseEnumByLocale returns a string format ..
	String value
	
	
    DaysOfMonth(int dom, String numberName) {
        this.dom=dom
		this.value=numberName	
    }

    public String getValue(){
        return value
    }
    public int getDom(){
        return dom
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
	
	public static void initialiseEnumByLocale(ULocale ulocale) {
		NumberFormat nf = NumberFormat.getInstance(ulocale)
		DaysOfMonth first = values()[0]
		if (first.value!=nf.format(first.dom)) {
			DaysOfMonth.values().each{DaysOfMonth val ->
				val.setValue(nf.format(val.dom))
			}
		}
	}
	
	/**
	 * Called like DaysOfMonth dayOfMonth =  DaysOfMonth.byDom(1)
	 * dayOfMonth.value will return string value of locale formatted number
	 * @param val
	 * @return
	 */
	
	static DaysOfMonth byDom(int val) {
		return values().find { it.dom == val }
	}
	
	static DaysOfMonth byDom(Locale locale, int val) {
		initialiseEnumByLocale(locale)
		return values().find { it.dom == val }
		//return ((EnumSet<DaysOfMonth>)initialiseEnumByLocale(locale)).find { it.dom == val }
	}
}

