package ru.vms;

/**
 * Enum class for employees eligible to book vehicles.
 * Each employee is associated with a department.
 *
 * Eligible employees:
 * Patel (Computer Science), Lim (Electricla Engineering), Zimnes (Computer Science), Harper (Electrical Engineering),
 * Kaur (Information Technology and Informatics), Taylor (MATH), Ramesh (MATH), Ceravolo (Business Analytics and Information Technology)
 * @author Reeham Anwar
 * @author Simeon Thomas
 */
public enum Employee {
    PATEL(Department.COMPUTER_SCIENCE),
    LIM(Department.ELECTRICAL_ENGINEERING),
    ZIMNES(Department.COMPUTER_SCIENCE),
    HARPER(Department.ELECTRICAL_ENGINEERING),
    KAUR(Department.INFORMATION_TECHNOLOGY_AND_INFORMATICS),
    TAYLOR(Department.MATHEMATICS),
    RAMESH(Department.MATHEMATICS),
    CERAVOLO(Department.BUSINESS_ANALYTICS_AND_INFORMATION_TECHNOLOGY);

    private final Department dept;

    Employee(Department dept) {
        this.dept = dept;
    }

    public Department getDepartment() {
        return dept;
    }
}
