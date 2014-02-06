package org.icc.app.common.jackjson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Set;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ser.FilterProvider;
import org.codehaus.jackson.map.ser.impl.SimpleBeanPropertyFilter;
import org.codehaus.jackson.map.ser.impl.SimpleFilterProvider;
import org.codehaus.jackson.type.TypeReference;


public class JackJson {
	private static final Logger logger = LoggerFactory.getLogger(JackJson.class);
	
	private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	
	public static <T> T fromJsonToObject(String json, TypeReference<T> typeReference) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(json, typeReference);
		} catch (JsonParseException e) {
			logger.error("JsonParseException: ", e);
		} catch (JsonMappingException e) {
			logger.error("JsonMappingException: ", e);
		} catch (IOException e) {
			logger.error("IOException: ", e);
		}
		return null;
	}

	public static <T> T fromJsonToObject(String json, Class<T> valueType) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(json, valueType);
		} catch (JsonParseException e) {
			logger.error("JsonParseException: ", e);
		} catch (JsonMappingException e) {
			logger.error("JsonMappingException: ", e);
		} catch (IOException e) {
			logger.error("IOException: ", e);
		}
		return null;
	}

	public static String fromObjectToJson(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			logger.error("JsonGenerationException: ", e);
		} catch (JsonMappingException e) {
			logger.error("JsonMappingException: ", e);
		} catch (IOException e) {
			logger.error("IOException: ", e);
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public static String fromObjectToJson(Object object, String filterName, Set<String> properties) {
		ObjectMapper mapper = new ObjectMapper();
		FilterProvider filters = new SimpleFilterProvider().addFilter(filterName, SimpleBeanPropertyFilter.serializeAllExcept(properties));
		try {
			return mapper.filteredWriter(filters).writeValueAsString(object);
		} catch (JsonGenerationException e) {
			logger.error("JsonGenerationException: ", e);
		} catch (JsonMappingException e) {
			logger.error("JsonMappingException: ", e);
		} catch (IOException e) {
			logger.error("IOException: ", e);
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	public static String fromObjectToJson(Object object, String filterName, String property) {
		ObjectMapper mapper = new ObjectMapper();
		FilterProvider filters = new SimpleFilterProvider().addFilter(filterName, SimpleBeanPropertyFilter.serializeAllExcept(property));
		try {
			return mapper.filteredWriter(filters).writeValueAsString(object);
		} catch (JsonGenerationException e) {
			logger.error("JsonGenerationException: ", e);
		} catch (JsonMappingException e) {
			logger.error("JsonMappingException: ", e);
		} catch (IOException e) {
			logger.error("IOException: ", e);
		}
		return null;
	}

	public static String fromObjectHasDateToJson(Object object) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.getSerializationConfig().withDateFormat(new SimpleDateFormat(DATE_TIME_FORMAT));
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			logger.error("JsonGenerationException: ", e);
		} catch (JsonMappingException e) {
			logger.error("JsonMappingException: ", e);
		} catch (IOException e) {
			logger.error("IOException: ", e);
		}
		return null;
	}

	public static String fromObjectHasDateToJson(Object object, String dateTimeFormatString) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.getSerializationConfig().withDateFormat(new SimpleDateFormat(dateTimeFormatString));
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonGenerationException e) {
			logger.error("JsonGenerationException: ", e);
		} catch (JsonMappingException e) {
			logger.error("JsonMappingException: ", e);
		} catch (IOException e) {
			logger.error("IOException: ", e);
		}
		return null;
	}
}
