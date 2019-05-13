package grails.utils

import grails.utils.enums.IncrementMethod
import grails.validation.Validateable

@Validateable
class IcuBean {

	Date date=new Date()
	String lang
	Locale locale=Locale.UK
	int reverseBy=-3
	int forwardBy=4
	IncrementMethod incrementMethod=IncrementMethod.YEAR
	
	
	def bindBean() {
		if (lang) {
			locale = Icu4jHelper.convertLocale(lang)
		}
	}
	
	
}
