package data_models.orders;

import data_models.people.Person;
import data_models.products.Part;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Order is an abstract class used for the basis of various other types of Orders.
 *
 * Any class extending this should implement factory methods for instantiating
 * objects rather than allow access to the constructors.
 */
public abstract class Order {

    public static enum Status {NEW, PROCESSED, APPROVED, IN_TRANSIT, SHIPPED, DELIVERED, RECEIVED, ON_BACK_ORDER}

    private Integer orderID;
    private Person initiatingPerson;
    private Map<Part, Integer> orderedParts = new HashMap<>();
    private Date orderCreationDate;
    private Date shippingDate;
    private BigDecimal shippingFee;
    private BigDecimal taxAmount;
    private BigDecimal totalOrderAmount; // includes shipping, taxes, etc
    private Status orderStatus;
    private String notes;

    /**
     * This constructor should be used to construct a new Order object by the
     * inheriting class. The class should also typically have a private constructor
     * and provide factory methods for creating objects that extend Order.
     * @param orderID The ID number of the order stored in the database.
     * @param initiatingPerson The Person who created the order
     *                         (ie a Customer or Employee).
     * @param orderedParts A map of parts ordered with the Key being a part and
     *                     the value being the number of those parts ordered.
     * @param orderCreationDate The date an order was created.
     * @param shippingDate The date an order was shipped.
     * @param shippingFee The total shipping fee for an order.
     * @param taxAmount The total tax amount for the order.
     * @param totalOrderAmount The total amount of the order including the parts,
     *                         shipping fee, and tax amount. This should be an
     *                         all encompassing amount.
     * @param orderStatus The current status of the order.
     * @param notes Any notes about the order that should be stored.
     */
    protected Order(Integer orderID,
                    Person initiatingPerson,
                    Map<Part, Integer> orderedParts,
                    Date orderCreationDate,
                    Date shippingDate,
                    BigDecimal shippingFee,
                    BigDecimal taxAmount,
                    BigDecimal totalOrderAmount,
                    Status orderStatus,
                    String notes) {
        setOrderID(orderID);
        setInitiatingPersonID(initiatingPerson);
        setOrderedParts(orderedParts);
        setOrderCreationDate(orderCreationDate);
        setShippingDate(shippingDate);
        setShippingFee(shippingFee);
        setTaxAmount(taxAmount);
        setTotalOrderAmount(totalOrderAmount);
        setOrderStatus(orderStatus);
        setNewNotes(notes);
    }

    /**Getter for orderID*/
    public Integer getOrderID() {
        return orderID;
    }

    /**Setter for orderID*/
    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    /**Getter for initiatingPerson*/
    public Person getInitiatingPerson() {
        return initiatingPerson;
    }

    /**Setter for initiatingPerson*/
    public void setInitiatingPersonID(Person initiatingPerson) {
        this.initiatingPerson = initiatingPerson;
    }

    /**Getter for orderedParts*/
    public Map<Part, Integer> getOrderedParts() {
        return orderedParts;
    }

    /**Setter for orderedParts*/
    public void setOrderedParts(Map<Part, Integer> orderedParts) {
        this.orderedParts = orderedParts;
    }

    /**Getter for orderCreationDate*/
    public Date getOrderCreationDate() {
        return orderCreationDate;
    }

    /**Setter for orderCreationDate*/
    public void setOrderCreationDate(Date orderCreationDate) {
        this.orderCreationDate = orderCreationDate;
    }

    /**Getter for shippingDate*/
    public Date getShippingDate() {
        return shippingDate;
    }

    /**Getter for shippingDate*/
    public void setShippingDate(Date shippingDate) {
        this.shippingDate = shippingDate;
    }

    /**Getter for shippingFee*/
    public BigDecimal getShippingFee() {
        return shippingFee;
    }

    /**Setter for shippingFee*/
    public void setShippingFee(BigDecimal shippingFee) {
        this.shippingFee = shippingFee;
    }

    /**Getter for taxAmount*/
    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    /**Setter for taxAmount*/
    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    /**Getter for totalOrderAmount*/
    public BigDecimal getTotalOrderAmount() {
        return totalOrderAmount;
    }

    /**Setter for totalOrderAmount*/
    public void setTotalOrderAmount(BigDecimal totalOrderAmount) {
        this.totalOrderAmount = totalOrderAmount;
    }

    /**Getter for orderStatus*/
    public Status getOrderStatus() {
        return orderStatus;
    }

    /**Setter for orderStatus*/
    public void setOrderStatus(Status orderStatus) {
        this.orderStatus = orderStatus;
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
     * Method for clearing and overwriting existing Order notes. This method
     * should not be used except in the case that the notes need to be completely
     * overwritten. In general, notes should be appended to using the appendNotes()
     * method to maintain a constant record of any notes.
     * @param notes The new notes we wish to set for the Order. Ideally, an
     *              entire note field should not completely overwritten. We should
     *              take the current notes from the system, remove what we need,
     *              and then use this method to set the notes to the updated information.
     */
    public void setNewNotes(String notes) {
        this.notes = notes;
    }
}
