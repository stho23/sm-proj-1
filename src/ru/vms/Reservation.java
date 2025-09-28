package ru.vms;

/**
 * Holds bookings in a resizable array; prints by vehicle/begin-date or by dept/employee.
 * Enforces in-place sorting; no Java Collections allowed.
 * Authors: Reeham Anwar, Simeon Thomas
 */
public class Reservation {
    private static final int CAPACITY = 4;
    private static final int NOT_FOUND = -1;

    private Booking[] bookings;
    private int size;

    /**
     * Creates an empty reservation list with the initial capacity.
     */
    public Reservation() {
        this.bookings = new Booking[CAPACITY];
        this.size = 0;
    }

    private int find(Booking b) {
        for (int i = 0; i < size; i++) {
            if (bookings[i].equals(b)) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    public int size() {
        return size;
    }

    private void grow() {
        Booking[] n = new Booking[bookings.length + CAPACITY];
        for (int i = 0; i < size; i++) {
            n[i] = bookings[i];
        }
        bookings = n;
    }

    /**
     * Adds a booking to the list, growing the underlying array as needed.
     * @param b the booking to add
     */
    public void add(Booking b) {
        if (size == bookings.length) grow();
        bookings[size++] = b;
    }

    /**
     * Removes the given booking if present by overwriting it with the last element.
     * Does nothing if the booking is not found.
     * @param b the booking to remove
     */
    public void remove(Booking b) {
        int idx = find(b);
        if (idx == NOT_FOUND) return;
        bookings[idx] = bookings[size - 1];
        bookings[size - 1] = null;
        size--;
    }

    /**
     * Checks whether the given booking exists in the list.
     * @param b the booking to search for
     * @return true if found, false otherwise
     */
    public boolean contains(Booking b) { return find(b) != NOT_FOUND; }

    /**
     * Gets the booking at the given index.
     * @param i the index in the list (0-based, must be &lt; size)
     * @return the booking at the index
     */
    public Booking get(int i) { return bookings[i]; }

    /**
     * Prints the bookings ordered by license plate, then beginning date.
     * Prints a standard message if there is no booking.
     */
    public void printByVehicle() {
        if (size == 0) {
            System.out.println("There is no booking record.");
            return;
        }
        for (int i = 1; i < size; i++) {
            Booking key = bookings[i];
            int j = i - 1;
            while (j >= 0 && compareByVehicle(bookings[j], key) > 0) {
                bookings[j + 1] = bookings[j];
                j--;
            }
            bookings[j + 1] = key;
        }
        System.out.println("*List of reservations ordered by license plate number and beginning date.");
        for (int i = 0; i < size; i++) System.out.println(bookings[i]);
        System.out.println("*end of list.");
    }

    /**
     * Prints the bookings ordered by department, then by employee.
     * Prints a standard message if there is no booking.
     */
    public void printByDept() {
        if (size == 0) {
            System.out.println("There is no booking record.");
            return;
        }
        for (int i = 1; i < size; i++) {
            Booking key = bookings[i];
            int j = i - 1;
            while (j >= 0 && compareByDept(bookings[j], key) > 0) {
                bookings[j + 1] = bookings[j];
                j--;
            }
            bookings[j + 1] = key;
        }
        System.out.println("*List of reservations ordered by department and employee.");

        printDeptSection(Department.BUSINESS_ANALYTICS_AND_INFORMATION_TECHNOLOGY);
        printDeptSection(Department.COMPUTER_SCIENCE);
        printDeptSection(Department.ELECTRICAL_ENGINEERING);
        printDeptSection(Department.INFORMATION_TECHNOLOGY_AND_INFORMATICS);
        printDeptSection(Department.MATHEMATICS);

        System.out.println("*end of list.");
    }

    private void printDeptSection(Department dept) {
        boolean printedHeader = false;
        for (int i = 0; i < size; i++) {
            Booking b = bookings[i];
            if (b.getEmployee().getDepartment() == dept) {
                if (!printedHeader) {
                    System.out.println("--" + dept.getDisplayName() + "--");
                    printedHeader = true;
                }
                System.out.println(b);
            }
        }
    }

    private int compareByVehicle(Booking a, Booking b) {
        int plateCmp = a.getVehicle().getPlate().compareTo(b.getVehicle().getPlate());
        if (plateCmp != 0) return plateCmp;
        return a.getBegin().compareTo(b.getBegin());
    }

    private int compareByDept(Booking a, Booking b) {
        int deptCmp = a.getEmployee().getDepartment().getDisplayName()
                .compareTo(b.getEmployee().getDepartment().getDisplayName());
        if (deptCmp != 0) return deptCmp;
        return a.getEmployee().name().compareTo(b.getEmployee().name());
    }
}
