/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeetimekeepingapplication;

import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Dave
 */
public class EmployeeTimekeepingApplication {

    public static void main(String[] args){
        
        //create linked list
        SLinkedList list = new SLinkedList();
        
        //if file does not exist, create file
        if(!checkFileExists()){
            createFile();
        }
        
        //store contents of file into linked list
      fileToList(list);
      
  

      /*chooses which user type. final version of app will select this based on the type of object has logged in
      */
      

      
      
      int choice = -1;
       do{
            try{
                choice = Integer.parseInt(JOptionPane.showInputDialog("Which Account is logging in? \n" + 
                        "1. Employee account \n" +
                        "2. Manager account \n" +
                        "3. Admin account\n "+
                        "4. Exit\n"
                        
                ));
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(null, "Invalid Option. Try again");
            }
            if(choice == 1){
              employeeMenu(list);
            }
            if(choice == 2){
              managerMenu(list);  
            }            
            if(choice == 3){
              adminMenu(list);  
            }

            
        }while(choice != 4) ;
      

    }

    public static SLinkedList sort(SLinkedList list){
        



            for(SNode index = list.getHead(); index != null; index = index.getNext()){
                SNode min = index;
                for(SNode index2 = index.getNext(); index2 != null; index2 = index2.getNext()){
                    if(index2.getData().getLastName().compareToIgnoreCase(min.getData().getLastName()) < 0){
                        min = index2;

                    }
                    SNode temp = index;
                    index.setData(min.getData());
                    min.setData(temp.getData());

                }
            }
            JOptionPane.showMessageDialog(null,"list has been sorted");
           
        return list;
    }     
    private static void managerMenu(SLinkedList list){
        int choice = 0;
        do{
            try{
                choice = Integer.parseInt(JOptionPane.showInputDialog("Manager Menu: \n" + 
                        "1. Create Account\n" +
                        "2. Reports\n"+
                        "3. Logout\n"
           
                ));
              
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(null, "Invalid Option. Try again");
            }
                            
                if(choice == 1){
                    createUserMenu(list);
                }
                if(choice == 2){
                    if(list.getHead()==null){
                       JOptionPane.showMessageDialog(null, "There are no employees addded to the program");
                    }
                    else{
                       reports(list); 
                    }
          
          
                }
                if(choice == 5){
                    printAll(list);
                }
        }while(choice != 3) ;  
      
    }
    private static void employeeMenu(SLinkedList list){
        int choice = 0;
        do{
            try{
                choice = Integer.parseInt(JOptionPane.showInputDialog("Employee Menu: \n" + 
                        "1. Display Profile\n" +
                        "2. Update Information\n"+
                        "3. Enter Time\n"+
                        "4. Sign Timesheet\n"+
                        "5. Logout\n"
                      
           
                ));
              
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(null, "Invalid Option. Try again");
            }
                            
                if(choice == 1){
                  
                }
                if(choice == 2){
  
                }
                if(choice == 3){
               
                }
        }while(choice != 5) ;  
      
    }
    private static void reports(SLinkedList list){
        int choice = 0;
        do{
            try{
                        choice = Integer.parseInt(JOptionPane.showInputDialog("How would you like to sort the reports?: \n" + 
                        "1. Alphabetically \n" +
                        "2. By Hours Worked\n"+
                        "3. As is (do not sort) \n"+
                        "4. Logout"
                        
                ));
                
                
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Invalid Option. Try again");
            }
            if(choice ==1){
                list = sort(list);
                printAll(list);
            }
        }while(choice != 4);
        
        
    }
    
    private static void fileToList(SLinkedList list){
       Scanner inputStream; 
       
       try{
           inputStream = new Scanner(new FileInputStream("database.txt"));
           inputStream.close();
       }catch(FileNotFoundException e){
           System.out.println(e);
           System.exit(1);
       }
       
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
     //   employee.setFirstName(JOptionPane.showInputDialog("Enter first name:"));
        employee.setLastName(JOptionPane.showInputDialog("Enter last name: "));
     //   employee.setPassword(JOptionPane.showInputDialog("Enter password: "));
        
     //   addAddress(employee);
     //   addLocation(employee);
        
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
        employee.getLocation().setLocation(id);
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
    
  //  public static void writeDefaultAdd()
    
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
    
    private static void createUserMenu(SLinkedList list){
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
        
       addEmployee(list,choice);
        
        
    }
    private static void adminMenu(SLinkedList list){
        int choice = 0;
        do{
            try{
                choice = Integer.parseInt(JOptionPane.showInputDialog("Administrator Menu: \n" + 
                        "1. Create Account \n" +
                        "2. Manage User \n" +
                        "3. View Disabled Users\n "+
                        "4. Logout\n"

                        
                ));
             
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(null, "Invalid Option. Try again");
            }
                            
                if(choice == 1){
                    createUserMenu(list);
                }
                if(choice == 2){

                }
                if(choice == 3){

                }
        }while(choice != 4) ;  
      
    }
    //PRINTS ALL EMPLOYEE OBJECTS FOR TESTING PURPOSES***
    private static void printAll(SLinkedList list){
        
        if(list.getHead() == null){
          
        }
        else{


            String output = "";
            SNode curr = list.getHead();

            //if there is only one object in list
           if(!curr.hasNext()){
                output += curr.getData();
            }
            else{
                //if there are multiple objects in list
                    while(curr != null){
                       output += curr.getData() + "\n";
                       curr = curr.getNext();
                    }
                }
                JOptionPane.showMessageDialog(null, output);
            }    
        }

    
    
    
}
