/*
Phase 5 - Preliminary System.
David Brown & Aditya Tornala
This current version of the program contains all of the features that the final
program will contain, but it does not retreive data from the text file yet. 
**When the program is run for the first time and the database(text file) is created, the user will have to
log into a default Admin account in order to start adding employees to the database the credentials for this default admin
account is :
*******************
Username: admin
Password: 123
*******************
 */
package employeetimekeepingapplication;

import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

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
      
      addAdminToList(list);
      loginMenu(list);

      /*chooses which user type. final version of app will select this based on the type of object has logged in
 
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
      
          */
    }

    
    public static void loginMenu(SLinkedList list){
        
       
        Employee test = null;
        do{
        
            String userName = JOptionPane.showInputDialog("enter username");
            String password = JOptionPane.showInputDialog("enter password");

            test = login(userName,password, list);
        }while(test == null);
        

        if(test instanceof Manager){
            managerMenu(list);
        }
        else if(test instanceof Admin){
           adminMenu(list);
           
        }
        else{
            employeeMenu(list);
        }
        
      
        
    }
    
     public static SLinkedList sort(SLinkedList list){ // working code!!
        SNode min;
        Employee temp;
        for(SNode index = list.getHead(); index != null; index = index.getNext()){
            min = index;
            for(SNode index2 = index.getNext(); index2 != null; index2 = index2.getNext()){
                if(index2.getData().getLastName().compareToIgnoreCase(min.getData().getLastName()) < 0){
                    min = index2;
                }
            }
            if(index != min){
                temp = index.getData();
                index.setData(min.getData());
                min.setData(temp);
            }
        }
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
            if(choice ==3){
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

        employee.setFirstName(JOptionPane.showInputDialog("Enter first name:"));
        employee.setLastName(JOptionPane.showInputDialog("Enter last name: "));
        employee.setID();
        employee.setPassword(JOptionPane.showInputDialog("Enter password: "));
        boolean valid = false;
        do{
            try{
                  valid = employee.setHoursWorked(Integer.parseInt(JOptionPane.showInputDialog("Enter hours worked")));    
            }catch(NumberFormatException e){
                JOptionPane.showMessageDialog(null,"Invalid input. Try again");
            }
        }while(!valid);
        
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
                    checkIfFileEmpty("database.txt");
                }
            }catch(IOException e){
                e.printStackTrace();
            }
            
        }
        
    }
    
    //adds the default admin in the text file to the linked list
    private static void addAdminToList(SLinkedList list){
        Employee defaultAdmin = new Admin();
        defaultAdmin.setID("admin");
        defaultAdmin.setPassword("123");
        
        SNode admin = new SNode(defaultAdmin, null);
        list.add(admin);
        
    }
    
  //  public static void writeDefaultAdd()
    
    private static Employee login(String userID, String password, SLinkedList list){// login method 
        int code = searchTextFile(userID, password);
        Employee employee = null;
        
        if(code == 1){
            employee = list.getSpecific(userID);
            return employee;
        }
        else if(code == 0){
            JOptionPane.showMessageDialog(null, "Account disabled");
        }
        else if(code == -1){
            JOptionPane.showMessageDialog(null, "Incorrect password");
        }
        else{
            JOptionPane.showMessageDialog(null, "Account does not exist.");
        }
        
        return null;
    }
    
    
    private static void checkIfFileEmpty(String filepath){// if file is empty adds admin account
        File file = new File(filepath);
        
        if(file.length() == 0){
            PrintWriter pr = null;
            try{
            pr = new PrintWriter(new BufferedWriter(new FileWriter(new File(filepath), true)));
            pr.println("admin" + ","+ "123" + "," + "enabled");
            
            
        }catch(IOException e){
            e.printStackTrace();
        }
        finally{
            pr.close();
        }
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

    //write to file method
    
    /*
    
    private static void createDefaultAdmin(File filepath){
        PrintWriter pr = null;
        try{
            pr = new PrintWriter(new BufferedWriter(filepath), true)));
            pr.println(employee.getId() + " " + employee.getPassword() + " " + employee.getFirstName() +
                        " " + employee.getLastName() + " " + " " + employee.getHoursWorked() + " " +
                            employee.getLocation() + " " + employee.getAddress()
                            );
            
        }catch(IOException e){
            e.printStackTrace();
        }
        finally{
            pr.close();
        }       
        
        
        
    }
    */
    private static void writeToFile(String filepath, Employee employee){ // adds object to the files. 
        PrintWriter pr = null;
        try{
            pr = new PrintWriter(new BufferedWriter(new FileWriter(new File(filepath), true)));
            pr.println(employee.getId() + "," + employee.getPassword() + "," + employee.getFirstName() +
                        "," + employee.getLastName() + "," + "," + employee.getHoursWorked() + "," +
                            employee.getLocation().getLocationId() + "," + employee.getAddress()
                            );
            
        }catch(IOException e){
            e.printStackTrace();
        }
        finally{
            pr.close();
        }
    }
        
     private static int searchTextFile(String userID, String password){// earches file for the specified login
        File data = new File("database.txt");
        int statusCode = -2;
        try{
            BufferedReader br = new BufferedReader(new FileReader(data));
          //  br.readLine();
            String line;
            while((line = br.readLine()) != null){
                String[] dataSet = line.split(",");
                if(dataSet[0].equals(userID) && dataSet[1].equals(password) && dataSet[dataSet.length-1].equals("enabled")){
                    statusCode = 1; //status code 1 everything is good.
                    return statusCode;
                }
                else if(dataSet[0].equals(userID) && dataSet[1].equals(password) && !dataSet[dataSet.length-1].equals("enabled")){
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
