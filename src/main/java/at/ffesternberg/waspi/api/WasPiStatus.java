package at.ffesternberg.waspi.api;

import java.util.Date;

import at.ffesternberg.libwas.WASStatus;

public class WasPiStatus {
	private WASStatus status;
	private Date timestamp;
	private int alertCount;

	public WASStatus getStatus() {
		return status;
	}

	public void setStatus(WASStatus status) {
		this.status = status;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public int getAlertCount() {
		return alertCount;
	}

	public void setAlertCount(int alertCount) {
		this.alertCount = alertCount;
	}

}
