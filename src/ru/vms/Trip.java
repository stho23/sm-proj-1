package ru.vms;

/**
 * This class stores information about a trip, including the associated booking and the start and end mileage of the vehicle for that trip.
 * @author Simeon Thomas
 * @author Reeham Anwar
 */
public class Trip {
    private Booking booking;
    private int beginMileage;
    private int endMileage;

    /**
     * Creates a trip with the given booking and mileage readings.
     * @param booking the original booking associated with this trip
     * @param beginMileage the mileage at the start of the trip
     * @param endMileage the mileage at the end of the trip
     */
    public Trip(Booking booking, int beginMileage, int endMileage) {
        this.booking = booking;
        this.beginMileage =  beginMileage;
        this.endMileage = endMileage;
    }

    /**
     * Gets the booking associated with this trip.
     * @return the booking
     */
    public Booking getBooking() {
        return booking;
    }

    /**
     * Returns true if two trips have the same booking details, false otherwise.
     * @param obj the object to compare to
     * @return true if equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Trip)) return false;
        Trip t = (Trip) obj;
        return this.booking.equals(t.booking);
    }

    /**
     * Returns string including dates and mileage usage.
     * @return the string representation of this trip
     */
    @Override
    public String toString() {
        int used = endMileage - beginMileage;
        return booking.getVehicle().getPlate() + " " + booking.getBegin() + " ~ " + booking.getEnd()
                + " original mileage: " + beginMileage + " current mileage: " + endMileage + " mileage used: " + used;
    }
}
