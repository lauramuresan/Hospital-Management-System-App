Hospital Management System

Team Members: Mureșan Laura, Marica Ioana;
Project Overview
This project represents the first phase(in-memory) of the Hospital Management System, built using Spring Boot and Java 21.
Goals: MVC arhitecture, OOP principles, SOLID design principles
Technologies used: Java 21, Spring Boot 3.5.7, Maven, Spring Web
Structure:
com.example.hospital/
 ├── model/         → contains entity classes 
 ├── repository/    → manages in-memory data (Map)
 ├── service/       → contains business logic
 └── controller/    → handles user requests (HTTP endpoints)

 Design Decisions
 Each entity has its own service and repository, following the Single Responsibility Principle. 
 All data is stored in memory using Java collections -Map, HashMap(for complexity teta(1)).

 Principles Applied: SOLID, KISS, DRY, Clean Code Conventions
 
