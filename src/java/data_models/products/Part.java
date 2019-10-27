package data_models.products;

import data_models.business_entities.Business;

import java.math.BigDecimal;
import java.util.Objects;



/**
 * Part represents a Part that is sold by our store.
 *
 * Member variables for a supplier are non-null if required non-null when saving
 * to the database.
 *
 * Any new part that is to be added to the database should be created createNew
 * factory method.
 */
public class Part {
    private Integer partID;
    private Business supplier;
    private Car car;
    private String name;
    private String description;
    private String category;
    private BigDecimal pricePerUnit;
    private String quantityPerUnit;
    private boolean discontinued;

    /**
     * For constructing a Part object. Can only be used by calling the static
     * factory methods createNew or createExisting.
     *
     * @param partID The part ID saved by the database. This should not be
     *               provided programmatically by anything other than the database
     *               controller. Any new Part to be added to the database should
     *               be instantiated using the createNew method.
     * @param business The supplier who provides this item to our company.
     *                 Non-null required.
     * @param car The car or car configuration that this part can belong to.
     *            Non-null required.
     * @param name The name of the part.
     *             Non-null required.
     * @param description A brief description of what the part is.
     * @param category The category the part belongs to.
     * @param pricePerUnit The price for each unit of a part.
     *                     Non-null required.
     * @param quantityPerUnit The number of parts to a quantity per an order.
     *                        Non-null required. If there is only 1 part per
     *                        order unit, this should be explicit.
     * @param discontinued A flag indicating this part is no longer sold by the
     *                     company.
     */
    public Part(Integer partID, Business business, Car car, String name,
                 String description, String category, BigDecimal pricePerUnit,
                 String quantityPerUnit,
                 boolean discontinued) {
        setPartID(partID);
        setSupplier(business);
        setCar(car);
        setName(name);
        this.description = description;
        this.category = category;
        setPricePerUnit(pricePerUnit);
        setQuantityPerUnit(quantityPerUnit);
        this.discontinued = discontinued;
    }
    /**
     * Factory method for creating a new Part item to add to the database.
     * Automatically sets the partID to -1 and discontinued to false
     * (brand new parts cannot be discontinued).
     *
     * @param business The supplier who provides this item to our company.
     *                 Non-null required.
     * @param car The car or car configuration that this part can belong to.
     *            Non-null required.
     * @param name The name of the part.
     *             Non-null required.
     * @param description A brief description of what the part is.
     * @param category The category the part belongs to.
     * @param pricePerUnit The price for each unit of a part.
     *                     Non-null required.
     * @param quantityPerUnit The number of parts to a quantity per an order.
     *                        Non-null required. If there is only 1 part per
     *                        order unit, this should be explicit.
     * @return A new Part object that is to be written to the database.
     */
    public static Part createNew(Business business, Car car, String name, String description,
                                 String category, BigDecimal pricePerUnit,
                                 String quantityPerUnit) {
        return new Part(-1, business, car, name, description, category,
            pricePerUnit, quantityPerUnit, false);
    }

    /**
     * Factory method for creating existing Part objects from the database or for
     * parts that have an existing Part ID number but are not saved in the
     * database already.
     *
     * Constructs a new Part object from the given information and returns it.
     *
     * @param partID The part ID saved by the database. This should not be
     *               provided programmatically by anything other than the database
     *               controller. Any new Part to be added to the database should
     *               be instantiated using the createNew method.
     * @param business The supplier who provides this item to our company.
     *                 Non-null required.
     * @param car The car or car configuration that this part can belong to.
     *            Non-null required.
     * @param name The name of the part.
     *             Non-null required.
     * @param description A brief description of what the part is.
     * @param category The category the part belongs to.
     * @param pricePerUnit The price for each unit of a part.
     *                     Non-null required.
     * @param quantityPerUnit The number of parts to a quantity per an order.
     *                        Non-null required. If there is only 1 part per
     *                        order unit, this should be explicit.
     * @param discontinued A flag indicating this part is no longer sold by the
     *                     company.
     * @return An existing Part (typically from the database) object.
     */
    public static Part createExisting(Integer partID, Business business, Car car,
                                      String name, String description, String category,
                                      BigDecimal pricePerUnit, String quantityPerUnit,
                                      boolean discontinued) {
        return new Part(partID, business, car, name, description, category,
            pricePerUnit, quantityPerUnit, discontinued);
    }

    /**Getter for partID*/
    public int getPartID() {
        return partID;
    }

    /**
     * Setter for partID, requires non-null.
     * This method should only be used with a very good rationale as it can cause
     * potential problems with storing information in the database.
     */
    public void setPartID(Integer partID) {
        this.partID = Objects.requireNonNull(partID);
    }

    public Business getSupplier() {
        return supplier;
    }

    /**Setter for supplier, requires non-null*/
    public void setSupplier(Business business) {
        this.supplier = Objects.requireNonNull(business);
    }

    /**Getter for car*/
    public Car getCar() {
        return car;
    }

    /**Setter for car, requires non-null*/
    public void setCar(Car car) {
        this.car = Objects.requireNonNull(car);
    }

    /**Getter for name*/
    public String getName() {
        return name;
    }

    /**Setter for name, requires non-null*/
    public void setName(String name) {
        this.name = Objects.requireNonNull(name);
    }

    /**Getter for description*/
    public String getDescription() {
        return description;
    }

    /**Setter for quantityPerUnit, requires non-null*/
    public void setDescription(String description) {
        this.description = description;
    }

    /**Getter for category*/
    public String getCategory() {
        return category;
    }

    /**Setter for category*/
    public void setCategory(String category) {
        this.category = category;
    }

    /**Getter for pricePerUnit*/
    public BigDecimal getPricePerUnit() {
        return pricePerUnit;
    }

    /**Setter for pricePerUnit, requires non-null*/
    public void setPricePerUnit(BigDecimal pricePerUnit) {
        this.pricePerUnit = Objects.requireNonNull(pricePerUnit);
    }

    /**Getter for quantityPerUnit*/
    public String getQuantityPerUnit() {
        return quantityPerUnit;
    }

    /**Setter for quantityPerUnit, requires non-null*/
    public void setQuantityPerUnit(String quantityPerUnit) {
        this.quantityPerUnit = Objects.requireNonNull(quantityPerUnit);
    }

    /**Getter for discontinued status*/
    public boolean isDiscontinued() {
        return discontinued;
    }

    /**Setter for discontinued status*/
    public void setDiscontinued(boolean discontinued) {
        this.discontinued = discontinued;
    }
}
