package cs9322.cafe.resources;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import cs9322.cafe.dao.PaymentsDao;
import cs9322.cafe.model.Payment;

public class PaymentResource {	
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String orderID;
	
	public PaymentResource(UriInfo uriInfo, Request request, String orderID) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.orderID = orderID;
	}
	
	// Produces XML or JSON output for a client 'program'			
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Payment getOrder() {
		Payment p = PaymentsDao.instance.getPayments().get(orderID);
		if(p == null)
			throw new RuntimeException("GET: Payment with " + orderID +  " not found");
		return p;
	}
	
	// Produces HTML for browser-based client
	@GET
	@Produces(MediaType.TEXT_XML)
	public Payment getOrderHTML() {
		Payment p = PaymentsDao.instance.getPayments().get(orderID);
		if( p==null )
			throw new RuntimeException("GET: Payment with " + orderID +  " not found");
		return p;
	}
	
	@PUT
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response newPayment(
			@FormParam("type") String type,
			@FormParam("amount") String amount,
			@FormParam("cardNumber") String cardNumber,
			@Context HttpServletResponse servletResponse
	) throws IOException {
        Response rsp;
		Payment p = new Payment();
        p.setId(orderID);
        p.setAmount(amount);
        p.setCardNumber(cardNumber);
        p.setType(type);
        PaymentsDao.instance.getPayments().put(p.getId(), p);
		PaymentsDao.instance.writePayments();
		
		String results = "{\"amount\":\"" + p.getAmount() + "\",\"cardNumber\":\"" 
		+ p.getCardNumber()+ "\",\"id\":\"" + p.getId() + "\",\"type\":\""
		+ p.getType() + "\"}";
		rsp = Response.ok(results).build();
		
		return rsp;
	}

}
