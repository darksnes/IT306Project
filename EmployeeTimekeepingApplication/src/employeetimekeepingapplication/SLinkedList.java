package employeetimekeepingapplication;

public class SLinkedList {
	private SNode head; 
	private int size; 
	
	public SLinkedList(){
            size = 0;
        }
	
	public boolean isEmpty() {
            if(head==null){
                return true;
            }
            else{
                 return false; 
            }
        }
        
	public void add(SNode node){
		size++;
		if(head==null) head = node; //if the linkedlist is empty
		else{
			node.setNext(head);
                        head = node;
		}
		
	}
	
	public Employee remove(){ //return and remove the last element inside the linkedlist
		if(isEmpty()){
                    return null;
                }
                
                size--;
		SNode curr = head; 
		SNode previous = head; 
		//if there is only one element in the linkedlist:
		if(!curr.hasNext()) {
			Employee data = curr.getData(); 
			head= null; 
			return data; 
		}
		else{ //multiple elemets inside the linkedlist
			while(curr.hasNext()){
				previous = curr; 
				curr = curr.getNext(); 
			}
			Employee data = curr.getData(); 
			previous.setNext(null);
			curr = null; 
			return data; 
		}
		
		
	}
        
        public Employee getSpecific(String userID){  //gets the object that has same userID as login.
            SNode node = head;
            
            while(node != null){
                if(node.getData().getId().equals(userID)){
                    return node.getData();
                }
                node = node.getNext();
            }
            
            return null;
        }
	
	public int getSize(){ 
            return size;
        }
        public SNode getHead(){
            return head;
        }
<<<<<<< HEAD
        
        public void removeSpecific(SNode node){
            
        }
=======
        public Employee getSpecific(String userID){  //gets the object that has same userID as login.
            SNode node = head;
            
            while(node != null){
                if(node.getData().getId().equals(userID)){
                    return node.getData();
                }
                node = node.getNext();
            }
            
            return null;
        } 
        
>>>>>>> 5b72f7ad97027e43260b0db744d5b3c4216bc11a

	

}
