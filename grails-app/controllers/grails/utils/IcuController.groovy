package grails.utils

class IcuController {

	def index(IcuBean bean) {
		bean.bindBean()
		Icu4jHelper icuj = new Icu4jHelper(bean)
		render icuj.init()
	}
	
	def loadCalendar(IcuBean bean) {
		bean.bindBean()
		Icu4jHelper icuj = new Icu4jHelper(bean)
		render  template:'/icu/calendar' , model: [bean:bean, jsonObject:icuj.initMap()]
	}
	
	
	
}
