package entity;

/**
 * (1) If an event is one of the five closest events of the input location,
 *     this class will store its id, cheapest ticket price and corresponding distance
 * (2) Implements Comparable to use Collections to automatically rank their distance
 */
public class Result implements Comparable<Result>{
	
	private int id;                 // unique numeric identifier of event
	private float cheapest_price;   // cheapest price of this event
	private Integer distance;       // distance from this event to the given location(input)
	
	public Result(int id, Integer distance) {
		super();
		this.id = id;
		this.distance = distance;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getDistance() {
		return distance;
	}
	public void setDistance(Integer distance) {
		this.distance = distance;
	}
	public float getCheapest_price() {
		return cheapest_price;
	}
	public void setCheapest_price(float cheapest_price) {
		this.cheapest_price = cheapest_price;
	}

	@Override  
    public int compareTo(Result r) {  
        return this.distance.compareTo(r.getDistance());  
    }  

}
