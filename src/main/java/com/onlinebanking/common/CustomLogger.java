package com.onlinebanking.common;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public final class CustomLogger {
	private static CustomLogger log = new CustomLogger();
	private Logger logger;

	private CustomLogger(){
		logger = Logger.getLogger("OnlineBankingLog");
		try {
			FileHandler fh = new FileHandler(System.getenv("log.dir") + "/obank.log");
			logger.addHandler(fh);
			SimpleFormatter formatter = new SimpleFormatter();
			fh.setFormatter(formatter);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

	public static CustomLogger getInstance(){
		return log;
	}

	public void info(String logMessage){
		logger.info(logMessage);
	}
}
