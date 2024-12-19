package com.HIS.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JsonHandler {
	private static ObjectMapper mapper=new ObjectMapper();
	public JsonHandler() {
		mapper.registerModule(new JavaTimeModule());
	}
	static String filePath = "DoctorsProfile.json";
	public static void writeToJson(ArrayList<Doctor> doctors) {
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), doctors);
		} catch (StreamWriteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DatabindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	public ArrayList<Doctor> readFromJson(){
		try {
			return mapper.readValue(new File(filePath), new TypeReference<ArrayList<Doctor>>() {});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			return new ArrayList<Doctor>();
		}
	}
}
