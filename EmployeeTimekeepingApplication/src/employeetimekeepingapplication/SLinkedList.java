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
			SNode curr = head; 
			//if there is only one node in the linkedlist:
			if(!curr.hasNext()) {
				curr.setNext(node);
				//curr= node; 
			}
			
			//if there are multiple nodes inside the linkedlist
			else{
				//we need a loop
				while(curr.hasNext()){
					curr = curr.getNext(); 
				}
				curr.setNext(node);
			}
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
	
	public int getSize(){ 
            return size;
        }
        public SNode getHead(){
            return head;
        }

	

}
