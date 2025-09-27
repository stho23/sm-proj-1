package ru.vms;

/**
 * Represents a vehicle in the fleet.
 * This class stores detailed information about a vehicle, including its license plate, the date it was obtained, its make, and its current mileage.
 * It also provides methods for comparing vehicles and for string representation.
 * @author Simeon Thomas
 * @author Reeham Anwar
 */
public class Vehicle implements Comparable<Vehicle> {
    private String plate;
    private Date obtained;
    private Make make;
    private int mileage;

    public Vehicle(String plate, Date obtained, Make make, int mileage) {
        this.plate = plate;
        this.obtained = obtained;
        this.make = make;
        this.mileage = mileage;
    }

    public String getPlate() {
        return plate;
    }
    public Date getObtained() {
        return obtained;
    }
    public Make getMake() {
        return make;
    }
    public int getMileage() {
        return mileage;
    }
    public void setMileage(int m) {
        this.mileage = m;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vehicle)) return false;
        Vehicle v = (Vehicle) obj;
        return this.plate.equals(v.plate);
    }

    @Override
    public String toString() {
        return plate + ":" + make + ":" + obtained + " [mileage:" + mileage + "]";
    }

    @Override
    public int compareTo(Vehicle o) {
        int makeCmp = this.make.name().compareTo(o.make.name());
        if (makeCmp != 0) return makeCmp;
        return this.obtained.compareTo(o.obtained);
    }

    public static void main(String[] argos) {
        Vehicle a = new Vehicle("111AA", Date.fromString("1/1/2020"), Make.CHEVY, 100);
        Vehicle b = new Vehicle("222BB", Date.fromString("1/1/2021"), Make.CHEVY, 200);
        Vehicle c = new Vehicle("333CC", Date.fromString("1/1/2021"), Make.CHEVY, 300);
        System.out.println(a.compareTo(b) < 0);
        System.out.println(b.compareTo(c) == 0);
        Vehicle d = new Vehicle("444DD", Date.fromString("1/1/2019"), Make.FORD, 400);
        System.out.println(d.compareTo(a) < 0);
    }
}
