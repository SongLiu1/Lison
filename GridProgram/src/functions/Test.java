package functions;

import java.util.List;
import java.util.Scanner;

import entity.Result;
/**
 * Test class is used to provide a console UI to test the program
 */
public class Test {
	static int event_number = 10;           // how many events will be generated 

	public static void main(String[] args) {
		Functions.initial(event_number);      // randomly generate N events 
		System.out.println("System initialization.\n\nEvents list with details:");
		Functions.displayEvents();            // display the generated N events
		entry();                            // console UI entrance

	}
	
	/**
	 * Console UI entrance
	 */
	public static void entry(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("\nPlease input a co-ordinate in the form of 'x,y', e.g. 4,2"
				+ "\nNote: -10<=x,y<=+10, or input 'Q' to cancel");
		String input_string = scanner.next();       // input location (co-ordinate)
		for(;;){  // loop until user cancel the program
			if(input_string.indexOf(',') != -1 || input_string.split(",").length == 2){
				try{   // Exception handler of unexpected input data type
					int input_x = Integer.parseInt(input_string.split(",")[0]);
					int input_y = Integer.parseInt(input_string.split(",")[1]);
					if(input_x < -10 || input_x > 10 || input_y < -10 || input_y > 10){  // input out of world size
						System.out.println("Co-ordinate out of scale, please input x,y both between -10 and +10.");
					}
					else{  // Show the details of 5 closest events with their cheapest ticket price
						List<Result> result_list = Functions.findClosestEvents(input_x, input_y);
						System.out.println("5 Closest Events to (" + input_string + "):");
						for(Result result: result_list){
							System.out.print("Event " + result.getId() + " - Distance: " + result.getDistance());
							if(result.getCheapest_price() != 201)
								System.out.println(", $" + result.getCheapest_price());
							else {
								System.out.println(", no tickets left for this Event");
							}
							
						}
					}
					System.out.println("\nPlease input a co-ordinate in the form of 'x,y', e.g. 4,2"
							+ "\nNote: -10<=x,y<=+10, or input 'Q' to cancel");
				}
				catch(Exception e){
					System.out.println("Unexpected input format, please input again, or input 'Q' to cancel");
				}
			}
			else {  // user cancel or wrong input format
				if(input_string.equals("Q")){
					System.out.println("System canceled.");
					scanner.close();
					break;
				}
				else
					System.out.println("Unexpected input format, please input again, or input 'Q' to cancel");
			}  
			input_string = scanner.next();
		}
		
	}

}
