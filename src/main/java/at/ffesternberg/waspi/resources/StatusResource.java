package at.ffesternberg.waspi.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

import at.ffesternberg.waspi.api.WasPiStatus;
import at.ffesternberg.waspi.core.WasClientCacher;

@Path("/status")
@Produces(MediaType.APPLICATION_JSON)
public class StatusResource {
	private final WasClientCacher wasClientCache;

	public StatusResource(WasClientCacher wasClientCache) {
		this.wasClientCache = wasClientCache;
	}

	@GET
	@Timed
	public WasPiStatus getStatus() {
		WasPiStatus status = new WasPiStatus();
		status.setStatus(wasClientCache.getStatus());
		status.setTimestamp(wasClientCache.getTimestamp());
		status.setAlertCount(wasClientCache.getOrders().size());

		return status;
	}
}
