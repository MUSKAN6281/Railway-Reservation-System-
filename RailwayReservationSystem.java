import java.util.*;

public class Main {
    static Scanner sc = new Scanner(System.in);
    static ArrayList<Train> trains = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();
    static int pnr = 1001;

    static class Train {
        int no, sleeper, ac, general;
        String name, from, to;

        Train(int no, String name, String from, String to,
              int sleeper, int ac, int general) {
            this.no = no;
            this.name = name;
            this.from = from;
            this.to = to;
            this.sleeper = sleeper;
            this.ac = ac;
            this.general = general;
        }

        int seats(String cls) {
            if (cls.equalsIgnoreCase("Sleeper")) return sleeper;
            if (cls.equalsIgnoreCase("AC")) return ac;
            if (cls.equalsIgnoreCase("General")) return general;
            return 0;
        }

        void display() {
            System.out.println(no + " " + name + " | "
                    + from + " to " + to);
        }
    }

    static class Booking {
        int id, trainNo, age;
        String name, cls, status;

        Booking(int id, int trainNo, int age,
                String name, String cls, String status) {
            this.id = id;
            this.trainNo = trainNo;
            this.age = age;
            this.name = name;
            this.cls = cls;
            this.status = status;
        }

        void display() {
            System.out.println("PNR:" + id + " Name:" + name
                    + " Train:" + trainNo + " Class:" + cls
                    + " Status:" + status);
        }
    }

    static Train findTrain(int no) {
        for (Train t : trains)
            if (t.no == no) return t;
        return null;
    }

    static Booking findBooking(int id) {
        for (Booking b : bookings)
            if (b.id == id) return b;
        return null;
    }

    static int available(Train t, String cls) {
        int used = 0;
        for (Booking b : bookings)
            if (b.trainNo == t.no && b.cls.equalsIgnoreCase(cls)
                    && b.status.equals("Confirmed"))
                used++;
        return t.seats(cls) - used;
    }

    static void book() {
        System.out.print("Train number: ");
        Train t = findTrain(sc.nextInt());
        sc.nextLine();

        if (t == null) {
            System.out.println("Train not found");
            return;
        }

        System.out.print("Passenger name: ");
        String name = sc.nextLine();
        System.out.print("Age: ");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.print("Class (Sleeper/AC/General): ");
        String cls = sc.nextLine().trim();

        if (age <= 0 || name.trim().isEmpty()
                || t.seats(cls) <= 0) {
            System.out.println("Invalid details or class");
            return;
        }

        String status = available(t, cls) > 0
                ? "Confirmed" : "Waiting List";

        Booking b = new Booking(pnr++, t.no, age,
                name, cls, status);
        bookings.add(b);
        b.display();
    }

    static void cancel() {
        System.out.print("Enter PNR: ");
        Booking b = findBooking(sc.nextInt());
        sc.nextLine();

        if (b == null || b.status.equals("Cancelled")) {
            System.out.println("Booking not found");
            return;
        }

        b.status = "Cancelled";
        System.out.println("Ticket cancelled");

        if (available(findTrain(b.trainNo), b.cls) > 0) {
            for (Booking x : bookings) {
                if (x.trainNo == b.trainNo
                        && x.cls.equalsIgnoreCase(b.cls)
                        && x.status.equals("Waiting List")) {
                    x.status = "Confirmed";
                    System.out.println("Waiting-list ticket confirmed: "
                            + x.name);
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        trains.add(new Train(101, "Bangalore Express",
                "Bangalore", "Chennai", 2, 1, 3));
        trains.add(new Train(102, "Mysore Express",
                "Mysore", "Bangalore", 3, 2, 4));

        int choice = 0;

        do {
            System.out.println("\n1.Trains  2.Availability  3.Book");
            System.out.println("4.Cancel  5.Passenger  6.History  7.Exit");
            System.out.print("Choice: ");

            if (!sc.hasNextInt()) {
                System.out.println("Enter a number");
                sc.nextLine();
                continue;
            }

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    for (Train t : trains) t.display();
                    break;

                case 2:
                    System.out.print("Train number: ");
                    Train t = findTrain(sc.nextInt());
                    sc.nextLine();

                    if (t == null) {
                        System.out.println("Train not found");
                    } else {
                        for (String c : new String[]{
                                "Sleeper", "AC", "General"})
                            System.out.println(c + ": "
                                    + available(t, c));
                    }
                    break;

                case 3:
                    book();
                    break;

                case 4:
                    cancel();
                    break;

                case 5:
                    System.out.print("Enter PNR: ");
                    Booking b = findBooking(sc.nextInt());
                    sc.nextLine();
                    if (b == null) System.out.println("Not found");
                    else b.display();
                    break;

                case 6:
                    for (Booking x : bookings) x.display();
                    break;

                case 7:
                    System.out.println("Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice");
            }
        } while (choice != 7);

        sc.close();
    }
}
