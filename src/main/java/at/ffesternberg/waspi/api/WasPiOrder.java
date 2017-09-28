package at.ffesternberg.waspi.api;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import at.ffesternberg.libwas.entity.Order;

@JsonAutoDetect
public class WasPiOrder extends Order {
	public WasPiOrder() {
	}
	public WasPiOrder(Order old){
		super(old);
	}
}
