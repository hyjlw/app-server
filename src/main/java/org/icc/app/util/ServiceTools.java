/**
 * 
 */
package org.icc.app.util;

import java.util.Collection;

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
}
