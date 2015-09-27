/**
 * @author Nirmallya Mukherjee
 * 
 * Note: this is a sample code and is provided as part of training without any warranty.
 * You can use this code in any way at your own risk. 
 */

package com.skl.app.util;

import java.util.logging.Logger;

public class Util {

	private final static Logger LOGGER = Logger.getLogger(Util.class .getName());
	public static int MILLI = 1000;

	public static void delay(int sleeptimeseconds) {
		delayMillis(sleeptimeseconds*MILLI);
	}
	
	
	public static void delayMillis(int sleeptimemillis) {
		try {
			Thread.sleep(sleeptimemillis);
		} catch (InterruptedException e) {
			LOGGER.severe("Unable to cause delay for "+sleeptimemillis+" seconds ");
		}
	}

}
