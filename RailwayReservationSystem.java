
import java.util.*;

public class RailwayReservationSystem {

    enum Coach { SLEEPER, AC, GENERAL }
    enum Status { CONFIRMED, WAITING_LIST, CANCELLED }

    static class Train {
        int number;
        String name, source, destination, time;
        Map<Coach, Integer> capacity = new HashMap<>();
        Map<Coach, Integer> available = new HashMap<>();

        Train(int number, String name, String source,
              String destination, String time,
              int sleeper, int ac, int general) {
            this.number = number;
            this.name = name;
            this.source = source;
            this.destination = destination;
            this.time = time;

            capacity.put(Coach.SLEEPER, sleeper);
            capacity.put(Coach.AC, ac);
            capacity.put(Coach.GENERAL, general);

            for (Coach c : Coach.values())
                available.put(c, capacity.get(c));
        }
    }

    static class Booking {
        String ticketNo, passenger, phone;
        int age;
        Train train;
        Coach coach;
        Status status;

        Booking(String ticketNo, String passenger, int age,
                String phone, Train train, Coach coach,
                Status status) {
            this.ticketNo = ticketNo;
            this.passenger = passenger;
            this.age = age;
            this.phone = phone;
            this.train = train;
            this.coach = coach;
            this.status = status;
        }

        void display() {
            System.out.println("\nTicket: " + ticketNo);
            System.out.println("Passenger: " + passenger);
            System.out.println("Age: " + age);
            System.out.println("Phone: " + phone);
            System.out.println("Train: " + train.number + " - " + train.name);
            System.out.println("Route: " + train.source + " to " + train.destination);
            System.out.println("Departure: " + train.time);
            System.out.println("Class: " + coach);
            System.out.println("Status: " + status);
        }
    }

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Train> trains = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();
    static int nextTicket = 1001;

    static void addTrains() {
        trains.add(new Train(101, "Chennai Express",
                "Bangalore", "Chennai", "06:00", 2, 1, 2));
        trains.add(new Train(102, "Mysore Express",
                "Bangalore", "Mysore", "08:30", 3, 1, 2));
        trains.add(new Train(103, "Hyderabad Express",
                "Bangalore", "Hyderabad", "20:00", 2, 2, 1));
    }

    static Train findTrain(int number) {
        for (Train t : trains)
            if (t.number == number)
                return t;
        return null;
    }

    static Coach chooseCoach() {
        System.out.println("1. Sleeper  2. AC  3. General");
        int choice = sc.nextInt();

        switch (choice) {
            case 1: return Coach.SLEEPER;
            case 2: return Coach.AC;
            case 3: return Coach.GENERAL;
            default: return null;
        }
    }

    static void displayTrains() {
        System.out.println("\n--- Train Schedule ---");
        for (Train t : trains) {
            System.out.println(t.number + " | " + t.name + " | "
                    + t.source + " to " + t.destination
                    + " | Departure: " + t.time);
        }
    }

    static void searchTrains() {
        sc.nextLine();
        System.out.print("Enter source: ");
        String source = sc.nextLine().trim();

        System.out.print("Enter destination: ");
        String destination = sc.nextLine().trim();

        boolean found = false;
        for (Train t : trains) {
            if (t.source.equalsIgnoreCase(source)
                    && t.destination.equalsIgnoreCase(destination)) {
                System.out.println(t.number + " - " + t.name
                        + " | " + t.time);
                found = true;
            }
        }
        if (!found)
            System.out.println("No matching trains found.");
    }

    static void checkSeats() {
        displayTrains();
        System.out.print("Enter train number: ");
        Train t = findTrain(sc.nextInt());

        if (t == null) {
            System.out.println("Train not found.");
            return;
        }

        for (Coach c : Coach.values()) {
            System.out.println(c + ": "
                    + t.available.get(c) + " seats available");
        }
    }

    static void bookTicket() {
        displayTrains();
        System.out.print("Enter train number: ");
        Train t = findTrain(sc.nextInt());

        if (t == null) {
            System.out.println("Train not found.");
            return;
        }

        Coach c = chooseCoach();
        if (c == null) {
            System.out.println("Invalid class.");
            return;
        }

        sc.nextLine();
        System.out.print("Passenger name: ");
        String name = sc.nextLine();

        System.out.print("Age: ");
        int age = sc.nextInt();
        if (age <= 0 || age > 120) {
            System.out.println("Invalid age.");
            return;
        }

        sc.nextLine();
        System.out.print("Phone number: ");
        String phone = sc.nextLine();

        Status status;
        if (t.available.get(c) > 0) {
            t.available.put(c, t.available.get(c) - 1);
            status = Status.CONFIRMED;
        } else {
            status = Status.WAITING_LIST;
        }

        String ticket = "T" + nextTicket++;
        Booking b = new Booking(ticket, name, age, phone, t, c, status);
        bookings.add(b);

        System.out.println("\nBooking successful!");
        b.display();
    }

    static void cancelTicket() {
        System.out.print("Enter ticket number: ");
        String id = sc.next();

        for (Booking b : bookings) {
            if (b.ticketNo.equalsIgnoreCase(id)
                    && b.status != Status.CANCELLED) {

                if (b.status == Status.CONFIRMED) {
                    b.train.available.put(b.coach,
                            b.train.available.get(b.coach) + 1);

                    // Promote the first waiting passenger.
                    for (Booking w : bookings) {
                        if (w.train == b.train
                                && w.coach == b.coach
                                && w.status == Status.WAITING_LIST) {
                            w.status = Status.CONFIRMED;
                            w.train.available.put(w.coach,
                                    w.train.available.get(w.coach) - 1);
                            System.out.println("Waiting passenger "
                                    + w.passenger + " is now confirmed.");
                            break;
                        }
                    }
                }

                b.status = Status.CANCELLED;
                System.out.println("Ticket cancelled successfully.");
                return;
            }
        }

        System.out.println("Active ticket not found.");
    }

    static void passengerInfo() {
        System.out.print("Enter ticket number: ");
        String id = sc.next();

        for (Booking b : bookings) {
            if (b.ticketNo.equalsIgnoreCase(id)) {
                b.display();
                return;
            }
        }
        System.out.println("Ticket not found.");
    }

    static void bookingHistory() {
        System.out.println("\n--- Booking History ---");
        if (bookings.isEmpty()) {
            System.out.println("No bookings available.");
            return;
        }
        for (Booking b : bookings)
            b.display();
    }

    public static void main(String[] args) {
        addTrains();
        int choice;

        do {
            System.out.println("\n=== RAILWAY RESERVATION SYSTEM ===");
            System.out.println("1. Display Train Schedule");
            System.out.println("2. Search Trains");
            System.out.println("3. Check Seat Availability");
            System.out.println("4. Book Ticket");
            System.out.println("5. Cancel Ticket");
            System.out.println("6. Passenger Information");
            System.out.println("7. Booking History");
            System.out.println("8. Exit");
            System.out.print("Enter choice: ");

            if (!sc.hasNextInt()) {
                System.out.println("Enter a valid number.");
                sc.next();
                continue;
            }

            choice = sc.nextInt();

            switch (choice) {
                case 1: displayTrains(); break;
                case 2: searchTrains(); break;
                case 3: checkSeats(); break;
                case 4: bookTicket(); break;
                case 5: cancelTicket(); break;
                case 6: passengerInfo(); break;
                case 7: bookingHistory(); break;
                case 8: System.out.println("Thank you!"); break;
                default: System.out.println("Invalid choice.");
            }
        } while (choice != 8);

        sc.close();
    }
}
