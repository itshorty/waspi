package at.ffesternberg.waspi;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import at.ffesternberg.libwas.WASClient;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;
import io.dropwizard.validation.PortRange;

public class WasClientFactory {
	// VPN Route: route add 192.168.130.0 mask 255.255.255.0 10.106.40.254
	@NotEmpty
	private String host="192.168.130.100";
	
	@PortRange
	private int port=47000;

	
	
	@JsonProperty
	public String getHost() {
		return host;
	}

	@JsonProperty
	public void setHost(String host) {
		this.host = host;
	}

	@JsonProperty
	public int getPort() {
		return port;
	}

	@JsonProperty
	public void setPort(int port) {
		this.port = port;
	}
	
	
    public WASClient build(Environment environment) {
    	final WASClient client = new WASClient(getHost(),getPort());
    	environment.lifecycle().manage(new Managed() {
			
			public void stop() throws Exception {
				client.stop();
			}
			
			public void start() throws Exception {
				client.start();
			}
		});
    	return client;
    }

}
