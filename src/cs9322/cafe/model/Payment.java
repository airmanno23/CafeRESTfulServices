package cs9322.cafe.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Payment {

    private String id;
    private String type;
    private String cardNumber;
    private String amount;

    public Payment(){
    	this.cardNumber = "";
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

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
    
}