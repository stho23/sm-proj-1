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

    public Trip(Booking booking, int beginMileage, int endMileage) {
        this.booking = booking;
        this.beginMileage =  beginMileage;
        this.endMileage = endMileage;
    }
    public Booking getBooking() {
        return booking;
    }
    public int getBeginMileage() {
        return beginMileage;
    }
    public int getEndMileage() {
        return endMileage;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Trip)) return false;
        Trip t = (Trip) obj;
        return this.booking.equals(t.booking);
    }

    @Override
    public String toString() {
        int used = endMileage - beginMileage;
        return booking.getVehicle().getPlate() + " " + booking.getBegin() + " ~ " + booking.getEnd()
                + " original mileage: " + beginMileage + " current mileage: " + endMileage + " mileage used: " + used;
    }
}
