# SmartHost puzzle project

## Running
From the terminal:
```dtd
 ✗ ./gradlew bootRun
```
Or via IDE via the default Spring Boot development runners.

This will start up the Sprint Boot web app and run it on Tomcat on the default port 8080.

In order to use the service via the JSON api send GET requests to the `/occupancy/matchRooms` endpoint either from a browser of via curl.
Pass in `freeEconomyRooms` and `freePremiumRooms` integer parameters to change the number of available rooms.

Examples:

http://localhost:8080/occupancy/matchRooms?freeEconomyRooms=1&freePremiumRooms=7
http://localhost:8080/occupancy/matchRooms?freeEconomyRooms=3&freePremiumRooms=3

```dtd
➜ curl -XGET 'http://localhost:8080/occupancy/matchRooms?freeEconomyRooms=1&freePremiumRooms=7'
{"ECONOMY":{"takenRooms":1,"totalIncome":4500},"PREMIUM":{"takenRooms":7,"totalIncome":115399}}%                                                                  ```
```


## TODO (things that could actually make a difference, out of tens of all potential improvements)
- input parameters validation
- tests for unhappy paths and errors
- Spring Boot actuator (hot reload, etc.)
- cache if bids don't change between API calls
- business logic optimisation, improve performance if the flow can be simplified and turns out the be a bottleneck