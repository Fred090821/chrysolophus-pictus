# Sample Java Project



## Setup
just pull the code and run as a springboot project in intellij or any IDE

## Test
tests are in the test folder
 
Swagger located at 
http://localhost:8080/swagger-ui/index.html

 h2 console is available at http://localhost:8080/h2-console
 
 ## usage in postman, sample data


1- CREATE 1 SENSORS FOR EXAMPLE
each sensor will have a some metrics

Post url : http://localhost:8080/api/sensors

Body : 

{
    "type": "Weather",
    "status": "active",
    "longitude": 20.23,
    "latitude": 12.87,
    "ipAddress": "86.12.43.1",
    "city": "Dublin"
}


2 THE ID OF THE SENSOR JUST CREATED WILL BE 1 AND USED IN THE URL 
use it to post metrics to the db, this simulate a sensor sending metrics to the server

Post url: http://localhost:8080/api/metrics/sensor/1

Body:

[
{
    "name": "fred",
    "origin": "Math",
    "timestamp": 23,
    "temperature": 6.0,
    "humidity": 40.0,
    "windSpeed": 23,
     "windDirection": 9
},
{
    "name": "fred",
    "origin": "Math",
    "timestamp": 234,
    "temperature": 6.0,
    "humidity": 40.0,
    "windSpeed": 23,
     "windDirection": 9
},
{
    "name": "fred",
    "origin": "Math",
    "timestamp": 2345,
    "temperature": 12.0,
    "humidity": 40.0,
    "windSpeed": 23,
     "windDirection": 9
},
{
    "name": "fred",
    "origin": "Math",
    "timestamp": 23456,
    "temperature": 0.0,
    "humidity": 80.0,
    "windSpeed": 23,
     "windDirection": 9
},
{
    "name": "fred",
    "origin": "Math",
    "timestamp": 234567,
    "temperature": 6.0,
    "humidity": 0.0,
    "windSpeed": 23,
     "windDirection": 9
}


]


json result :

{
    "created": [
        {
            "createdAt": "2022-12-06T04:38:08.782+00:00",
            "id": 1,
            "name": "fred",
            "temperature": 6.0,
            "humidity": 40.0,
            "sensorId": 1,
            "windSpeed": 23,
            "windDirection": 9
        },
        {
            "createdAt": "2022-12-06T04:38:08.783+00:00",
            "id": 2,
            "name": "fred",
            "temperature": 6.0,
            "humidity": 40.0,
            "sensorId": 1,
            "windSpeed": 23,
            "windDirection": 9
        },
        {
            "createdAt": "2022-12-06T04:38:08.784+00:00",
            "id": 3,
            "name": "fred",
            "temperature": 12.0,
            "humidity": 40.0,
            "sensorId": 1,
            "windSpeed": 23,
            "windDirection": 9
        },
        {
            "createdAt": "2022-12-06T04:38:08.786+00:00",
            "id": 4,
            "name": "fred",
            "temperature": 0.0,
            "humidity": 80.0,
            "sensorId": 1,
            "windSpeed": 23,
            "windDirection": 9
        },
        {
            "createdAt": "2022-12-06T04:38:08.787+00:00",
            "id": 5,
            "name": "fred",
            "temperature": 6.0,
            "humidity": 0.0,
            "sensorId": 1,
            "windSpeed": 23,
            "windDirection": 9
        }
    ],
    "failed": []
}



3 - GET ALL SENSORS

URL : http://localhost:8080/api/sensors

JSON RESULT :

[
    {
        "createdAt": "2022-12-04T20:53:31.445+00:00",
        "id": 1,
        "type": "Weather",
        "status": "ACTIVE",
        "latitude": 12.87,
        "longitude": 20.23,
        "ipAddress": "86.12.43.1",
        "city": "Dublin"
    }
]



4 - GET ALL METRICS

url : http://localhost:8080/api/metrics

Body :

{
    "1": [
        {
            "createdAt": "2022-12-04T20:53:40.505+00:00",
            "id": 1,
            "name": "fred",
            "temperature": 6.0,
            "humidity": 40.0,
            "sensorId": 1,
            "windSpeed": 23,
            "windDirection": 9
        },
        {
            "createdAt": "2022-12-04T20:53:40.506+00:00",
            "id": 2,
            "name": "fred",
            "temperature": 6.0,
            "humidity": 40.0,
            "sensorId": 1,
            "windSpeed": 23,
            "windDirection": 9
        },
        {
            "createdAt": "2022-12-04T20:53:40.507+00:00",
            "id": 3,
            "name": "fred",
            "temperature": 12.0,
            "humidity": 40.0,
            "sensorId": 1,
            "windSpeed": 23,
            "windDirection": 9
        },
        {
            "createdAt": "2022-12-04T20:53:40.508+00:00",
            "id": 4,
            "name": "fred",
            "temperature": 0.0,
            "humidity": 80.0,
            "sensorId": 1,
            "windSpeed": 23,
            "windDirection": 9
        },
        {
            "createdAt": "2022-12-04T20:53:40.510+00:00",
            "id": 5,
            "name": "fred",
            "temperature": 6.0,
            "humidity": 0.0,
            "sensorId": 1,
            "windSpeed": 23,
            "windDirection": 9
        }
    ]
}


5 - GET STATS

url : http://localhost:8080/api/metrics/stats?range=today&sensor=1

Body :

[
    {
        "sensorId": 1,
        "averageHumidity": 40.0,
        "averageTemperature": 6.0,
        "maxHumidity": 80.0,
        "maxTemperature": 12.0,
        "rangeFrom": "06-Dec-2022 00:00:00",
        "rangeTo": "06-Dec-2022 08:08:51"
    }
]



6 - PATCH : 

Patch URL : http://localhost:8080/api/sensors/1

Body :
{
    "status": "active",
    "longitude": 0.0,
    "latitude": 0.0
}


Result:
{
    "createdAt": "2022-12-06T08:05:59.643+00:00",
    "id": 1,
    "type": "Weather",
    "status": "ACTIVE",
    "latitude": 0.0,
    "longitude": 0.0,
    "ipAddress": "86.12.43.1",
    "city": "Dublin"
}

