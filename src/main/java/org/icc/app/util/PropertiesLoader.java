package org.icc.app.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {
	private static final String PROPS_FILE_NAME = "server.properties";
	
	private static PropertiesLoader instance = new PropertiesLoader();
	
	private PropertiesLoader() {
		
	}
	
	public static PropertiesLoader getInstance() {
		return instance;
	}
	
	public String getResetPassword() {
		return getValueByKey("user.reset.password");
	}
	
	public String getProxyHost() {
		return getValueByKey("proxy.host");
	}
	public String getProxyPort() {
		return getValueByKey("proxy.port");
	}
	
	public String getBaiduMapsUrl() {
		return getValueByKey("baidu.maps.url");
	}
	public String getBaiduMapsKey() {
		return getValueByKey("baidu.maps.key");
	}
	public String getBaiduMapsOutput() {
		return getValueByKey("baidu.maps.output");
	}
	public String getBaiduMapsBounds() {
		return getValueByKey("baidu.maps.bounds");
	}
	
	public String getSmsPrefix() {
		return getValueByKey("sms.msg.prefix");
	}
	
	public String getShopImageBaseUrl() {
		return getValueByKey("shop.img.url");
	}
	
	public String getVoucherImageBaseUrl() {
		return getValueByKey("voucher.img.url");
	}
	
	public String getCompanyId() {
		return getValueByKey("com.id");
	}
	public String getUserName() {
		return getValueByKey("user.name");
	}
	public String getUserPwd() {
		return getValueByKey("user.pwd");
	}
	public String getSmsNumber() {
		return getValueByKey("sms.number");
	}
	public String getSmsUrl() {
		return getValueByKey("sms.url");
	}
	
	public String getSmtpHost() {
		return getValueByKey("mail.smtp.host");
	}
	public String getSmtpAuth() {
		return getValueByKey("mail.smtp.auth");
	}
	
	public String getMailAccount() {
		return getValueByKey("email.account");
	}
	public String getMailPassword() {
		return getValueByKey("email.password");
	}
	
	public String getCacheTimeout() {
		return getValueByKey("data.cache.timeout");
	}
	
	public String getValueByKey(String key) {
		Properties prop = loadProperties();
		String path = (String) prop.get(key);
		return path;
	}
	
	private Properties loadProperties() {
		Properties prop = new Properties();
		InputStream is = this.getClass().getClassLoader().getResourceAsStream(PROPS_FILE_NAME);
		if(is != null) {
			try {
				prop.load(is);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return prop;
	}
}
