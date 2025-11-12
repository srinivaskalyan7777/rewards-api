🏆 Rewards API
A Spring Boot 3.x REST API built with Java 17, designed to dynamically calculate customer reward points based on transaction history within a configurable date range (maximum 3 months).

🧰 Technologies Used
• Java 17
• Spring Boot 3.x
• Maven
• RESTful Web Services
• SLF4J Logging
• JUnit 5 (for testing)

📘 Project Overview
This API simulates a retailer’s customer rewards program, where customers earn points based on their purchase amounts:
- 2 points for every dollar spent over $100
- 1 point for every dollar spent between $50 and $100

The service lets you calculate reward points for any date range (≤ 3 months) or defaults to the last 3 months if no dates are provided.

⚙️ API Endpoint
GET http://localhost:8080/api/rewards?customerId=CUST001&startDate=2025-08-01&endDate=2025-10-31

📋 Parameters
Parameter	Type	Required	Description
customerId	String	Yes	Unique customer identifier
startDate	LocalDate	No	Start of the date range (yyyy-MM-dd). Defaults to 3 months ago.
endDate	LocalDate	No	End of the date range (yyyy-MM-dd). Defaults to today.

Validation Rules:
- startDate cannot be after endDate.
- Date range cannot exceed 3 months.

📤 Sample Response
{
  "customerId": "CUST001",
  "name": "John Doe",
  "transactions": [
    {"transactionId": "T1", "customerId": "CUST001", "date": "2025-11-07", "amount": 120},
    {"transactionId": "T2", "customerId": "CUST001", "date": "2025-10-13", "amount": 75},
    {"transactionId": "T3", "customerId": "CUST001", "date": "2025-09-03", "amount": 200}
  ],
  "monthlyRewards": {"2025-09": 250, "2025-10": 25, "2025-11": 90},
  "totalRewards": 365
}

❌ Example Error Responses
Invalid Date Range:
{
  "status": 400,
  "error": "Reward Processing Error",
  "message": "Date range cannot exceed 3 months."
}

🧪 Testing with Postman
1. Choose GET as the request method.
2. Enter URL: http://localhost:8080/api/rewards?customerId=CUST001&startDate=2025-08-01&endDate=2025-10-31
3. Leave Body tab empty.
4. Click Send.

💡 If startDate and endDate are omitted, defaults to last 3 months.

🧾 Error Handling
Global exception handling implemented via @ControllerAdvice (RewardExceptionHandler).
All errors return a structured JSON with timestamp, status, error, and message.

🚀 Running the Application
Using Maven:
mvn spring-boot:run

Or build JAR:
mvn clean package
java -jar target/rewards-api-0.0.1-SNAPSHOT.jar


