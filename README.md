# demoqa-api-automation

Automated API testing framework for [demoqa.com](https://demoqa.com), targeting the Account and BookStore REST APIs.

## 🛠️ Tech Stack

- **Java 17**
- **REST Assured** - API request execution and response validation
- **TestNG** - Test execution and assertions
- **Jackson** - JSON deserialization into POJOs
- **org.json** - JSON request body building
- **Lombok** - Boilerplate code reduction
- **Maven** - Build and dependency management

## 📁 Project Structure

```
src/test/
├── java/
│   ├── client/              # APIClient - base URI, content type, HTTP methods
│   ├── requestObject/       # POJOs for request bodies
│   ├── responseObject/      # POJOs for response bodies
│   ├── services/            # API service layer (AccountService, BookStoreService)
│   └── tests/               # Test classes
│       ├── account/         # CreateUser, GenerateToken, DeleteUser tests
│       └── bookstore/       # GetBooks, AddBooks, UpdateBook, DeleteBook tests
└── resources/
    └── testdata/            # JSON test data files
```

## ✅ Test Scenarios

### Account
- Create a new user
- Generate authentication token
- Validate user authorization
- Delete user

### BookStore
- Get all books
- Get book by ISBN
- Add books to user collection
- Replace a book in user collection
- Delete a book from user collection
- Delete all books from user collection

## ▶️ How to Run

1. Clone the repository `git clone https://github.com/Riverutz/demoqa-api-automation.git`
2. Open in IntelliJ IDEA
3. Run all tests via `testng.xml`

## 📋 Design Patterns

- **Service Layer Pattern** - API calls encapsulated in service classes
- **Client Pattern** - centralized HTTP client configuration
- **POJO Mapping** - strongly typed request and response objects via Jackson
- **Data Driven Testing** - dynamic test data via JSON files