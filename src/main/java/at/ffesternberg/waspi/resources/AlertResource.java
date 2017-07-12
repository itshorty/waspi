package at.ffesternberg.waspi.resources;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

import at.ffesternberg.libwas.entity.Order;
import at.ffesternberg.waspi.api.WasPiOrder;
import at.ffesternberg.waspi.core.WasClientCacher;

@Path("/alerts")
@Produces(MediaType.APPLICATION_JSON)
public class AlertResource {

	private final WasClientCacher wasClientCache;

	public AlertResource(WasClientCacher wasClientCache) {
		this.wasClientCache = wasClientCache;
	}

	@GET
	@Timed
	public Set<WasPiOrder> getAlerts() {
		Set<WasPiOrder> orders = new HashSet<WasPiOrder>();
		for (Order o : wasClientCache.getOrders()){
			orders.add(new WasPiOrder(o));
		}
		return orders;
	}

}
