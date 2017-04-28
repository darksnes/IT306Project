/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeetimekeepingapplication;

/**
 *
 * @author Dave
 */
public class Admin extends Employee {
    
    private String typeKey;
    
    public Admin(){
        
    }
    public Admin(String id, String password, String firstName, String lastName, double hoursWorked, int location, String address,String status,String typeKey){
        super(id,  password,  firstName,  lastName,  hoursWorked,  location,  address, status, typeKey);
        
    }
    public Admin(String locationId){
        //super(locationId);
    }
    
    //overridden setTypeKey method
    public void setTypeKey(){
        this.typeKey = "3";
    }
    
    public String getTypeKey(){
        return typeKey;
    }
    
    private String adminLoginId;

}
