package entity;

import java.util.List;

/**
 * This is used to stored the detailed information of a specific event
 */
public class Event {
	
	private int id;              // unique numeric identifier of event
	private int x;               // co-ordinate x
	private int y;               // co-ordinate y
	private List<Float> tkts_price_list; // a list of different ticket price for this event 

	/**
	 * Constructor of event
	 * @param id
	 * @param x
	 * @param y
	 * @param tkts_price_list
	 */
	public Event(int id, int x, int y, List<Float> tkts_price_list) {
		super();
		this.id = id;
		this.x = x;
		this.y = y;
		this.tkts_price_list = tkts_price_list;
	}
	
	//getters and setters of each field
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public List<Float> getTkts_price_list() {
		return tkts_price_list;
	}
	public void setTkts_price_list(List<Float> tkts_price_list) {
		this.tkts_price_list = tkts_price_list;
	}

}
