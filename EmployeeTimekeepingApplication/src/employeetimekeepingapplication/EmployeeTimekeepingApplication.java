/*
Phase 6- Final Version
David Brown & Aditya Tornala

This is the final version of the program, will all feautures, sorting,and a UI 


**When the program is run for the first time and the database(text file) is created, the user will have to
log into a default Admin account in order to start adding employees to the database the credentials for this default admin
account is :
*******************
Username: admin
Password: 123
*******************
 */
package employeetimekeepingapplication;

import java.awt.Dimension;
import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

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
                managerMenu(list, test);
            }
            else if(test instanceof Admin){
               adminMenu(list,test);

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
     
    private static void managerMenu(SLinkedList list, Employee test){
     
      Object [] options = {"Create Account","Reports", "Logout"};
  
      int choice = -1;
      
      do{
         
         choice = JOptionPane.showOptionDialog(null, "", "Manager Menu ",
                 JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                   		   null,options,options[0]);
                if(choice == 0){
                    createUserMenu(list,test);
                }
                if(choice == 1){
                    if(list.getHead()==null){
                       JOptionPane.showMessageDialog(null, "There are no employees addded to the program");
                    }
                    else{
                     
                       viewSameLocation(list,test);
                    }
                }
     }while(choice!=2); 
     
    }
              
       
        
    
    private static void employeeMenu(SLinkedList list, Employee test){
      Object [] options = {"Display Profile","Update Information", "Enter Time", "Sign Timesheet", "Logout" };
  
      int choice = -1;
      
      do{
         
         choice = JOptionPane.showOptionDialog(null, "", "Employee Menu ",
                 JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                   		   null,options,options[0]);
                if(choice == 0){
                    JOptionPane.showMessageDialog(null,test);
                }
                if(choice == 1){
                    updateInformation(test);
                }
                if(choice == 2){
                    setHoursWorked(test);
                    JOptionPane.showMessageDialog(null, "Thank you. Please sign the timesheet to finalize your time.");
                }if(choice == 3){
                     signTimeSheet(test);
                }
                
      
      }while(choice!=4);       
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
                    if(str[9].equals("3")){
                        Admin employee = new Admin(str[0],str[1],str[2],str[3],Double.parseDouble(str[4]),Integer.parseInt(str[5]),str[6],str[8],str[9]);
                        SNode node = new SNode(employee,null);
                       
                        list.add(node);
                    }
                    //if type is manager
                    if(str[9].equals("2")){
                        Manager employee = new Manager(str[0],str[1],str[2],str[3],Double.parseDouble(str[4]),Integer.parseInt(str[5]),str[6],str[8],str[9]); 
                         SNode node = new SNode(employee,null);
                         list.add(node);                       
                    }
                    //if type is employee
                    else{
                        Employee employee  = new Employee(str[0],str[1],str[2],str[3],Double.parseDouble(str[4]),Integer.parseInt(str[5]),str[6],str[8],str[9]);
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
            case 0:
                employee = new Employee();
                break;
            case 1:
                employee = new Manager();
                break;
            case 2:
                employee = new Admin();
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
        
        String password = "";
         
               
        do{
             password = JOptionPane.showInputDialog("Enter Password. Must be 5 or more characters long.");
             if(password.length()< 5){
                 JOptionPane.showMessageDialog(null,"Password must be 5 or more characters long");
             }
        
        }while(password.length() < 5);
        
            
        employee.setPassword(password);
        
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
        Admin defaultAdmin = new Admin("admin","123","defaultFN","defaultLN",1,1,"default","enabled","3");        
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
    
    private static void createUserMenu(SLinkedList list, Employee test){
      
      //check if database is full  
      if(list.getSize() >1000){
          
          JOptionPane.showMessageDialog(null, "The Database is full\n Database cannot exceeded 1000 users ");
      }
      else{ //if not full, allow adding employees
        
     
        if(test instanceof Admin){  

            //display admin create user menu
            Object [] options = {"Create Employee account","Create Manager account", "Create Admin Account", "Exit" };

            int choice = -1;

            do{

              choice = JOptionPane.showOptionDialog(null, "Administrator ", "Create User Menu ",
                         JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                                           null,options,options[0]);
               if(choice != 3){
                   addEmployee(list,choice);
               }

            }while(choice != 3);
    
        }
        else{
            //display manager create user menu
            Object [] options = {"Create Employee account","Exit" };

            int choice = -1;

            do{

              choice = JOptionPane.showOptionDialog(null, "Manager Access", "Create User Menu ",
                         JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                                           null,options,options[0]);
               if(choice != 1){
                   addEmployee(list,choice);
               }

            }while(choice != 1);          
            
            
        }
      }
    }
    private static void adminMenu(SLinkedList list, Employee test){
        
   
      Object [] options = {"Create Account","Manager User", "View Disabled Users", "Logout" };
  
      int choice = -1;
      
      do{
         
         choice = JOptionPane.showOptionDialog(null, "", "Admin Menu ",
                 JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                   		   null,options,options[0]);
                if(choice == 0){
                    createUserMenu(list,test);
                }
                if(choice == 1){
                    manageUser(list, "jimmy");
                }
                if(choice == 2){
                    viewDisabled(list);
                }
      
      }while(choice!=3);       
             
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
    
    public static void disableUser(String userID){ //use for 
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
        finally{
            JOptionPane.showMessageDialog(null, "Account is now Disabled");
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
    
    public static void viewDisabled(SLinkedList list){
        String disabled = "**DISABLED USERS**\n";
        try{ 
            BufferedReader br = new BufferedReader(new FileReader("database.txt"));
            br.readLine();
            String line;
            while((line = br.readLine()) != null){ 
                if(!line.equals("")){
                    String[] dataSet = line.split(",");
                    if(dataSet[8].equals("disabled")){
                        disabled += dataSet[0] + "\n";
                    }
                }
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
        finally{
            JOptionPane.showMessageDialog(null, disabled);
        }
            
    }
    
    public static void viewSameLocation(SLinkedList list, Employee test){
        SNode node = list.getHead().getNext();
        
       // String data = "***Employees in same Location***\n";
        SLinkedList sameLocation = new SLinkedList();
        
        while(node != null){
         
            if(node.getData() instanceof Employee && 
                    node.getData().getLocation().getLocationId() == test.getLocation().getLocationId()){
                
               SNode node2 = new SNode(node.getData(),null);
 
               sameLocation.add(node2);
               System.out.println(sameLocation.getSize());
            }
              
            node = node.getNext();
        }
    
        reports(sameLocation);
    }
    public static void enableUser(String userID){
        try {
        
        BufferedReader file = new BufferedReader(new FileReader("database.txt"));
        String line;
        StringBuffer inputBuffer = new StringBuffer();
        

        while ((line = file.readLine()) != null) {
            String[] data = line.split(",");
            if(data[0].equals(userID)){
                line = line.replace("disabled", "enabled");
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
      finally{
            JOptionPane.showMessageDialog(null, "Account is now Enabled");
        }
    }
    
    public static void manageUser(SLinkedList list, String userID){
        SNode node = list.getHead().getNext();
        String data = "";
   
        
        while(node != null){
            data += node.getData().getId() + "\n";
            System.out.println(data + " \n\n");
            node = node.getNext();
        }
                
        /*
        while(node != null){
            data += node.getData().getId() + "\n";
            node = node.getNext();
        }
        
        */
        
        
        data += "\n****Admin****";
        
        
        String Id = JOptionPane.showInputDialog(data,"Enter ID to view profile");
            getProfile(Id, list);
        
    }
    
    /**
     * Gets the user profile and gives the admin options, that include enable or
     * disable account and reset user password. 
     */
    
    public static void getProfile(String userID, SLinkedList list){
        SNode node = list.getHead();
        String profile = "";
        
        while(node != null){
            if(node.getData().getId().equals(userID)){
                profile = node.getData().toString();
            }
            node = node.getNext();
        }
        
        String[] choices = {"Enable Account","Disable Account","Reset Password"};
        profile += "\n";
        profile += "\n**** Admin Options ****";
        
        String choice = (String) JOptionPane.showInputDialog(null, profile,
        "Manage User", JOptionPane.QUESTION_MESSAGE, null, 
        choices, 
        choices[0]);
        
        if(choice.equalsIgnoreCase(choices[0])){
            enableUser(userID);
        }
        else if(choice.equalsIgnoreCase(choices[1])){
            disableUser(userID);
        }
        else if(choice.equals(choices[2])){
            resetPassword(userID);
        }
        else{
            JOptionPane.showMessageDialog(null, "Invalid");
        }
        
     
    }
    
    /**
     Searches text file for User ID, if match is found resets password. 
     */
    
    public static void resetPassword(String userID){
        try {
        
        BufferedReader file = new BufferedReader(new FileReader("database.txt"));
        String line;
        StringBuffer inputBuffer = new StringBuffer();
        

        while ((line = file.readLine()) != null) {
            String[] data = line.split(",");
            if(data[0].equals(userID)){
                line = line.replace(data[1], "password");
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
    finally{
        JOptionPane.showMessageDialog(null, "Password is Reset");
        }
        
        
    }
    
    
    
      
}