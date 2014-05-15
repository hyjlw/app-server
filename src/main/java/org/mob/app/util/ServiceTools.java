/**
 * 
 */
package org.mob.app.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;

import org.slf4j.Logger;

/**
 * @author LL
 *
 */
public class ServiceTools {
	public static boolean isEmpty(Collection<?> c) {
		return c == null || c.isEmpty();
	}
	
	public static String getBaseUrl(String url) {
		int pos = url.indexOf('/', 7);
		
		return url.substring(0, pos + 1);
	}
	
	public static void logException(Logger log, String message, Throwable cause) {
		StringWriter stack = new StringWriter();
		cause.printStackTrace(new PrintWriter(stack));
		
		log.error(message + ", Exception stackTrace: " + stack.toString());
	}
}
