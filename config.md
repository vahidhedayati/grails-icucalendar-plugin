Grails 2 : config.groovy


```
grails.databinding.dateFormats = [
	'dd MMM yyyy', 'dd MMMM yyyy','dd/MM/yyyy', 'MMddyyyy', 'yyyy-MM-dd HH:mm:ss.S', "yyyy-MM-dd'T'hh:mm:ss'Z'"
] 
```


Grails 3+ Config:

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