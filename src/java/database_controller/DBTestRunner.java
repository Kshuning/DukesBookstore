/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database_controller;

import data_models.people.Employee;
import java.util.ArrayList;

/**
 *
 * @author Shuning
 */
public class DBTestRunner {

    public static void main(String args[]) {
        DatabaseManager dbManager = new DatabaseManager();
        ArrayList<Employee> employee = null;
        try {
            employee = new ArrayList<>(dbManager.getEmployees());
        } catch (Exception e) {
            System.out.print(e.getMessage());
        }

        for(int i=0;i<employee.size();i++){
            System.out.print(employee.get(i).getFirstName()+":");
            System.out.print(employee.get(i).getLastName()+":");
            System.out.print(employee.get(i).getIdNumber()+":");
            System.out.print(employee.get(i).getPrimaryPhone()+":");
            System.out.print(employee.get(i).getSecondaryPhone()+":");
            System.out.print(employee.get(i).getEmail()+":");
            System.out.print(employee.get(i).getTitle()+":");
            System.out.print(employee.get(i).getAddress().getCity()+":");
            System.out.print(employee.get(i).getAddress().getState()+":");
            System.out.print(employee.get(i).getAddress().getZipCode()+":");
            System.out.println(employee.get(i).getAddress().getStreet()+":");
        }
        
    }
}
