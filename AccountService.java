package prototype;

public class AccountService {

    private Node head;

    private class Node {
        Account data;
        Node next;

        Node(Account data) {
            this.data = data;
            this.next = null;
        }
    }

    public void addAccount(String id) {

        Account acc = new Account(id);
        Node newNode = new Node(acc);

        if (head == null) {
            head = newNode;
            System.out.println("Account created.");
            return;
        }

        Node temp = head;

        while (temp.next != null) {
            temp = temp.next;
        }

        temp.next = newNode;

        System.out.println("Account created.");
    }

    public Account getAccount(String id) {

        Node temp = head;

        while (temp != null) {

            if (temp.data.getAccountId().equals(id)) {
                return temp.data;
            }

            temp = temp.next;
        }

        return null;
    }

    public void removeAccount(String id) {

        if (head == null) {
            System.out.println("No accounts exist.");
            return;
        }

        if (head.data.getAccountId().equals(id)) {
            head = head.next;
            System.out.println("Account removed.");
            return;
        }

        Node current = head;
        Node previous = null;

        while (current != null &&
               !current.data.getAccountId().equals(id)) {

            previous = current;
            current = current.next;
        }

        if (current == null) {
            System.out.println("Account not found.");
            return;
        }

        previous.next = current.next;

        System.out.println("Account removed.");
    }

    public void displayAccounts() {

        if (head == null) {
            System.out.println("No accounts available.");
            return;
        }

        Node temp = head;

        while (temp != null) {

            System.out.println(
                "Account ID: " +
                temp.data.getAccountId()
            );

            temp = temp.next;
        }
    }
}
