package prototype;

import java.util.Scanner;

public class Account {

    private String accountId;
    private boolean hasBooking;

    private Passenger[] passengers;
    private int passengerCount;

    public Account(String accountId) {
        this.accountId = accountId;
        this.hasBooking = false;
    }

    public String getAccountId() {
        return accountId;
    }

    public boolean hasBooking() {
        return hasBooking;
    }

    public void setBookingStatus(boolean status) {
        this.hasBooking = status;
    }

    public Passenger[] getPassengers() {
        return passengers;
    }

    public int getPassengerCount() {
        return passengerCount;
    }

    public void createPassengers(int count, Scanner scanner) {

        passengerCount = count;
        passengers = new Passenger[count];

        for (int i = 0; i < count; i++) {

            System.out.println("Passenger " + (i + 1));

            System.out.print("Enter Name: ");
            String name = scanner.next();

            System.out.print("Enter Age: ");
            int age = scanner.nextInt();

            System.out.print("Enter Seat (example A1): ");
            String seat = scanner.next();

            // check duplicate seat inside same booking
            for (int j = 0; j < i; j++) {

                if (passengers[j].getSeat().equalsIgnoreCase(seat)) {

                    System.out.println("Seat already chosen in this booking.");
                    i--;
                    continue;
                }
            }

            passengers[i] = new Passenger(name, age, seat);
        }
        
    }
}
