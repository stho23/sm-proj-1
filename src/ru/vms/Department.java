package ru.vms;

/**
 * Enum class for all the departments
 * Allowed Departments: Computer Science, Electrical Engineering, Information Technology and Informatics, Math, Business Analytics and Information Technology
 * @author Simeon Thomas
 * @author Reeham Anwar
 */
public enum Department {
    COMPUTER_SCIENCE("Computer Science"),
    ELECTRICAL_ENGINEERING("Electrical Engineering"),
    INFORMATION_TECHNOLOGY_AND_INFORMATICS("Information Technology and Informatics"),
    MATHEMATICS("Mathematics"),
    BUSINESS_ANALYTICS_AND_INFORMATION_TECHNOLOGY("Business Analytics and Information Technology");

    private final String displayName;

    /**
     * Constructs a department with a display name.
     * @param displayName the department name
     */
    Department(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets the department name.
     * @return the display name
     */
    public String getDisplayName() {
        return displayName;
    }
}
