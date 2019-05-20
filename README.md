Grails 2 : config.groovy


```
grails.databinding.dateFormats = [
	'dd MMM yyyy', 'dd MMMM yyyy','dd/MM/yyyy', 'MMddyyyy', 'yyyy-MM-dd HH:mm:ss.S', "yyyy-MM-dd'T'hh:mm:ss'Z'"
] 
```

![sample image](https://raw.githubusercontent.com/vahidhedayati/grails-icucalendar-plugin/master/docs/basic-calendar-conversion-from_en_gb-to-fa_IR.png)

[video demonstrating very basic stuff on youtube part 1](https://www.youtube.com/watch?v=ARFbeiKbzm8)


Grails 3:
```
grails:
  databinding:
    dateFormats:
      - 'dd MMM yyyy'
      - 'dd MMMM yyyy'
      - 'dd/MM/yyyy'
      - 'dd/MM/yyyy HH:mm:ss'
      - 'yyyy-MM-dd HH:mm:ss.S'
      - "yyyy-MM-dd'T'hh:mm:ss'Z'"
      - "yyyy-MM-dd HH:mm:ss.S z"
      - "yyyy-MM-dd'T'HH:mm:ssX"



#### [Examples / how to use ](https://github.com/vahidhedayati/grails-icucalendar-plugin/blob/master/example.md)


[Backend content produced for frontend ](https://github.com/vahidhedayati/grails-icucalendar-plugin/blob/master/backend-content.md)


[Manual method most provided by plugin](https://github.com/vahidhedayati/grails-icucalendar-plugin/blob/master/docs/manual-most-provided.gsp)

[Total Manual way of calling](https://github.com/vahidhedayati/grails-icucalendar-plugin/blob/master/docs/manual-sample.gsp)

  