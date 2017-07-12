package at.ffesternberg.waspi.resources;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Lists;

import at.ffesternberg.libwas.entity.Order;
import at.ffesternberg.libwas.entity.OrderStatus;
import at.ffesternberg.waspi.api.WasPiOrder;
import io.dropwizard.jersey.params.IntParam;

@Path("/dummyalerts")
@Produces(MediaType.APPLICATION_JSON)
public class DummyAlertResource {
	private static final Logger log = LoggerFactory.getLogger(DummyAlertResource.class);

	private final AtomicLong counter;

	public DummyAlertResource() {
		counter = new AtomicLong();
	}

	@GET
	public Set<Order> getAlerts(@DefaultValue("3") @QueryParam("count") IntParam count) {
		return getDummyOrders(count.get());
	}

	@Timed
	private Set<Order> getDummyOrders(int count) {
		log.debug("Serving " + count + " dummy orders");
		Set<Order> orders = new HashSet<Order>(count);
		for (int i = 0; i < count; i++) {
			orders.add(getDummyOrder());
		}
		return orders;
	}

	private Order getDummyOrder() {
		Order order = new Order();
		order.setKey(counter.incrementAndGet());
		order.setAlarmlevel(3);
		order.setCaller("Somebody, +43 1234 2342343");
		order.setFireDepartments(Lists.newArrayList("FF A-Dorf", "FF B-Dorf"));
		order.setInfo("Heuboden brennt");
		order.setLocation("Irgendwo 3, Gemeinde so und so");
		order.setOperation("BR - LANDWIRTSCHAFTLICHES OBJEKT");
		order.setOperationId("BR-LWO");
		order.setOrigin("DUMMY! DUMMY!");
		order.setReceived(new Date());
		order.setStatus(OrderStatus.ALERT);
		order.setWatchout(null);
		order.setFinished(null);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return order;
	}
}
