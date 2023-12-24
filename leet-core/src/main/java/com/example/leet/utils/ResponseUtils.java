package com.example.leet.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ResponseUtils {
	public ResponseUtils() {	
	}
	
	public static String getStringFromInputStream(InputStream inputStream) throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(
				inputStream));
		String inputLine;
		StringBuffer response = new StringBuffer();
		try {
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return response.toString();
	}
}
