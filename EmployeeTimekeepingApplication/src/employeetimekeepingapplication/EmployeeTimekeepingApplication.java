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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
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
    
    /**
     * The loginMenu is a void method that presents the user with a dialog box containing a
     * user input field for username, then password. It also contains a exit button
     * to close out and a login button. When the user chooses to login, an employee object is returned 
     * and then checked to see what specific instance is it off. Based on the instance, an appropriate menu 
     * function is called. Method repeats until user chooses to exit. 
     */
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
    
    /**
     * The sort method accepts a linkedList parameter and returns a sorted linked list using
     * selection sort. The sorting criteria is the last name of the employees in the linked list.
     * Sorting is done in ascending order.
     */
    
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
     
     /**
      * ManagerMenu is a void method that accepts a linked List and an manager object as parameters. 
      * Purpose of this method is to display a manager menu option to the user and acquire the users chosen
      * option. Based on the user option, its corresponding method is called. The manager object is passed on to those
      * methods, to facilitate particular function of that manager. 
      */
     
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

    /**
      * employeeMenu is a void method that accepts a linked List and an employee object as parameters. 
      * Purpose of this method is to display a employee menu option to the user and acquire the users chosen
      * option. Based on the user option, its corresponding method is called. The employee object is passed on to those
      * methods, to facilitate particular function of that employee.  
     */
    
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
    
    /**
     * The reports is a void method that accepts a linkedList as a parameter. The purpose of the method is to provide 
     * a manager with a sorting options menu. Based on the chosen option, its corresponding method is called. The linkedList
     * parameter is passed to those corresponding methods. 
     */
    
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
    /**
     * FileToList is a void method, that accepts a linked list as parameter. The purpose of this method is to create Employee
     * objects from each line in the text file, and add to the linked list. The method uses Buffered reader to read data from
     * the text file. Based on certain trigger number at the end of each text file, the object can be instantiated as either Admin
     * employee or Manager. 
     */
    
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
    /**
     * AddEmployee method is a void method, that accepts two parameters, linked list and choice. The choice is used to 
     * determine the which subclass object to initialize. The objects and the list are then passed as parameters to 
     * createEmployee method. The objects is also added to the linked list. 
     */
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
        
        createEmployee(employee,list);        
        SNode node = new SNode(employee,null);
        list.add(node);
    }
    /**
     * createEmloyee is a void method. THe purpose of this method is to provide details(fill out attributes) 
     * from user input to fully create an employee object. The writeToFile method is called after object has been
     * successfully filled out. The write to file method add the object to the text file. 
     * @param employee
     * @param list 
     */
    
    private static void createEmployee(Employee employee,SLinkedList list){
        
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
        employee.setID(list);
        JOptionPane.showMessageDialog(null,"****\nYour user ID is: " + employee.getId() + "\n****\n\nwrite this down!!");
        
        
        String password = "";
         
               
        do{
             password = getHiddenPassWord();
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
    /**
     * addAdress is a void method, that fill out attributes of an address object, from user input. 
     * @param employee 
     */
    private static void addAddress(Employee employee){
        boolean valid = false;
        
        do{
            valid = employee.getAddress().setStreet(JOptionPane.showInputDialog("Enter Street name"));
            if(!valid){
                JOptionPane.showMessageDialog(null, "Invalid cannot be empty. ");
            }
        }while(!valid);
        
        valid = false;
        
        do{
            valid = employee.getAddress().setCity(JOptionPane.showInputDialog("Enter City name"));
            if(!valid){
                JOptionPane.showMessageDialog(null, "Invalid cannot be empty. ");
            }
        }while(!valid);
        
        valid = false;
        
        do{
            valid = employee.getAddress().setAptNum(JOptionPane.showInputDialog("Enter apartment number"));
        }while(!valid);
        
        valid = false;
        
        do{
            valid = employee.getAddress().setState(JOptionPane.showInputDialog("Enter State name: "));
        }while(!valid);
        
        valid = false;
        
        do{
            valid = employee.getAddress().setZipCode(JOptionPane.showInputDialog("Enter Zip Code"));
        }while(!valid);
        
    }
    /**
     * addLocation is a void method that fill out attributes of a location object from user input. 
     * @param employee 
     */
    
    private static void addLocation(Employee employee){
        int id = locationMenu();
        employee.getLocation().setLocation(id);
    }
    /**
     * locationMenu returns a int value.  The purpose of this method is to provide user with a location menu, 
     * and return their choice. 
     * @return 
     */
    
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
    /**
     * checkFilExists return a boolean value. The purpose of this method is to check whether the chosen text file to store all 
     * the data exits. Return the appropriate value, depending on whether the file exists or not. 
     * @return 
     */
    
    private static boolean checkFileExists(){
        String fileName = "database.txt";
        File f = new File(fileName);
        boolean check = f.exists();

        
        return check;
    }
    /**
     * createFile is a void method. The purpose of this method is to create a text file, if the chosen text file does not 
     * exist.
     */
    
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
    
  /**
   * The purpose of the login method is to return employee object of the specified user id if the login is 
   * a success. If the login fails, the method displays the particular error to the user. The method also checks to 
   * see the number of login attempts, if the login exceeds 3 attempts, the method calls an other method which disables
   * the account associated with the user ID. 
   * @param userID
   * @param password
   * @param list
   * @return 
   */
    
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
    
    /**
     * checkIfFileEmpty is a void method. THe purpose of this method is to check if the contents of the specified
     * file is empty. If empty the method writes to the file a default admin account. 
     * @param filepath 
     */
    
    
    private static void checkIfFileEmpty(String filepath){
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
    /**
     * mainMenu method displays the main menu to the user, which contains create account or login to existing account.
     * The method return the choice of the user. 
     * @return 
     */
      
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
    /**
     * createUserMenu method provides provides the user option to create various employee accounts. 
     * @param list
     * @param test 
     */
    
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
    /**
     * adminMenu method provides the logged in admin user a menu option, with task that an admin account has right
     * to do. The chosen task is then passed onto their specified methods. 
     * @param list
     * @param test 
     */
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
    /**
     * printAll method purpose is to print all users that have so far registered with an account. 
     * @param list 
     */

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
    
    /**
     * The writeTofile method accepts an employee object as a parameter. The employee object is then written to a specified file using
     * the printWriter class. 
     * @param filepath
     * @param employee 
     */

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
    /**
     * searchTextFile method purpose is to search the text file line that matches the given parameters, user and password. If a 
     * match is found for both parameters, the text file returns the specified status code value. If the user name is matched, 
     * but password does not, its specified status code is returned. If both don't match, its specified status will be returned. 
     * @param userID
     * @param password
     * @return 
     */
        
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
     
     /**
      * sortByHours method sorts the linked listed in the parameter. THe sort is based on selection sort algorithm.
      * The sort criteria is the number of hours worked by each employee object. Sort is done in ascending order. 
      * @param list
      * @return 
      */
     
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
     
    /**
     * updateInformationMethod gets the user input and updates certain attributes of the 
     * employee object in its parameters. The method user buffer reader to read data from the file
     * and appends the data to a string buffer. The data in the buffer is added to string, then the 
     * replace method is used to update the data to the one provided by the user. Then the file is 
     * over written with the new data. 
     * @param test 
     */
     
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
    
    /**
     * disableUser method find the userID given in its parameter, within the text file, then replaces the enabled keyword 
     * in that specific line to disabled. The user then becomes disabled, when the data is read. 
     * @param userID 
     */
    
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
    /**
     * 
     * @param employee 
     */
    
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
    
    public static String getHiddenPassWord(){
        JPasswordField password = new JPasswordField(10);
        JLabel label = new JLabel("Enter password: ");
        JPanel panel = new JPanel();
        panel.add(label);
        panel.add(password);
        
        JOptionPane.showConfirmDialog(null, panel, "Enter Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (JOptionPane.OK_OPTION == 0) {
            String passWord = new String(password.getPassword());
            return passWord;
        }
        
        return "";
    }
    
    
    
      
}