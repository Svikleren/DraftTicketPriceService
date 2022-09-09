# DraftTicketPriceService

Service for travel agencies to provide draft ticket price.

## Technologies used:
- Java 8
- Spring Boot
- JUnit5
- Mockito
- Gradle
- Lombok

## Service API:
- api: /draft-ticket-price
- method: POST
- request body example:
```json
{
    "route": "Vilnius",
    "passengerRequestDtoList": [
        {
        "luggageCount":2,
        "child":false
        },
        {
        "luggageCount":1,
        "child":true
        }
     ]
  }
```
- response body example:
```json
{
    "route": "Vilnius",
    "vat": 0.21,
    "totalOrderPrice": 29.040,
    "passengerDtoList": [
        {
            "totalPassengerOrderPrice": 19.360,
            "ticketResponseDtoList": [
                {
                    "ticketType": "ADULT",
                    "ticketPriceWithoutVat": 10,
                    "vat": 2.10,
                    "ticketTotalPrice": 12.10
                },
                {
                    "ticketType": "LUGGAGE",
                    "ticketPriceWithoutVat": 3.0,
                    "vat": 0.630,
                    "ticketTotalPrice": 3.630
                },
                {
                    "ticketType": "LUGGAGE",
                    "ticketPriceWithoutVat": 3.0,
                    "vat": 0.630,
                    "ticketTotalPrice": 3.630
                }
            ]
        },
        {
            "totalPassengerOrderPrice": 9.680,
            "ticketResponseDtoList": [
                {
                    "ticketType": "CHILD",
                    "ticketPriceWithoutVat": 5.0,
                    "vat": 1.050,
                    "ticketTotalPrice": 6.050
                },
                {
                    "ticketType": "LUGGAGE",
                    "ticketPriceWithoutVat": 3.0,
                    "vat": 0.630,
                    "ticketTotalPrice": 3.630
                }
            ]
        }
    ]
}
```
