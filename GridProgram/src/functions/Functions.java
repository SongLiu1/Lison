package functions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Vector;
import entity.Event;
import entity.Price;
import entity.Result;
/**
 * System initialization:
 * (1) Randomly generate events along with co-ordinate and a tickets price list
 */
public class Functions {
	static int current_id = 0;        // next id that available
	static HashMap<Integer, Event> event_map = new HashMap<Integer, Event>();  // a HashMap to store events with their id
	static Random r = new Random();   // random number generator 
	static Vector<String> v = new Vector<String>();  // a vector to store (x,y), in case some of them are in the same location

	/**
	 * Initial the random events with id, x, y and price list
	 * @param n to generate n events
	 */
	public static void initial(int n){
		for(int i = 0; i < n ;){
			int current_x = r.nextInt(21) - 10;
			int current_y = r.nextInt(21) - 10;   // generate random co-ordinate number between -10 and +10
			String coordinate = current_x + "," + current_y;
			// check whether the current co-ordinate is already occupied by another event
			if(!v.contains(coordinate)){
                // if not, create a new event (add it in event_map as well as vector)
				int number_of_price = r.nextInt(6);  // assume the maximum number of different-price tickets is 5
				List<Float> tkts_price_list;
				// generate tickets price list
				if(number_of_price == 0){
					tkts_price_list = null;
				}else{
					tkts_price_list = new ArrayList<Float>();
					for(int j = 0; j<number_of_price; j++){
						float price = (float)(r.nextInt(17001) + 3000) / 100;
//						float price = (float)(r.nextInt(20000) + 1) / 100;    // initial non-zero price requirement 
						tkts_price_list.add(price);
					}
				}   
				Event event = new Event(current_id, current_x, current_y, tkts_price_list);
				event_map.put(current_id, event);
                v.add(coordinate);
                i++;            // event index plus 1
                current_id++;   // current event id plus 1
            }
		}
 	}
	
	/**
	 * Display the details (id, x, y, price list) of generated events
	 */
	public static void displayEvents(){
		for(int i=0; i<current_id; i++){
			if(event_map.containsKey(i)){
				Event event = event_map.get(i);
				System.out.print("ID: " +event.getId() + ", Co-ordanate: (" + event.getX() + ", "+event.getY() + ")\n    ");
				List<Float> tkts = event.getTkts_price_list();
				if(tkts != null){
					System.out.print("Price List: ");
					for(Float price: tkts){
						System.out.print("$" + price + "; ");
					}
					System.out.print("\n");
				}
				else {
					System.out.println("No tickets left.");
				}
			}
		} 
	}
	
	/**
	 * Find the closest 5 events for the given location
	 * @param x given x co-ordinate (input)
	 * @param y given y co-ordinate (input)
	 * @return A list of 5 Result objects that contain id, cheapest ticket price and distance of the 5 closest events
	 */
	public static List<Result> findClosestEvents(int x, int y){
		List<Result> result_list = new ArrayList<>();  
		for(int i=0; i<current_id; i++){
			if(event_map.containsKey(i)){
				Event event = event_map.get(i);
				int manhattan_distance = Math.abs(x - event.getX()) + Math.abs(y - event.getY());
				result_list.add(new Result(event.getId(), manhattan_distance));
			}
		}   // create a result list, including all the events with their Manhattan distance from given location
		Collections.sort(result_list);   // rank the list, closer event ahead 
		
		// Same-distance events (sd events) issue processing
		// If it affects the result, collect the events that have the same distance from given location
		Set<Integer> equal_distance_set = new HashSet<Integer>();  // a set contains the ids of sd events 
		int equal_start_index = -1;   // list index where these sd events start (start from 0 and -1 means no sd events affects the final result) 
		for(int i=0; i<result_list.size()-1; i++){
			if(result_list.get(i).getDistance() == result_list.get(i+1).getDistance()){
				if(equal_start_index == -1)
					equal_start_index = i;
				equal_distance_set.add(result_list.get(i).getId());
				equal_distance_set.add(result_list.get(i+1).getId());
			}   // sd events found, update index and save event ids
			else{
				if(i <= 4){
					equal_start_index = -1;
					equal_distance_set.clear();
					if(i == 4){
						break;
					}
				}
				else
					break;
			}   //  sd events not found, update index and set, if loop index is larger than 4, break
		}  
		
		List<Result> results= new ArrayList<>();  // Final result list that used as return value
		if(equal_start_index != -1){
			List<Integer> equal_distance_list = new ArrayList<Integer>(equal_distance_set); 
			List<Price> price_list = new ArrayList<>();
			for(Integer id: equal_distance_list){
				price_list.add(new Price(id, getCheapestPrice(id)));
			}  // create a price (cheapest ticket price) list of sd events
			Collections.sort(price_list);  // rank it from lower to higher
			for(int i=0; i<equal_start_index; i++){
				Result result = result_list.get(i);
				result.setCheapest_price(getCheapestPrice(result.getId()));
				results.add(result);
			}  // add non-sd events to result list 
			for(int i=0; i< 5-equal_start_index; i++){
				Event event = event_map.get(price_list.get(i).getId());
				int manhattan_distance = Math.abs(x - event.getX()) + Math.abs(y - event.getY());
				Result result = new Result(event.getId(), manhattan_distance);
				result.setCheapest_price(price_list.get(i).getPrice());
				results.add(result);
			}  // add sd events to result list 
		}   // sd events issue found, re-organize result list
		else{
			for(int i=0; i<5; i++){
				Result result = result_list.get(i);
				result.setCheapest_price(getCheapestPrice(result.getId()));
				results.add(result);
			}
		}   // no sd events issue, construct result list directly
		return results;
	}
	
	/**
	 * Get the cheapest price of given event
	 * @param id: event id
	 * @return the cheapest ticket price of the event
	 */
	public static float getCheapestPrice(int id){
		// Default price 201 is higher than any possible price.
		// If an event returned 201 as cheapest price, this means there is no tickets left for this event 
		Float cheapest_price = (float)201;  
		List<Float> price_list = event_map.get(id).getTkts_price_list();
		if(price_list != null){
			for(Float f: price_list){
				if(f < cheapest_price)
					cheapest_price = f;
			}
		}
		return cheapest_price;
	}
	
}
