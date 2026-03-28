package prototype;

public class AircraftService {

    private Node head;

    private class Node {
        Aircraft data;
        Node next;

        Node(Aircraft data) {
            this.data = data;
            this.next = null;
        }
    }

    public void addAircraft(String id, String route, int rows, int cols, int atime, int dtime) {

        Aircraft aircraft = new Aircraft(id, route, rows, cols, atime, dtime);

        Node newNode = new Node(aircraft);

        if (head == null) {
            head = newNode;
            System.out.println("Aircraft added.");
            return;
        }

        Node temp = head;

        while (temp.next != null) {
            temp = temp.next;
        }

        temp.next = newNode;

        System.out.println("Aircraft added.");
    }

    public void removeAircraft(String id) {

        if (head == null) {
            System.out.println("No aircraft available.");
            return;
        }

        if (head.data.getAircraftId().equals(id)) {
            head = head.next;
            System.out.println("Aircraft removed.");
            return;
        }

        Node current = head;
        Node previous = null;

        while (current != null && !current.data.getAircraftId().equals(id)) {
            previous = current;
            current = current.next;
        }

        if (current == null) {
            System.out.println("Aircraft not found.");
            return;
        }

        previous.next = current.next;

        System.out.println("Aircraft removed.");
    }

    public Aircraft getAircraft(String id) {

        Node temp = head;

        while (temp != null) {

            if (temp.data.getAircraftId().equals(id)) {
                return temp.data;
            }

            temp = temp.next;
        }

        return null;
    }

    public void displayAllAircraft() {

        if (head == null) {
            System.out.println("No aircraft available.");
            return;
        }

        System.out.println("\nAvailable Aircraft");
        System.out.println("--------------------------------------------------");
        System.out.println("ID        ROUTE           ARRIVAL    DEPARTURE");
        System.out.println("--------------------------------------------------");

        Node temp = head;

        while (temp != null) {

            Aircraft a = temp.data;

            System.out.printf("%-10s %-15s %-10d %-10d\n",
                    a.getAircraftId(),
                    a.getRoute(),
                    a.getArrivalTime(),
                    a.getDepartureTime());

            temp = temp.next;
        }
    }
    
    public void sortAircraft() {

        if (head == null || head.next == null) {
            return;
        }

        boolean swapped;

        do {

            swapped = false;

            Node current = head;

            while (current.next != null) {

                Aircraft a1 = current.data;
                Aircraft a2 = current.next.data;

                if (a1.getDepartureTime() > a2.getDepartureTime()) {

                    current.data = a2;
                    current.next.data = a1;

                    swapped = true;
                }

                current = current.next;
            }

        } while (swapped);
    }
    
    public void removeAccountBookings(String accountId) {

        Node temp = head;

        while (temp != null) {

            temp.data.cancelBooking(accountId);

            temp = temp.next;
        }
    }
}
