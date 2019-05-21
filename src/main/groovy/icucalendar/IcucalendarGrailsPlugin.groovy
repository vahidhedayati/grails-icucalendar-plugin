package icucalendar

import grails.plugins.*

class IcucalendarGrailsPlugin extends Plugin {
    def version = "1.0"
    def grailsVersion = "2.4 > *"
    def title = "icucalendar datepicker plugin"
    def description = """Grails icucalendar plugin using ICU4J libraries to work out international calendar / datepicker.
                         Converts a date between locale / calendars Can be run within modal dialog or pure javascript 
                        "no popup window"
                        """
    def documentation = "https://github.com/vahidhedayati/grails-icucalendar-plugin"
    def license = "APACHE"
    def developers = [name: 'Vahid Hedayati', email: 'badvad@gmail.com']
    def issueManagement = [system: 'GITHUB', url: 'https://github.com/vahidhedayati/grails-icucalendar-plugin/issues']
    def scm = [url: 'https://github.com/vahidhedayati/grails-icucalendar-plugin']
}