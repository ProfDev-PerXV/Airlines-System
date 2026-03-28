package prototype;

public class Aircraft {

    private String aircraftId;
    private String route;
    private int rows;
    private int cols;
    private int atime;
    private int dtime;

    private boolean[][] seats;

    private AccountNode accountHead;

    private class AccountNode {
        Account account;
        AccountNode next;

        AccountNode(Account account) {
            this.account = account;
            this.next = null;
        }
    }

    public Aircraft(String aircraftId, String route, int rows, int cols, int atime, int dtime) {
        this.aircraftId = aircraftId;
        this.route = route;
        this.rows = rows;
        this.cols = cols;
        this.atime = atime;
        this.dtime = dtime;

        seats = new boolean[rows][cols];
    }
    
    public String getRoute() {
        return route;
    }

    public int getArrivalTime() {
        return atime;
    }

    public int getDepartureTime() {
        return dtime;
    }

    public String getAircraftId() {
        return aircraftId;
    }

    public void addAccount(Account acc) throws SeatNotFoundException {

        Passenger[] passengers = acc.getPassengers();

        for (Passenger p : passengers) {

            String seat = p.getSeat().toUpperCase();

            int row = seat.charAt(0) - 'A';
            int col = Integer.parseInt(seat.substring(1)) - 1;

            if (row < 0 || row >= rows || col < 0 || col >= cols) {
                throw new SeatNotFoundException("Invalid seat: " + seat);
            }

            if (seats[row][col]) {
                throw new SeatNotFoundException("Seat already booked: " + seat);
            }

            seats[row][col] = true;
        }

        AccountNode newNode = new AccountNode(acc);

        if (accountHead == null) {
            accountHead = newNode;
            return;
        }

        AccountNode temp = accountHead;

        while (temp.next != null) {
            temp = temp.next;
        }

        temp.next = newNode;
    }
    
    public void cancelBooking(String accountId) {

        if (accountHead == null) {
            System.out.println("No bookings exist.");
            return;
        }

        AccountNode current = accountHead;
        AccountNode previous = null;

        while (current != null && !current.account.getAccountId().equals(accountId)) {
            previous = current;
            current = current.next;
        }

        if (current == null) {
            System.out.println("Booking not found for this account.");
            return;
        }

        // Free all seats of that account
        Passenger[] passengers = current.account.getPassengers();

        for (Passenger p : passengers) {

            String seat = p.getSeat().toUpperCase();

            int row = seat.charAt(0) - 'A';
            int col = Integer.parseInt(seat.substring(1)) - 1;

            seats[row][col] = false;
        }

        // Remove from linked list
        if (previous == null) {
            accountHead = current.next;
        } else {
            previous.next = current.next;
        }

        current.account.setBookingStatus(false);

        System.out.println("Booking cancelled successfully.");
    }


    public void displaySeats() {

        System.out.println("\nSeat Layout (O = Available, X = Booked)");

        for (int i = 0; i < rows; i++) {

            char rowLabel = (char) ('A' + i);

            for (int j = 0; j < cols; j++) {

                if (seats[i][j]) {
                    System.out.print(rowLabel + "" + (j + 1) + "[X] ");
                } else {
                    System.out.print(rowLabel + "" + (j + 1) + "[O] ");
                }
            }

            System.out.println();
        }
    }

    public boolean searchSeat(String seat) throws SeatNotFoundException {

        seat = seat.toUpperCase();

        int row = seat.charAt(0) - 'A';
        int col = Integer.parseInt(seat.substring(1)) - 1;

        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            throw new SeatNotFoundException("Invalid seat.");
        }

        return seats[row][col];
    }

    public void displayWindowSeatLayout() {

        System.out.println("\nWindow Seat Layout (- = Not Window, 0 = Available Window, X = Booked Window)");

        for (int i = 0; i < rows; i++) {

            char rowLabel = (char) ('A' + i);

            for (int j = 0; j < cols; j++) {

                if (j == 0 || j == cols - 1) {

                    if (seats[i][j]) {
                        System.out.print(rowLabel + "" + (j + 1) + "[X] ");
                    } else {
                        System.out.print(rowLabel + "" + (j + 1) + "[0] ");
                    }

                } else {
                    System.out.print(rowLabel + "" + (j + 1) + "[-] ");
                }
            }

            System.out.println();
        }
    }
}
