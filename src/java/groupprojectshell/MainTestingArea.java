package groupprojectshell;

import data_models.locations.Address;
import data_models.business_entities.Business;
import data_models.business_entities.ShippingProvider;
import data_models.people.Customer;
import data_models.people.Employee;
import database_controller.DatabaseManager;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

public class MainTestingArea {

    public static void main(String[] args) {
        Address address = new Address("123", "Atlanta", "GA", "30033");

        Customer test = Customer.createNew("TEST1", "TEST2", "test@test.test", "1234567890", "1234567890", address, null);
        DatabaseManager db = new DatabaseManager();
        Customer test2 = Customer.createExisting(103, "UPDATETEST1", "UPDATETEST2", "change@email.change", "1234567890", null, address, null);


        //Employee employee = Employee.createNew("TestEmployee", "TestEmployee", "testEmail@testEmail.test", "0123456789", null, address, null, "Boss");


        try {
            Customer existing = db.getCustomerByEmail("change@email.change");

            System.out.println(existing.getIdNumber());
            System.out.println(existing.getLastName());
            System.out.println(existing.getFirstName());
            System.out.println(existing.getEmail());
            System.out.println(existing.getPrimaryPhone());
            System.out.println(existing.getSecondaryPhone());
            System.out.println(existing.getAddress().getStreet());
            System.out.println(existing.getAddress().getCity());
            System.out.println(existing.getAddress().getState());
            System.out.println(existing.getAddress().getZipCode());
            System.out.println(existing.getNotes());
        }
        catch (SQLException ex) {
            ex.printStackTrace();
        }




    }

    public static void connectToDb() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        String serverName = "jdbc:mysql://localhost";
        DriverManager.getConnection(serverName, "testuser", "javaiii");
        System.out.println("Connected");
    }

}
