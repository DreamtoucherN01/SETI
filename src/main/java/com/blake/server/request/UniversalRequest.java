package com.blake.server.request;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestHandler;

import com.blake.server.handler.EventHandler;
import com.blake.server.response.UnifiedResponse;
import com.blake.server.share.HttpMethod;

public class UniversalRequest implements HttpRequestHandler{

	final static Logger logger = LoggerFactory.getLogger(UniversalRequest.class);
	
	public void handleRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if(logger.isDebugEnabled()) {
			
			logger.debug("InsertHandler invoked");
		}
		
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=UTF-8");
		if(HttpMethod.fromString(request.getMethod()) != HttpMethod.POST) {
			
			UnifiedResponse.sendErrResponse(response);
			return;
		}
		
		logger.info("get request from : " + request.getRemoteAddr() );
		
		EventHandler eventHandler = new EventHandler(request, response);
		eventHandler.handleEvent();
		
		UnifiedResponse.sendSuccessResponse(response);
	}

}
