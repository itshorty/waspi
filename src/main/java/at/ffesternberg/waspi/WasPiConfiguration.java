package at.ffesternberg.waspi;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class WasPiConfiguration extends Configuration {
	
	@Valid
	@NotNull
	private WasClientFactory wasClientFactory = new WasClientFactory();

	@JsonProperty("wasClient")
	public WasClientFactory getWasClientFactory() {
		return wasClientFactory;
	}

	@JsonProperty("wasClient")
	public void setWasClientFactory(WasClientFactory wasClientFactory) {
		this.wasClientFactory = wasClientFactory;
	}
	
}
