# json-csv-microservice
Sofixit interview

## Requirements

* gitbash or similar
* docker
* postman or just browser

## How to run

* Clone repo
* Run command `docker-compose up`

Application should be available on:
* jsonGenerator: `http://localhost:8084`
* csvConverter: `http://localhost:8085`

## API

### CsvConverter
* Download csv for given number of data

`/downloadCsv/{size}` **

* Download csv for given number of data and parameters

`/downloadCsv/{size}/filter?params={params}` **

List of accessible parameters:
{params} = {_id,_type,type,key,name,fullName,iata_airport_code,country,latitude,longitude,location_id,inEurope,countryCode,coreCountry,distance}

### JsonGenerator
* Generate json for given number of data

`/generate/json/{size}` **

* Generate json for given number of data in flatten structure 

`/generate/json/{size}/flatten` **

### Open-api

For both services swagger is accessible from the browser:

* jsonGenerator: `http://localhost:8084/swagger-ui/index.html`
* csvConverter: `http://localhost:8085/swagger-ui/index.html`



** {size} should be a number
