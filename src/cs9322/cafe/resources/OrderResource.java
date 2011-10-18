package cs9322.cafe.resources;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

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
		Order o = OrdersDao.instance.getOrders().get(id);
		if(o==null)
			throw new RuntimeException("GET: Order with " + id +  " not found");
		return o;
	}
	
	// Produces HTML for browser-based client
	@GET
	@Produces(MediaType.TEXT_XML)
	public Order getOrderHTML() {
		Order o = OrdersDao.instance.getOrders().get(id);
		if(o==null)
			throw new RuntimeException("GET: Order with " + id +  " not found");
		return o;
	}
	
	@PUT
	@Produces(MediaType.TEXT_HTML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public Response putOrder(
			@FormParam("type") String type,
			@FormParam("additions") String additions,
			@FormParam("paidStatus") String paidStatus,
			@FormParam("baristaStatus") String baristaStatus,
			@Context HttpServletResponse servletResponse
			) throws IOException {
		Order newo = OrdersDao.instance.getOrders().get(id);
		if(type != null)
			newo.setType(type);
		if(additions != null)
			newo.setAdditions(additions);
		if(paidStatus != null)
			newo.setPaidStatus(paidStatus);
		if(baristaStatus != null)
			newo.setBaristaStatus(baristaStatus);
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