package cs9322.cafe.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cs9322.cafe.model.Payment;


public enum PaymentsDao {
	instance;
	
    private Map<String, Payment> contentStore = new HashMap<String, Payment>();

    private PaymentsDao() {

    	try {
			FileReader fw = new FileReader("Workspace/CafeRESTfulServices/PaymentDB.txt");
			BufferedReader br = new BufferedReader(fw);
			String temp = br.readLine();
			while(temp != null && temp.length() != 0) {
				String[] fields = temp.split(",");
				Payment p = new Payment();
				p.setId(fields[0]);
				p.setType(fields[1]);
				p.setCardNumber(fields[2]);
				p.setAmount(fields[3]);
				contentStore.put(p.getId(), p);
				temp = br.readLine();
			}
			br.close();
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public Map<String, Payment> getPayments() {
        return contentStore;
    }
    
    public void writePayments() {
    	try {
			FileWriter fw = new FileWriter("Workspace/CafeRESTfulServices/PaymentDB.txt", false);
			Collection<Payment> c = contentStore.values();
			Iterator<Payment> it = c.iterator();
			while(it.hasNext()) {
				Payment p = it.next();
				fw.append(p.getId() + "," + p.getType() + "," + p.getCardNumber() + "," + p.getAmount() + "\n");
			}
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}