package com.elasticpath.messaging.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

/**
 * MyProcessor.
 */
public class HttpClient {
	//private static final transient Logger LOG = LoggerFactory.getLogger(HttpClient.class);
	/**
	 * This processor exist for set headers into sending message.
	 *
	 * @param args the new exchange instance
	 * @throws Exception is thrown if error post processing bean
	 */

	public static void main(final String[] args) throws Exception {
		HttpClient client = new HttpClient();
		// client.requestPOST();
		// client.getAllAccessories();
		client.getAccessoriesForSKU("45");
	}

	/**
	 * This processor exist for set headers into sending message.
	 *
	 */
	public void requestPOST() {
		String[] roles = { "PUBLIC" };
		String enc = "UTF-8";
		URL url;
		try {
			url = new URL(
			// "http://ec2-35-164-110-172.us-west-2.compute.amazonaws.com:9080/cortex/oauth/tokens/");
					"http://ec2-35-164-110-172.us-west-2.compute.amazonaws.com:9080/cortex/oauth2/tokens ");
			Map<String, Object> params = new LinkedHashMap<>();

			params.put("grant_type", "password");
			params.put("role", "PUBLIC");
			params.put("roles", roles);
			params.put("scope", "mobee");
			params.put("username", "");
			params.put("password", "");

			//params.put("Accept", "*/*");
			//params.put("Accept-Encoding", "gzip, deflate");
			//params.put("Accept-Language", "en-US,en;q=0.8");
			params.put("Authorization",
					"bearer c5821d5b-77b2-41fa-ba08-7f2cd30d3137");
			params.put("Connection", "keep-alive");
			final int contentlength = 63;
			params.put("Content-Length", contentlength);
			params.put("Content-Type",
					"application/x-www-form-urlencoded; charset=" + enc);

			/*
			 * params.put("Host",
			 * "ec2-35-164-110-172.us-west-2.compute.amazonaws.com:9080");
			 * params.put("Origin",
			 * "http://ec2-35-164-110-172.us-west-2.compute.amazonaws.com:9080"
			 * ); params.put("Referer",
			 * "http://ec2-35-164-110-172.us-west-2.compute.amazonaws.com:9080/studio/"
			 * );
			 */
			params.put(
					"User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
			params.put("X-Requested-With", "XMLHttpRequest");

			StringBuilder postData = new StringBuilder();
			for (Map.Entry<String, Object> param : params.entrySet()) {
				if (postData.length() != 0) {
					postData.append('&');
				}
				postData.append(URLEncoder.encode(param.getKey(), enc));
				postData.append('=');
				postData.append(URLEncoder.encode(
						String.valueOf(param.getValue()), enc));
			}
			byte[] postDataBytes = postData.toString().getBytes(enc);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded;charset=" + enc);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			// conn.setRequestProperty("Content-Length",
			// String.valueOf(postDataBytes.length));
			conn.setDoOutput(true);
			conn.getOutputStream().write(postDataBytes);

			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream(), enc));
			String output = "";
			String finalOutput = "";		
			while ((output = in.readLine()) != null) {
				finalOutput += output;
			}

			/* for (int c; (c = in.read()) >= 0;) { */
				 System.out.println("Data :::: " + finalOutput);
			/* }  */	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			 System.out.println("Exception :::: " + e.getMessage());
		}
	}

	/**
	 * This processor exist for set headers into sending message.
	 *
	 */
	public void requestGET() {

	}
	
	public void getAccessories(String skuId) {
		try {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet getRequest = new HttpGet(
			"http://localhost:8080/accessories/");
		getRequest.addHeader("accept", "application/json");

		HttpResponse response = httpClient.execute(getRequest);

		if (response.getStatusLine().getStatusCode() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
			   + response.getStatusLine().getStatusCode());
		}

		BufferedReader br = new BufferedReader(
                         new InputStreamReader((response.getEntity().getContent())));

		String output;
		System.out.println("Output from Server .... \n");
		while ((output = br.readLine()) != null) {
			System.out.println(output);
		}

		httpClient.getConnectionManager().shutdown();

	  } catch (ClientProtocolException e) {

		e.printStackTrace();

	  } catch (IOException e) {

		e.printStackTrace();
	  }

	}
	
	public String getAllAccessories(){
		String finalOtput = "";
		try {

			URL url = new URL("http://localhost:8080/accessories/");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				finalOtput += output;
			}
			System.out.println(finalOtput);
			conn.disconnect();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }
		return null;
		}
	
	public String getAccessoriesForSKU(String skuId){
		String finalOtput = "";
		try {

			URL url = new URL("http://localhost:3000/accessories/"+skuId);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				finalOtput += output;
			}
			System.out.println(finalOtput);
			conn.disconnect();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }
		return null;
		}
	
	
	}
	
	
	
