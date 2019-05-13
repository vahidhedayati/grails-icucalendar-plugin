package grails.utils

class IcuController {

	def index(IcuBean bean) {
		bean.bindBean()
		Icu4jHelper icuj = new Icu4jHelper(bean)
		render icuj.init()
	}
	
	
	
}
