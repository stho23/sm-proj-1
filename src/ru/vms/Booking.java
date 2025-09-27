package ru.vms;

/**
 * Represents a booking for a vehicle by an employee.
 * This class holds details about a specific booking, including the start and end dates, the employee who made the booking, and the vehicle that was booked
 * @author Simeon Thomas
 * @author Reeham Anwar
 */
public class Booking  {
    private Date begin;
    private Date end;
    private Employee employee;
    private Vehicle vehicle;

    public Booking(Date begin, Date end, Employee employee, Vehicle vehicle) {
        this.begin = begin;
        this.end = end;
        this.employee = employee;
        this.vehicle = vehicle;
    }

    public Date getBegin() {
        return begin;
    }
    public Date getEnd() {
        return end;
    }
    public Employee getEmployee() {
        return employee;
    }
    public Vehicle getVehicle() {
        return vehicle;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Booking)) return false;
        Booking b = (Booking) obj;
        return this.vehicle.equals(b.vehicle) && this.begin.equals(b.begin) && this.end.equals(b.end);
    }

    @Override
    public String toString() {
        return vehicle + " [beginning " + begin + " ending " + end + ":" + employee + "]";
    }
}
