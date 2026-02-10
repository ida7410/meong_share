# 🐾 MEONG SHARE

> A trusted secondhand marketplace platform for pet owners to buy and sell unused or non-returnable items

[![Live Demo](https://img.shields.io/badge/Live%20Demo-Visit%20Site-blue?style=for-the-badge)](https://meong-share-257150041772.northamerica-northeast2.run.app/home)
[![GitHub](https://img.shields.io/badge/GitHub-Repository-black?style=for-the-badge&logo=github)](https://github.com/ida7410/meong_share)
[![Google Cloud](https://img.shields.io/badge/Google%20Cloud-Run-4285F4?style=for-the-badge&logo=googlecloud)](https://cloud.google.com/run)

## 📋 Table of Contents
- [Overview](#overview)
- [Key Features](#key-features)  
- [Technology Stack](#technology-stack)
- [Getting Started](#getting-started)
- [User Flow](#user-flow)
- [Development Timeline](#development-timeline)
- [Contributing](#contributing)

## 🎯 Overview

**Meong Share** is a comprehensive secondhand marketplace specifically designed for pet owners. The platform enables users to trade unused or non-returnable pet-related items with high reliability through secure user authentication, real-time communication, and a trusted transaction system.

**🌐 Live Demo:** [https://meong-share-257150041772.northamerica-northeast2.run.app/home](https://meong-share-257150041772.northamerica-northeast2.run.app/home)

## ✨ Key Features

### 🔐 **User Management System**
- **Secure Registration**: Email verification with SHA256 password encryption
- **Profile Management**: Update login ID, password, and profile images
- **Account Authentication**: Secure login system with encrypted credentials

### 📦 **Product Marketplace**
- **Product Listing**: Upload items with detailed descriptions and images
- **Product Discovery**: Browse and search available items
- **Like System**: Save interesting products for later
- **Image Management**: Multiple product photos with cloud storage

### 💬 **Real-time Chat System**
- **WebSocket Communication**: Live chat between buyers and sellers
- **Email Notifications**: Automatic email alerts when new chats are initiated
- **Message History**: Persistent chat conversations per product
- **Instant Messaging**: Real-time communication for negotiations

### 🤝 **Secure Transaction Management**
- **Trade Completion Flow**: Seller initiates completion, buyer accepts
- **Mutual Agreement**: Both parties must agree to finalize transactions
- **Chat Termination**: Conversations end when trades are completed
- **Recommendation System**: Buyers can recommend sellers post-transaction

### 🛡️ **Trust & Safety**
- **Verified Users**: Email-verified accounts only
- **Secure Passwords**: SHA256 encryption for enhanced security
- **Transaction Tracking**: Clear completion workflow prevents disputes

## 🛠️ Technology Stack

### **Frontend**
- **HTML5** - Semantic markup and structure
- **JSP** - Server-side rendering and dynamic content
- **jQuery** - DOM manipulation and AJAX communications
- **Bootstrap** - Responsive UI framework

### **Backend**
- **Java** - Core application logic
- **Spring Boot** - Application framework and dependency injection
- **WebSocket** - Real-time communication protocol

### **Database & Storage**
- **Google Cloud SQL** - Primary database system
- **Google Cloud Storage** - File and image storage

### **Deployment & Infrastructure**
- **Google Cloud Run** - Serverless deployment platform
- **SMTP Integration** - Email notification system

### **Development Tools**
- **GitHub** - Version control and issue management
- **Maven** - Dependency management and build tool

## 🚀 Getting Started

### 🌐 Live Demo
Experience Meong Share directly through our live deployment:

**🔗 [Try Meong Share Now](https://meong-share-257150041772.northamerica-northeast2.run.app/home)**

The application is deployed on Google Cloud Run for optimal performance and cost-effectiveness.

### 🛠️ For Developers
If you're interested in contributing or exploring the codebase:

1. **Explore the Repository**
   ```bash
   git clone https://github.com/ida7410/meong_share.git
   ```

2. **Technology Stack**
   - **Backend**: Java, Spring Boot, WebSocket
   - **Frontend**: HTML5, JSP, jQuery, Bootstrap
   - **Database**: Google Cloud SQL
   - **Storage**: Google Cloud Storage
   - **Deployment**: Google Cloud Run

## 👥 User Flow

### **For Sellers**
1. **Sign Up** → Email verification → Profile setup
2. **Upload Products** → Add photos, descriptions, pricing
3. **Receive Notifications** → Email alerts for new chat inquiries
4. **Chat with Buyers** → Real-time negotiation via WebSocket
5. **Complete Trade** → Send completion request to buyer
6. **Receive Recommendation** → Optional buyer feedback

### **For Buyers**
1. **Browse Products** → Search and discover items
2. **Like Items** → Save interesting products
3. **Start Conversations** → Send messages to sellers
4. **Live Chat** → Real-time communication with sellers
5. **Accept Trade Completion** → Confirm successful transaction
6. **Recommend Seller** → Optional seller feedback

## 🔧 Core Features Explained

### User Registration & Authentication
- **Email Verification**: Secure account activation via email
- **Password Security**: SHA256 encryption with secure storage
- **Profile Management**: Update personal information and profile pictures

### Product Management
- **Listing Creation**: Comprehensive product upload with images
- **Image Storage**: Google Cloud Storage integration for reliable file handling
- **Product Interaction**: Like and message functionality per listing

### Communication System
- **WebSocket Integration**: Real-time messaging without page refreshes
- **Email Integration**: SMTP notifications for new chat initiations
- **Chat Persistence**: Message history maintained throughout conversations

### Transaction Workflow
- **Seller-Initiated Completion**: Trade completion requests from product owners
- **Buyer Confirmation**: Acceptance-based transaction finalization
- **Automatic Chat Management**: Conversations terminate upon completed trades
- **Feedback System**: Post-transaction recommendation capabilities

## 📅 Development Timeline

### **Version 1.0**
**Development Period:** February 1, 2023 - March 14, 2024
- Initial marketplace platform development
- Core features: user authentication, product listings, chat system
- Original deployment on AWS

### **Version 2.0** 🚀
**Development Started:** August 2025 - Present
- Platform modernization and feature enhancements
- Migration to Google Cloud Run for improved scalability and cost-efficiency
- WebSocket implementation for real-time communication
- Enhanced user experience and system optimizations

**Current Status:** Version 2.0 in active development

## 🔗 System Integrations

- **Google Cloud Services** - SQL database and file storage
- **SMTP Email Services** - Automated notification system
- **WebSocket Protocol** - Real-time communication infrastructure

## 📈 Future Enhancements

- [ ] Mobile application development
- [ ] Advanced search and filtering
- [ ] Payment gateway integration
- [ ] Enhanced recommendation algorithm
- [ ] Advanced analytics dashboard

## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📧 Contact

For questions, suggestions, or support, please reach out through the contact form on the website or create an issue in this repository.

---
