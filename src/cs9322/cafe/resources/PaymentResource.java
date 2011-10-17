package cs9322.cafe.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
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
		System.out.println("in the get");
		Payment p = PaymentsDao.instance.getPayments().get(orderID);
		if(p == null)
			throw new RuntimeException("GET: Payment with " + orderID +  " not found");
		return p;
	}
	
	// Produces HTML for browser-based client
	@GET
	@Produces(MediaType.TEXT_XML)
	public Payment getOrderHTML() {
		System.out.println("in the get for html");
		Payment p = PaymentsDao.instance.getPayments().get(orderID);
		if( p==null )
			throw new RuntimeException("GET: Payment with " + orderID +  " not found");
		return p;
	}

}
