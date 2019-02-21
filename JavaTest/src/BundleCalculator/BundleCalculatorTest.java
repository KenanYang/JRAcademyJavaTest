package BundleCalculator;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map.Entry;

public class BundleCalculatorTest {
	
	private static ArrayDeque<Double[]> imageQueue = new ArrayDeque<>();
	private static ArrayDeque<Double[]> audioQueue = new ArrayDeque<>();
	private static ArrayDeque<Double[]> videoQueue = new ArrayDeque<>();
	
	private static HashMap<Double, Double> imageMap =  new HashMap<>();
	private static HashMap<Double, Double> audioMap =  new HashMap<>();
	private static HashMap<Double, Double> videoMap =  new HashMap<>();
	
	static {
		imageQueue.addLast(new Double[] {5.0, 450.0});
		imageQueue.addLast(new Double[] {10.0, 800.0});
		
		audioQueue.addLast(new Double[] {3.0, 427.5});
		audioQueue.addLast(new Double[] {6.0, 810.0});
		audioQueue.addLast(new Double[] {9.0, 1147.5});
		
		videoQueue.addLast(new Double[] {3.0, 570.0});
		videoQueue.addLast(new Double[] {5.0, 900.0});
		videoQueue.addLast(new Double[] {9.0, 1530.0});
		
		imageMap.put(5.0, 450.0);
		imageMap.put(10.0, 800.0);
		
		audioMap.put(3.0, 427.5);
		audioMap.put(6.0, 810.0);
		audioMap.put(9.0, 1147.5);
		
		videoMap.put(3.0, 570.0);
		videoMap.put(5.0, 900.0);
		videoMap.put(9.0, 1530.0);
		
		
	}
	

	public static void main(String[] args) {		
		printResult(10, "IMG");
		printResult(15, "Flac");
		printResult(13, "VID");
	}
	
	private static ArrayDeque<Double[]> chooseQueue(String str){
		ArrayDeque<Double []> ret = null;
		if ("IMG".equals(str)) ret = imageQueue.clone();
		if ("Flac".equals(str)) ret = audioQueue.clone();
		if ("VID".equals(str)) ret = videoQueue.clone();			
		return ret;
	}
	
	private static HashMap<Double, Double> chooseMap(String str){
		HashMap<Double, Double> ret = null;
		if ("IMG".equals(str)) ret = new HashMap<Double, Double>(imageMap);
		if ("Flac".equals(str)) ret = new HashMap<Double, Double>(audioMap);
		if ("VID".equals(str)) ret = new HashMap<Double, Double>(videoMap);	
		return ret;
	}
	
	public static void printResult(int totalNumber, String mediaType) {
		ArrayDeque<Double[]> database = chooseQueue(mediaType);
		HashMap<Double, Double> mapType = chooseMap(mediaType);
		BundleCalculator bc = new BundleCalculator();
		HashMap<Double, HashMap<Double, Integer>> comb = bc.calculator(totalNumber, mediaType, database);
		if(comb != null) {
			for(Entry<Double, HashMap<Double, Integer>> firstLayer : comb.entrySet()) {
				double total = firstLayer.getKey();
				HashMap<Double, Integer> detail = firstLayer.getValue();
				if(total < Double.MAX_VALUE) {
					System.out.println(totalNumber + "\t" + mediaType + "\t$" + total);
					for(Entry<Double, Integer> secondLayer : detail.entrySet()) {
						double buddleType = secondLayer.getKey();
						int quantity = secondLayer.getValue();
						System.out.println("\t" + quantity + " x " + (int)buddleType + "\t$" + mapType.get(buddleType));
					}
				} else {
					System.out.println("No bundle combination found for " + mediaType);
				}				
			}	
		} else {
			System.out.println(mediaType + "is not a right input type");
		}
	}

}
