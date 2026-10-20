# Hospital Management System

A Java-based Hospital Management System developed using Java Swing, JDBC, and MySQL. The application provides a graphical interface for managing hospital operations.

## Features

- **Dashboard:** Navigation between hospital management modules.
- **Doctor Management:** Add and view doctor records.
- **Patient Management:** Register and view patients.
- **Appointment Management:** Manage patient appointments.
- **Room Management:** View and manage hospital rooms.
- **Room Booking:** Record patient room bookings.
- **Billing Management:** Generate and view patient bills.

## Technologies Used

- Java
- Java Swing (GUI)
- MySQL
- JDBC
- MySQL Connector/J
- Object-Oriented Programming

## Project Structure

```text
HospitalManagementSystem/
├── src/
│   ├── dao/
│   ├── database/
│   ├── exception/
│   ├── gui/
│   ├── interfaces/
│   ├── model/
│   ├── service/
│   └── Main.java
├── lib/
│   └── MySQL Connector/J
├── database/
└── README.md
```

## Database Setup

1. Install and start MySQL Server.
2. Create a database named `hospital_db`.
3. Execute the SQL script containing the required table definitions.
4. Configure the database connection in `DBConnection.java`.
5. Add MySQL Connector/J to the Java classpath.

## Running the Project

Compile the Java source files and run the dashboard using the configured Java classpath.

```bash
javac -cp "lib/mysql-connector-j-26.7.0.jar" -d out $(find src -type f -name "*.java")
java -cp "out:lib/mysql-connector-j-26.7.0.jar" gui.DashboardFrame
```

## Academic Project

This project demonstrates Java GUI development, object-oriented programming, database connectivity using JDBC, and data access through DAO classes.