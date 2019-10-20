package data_models.business_entities;

import data_models.locations.Address;

import java.util.Objects;

/**
 * Business represents a Business outside of our operation that provide services
 * or products (ie Suppliers and Shippers).
 *
 * This is used as a base class for Business entities and any class extending
 * this should provide factory methods for instantiating new objects rather than
 * use a constructor.
 *
 * Member variables for a Business are non-null if required non-null when saving
 * to the database.
 */
public abstract class Business {
    private Integer businessID;
    private String companyName;
    private String contactName;
    private String primaryPhone;
    private String secondaryPhone;
    private String website;
    private Address address;
    private String notes;

    /**
     * Protected constructor should be overridden as private.
     *
     * Inheriting classes should create factory methods for instantiating new
     * Business objects.
     *
     * @param businessID The Business ID saved by the database. This should not
     *                   be provided programmatically by anything other than the
     *                   database controller. Any new Business to be added to the
     *                   database should be instantiated with a value of -1 to
     *                   flag the controller to this action.
     * @param companyName The Business company name.
     * @param contactName The contact person within the company.
     * @param primaryPhone The primary phone number for contacting the Business.
     * @param secondaryPhone A secondary contact phone number.
     * @param website The fully qualified URL for the Business's home website.
     * @param address A complete Address object containing the Business's primary
     *                address information.
     * @param notes Any notes that need to be recorded regarding the Business.
     */
    protected Business(Integer businessID, String companyName, String contactName,
                     String primaryPhone, String secondaryPhone, String website,
                     Address address, String notes) {
        setBusinessID(businessID);
        setCompanyName(companyName);
        setPrimaryPhone(primaryPhone);
        setAddress(address);
        this.contactName = contactName;
        this.secondaryPhone = secondaryPhone;
        this.website = website;
        this.notes = notes;
    }

    /**Getter for BusinessID*/
    public Integer getBusinessID() {
        return businessID;
    }

    /**
     * Setter for Business ID. Because this field is set during instantiation,
     * it should not be used outside of this class without a good reason. Data
     * could be corrupted in the database if saving over an existing ID or there
     * may be duplication.
     *
     * The datatype is a boxed Integer rather than an unboxed int because primitive
     * ints are initialized as 0. With 0 being a valid ID number, we need to make
     * sure the value is not null, not that it is not 0.
     * @param BusinessID the value to set for the Business ID. Values of -1 indicate
     *                   to the database controller that this is a new Business
     *                   to add to the database.
     */
    public void setBusinessID(Integer BusinessID) {
        this.businessID = Objects.requireNonNull(BusinessID);
    }

    /**Getter for companyName*/
    public String getCompanyName() {
        return companyName;
    }

    /**Setter for companyName, non-null required*/
    public void setCompanyName(String companyName) {
        this.companyName = Objects.requireNonNull(companyName);
    }

    /**Getter for contactName*/
    public String getContactName() {
        return contactName;
    }

    /**Setter for contactName*/
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    /**Getter for primaryPhone*/
    public String getPrimaryPhone() {
        return primaryPhone;
    }
    /**Setter for primaryPhone, non-null required*/
    public void setPrimaryPhone(String primaryPhone) {
        this.primaryPhone = Objects.requireNonNull(primaryPhone);
    }

    /**Getter for secondaryPhone*/
    public String getSecondaryPhone() {
        return secondaryPhone;
    }

    /**Setter for secondaryPhone*/
    public void setSecondaryPhone(String secondaryPhone) {
        this.secondaryPhone = secondaryPhone;
    }

    /**Getter for website*/
    public String getWebsite() {
        return website;
    }

    /**Setter for website*/
    public void setWebsite(String website) {
        this.website = website;
    }

    /**Getter for address*/
    public Address getAddress() {
        return address;
    }

    /**Setter for address, non-null required*/
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
     * Method for clearing and overwriting existing Business notes. This method
     * should not be used except in the case that the notes need to be completely
     * overwritten. In general, notes should be appended to using the appendNotes()
     * method to maintain a constant record of any notes.
     * @param notes The new notes we wish to set for the Business. Ideally, an
     *              entire note field should not completely overwritten. We should
     *              take the current notes from the system, remove what we need,
     *              and then use this method to set the notes to the updated information.
     */
    public void setNewNotes(String notes) {
        this.notes = notes;
    }
}
