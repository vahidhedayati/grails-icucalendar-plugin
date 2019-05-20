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
      
```
Below for index 3 you will see:

```
  "2017": {
        "name": "۱۳۹۶",
```     
        
The conversion process has provided a name of ۱۳۹۶ for the year 2017 in Persian calendar. The number translates to 1396 which means it has also provided year in understood format of that locale. i.e 2017 in the west was 1396 in Iran. 

Below for index 3 In Thailand 2019 was actually 2562.

```

     "2019": {
        "name": "2562",
```     

[Backend content produced for frontend ](https://github.com/vahidhedayati/grails-icucalendar-plugin/blob/master/backend-content.md)



[Manual method most provided by plugin](https://github.com/vahidhedayati/grails-icucalendar-plugin/blob/master/docs/manual-most-provided.gsp)

[Total Manual way of calling](https://github.com/vahidhedayati/grails-icucalendar-plugin/blob/master/docs/manual-sample.gsp)

  