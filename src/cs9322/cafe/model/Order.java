package cs9322.cafe.model;

import javax.xml.bind.annotation.XmlRootElement;

import cs9322.cafe.menu.OrderMenu;

@XmlRootElement
public class Order {

    private String id;
    private String type;
    private String additions;
    private String cost;
    private String paidStatus;	//1 means unpaid, 2 means paid
    private String baristaStatus; //1 means not prepared, 2 means prepared, 3 means released
    private static OrderMenu _menu = OrderMenu.instance;
    public static int _id = 1;

    public Order(){

    }
    
    public Order (String type, String additions){
        this.id = String.valueOf(_id++);
        this.type = type;
        this.additions = additions;
        this.cost = String.valueOf((_menu.getPrice(type) + _menu.getPrice(additions)));
        this.setPaidStatus("1");
        this.setBaristaStatus("1");
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getCost() {
        return cost;
    }
    
    public void setCost(String cost) {
        this.cost = cost;
    }
    
	public void setAdditions(String additions) {
		this.additions = additions;
	}
	
	public String getAdditions() {
		return additions;
	}

	public void setBaristaStatus(String baristaStatus) {
		this.baristaStatus = baristaStatus;
	}

	public String getBaristaStatus() {
		return baristaStatus;
	}

	public void setPaidStatus(String paidStatus) {
		this.paidStatus = paidStatus;
	}

	public String getPaidStatus() {
		return paidStatus;
	}

}