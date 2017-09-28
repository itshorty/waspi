package at.ffesternberg.waspi.core;

import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

import javax.ws.rs.core.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import at.ffesternberg.libwas.WASClient;
import at.ffesternberg.libwas.WASOrderListener;
import at.ffesternberg.libwas.WASStatus;
import at.ffesternberg.libwas.WASStatusListener;
import at.ffesternberg.libwas.entity.Order;

public class WasClientAsyncWaiter {
	private static final Logger logger = LoggerFactory.getLogger(WasClientAsyncWaiter.class);
	private CountDownLatch changeSignal = new CountDownLatch(1);

	public WasClientAsyncWaiter() {

	}

	public void waitForWASChange() {
		try {
			if (logger.isTraceEnabled())
				logger.trace("Starting waiting - thread: " + Thread.currentThread().getName());

			changeSignal.await(10, TimeUnit.SECONDS);
			
			if (logger.isTraceEnabled())
				logger.trace("End waiting - thread: " + Thread.currentThread().getName());
		} catch (InterruptedException e) {

		}
	}

	protected synchronized void releaseWaiters() {
		if (logger.isDebugEnabled())
			logger.debug("Releasing Waiters - thread: " + Thread.currentThread().getName());
		changeSignal.countDown();
		changeSignal = new CountDownLatch(1);
	}

}
