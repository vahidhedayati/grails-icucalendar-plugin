package grails.utils

import org.codehaus.groovy.grails.web.binding.DataBindingUtils

class IcuTagLib {
	static defaultEncodeAs = [taglib:'raw']
    static namespace='icu'
	
	def jqueryui={
		out << g.render(contextPath: pluginContextPath, template:'/icu/jqueryui')
	}
	
	def week = { attrs ->
		IcuBean bean = new IcuBean()
		DataBindingUtils.bindObjectToInstance(bean, attrs)
		bean.bindBean()
		Icu4jHelper icuj = new Icu4jHelper(bean)
		if (attrs.template) {
			out << g.render(template:attrs.template, [bean:bean, instance:icuj.init()])
		} else {
			out << g.render(contextPath: pluginContextPath, template : attrs?.template?:"/icu/calendar", model: [bean:bean, instance:icuj.init()])
		}
		
	}
	
	def weekMap ={ attrs ->
		IcuBean bean = new IcuBean()
		DataBindingUtils.bindObjectToInstance(bean, attrs)
		bean.bindBean()
		Icu4jHelper icuj = new Icu4jHelper(bean)
		out << icuj.initMap()
	}
	def weekJson ={ attrs ->
		IcuBean bean = new IcuBean()
		DataBindingUtils.bindObjectToInstance(bean, attrs)
		bean.bindBean()
		Icu4jHelper icuj = new Icu4jHelper(bean)
		out << icuj.init()
    }
}
