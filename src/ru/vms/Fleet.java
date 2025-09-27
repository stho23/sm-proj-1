package ru.vms;

/**
 * Holds vehicles in a resizable array; prints sorted by make then obtained date.
 * @author Reeham Anwar
 * @author Simeon Thomas
 */
public class Fleet {
    private static final int CAPACITY = 4;
    private static final int NOT_FOUND = -1;

    private Vehicle[] fleet;
    private int size;

    public Fleet() {
        this.fleet = new Vehicle[CAPACITY];
        this.size = 0;
    }

    private int find(Vehicle v) {
        for (int i = 0; i < size; i++) if (fleet[i].equals(v)) return i;
        return NOT_FOUND;
    }

    private void grow() {
        Vehicle[] n = new Vehicle[fleet.length + CAPACITY];
        for (int i = 0; i < size; i++) n[i] = fleet[i];
        fleet = n;
    }

    public void add(Vehicle v) {
        if (size == fleet.length) grow();
        fleet[size++] = v;
    }

    public void remove(Vehicle v) {
        int idx = find(v);
        if (idx == NOT_FOUND) return;
        fleet[idx] = fleet[size - 1];
        fleet[size - 1] = null;
        size--;
    }

    public boolean contains(Vehicle v) { return find(v) != NOT_FOUND; }

    public Vehicle getByPlate(String plate) {
        for (int i = 0; i < size; i++) {
            if (fleet[i].getPlate().equals(plate)) return fleet[i];
        }
        return null;
    }

    public int size() { return size; }

    public void printByMake() {
        if (size == 0) {
            System.out.println("There is no vehicle in the fleet.");
            return;
        }
        // insertion sort by Vehicle.compareTo() (make, then obtained date)
        for (int i = 1; i < size; i++) {
            Vehicle key = fleet[i];
            int j = i - 1;
            while (j >= 0 && fleet[j].compareTo(key) > 0) {
                fleet[j + 1] = fleet[j];
                j--;
            }
            fleet[j + 1] = key;
        }
        System.out.println("*List of vehicles in the fleet, ordered by make and date obtained.");
        for (int i = 0; i < size; i++) System.out.println(fleet[i]);
        System.out.println("*end of list.");
    }
}
