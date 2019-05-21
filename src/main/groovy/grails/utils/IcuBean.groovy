package grails.utils

import grails.utils.enums.IncrementMethod
import grails.validation.Validateable

class IcuBean implements Validateable {
	
	/**
	 * This is the from Language / from locale of given date
	 * currently only supports Julian "Western Dates" which can be converted to
	 * other calendars by defining a lang or locale below
	 * These 2 optional if not set will default from/to locale to locale value below 
	 */
	Locale fromLocale
	String fromLang
	
	//Actual date to work from
	Date date
	
	
	//This is the language / or locale to be of provided date
	//lang optional if provided and no locale given locale loaded for language code given
	String lang
	Locale locale
	
	
	//Reverse calendar date by period provided i.e. -3 - this gives -3{years/months/days} of calendar date to look at on viewer
	int reverseBy
	//forward from date provided 4 i.e. 4 years/months/days on from date
	int forwardBy
	
	//Driven by simple ENUM which defines day/month/year as selection of reverse/forward methods
	IncrementMethod incrementMethod
	
	//This is your fieldName that is doing the datePicker by default set to fromDate
	String name
	
	//date value provided above? should this be already be pre-selected as datepicker value
	//if not set by default false datepicker will open to today's calendar/date
	boolean selectDate
	
	
	Boolean popupCalendar
	
	boolean loadStyle
	
	boolean loadJqueryUi
	
	
	List<String> updateSelectionFor=[]
	boolean updateOnlyIfEmpty
	//The format to get date picker to select date by
	//At the moment only supported format is this or defaulted to this
	//String dateFormat='dd/MM/yyyy'
	
	static constraints = {
		selectDate(nullable:true)
		fromLang(nullable:true)
		fromLocale(nullable:true)
		lang(nullable:true)
		locale(nullable:true, validator:checkLocale)
		reverseBy(nullable:false)
		forwardBy(nullable:false)
		incrementMethod(nullable:false)
		name(nullable:true)
		loadStyle(nullable:true)
		popupCalendar(nullable:true)
		loadJqueryUi(nullable:true)
		updateOnlyIfEmpty(nullable:true)
	}
	static def checkLocale= { val, obj, errors ->
		if (!val && !obj.lang)  {
			errors.rejectValue(propertyName, "localeLang.error")
		}
	}

	Locale getLocale() {
		if (lang && !locale) {
			return Icu4jHelper.convertLocale(lang)
		}
		return locale?:Locale.UK
	}
	
	Locale getFromLocale() {
		if (fromLang && !fromLocale) {
			return Icu4jHelper.convertLocale(fromLang)
		}
		return fromLocale?:locale
	}
	
	
	def bindBean() {
		if (!date) {
			date=new Date()
		}
		if (!reverseBy){
			reverseBy=-3
		}
		if (!forwardBy){
			forwardBy=4
		}
		if (!incrementMethod){
			incrementMethod=IncrementMethod.YEAR
		}
		if (popupCalendar==null) {
			popupCalendar=true
		}
		if (!name){
			name='fromDate'
		}
	}
	

}
