# Gold Investment AI - Submission Package

##  Project Overview
A full-stack AI-powered gold investment platform that uses intelligent chatbot functionality to guide users through gold investment decisions and facilitate digital gold purchases.

##  Architecture
- **Frontend**: React.js with modern UI libraries
- **Backend**: Java Spring Boot with reactive programming
- **Database**: MongoDB for user and transaction data
- **AI System**: Enhanced rule-based intelligent response generation

##  Project Structure
gold-investment-ai/
├── backend/ # Spring Boot API server
│ ├── src/main/java/com/goldai/
│ │ ├── controller/ # REST API controllers
│ │ ├── service/ # Business logic services
│ │ ├── model/ # Data models
│ │ └── repository/ # Database repositories
│ ├── src/main/resources/
│ │ └── application.properties
│ └── pom.xml # Maven dependencies
├── frontend/ # React.js application
│ ├── src/
│ │ ├── components/ # React components
│ │ ├── services/ # API service layer
│ │ ├── App.js # Main application
│ │ └── App.css # Styling
│ ├── public/
│ └── package.json # NPM dependencies
└── README.md

## Quick Start

### Prerequisites
- Java 17+
- Node.js 16+
- MongoDB 4.4+
- Maven 3.8+

### Installation & Execution

#### 1. Database Setup
Start MongoDB
net start MongoDB

Verify connection
mongosh
use goldai
exit


#### 2. Backend Setup
cd backend
mvn clean install
mvn spring-boot:run

Backend runs on: http://localhost:8080

#### 3. Frontend Setup
cd frontend
npm install
npm start

Frontend runs on: http://localhost:3000


## Solution Approach

### Core Features
**Two-API Architecture**
- API 1: Intelligent chat with gold investment detection
- API 2: Secure gold purchase processing

 **AI-Powered Decision Flow**
- Enhanced pattern matching for natural conversations
- Context-aware response generation
- Explicit investment confirmation required

 **Complete Transaction Management**
- User registration and management
- Real-time gold pricing simulation
- Transaction history and portfolio tracking

### Technical Implementation
- **Reactive Programming**: Using Spring WebFlux for non-blocking operations
- **Smart Classification**: Advanced keyword and context analysis
- **Database Integration**: MongoDB with proper schema design
- **Frontend State Management**: React hooks for user interaction
- **Real-time Updates**: Live gold price simulation
- **Responsive Design**: Mobile-friendly interface

##  Key Challenges & Solutions

### Challenges Faced
1. **LLM Integration Complexity**: Initial Spring AI dependency issues
2. **Type Safety in Reactive Streams**: Generic type inference problems
3. **User Flow Design**: Ensuring explicit consent before purchase
4. **Database Schema**: Flexible user and transaction modeling

### Solutions Implemented
1. **Enhanced Rule-Based System**: Sophisticated pattern matching with AI-like responses
2. **Fixed Generic Types**: Proper type casting in reactive controllers
3. **Explicit Confirmation Flow**: Multi-step user consent process
4. **Flexible MongoDB Schema**: Embedded documents for purchase history

## Deployment Notes

### Local Development
- Backend runs on port 8080
- Frontend runs on port 3000
- MongoDB on default port 27017
- CORS configured for cross-origin requests

### Production Considerations
- Environment-specific configuration
- Database connection pooling
- API rate limiting
- Security headers and validation
- SSL/TLS termination

##  Database Schema
// Users Collection
{
_id: ObjectId,
userName: String,
email: String,
phone: String,
totalGoldOwned: Number, // grams
totalInvestment: Number, // INR
purchases: [{ // Embedded documents
transactionId: String,
amount: Number,
grams: Number,
pricePerGram: Number,
purchaseDate: Date,
status: String
}],
createdAt: Date,
updatedAt: Date
}
