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
    
    //mutators
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public void setLastName(String lastName){
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
        this.id = this.firstName.substring(0,3) + rand.nextInt(2000);
    }

    
    
    public String toString(){
        return "\nID: " + id + "\nName: " + firstName + "" + lastName +  
                "\nHours Worked: " + hoursWorked + "\nSalary Earned: " + salary +"\n" + location;
                
                
    }
}
