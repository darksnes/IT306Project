package employeetimekeepingapplication;

public class SNode {
	//instance variables
	private Employee data;
	private SNode next;
	//constructor
	public SNode(Employee data, SNode next){
		this.data = data;
		this.next = next;
	}
	//accessors/mutators
	public Employee getData(){
		return this.data;
	}
	public SNode getNext(){
		return this.next;
	}
	public void setData(Employee data){
		this.data = data;
	}
	public void setNext(SNode next){
		this.next = next;
	}
	public boolean hasNext(){
		if(this.next == null){
			return false;
		}
		else{
			return true;
		}
	}
	
	
}
