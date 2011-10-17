package cs9322.cafe.resources;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
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
	
	@Path("new")
	@PUT
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public void newPayment(
			@FormParam("id") String orderID,
			@FormParam("type") String type,
			@FormParam("amount") String amount,
			@FormParam("cardNumber") String cardNumber,
			@Context HttpServletResponse servletResponse
	) throws IOException {
        Payment p = new Payment();
        p.setId(orderID);
        p.setAmount(amount);
        p.setCardNumber(cardNumber);
        p.setType(type);
        PaymentsDao.instance.getPayments().put(p.getId(), p);
		PaymentsDao.instance.writePayments();
		
		URI uri = uriInfo.getAbsolutePathBuilder().path(p.getId()).build();
		Response.created(uri).build();
	}
}
