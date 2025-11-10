# Rewards API

A **Spring Boot 3.x REST API** built with **Java 17**, designed to dynamically calculate customer reward points based on their transaction history within a configurable time frame.

---

## Technologies Used
- **Java 17**
- **Spring Boot 3.x**
- **Maven**
- **RESTful Web Services**
- **SLF4J Logging**
- **JUnit 5 (for testing)**

---

## Project Overview

This API simulates a retailer’s **customer rewards program**, where customers earn points based on purchase amounts:

-  **2 points** for every dollar spent **over $100**  
-  **1 point** for every dollar spent **between $50 and $100**

The service allows you to calculate reward points over a **user-defined timeframe** (default = 3 months) and returns:
- Total reward points  
- Transaction-level details  
- Summary statistics per customer  

---

## API Endpoint

### **POST**

http://localhost:8080/api/rewards?customerId=CUST001&months=3


### **Parameters**
| Parameter | Type | Required | Description |
|------------|------|-----------|-------------|
| `customerId` | String | ✅ Yes | Unique identifier of the customer |
| `months` | Integer | ❌ No | Number of months to consider (default = 3, max = 3) |

---

## Sample Request Body
```json
[
  { "transactionId": "T1", "date": "2025-08-15", "amount": 120.5 },
  { "transactionId": "T2", "date": "2025-09-12", "amount": 75.0 },
  { "transactionId": "T5", "date": "2025-10-20", "amount": 95.0 }
]

## Sample Response body
```json
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


## Testing API using Postman:

-Choose GET as the request method.
-Enter the URL:
	http://localhost:8080/api/rewards/calculate?customerId=CUST001&months=3
-Go to the Body tab → select raw → choose JSON.
-Paste the sample request body and click Send.