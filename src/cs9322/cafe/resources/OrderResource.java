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
	
	OrdersDao dao = new OrdersDao();
	
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
		Order b = dao.getStore().get(id);
		if(b==null)
			throw new RuntimeException("GET: Order with" + id +  " not found");
		return b;
	}
	
	// Produces HTML for browser-based client
	@GET
	@Produces(MediaType.TEXT_XML)
	public Order getOrderHTML() {
		System.out.println("in the get for html");
		Order b = dao.getStore().get(id);
		if(b==null)
			throw new RuntimeException("GET: Order with " + id +  " not found");
		return b;
	}
	
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response putOrder(JAXBElement<Order> b) {
		Order newb = b.getValue();
		return putAndGetResponse(newb);
	}
	
	@DELETE
	public void deleteOrder() {
		Order delb = dao.getStore().remove(id);
		if(delb==null)
			throw new RuntimeException("DELETE: Order with " + id +  " not found");
	}
	
	private Response putAndGetResponse(Order b) {
		Response res;
		if(dao.getStore().containsKey(b.getId())) {
			res = Response.noContent().build();
		} else {
			res = Response.created(uriInfo.getAbsolutePath()).build();
		}
		dao.getStore().put(b.getId(), b);
		return res;
	}
}