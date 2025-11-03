# Rewards API

A Spring Boot REST API that calculates customer reward points dynamically based on transaction history and configurable timeframes.


##Technologies Used
- Java 8
- Spring Boot
- Maven
- RESTful Web Services
- SLF4J Logging


##Description
A retailer offers a reward program where:
- 2 points for every dollar spent over $100.
- 1 point for every dollar spent between $50 and $100.

For a given time frame (default 3 months), the API calculates the total rewards and details of all transactions.

POST
##Full endpoint
http://localhost:8080/api/rewards/calculate?customerId=CUST001&months=3

##Sample request body
[
  {"transactionId": "T1", "date": "2025-08-15", "amount": 120.5},
  {"transactionId": "T2", "date": "2025-09-12", "amount": 75.0},
  {"transactionId": "T5", "date": "2025-10-20", "amount": 95.0}
  
]

##Expected response body
{
    "customerId": "CUST001",
    "customerName": "Customer CUST001",
    "timeFrameInMonths": 3,
    "transactionCount": 3,
    "totalAmount": 240.5,
    "totalRewardPoints": 115,
    "transactions": [
        {
            "transactionId": "T1",
            "date": "2025-08-15",
            "amount": 120.5,
            "rewardPoints": 90
        },
        {
            "transactionId": "T2",
            "date": "2025-09-12",
            "amount": 75.0,
            "rewardPoints": 25
        },
        {
            "transactionId": "T3",
            "date": "2025-10-20",
            "amount": 45.0,
            "rewardPoints": 0
        }
    ]
}

##Run the Application
mvn spring-boot:run
