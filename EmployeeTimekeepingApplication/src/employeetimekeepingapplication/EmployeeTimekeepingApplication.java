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
      JOptionPane.showMessageDialog(null, "Welcome!");
      loginMenu(list);

    }

    
    public static void loginMenu(SLinkedList list){
        
       //current employee
        Employee test = null;
        boolean exit = false;
        int count = 0;
        
        do{
            do{

                String userName = JOptionPane.showInputDialog("enter username");
                     
                    if(userName == null){
                        JOptionPane.showMessageDialog(null,"Goodbye");
                        System.exit(1);
                    }
                
                String password = JOptionPane.showInputDialog("enter password");

                test = login(userName,password, list);
            }while(test == null || exit);


            if(test instanceof Manager){
                managerMenu(list);
            }
            else if(test instanceof Admin){
               adminMenu(list);

            }
            else{

                employeeMenu(list,test);
            }
        
        }while(JOptionPane.showConfirmDialog(null,"Log into another account?")== JOptionPane.YES_OPTION);
        
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
    private static void employeeMenu(SLinkedList list, Employee test){
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
                  JOptionPane.showMessageDialog(null,test);
                }
                if(choice == 2){
                    updateInformation(test);

                }
                if(choice == 3){
                    setHoursWorked(test);
                }
                if(choice == 4){
                    signTimeSheet(test);
                }
        }while(choice != 5) ;  
      
    }
    private static void reports(SLinkedList list){
        int choice = 0;
        do{
            try{
                        choice = Integer.parseInt(JOptionPane.showInputDialog("How would you like to sort the reports?: \n" + 
                        "1. Alphabetically (by last name) \n" +
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
            if(choice ==2){
                list = sortByHours(list);
                printAll(list);
            }
            if(choice ==3){
                printAll(list);
            }
        }while(choice != 4);
        
        
    }
    
    private static void fileToList(SLinkedList list){
       File data = new File("database.txt");
       String[] str;
        try{ 
            BufferedReader br = new BufferedReader(new FileReader(data));
            br.readLine();
            String line;
            while((line = br.readLine()) != null){ 
                if(line.trim().length() > 0){
                    str = line.split(",");
                    
                    //if type is admin
                    if(str[8].equals("3")){
                        Admin employee = new Admin(str[0],str[1],str[2],str[3],Double.parseDouble(str[4]),Integer.parseInt(str[5]),str[6],str[8]);
                        SNode node = new SNode(employee,null);
                       
                        list.add(node);
                    }
                    //if type is manager
                    if(str[8].equals("2")){
                        Manager employee = new Manager(str[0],str[1],str[2],str[3],Double.parseDouble(str[4]),Integer.parseInt(str[5]),str[6],str[8]); 
                         SNode node = new SNode(employee,null);
                         list.add(node);                       
                    }
                    //if type is employee
                    else{
                        Employee employee  = new Employee(str[0],str[1],str[2],str[3],Double.parseDouble(str[4]),Integer.parseInt(str[5]),str[6],str[8]);
                         SNode node = new SNode(employee,null);
                         list.add(node);    
                    }

                }
            }
            
        }catch(Exception e){
            e.printStackTrace();
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
                System.out.println("admin instanciated");
                break;            
        }
        
        createEmployee(employee);        
        SNode node = new SNode(employee,null);
        list.add(node);
    }
    
    private static void createEmployee(Employee employee){
        
        //set the typeKey
        
        
        if(employee instanceof Admin){
            employee.setTypeKey();
        }
        if(employee instanceof Manager){
            employee.setTypeKey();
        }
        else{
            employee.setTypeKey();
        }

        
        employee.setFirstName(JOptionPane.showInputDialog("Enter first name:"));
        employee.setLastName(JOptionPane.showInputDialog("Enter last name: "));
        employee.setID();
        JOptionPane.showMessageDialog(null,"****\nYour user ID is: " + employee.getId() + "\n****\n\nwrite this down!!");
        employee.setPassword(JOptionPane.showInputDialog("Enter password: "));
        employee.setSalary(10000);
        
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
        
        
        writeToFile("database.txt", employee);
      
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
                locationID = Integer.parseInt(JOptionPane.showInputDialog("Please Enter Work Location\n\n1. Factory 415, 123 Fake Street, Nowhere, NN, 00111 \n" +  
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
        Admin defaultAdmin = new Admin("admin","123","defaultFN","defaultLN",1,1,"default","3");        
        SNode admin = new SNode(defaultAdmin, null);
        list.add(admin);
        
    }
    
  //  public static void writeDefaultAdd()
    
    private static Employee login(String userID, String password, SLinkedList list){// login method 
        int code = searchTextFile(userID, password);
        Employee employee = null;
        int count = 0;
        
        if(code == 1){
            employee = list.getSpecific(userID);
            return employee;
        }
        else if(code == 0){
            JOptionPane.showMessageDialog(null, "Account disabled");
        }
        else if(code == -1){
            JOptionPane.showMessageDialog(null, "Incorrect password");
            employee = list.getSpecific(userID);
            employee.setLoginAttempts();
            
            if(employee.getLoginAttempts() == 3){
                disableUser(userID);
            }
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
            pr.println("admin" + ","+ "123" + "," + "enabled" + "," + 3);
            
            
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

    private static void writeToFile(String filepath, Employee employee){ // adds object to the files. 
        PrintWriter pr = null;
        try{
            pr = new PrintWriter(new BufferedWriter(new FileWriter(new File(filepath), true)));
            pr.println(employee.getId() + "," + employee.getPassword() + "," + employee.getFirstName() +
                        "," + employee.getLastName() + "," + employee.getHoursWorked() + "," +
                            employee.getLocation().getLocationId() + "," + employee.getAddress() + ","+ "enabled" + "," + employee.getTypeKey()
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
 
                if(dataSet[0].equals(userID) && dataSet[1].equals(password) &&  dataSet[dataSet.length-2].equals("enabled")){
                    statusCode = 1; //status code 1 everything is good.
                    return statusCode;
                }
                else if(dataSet[0].equals(userID) && dataSet[1].equals(password) && !dataSet[dataSet.length-2].equals("enabled")){
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
     
     public static SLinkedList sortByHours(SLinkedList list){ 
        SNode min;
        Employee temp;
        for(SNode index = list.getHead(); index != null; index = index.getNext()){
            min = index;
            for(SNode index2 = index.getNext(); index2 != null; index2 = index2.getNext()){
                if(index2.getData().getHoursWorked() < min.getData().getHoursWorked()){
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
     
    public static void updateInformation(Employee test){
        
        String ogName = test.getFirstName();
        String ogLastName = test.getLastName();
        
        test.setFirstName(JOptionPane.showInputDialog("Enter new first name: "));
        test.setLastName(JOptionPane.showInputDialog("Enter new last name: "));
        
        try {
        
        BufferedReader file = new BufferedReader(new FileReader("database.txt"));
        String line;
        StringBuffer inputBuffer = new StringBuffer();

        while ((line = file.readLine()) != null) {
                inputBuffer.append(line);
                inputBuffer.append('\n');         
        }
        String inputStr = inputBuffer.toString();
        
        file.close();
        
        System.out.println(inputStr); // check that it's inputted right

        
        inputStr = inputStr.replace(ogName, test.getFirstName());
        inputStr = inputStr.replace(ogLastName, test.getLastName());

        // check if the new input is right
        System.out.println("----------------------------------\n"  + inputStr);

        // write the new String with the replaced line OVER the same file
        FileOutputStream fileOut = new FileOutputStream("database.txt");
        fileOut.write(inputStr.getBytes());
        fileOut.close();

    } catch (Exception e) {
        e.printStackTrace();
    }
        
    }
    
    public static void disableUser(String userID){
        try {
        
        BufferedReader file = new BufferedReader(new FileReader("database.txt"));
        String line;
        StringBuffer inputBuffer = new StringBuffer();
        

        while ((line = file.readLine()) != null) {
            String[] data = line.split(",");
            if(data[0].equals(userID)){
                line = line.replace("enabled", "disabled");
            }
            inputBuffer.append(line);
            inputBuffer.append('\n');
        }
        String inputStr = inputBuffer.toString();

        file.close();

        // check if the new input is right
        System.out.println("----------------------------------\n"  + inputStr);

        // write the new String with the replaced line OVER the same file
        FileOutputStream fileOut = new FileOutputStream("database.txt");
        fileOut.write(inputStr.getBytes());
        fileOut.close();

    } catch (Exception e) {
        e.printStackTrace();
    }
    }
    
    public static void setHoursWorked(Employee employee){
        boolean valid = false;
        
        do{
            try{
                valid = employee.setHoursWorked(Double.parseDouble(JOptionPane.showInputDialog("Enter hours worked")));
            }catch(NumberFormatException e){
                valid = false;
                JOptionPane.showMessageDialog(null, "Invalid. Hours must be between 0 and 200");
            }
        }while(!valid);
    }
    
    public static void signTimeSheet(Employee employee){
        
        try {
        
        BufferedReader file = new BufferedReader(new FileReader("database.txt"));
        String line;
        StringBuffer inputBuffer = new StringBuffer();
        
        while ((line = file.readLine()) != null) {
                String[] dataSet = line.split(",");
                if(dataSet[0].equals(employee.getId())){
                    line = line.replace(dataSet[4],String.valueOf(employee.getHoursWorked()));
                    }
                inputBuffer.append(line);
                inputBuffer.append('\n');
        }
        String inputStr = inputBuffer.toString();
        
        file.close();
        
        System.out.println(inputStr); // check that it's inputted right


        // check if the new input is right
        System.out.println("----------------------------------\n"  + inputStr);

        // write the new String with the replaced line OVER the same file
        FileOutputStream fileOut = new FileOutputStream("database.txt");
        fileOut.write(inputStr.getBytes());
        fileOut.close();

    } catch (Exception e) {
        e.printStackTrace();
    }
        JOptionPane.showMessageDialog(null, "Hour changes have been saved.");
    }
   
    
}