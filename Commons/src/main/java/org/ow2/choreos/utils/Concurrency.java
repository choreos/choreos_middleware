package org.ow2.choreos.utils;

import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class Concurrency {

	public static void waitExecutor(ExecutorService executor, int timeoutMinutes, TimeUnit timeUnit, Logger logger) {

		executor.shutdown();
		boolean status = false;
		try {
			status = executor.awaitTermination(timeoutMinutes, timeUnit);
		} catch (InterruptedException e) {
			logErrorMessage("Interrupted thread! Should not happen!", logger);
		}
		if (!status) {
			logErrorMessage("Executor status is False. Probably there is some problem!", logger);
		}
		executor.shutdownNow();
	}
	
	public static void waitExecutor(ExecutorService executor, int timeoutMinutes) {
		waitExecutor(executor, timeoutMinutes, TimeUnit.MINUTES, null);
	}

	public static void waitExecutor(ExecutorService executor, int timeoutMinutes, Logger logger) {
		waitExecutor(executor, timeoutMinutes, TimeUnit.MINUTES, logger);
	}

	private static void logErrorMessage(String msg, Logger logger) {
		if (logger != null) {
			logger.error(msg);
		} else {
			System.out.println(msg);
		}
	}
	
	/**
	 * Returns the future return or throws an exception.
	 * 
	 * If there is no exception and future is not done, future is canceled.
	 * 
	 * @param f
	 * @return f.get()
	 * @throws ExecutionException if the Callable code caused the exception, or IllegalStateException if there was any other problem
	 */
	public static <T> T checkFuture(Future<T> f)  throws ExecutionException {
		
		T result = null;
		try {
			if (f.isDone()) {
				result = f.get();
			} else {
				f.cancel(true);
				throw new IllegalStateException("Future not done, therefore canceled"); 
			}
		} catch (InterruptedException e) {
			throw new IllegalStateException(e);
		} catch (ExecutionException e) {
			throw e;
		} catch (CancellationException e) {
			throw new IllegalStateException(e);
		}
		return result;
	}
	
	public static void waitThreads(List<Thread> threads) {
		
		for (Thread trd: threads) {
			try {
				trd.join();
			} catch (InterruptedException e) {
				System.out.println("Vish! Thread died at sleep!");
			}
		}
	}
	
}
