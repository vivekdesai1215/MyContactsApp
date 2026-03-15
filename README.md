# MyContacts App

## 📌 Overview
MyContacts App is a **Java-based, console-driven application** designed to demonstrate **object-oriented programming (OOP)**, **design patterns**, and **core Java concepts** through a contact management system.  
The project is structured use-case-wise (UC1 – UC12), each showcasing a specific OOP principle or design pattern.

---

## 🚀 Features
- **User Management**
  - Registration & Authentication (UC1, UC2)
  - Profile Management (UC3)
- **Contacts**
  - Create Person & Organization contacts (UC4)
  - View details with Decorator enhancements (UC5)
  - Edit contacts with Undo/Redo (UC6)
  - Delete contacts (Soft & Hard Delete) (UC7)
- **Groups**
  - Bulk operations on groups (UC8)
- **Search & Filter**
  - Advanced search using Specification Pattern (UC9)
  - Multi-level filtering & sorting (UC10)
- **Tagging**
  - Create & Manage Tags (UC11)
  - Apply Tags to Contacts (UC12)
- **Admin Features**
  - User oversight and global search

---

## 🧩 Use Case Scenarios

### 1. User Management
- **UC-01:** User Registration → Factory & Builder Pattern  
- **UC-02:** User Authentication → Strategy & Singleton Pattern  
- **UC-03:** User Profile Management → Command Pattern  

### 2. Contact Management
- **UC-04:** Create Contact → Builder & Factory Pattern  
- **UC-05:** View Contact Details → Decorator Pattern  
- **UC-06:** Edit Contact → Command & Memento Pattern  
- **UC-07:** Delete Contact → Observer Pattern  
- **UC-08:** Bulk Operations → Composite Pattern  

### 3. Search & Filter
- **UC-09:** Search Contacts → Specification & Chain of Responsibility  
- **UC-10:** Advanced Filtering → Composite & Strategy Pattern  

### 4. Tagging & Organization
- **UC-11:** Create & Manage Tags → Flyweight Pattern  
- **UC-12:** Apply Tags to Contacts → Observer Pattern  

---

## 🛠️ Core OOP Concepts
- **Encapsulation & Validation** → User & Contact classes  
- **Inheritance & Polymorphism** → Person vs Organization contacts  
- **Composition** → Contact has PhoneNumber, Email, Tags  
- **Association (Many-to-Many)** → Contact ↔ Tag relationship  
- **Lifecycle Management** → Soft/Hard delete, undo/redo  

---

## 📚 Java Concepts Used
- Collections (`List`, `Set`, `Map`)  
- Streams & Lambda expressions  
- Comparator & Functional Interfaces  
- Exception Handling & Validation  
- UUIDs & LocalDateTime for unique IDs and timestamps  
- Regular Expressions for email validation  

---

## 🎯 Learning Goals
This project demonstrates:
- Applying **OOP principles** in real-world scenarios.  
- Using **design patterns** to solve common problems.  
- Writing **modular, maintainable, and extensible code**.  
- Building a **console-driven workflow** with clear separation of concerns.  

---

## ▶️ Running the App
1. Clone the repository:
   ```bash
   git clone https://github.com/your-username/MyContactsApp.git
