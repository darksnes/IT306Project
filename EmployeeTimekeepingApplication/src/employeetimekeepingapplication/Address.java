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
public class Address {
    
    //**ATTRIBUTES**
    
    private String street;
    private String aptNum;
    private String city;
    private String state;
    private String zipCode;
    
    
    //**BEHAVIORS**
    
    //accessors
    public String getStreet(){
        return street;
    }
    public String getAptNum(){
        return aptNum;
    }
    public String getCity(){
        return city;
    }
    public String getState(){
        return state;
    }
    public String getZipCode(){
        return zipCode;
    }
    //mutators
    public void setStreet(String street){
        this.street = street;
    }
    public void setAptNum(String aptNum){
        this.aptNum = aptNum;
    }
    public void setCity(String city){
        this.city = city;
    }
    public void setState(String state){
        this.state = state;
    }
    public void setZipCode(String zipCode){
        this.zipCode = zipCode;
    }
    
    public String toString(){
        return "\nCity: " + this.city + "\nState: " + this.state;
    }
}
