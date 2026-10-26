
🚆 Railway Reservation System — Java

An interactive, menu-driven railway reservation application developed using Java and Object-Oriented Programming. The system enables passengers to search trains, check seat availability, book tickets, cancel reservations, and track booking status

🌟 Key Features

1. 🚉 Train Search & Schedule Management

   • Search trains by source and destination.
   • Display train numbers, routes, and departure times.
   • View available trains and their schedules.

2. 🎟️ Ticket Booking System

   • Book tickets in Sleeper, AC, and General classes.
   • Generate a unique ticket number for every reservation.
   • Automatically update seat availability after booking.
   • Assign Confirmed or Waiting List status based on availability.

3. ⏳ Booking Status Management

   • Confirmed — Seat successfully allocated.
   • Waiting List — No seat currently available.
   • Cancelled — Reservation cancelled by the passenger.

4. ❌ Ticket Cancellation

   • Cancel reservations using the ticket number.
   • Automatically update seat availability.
   • Promote a waiting-list passenger when a confirmed seat becomes available.

5. 👤 Passenger Information

   • Store passenger name, age, and phone number.
   • Display passenger and journey details using the ticket number.
   • View the class and current reservation status.

6. 📋 Booking History

   • Display all reservations.
   • Track confirmed, waiting-list, and cancelled tickets.
   • Maintain booking records during program execution.


🛠️ Technologies Used

   • Programming Language: Java
   • Concepts: Object-Oriented Programming (OOP)
   • Data Structures: ArrayList and HashMap
   • Control Structures: Loops and Switch Statements
   • Tools: JDK and GitHub



📂 Project Structure

Railway-Reservation-System/
│
├── RailwayReservationSystem.java
└── README.md


▶️ How to Execute

1. Install Java Development Kit (JDK).
2. Download or clone the project repository.
3. Open a terminal in the project directory.
4. Compile the program:

   javac RailwayReservationSystem.java

5. Execute the application:

   java RailwayReservationSystem



🎯 Project Objective

To develop a simple and efficient railway reservation application that demonstrates train scheduling, passenger management, ticket booking, seat allocation, cancellation, and waiting-list processing using Java.


🔮 Future Enhancements

   • Integrate a MySQL database for permanent data storage.
   • Add a graphical user interface.
   • Implement login and user authentication.
   • Introduce journey dates, ticket fares, and payment processing.
   • Generate printable tickets and detailed booking reports.


⚠️ Project Limitations

This project is an educational demonstration. Data is stored in memory and is cleared when the application exits. It is not connected to an official railway reservation service.
Developed as a Java mini-project to demonstrate programming fundamentals and reservation management.
