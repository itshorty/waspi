package at.ffesternberg.waspi.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.codahale.metrics.annotation.Timed;

import at.ffesternberg.libwas.WASClient;

/**
 * Access the RAW XML from WAS
 * 
 * @author floriahu
 *
 */
@Path("/raw")
@Produces(MediaType.APPLICATION_XML)
public class RawXMLDataResource {
	
	private final WASClient client;
	
	public RawXMLDataResource(WASClient client){
		this.client=client;
	}
	
	@GET
	@Timed
	public String getRawData(){
		return client.getLastRAWData().getResponse();
	}

}
