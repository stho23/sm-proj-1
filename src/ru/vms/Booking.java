package ru.vms;

/**
 * Represents a booking for a vehicle by an employee.
 * This class holds details about a specific booking, including the start and end dates,
 * the employee who made the booking, and the vehicle that was booked.
 * @author Simeon Thomas
 * @author Reeham Anwar
 */
public class Booking {

    private Date begin;
    private Date end;
    private Employee employee;
    private Vehicle vehicle;

    /**
     * Creates a booking with the given dates, employee, and vehicle.
     * @param begin the beginning date of the booking
     * @param end the ending date of the booking
     * @param employee the employee who made the booking
     * @param vehicle the vehicle being booked
     */
    public Booking(Date begin, Date end, Employee employee, Vehicle vehicle) {
        this.begin = begin;
        this.end = end;
        this.employee = employee;
        this.vehicle = vehicle;
    }

    /**
     * Gets the beginning date of this booking.
     * @return the beginning date
     */
    public Date getBegin() {
        return begin;
    }

    /**
     * Gets the ending date of this booking.
     * @return the ending date
     */
    public Date getEnd() {
        return end;
    }

    /**
     * Gets the employee who made this booking.
     * @return the employee
     */
    public Employee getEmployee() {
        return employee;
    }

    /**
     * Gets the vehicle booked in this reservation.
     * @return the vehicle
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * Compares this booking to another object for equality.
     * Two bookings are equal if they have the same vehicle, beginning date, and ending date.
     * @param obj the object to compare with
     * @return true if the bookings are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Booking)) {
            return false;
        }
        Booking other = (Booking) obj;
        return vehicle.equals(other.vehicle)
                && begin.equals(other.begin)
                && end.equals(other.end);
    }

    /**
     * Returns a string including the vehicle, dates, and employee.
     * @return the string representation of this booking
     */
    @Override
    public String toString() {
        return vehicle + " [beginning " + begin + " ending " + end + ":" + employee + "]";
    }
}
