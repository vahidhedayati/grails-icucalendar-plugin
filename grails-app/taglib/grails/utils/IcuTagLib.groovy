package grails.utils

import grails.converters.JSON

import java.util.concurrent.atomic.AtomicInteger

import org.codehaus.groovy.grails.web.binding.DataBindingUtils

class IcuTagLib {
	static defaultEncodeAs = [taglib:'raw']
    static namespace='icu'
	
	
	//Judges if the addStyle tagLiv was called if not 
	AtomicInteger styleAdded=new AtomicInteger()
	
	//Judges if the addjqueryUi is enabled
	AtomicInteger jqueryUiAdded=new AtomicInteger()
	
	def jqueryui={
		jqueryUiAdded.andIncrement
		out << g.render(contextPath: pluginContextPath, template:'/icu/jqueryui')
	}
	
	def addStyle={
		styleAdded.andIncrement
		out << g.render(contextPath: pluginContextPath, template:'/icu/styleCalendar')
	}
	
	/**
	 * Using javascript to draw out calendar like datepicker datepicker calendar of jquery but with a big
	 * difference - proper international date picker driven by backend icu providing base data 
	 */
	def calendar = { attrs ->
		IcuBean bean = new IcuBean()
		DataBindingUtils.bindObjectToInstance(bean, attrs)
		bean.bindBean()
		bean.loadStyle=styleAdded.value<1
		Icu4jHelper icuj = new Icu4jHelper(bean)
		if (attrs.template) {
			out << g.render(template:attrs.template,  [bean:bean, jsonObject:(icuj.init())])
		} else {
			out << g.render(contextPath: pluginContextPath, template : attrs?.template?:"/icu/calendar", model: [bean:bean, jsonObject:(icuj.init())])
		}
		
	}
	
	def calendarMap ={ attrs ->
		IcuBean bean = new IcuBean()
		DataBindingUtils.bindObjectToInstance(bean, attrs)
		bean.bindBean()
		Icu4jHelper icuj = new Icu4jHelper(bean)
		out << icuj.initMap()
	}
	def calendarJson ={ attrs ->
		IcuBean bean = new IcuBean()
		DataBindingUtils.bindObjectToInstance(bean, attrs)
		bean.bindBean()
		Icu4jHelper icuj = new Icu4jHelper(bean)
		out << icuj.init()
    }
}
