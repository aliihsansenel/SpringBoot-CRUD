package com.aliihsansenel.springboot.json;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {

	
	public static void main(String[] args) {
		read();
		//write();
	}
	public static void read() {
		
		ObjectMapper mapper = new ObjectMapper();

		try {

			// Convert JSON string from file to Object
			UserDemo user = mapper.readValue(new File("src/main/resources/static/example.json"), UserDemo.class);
			System.out.println(user.getName());
			System.out.println(user.getAge());
			System.out.println(user.getComment());

			/*// Convert JSON string to Object
			String jsonInString = "{\"age\":33,\"messages\":[\"msg 1\",\"msg 2\"],\"name\":\"mkyong\"}";
			UserDemo user1 = mapper.readValue(jsonInString, User.class);
			System.out.println(user1);*/

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	public String getUserDemo() {
		ObjectMapper mapper = new ObjectMapper();

		//For testing
		UserDemo user = new UserDemo();
		user.setName("Ali İhsan Şenel");
		user.setAge(21);
		user.setComment("Çok güzel.");
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	static void write() {
		ObjectMapper mapper = new ObjectMapper();

		//For testing
		UserDemo user = new UserDemo();
		user.setName("Ali İhsan Şenel");
		user.setAge(21);
		user.setComment("Çok güzel.");
		
		try {
			//Convert object to JSON string and save into file directly 
			mapper.writeValue(new File("src/main/resources/static/example.json"), user);
			
			//Convert object to JSON string
			String jsonInString = mapper.writeValueAsString(user);
			System.out.println(jsonInString);
			
			System.out.println();
			
			//Convert object to JSON string and pretty print
			jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(user);
			System.out.println(jsonInString);
			
			
		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
