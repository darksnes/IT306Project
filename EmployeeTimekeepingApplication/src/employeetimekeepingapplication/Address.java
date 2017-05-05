/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package employeetimekeepingapplication;

import javax.swing.JOptionPane;

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
    public boolean setStreet(String street){
        if(!street.equals("")){
            this.street = street;
            return true;
        }
        
        return false;
    }
    public boolean setAptNum(String aptNum){
        if(validateApartmentNum(aptNum)){
            this.aptNum = aptNum;
            return true;
        }
        
        return false;
    }
    public boolean setCity(String city){
        if(!city.equals("")){
            this.city = city;
            return true;
        }
        
        return false;
    }
    public boolean setState(String state){
        if(validateState(state)){
            this.state = state;
            return true;
        }
        
        return false;
    }
    public boolean setZipCode(String zipCode){
        if(validateZip(zipCode)){
            this.zipCode = zipCode;
            return true;
        }
        
        return false;
    }
    
    //Special Purpose Methods
    private boolean validateZip(String zip){
        if(zip.length() != 5){
            JOptionPane.showMessageDialog(null,"Invalid. Zip has to have 5 digits");
            return false;
        }
        
        for(int i=0;i<zip.length();i++){
            if(!Character.isDigit(zip.charAt(i))){
                JOptionPane.showMessageDialog(null,"Invalid. Zip cannot have alphabets or characters.");
                return false;
            }
        }
        
        return true;
    }
    
    private boolean validateState(String state){
        if(state.equals("")){
            JOptionPane.showMessageDialog(null,"State cannot be empty.");
            return false;
        }
        
        for(int i=0;i<state.length();i++){
            if(!Character.isAlphabetic(state.charAt(i))){
                JOptionPane.showMessageDialog(null,"Invalid. Cannot contain numbers or characters.");
                return false;
            }
        }
        
        return true;
    }
    
    private boolean validateApartmentNum(String aprtNum){
        if(aprtNum.equals("")){
            JOptionPane.showMessageDialog(null,"Apartment number cannot be empty. If no apartment enter 0.");
            return false;
        }
        
        for(int i=0;i<aprtNum.length();i++){
            if(!Character.isDigit(aprtNum.charAt(i))){
                JOptionPane.showMessageDialog(null,"Invalid. Apartment number cannot have alphabets or characters.");
                return false;
            }
        }
        
        return true;
    }
    
    public String toString(){
        return String.format("(%s;%s;%s;%s,%s)", this.getStreet(),this.getAptNum(),this.getCity(),
                this.getState(),this.getZipCode());
    }
}
