package at.ffesternberg.waspi;

import at.ffesternberg.libwas.WASClient;
import at.ffesternberg.waspi.core.WasClientAsyncWaiter;
import at.ffesternberg.waspi.core.WasClientCacher;
import at.ffesternberg.waspi.health.WasClientHealthCheck;
import at.ffesternberg.waspi.resources.AlertResource;
import at.ffesternberg.waspi.resources.AsyncAlertResource;
import at.ffesternberg.waspi.resources.DummyAlertResource;
import at.ffesternberg.waspi.resources.RawXMLDataResource;
import at.ffesternberg.waspi.resources.StatusResource;
import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class WasPi extends Application<WasPiConfiguration> {

	public static void main(String[] args) throws Exception {
		new WasPi().run(args);
	}

	@Override
	public String getName() {
		return "WasPi";
	}

	@Override
	public void initialize(io.dropwizard.setup.Bootstrap<WasPiConfiguration> bootstrap) {
		// Noting to do yet
	}

	@Override
	public void run(WasPiConfiguration configuration, Environment environment) throws Exception {

		// Environment
		WASClient wasClient = configuration.getWasClientFactory().build(environment);
		WasClientAsyncWaiter wasClientAsyncWaiter = new WasClientAsyncWaiter();
		WasClientCacher wasClientCache = new WasClientCacher(wasClient, wasClientAsyncWaiter);
		// HealthChecks
		environment.healthChecks().register("wasClient", new WasClientHealthCheck(wasClient));

		// Resources
		final AlertResource alertResource = new AlertResource(wasClientCache);
		environment.jersey().register(alertResource);
		final AsyncAlertResource asyncAlertResource = new AsyncAlertResource(wasClientAsyncWaiter);
		environment.jersey().register(asyncAlertResource);
		final StatusResource statusResource = new StatusResource(wasClientCache);
		environment.jersey().register(statusResource);
		final RawXMLDataResource rawAlertResource = new RawXMLDataResource(wasClient);
		environment.jersey().register(rawAlertResource);
		final DummyAlertResource dummyAlertResource = new DummyAlertResource();
		environment.jersey().register(dummyAlertResource);

	}

}
