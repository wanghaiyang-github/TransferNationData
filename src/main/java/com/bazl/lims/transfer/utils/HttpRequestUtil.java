package com.bazl.lims.transfer.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;


public class HttpRequestUtil {

	public static Logger logger = LoggerFactory.getLogger(HttpRequestUtil.class);

	public static String sendRequest(String url,String data){
		return sendRequest(url, "POST", data, null);
	}
	
	public static void sendRequest(String url1,String requestMethod,String data){
		sendRequest(url1, requestMethod, data, null);
	}

	public static void sendRequest(String url1,String requestMethod,Map<String,String> headParamMap){
		sendRequest(url1, requestMethod, null, headParamMap);
	}
	
	public static String sendRequest(String url1,String requestMethod,String data,Map<String,String> headParamMap){
		String operation = "url=>"+url1+"==requestmethod=>"+requestMethod+"==senddata=>"+data;
		StringBuilder sb = new StringBuilder();
		try {

			URL url = new URL(url1);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(requestMethod);
			conn.setRequestProperty("Accept", "application/json");

			if(headParamMap!=null && !headParamMap.isEmpty()){
				for(String key:headParamMap.keySet()){
					String value = headParamMap.get(key);
					conn.addRequestProperty(key, value);
				}
			}

			if(data!=null && data.trim().length()>0){
				conn.setDoOutput(true);
				conn.setRequestProperty("Content-Type", "application/json");
				String input = data;

				OutputStream os =null;
				try {
					os = conn.getOutputStream();
				} catch (Exception e) {
					logger.error("网络连接失败");
					return "{\"status\":404}";
				}
				os.write(input.getBytes());
				os.flush();
			}

			if (conn.getResponseCode() != 200) {
				logger.error("请求失败:响应码为"+conn.getResponseCode()+" |operation==>"+operation);
				return "{\"status\":" +conn.getResponseCode()+'}';
			}

			InputStream inputStream = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(inputStream, "UTF-8");

			
			BufferedReader br = new BufferedReader(isr);
			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				sb.append(output);
			}
			logger.debug("===resultstr==>"+sb.toString());
			conn.disconnect();

		} catch (IOException e) {

	        logger.error("请求失败:"+operation, e);
			return "{\"status\":401}";

		}
		String result = sb.toString();
		logger.debug("请求结果:"+result+"   | 请求信息"+operation);
		return result;
	}


}
