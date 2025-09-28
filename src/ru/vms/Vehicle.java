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

    /**
     * Constructs a vehicle with the given data.
     * @param plate the license plate number
     * @param obtained the date the vehicle was obtained
     * @param make the make of the vehicle
     * @param mileage the current odometer reading
     */
    public Vehicle(String plate, Date obtained, Make make, int mileage) {
        this.plate = plate;
        this.obtained = obtained;
        this.make = make;
        this.mileage = mileage;
    }

    /**
     * Gets the license plate number.
     * @return the license plate
     */
    public String getPlate() {
        return plate;
    }

    /**
     * Gets the obtained date.
     * @return the obtained date
     */
    public Date getObtained() {
        return obtained;
    }

    /**
     * Gets the make.
     * @return the make
     */
    public Make getMake() {
        return make;
    }

    /**
     * Gets the current mileage.
     * @return the mileage
     */
    public int getMileage() {
        return mileage;
    }

    /**
     * Sets the current mileage.
     * @param m the new odometer reading
     */
    public void setMileage(int m) {
        this.mileage = m;
    }

    /**
     * Two vehicles are equal if they share the same license plate number.
     * @param obj the object to compare
     * @return true if plates match, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Vehicle)) return false;
        Vehicle v = (Vehicle) obj;
        return this.plate.equals(v.plate);
    }

    /**
     * Text form: plate:MAKE:mm/dd/yyyy [mileage:####]
     * @return the string representation
     */
    @Override
    public String toString() {
        return plate + ":" + make + ":" + obtained + " [mileage:" + mileage + "]";
    }

    /**
     * Orders by make (alphabetical by enum name), then by obtained date.
     * @param o the other vehicle
     * @return a negative number, zero, or a positive number as this vehicle is less than, equal to, or greater than the other
     */
    @Override
    public int compareTo(Vehicle o) {
        int makeCmp = this.make.name().compareTo(o.make.name());
        if (makeCmp != 0) return makeCmp;
        return this.obtained.compareTo(o.obtained);
    }

    public static void main(String[] args) {
        Vehicle v1 = new Vehicle("AA111", Date.fromString("1/1/2020"), Make.CHEVY, 100);
        Vehicle v2 = new Vehicle("BB222", Date.fromString("1/1/2019"), Make.FORD, 200);
        System.out.println(v1.compareTo(v2));

        Vehicle v3 = new Vehicle("CC333", Date.fromString("1/1/2020"), Make.TOYOTA, 300);
        Vehicle v4 = new Vehicle("DD444", Date.fromString("1/1/2020"), Make.HONDA, 400);
        System.out.println(v3.compareTo(v4));

        Vehicle v5 = new Vehicle("EE555", Date.fromString("3/15/2022"), Make.CHEVY, 500);
        Vehicle v6 = new Vehicle("FF666", Date.fromString("3/15/2022"), Make.CHEVY, 600);
        System.out.println(v5.compareTo(v6));
    }
}
