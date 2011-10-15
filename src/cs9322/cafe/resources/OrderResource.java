package cs9322.cafe.resources;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import cs9322.cafe.dao.OrdersDao;
import cs9322.cafe.model.Order;


public class OrderResource {
	// Allows to insert contextual objects into the class, 
	// e.g. ServletContext, Request, Response, UriInfo
	@Context
	UriInfo uriInfo;
	@Context
	Request request;
	String id;
	
	public OrderResource(UriInfo uriInfo, Request request, String id) {
		this.uriInfo = uriInfo;
		this.request = request;
		this.id = id;
	}
	
	// Produces XML or JSON output for a client 'program'			
	@GET
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	public Order getOrder() {
		System.out.println("in the get");
		Order o = OrdersDao.instance.getOrders().get(id);
		if(o==null)
			throw new RuntimeException("GET: Order with" + id +  " not found");
		return o;
	}
	
	// Produces HTML for browser-based client
	@GET
	@Produces(MediaType.TEXT_XML)
	public Order getOrderHTML() {
		System.out.println("in the get for html");
		Order o = OrdersDao.instance.getOrders().get(id);
		if(o==null)
			throw new RuntimeException("GET: Order with " + id +  " not found");
		return o;
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response putOrder(JAXBElement<Order> o) {
		Order newo = o.getValue();
		return putAndGetResponse(newo);
	}
	
	@DELETE
	public void deleteOrder() {
		Order delo = OrdersDao.instance.getOrders().remove(id);
		OrdersDao.instance.writeOrders();
		if(delo==null)
			throw new RuntimeException("DELETE: Order with " + id +  " not found");
	}
	
	private Response putAndGetResponse(Order o) {
		Response res;
		if(OrdersDao.instance.getOrders().containsKey(o.getId())) {
			res = Response.noContent().build();
		} else {
			res = Response.created(uriInfo.getAbsolutePath()).build();
		}
		OrdersDao.instance.getOrders().put(o.getId(), o);
		OrdersDao.instance.writeOrders();
		return res;
	}
}