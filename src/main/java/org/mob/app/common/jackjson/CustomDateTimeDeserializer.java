package org.mob.app.common.jackjson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;


public class CustomDateTimeDeserializer extends JsonDeserializer<Date> {
	private static final Logger logger = LoggerFactory.getLogger(CustomDateTimeDeserializer.class);
	private static final String[] DATE_TIME = { "yyyy-MM-dd HH:mm:ss" };

	@Override
	public Date deserialize(JsonParser parser, DeserializationContext arg1) throws IOException, JsonProcessingException {
		try {
			return DateUtils.parseDate(parser.getText(), DATE_TIME);
		} catch (ParseException e) {
			logger.error("ParseException: ", e);
		}
		return null;
	}
}