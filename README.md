Below you will see:

```
  "2017": {
        "name": "۱۳۹۶",
```     
        
2017 = ۱۳۹۶  which translates to 1396 

In short the conversion provides a basic template of day names mapped to locale, month names mapped to locale, then a dataSet containing the actual date range with minimal date of start day of each month to be included and its end day - with the other maps using javascript will build a front view to pick from available dates provided via the back end arrays -  
   

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
		Icu4jHelper icuj = new Icu4jHelper(new Locale("hi","IN"),new Date(),1,-2,IncrementMethod.MONTH)
		render icuj.init()
	}
	
	def index3() {
		Icu4jHelper icuj = new Icu4jHelper(new Locale("fa","IR"),new Date(),-2,2,IncrementMethod.YEAR)
		render icuj.init()
	}
	
	def index4() {
		Icu4jHelper icuj = new Icu4jHelper(new Locale("vi","VI"),new Date(),-1,1,IncrementMethod.MONTH)
		render icuj.init()
	}
	
}
```   

index - produces:

```
{
  "monthsOfYear": 
  [
    {
      "month": 1,
      "value": "January"
    },
    {
      "month": 2,
      "value": "February"
    },
    {
      "month": 3,
      "value": "March"
    },
    {
      "month": 4,
      "value": "April"
    },
    {
      "month": 5,
      "value": "May"
    },
    {
      "month": 6,
      "value": "June"
    },
    {
      "month": 7,
      "value": "July"
    },
    {
      "month": 8,
      "value": "August"
    },
    {
      "month": 9,
      "value": "September"
    },
    {
      "month": 10,
      "value": "October"
    },
    {
      "month": 11,
      "value": "November"
    },
    {
      "month": 12,
      "value": "December"
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
      "month": 4,
      "value": "Tuesday"
    },
    {
      "month": 8,
      "value": "Wednesday"
    },
    {
      "month": 16,
      "value": "Thursday"
    },
    {
      "month": 32,
      "value": "Friday"
    },
    {
      "month": 64,
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
  ],
  "dataSet": 
  [
    {
      "2019": {
        "name": "2019",
        "formation": 
        [
          {
            "month": 2,
            "name": "February",
            "start": 1,
            "end": 31
          },
          {
            "month": 3,
            "name": "March",
            "start": 1,
            "end": 30
          },
          {
            "month": 4,
            "name": "April",
            "start": 1,
            "end": 31
          },
          {
            "month": 5,
            "name": "May",
            "start": 1,
            "end": 30
          },
          {
            "month": 6,
            "name": "June",
            "start": 1,
            "end": 31
          },
          {
            "month": 7,
            "name": "July",
            "start": 1,
            "end": 31
          },
          {
            "month": 8,
            "name": "August",
            "start": 1,
            "end": 30
          }
        ],
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
        ]
      }
    }
  ]
}
```

index2 produces:

```
{
  "monthsOfYear": 
  [
    {
      "month": 1,
      "value": "फ़रवरी"
    },
    {
      "month": 2,
      "value": "मार्च"
    },
    {
      "month": 3,
      "value": "अप्रैल"
    },
    {
      "month": 4,
      "value": "मई"
    },
    {
      "month": 5,
      "value": "जून"
    },
    {
      "month": 6,
      "value": "जुलाई"
    },
    {
      "month": 7,
      "value": "अगस्त"
    },
    {
      "month": 8,
      "value": "सितंबर"
    },
    {
      "month": 9,
      "value": "अक्तूबर"
    },
    {
      "month": 10,
      "value": "नवंबर"
    },
    {
      "month": 11,
      "value": "दिसंबर"
    },
    {
      "month": 12,
      "value": "जनवरी"
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
      "month": 4,
      "value": "मंगलवार"
    },
    {
      "month": 8,
      "value": "बुधवार"
    },
    {
      "month": 16,
      "value": "गुरुवार"
    },
    {
      "month": 32,
      "value": "शुक्रवार"
    },
    {
      "month": 64,
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
  ],
  "dataSet": 
  [
    {
      "2019": {
        "name": "2019",
        "months": 
        [
        ]
      }
    }
  ]
}
```

index3 produces:

```
{
  "monthsOfYear": 
  [
    {
      "month": 1,
      "value": "بهمن"
    },
    {
      "month": 2,
      "value": "اسفند"
    },
    {
      "month": 3,
      "value": "فروردین"
    },
    {
      "month": 4,
      "value": "اردیبهشت"
    },
    {
      "month": 5,
      "value": "خرداد"
    },
    {
      "month": 6,
      "value": "تیر"
    },
    {
      "month": 7,
      "value": "مرداد"
    },
    {
      "month": 8,
      "value": "شهریور"
    },
    {
      "month": 9,
      "value": "مهر"
    },
    {
      "month": 10,
      "value": "آبان"
    },
    {
      "month": 11,
      "value": "آذر"
    },
    {
      "month": 12,
      "value": "دی"
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
      "month": 4,
      "value": "سه‌شنبه"
    },
    {
      "month": 8,
      "value": "چهارشنبه"
    },
    {
      "month": 16,
      "value": "پنجشنبه"
    },
    {
      "month": 32,
      "value": "جمعه"
    },
    {
      "month": 64,
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
  ],
  "dataSet": 
  [
    {
      "2017": {
        "name": "۱۳۹۶",
        "months": 
        [
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
      "2018": {
        "name": "۱۳۹۷",
        "months": 
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
        "months": 
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
      "2020": {
        "name": "۱۳۹۹",
        "months": 
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
        "months": 
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
          }
        ]
      }
    }
  ]
}
```

index4 produces:

```
{
  "monthsOfYear": 
  [
    {
      "month": 1,
      "value": "tháng 2"
    },
    {
      "month": 2,
      "value": "tháng 3"
    },
    {
      "month": 3,
      "value": "tháng 4"
    },
    {
      "month": 4,
      "value": "tháng 5"
    },
    {
      "month": 5,
      "value": "tháng 6"
    },
    {
      "month": 6,
      "value": "tháng 7"
    },
    {
      "month": 7,
      "value": "tháng 8"
    },
    {
      "month": 8,
      "value": "tháng 9"
    },
    {
      "month": 9,
      "value": "tháng 10"
    },
    {
      "month": 10,
      "value": "tháng 11"
    },
    {
      "month": 11,
      "value": "tháng 12"
    },
    {
      "month": 12,
      "value": "tháng 1"
    }
  ],
  "daysOfWeek": 
  [
    {
      "month": 1,
      "value": "Chủ Nhật"
    },
    {
      "month": 2,
      "value": "Thứ Hai"
    },
    {
      "month": 4,
      "value": "Thứ Ba"
    },
    {
      "month": 8,
      "value": "Thứ Tư"
    },
    {
      "month": 16,
      "value": "Thứ Năm"
    },
    {
      "month": 32,
      "value": "Thứ Sáu"
    },
    {
      "month": 64,
      "value": "Thứ Bảy"
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
  "dataSet": 
  [
    {
      "2019": {
        "name": "2019",
        "months": 
        [
          {
            "month": 4,
            "name": "tháng 5",
            "start": 1,
            "end": 31
          },
          {
            "month": 5,
            "name": "tháng 6",
            "start": 1,
            "end": 30
          },
          {
            "month": 6,
            "name": "tháng 7",
            "start": 1,
            "end": 31
          }
        ]
      }
    }
  ]
}
```