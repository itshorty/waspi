package at.ffesternberg.waspi.core;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ffesternberg.libwas.WASClient;
import at.ffesternberg.libwas.WASOrderListener;
import at.ffesternberg.libwas.WASStatus;
import at.ffesternberg.libwas.WASStatusListener;
import at.ffesternberg.libwas.entity.Order;

public class WasClientCacher implements WASStatusListener, WASOrderListener {
	
	private static final Logger logger = LoggerFactory.getLogger(WasClientCacher.class);


	private Date timestamp=new Date();
	private WASStatus status = WASStatus.STOPPED;
	private WasClientAsyncWaiter asyncWaiter;
	private Set<Order> orders = Collections.emptySet();

	public WasClientCacher(WASClient wasClient, WasClientAsyncWaiter asyncWaiter) {
		wasClient.addWasOrderListener(this);
		wasClient.addWasStatusListener(this);
		this.asyncWaiter=asyncWaiter;
	}

	public void statusUpdated(WASStatus oldStatus, WASStatus newStatus) {
		logger.debug("WAS Status Updated! "+oldStatus+" -> "+newStatus);
		timestamp = new Date();
		status = newStatus;
		asyncWaiter.releaseWaiters();
	}

	public void updateOrders(Set<Order> orders) {
		
		timestamp = new Date();
		if (orders != null) {
			this.orders = orders;
		} else {
			this.orders = Collections.emptySet();
		}
		logger.debug("WAS Orders Updated! Order Count: "+this.orders.size());
		asyncWaiter.releaseWaiters();
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public WASStatus getStatus() {
		return status;
	}

	public void setStatus(WASStatus status) {
		this.status = status;
	}

	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

}
