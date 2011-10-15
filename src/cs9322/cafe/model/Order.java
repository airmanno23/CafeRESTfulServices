package cs9322.cafe.model;

import javax.xml.bind.annotation.XmlRootElement;

import cs9322.cafe.menu.OrderMenu;

@XmlRootElement
public class Order {

    private String id;
    private String type;
    private String additions;
    private String cost;
    private String status;
    private static OrderMenu _menu = OrderMenu.instance;
    public static int _id = 1;

    public Order(){

    }
    
    public Order (String type, String additions){
        this.id = String.valueOf(_id++);
        this.type = type;
        this.additions = additions;
        this.cost = String.valueOf((_menu.getPrice(type) + _menu.getPrice(additions)));
        
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

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}
}