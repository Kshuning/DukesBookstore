package data_models.people;

import data_models.locations.Address;

import java.util.Objects;

/**
 * Base class for the people we store in the database.
 *
 * Any class extending this should implement factory methods for creating a new
 * and existing object rather than allow instantiation from a constructor.
 */
public abstract class Person {
    private Integer idNumber;
    private String lastName;
    private String firstName;
    private String email;
    private String primaryPhone;
    private String secondaryPhone;
    private Address address;
    private String notes;

    /**
     * This constructor should be used to construct a new Person object by the
     * inheriting class. The class should also typically have a private constructor
     * and provide factory methods for creating objects that extend Person.
     * @param idNumber The ID number of the person stored in the database.
     * @param lastName The surname of the Person.
     * @param firstName The first name of the Person.
     * @param email The email address of the Person.
     * @param primaryPhone The primary phone number for the person.
     * @param secondaryPhone The secondary phone number for the person.
     * @param address The full address information for a Person.
     * @param notes Any notes that need to be appended to a Person file.
     */
    protected Person(Integer idNumber,
                     String lastName,
                     String firstName,
                     String email,
                     String primaryPhone,
                     String secondaryPhone,
                     Address address,
                     String notes) {
        setIdNumber(idNumber);
        setLastName(lastName);
        setFirstName(firstName);
        setEmail(email);
        setPrimaryPhone(primaryPhone);
        setSecondaryPhone(secondaryPhone);
        setAddress(address);
        setNewNotes(notes);
    }

    /**Getter for idNumber*/
    public Integer getIdNumber() {
        return idNumber;
    }

    /**Setter for idNumber*/
    public void setIdNumber(Integer idNumber) {
        this.idNumber = Objects.requireNonNull(idNumber);
    }

    /**Getter for lastName*/
    public String getLastName() {
        return lastName;
    }

    /**Setter for lastName*/
    public void setLastName(String lastName) {
        this.lastName = Objects.requireNonNull(lastName);
    }

    /**Getter for firstName*/
    public String getFirstName() {
        return firstName;
    }

    /**Setter for firstName*/
    public void setFirstName(String firstName) {
        this.firstName = Objects.requireNonNull(firstName);
    }

    /**Getter for email*/
    public String getEmail() {
        return email;
    }

    /**Setter for email*/
    public void setEmail(String email) {
        this.email = email;
    }

    /**Getter for primaryPhone*/
    public String getPrimaryPhone() {
        return primaryPhone;
    }

    /**Setter for primaryPhone*/
    public void setPrimaryPhone(String primaryPhone) {
        if (primaryPhone.length() != 10) {
            throw new IllegalArgumentException("Phone number must be 10 characters only with no delimiters");
        }
        this.primaryPhone = Objects.requireNonNull(primaryPhone);
    }

    /**Getter for secondaryPhone*/
    public String getSecondaryPhone() {
        return secondaryPhone;
    }

    /**Setter for secondaryPhone*/
    public void setSecondaryPhone(String secondaryPhone) {
        if (secondaryPhone == null) {
            this.secondaryPhone = null;
            return;
        }
        if (secondaryPhone.length() != 10) {
            throw new IllegalArgumentException("Phone number must be 10 characters only with no delimiters");
        }
        this.secondaryPhone = secondaryPhone;
    }

    /**Getter for address*/
    public Address getAddress() {
        return address;
    }

    /**Setter for address*/
    public void setAddress(Address address) {
        this.address = Objects.requireNonNull(address);
    }

    /**Getter for notes*/
    public String getNotes() {
        return notes;
    }

    /**
     * Method for adding new notes to a Business.
     * In general, existing notes should only ever be appended to, not overwritten.
     * Prefer this method of setNewNotes().
     * @param note The note we wish to append to the Business.
     */
    public void appendNote(String note) {
        if (this.notes != null) {
            this.notes += "\n" + note;
        }
        else {
            this.notes = note;
        }
    }

    /**
     * Method for clearing and overwriting existing Person notes. This method
     * should not be used except in the case that the notes need to be completely
     * overwritten. In general, notes should be appended to using the appendNotes()
     * method to maintain a constant record of any notes.
     * @param notes The new notes we wish to set for the Person. Ideally, an
     *              entire note field should not completely overwritten. We should
     *              take the current notes from the system, remove what we need,
     *              and then use this method to set the notes to the updated information.
     */
    public void setNewNotes(String notes) {
        this.notes = notes;
    }
}
