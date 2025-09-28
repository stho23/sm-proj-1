package ru.vms;

/**
 * Circular linked list of trips; prints ordered by ending date.
 * @author Simeon Thomas
 * @author Reeham Anwar
 */
public class TripList {
    private static class Node {
        Trip data;
        Node next;
        Node(Trip t) { this.data = t; }
    }
    private Node last;

    /**
     * Adds a completed trip to the circular linked list.
     * @param t the trip to add
     */
    public void add(Trip t) {
        Node n = new Node(t);
        if (last == null) {
            last = n;
            last.next = last;
        } else {
            n.next = last.next;
            last.next = n;
            last = n;
        }
    }

    /**
     * Prints all completed trips ordered by the booking end date.
     * If there are no trips, prints the required message.
     */
    public void print() {
        if (last == null) {
            System.out.println("There is no archived trips.");
            return;
        }
        int count = 0;
        for (Node p = last.next; ; p = p.next) { count++; if (p == last) break; }
        Trip[] arr = new Trip[count];
        int i = 0; for (Node p = last.next; ; p = p.next) { arr[i++] = p.data; if (p == last) break; }
        for (int k = 1; k < arr.length; k++) {
            Trip key = arr[k];
            int j = k - 1;
            while (j >= 0 && arr[j].getBooking().getEnd().compareTo(key.getBooking().getEnd()) > 0) {
                arr[j + 1] = arr[j]; j--;
            }
            arr[j + 1] = key;
        }
        System.out.println("*List of completed trips ordered by ending date.");
        for (Trip t : arr) System.out.println(t);
        System.out.println("*end of list.");
    }
}
