# Rewards API

A **Spring Boot 3.x REST API** built with **Java 17**, designed to dynamically calculate customer reward points based on their transaction history within a configurable date range.

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

This API simulates a retailer’s **customer rewards program**, where customers earn points based on their purchase amounts:

- **2 points** for every dollar spent **over $100**  
- **1 point** for every dollar spent **between $50 and $100**

The service allows users to calculate reward points for a given **date range** or defaults to the **last 3 months** if no dates are provided.

It returns:
- Total reward points  
- Monthly reward summary  
- Transaction-level details per customer  

---

## API Endpoint

### **GET**

http://localhost:8080/api/rewards?customerId=CUST001&startDate=2025-08-01&endDate=2025-10-31


### **Parameters**

| Parameter | Type | Required | Description |
|------------|------|-----------|-------------|
<<<<<<< HEAD
| `customerId` | String | Yes | Unique customer identifier |
| `startDate` | LocalDate | No | Start of the date range (format: yyyy-MM-dd). Defaults to 3 months ago if not provided. |
| `endDate` | LocalDate | No | End of the date range (format: yyyy-MM-dd). Defaults to today if not provided. |
=======
| `customerId` | String | Yes | Unique identifier of the customer |
| `months` | Integer | No | Number of months to consider (default = 3, max = 3) |
>>>>>>> 42749df7b7f56dee9dabb47e532a6fe5017bf374

---

## Sample Request (via Query Parameters)

**Example 1 — Custom Date Range**


---

## Sample Response
```json
{
  "customerId": "CUST001",
  "name": "John Doe",
  "totalRewards": 365,
  "monthlyRewards": {
    "2025-08": 90,
    "2025-09": 25,
    "2025-10": 250
  },
  "transactions": [
    {
      "transactionId": "T1",
      "customerId": "CUST001",
      "date": "2025-10-25",
      "amount": 120.00
    },
    {
      "transactionId": "T2",
      "customerId": "CUST001",
      "date": "2025-09-15",
      "amount": 75.00
    },
    {
      "transactionId": "T3",
      "customerId": "CUST001",
      "date": "2025-08-10",
      "amount": 200.00
    }
  ]
}

<<<<<<< HEAD
Testing the API (Postman)

Select GET as the request method.
Enter the URL:
http://localhost:8080/api/rewards?customerId=CUST001&startDate=2025-08-01&endDate=2025-10-31

Click Send — you’ll receive a JSON response with calculated reward points.
If you omit startDate and endDate, it automatically calculates rewards for the last 3 months.
=======
##Run the Application

mvn spring-boot:run
>>>>>>> 42749df7b7f56dee9dabb47e532a6fe5017bf374
