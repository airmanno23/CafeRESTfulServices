package cs9322.cafe.menu;

import java.util.HashMap;
import java.util.Map;

public enum OrderMenu {
	instance;

    private Map<String, Double> contentStore = new HashMap<String, Double>();

    private OrderMenu() {

    	contentStore.put("Mocha", 4.5);
		contentStore.put("LongBlack", 2.5);
		contentStore.put("Latte", 5.0);
		contentStore.put("Cappuccino", 6.5);
		
		//for additions
		contentStore.put("skim milk", 1.0);
		contentStore.put("extra shot", 0.5);
    }
    public double getPrice(String key){
        if(key == null || key.length() == 0)
        	return 0.0;
        else
        	return contentStore.get(key);
    }
}
