package ru.vms;

import java.util.Calendar;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * The Frontend class provides the command-line interface for the Vehicle
 * Management System. It reads user commands, validates input in the required
 * left-to-right order, and coordinates actions between the Fleet, Reservation,
 * and TripList classes.
 * @author Reeham Anwar
 * @author Simeon Thomas
 */
public class Frontend {
    private final Fleet fleet = new Fleet();
    private final Reservation reservations = new Reservation();
    private final TripList trips = new TripList();

    /**
     * Starts the loop, reading commands from standard input,
     * printing the system start message, and terminating when 'Q'
     * is entered. Calls process() for each non-empty command line.
     */
    public void run() {
        System.out.println("Vehicle Management System is running.");
        Scanner sc = new Scanner(System.in);
        while (true) {
            if (!sc.hasNextLine()) break;
            String line = sc.nextLine().trim();
            if (line.isEmpty()) continue;
            if (line.charAt(0) == 'Q') {
                System.out.println("Vehicle Management System is terminated.");
                break;
            }
            process(line);
        }
        sc.close();
    }

    /**
     * Processes a single input command line by tokenizing the string,
     * identifying the command, and invoking the appropriate logic.
     * All validations are performed in left-to-right order.
     *
     * @param line the command line entered by the user
     */
    private void process(String line) {
        StringTokenizer st = new StringTokenizer(line);
        if (!st.hasMoreTokens()) return;
        String cmd = st.nextToken();

        switch (cmd) {
            case "A": handleAdd(st); break;
            case "D": handleDelete(st); break;
            case "B": handleBook(st); break;
            case "C": handleCancel(st); break;
            case "R": handleReturn(st); break;
            case "PF": fleet.printByMake(); break;
            case "PR": reservations.printByVehicle(); break;
            case "PD": reservations.printByDept(); break;
            case "PT": trips.print(); break;
            default:
                System.out.println(cmd + " - invalid command!");
        }
    }

    /** Processes the A command (add vehicle) */
    private void handleAdd(StringTokenizer st) {
        String plate = st.nextToken();
        String obtainedTok = st.nextToken();
        Date obtained = Date.fromString(obtainedTok);
        if (obtained == null || !obtained.isValid()) {
            System.out.println(obtainedTok + " - invalid calendar date.");
            return;
        }
        if (!isBeforeToday(obtained)) {
            System.out.println(obtainedTok + " - is today or a future date.");
            return;
        }
        String makeTok = st.nextToken();
        Make make;
        try {
            make = Make.valueOf(makeTok.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(makeTok + " - invalid make.");
            return;
        }
        String mileageTok = st.nextToken();
        int mileage;
        try {
            mileage = Integer.parseInt(mileageTok);
        } catch (NumberFormatException e) {
            System.out.println(mileageTok + " - invalid mileage.");
            return;
        }
        if (mileage <= 0) {
            System.out.println(mileageTok + " - invalid mileage.");
            return;
        }
        Vehicle probe = new Vehicle(plate, obtained, make, mileage);
        if (fleet.contains(probe)) {
            System.out.println(plate + " is already in the fleet.");
            return;
        }
        fleet.add(probe);
        System.out.println(probe + " has been added to the fleet.");
    }

    /** Processes the D command (delete vehicle) */
    private void handleDelete(StringTokenizer st) {
        String plate = st.nextToken();
        Vehicle existing = fleet.getByPlate(plate);
        if (existing == null) {
            System.out.println(plate + " is not in the fleet.");
            return;
        }
        if (hasVehicleBookings(plate)) {
            System.out.println(plate + " - has existing bookings; cannot be removed.");
            return;
        }
        fleet.remove(existing);
        System.out.println(existing + " has been removed from the fleet.");
    }

    /** Processes the B command (book vehicle) */
    private void handleBook(StringTokenizer st) {
        String beginTok = st.nextToken();
        Date begin = Date.fromString(beginTok);
        if (begin == null || !begin.isValid()) {
            System.out.println(beginTok + " - beginning date is not a valid calendar date.");
            return;
        }
        if (isBeforeToday(begin)) {
            System.out.println(beginTok + " - beginning date is not today or a future date.");
            return;
        }
        if (!withinThreeMonths(begin)) {
            System.out.println(beginTok + " - beginning date beyond 3 months.");
            return;
        }

        String endTok = st.nextToken();
        Date end = Date.fromString(endTok);
        if (end == null || !end.isValid()) {
            System.out.println(endTok + " - ending date is not a valid calendar date.");
            return;
        }
        if (end.compareTo(begin) < 0) {
            System.out.println(endTok + " - ending date must be equal or after the beginning date " + beginTok);
            return;
        }
        if (daysSpan(begin, end) > 7) {
            System.out.println(beginTok + " ~ " + endTok + " - duration more than a week.");
            return;
        }

        String plate = st.nextToken();
        Vehicle veh = fleet.getByPlate(plate);
        if (veh == null) {
            System.out.println(plate + " is not in the fleet.");
            return;
        }
        if (!isVehicleAvailable(plate, begin, end)) {
            System.out.println(plate + " - booking with " + beginTok + " ~ " + endTok + " not available.");
            return;
        }

        String empTok = st.nextToken();
        Employee emp;
        try {
            emp = Employee.valueOf(empTok.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println(empTok + " - not an eligible employee to book.");
            return;
        }
        if (hasEmployeeConflict(emp, begin, end)) {
            System.out.println(emp.name() + " - has an existing booking conflicting with the beginning date " + beginTok);
            return;
        }

        Booking b = new Booking(begin, end, emp, veh);
        reservations.add(b);
        System.out.println(b + " booked.");
    }

    /** Processes the C command (cancel booking) */
    private void handleCancel(StringTokenizer st) {
        String beginTok = st.nextToken();
        String endTok = st.nextToken();
        String plate = st.nextToken();

        Date begin = Date.fromString(beginTok);
        Date end = Date.fromString(endTok);
        if (begin == null || end == null || !begin.isValid() || !end.isValid()) {
            return;
        }
        Booking b = findBooking(begin, end, plate);
        if (b == null) {
            System.out.println(plate + ":" + beginTok + " ~ " + endTok + " - cannot find the booking.");
            return;
        }
        reservations.remove(b);
        System.out.println(plate + ":" + beginTok + " ~ " + endTok + " has been canceled.");
    }

    /** Processes the R command (return vehicle) */
    private void handleReturn(StringTokenizer st) {
        String endTok = st.nextToken();
        String plate = st.nextToken();
        String mileageTok = st.nextToken();

        Date end = Date.fromString(endTok);
        if (end == null || !end.isValid()) {
            return;
        }
        Booking b = findBookingByEndAndPlate(end, plate);
        if (b == null) {
            System.out.println(plate + " booked with ending date " + endTok + " - cannot find the booking.");
            return;
        }

        Date earliest = earliestEndingDate();
        if (earliest != null && end.compareTo(earliest) != 0) {
            System.out.println(plate + " booked with ending date " + endTok + " - returning not in order of ending date.");
            return;
        }

        int newMileage;
        try { newMileage = Integer.parseInt(mileageTok); }
        catch (NumberFormatException e) {
            System.out.println(mileageTok + " - invalid mileage.");
            return;
        }
        if (newMileage <= 0) {
            System.out.println(mileageTok + " - invalid mileage.");
            return;
        }

        Vehicle veh = b.getVehicle();
        int original = veh.getMileage();
        if (newMileage <= original) {
            System.out.println("Invalid mileage - current mileage: " + original + " entered mileage: " + newMileage);
            return;
        }

        Trip t = new Trip(b, original, newMileage);
        trips.add(t);
        veh.setMileage(newMileage);
        reservations.remove(b);
        System.out.println("Trip completed: " + t);
    }

    private boolean isBeforeToday(Date d) {
        Calendar today = Calendar.getInstance();
        Calendar c = toCal(d);
        zeroHMS(today); zeroHMS(c);
        return c.before(today);
    }

    private boolean withinThreeMonths(Date d) {
        Calendar today = Calendar.getInstance();
        zeroHMS(today);
        Calendar limit = (Calendar) today.clone();
        limit.add(Calendar.MONTH, 3);
        Calendar c = toCal(d);
        zeroHMS(c);
        return !c.after(limit);
    }

    private int daysSpan(Date a, Date b) {
        Calendar ca = toCal(a), cb = toCal(b);
        zeroHMS(ca); zeroHMS(cb);
        long diffMs = cb.getTimeInMillis() - ca.getTimeInMillis();
        long d = diffMs / (24L*60*60*1000);
        return (int) d + 1;
    }

    private Calendar toCal(Date d) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR,  d.getYear());
        c.set(Calendar.MONTH, d.getMonth() - 1);
        c.set(Calendar.DAY_OF_MONTH, d.getDay());
        return c;
    }

