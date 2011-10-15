package cs9322.cafe.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cs9322.cafe.model.Order;


public enum OrdersDao {
	instance;
	
    private Map<String, Order> contentStore = new HashMap<String, Order>();

    private OrdersDao() {

    	try {
			FileReader fw = new FileReader("Workspace/CafeRESTfulServices/CafeDB.txt");
			BufferedReader br = new BufferedReader(fw);
			String temp = br.readLine();
			int maxId = 0;
			while(temp != null) {
				String[] fields = temp.split(",");
				int id = Integer.parseInt(fields[0]);
				if(maxId < id)
					maxId = id;
				Order o = new Order(fields[1], fields[2]);
				o.setId(fields[0]);
				o.setCost(fields[3]);
				contentStore.put(o.getId(), o);
				temp = br.readLine();
			}
			Order._id = maxId + 1;
			Order or = new Order("Latte", "extra shot");
			contentStore.put(or.getId(), or);
			br.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public Map<String, Order> getOrders() {
        return contentStore;
    }
    
    public void writeOrders() {
    	try {
			FileWriter fw = new FileWriter("Workspace/CafeRESTfulServices/CafeDB.txt", false);
			Collection<Order> c = contentStore.values();
			Iterator<Order> it = c.iterator();
			while(it.hasNext()) {
				Order o = it.next();
				fw.append(o.getId() + "," + o.getType() + "," + o.getAdditions() + "," + o.getCost() + "\n");
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}