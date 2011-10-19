package cs9322.cafe.resources;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import cs9322.cafe.dao.OrdersDao;
import cs9322.cafe.model.Order;


// will map xxx.xxx.xxx/rest/books
@Path("/orders")
public class OrdersResource {
	// Allows to insert contextual objects into the class, 
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;

	// Return the list of books to the user in the browser
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Order> getOrdersBrowser() {
		List<Order> bs = new ArrayList<Order>();
		bs.addAll( OrdersDao.instance.getOrders().values() );
		return bs; 
	}
	
	// Return the list of books for client applications/programs
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public List<Order> getOrders() {
		List<Order> bs = new ArrayList<Order>();
		bs.addAll( OrdersDao.instance.getOrders().values() );
		return bs; 
	}
	
	@GET
	@Path("count")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCount() {
		int count = OrdersDao.instance.getOrders().size();
		return String.valueOf(count);
	}
	
	@POST
	@Produces({MediaType.TEXT_HTML, MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.TEXT_XML})
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String newOrder(
			@FormParam("type") String type,
			@FormParam("additions") String additions,
			@Context HttpServletResponse servletResponse
	) throws IOException {
		Order o = new Order(type, additions);
		OrdersDao.instance.getOrders().put(o.getId(), o);
		OrdersDao.instance.writeOrders();
		
		URI uri = uriInfo.getAbsolutePathBuilder().path(o.getId()).build();
		Response.created(uri).build();
		
		String orderURI = uri.toString();
		String paymentURI = orderURI.replace("orders", "payments");
		String results = "{\"additions\":\"" + o.getAdditions() + "\",\"baristaStatus\":\"" 
		+ o.getBaristaStatus()+ "\",\"cost\":\"" + o.getCost() + "\",\"id\":\""
		+ o.getId() + "\",\"paidStatus\":\"" + o.getPaidStatus() + "\",\"type\":\""
		+ o.getType() + "\"}";
		
		return results + "#" + orderURI + "#" + paymentURI;
	}
	
	
	// Important to note that this Path annotation define.
	// This will match xxx.xxx.xxx/rest/books/{book}
	// It says 'the thing that comes after books/ is a parameter
	// and it is passed to the OrderResource class for processing
	// e.g., http://localhost:8080/cs9322.simple.rest.books/rest/books/3
    // This matches this method which returns OrderResource.
	@Path("{order}")
	public OrderResource getOrder(
			@PathParam("order") String id) {
		return new OrderResource(uriInfo, request, id);
	}
	
}