    private void zeroHMS(Calendar c) {
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
    }

    private boolean overlaps(Date a1, Date a2, Date b1, Date b2) {
        return a1.compareTo(b2) <= 0 && b1.compareTo(a2) <= 0;
    }

    private boolean isVehicleAvailable(String plate, Date begin, Date end) {
        for (int i = 0; i < reservations.size(); i++) {
            Booking b = reservations.get(i);
            if (b.getVehicle().getPlate().equals(plate)) {
                if (overlaps(begin, end, b.getBegin(), b.getEnd())) return false;
            }
        }
        return true;
    }

    private boolean hasEmployeeConflict(Employee emp, Date begin, Date end) {
        for (int i = 0; i < reservations.size(); i++) {
            Booking b = reservations.get(i);
            if (b.getEmployee() == emp) {
                if (overlaps(begin, end, b.getBegin(), b.getEnd())) return true;
            }
        }
        return false;
    }

    private boolean hasVehicleBookings(String plate) {
        for (int i = 0; i < reservations.size(); i++) {
            if (reservations.get(i).getVehicle().getPlate().equals(plate)) return true;
        }
        return false;
    }

    private Booking findBooking(Date begin, Date end, String plate) {
        for (int i = 0; i < reservations.size(); i++) {
            Booking b = reservations.get(i);
            if (b.getVehicle().getPlate().equals(plate)
                    && b.getBegin().equals(begin)
                    && b.getEnd().equals(end)) return b;
        }
        return null;
    }

    private Booking findBookingByEndAndPlate(Date end, String plate) {
        for (int i = 0; i < reservations.size(); i++) {
            Booking b = reservations.get(i);
            if (b.getVehicle().getPlate().equals(plate)
                    && b.getEnd().equals(end)) return b;
        }
        return null;
    }

    private Date earliestEndingDate() {
        Date best = null;
        for (int i = 0; i < reservations.size(); i++) {
            Date e = reservations.get(i).getEnd();
            if (best == null || e.compareTo(best) < 0) best = e;
        }
        return best;
    }
}
