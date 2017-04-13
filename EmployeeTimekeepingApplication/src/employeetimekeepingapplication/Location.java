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
public class Location {
    //**ATTRIBUTES**
    
    private String locationId;
    private String title;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    
    
    //constructor
    
    public Location(){
        
    }
    public Location(String locationId){
        this.locationId = locationId;
        setLocation(locationId);
    }
    
    //**BEHAVIORS**
    //accessors
    public String getLocationId(){
        return locationId;
    }
    public String getTitle(){
        return title;
    }
    public String getStreet(){
        return street;
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
    
    
    public void setLocation(String locationId){//location id is a String of either 1,2, or 3.
        if(locationId.equals('1')){
            this.title = "Factory 415";
            this.street = "123 Fake Street";
            this.city = "Nowhere";
            this.state = "NN";
            this.zipCode = "00111";
        }
        else
            if(locationId.equals('2')){
                this.title = "Research 221";
                this.street = "123 Faker Street";
                this.city = "Still Somewhere";
                this.state = "NN";
                this.zipCode = "00112";
            }
        else
             if(locationId.equals('3')){
                this.title = "Corporate 001";
                this.street = "123 Non-Fake Street";
                this.city = "Somewhere";
                this.state = "IL";
                this.zipCode = "01010"; 
                
              }
        }
       
}
