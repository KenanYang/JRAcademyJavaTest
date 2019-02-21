package BundleCalculator;

import java.util.ArrayDeque;
import java.util.HashMap;


/**
 * Find the best bundle combination of input bundle
 * @author kenanyang
 *
 */
public class BundleCalculator {
	
	private HashMap<Double, Integer> record = null;
	private double totalPrice;
	

	
	public HashMap<Double, HashMap<Double, Integer>> calculator(int quantities, String types, ArrayDeque<Double[]> database) {
		if (database == null) return null;
		record = new HashMap<Double, Integer>();
		totalPrice = Double.MAX_VALUE;
		double currentPrice = 0;
		helper(database, quantities, currentPrice, new HashMap<Double, Integer>());
		HashMap<Double, HashMap<Double,Integer>> ans = new HashMap<Double, HashMap<Double,Integer>>();
		ans.put(totalPrice, record);
		return ans;
	}
	
	private void helper(ArrayDeque<Double[]> database, int quantities, double currentPrice, HashMap<Double, Integer> currentRecord){

		if(quantities == 0) {
			if (currentPrice < totalPrice) {
				totalPrice = currentPrice;
				record = currentRecord;
			}
		}
		if(quantities > 0 && (!database.isEmpty())) {
			Double[] qVPair = database.poll();
			HashMap<Double, Integer> tempRecord = new HashMap<Double, Integer>(currentRecord);
			if (qVPair[0] <= quantities) {
				if(tempRecord.get(qVPair[0]) == null) {
					tempRecord.put(qVPair[0], 1);
				} else {
					int freq = tempRecord.get(qVPair[0]);
					tempRecord.replace(qVPair[0], freq+1);
				}
				database.push(qVPair);
				helper(database.clone(), quantities-(int)qVPair[0].doubleValue(), currentPrice + qVPair[1], tempRecord);
											
			}
			database.poll();
			helper(database.clone(), quantities, currentPrice, new HashMap<Double, Integer>(currentRecord));
		}	
	}
}
