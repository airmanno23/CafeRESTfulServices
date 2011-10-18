package cs9322.cafe.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.UriInfo;

import cs9322.cafe.dao.PaymentsDao;
import cs9322.cafe.model.Payment;

@Path("/payments")
public class PaymentsResource {
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	// Return the list of books to the user in the browser
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Payment> getPaymentsBrowser() {
		List<Payment> paymentList = new ArrayList<Payment>();
		paymentList.addAll(PaymentsDao.instance.getPayments().values() );
		return paymentList; 
	}
	
	// Return the list of books for client applications/programs
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Payment> getPayments() {
		List<Payment> paymentList = new ArrayList<Payment>();
		paymentList.addAll(PaymentsDao.instance.getPayments().values() );
		return paymentList; 
	}
	
	@Path("{payment}")
	public PaymentResource getPayment(
			@PathParam("payment") String OrderID) {
		return new PaymentResource(uriInfo, request, OrderID);
	}
}
