package data_models.business_entities;

import data_models.locations.Address;

/**
 * ShippingProvider encapsulates a Business that provides shipping services for
 * our products.
 *
 * All objects should be instantiated using the static factory methods provided
 * (createNew and createExisting).
 */
public class ShippingProvider extends Business {

    /**
     * Private constructor.
     *
     * All objects should be created using the factory methods createNew and
     * createExisting.
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
    private ShippingProvider(Integer businessID, String companyName, String contactName,
                             String primaryPhone, String secondaryPhone, String website,
                             Address address, String notes) {
        super(businessID, companyName, contactName, primaryPhone, secondaryPhone,
            website, address, notes);
    }

    /**
     * Factory method for creating a new Business that is to be added to the database.
     *
     * This sets each possible field for a Business to an empty string for fields
     * that are able to be NULL in the database.
     *
     * Prefer using the fully parameterized version of this method to save all
     * relevant data to the database.
     *
     * @param companyName The Business company name.
     * @param primaryPhone The primary phone number for contacting the Business.
     * @param address A complete Address object containing the Business's primary
     *                address information.
     * @return a new Business object with empty fields to be saved to the database.
     */
    public static ShippingProvider createNew(String companyName, String primaryPhone,
                                             Address address) {
        return createNew(companyName, "",
            primaryPhone, "", "", address, "");
    }

    /**
     * Factory method for creating a new Business that is to be added to the database.
     *
     * Prefer using this method over the overloaded method requiring less parameters
     * so that all relevant data can be saved to the database.
     *
     * @param companyName The Business company name.
     * @param contactName The contact person within the company.
     * @param primaryPhone The primary phone number for contacting the Business.
     * @param secondaryPhone A secondary contact phone number.
     * @param website The fully qualified URL for the Business's home website.
     * @param address A complete Address object containing the Business's primary
     *                address information.
     * @param notes Any notes that need to be recorded regarding the Business.
     * @return a new Business object to be saved to the database.
     */
    public static ShippingProvider createNew(String companyName, String contactName,
                                             String primaryPhone, String secondaryPhone,
                                             String website, Address address, String notes) {
        return new ShippingProvider(-1, companyName, contactName, primaryPhone,
            secondaryPhone, website, address, notes);
    }

    /**
     * Factory method for instantiating a Business from existing data (typically
     * the database).
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
     * @return an existing Business from established data (typically the database).
     */

    public static ShippingProvider createExisting(Integer businessID, String companyName,
                                          String contactName, String primaryPhone,
                                          String secondaryPhone, String website,
                                          Address address, String notes) {
        return new ShippingProvider(businessID, companyName, contactName, primaryPhone,
            secondaryPhone, website, address, notes);
    }
}
