package prototype;

import java.util.Scanner;

public class Main {

    @SuppressWarnings("resource")
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        AircraftService service = new AircraftService();
        AccountService accountService = new AccountService();

        while (true) {

            System.out.println("\n=== Airline Seat Management ===");

            System.out.println("\nAircraft Management");
            System.out.println("1 Add Aircraft");
            System.out.println("2 Remove Aircraft");
            System.out.println("3 View Aircraft");

            System.out.println("\nAccount Management");
            System.out.println("4 Create Account");
            System.out.println("5 Remove Account");
            System.out.println("6 View Accounts");

            System.out.println("\nBooking System");
            System.out.println("7 Book Aircraft Seats");

            System.out.println("\nSeat Utilities");
            System.out.println("8 View Seats");
            System.out.println("9 Search Seat");
            System.out.println("10 View Window Seats");
            System.out.println("11 Cancel Booking");

            System.out.println("\n12 Exit");

            int choice = scanner.nextInt();

            try {

                switch (choice) {

                case 1:

                    System.out.print("Enter Aircraft ID: ");
                    String id = scanner.next();

                    System.out.println("Enter route details:");
                    String route = scanner.next();

                    System.out.println("Enter flight Arrival time in 24 hrs:");
                    int atime = scanner.nextInt();

                    while (atime < 0 || atime > 2359) {
                        System.out.println("Enter valid time between 0000 and 2359:");
                        atime = scanner.nextInt();
                    }

                    System.out.println("Enter flight Departure time in 24 hrs:");
                    int dtime = scanner.nextInt();

                    while (dtime < 0 || dtime > 2359) {
                        System.out.println("Enter valid time between 0000 and 2359:");
                        dtime = scanner.nextInt();
                    }

                    System.out.print("Enter rows: ");
                    int rows = scanner.nextInt();

                    System.out.print("Enter columns: ");
                    int cols = scanner.nextInt();

                    service.addAircraft(id, route, rows, cols, atime, dtime);

                    break;

                case 2:

                    System.out.print("Enter Aircraft ID to remove: ");
                    service.removeAircraft(scanner.next());

                    break;

                case 3:

                    service.displayAllAircraft();

                    break;

                case 4:

                    System.out.print("Enter Account ID: ");
                    String accId = scanner.next();

                    accountService.addAccount(accId);

                    break;

                case 5:

                	System.out.print("Enter Account ID to remove: ");
                	String Accid = scanner.next();

                	service.removeAccountBookings(Accid);  
                	accountService.removeAccount(Accid);    

                    break;

                case 6:

                    accountService.displayAccounts();

                    break;

                case 7:

                    System.out.print("Enter Aircraft ID: ");
                    Aircraft aircraft = service.getAircraft(scanner.next());

                    if (aircraft == null) {
                        System.out.println("Aircraft not found.");
                        break;
                    }
                    
                    

                    System.out.print("Enter Account ID: ");
                    String accountId = scanner.next();

                    Account acc = accountService.getAccount(accountId);

                    if (acc == null) {
                        System.out.println("Account does not exist.");
                        break;
                    }

                    if (acc.hasBooking()) {
                        System.out.println("Account already has a booking.");
                        break;
                    }
                    
                    aircraft.displaySeats();

                    System.out.print("Enter number of passengers: ");
                    int count = scanner.nextInt();

                    acc.createPassengers(count, scanner);

                    aircraft.addAccount(acc);

                    acc.setBookingStatus(true);

                    System.out.println("Booking completed successfully.");

                    break;

                case 8:

                    System.out.print("Enter Aircraft ID: ");
                    Aircraft aircraftseats = service.getAircraft(scanner.next());

                    if (aircraftseats == null) {
                        System.out.println("Aircraft not found.");
                        break;
                    }

                    aircraftseats.displaySeats();

                    break;

                case 9:

                    System.out.print("Enter Aircraft ID: ");
                    Aircraft a2 = service.getAircraft(scanner.next());
                    

                    if (a2 == null) {
                        System.out.println("Aircraft not found.");
                        break;
                    }
                    System.out.print("Enter Seat (example A1): ");
                    String seat = scanner.next();

                    boolean booked = a2.searchSeat(seat);

                    if (booked) {
                        System.out.println("Seat is Booked.");
                    } else {
                        System.out.println("Seat is Available.");
                    }

                    break;

                case 10:

                    System.out.print("Enter Aircraft ID: ");
                    Aircraft a3 = service.getAircraft(scanner.next());

                    if (a3 == null) {
                        System.out.println("Aircraft not found.");
                        break;
                    }

                    a3.displayWindowSeatLayout();

                    break;

                case 11:

                    System.out.print("Enter Aircraft ID: ");
                    Aircraft aircraftCancel = service.getAircraft(scanner.next());

                    if (aircraftCancel == null) {
                        System.out.println("Aircraft not found.");
                        break;
                    }

                    System.out.print("Enter Account ID: ");
                    String cancelId = scanner.next();

                    aircraftCancel.cancelBooking(cancelId);

                    break;

                case 12:
                	
                	service.sortAircraft();

                    System.out.println("Exiting...");
                    return;

                default:

                    System.out.println("Invalid option.");

                }

            } catch (SeatNotFoundException e) {

                System.out.println(e.getMessage());
            }
        }
    }
}
