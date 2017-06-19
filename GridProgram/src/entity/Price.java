package entity;
/**
 * (1) If two events have the same distance, this class will store their ids and cheapest ticket price
 * (2) Implements Comparable to use Collections to automatically rank their price
 */
public class Price implements Comparable<Price>{
	
	private int id;           // unique numeric identifier of event
	private Float price;      // cheapest price of this event
	
	public Price(int id, Float price) {
		super();
		this.id = id;
		this.price = price;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}

	@Override  
    public int compareTo(Price p) {  
        return this.price.compareTo(p.getPrice());  
    }  

}
