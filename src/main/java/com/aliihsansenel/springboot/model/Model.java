package com.aliihsansenel.springboot.model;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Iterator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class Model {

	public String objectToJson() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	public Model jsonToObject(String json) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(json, this.getClass());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String arrayToJson(ArrayList array) {
		ObjectMapper mapper = new ObjectMapper();
		
		StringBuilder builder = new StringBuilder("[\n");
		try {
			Iterator iterator = array.iterator();
			while(iterator.hasNext()) {
				builder.append(mapper
						.writerWithDefaultPrettyPrinter()
						.writeValueAsString(iterator.next()));
				builder.append(",\n");
			}
			builder.setLength(Math.max(builder.length() - 2, 0));
			builder.append("\n]");
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		}
		return builder.toString();
	}
	
	abstract public Model toModel(ResultSet rs);
}
