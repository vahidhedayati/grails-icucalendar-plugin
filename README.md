Grails 2 : config.groovy


```
grails.databinding.dateFormats = [
	'dd MMM yyyy', 'dd MMMM yyyy','dd/MM/yyyy', 'MMddyyyy', 'yyyy-MM-dd HH:mm:ss.S', "yyyy-MM-dd'T'hh:mm:ss'Z'"
] 
```

![sample image](https://raw.githubusercontent.com/vahidhedayati/grails-icucalendar-plugin/master/docs/basic-calendar-conversion-from_en_gb-to-fa_IR.png)

[video demonstrating very basic stuff on youtube part 1 ](https://www.youtube.com/watch?v=yldSVDp4vKM)


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

        

This provides a basic JSON of :

> day names mapped to locale, 
> month names mapped to locale, 
> dataSet:
> -> containing the actual date range with minimal date of start day of each month to be included and its end day
> -> available months: 

Awaiting javascript to pickup json and draw out the page .. To do 
   

Controller:
```groovy
import grails.utils.Icu4jHelper
import grails.utils.enums.IncrementMethod

class TestController {

	def index() {
		Icu4jHelper icuj = new Icu4jHelper(Locale.UK,new Date(),-90,90,IncrementMethod.DAY)
		render icuj.init() 
	}
	
	def index2() {
		Icu4jHelper icuj = new Icu4jHelper(new Locale("hi","IN"),new Date(),-2,1,IncrementMethod.MONTH)
		render icuj.init()
	}
	
	def index3() {
		Icu4jHelper icuj = new Icu4jHelper(new Locale("fa","IR"),new Date(),-2,2,IncrementMethod.YEAR)
		render icuj.init()
	}
	
	def index4() {
		Icu4jHelper icuj = new Icu4jHelper(new Locale("th","TH"),new Date(),-1,1,IncrementMethod.MONTH)
		render icuj.init()
	}
	
}
```   

index - produces:

```
{
  "dataSet": {
    "monthData": {
      "today": "17/May/2019",
      "year": 2019,
      "month": 5,
      "day": 17,
      "startDate": "16/Feb/2019",
      "endDate": "15/Aug/2019"
    },
    "results": {
      "2019": {
        "name": "2019",
        "formation": {
          "2": {
            "month": 2,
            "name": "Feb",
            "start": 16,
            "end": 28,
            "firstMonth": true,
            "startDay": 7
          },
          "3": {
            "month": 3,
            "name": "Mar",
            "start": 1,
            "end": 31,
            "startDay": 6
          },
          "4": {
            "month": 4,
            "name": "Apr",
            "start": 1,
            "end": 30,
            "startDay": 2
          },
          "5": {
            "month": 5,
            "name": "May",
            "start": 1,
            "end": 31,
            "currentMonth": true,
            "active": 17,
            "startDay": 4
          },
          "6": {
            "month": 6,
            "name": "Jun",
            "start": 1,
            "end": 30,
            "startDay": 7
          },
          "7": {
            "month": 7,
            "name": "Jul",
            "start": 1,
            "end": 31,
            "startDay": 2
          },
          "8": {
            "month": 8,
            "name": "Aug",
            "start": 1,
            "end": 15,
            "lastMonth": true,
            "startDay": 5
          }
        }
      }
    }
  },
  "selectDate": false,
  "daysOfWeek": 
  [
    {
      "dow": 2,
      "value": "Monday",
      "weekend": false
    },
    {
      "dow": 3,
      "value": "Tuesday",
      "weekend": false
    },
    {
      "dow": 4,
      "value": "Wednesday",
      "weekend": false
    },
    {
      "dow": 5,
      "value": "Thursday",
      "weekend": false
    },
    {
      "dow": 6,
      "value": "Friday",
      "weekend": false
    },
    {
      "dow": 7,
      "value": "Saturday",
      "weekend": true
    },
    {
      "dow": 1,
      "value": "Sunday",
      "weekend": true
    }
  ],
  "daysOfMonth": 
  [
    {
      "day": 1,
      "value": "1"
    },
    {
      "day": 2,
      "value": "2"
    },
    {
      "day": 3,
      "value": "3"
    },
    {
      "day": 4,
      "value": "4"
    },
    {
      "day": 5,
      "value": "5"
    },
    {
      "day": 6,
      "value": "6"
    },
    {
      "day": 7,
      "value": "7"
    },
    {
      "day": 8,
      "value": "8"
    },
    {
      "day": 9,
      "value": "9"
    },
    {
      "day": 10,
      "value": "10"
    },
    {
      "day": 11,
      "value": "11"
    },
    {
      "day": 12,
      "value": "12"
    },
    {
      "day": 13,
      "value": "13"
    },
    {
      "day": 14,
      "value": "14"
    },
    {
      "day": 15,
      "value": "15"
    },
    {
      "day": 16,
      "value": "16"
    },
    {
      "day": 17,
      "value": "17"
    },
    {
      "day": 18,
      "value": "18"
    },
    {
      "day": 19,
      "value": "19"
    },
    {
      "day": 20,
      "value": "20"
    },
    {
      "day": 21,
      "value": "21"
    },
    {
      "day": 22,
      "value": "22"
    },
    {
      "day": 23,
      "value": "23"
    },
    {
      "day": 24,
      "value": "24"
    },
    {
      "day": 25,
      "value": "25"
    },
    {
      "day": 26,
      "value": "26"
    },
    {
      "day": 27,
      "value": "27"
    },
    {
      "day": 28,
      "value": "28"
    },
    {
      "day": 29,
      "value": "29"
    },
    {
      "day": 30,
      "value": "30"
    },
    {
      "day": 31,
      "value": "31"
    }
  ],
  "monthsOfYear": 
  [
    {
      "month": 1,
      "value": "Jan"
    },
    {
      "month": 2,
      "value": "Feb"
    },
    {
      "month": 3,
      "value": "Mar"
    },
    {
      "month": 4,
      "value": "Apr"
    },
    {
      "month": 5,
      "value": "May"
    },
    {
      "month": 6,
      "value": "Jun"
    },
    {
      "month": 7,
      "value": "Jul"
    },
    {
      "month": 8,
      "value": "Aug"
    },
    {
      "month": 9,
      "value": "Sep"
    },
    {
      "month": 10,
      "value": "Oct"
    },
    {
      "month": 11,
      "value": "Nov"
    },
    {
      "month": 12,
      "value": "Dec"
    }
  ]
}
```

index2 produces:

```
{
  "dataSet": {
    "monthData": {
      "today": "17/May/2019",
      "year": 2019,
      "month": 5,
      "day": 17,
      "startDate": "17/Mar/2019",
      "endDate": "17/Jun/2019"
    },
    "results": {
      "2019": {
        "name": "2019",
        "formation": {
          "3": {
            "month": 3,
            "name": "मार्च",
            "start": 17,
            "end": 31,
            "firstMonth": true,
            "startDay": 1
          },
          "4": {
            "month": 4,
            "name": "अप्रैल",
            "start": 1,
            "end": 30,
            "startDay": 2
          },
          "5": {
            "month": 5,
            "name": "मई",
            "start": 1,
            "end": 31,
            "currentMonth": true,
            "active": 17,
            "startDay": 4
          },
          "6": {
            "month": 6,
            "name": "जून",
            "start": 1,
            "end": 17,
            "lastMonth": true,
            "startDay": 7
          }
        }
      }
    }
  },
  "selectDate": false,
  "daysOfWeek": 
  [
    {
      "dow": 1,
      "value": "रविवार",
      "weekend": true
    },
    {
      "dow": 2,
      "value": "सोमवार",
      "weekend": false
    },
    {
      "dow": 3,
      "value": "मंगलवार",
      "weekend": false
    },
    {
      "dow": 4,
      "value": "बुधवार",
      "weekend": false
    },
    {
      "dow": 5,
      "value": "गुरुवार",
      "weekend": false
    },
    {
      "dow": 6,
      "value": "शुक्रवार",
      "weekend": false
    },
    {
      "dow": 7,
      "value": "शनिवार",
      "weekend": false
    }
  ],
  "daysOfMonth": 
  [
    {
      "day": 1,
      "value": "1"
    },
    {
      "day": 2,
      "value": "2"
    },
    {
      "day": 3,
      "value": "3"
    },
    {
      "day": 4,
      "value": "4"
    },
    {
      "day": 5,
      "value": "5"
    },
    {
      "day": 6,
      "value": "6"
    },
    {
      "day": 7,
      "value": "7"
    },
    {
      "day": 8,
      "value": "8"
    },
    {
      "day": 9,
      "value": "9"
    },
    {
      "day": 10,
      "value": "10"
    },
    {
      "day": 11,
      "value": "11"
    },
    {
      "day": 12,
      "value": "12"
    },
    {
      "day": 13,
      "value": "13"
    },
    {
      "day": 14,
      "value": "14"
    },
    {
      "day": 15,
      "value": "15"
    },
    {
      "day": 16,
      "value": "16"
    },
    {
      "day": 17,
      "value": "17"
    },
    {
      "day": 18,
      "value": "18"
    },
    {
      "day": 19,
      "value": "19"
    },
    {
      "day": 20,
      "value": "20"
    },
    {
      "day": 21,
      "value": "21"
    },
    {
      "day": 22,
      "value": "22"
    },
    {
      "day": 23,
      "value": "23"
    },
    {
      "day": 24,
      "value": "24"
    },
    {
      "day": 25,
      "value": "25"
    },
    {
      "day": 26,
      "value": "26"
    },
    {
      "day": 27,
      "value": "27"
    },
    {
      "day": 28,
      "value": "28"
    },
    {
      "day": 29,
      "value": "29"
    },
    {
      "day": 30,
      "value": "30"
    },
    {
      "day": 31,
      "value": "31"
    }
  ],
  "monthsOfYear": 
  [
    {
      "month": 1,
      "value": "जन॰"
    },
    {
      "month": 2,
      "value": "फ़र॰"
    },
    {
      "month": 3,
      "value": "मार्च"
    },
    {
      "month": 4,
      "value": "अप्रैल"
    },
    {
      "month": 5,
      "value": "मई"
    },
    {
      "month": 6,
      "value": "जून"
    },
    {
      "month": 7,
      "value": "जुल॰"
    },
    {
      "month": 8,
      "value": "अग॰"
    },
    {
      "month": 9,
      "value": "सित॰"
    },
    {
      "month": 10,
      "value": "अक्तू॰"
    },
    {
      "month": 11,
      "value": "नव॰"
    },
    {
      "month": 12,
      "value": "दिस॰"
    }
  ]
}
```

index3 produces:

```
{
  "dataSet": {
    "monthData": {
      "today": "17/May/2019",
      "year": 1398,
      "month": 2,
      "day": 27,
      "startDate": "17/May/2017",
      "endDate": "17/May/2021"
    },
    "results": {
      "1396": {
        "name": "۱۳۹۶",
        "availableMonths": 
        [
          {
            "month": 2,
            "value": "اردیبهشت"
          },
          {
            "month": 3,
            "value": "خرداد"
          },
          {
            "month": 4,
            "value": "تیر"
          },
          {
            "month": 5,
            "value": "مرداد"
          },
          {
            "month": 6,
            "value": "شهریور"
          },
          {
            "month": 7,
            "value": "مهر"
          },
          {
            "month": 8,
            "value": "آبان"
          },
          {
            "month": 9,
            "value": "آذر"
          },
          {
            "month": 10,
            "value": "دی"
          },
          {
            "month": 11,
            "value": "بهمن"
          },
          {
            "month": 12,
            "value": "اسفند"
          }
        ],
        "formation": {
          "2": {
            "month": 2,
            "name": "اردیبهشت",
            "start": 1,
            "end": 31,
            "startDay": 6
          },
          "3": {
            "month": 3,
            "name": "خرداد",
            "start": 1,
            "end": 31,
            "startDay": 2
          },
          "4": {
            "month": 4,
            "name": "تیر",
            "start": 1,
            "end": 31,
            "startDay": 5
          },
          "5": {
            "month": 5,
            "name": "مرداد",
            "start": 1,
            "end": 31,
            "startDay": 1
          },
          "6": {
            "month": 6,
            "name": "شهریور",
            "start": 1,
            "end": 31,
            "startDay": 4
          },
          "7": {
            "month": 7,
            "name": "مهر",
            "start": 1,
            "end": 30,
            "startDay": 7
          },
          "8": {
            "month": 8,
            "name": "آبان",
            "start": 1,
            "end": 30,
            "startDay": 2
          },
          "9": {
            "month": 9,
            "name": "آذر",
            "start": 1,
            "end": 30,
            "startDay": 4
          },
          "10": {
            "month": 10,
            "name": "دی",
            "start": 1,
            "end": 30,
            "startDay": 6
          },
          "11": {
            "month": 11,
            "name": "بهمن",
            "start": 1,
            "end": 30,
            "startDay": 1
          },
          "12": {
            "month": 12,
            "name": "اسفند",
            "start": 1,
            "end": 29,
            "startDay": 3
          }
        }
      },
      "1397": {
        "name": "۱۳۹۷",
        "availableMonths": 
        [
          {
            "month": 1,
            "value": "فروردین"
          },
          {
            "month": 2,
            "value": "اردیبهشت"
          },
          {
            "month": 3,
            "value": "خرداد"
          },
          {
            "month": 4,
            "value": "تیر"
          },
          {
            "month": 5,
            "value": "مرداد"
          },
          {
            "month": 6,
            "value": "شهریور"
          },
          {
            "month": 7,
            "value": "مهر"
          },
          {
            "month": 8,
            "value": "آبان"
          },
          {
            "month": 9,
            "value": "آذر"
          },
          {
            "month": 10,
            "value": "دی"
          },
          {
            "month": 11,
            "value": "بهمن"
          },
          {
            "month": 12,
            "value": "اسفند"
          }
        ],
        "formation": {
          "1": {
            "month": 1,
            "name": "فروردین",
            "start": 1,
            "end": 31,
            "startDay": 4
          },
          "2": {
            "month": 2,
            "name": "اردیبهشت",
            "start": 1,
            "end": 31,
            "startDay": 7
          },
          "3": {
            "month": 3,
            "name": "خرداد",
            "start": 1,
            "end": 31,
            "startDay": 3
          },
          "4": {
            "month": 4,
            "name": "تیر",
            "start": 1,
            "end": 31,
            "startDay": 6
          },
          "5": {
            "month": 5,
            "name": "مرداد",
            "start": 1,
            "end": 31,
            "startDay": 2
          },
          "6": {
            "month": 6,
            "name": "شهریور",
            "start": 1,
            "end": 31,
            "startDay": 5
          },
          "7": {
            "month": 7,
            "name": "مهر",
            "start": 1,
            "end": 30,
            "startDay": 1
          },
          "8": {
            "month": 8,
            "name": "آبان",
            "start": 1,
            "end": 30,
            "startDay": 3
          },
          "9": {
            "month": 9,
            "name": "آذر",
            "start": 1,
            "end": 30,
            "startDay": 5
          },
          "10": {
            "month": 10,
            "name": "دی",
            "start": 1,
            "end": 30,
            "startDay": 7
          },
          "11": {
            "month": 11,
            "name": "بهمن",
            "start": 1,
            "end": 30,
            "startDay": 2
          },
          "12": {
            "month": 12,
            "name": "اسفند",
            "start": 1,
            "end": 29,
            "startDay": 4
          }
        }
      },
      "1398": {
        "name": "۱۳۹۸",
        "availableMonths": 
        [
          {
            "month": 1,
            "value": "فروردین"
          },
          {
            "month": 2,
            "value": "اردیبهشت"
          },
          {
            "month": 3,
            "value": "خرداد"
          },
          {
            "month": 4,
            "value": "تیر"
          },
          {
            "month": 5,
            "value": "مرداد"
          },
          {
            "month": 6,
            "value": "شهریور"
          },
          {
            "month": 7,
            "value": "مهر"
          },
          {
            "month": 8,
            "value": "آبان"
          },
          {
            "month": 9,
            "value": "آذر"
          },
          {
            "month": 10,
            "value": "دی"
          },
          {
            "month": 11,
            "value": "بهمن"
          },
          {
            "month": 12,
            "value": "اسفند"
          }
        ],
        "formation": {
          "1": {
            "month": 1,
            "name": "فروردین",
            "start": 1,
            "end": 31,
            "startDay": 5
          },
          "2": {
            "month": 2,
            "name": "اردیبهشت",
            "start": 1,
            "end": 31,
            "currentMonth": true,
            "active": 27,
            "startDay": 1
          },
          "3": {
            "month": 3,
            "name": "خرداد",
            "start": 1,
            "end": 31,
            "startDay": 4
          },
          "4": {
            "month": 4,
            "name": "تیر",
            "start": 1,
            "end": 31,
            "startDay": 7
          },
          "5": {
            "month": 5,
            "name": "مرداد",
            "start": 1,
            "end": 31,
            "startDay": 3
          },
          "6": {
            "month": 6,
            "name": "شهریور",
            "start": 1,
            "end": 31,
            "startDay": 6
          },
          "7": {
            "month": 7,
            "name": "مهر",
            "start": 1,
            "end": 30,
            "startDay": 2
          },
          "8": {
            "month": 8,
            "name": "آبان",
            "start": 1,
            "end": 30,
            "startDay": 4
          },
          "9": {
            "month": 9,
            "name": "آذر",
            "start": 1,
            "end": 30,
            "startDay": 6
          },
          "10": {
            "month": 10,
            "name": "دی",
            "start": 1,
            "end": 30,
            "startDay": 1
          },
          "11": {
            "month": 11,
            "name": "بهمن",
            "start": 1,
            "end": 30,
            "startDay": 3
          },
          "12": {
            "month": 12,
            "name": "اسفند",
            "start": 1,
            "end": 29,
            "startDay": 5
          }
        }
      },
      "1399": {
        "name": "۱۳۹۹",
        "availableMonths": 
        [
          {
            "month": 1,
            "value": "فروردین"
          },
          {
            "month": 2,
            "value": "اردیبهشت"
          },
          {
            "month": 3,
            "value": "خرداد"
          },
          {
            "month": 4,
            "value": "تیر"
          },
          {
            "month": 5,
            "value": "مرداد"
          },
          {
            "month": 6,
            "value": "شهریور"
          },
          {
            "month": 7,
            "value": "مهر"
          },
          {
            "month": 8,
            "value": "آبان"
          },
          {
            "month": 9,
            "value": "آذر"
          },
          {
            "month": 10,
            "value": "دی"
          },
          {
            "month": 11,
            "value": "بهمن"
          },
          {
            "month": 12,
            "value": "اسفند"
          }
        ],
        "formation": {
          "1": {
            "month": 1,
            "name": "فروردین",
            "start": 1,
            "end": 31,
            "startDay": 6
          },
          "2": {
            "month": 2,
            "name": "اردیبهشت",
            "start": 1,
            "end": 31,
            "startDay": 2
          },
          "3": {
            "month": 3,
            "name": "خرداد",
            "start": 1,
            "end": 31,
            "startDay": 5
          },
          "4": {
            "month": 4,
            "name": "تیر",
            "start": 1,
            "end": 31,
            "startDay": 1
          },
          "5": {
            "month": 5,
            "name": "مرداد",
            "start": 1,
            "end": 31,
            "startDay": 4
          },
          "6": {
            "month": 6,
            "name": "شهریور",
            "start": 1,
            "end": 31,
            "startDay": 7
          },
          "7": {
            "month": 7,
            "name": "مهر",
            "start": 1,
            "end": 30,
            "startDay": 3
          },
          "8": {
            "month": 8,
            "name": "آبان",
            "start": 1,
            "end": 30,
            "startDay": 5
          },
          "9": {
            "month": 9,
            "name": "آذر",
            "start": 1,
            "end": 30,
            "startDay": 7
          },
          "10": {
            "month": 10,
            "name": "دی",
            "start": 1,
            "end": 30,
            "startDay": 2
          },
          "11": {
            "month": 11,
            "name": "بهمن",
            "start": 1,
            "end": 30,
            "startDay": 4
          },
          "12": {
            "month": 12,
            "name": "اسفند",
            "start": 1,
            "end": 30,
            "startDay": 6
          }
        }
      },
      "1400": {
        "name": "۱۴۰۰",
        "availableMonths": 
        [
          {
            "month": 1,
            "value": "فروردین"
          },
          {
            "month": 2,
            "value": "اردیبهشت"
          }
        ],
        "formation": {
          "1": {
            "month": 1,
            "name": "فروردین",
            "start": 1,
            "end": 31,
            "startDay": 1
          },
          "2": {
            "month": 2,
            "name": "اردیبهشت",
            "start": 1,
            "end": 31,
            "startDay": 4
          }
        }
      }
    }
  },
  "selectDate": false,
  "daysOfWeek": 
  [
    {
      "dow": 7,
      "value": "شنبه",
      "weekend": false
    },
    {
      "dow": 1,
      "value": "یکشنبه",
      "weekend": false
    },
    {
      "dow": 2,
      "value": "دوشنبه",
      "weekend": false
    },
    {
      "dow": 3,
      "value": "سه‌شنبه",
      "weekend": false
    },
    {
      "dow": 4,
      "value": "چهارشنبه",
      "weekend": false
    },
    {
      "dow": 5,
      "value": "پنجشنبه",
      "weekend": false
    },
    {
      "dow": 6,
      "value": "جمعه",
      "weekend": true
    }
  ],
  "daysOfMonth": 
  [
    {
      "day": 1,
      "value": "۱"
    },
    {
      "day": 2,
      "value": "۲"
    },
    {
      "day": 3,
      "value": "۳"
    },
    {
      "day": 4,
      "value": "۴"
    },
    {
      "day": 5,
      "value": "۵"
    },
    {
      "day": 6,
      "value": "۶"
    },
    {
      "day": 7,
      "value": "۷"
    },
    {
      "day": 8,
      "value": "۸"
    },
    {
      "day": 9,
      "value": "۹"
    },
    {
      "day": 10,
      "value": "۱۰"
    },
    {
      "day": 11,
      "value": "۱۱"
    },
    {
      "day": 12,
      "value": "۱۲"
    },
    {
      "day": 13,
      "value": "۱۳"
    },
    {
      "day": 14,
      "value": "۱۴"
    },
    {
      "day": 15,
      "value": "۱۵"
    },
    {
      "day": 16,
      "value": "۱۶"
    },
    {
      "day": 17,
      "value": "۱۷"
    },
    {
      "day": 18,
      "value": "۱۸"
    },
    {
      "day": 19,
      "value": "۱۹"
    },
    {
      "day": 20,
      "value": "۲۰"
    },
    {
      "day": 21,
      "value": "۲۱"
    },
    {
      "day": 22,
      "value": "۲۲"
    },
    {
      "day": 23,
      "value": "۲۳"
    },
    {
      "day": 24,
      "value": "۲۴"
    },
    {
      "day": 25,
      "value": "۲۵"
    },
    {
      "day": 26,
      "value": "۲۶"
    },
    {
      "day": 27,
      "value": "۲۷"
    },
    {
      "day": 28,
      "value": "۲۸"
    },
    {
      "day": 29,
      "value": "۲۹"
    },
    {
      "day": 30,
      "value": "۳۰"
    },
    {
      "day": 31,
      "value": "۳۱"
    }
  ],
  "monthsOfYear": 
  [
    {
      "month": 1,
      "value": "فروردین"
    },
    {
      "month": 2,
      "value": "اردیبهشت"
    },
    {
      "month": 3,
      "value": "خرداد"
    },
    {
      "month": 4,
      "value": "تیر"
    },
    {
      "month": 5,
      "value": "مرداد"
    },
    {
      "month": 6,
      "value": "شهریور"
    },
    {
      "month": 7,
      "value": "مهر"
    },
    {
      "month": 8,
      "value": "آبان"
    },
    {
      "month": 9,
      "value": "آذر"
    },
    {
      "month": 10,
      "value": "دی"
    },
    {
      "month": 11,
      "value": "بهمن"
    },
    {
      "month": 12,
      "value": "اسفند"
    }
  ]
}
```

index4 produces:

```
{
  "dataSet": {
    "monthData": {
      "today": "17/May/2019",
      "year": 2562,
      "month": 5,
      "day": 17,
      "startDate": "17/Apr/2019",
      "endDate": "17/Jun/2019"
    },
    "results": {
      "2562": {
        "name": "2562",
        "formation": {
          "4": {
            "month": 4,
            "name": "เม.ย.",
            "start": 17,
            "end": 30,
            "firstMonth": true,
            "startDay": 4
          },
          "5": {
            "month": 5,
            "name": "พ.ค.",
            "start": 1,
            "end": 31,
            "currentMonth": true,
            "active": 17,
            "startDay": 4
          },
          "6": {
            "month": 6,
            "name": "มิ.ย.",
            "start": 1,
            "end": 17,
            "lastMonth": true,
            "startDay": 7
          }
        }
      }
    }
  },
  "selectDate": false,
  "daysOfWeek": 
  [
    {
      "dow": 1,
      "value": "วันอาทิตย์",
      "weekend": true
    },
    {
      "dow": 2,
      "value": "วันจันทร์",
      "weekend": false
    },
    {
      "dow": 3,
      "value": "วันอังคาร",
      "weekend": false
    },
    {
      "dow": 4,
      "value": "วันพุธ",
      "weekend": false
    },
    {
      "dow": 5,
      "value": "วันพฤหัสบดี",
      "weekend": false
    },
    {
      "dow": 6,
      "value": "วันศุกร์",
      "weekend": false
    },
    {
      "dow": 7,
      "value": "วันเสาร์",
      "weekend": true
    }
  ],
  "daysOfMonth": 
  [
    {
      "day": 1,
      "value": "1"
    },
    {
      "day": 2,
      "value": "2"
    },
    {
      "day": 3,
      "value": "3"
    },
    {
      "day": 4,
      "value": "4"
    },
    {
      "day": 5,
      "value": "5"
    },
    {
      "day": 6,
      "value": "6"
    },
    {
      "day": 7,
      "value": "7"
    },
    {
      "day": 8,
      "value": "8"
    },
    {
      "day": 9,
      "value": "9"
    },
    {
      "day": 10,
      "value": "10"
    },
    {
      "day": 11,
      "value": "11"
    },
    {
      "day": 12,
      "value": "12"
    },
    {
      "day": 13,
      "value": "13"
    },
    {
      "day": 14,
      "value": "14"
    },
    {
      "day": 15,
      "value": "15"
    },
    {
      "day": 16,
      "value": "16"
    },
    {
      "day": 17,
      "value": "17"
    },
    {
      "day": 18,
      "value": "18"
    },
    {
      "day": 19,
      "value": "19"
    },
    {
      "day": 20,
      "value": "20"
    },
    {
      "day": 21,
      "value": "21"
    },
    {
      "day": 22,
      "value": "22"
    },
    {
      "day": 23,
      "value": "23"
    },
    {
      "day": 24,
      "value": "24"
    },
    {
      "day": 25,
      "value": "25"
    },
    {
      "day": 26,
      "value": "26"
    },
    {
      "day": 27,
      "value": "27"
    },
    {
      "day": 28,
      "value": "28"
    },
    {
      "day": 29,
      "value": "29"
    },
    {
      "day": 30,
      "value": "30"
    },
    {
      "day": 31,
      "value": "31"
    }
  ],
  "monthsOfYear": 
  [
    {
      "month": 1,
      "value": "ม.ค."
    },
    {
      "month": 2,
      "value": "ก.พ."
    },
    {
      "month": 3,
      "value": "มี.ค."
    },
    {
      "month": 4,
      "value": "เม.ย."
    },
    {
      "month": 5,
      "value": "พ.ค."
    },
    {
      "month": 6,
      "value": "มิ.ย."
    },
    {
      "month": 7,
      "value": "ก.ค."
    },
    {
      "month": 8,
      "value": "ส.ค."
    },
    {
      "month": 9,
      "value": "ก.ย."
    },
    {
      "month": 10,
      "value": "ต.ค."
    },
    {
      "month": 11,
      "value": "พ.ย."
    },
    {
      "month": 12,
      "value": "ธ.ค."
    }
  ]
}
```