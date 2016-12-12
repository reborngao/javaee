package com.reborn.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;


/**
 * 处理返回的json字符串值
 * @author Administrator
 *
 */
public class ObjectMapperCustomer  extends XmlMapper {
	public ObjectMapperCustomer(){
		super();
		//允许单引号
		this.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		//字段和值都加引号
		this.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		//数字也加引号
		this.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);
		this.configure(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS, true);
		//空值处理为空字符串
		this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>(){

			@Override
			public void serialize(Object value, JsonGenerator jsonGenerator,
					SerializerProvider provider) throws IOException,
					JsonProcessingException {
				jsonGenerator.writeString("");
			}
		});
	}
}
