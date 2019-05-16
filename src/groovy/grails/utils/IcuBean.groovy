package grails.utils

import grails.utils.enums.IncrementMethod
import grails.validation.Validateable

@Validateable
class IcuBean {
	
	
	Locale fromLocale
	String fromLang
	Date date
	
	
	String lang
	Locale locale
	
	
	int reverseBy
	int forwardBy
	IncrementMethod incrementMethod
	
	//This is your fieldName that is doing the datePicker by default set to fromDate
	String name
	
	//The format to get date picker to select date by
	//At the moment only supported format is this or defaulted to this
	String dateFormat='dd/MM/yyyy'
	
	
	//Provided date to set on date picker as actual value already selected 
	//Does not need to be provided if first hit
	//Date currentDate
	
	static constraints = {
		//currentDate(nullable:true)
		fromLang(nullable:true)
		fromLocale(nullable:true)
		lang(nullable:true)
		locale(nullable:true, validator:checkLocale)
		reverseBy(nullable:false)
		forwardBy(nullable:false)
		incrementMethod(nullable:false)
		name(nullable:true)
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
		return locale
	}
	
	Locale getFromLocale() {
		if (fromLang && !fromLocale) {
			return Icu4jHelper.convertLocale(fromLang)
		}
		return fromLocale?:locale
	}
	
	
	def bindBean() {
		if (lang && !locale) {
			locale = Icu4jHelper.convertLocale(lang)
		}
		if (!locale) {
			locale=Locale.UK
		}
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
		if (!name){
			name='fromDate'
		}
	}
	
	
	
	
}
