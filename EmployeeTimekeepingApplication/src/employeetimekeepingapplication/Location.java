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
    
    private int locationId;
    private String title;
    private String street;
    private String city;
    private String state;
    private String zipCode;
    
    
    //constructor
    
    public Location(){
        
    }

    
    //**BEHAVIORS**
    //accessors
    public int getLocationId(){
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
    
    
    public void setLocation(int locationId){//location id is a String of either 1,2, or 3.
        if(locationId == 1){
            this.locationId = locationId;
            this.title = "Factory 415";
            this.street = "123 Fake Street";
            this.city = "Nowhere";
            this.state = "NN";
            this.zipCode = "00111";
        }
   
        if(locationId == 2){
                this.locationId = locationId;
                this.title = "Research 221";
                this.street = "123 Faker Street";
                this.city = "Still Somewhere";
                this.state = "NN";
                this.zipCode = "00112";
            }
   
        if(locationId == 3){
                this.locationId = locationId;
                this.title = "Corporate 001";
                this.street = "123 Non-Fake Street";
                this.city = "Somewhere";
                this.state = "IL";
                this.zipCode = "01010"; 
                
              }
        
    }  
    public String toString(){
        return "location ID: "+locationId; 
    }
}