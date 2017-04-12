/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeetimekeepingapplication;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.util.LinkedList;
import javax.swing.JOptionPane;

/**
 *
 * @author Dave
 */
public class EmployeeTimekeepingApplication {

    public static void main(String[] args){
        LinkedList<Employee> employeeList = new LinkedList<Employee>();
        
        int item = mainMenu();
        
        System.out.println(item);
        
    }
    
    private static void addEmployee(LinkedList<Employee> list){
        
    }
    
    private static void createEmployee(Employee employee){
        employee.setFirstName(JOptionPane.showInputDialog("Enter first name:"));
        employee.setLastName(JOptionPane.showInputDialog("Enter last name: "));
        employee.setPassword(JOptionPane.showInputDialog("Enter password: "));
        
        if(employee instanceof Manager){
            ///
        }
        
    }
    
    private static void addAddress(Employee employee){
        employee.getAddress().setStreet(JOptionPane.showInputDialog("Enter Street name: "));
        employee.getAddress().setAptNum(JOptionPane.showInputDialog("Enter apt num: "));
        employee.getAddress().setCity(JOptionPane.showInputDialog("Enter City: "));
        employee.getAddress().setState(JOptionPane.showInputDialog("Enter State: "));
        employee.getAddress().setZipCode(JOptionPane.showInputDialog("Enter ZIP: "));    
    }
    
    private static void addLocation(Employee employee){
        int id = locationMenu();
        employee.getLocation().setLocation(Integer.toString(id));
    }
    
    private static int locationMenu(){
        int locationID;
        do{
            try{
                locationID = Integer.parseInt(JOptionPane.showInputDialog("1. Factory 415, 123 Fake Street, Nowhere, NN, 00111 \n" +  
                "2. Research 221, 123 Faker Street, Still Nowhere, NN, 00112 \n" + 
                "3. Corporate 001, 123 Not-Fake Street, Somewhere, IL, 01010"));
            }
            catch(NumberFormatException e){
                JOptionPane.showMessageDialog(null, "Invalid location try again");
                locationID = -1;
            }
        }while(locationID < 0 || locationID > 3 );
        
        return locationID;
    }
    
    private static boolean checkFileExists(){
        String fileName = "database.txt";
        File f = new File(fileName);
        boolean check = f.exists();

        
        return check;
    }
    
    private static void createFile(){
        if(checkFileExists()){
            JOptionPane.showMessageDialog(null, "Loading database");
        }
        else{
            try{
                File file = new File("database.txt");
                
                if(file.createNewFile()){
                    JOptionPane.showMessageDialog(null, "Creating database");
                }
            }catch(IOException e){
                e.printStackTrace();
            }
            
        }
    }
    
    private static void login(String filepath){
        
    }
    
    private static void writeToFile(String filepath, Employee employee){
        PrintWriter pr = null;
        try{
            pr = new PrintWriter(new BufferedWriter(new FileWriter(new File(filepath), true)));
            pr.println("Testing");
            
        }catch(IOException e){
            e.printStackTrace();
        }
        finally{
            pr.close();
        }
    }
    
    private static int mainMenu(){
        int choice = 0;
        do{
            try{
            choice = Integer.parseInt(JOptionPane.showInputDialog("Enter Number Option: \n" + 
                    "1. Create Account \n" + "2. Login To existing Account"));
            }catch(NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Invalid choice, Try again");
            }
        }while(choice <= 0 || choice > 2);
        
        return choice;
    }
    
    
    
}
