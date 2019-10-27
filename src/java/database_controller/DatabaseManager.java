package database_controller;

import data_models.locations.Address;
import data_models.people.Customer;
import data_models.people.Employee;
import data_models.people.Person;
import exceptions.CustomerNotFoundException;
import java.io.Serializable;

import java.sql.*;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class DatabaseManager implements Serializable{

    public Connection connection;

    public DatabaseManager() {
        try {
            connectToDatabase();
        } catch (ClassNotFoundException ex) {
            System.err.println("Driver error");
            ex.printStackTrace();
        } catch (SQLException ex) {
            System.err.println("Server error");
            ex.printStackTrace();
        }
    }

    /**
     * Creates a connection to the database. Currently works on a localhost
     * only. TODO Make sure to change this when ready to deploy to the live
     * database.
     *
     * @throws ClassNotFoundException If there is an issue with initiating the
     * driver.
     * @throws SQLException If a connection cannot be established with the
     * database.
     */
    /*private void connectToDatabase() throws ClassNotFoundException, SQLException {
        //Class.forName("com.mysql.cj.jdbc.Driver");
        Class.forName("com.mysql.jdbc.Driver");
        String serverName = "jdbc:mysql://localhost";
        connection = DriverManager.getConnection(serverName, "testuser", "javaiii");//fixme
        System.out.println("Connected");
    }*/
    private void connectToDatabase() throws ClassNotFoundException, SQLException {
        connection = null;
        Class.forName("com.mysql.cj.jdbc.Driver");
        String serverName = "jdbc:mysql://scgrk-db-test.crd1qvhssojx.us-east-1.rds.amazonaws.com";
        try {
            connectToLocalHostDatabase();
            //connection = DriverManager.getConnection(serverName, "testuser", "javaiii");
            System.out.println("jdbc:mysql://scgrk-db-test.crd1qvhssojx.us-east-1.rds.amazonaws.com");
        } catch (SQLException ex) {
            // if cannot connect to remote database, connect to local host
            // fixme remove for production
            try {
                System.err.println("Could not connect to RDBS. Attempting connection"
                        + " to localhost server...");
                connectToLocalHostDatabase();
            } catch (SQLException ex2) {
                System.err.println("Could not establish connection to localhost"
                        + " server. Check that it is running.");
                ex2.printStackTrace();
                throw ex2;
            }

        }

        System.out.println("Connected");
    }

    /**
     * Forces a connection to a local host instantiation of the database.
     *
     * @throws ClassNotFoundException If there is an issue with initiating the
     * driver.
     * @throws SQLException If a connection cannot be established with the
     * database.
     */
    private void connectToLocalHostDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String serverName = "jdbc:mysql://localhost";
        connection = DriverManager.getConnection(serverName, "testuser", "javaiii");
        
        
        
    }

    public ArrayList<Employee> getEmployees() throws SQLException, NullPointerException {
        ArrayList<Employee> employeeReturn = new ArrayList<>();
        String sql = "SELECT EmployeeID, LastName, FirstName, Title, Email, PrimaryPhone,"
                + "SecondaryPhone, Address, City, State, ZipCode,"
                + "Notes FROM AutoPartsStore.Employees ;";
        
        

        
        PreparedStatement pStmt = connection.prepareStatement(sql);
        //pStmt.setInt(1, id);
        ResultSet rs = pStmt.executeQuery();

        while (rs.isAfterLast() == false) {
            try {
                rs.next();
            } catch (Exception ex) {
                //     System.out.println(ex.getMessage());
            }
            if (rs.isAfterLast() == false) {
                //   System.out.println(rs.isAfterLast());
                int i = 1;
                int eid = Integer.parseInt(rs.getString(i++));
                String lastName = rs.getString(i++);
                String firstName = rs.getString(i++);
                String title = rs.getString(i++);
                String email = rs.getString(i++);
                i++;
                i++;
                //String primaryPhone = rs.getString(i++);
                //String secondaryPhone = rs.getString(i++);
                String primaryPhone = "0000000000";
                String secondaryPhone = "0000000000";

                String Street = rs.getString(i++);
                String City = rs.getString(i++);
                String State = rs.getString(i++);
                String Zip = rs.getString(i++);
                Address address = null;
                try {
                    address = new Address(Street, City, State, Zip);
                } catch (Exception ex) {
                    //      System.out.println(ex.getMessage());
                }

                String notes = rs.getString(i++);
                //  System.out.println(eid + " " + firstName + " " + lastName + " " + email + " " + primaryPhone + " " + secondaryPhone + " " + notes + " " + title);
                Employee employee=null;
                try {
                    employee = new Employee(eid, firstName, lastName, email, primaryPhone, secondaryPhone, address, notes, title);
                } catch (Exception ex) {
                    //      System.out.println(ex.getMessage());
                }
                try {
                    employeeReturn.add(employee);
                } catch (Exception ex) {
                    //      System.out.println(ex.getMessage());
                }
            }
            //employee.add(Employee.createExisting(eid, lastName, firstName, email, primaryPhone, secondaryPhone, address, notes, title));
        }

        return employeeReturn;

    }

    /**
     * Writes a customer to the database. If it is a new customer, it will be
     * inserted. Otherwise, it will be updated.
     *
     * @param customer The customer we wish to write to the database.
     * @return True if the statement executed with success.
     * @throws SQLException If there is a problem creating a connection with the
     * database.
     */
    public boolean write(Customer customer) throws SQLException {
        if (customer.getIdNumber() < 0) {
            return insert(customer);
        } else {
            return update(customer);
        }
    }

    /**
     * Gets a Customer from the database from an ID number.
     *
     * @param id The Customer ID number.
     * @return A Customer object from the database data.
     * @throws SQLException If there is a problem creating a connection with the
     * database.
     */
    public Customer getCustomerByID(Integer id) throws SQLException {
        String sql = "SELECT LastName, FirstName, Email, PrimaryPhone,"
                + "SecondaryPhone, Address, City, State, ZipCode,"
                + "Notes FROM AutoPartsStore.Customers WHERE CustomerID = ?";
        PreparedStatement pStmt = connection.prepareStatement(sql);
        pStmt.setInt(1, id);
        ResultSet rs = pStmt.executeQuery();
        rs.next();
        int i = 1;
        String lastName = rs.getString(i++);
        String firstName = rs.getString(i++);
        String email = rs.getString(i++);
        String primaryPhone = rs.getString(i++);
        String secondaryPhone = rs.getString(i++);
        Address address = new Address(
                rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getString(i++)
        );
        String notes = rs.getString(i++);
        return Customer.createExisting(id, lastName, firstName, email, primaryPhone,
                secondaryPhone, address, notes);
    }

    /**
     * Gets a Customer from the database from an email address.
     *
     * @param email The Customer's email address.
     * @return A Customer object from the database data.
     * @throws SQLException If there is a problem creating a connection with the
     * database or the email address was not found in the database.
     */
    public Customer getCustomerByEmail(String email) throws SQLException {
        String sql = "SELECT CustomerID FROM AutoPartsStore.Customers WHERE Email = ?;";
        PreparedStatement pStmt = connection.prepareStatement(sql);
        pStmt.setString(1, email);
        ResultSet rs = pStmt.executeQuery();
        rs.last();
        if (rs.getRow() < 1) {
            throw new CustomerNotFoundException("Email '" + email + "' not found.");
        }
        return getCustomerByID(rs.getInt(1));
    }

    /**
     * Inserts a new customer into the database.
     *
     * @param customer The customer we wish to insert.
     * @return True if the statement executed with success.
     * @throws SQLException If there is a problem creating a connection with the
     * database.
     */
    private boolean insert(Customer customer) throws SQLException {
        String sql = "INSERT INTO AutoPartsStore.Customers"
                + "(LastName, FirstName, Email, PrimaryPhone,"
                + "SecondaryPhone, Address, City, State, ZipCode, Notes)"
                + "VALUES"
                + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement pStmt = connection.prepareStatement(sql);
        prepareStatement(customer, pStmt);
        return pStmt.executeUpdate() >= 1;
    }

    /**
     * Updates an existing customer record in the database.
     *
     * @param customer The customer we wish to update in the database.
     * @return True if the statement executed with success.
     * @throws SQLException If there is a problem creating a connection with the
     * database.
     */
    private boolean update(Customer customer) throws SQLException {
        String sql = "UPDATE AutoPartsStore.Customers"
                + " SET LastName = ?, FirstName = ?, Email = ?, PrimaryPhone = ?,"
                + " SecondaryPhone = ?, Address = ?, City = ?, State = ?,"
                + " ZipCode = ?, Notes = ?"
                + " WHERE CustomerID = ?;";
        PreparedStatement pStmt = connection.prepareStatement(sql);
        prepareStatement(customer, pStmt);
        pStmt.setInt(11, customer.getIdNumber());
        return pStmt.executeUpdate() >= 1;
    }

    /**
     * Prepares a statement with Person information.
     *
     * @param person The person we are going to prepare a statement with.
     * @param pStmt The PreparedStatement object we wish to prepare.
     * @throws SQLException If there is a problem creating a connection with the
     * database.
     */
    private void prepareStatement(Person person, PreparedStatement pStmt) throws SQLException {
        int i = 1;
        pStmt.setString(i++, person.getLastName());
        pStmt.setString(i++, person.getFirstName());
        pStmt.setString(i++, person.getEmail());
        pStmt.setString(i++, person.getPrimaryPhone());
        pStmt.setString(i++, person.getSecondaryPhone());
        pStmt.setString(i++, person.getAddress().getStreet());
        pStmt.setString(i++, person.getAddress().getCity());
        pStmt.setString(i++, String.valueOf(person.getAddress().getState()));
        pStmt.setString(i++, String.valueOf(person.getAddress().getZipCode()));
        pStmt.setString(i++, person.getNotes());
    }
    
    public void closeConnection() throws SQLException{
        connection.close();
    }

}
