package data_models.people;

import data_models.locations.Address;

/**
 * Employee class holds information about an employee.
 *
 * Use the factory methods createNew or createExisting for instantiation.
 */
public class Employee extends Person {
    private String title;

    /**
     * Private constructor. Use the static factory methods for instantiation.
     * @param idNumber The ID number of the Employee stored in the database.
     * @param lastName The surname of the Employee.
     * @param firstName The first name of the Employee.
     * @param email The email address of the Employee.
     * @param primaryPhone The primary phone number for the Employee.
     * @param secondaryPhone The secondary phone for the Employee.
     * @param address The full address information for a Employee.
     * @param notes Any notes that need to be appended to a Employee file.
     * @param title The title of the Employee (ie Sales, Marketing, etc).
     */
    public Employee(Integer idNumber,
                     String lastName,
                     String firstName,
                     String email,
                     String primaryPhone,
                     String secondaryPhone,
                     Address address,
                     String notes,
                     String title) {
        super(idNumber, lastName, firstName, email, primaryPhone, secondaryPhone, address, notes);
        this.title = title;
    }

    /**
     * Factory method for creating a new Employee to be added to the database.
     * @param lastName The surname of the Employee.
     * @param firstName The first name of the Employee.
     * @param email The email address of the Employee.
     * @param primaryPhone The primary phone number for the Employee.
     * @param secondaryPhone The secondary phone for the Employee.
     * @param address The full address information for a Employee.
     * @param notes Any notes that need to be appended to a Employee file.
     * @param title The title of the Employee (ie Sales, Marketing, etc).
     * @return a new Employee from the provided data that will be saved to the
     *         database.
     */
    public static Employee createNew(String lastName,
                                   String firstName,
                                   String email,
                                   String primaryPhone,
                                   String secondaryPhone,
                                   Address address,
                                   String notes,
                                   String title) {
        return new Employee(-1, lastName, firstName, email, primaryPhone,
            secondaryPhone,address, notes, title);
    }

    /**
     * Factory method for creating an Employee from existing data (typically the
     * database).
     * @param idNumber The ID number of the Employee stored in the database.
     * @param lastName The surname of the Employee.
     * @param firstName The first name of the Employee.
     * @param email The email address of the Employee.
     * @param primaryPhone The primary phone number for the Employee.
     * @param secondaryPhone The secondary phone for the Employee.
     * @param address The full address information for a Employee.
     * @param notes Any notes that need to be appended to a Employee file.
     * @param title The title of the Employee (ie Sales, Marketing, etc).
     * @return an existing Employee from existing data (typically the database).
     */
    public static Employee createExisting(Integer idNumber,
                                        String lastName,
                                        String firstName,
                                        String email,
                                        String primaryPhone,
                                        String secondaryPhone,
                                        Address address,
                                        String notes,
                                        String title) {
        return new Employee(idNumber, lastName, firstName, email, primaryPhone,
            secondaryPhone, address, notes, title);
    }

    /**Getter for title*/
    public String getTitle() {
        return title;
    }

    /**Setter for title*/
    public void setTitle(String title) {
        this.title = title;
    }
}
