/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeetimekeepingapplication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JOptionPane;

/**
 *
 * @author Dave
 */
public class EmployeeTimekeepingApplication {

    public static void main(String[] args){
        
        //create linked list
        SLinkedList list = new SLinkedList();
        
<<<<<<< .merge_file_0dnicT
        
        Employee employee = new Employee();
        employee.setFirstName("John");
        employee.setPassword("oleole123");
        employee.setID();
        SNode node = new SNode(employee,null);
        list.add(node);
        writeToFile("database.txt",employee);
        Employee n = login(employee.getId(),"oleole123",list);
        System.out.println(n.getFirstName());
        
        
        
        
        
        
=======
        //if file does not exist, create file
        if(!checkFileExists()){
            createFile();
        }
        //store contents of file into linked list
       
>>>>>>> .merge_file_SNbG7T
        
        
       int item = -1;
       do{
            item = choiceMenu();
 
       }while(item !=4);
      //  
    }
    
    private static void addEmployee(SLinkedList list, int choice){
        Employee employee = null;
        switch(choice){
            case 1:
                employee = new Employee();
                break;
            case 2:
                employee = new Manager();
                break;
            case 3:
                employee = new Admin();
                break;            
        }
        createEmployee(employee);
        SNode node = new SNode(employee,null);
        list.add(node);
    }
    
    private static void createEmployee(Employee employee){
        employee.setFirstName(JOptionPane.showInputDialog("Enter first name:"));
        employee.setLastName(JOptionPane.showInputDialog("Enter last name: "));
        employee.setPassword(JOptionPane.showInputDialog("Enter password: "));
        
        addAddress(employee);
        addLocation(employee);
        
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
    
    private static Employee login(String userID, String pass,SLinkedList list){
        int code = searchTextFile(userID, pass);
        Employee employee = null;
        if(code == 1){
            employee = list.getSpecific(userID);
        }
        
        return employee;
    }
    
    private static void writeToFile(String filepath, Employee employee){
        PrintWriter pr = null;
        try{
            pr = new PrintWriter(new BufferedWriter(new FileWriter(new File(filepath), true)));
            pr.println(employee.getId() + " oleole123" + " enabled");
            
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
    
    private static int choiceMenu(){
        int choice = 0;
        do{
            try{
                choice = Integer.parseInt(JOptionPane.showInputDialog("Enter Number Option: \n" + 
                        "1. Create Employee account \n" +
                        "2. Create Manager account \n" +
                        "3. Create Admin account\n "+
                        "4. Exit"
                        
                ));
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(null, "Invalid Option. Try again");
            }
        }while(choice <= 0 || choice > 4) ;
        
        return choice;
    }
    
    private static int searchTextFile(String userID, String password){
        File data = new File("database.txt");
        int statusCode = -2;
        try{
            BufferedReader br = new BufferedReader(new FileReader(data));
            String line;
            while((line = br.readLine()) != null){
                String[] dataSet = line.split(" ");
                if(dataSet[0].equals(userID) && dataSet[1].equals(password) && dataSet[2].equals("enabled")){
                    statusCode = 1;
                    return statusCode;
                }
                else if(dataSet[0].equals(userID) && dataSet[1].equals(password) && !dataSet[2].equals("enabled")){
                    statusCode = 0;
                    return statusCode;
                }
                else if(dataSet[0].equals(userID) && !dataSet[1].equals(password)){
                    statusCode = -1;
                    return statusCode;
                }
                
                
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        
        return statusCode;
    }
    
    
    
    
    
    
    
}
