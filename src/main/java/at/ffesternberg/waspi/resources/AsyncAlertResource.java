package at.ffesternberg.waspi.resources;

import java.util.concurrent.TimeUnit;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.container.TimeoutHandler;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.codahale.metrics.annotation.Timed;

import at.ffesternberg.waspi.core.WasClientAsyncWaiter;

@Path("/waitforalerts")
@Produces(MediaType.APPLICATION_JSON)
public class AsyncAlertResource {
	private final WasClientAsyncWaiter wasWaiter;

	public AsyncAlertResource(WasClientAsyncWaiter wasClientAsyncWaiter) {
		this.wasWaiter = wasClientAsyncWaiter;
	}

	@GET
	@Timed
	public void waitForAlerts(@Suspended final AsyncResponse asResponse) {
		asResponse.setTimeoutHandler(new TimeoutHandler() {
			public void handleTimeout(AsyncResponse asResponse) {
				asResponse.resume(Response.status(Response.Status.OK).entity("No alerts").build());
			}
		});
		asResponse.setTimeout(30, TimeUnit.SECONDS);
		new Thread(new Runnable() {
			public void run() {
				wasWaiter.waitForWASChange();
				asResponse.resume(Response.status(Response.Status.OK).entity("Alerts Changed!").build());
			}
		}).run();
	}
}
