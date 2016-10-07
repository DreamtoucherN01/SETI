package com.blake.server.response;

import java.io.IOException;
import java.util.HashSet;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UnifiedResponse {
	
	public static Logger logger = LoggerFactory.getLogger(UnifiedResponse.class);

	public static void sendResponse(HttpServletResponse response, @SuppressWarnings("rawtypes") HashSet set, int data) {
		
		JSONObject result = new JSONObject();
		result.put("result", true);
		result.put("err", 0);
		result.put("num", set.size());
		result.put("response", JSONArray.fromObject(set));
		result.put("data", data);
		
		if(logger.isDebugEnabled()) {
			
			logger.debug("the response is : ");
		}
		response.setContentLength(result.toString().getBytes().length);
		try {
			
			response.getWriter().write(result.toString());
			response.getWriter().close();
			return;
			
		} catch (IOException e) {
			
			logger.debug("catch IOException : {} ", e);
		}
	}
	
	public static void sendResponse(HttpServletResponse response, int num) {
		
		JSONObject result = new JSONObject();
		result.put("result", true);
		result.put("err", 0);
		result.put("number", num);
		
		if(logger.isDebugEnabled()) {
			
			logger.debug("the response is : ");
		}
		response.setContentLength(result.toString().getBytes().length);
		try {
			
			response.getWriter().write(result.toString());
			response.getWriter().close();
			return;
			
		} catch (IOException e) {
			
			logger.debug("catch IOException : {} ", e);
		}
	}
	
	public static void sendSuccessResponse(HttpServletResponse response ) {
		
		String respStr = "{\"result\":true,\"err\":1000,,\"ver\":1}";
		if(logger.isDebugEnabled()) {
			
			logger.debug("the response is {}" , respStr);
		}
		
		response.setContentLength(respStr.toString().getBytes().length);
		try {
			
			response.getWriter().write(respStr.toString());
			response.getWriter().close();
			return;
			
		} catch (IOException e) {
			
			logger.debug("catch IOException : {} ", e);
		}
	}
	
	public static void sendErrResponse(HttpServletResponse response ) {
		
		String respStr = "{\"result\":true,\"err\":5001,,\"ver\":1}";
		if(logger.isDebugEnabled()) {
			
			logger.debug("the response is {}" , respStr);
		}
		
		response.setContentLength(respStr.toString().getBytes().length);
		try {
			
			response.getWriter().write(respStr.toString());
			response.getWriter().close();
			return;
			
		} catch (IOException e) {
			
			logger.debug("catch IOException : {} ", e);
		}
	}
}
