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
}
