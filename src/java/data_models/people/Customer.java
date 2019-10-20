package data_models.people;

import data_models.locations.Address;

/**
 * Customer class holds information about an customer.
 *
 * Use the factory methods createNew or createExisting for instantiation.
 * 
 * Right now, this class does not store any information not already stored by the
 * Person base class but can be modified if additional information needs to be
 * saved.
 */
public class Customer extends Person {

    /**
     * Private constructor. Use the static factory methods for instantiation.
     * @param idNumber The ID number of the Customer stored in the database.
     * @param lastName The surname of the Customer.
     * @param firstName The first name of the Customer.
     * @param email The email address of the Customer.
     * @param primaryPhone The primary phone number for the Customer.
     * @param secondaryPhone The secondary phone for the Customer.
     * @param address The full address information for a Customer.
     * @param notes Any notes that need to be appended to a Customer file.
     */
    private Customer(Integer idNumber,
                     String lastName,
                     String firstName,
                     String email,
                     String primaryPhone,
                     String secondaryPhone,
                     Address address,
                     String notes) {
        super(idNumber, lastName, firstName, email, primaryPhone,
            secondaryPhone, address, notes);
    }

    /**
     * Factory method for creating a new Customer to be added to the database.
     * @param lastName The surname of the Customer.
     * @param firstName The first name of the Customer.
     * @param email The email address of the Customer.
     * @param primaryPhone The primary phone number for the Customer.
     * @param secondaryPhone The secondary phone for the Customer.
     * @param address The full address information for a Customer.
     * @param notes Any notes that need to be appended to a Customer file.
     * @return a new Customer from the provided data that will be saved to the
     *         database.
     */
    public static Customer createNew(String lastName,
                                     String firstName,
                                     String email,
                                     String primaryPhone,
                                     String secondaryPhone,
                                     Address address,
                                     String notes) {
        return new Customer(-1, lastName, firstName, email,
            primaryPhone, secondaryPhone, address, notes);
    }

    /**
     * Factory method for creating an Customer from existing data (typically the
     * database).
     * @param idNumber The ID number of the Customer stored in the database.
     * @param lastName The surname of the Customer.
     * @param firstName The first name of the Customer.
     * @param email The email address of the Customer.
     * @param primaryPhone The primary phone number for the Customer.
     * @param secondaryPhone The secondary phone for the Customer.
     * @param address The full address information for a Customer.
     * @param notes Any notes that need to be appended to a Customer file.
     * @return an existing Customer from existing data (typically the database).
     */
    public static Customer createExisting(Integer idNumber,
                                          String lastName,
                                          String firstName,
                                          String email,
                                          String primaryPhone,
                                          String secondaryPhone,
                                          Address address,
                                          String notes) {
        return new Customer(idNumber, lastName, firstName, email, primaryPhone,
            secondaryPhone, address, notes);
    }
}

