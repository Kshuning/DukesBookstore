/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administration;

import data_models.business_entities.Supplier;
import data_models.locations.Address;
import data_models.people.Employee;
import data_models.products.Part;

import java.util.ArrayList;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Shuning
 */
@SessionScoped
@ManagedBean(name = "administrations")
public class Administration {

    protected ArrayList<Employee> employee = new ArrayList<>();
    protected int id;
    protected int employeeID;
    private String lastName;
    private String firstName;
    private String email;
    private String primaryPhone;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    private String secondaryPhone;
    private String notes;
    private String title;

    private String action; // action for button
    private String status; // text to show
    private int businessID;
    private String companyName;
    private String contactName; 
    private String website;

    public int getBusinessID() {
        return businessID;
    }

    public void setBusinessID(int businessID) {
        this.businessID = businessID;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }
                                                 

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPrimaryPhone() {
        return primaryPhone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrimaryPhone(String primaryPhone) {
        this.primaryPhone = primaryPhone;
    }

    public String getSecondaryPhone() {
        return secondaryPhone;
    }

    public void setSecondaryPhone(String secondaryPhone) {
        this.secondaryPhone = secondaryPhone;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    protected ArrayList<Supplier> supplier= new ArrayList<Supplier>();

    public ArrayList<Supplier> getSupplier() {
        return supplier;
    }

    public void setSupplier(ArrayList<Supplier> supplier) {
        this.supplier = supplier;
    }

    public ArrayList<Part> getParts() {
        return parts;
    }

    public void setParts(ArrayList<Part> parts) {
        this.parts = parts;
    }
    protected ArrayList<Part> parts= new ArrayList<Part>();
    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Creates a new instance of Administration
     */
    public Administration() {
        Address a = null;
        try {
            a = new Address("123 Sesame Street", "New York", "NY", "96035");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        Employee e = null;
        try {
            e = new Employee(789654, "Smith", "Chedler", "SChedler@email.com", "1234567890", "1234567890", a, "", "Shipping Clark");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        employee.add(e);

        try {
            e = new Employee(456789, "Clark", "Jason", "CJason@email.com", "1234567890", "1234567890", a, "", "Manager");
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        employee.add(e);

    }

    public ArrayList<Employee> getEmployee() {
        return employee;
    }

    public void setEmployee(ArrayList<Employee> employee) {
        this.employee = employee;
    }

//    public ArrayList<Supplier> getSupplier() {
//        return supplier;
//    }
//
//    public void setSupplier(ArrayList<Supplier> supplier) {
//        this.supplier = supplier;
//    }
//
//    public ArrayList<Part> getParts() {
//        return parts;
//    }
//
//    public void setParts(ArrayList<Part> parts) {
//        this.parts = parts;
//    }
    
    /*
        Does all the actions when a button is clicked
    */
    public String processSubmit() {
        String link = "";
        status = "";
        Address a = null;
        
        // there is one variable action which defines which action should the button do. Changing an employee or adding or deleting
        switch (action) {
            case "deleteEmployee":
                link = "employeeDeleted";
                employee.remove(id);
                break;
            case "deleteVendor":
                link = "vendorDeleted";
                supplier.remove(id);
                break;
            case "changeEmployee":
                link = "employeeChanged";
                
                // This one has try and catch because the telephone number, state and zip has to follow certain standard
                try {
                    employee.get(id).setEmail(email);
                    employee.get(id).setFirstName(firstName);
                    employee.get(id).setLastName(lastName);
                    try {
                        employee.get(id).setPrimaryPhone(primaryPhone);
                        employee.get(id).setSecondaryPhone(secondaryPhone);
                    } catch (Exception ex) {
                        status = "<p style=\"color:red\">" + ex.getMessage() + "</p>";
                        link = "";
                    }
                    employee.get(id).setTitle(title);
                    employee.get(id).setNewNotes(notes);
                    try {
                        a = new Address(street, city, state, zipCode);
                    } catch (Exception ex) {
                        status = "<p style=\"color:red\">" + ex.getMessage() + "</p>";
                        link = "";
                    }
                    employee.get(id).setAddress(a);
                } catch (Exception ex) {
                    status = "<p style=\"color:red\">" + ex.getMessage() + "</p>";
                    link = "";
                }

                break;
                case "changeVendor":
                link = "vendorChanged";
                
                // This one has try and catch because the telephone number, state and zip has to follow certain standard
                try {
                    supplier.get(id).setWebsite(website);
                    supplier.get(id).setCompanyName(companyName);
                    supplier.get(id).setContactName(contactName);
                    try {
                        supplier.get(id).setPrimaryPhone(primaryPhone);
                        supplier.get(id).setSecondaryPhone(secondaryPhone);
                    } catch (Exception ex) {
                        status = "<p style=\"color:red\">" + ex.getMessage() + "</p>";
                        link = "";
                    }                   
                    supplier.get(id).setNewNotes(notes);
                    try {
                        a = new Address(street, city, state, zipCode);
                    } catch (Exception ex) {
                        status = "<p style=\"color:red\">" + ex.getMessage() + "</p>";
                        link = "";
                    }
                    supplier.get(id).setAddress(a);
                } catch (Exception ex) {
                    status = "<p style=\"color:red\">" + ex.getMessage() + "</p>";
                    link = "";
                }

                break;
            case "addEmployee":
                link = "employeeAdded";
                status = "";
                try {
                    a = new Address(street, city, state, zipCode);
                } catch (Exception ex) {
                    status = "<p style=\"color:red\">" + ex.getMessage() + "</p>";
                    link = "";
                }
                try {
                    employee.add(new Employee(employeeID, lastName, firstName, email, primaryPhone, secondaryPhone, a, notes, title));
                } catch (Exception ex) {
                    status = "<p style=\"color:red\">" + ex.getMessage() + "</p>";
                    link = "";
                }

                break;
            case "addVendor":
                link = "vendorAdded";
                status = "";
                try {
                    a = new Address(street, city, state, zipCode);
                } catch (Exception ex) {
                    status = "<p style=\"color:red\">" + ex.getMessage() + "</p>";
                    link = "";
                }
                try {
                    supplier.add(new Supplier (businessID, companyName, contactName, primaryPhone, secondaryPhone,website, a, notes));
                } catch (Exception ex) {
                    status = "<p style=\"color:red\">" + ex.getMessage() + "</p>";
                    link = "";
                }

                break;
        }
        
        /* Change the status if adding employee with invalid state or zip code */

        if (action.equals("addEmployee") || action.equals("changeEmployee") || action.equals("addVendor") || action.equals("deleteVendor")) {

            // Error handling
            if (state.length() != 2) {
                status = "<p style=\"color:red\">States can only be 2 characters</p>";
            }

            if (zipCode.length() != 10 && zipCode.length() != 5) {
                status = "<p style=\"color:red\">Zip code must be only 5 or 10 characters.</p>";
            }
            
        }
        return link;
    }

    public void resetForm() {
        email = "";
        firstName = "";
        lastName = "";
        primaryPhone = "";
        secondaryPhone = "";
        title = "";
        notes = "";
        street = "";
        city = "";
        state = "";
        zipCode = "";
        employeeID = 0;
    }

  
    // transform char[] to String
    public String getEmployeeStateAsString(int employeeId) {
        String s = String.valueOf(employee.get(employeeId).getAddress().getState());
        state = s;
        return s;
    }
    
    // transform char[] to String
    public String getEmployeeZipAsString(int employeeId) {
        String s = String.valueOf(employee.get(employeeId).getAddress().getZipCode());
        zipCode = s;
        return s;
    }
     public String getSupplierStateAsString(int businessID) {
        String s = String.valueOf(supplier.get(businessID).getAddress().getState());
        state = s;
        return s;
    }
    
    // transform char[] to String
    public String getSupplierZipAsString(int businessID) {
        String s = String.valueOf(supplier.get(businessID).getAddress().getZipCode());
        zipCode = s;
        return s;
    }
}
