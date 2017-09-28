package at.ffesternberg.waspi.health;

import com.codahale.metrics.health.HealthCheck;

import at.ffesternberg.libwas.WASClient;
import at.ffesternberg.libwas.WASStatus;

public class WasClientHealthCheck extends HealthCheck {
	
	private final WASClient client;
	
	public WasClientHealthCheck(WASClient client) {
		super();
		this.client = client;
	}

	@Override
	protected Result check() throws Exception {
		if (client==null)
			return Result.unhealthy("No WAS Client at all!");
		if(client.getState() != WASStatus.CONNECTED){
			return Result.unhealthy("WAS not connected! State:"+ client.getState());
		}
		return Result.healthy();
	}

}
