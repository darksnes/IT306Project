/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeetimekeepingapplication;
import java.util.Random;

/**
 *
 * @author Dave
 */
public class Employee {
    
    //**ATTRIBUTES**
    private String firstName;
    private String lastName;
    private String id;
    private String password;
    private int loginAttempts = 0;
    
    /* typeKey is a key that will be stored in the DB so when the DB is 
    written to file, the type of employee is known
    */
    private String typeKey;
    
    private double salary;
    private double hoursWorked;
    private boolean disabled;
    
    
    
    //embedded objects
    private Address address;
    private Location location;
    
    //**BEHAVIORS**
    
    //accesors

    public Employee(){
        this.address = new Address();
        this.location = new Location();
    }
        public Employee(String id, String password, String firstName, String lastName, double hoursWorked, int location, String address,String typeKey){
        this.location = new Location();
        this.address = new Address();
        this.id = id;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.hoursWorked = hoursWorked;
        this.location.setLocation(location);
        this.typeKey = typeKey;
        
        
        
    }
    public String getFirstName(){
        return firstName;
    }
    public String getLastName(){
        return lastName;
    }
    public String getId(){
        return id;
    }
    public Address getAddress(){
        return address;
    }
    public Location getLocation(){
        return location;
    }
    public String getPassword(){
        return password;
    }
    public double getSalary(){
        return salary;
    }
    public double getHoursWorked(){
        return hoursWorked;
    }
    public String getTypeKey(){
        return typeKey;
    }
    public int getLoginAttempts(){
        return loginAttempts;
    }
    
    //mutators
    public void setFirstName(String firstName){
        this.firstName = firstName + " ";
    }
    public void setLastName(String lastName){
        if(lastName.length()<3){
            lastName = lastName +  "aa";
        }
        this.lastName = lastName;
    }
    public void setPassword(String password){
        this.password = password;
    }
    public void setLocation(int locationKey){
        this.location.setLocation(locationKey);
    }
    
    public void setID(){
        Random rand = new Random();
        this.id = this.lastName.substring(0,2) + rand.nextInt(2000);
    }
    public void setID(String defaultID){
        this.id = defaultID;
    }
    public boolean setHoursWorked(int hoursWorked){
        if(hoursWorked >= 0 && hoursWorked < 100000 ){
                this.hoursWorked = hoursWorked;
                return true;
        }
        else{
            return false; 
        }
    
    }
    public void setSalary(double salary){
        this.salary = salary;
    }
    //method that will be overrideen
     public void setTypeKey(){
        this.typeKey = "1";
    }
    
    
    public void setTypeKey(String typeKey){
        
    }
    
    public void setLoginAttempts(){
        if(this.getLoginAttempts() < 3){
            this.loginAttempts++;
        }
    }


    
    
    public String toString(){
        return "\nID: " + id + "\nName: " + firstName + "" + lastName +  
                "\nHours Worked: " + hoursWorked + "\nSalary Earned: " + salary +"\n" + "Location: " + location.getLocationId();
                
                
    }
}
