Grails 2 : config.groovy


```
grails.databinding.dateFormats = [
	'dd MMM yyyy', 'dd MMMM yyyy','dd/MM/yyyy', 'MMddyyyy', 'yyyy-MM-dd HH:mm:ss.S', "yyyy-MM-dd'T'hh:mm:ss'Z'"
]
```
![sample image](https://raw.githubusercontent.com/vahidhedayati/grails-icucalendar-plugin/master/docs/basic-calendar-conversion-from_en_gb-to-fa_IR.png)

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
  "dataSet": 
  [
    {
      "availableMonths": 
      [
        {
          "enumType": "grails.utils.enums.MonthsOfYear",
          "name": "FEB"
        },
        {
          "enumType": "grails.utils.enums.MonthsOfYear",
          "name": "MAR"
        },
        {
          "enumType": "grails.utils.enums.MonthsOfYear",
          "name": "APR"
        },
        {
          "enumType": "grails.utils.enums.MonthsOfYear",
          "name": "MAY"
        },
        {
          "enumType": "grails.utils.enums.MonthsOfYear",
          "name": "JUN"
        },
        {
          "enumType": "grails.utils.enums.MonthsOfYear",
          "name": "JUL"
        },
        {
          "enumType": "grails.utils.enums.MonthsOfYear",
          "name": "AUG"
        }
      ],
      "today": "11/May/2019",
      "startDate": "10/Feb/2019",
      "endDate": "09/Aug/2019",
      "2019": {
        "name": "2019",
        "formation": 
        [
          {
            "month": 2,
            "name": "March",
            "start": 1,
            "end": 31,
            "firstDay": 10
          },
          {
            "month": 3,
            "name": "April",
            "start": 1,
            "end": 30
          },
          {
            "month": 4,
            "name": "May",
            "start": 1,
            "end": 31
          },
          {
            "month": 5,
            "name": "June",
            "start": 1,
            "end": 30,
            "currentDay": 11
          },
          {
            "month": 6,
            "name": "July",
            "start": 1,
            "end": 31
          },
          {
            "month": 7,
            "name": "August",
            "start": 1,
            "end": 31
          },
          {
            "month": 8,
            "name": "September",
            "start": 1,
            "end": 30,
            "lastDay": 9
          }
        ]
      }
    }
  ],
  "daysOfWeek": 
  [
    {
      "month": 1,
      "value": "Sunday"
    },
    {
      "month": 2,
      "value": "Monday"
    },
    {
      "month": 3,
      "value": "Tuesday"
    },
    {
      "month": 4,
      "value": "Wednesday"
    },
    {
      "month": 5,
      "value": "Thursday"
    },
    {
      "month": 6,
      "value": "Friday"
    },
    {
      "month": 7,
      "value": "Saturday"
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
  ]
}
```

index2 produces:

```
{
  "dataSet": 
  [
    {
      "availableMonths": 
      [
        {
          "enumType": "grails.utils.enums.MonthsOfYear",
          "name": "MAR"
        },
        {
          "enumType": "grails.utils.enums.MonthsOfYear",
          "name": "APR"
        },
        {
          "enumType": "grails.utils.enums.MonthsOfYear",
          "name": "MAY"
        },
        {
          "enumType": "grails.utils.enums.MonthsOfYear",
          "name": "JUN"
        }
      ],
      "today": "11/May/2019",
      "startDate": "11/Mar/2019",
      "endDate": "11/Jun/2019",
      "2019": {
        "name": "2019",
        "formation": 
        [
          {
            "month": 3,
            "name": "अप्रैल",
            "start": 1,
            "end": 30,
            "firstDay": 11
          },
          {
            "month": 4,
            "name": "मई",
            "start": 1,
            "end": 31
          },
          {
            "month": 5,
            "name": "जून",
            "start": 1,
            "end": 30,
            "currentDay": 11
          },
          {
            "month": 6,
            "name": "जुलाई",
            "start": 1,
            "end": 31,
            "lastDay": 11
          }
        ]
      }
    }
  ],
  "daysOfWeek": 
  [
    {
      "month": 1,
      "value": "रविवार"
    },
    {
      "month": 2,
      "value": "सोमवार"
    },
    {
      "month": 3,
      "value": "मंगलवार"
    },
    {
      "month": 4,
      "value": "बुधवार"
    },
    {
      "month": 5,
      "value": "गुरुवार"
    },
    {
      "month": 6,
      "value": "शुक्रवार"
    },
    {
      "month": 7,
      "value": "शनिवार"
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
  ]
}
```

index3 produces:

```
{
  "dataSet": 
  [
    {
      "availableMonths": 
      [
        {
          "enumType": "grails.utils.enums.MonthsOfYear",
          "name": "JAN"
        },
        {
          "enumType": "grails.utils.enums.MonthsOfYear",
          "name": "FEB"
        },
        {
          "enumType": "grails.utils.enums.MonthsOfYear",
          "name": "MAR"
        },
        {
          "enumType": "grails.utils.enums.MonthsOfYear",
          "name": "APR"
        },
        {
          "enumType": "grails.utils.enums.MonthsOfYear",
          "name": "MAY"
        },
        {
          "enumType": "grails.utils.enums.MonthsOfYear",
          "name": "JUN"
        },
        {
          "enumType": "grails.utils.enums.MonthsOfYear",
          "name": "JUL"
        },
        {
          "enumType": "grails.utils.enums.MonthsOfYear",
          "name": "AUG"
        },
        {
          "enumType": "grails.utils.enums.MonthsOfYear",
          "name": "SEP"
        },
        {
          "enumType": "grails.utils.enums.MonthsOfYear",
          "name": "OCT"
        },
        {
          "enumType": "grails.utils.enums.MonthsOfYear",
          "name": "NOV"
        },
        {
          "enumType": "grails.utils.enums.MonthsOfYear",
          "name": "DEC"
        }
      ],
      "today": "11/May/2019",
      "startDate": "11/May/2017",
      "endDate": "11/May/2021",
      "2017": {
        "name": "۱۳۹۶",
        "formation": 
        [
          {
            "month": 5,
            "name": "خرداد",
            "start": 1,
            "end": 31,
            "firstDay": 11
          },
          {
            "month": 6,
            "name": "تیر",
            "start": 1,
            "end": 31
          },
          {
            "month": 7,
            "name": "مرداد",
            "start": 1,
            "end": 31
          },
          {
            "month": 8,
            "name": "شهریور",
            "start": 1,
            "end": 31
          },
          {
            "month": 9,
            "name": "مهر",
            "start": 1,
            "end": 30
          },
          {
            "month": 10,
            "name": "آبان",
            "start": 1,
            "end": 30
          },
          {
            "month": 11,
            "name": "آذر",
            "start": 1,
            "end": 30
          },
          {
            "month": 12,
            "name": "دی",
            "start": 1,
            "end": 30
          }
        ]
      },
      "2018": {
        "name": "۱۳۹۷",
        "formation": 
        [
          {
            "month": 1,
            "name": "بهمن",
            "start": 1,
            "end": 30
          },
          {
            "month": 2,
            "name": "اسفند",
            "start": 1,
            "end": 29
          },
          {
            "month": 3,
            "name": "فروردین",
            "start": 1,
            "end": 31
          },
          {
            "month": 4,
            "name": "اردیبهشت",
            "start": 1,
            "end": 31
          },
          {
            "month": 5,
            "name": "خرداد",
            "start": 1,
            "end": 31
          },
          {
            "month": 6,
            "name": "تیر",
            "start": 1,
            "end": 31
          },
          {
            "month": 7,
            "name": "مرداد",
            "start": 1,
            "end": 31
          },
          {
            "month": 8,
            "name": "شهریور",
            "start": 1,
            "end": 31
          },
          {
            "month": 9,
            "name": "مهر",
            "start": 1,
            "end": 30
          },
          {
            "month": 10,
            "name": "آبان",
            "start": 1,
            "end": 30
          },
          {
            "month": 11,
            "name": "آذر",
            "start": 1,
            "end": 30
          },
          {
            "month": 12,
            "name": "دی",
            "start": 1,
            "end": 30
          }
        ]
      },
      "2019": {
        "name": "۱۳۹۸",
        "formation": 
        [
          {
            "month": 1,
            "name": "بهمن",
            "start": 1,
            "end": 30
          },
          {
            "month": 2,
            "name": "اسفند",
            "start": 1,
            "end": 29
          },
          {
            "month": 3,
            "name": "فروردین",
            "start": 1,
            "end": 31
          },
          {
            "month": 4,
            "name": "اردیبهشت",
            "start": 1,
            "end": 31
          },
          {
            "month": 5,
            "name": "خرداد",
            "start": 1,
            "end": 31,
            "currentDay": 11
          },
          {
            "month": 6,
            "name": "تیر",
            "start": 1,
            "end": 31
          },
          {
            "month": 7,
            "name": "مرداد",
            "start": 1,
            "end": 31
          },
          {
            "month": 8,
            "name": "شهریور",
            "start": 1,
            "end": 31
          },
          {
            "month": 9,
            "name": "مهر",
            "start": 1,
            "end": 30
          },
          {
            "month": 10,
            "name": "آبان",
            "start": 1,
            "end": 30
          },
          {
            "month": 11,
            "name": "آذر",
            "start": 1,
            "end": 30
          },
          {
            "month": 12,
            "name": "دی",
            "start": 1,
            "end": 30
          }
        ]
      },
      "2020": {
        "name": "۱۳۹۹",
        "formation": 
        [
          {
            "month": 1,
            "name": "بهمن",
            "start": 1,
            "end": 30
          },
          {
            "month": 2,
            "name": "اسفند",
            "start": 1,
            "end": 29
          },
          {
            "month": 3,
            "name": "فروردین",
            "start": 1,
            "end": 31
          },
          {
            "month": 4,
            "name": "اردیبهشت",
            "start": 1,
            "end": 31
          },
          {
            "month": 5,
            "name": "خرداد",
            "start": 1,
            "end": 31
          },
          {
            "month": 6,
            "name": "تیر",
            "start": 1,
            "end": 31
          },
          {
            "month": 7,
            "name": "مرداد",
            "start": 1,
            "end": 31
          },
          {
            "month": 8,
            "name": "شهریور",
            "start": 1,
            "end": 31
          },
          {
            "month": 9,
            "name": "مهر",
            "start": 1,
            "end": 30
          },
          {
            "month": 10,
            "name": "آبان",
            "start": 1,
            "end": 30
          },
          {
            "month": 11,
            "name": "آذر",
            "start": 1,
            "end": 30
          },
          {
            "month": 12,
            "name": "دی",
            "start": 1,
            "end": 30
          }
        ]
      },
      "2021": {
        "name": "۱۴۰۰",
        "formation": 
        [
          {
            "month": 1,
            "name": "بهمن",
            "start": 1,
            "end": 30
          },
          {
            "month": 2,
            "name": "اسفند",
            "start": 1,
            "end": 30
          },
          {
            "month": 3,
            "name": "فروردین",
            "start": 1,
            "end": 31
          },
          {
            "month": 4,
            "name": "اردیبهشت",
            "start": 1,
            "end": 31
          },
          {
            "month": 5,
            "name": "خرداد",
            "start": 1,
            "end": 31,
            "lastDay": 11
          }
        ]
      }
    }
  ],
  "daysOfWeek": 
  [
    {
      "month": 1,
      "value": "یکشنبه"
    },
    {
      "month": 2,
      "value": "دوشنبه"
    },
    {
      "month": 3,
      "value": "سه‌شنبه"
    },
    {
      "month": 4,
      "value": "چهارشنبه"
    },
    {
      "month": 5,
      "value": "پنجشنبه"
    },
    {
      "month": 6,
      "value": "جمعه"
    },
    {
      "month": 7,
      "value": "شنبه"
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
  ]
}
```

index4 produces:

```
{
  "dataSet": 
  [
    {
      "availableMonths": 
      [
        {
          "enumType": "grails.utils.enums.MonthsOfYear",
          "name": "APR"
        },
        {
          "enumType": "grails.utils.enums.MonthsOfYear",
          "name": "MAY"
        },
        {
          "enumType": "grails.utils.enums.MonthsOfYear",
          "name": "JUN"
        }
      ],
      "today": "11/May/2019",
      "startDate": "11/Apr/2019",
      "endDate": "11/Jun/2019",
      "2019": {
        "name": "2562",
        "formation": 
        [
          {
            "month": 4,
            "name": "พฤษภาคม",
            "start": 1,
            "end": 31,
            "firstDay": 11
          },
          {
            "month": 5,
            "name": "มิถุนายน",
            "start": 1,
            "end": 30,
            "currentDay": 11
          },
          {
            "month": 6,
            "name": "กรกฎาคม",
            "start": 1,
            "end": 31,
            "lastDay": 11
          }
        ]
      }
    }
  ],
  "daysOfWeek": 
  [
    {
      "month": 1,
      "value": "วันอาทิตย์"
    },
    {
      "month": 2,
      "value": "วันจันทร์"
    },
    {
      "month": 3,
      "value": "วันอังคาร"
    },
    {
      "month": 4,
      "value": "วันพุธ"
    },
    {
      "month": 5,
      "value": "วันพฤหัสบดี"
    },
    {
      "month": 6,
      "value": "วันศุกร์"
    },
    {
      "month": 7,
      "value": "วันเสาร์"
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
  ]
}
```