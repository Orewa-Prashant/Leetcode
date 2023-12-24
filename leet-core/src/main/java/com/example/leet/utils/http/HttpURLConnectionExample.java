package com.example.leet.utils.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.web.bind.annotation.RestController;

import com.example.leet.utils.ResponseUtils;

@RestController
public class HttpURLConnectionExample {

	private static final String USER_AGENT = "Mozilla/5.0";

	private static final String GET_URL = "https://leetcode.com/contest/api/ranking/weekly-contest-341/?pagination=";

	private static final String POST_URL = "https://localhost:9090/SpringMVCExample/home";

	private static final String POST_PARAMS = "716";

//	public static void main(String[] args) throws IOException {
//
////		sendGET();
//		System.out.println("GET DONE");
////		sendPOST();
////		System.out.println("POST DONE");
//	}

//	@GetMapping("fun")
	public static void sendGET(String contestId, int pageNo) throws IOException {
		URL obj = new URL(GET_URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);
		int responseCode = con.getResponseCode();
		System.out.println("GET Response Code :: " + responseCode);
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			String response = ResponseUtils.getStringFromInputStream(con.getInputStream());
		} else {
		}

	}

	public void sendPOST() throws IOException {
		URL obj = new URL(POST_URL);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);

		// For POST only - START
		con.setDoOutput(true);
		OutputStream os = con.getOutputStream();
		os.write(POST_PARAMS.getBytes());
		os.flush();
		os.close();
		// For POST only - END

		int responseCode = con.getResponseCode();
		System.out.println("POST Response Code :: " + responseCode);

		if (responseCode == HttpURLConnection.HTTP_OK) { //success
			BufferedReader in = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// print result
			System.out.println(response.toString());
		} else {
			System.out.println("POST request not worked");
		}
	}

}