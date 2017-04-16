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
public class Manager extends Employee {
    
   private String managerLoginId;
   private String typeKey;
   public Manager(){
       
   }
   public Manager(String locationId){
       //super(locationId);
   }

    public Manager(String id, String password, String firstName, String lastName, double hoursWorked, int location, String address,String typeKey){
        super(id,  password,  firstName,  lastName,  hoursWorked,  location,  address, typeKey);
        
    }

   public String generateReport(SLinkedList list){
       
       int locationId = this.getLocation().getLocationId();
       String report = "";
       
       //no employees in program
       if(list.isEmpty()){
           return "there are no employees in the application";
       }
       SNode curr = list.getHead();
       //only 1 node in list and it is in the same location as manager
       if(curr.getData().getLocation().getLocationId() == locationId){
               report += curr.getData();      
       }
       curr = curr.getNext();
       
       //there are multiple nodes 
       while(curr.getNext()!= null){
           if(curr.getData().getLocation().getLocationId() == locationId){
                report += curr.getData();
                curr = curr.getNext();
           }
       }
       
       
       return report;

      
   } 
   public void setTypeKey(){
       this.typeKey = "2";
   }
       public String getTypeKey(){
        return typeKey;
    }
   
}
