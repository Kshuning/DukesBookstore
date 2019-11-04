package database_controller;

import com.mysql.cj.jdbc.exceptions.MysqlDataTruncation;
import data_models.business_entities.ShippingProvider;
import data_models.business_entities.Supplier;
import data_models.locations.Address;
import data_models.locations.ShippingAddress;
import data_models.orders.CustomerOrder;
import data_models.orders.Order;
import data_models.orders.ResupplyOrder;
import data_models.people.Customer;
import data_models.people.Employee;
import data_models.products.Car;
import data_models.products.Part;
import exceptions.ItemNotFoundException;
import exceptions.PersonNotFoundException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseManager {

    private Connection connection;

    public DatabaseManager() {
        try {
            connectToDatabase();
        }
        catch (ClassNotFoundException ex) {
            System.err.println("Driver error");
            ex.printStackTrace();
        }
        catch (SQLException ex) {
            System.err.println("Server error");
            ex.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * Constructor that can choose between local host database and remote database
     * by simply changing the boolean value passed.
     * @param forceLocal If true, will connect to a local host database.
     */
    public DatabaseManager(boolean forceLocal) {
        try {
            if (forceLocal) {
                connectToLocalHostDatabase();
            }
            else {
                connectToDatabase();
            }
        }
        catch (ClassNotFoundException ex) {
            System.err.println("Driver error");
            ex.printStackTrace();
        }
        catch (SQLException ex) {
            System.err.println("Server error");
            ex.printStackTrace();
        }
    }

    /**
     * Creates a connection to the database. Currently works on a localhost only.
     * TODO Make sure to change this when ready to deploy to the live database.
     * @throws ClassNotFoundException If there is an issue with initiating the driver.
     * @throws SQLException If a connection cannot be established with the database.
     */
    private void connectToDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String serverName = "jdbc:mysql://scgrk-db-test.crd1qvhssojx.us-east-1.rds.amazonaws.com";
        try {
            connection = DriverManager.getConnection(serverName, "testuser", "javaiii");
        }
        catch (SQLException ex) {
            // if cannot connect to remote database, connect to local host
            // fixme remove for production
            try {
                System.err.println("Could not connect to RDBS. Attempting connection" +
                                       " to localhost server...");
                connectToLocalHostDatabase();
            }
            catch (SQLException ex2) {
                System.err.println("Could not establish connection to localhost" +
                                       " server. Check that it is running.");
                ex2.printStackTrace();
                throw ex2;
            }

        }

        System.out.println("Connected");
    }

    /**
     * Forces a connection to a local host instantiation of the database.
     * @throws ClassNotFoundException If there is an issue with initiating the driver.
     * @throws SQLException If a connection cannot be established with the database.
     */
    private void connectToLocalHostDatabase() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String serverName = "jdbc:mysql://localhost";
        connection = DriverManager.getConnection(serverName, "testuser", "javaiii");
    }

    /**
     * Gets a Customer from the database from an ID number.
     * @param id The Customer ID number.
     * @return A Customer object from the database data.
     * @throws SQLException If there is a problem creating a connection with the
     *                      database or the Customer was not found.
     */
    public Customer getCustomerByID(Integer id) throws SQLException {
        String sql = "SELECT LastName, FirstName, Email, PrimaryPhone," +
                         "SecondaryPhone, Address, City, State, ZipCode," +
                         "Notes FROM AutoPartsStore.Customers WHERE CustomerID = ?;";
        PreparedStatement pStmt = connection.prepareStatement(sql);//todo refactor
        pStmt.setInt(1, id);
        ResultSet rs = pStmt.executeQuery();
        rs.next();
        if (rs.getRow() < 1) {
            throw new PersonNotFoundException("ID: " + id + " was not found.");
        }
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
            secondaryPhone,address,notes);
    }

    /**
     * Gets a Customer from the database from an email address.
     * @param email The Customer's email address.
     * @return A Customer object from the database data.
     * @throws SQLException If there is a problem creating a connection with the
     *                      database or the email address was not found in the
     *                      database.
     */
    public Customer getCustomerByEmail(String email) throws SQLException {
        String sql = "SELECT CustomerID FROM AutoPartsStore.Customers WHERE Email = ?;";
        PreparedStatement pStmt = connection.prepareStatement(sql);
        pStmt.setString(1, email);
        ResultSet rs = pStmt.executeQuery();
        rs.last();
        if (rs.getRow() < 1) {
            throw new PersonNotFoundException("Email '" + email + "' not found.");
        }
        return getCustomerByID(rs.getInt(1));
    }

    /**
     * Gets an Employee from the database from an ID number.
     * @param id The Employee ID number.
     * @return An Employee object from the database data.
     * @throws SQLException If there is a problem creating a connection with the
     *                      database or the Employee was not found.
     */
    public Employee getEmployeeByID(Integer id) throws SQLException {
        String sql = "SELECT LastName, FirstName, Title, Email, PrimaryPhone," +
                         "SecondaryPhone, Address, City, State, ZipCode," +
                         "Notes FROM AutoPartsStore.Employees WHERE EmployeeID = ?;";
        PreparedStatement pStmt = connection.prepareStatement(sql);//todo refactor
        pStmt.setInt(1, id);
        ResultSet rs = pStmt.executeQuery();
        rs.next();
        if (rs.getRow() < 1) {
            throw new PersonNotFoundException("ID: " + id + " was not found.");
        }
        int i = 1;
        String lastName = rs.getString(i++);
        String firstName = rs.getString(i++);
        String title = rs.getString(i++);
        String email = rs.getString(i++);
        String primaryPhone = rs.getString(i++);
        String secondaryPhone = rs.getString(i++);
        Address address = new Address(
            rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getString(i++)
        );
        String notes = rs.getString(i++);
        return Employee.createExisting(id, lastName, firstName, email, primaryPhone,
            secondaryPhone, address, notes, title);
    }

    /**
     * Gets an Employee from the database from an email address.
     * @param email The Employee email address.
     * @return An Employee object from the database data.
     * @throws SQLException If there is a problem creating a connection with the
     *                      database or the email address was not found in the
     *                      database.
     */
    public Employee getEmployeeByEmail(String email) throws SQLException {
        String sql = "SELECT EmployeeID FROM AutoPartsStore.Employees WHERE Email = ?;";
        PreparedStatement pStmt = connection.prepareStatement(sql);
        pStmt.setString(1, email);
        ResultSet rs = pStmt.executeQuery();
        rs.last();
        if (rs.getRow() < 1) {
            throw new PersonNotFoundException("Email '" + email + "' not found.");
        }
        return getEmployeeByID(rs.getInt(1));
    }

    /**
     * Writes a database object that implements DatabaseWriteable to the database.
     * Can be used for new or updated information.
     * @param writeableObject An object that has a table representation in the
     *                        database and implements DatabaseWriteable.
     * @return True if the value was written to the database.
     * @throws SQLException Exceptions can be the result of these conditions
     * 1. If a connection cannot be established to the database.
     * 2. A unique constraint is violated for a new record.
     */
    public boolean saveToDatabase(DatabaseWriteable writeableObject) throws SQLException{
        try {
            PreparedStatement preparedStatement = writeableObject.getWriteStatement(connection);
            int rowsUpdated = preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                Integer id = rs.getInt(1);
                writeableObject.setGeneratedId(id);
            }
            return rowsUpdated >= 1;
        }
        catch (MysqlDataTruncation ex) {
            throw new SQLException("Attempt to save to database failed.\n" +
                                   "Please make sure all aggregated database objects are " +
                                   "saved in the database.\n" +
                                   "IE If you try to save a new Part with a new Supplier,\n" +
                                   "The supplier needs to be saved to the database first.");
        }
    }

    public void saveToDatabase(Order order) throws SQLException {
        OrderWriter orderWriter = new OrderWriter(this);
        orderWriter.write(order);
    }

    public CustomerOrder getCustomerOrderByID(Integer orderID) throws SQLException {
        //fixme major refactoring needed here
        // the query in particular can be more specific and not get everything
        String sql = "SELECT * FROM AutoPartsStore.CustomerOrders WHERE OrderID = ?;";
        PreparedStatement pStmt = connection.prepareStatement(sql);
        pStmt.setInt(1, orderID);
        ResultSet rs = pStmt.executeQuery();
        if (rs.next()) {
            Integer orderId = rs.getInt("OrderID");
            Customer customer = getCustomerByID(rs.getInt("CustomerID"));
            Date orderDate = new Date(rs.getTimestamp("OrderDate").getTime());
            Timestamp shipDateTimeStamp = rs.getTimestamp("ShipDate");
            Date shipDate = (shipDateTimeStamp == null) ? null : new Date(shipDateTimeStamp.getTime());
            ShippingAddress shippingAddress = new ShippingAddress(
                rs.getString("ShipName"), // shipName
                new Address(
                    rs.getString("ShipAddress"), // street
                    rs.getString("ShipCity"), // city
                    rs.getString("ShipState"), // state
                    rs.getString("ShipZipCode")) // zip
            );
            ShippingProvider shippingProvider = retrieveShippingProviderByID(rs.getInt("ShipperID"));
            BigDecimal shipFee = rs.getBigDecimal("ShipFee");
            BigDecimal taxAmount = rs.getBigDecimal("TaxAmount");
            Order.Status orderStatus = Order.getOrderStatus(rs.getInt("OrderStatusID"));
            String notes = rs.getString("Notes");
            Map<Part, Integer> parts = retrieveOrderedParts(orderID, "CustomerOrderID");
            return CustomerOrder.createExisting(
                orderId,
                customer,
                orderDate,
                shippingAddress,
                shippingProvider,
                shipDate,
                shipFee,
                parts,
                taxAmount,
                orderStatus,
                notes
            );
        }
        else {
            throw new ItemNotFoundException("Order with ID: " + orderID + " not found.");
        }
    }

    // could return empty list, this should be checked at calling
    public void collectAllOrders(Customer customer) throws SQLException {
        Integer customerId = customer.getIdNumber();
        if (customerId < 0) {
            throw new PersonNotFoundException("This customer is not saved to the database.");
        }
        String sql = "SELECT OrderID FROM AutoPartsStore.CustomerOrders WHERE CustomerID = ?;";
        PreparedStatement pStmt = connection.prepareStatement(sql);
        pStmt.setInt(1, customerId);
        ResultSet rs = pStmt.executeQuery();
        List<Order> orders = new ArrayList<>();
        while (rs.next()) {
            orders.add(getCustomerOrderByID(rs.getInt(1)));
        }
        customer.setOrderList(orders);
    }

    //todo needs test coverage
    public ResupplyOrder getResupplyOrderByID(Integer orderID) throws SQLException {
        String sql = "SELECT * FROM AutoPartsStore.ResupplyOrders WHERE ResupplyOrderID = ?;";
        PreparedStatement pStmt = connection.prepareStatement(sql);
        pStmt.setInt(1, orderID);
        ResultSet rs = pStmt.executeQuery();
        if (rs.next()) {
            int i = 1;
            Integer resupplyOrderID = rs.getInt("ResupplyOrderID");
            //fixme i think this is non-null so this check is redundant
            Employee creatingEmployee = getEmployeeByID(rs.getInt("CreatedBy"));
            Date creationDate = rs.getDate("CreationDate");
            BigDecimal shipFee = rs.getBigDecimal("ShipFee");
            Date shipDate = rs.getDate("ShipDate");
            BigDecimal taxAmount = rs.getBigDecimal("TaxAmount");
            Order.Status orderStatus = Order.getOrderStatus(rs.getInt("OrderStatusID"));
            Long approvingEmployeeId = (Long) rs.getObject("ApprovedBy");
            Employee approvedBy =
                (approvingEmployeeId == null)
                    ? null
                    : getEmployeeByID(java.lang.Math.toIntExact(approvingEmployeeId));
            Date approvedDate = rs.getDate("ApprovedDate");
            String notes = rs.getString(i++);
            Map<Part, Integer> parts = retrieveOrderedParts(orderID, "ResupplyOrderID");
            return ResupplyOrder.createExisting(
                resupplyOrderID,
                creatingEmployee,
                creationDate,
                approvedDate,
                approvedBy,
                shipDate,
                shipFee,
                parts,
                taxAmount,
                orderStatus,
                notes
            );
        }
        else {
            throw new ItemNotFoundException("Order with ID: " + orderID + " not found.");
        }
    }

    // could return empty list, this should be checked at calling
    //todo needs test coverage
    public void collectAllOrders(Employee employee) throws SQLException {
        Integer employeeId = employee.getIdNumber();
        if (employeeId < 0) {
            throw new PersonNotFoundException("This employee is not saved to the database.");
        }
        String sql = "SELECT ResupplyOrderID FROM AutoPartsStore.ResupplyOrders WHERE CreatedBy = ?;";
        PreparedStatement pStmt = connection.prepareStatement(sql);
        pStmt.setInt(1, employeeId);
        ResultSet rs = pStmt.executeQuery();
        List<Order> orders = new ArrayList<>();
        while (rs.next()) {
            orders.add(getResupplyOrderByID(rs.getInt(1)));
        }
        employee.setOrderList(orders);
    }

    //todo needs test coverage
    private Map<Part, Integer> retrieveOrderedParts(Integer orderId, String idType) throws SQLException {
        Map<Part, Integer> partsAndQuantity = new HashMap<>();
        String sql = "SELECT PartID, Quantity" +
                         " FROM AutoPartsStore.OrderDetails" +
                         " WHERE " + idType + " = ?;";
        PreparedStatement pStmt = connection.prepareStatement(sql);
        pStmt.setInt(1, orderId);
        ResultSet rs = pStmt.executeQuery();
        while (rs.next()) {
            // add each part and the quantity to the map
            partsAndQuantity.put(
                retrievePartByID(rs.getInt("PartID")),
                rs.getInt("Quantity")
            );
        }
        return partsAndQuantity;
    }

    // has test coverage
    public Part retrievePartByID(Integer id) throws SQLException {
        String sql = "SELECT * FROM AutoPartsStore.Parts WHERE PartID = ?;";
        PreparedStatement pStmt = connection.prepareStatement(sql);
        pStmt.setInt(1, id);
        ResultSet rs = pStmt.executeQuery();
        if (rs.next()) {
            int i = 1;
            Integer partId = rs.getInt(i++);
            Supplier supplier = retrieveSupplierByID(rs.getInt(i++));
            Car carConfig = retrieveCarConfigByID(rs.getInt(i++));
            String name = rs.getString(i++);
            String description = rs.getString(i++);
            String category = rs.getString(i++);
            BigDecimal pricePerUnit = rs.getBigDecimal(i++);
            String quantityPerUnit = rs.getString(i++);
            boolean discontinued = rs.getBoolean(i++);
            Integer quantityOnHand = rs.getInt(i++);
            byte[] img = rs.getBytes(i++);
            Part part = Part.createExisting(
                partId, supplier, carConfig, name, category, description,
                pricePerUnit, quantityPerUnit, discontinued, quantityOnHand, img
            );
            return part;
        }
        else {
            throw new ItemNotFoundException("Part with ID: " + id + " not found.");
        }
    }

    // has test coverage
    public List<Part> getAllParts() throws SQLException {
        String sql = "SELECT PartID FROM AutoPartsStore.Parts;";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<Part> parts = new ArrayList<>();
        while (rs.next()) {
            parts.add(retrievePartByID(rs.getInt(1)));
        }
        return parts;
    }

    // has test coverage
    public Supplier retrieveSupplierByID(Integer id) throws SQLException {
        String sql = "SELECT * FROM AutoPartsStore.Suppliers WHERE SupplierID = ?;";
        PreparedStatement pStmt = connection.prepareStatement(sql);
        pStmt.setInt(1, id);
        ResultSet rs = pStmt.executeQuery();
        if (rs.next()) {
            int i = 1;
            Integer supplierId = rs.getInt(i++);
            String companyName = rs.getString(i++);
            String contactName = rs.getString(i++);
            String primaryPhone = rs.getString(i++);
            String secondaryPhone = rs.getString(i++);
            String website = rs.getString(i++);
            Address address = new Address(
                rs.getString(i++), rs.getString(i++), rs.getString(i++), rs.getString(i++)
            );
            String notes = rs.getString(i++);
            return Supplier.createExisting(
                supplierId, companyName, contactName, primaryPhone,
                secondaryPhone, website, address, notes
            );
        }
        else {
            throw new ItemNotFoundException("Supplier with ID: " + id + " not found.");
        }
    }

    // has test coverage
    public Car retrieveCarConfigByID(Integer id) throws SQLException {
        String sql = "SELECT CarMakeID, CarModelID, ProductionYear FROM AutoPartsStore.CarConfigurations WHERE CarID = ?;";
        PreparedStatement pStmt = connection.prepareStatement(sql);
        pStmt.setInt(1, id);
        ResultSet rs = pStmt.executeQuery();
        if (rs.next()) {
            String make = retrieveCarMakeByID(rs.getInt("CarMakeID"));
            String model = retrieveCarModelByID(rs.getInt("CarModelID"));
            String yearAsString = rs.getString("ProductionYear");
            int year = -1;
            try {
                year = Integer.parseInt(yearAsString);
            }
            catch (NumberFormatException ex) {
                System.err.println("Problem parsing year: " + yearAsString + ". Set to -1");
            }
            return Car.createExisting(id, make, model, year);
        }
        else {
            throw new ItemNotFoundException("Car with ID: " + id + " not found.");
        }
    }

    // has test coverage
    public Car retrieveCarConfigByFields(String make, String model, Integer productionYear) throws SQLException {
        Integer carMake = retrieveCarMakeIDByName(make);
        Integer carModel = retrieveCarModelIDByName(model);
        String year = String.valueOf(productionYear);
        String sql = "SELECT CarID" +
                         " FROM AutoPartsStore.CarConfigurations" +
                         " WHERE CarMakeID = ?" +
                         " AND CarModelID = ?" +
                         " AND ProductionYear = ?;";
        PreparedStatement pStmt = connection.prepareStatement(sql);
        pStmt.setInt(1, carMake);
        pStmt.setInt(2, carModel);
        pStmt.setString(3, year);
        ResultSet rs = pStmt.executeQuery();
        if (rs.next()) {
            return retrieveCarConfigByID(rs.getInt(1));
        }
        else {
            throw new ItemNotFoundException(
                "Unable to locate a car configuration with values:\n" +
                "Make: " + make + "\n" +
                "Model: " + model + "\n" +
                "Year: " + year + "\n"
            );
        }
    }

    // has test coverage
    public String retrieveCarMakeByID(Integer id) throws SQLException {
        String sql = "SELECT MakeName FROM AutoPartsStore.CarMakes WHERE CarMakeID = ?;";
        PreparedStatement pStmt = connection.prepareStatement(sql);
        pStmt.setInt(1, id);
        ResultSet rs = pStmt.executeQuery();
        if (rs.next()) {
            return rs.getString("MakeName");
        }
        else {
            throw new ItemNotFoundException("Car Make with ID: " + id + " not found.");
        }
    }

    // has test coverage
    public Integer retrieveCarMakeIDByName(String makeName) throws SQLException {
        String sql = "SELECT CarMakeID" +
                         " FROM AutoPartsStore.CarMakes" +
                         " WHERE MakeName = ?;";
        PreparedStatement pStmt = connection.prepareStatement(sql);
        pStmt.setString(1, makeName);
        ResultSet rs = pStmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        else {
            throw new ItemNotFoundException("Car Make with name: \"" + makeName + "\" not found.");
        }
    }

    // has test coverage
    public List<String> getAllCarMakes() throws SQLException {
        String sql = "SELECT MakeName" +
                         " FROM AutoPartsStore.CarMakes;";
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(sql);
        List<String> makes = new ArrayList<>();
        while (rs.next()) {
            makes.add(rs.getString(1));
        }
        return makes;
    }

    // has test coverage
    public List<String> getAllCarModels(String make) throws SQLException {
        //find the make number in db
        String sql = "SELECT CarMakeID" +
                         " FROM AutoPartsStore.CarMakes" +
                         " WHERE MakeName = ?;";
        PreparedStatement pStmt = connection.prepareStatement(sql);
        pStmt.setString(1, make);
        ResultSet rs = pStmt.executeQuery();
        rs.next();
        Integer makeId = rs.getInt(1);
        if (makeId == null) {
            throw new ItemNotFoundException("Make name: \"" + make + "\" not found.");
        }
        sql = "SELECT CarModelName" +
                  " FROM AutoPartsStore.CarModels" +
                  " WHERE CarMakeID = ?;";
        pStmt = connection.prepareStatement(sql);
        pStmt.setInt(1, makeId);
        rs = pStmt.executeQuery();
        List<String> models = new ArrayList<>();
        while (rs.next()) {
            models.add(rs.getString(1));
        }
        return models;
    }

    // has test coverage
    public List<Integer> getAllProductionYears(String model) throws SQLException {
        String sql = "Select CarModelID" +
                         " FROM AutoPartsStore.CarModels" +
                         " WHERE CarModelID = ?;";
        PreparedStatement pStmt = connection.prepareStatement(sql);
        pStmt.setInt(1, retrieveCarModelIDByName(model));
        ResultSet rs = pStmt.executeQuery();
        rs.next();
        Integer carModelId = rs.getInt(1);
        if (carModelId == null) {
            throw new ItemNotFoundException("Model name: \"" + model + "\" not found.");
        }
        sql = "SELECT ProductionYear" +
                  " FROM AutoPartsStore.CarConfigurations" +
                  " WHERE CarModelID = ?;";
        pStmt = connection.prepareStatement(sql);
        pStmt.setInt(1, carModelId);
        rs = pStmt.executeQuery();
        List<Integer> years = new ArrayList<>();
        while (rs.next()) {
            String yearString = "";
            try {
                yearString = rs.getString(1);
                Integer yearInt = Integer.parseInt(yearString);
                years.add(yearInt);
            }
            catch (NumberFormatException ex) {
                throw new SQLException(
                    "Data corruption on row " + rs.getRow() + "\n" +
                        "Year value stored: " + yearString + "\n" +
                        "Could not convert to integer value.\n" +
                        "Query performed: " + sql
                );
            }
        }
        return years;
    }

    // has test coverage
    public String retrieveCarModelByID(Integer id) throws SQLException {
        String sql = "SELECT CarModelName FROM AutoPartsStore.CarModels WHERE CarModelID = ?;";
        PreparedStatement pStmt = connection.prepareStatement(sql);
        pStmt.setInt(1, id);
        ResultSet rs = pStmt.executeQuery();
        if (rs.next()) {
            return rs.getString("CarModelName");
        }
        else {
            throw new ItemNotFoundException("Car Model with ID: " + id + " not found.");
        }
    }

    // has test coverage
    public Integer retrieveCarModelIDByName(String modelName) throws SQLException {
        String sql = "SELECT CarModelID" +
                         " FROM AutoPartsStore.CarModels" +
                         " WHERE CarModelName = ?;";
        PreparedStatement pStmt = connection.prepareStatement(sql);
        pStmt.setString(1, modelName);
        ResultSet rs = pStmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(1);
        }
        else {
            throw new ItemNotFoundException("Car Model with name \"" + modelName + "\" not found.");
        }
    }

    // has test coverage
    public ShippingProvider retrieveShippingProviderByID(Integer id) throws SQLException {
        String sql = "SELECT * FROM AutoPartsStore.Shippers WHERE ShipperID = ?;";
        PreparedStatement pStmt = connection.prepareStatement(sql);
        pStmt.setInt(1, id);
        ResultSet rs = pStmt.executeQuery();
        if (rs.next()) {
            int i = 1;
            Integer shipperID = rs.getInt(i++);
            String companyName = rs.getString(i++);
            String contactName = rs.getString(i++);
            String primaryPhone = rs.getString(i++);
            String secondaryPhone = rs.getString(i++);
            String website = rs.getString(i++);
            Address address = new Address(
                rs.getString(i++), // street
                rs.getString(i++), // city
                rs.getString(i++), // state
                rs.getString(i++) // zip
            );
            String notes = rs.getString(i++);
            return ShippingProvider.createExisting(
                shipperID,
                companyName,
                contactName,
                primaryPhone,
                secondaryPhone,
                website,
                address,
                notes
            );
        }
        else {
            throw new ItemNotFoundException("Shipping Provider with ID: " + id + " not found.");
        }
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
}
