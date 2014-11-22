package org.mob.app.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


public class ClassLoaderUtil {
	private static final Logger logger = LoggerFactory.getLogger(ClassLoaderUtil.class);

	public static URL getResource(String resourceName, Class<?> callingClass) {
		URL url = Thread.currentThread().getContextClassLoader().getResource(resourceName);
		if (url == null) {
			url = ClassLoaderUtil.class.getClassLoader().getResource(resourceName);
		}
		if (url == null) {
			ClassLoader cl = callingClass.getClassLoader();
			if (cl != null) {
				url = cl.getResource(resourceName);
			}
		}
		if ((url == null) && (resourceName != null) && ((resourceName.length() == 0) || (resourceName.charAt(0) != '/'))) {
			return getResource('/' + resourceName, callingClass);
		}
		if (url != null) {
			logger.info("配置文件路径为= " + url.getPath());
		}
		return url;
	}

	public static InputStream getResourceAsStream(String resourceName, Class<?> callingClass) {
		URL url = getResource(resourceName, callingClass);
		try {
			return (url != null) ? url.openStream() : null;
		} catch (IOException e) {
			logger.error("配置文件" + resourceName + "没有找到! ", e);
			return null;
		}
	}

}