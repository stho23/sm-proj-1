package ru.vms;

/**
 * Circular linked list of trips; prints ordered by ending date.
 * Authors: Reeham Anwar, Simeon Thomas
 */
public class TripList {
    private static class Node {
        Trip data;
        Node next;
        Node(Trip t) { this.data = t; }
    }
    private Node last; // the reference to the last node of the linked list.

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

    public void print() {
        if (last == null) {
            System.out.println("There is no archived trips.");
            return;
        }
        // count
        int count = 0;
        for (Node p = last.next; ; p = p.next) { count++; if (p == last) break; }
        // copy to array
        Trip[] arr = new Trip[count];
        int i = 0; for (Node p = last.next; ; p = p.next) { arr[i++] = p.data; if (p == last) break; }
        // insertion sort by booking end date
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
