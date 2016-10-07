package com.blake.server.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.blake.procedure.seti.tuple.Segement;
import com.blake.procedure.seti.tuple.TimeInterval;
import com.blake.query.QueryObject;
import com.blake.server.response.UnifiedResponse;
import com.blake.server.share.DBOperationType;

public class EventHandler {

	Logger logger = Logger.getLogger(EventHandler.class);
	
	HttpServletRequest request; HttpServletResponse response;
	
	public EventHandler(HttpServletRequest request, HttpServletResponse response) {

		this.request = request;
		this.response = response;
	}

	public void handleEvent() {

		BufferedReader reader = null;
		String line = null;
		StringBuffer buffer = new StringBuffer();
		
		try {
			
			reader = new BufferedReader(new InputStreamReader(request.getInputStream(),"UTF-8"));
			while((line = reader.readLine()) != null) {
				
				buffer.append(line);
			}
			
		} catch (IOException e) {

			logger.error("error when reading transferring data ");
			UnifiedResponse.sendErrResponse(response);
			return;
		}
		
		JSONObject jo = null;
		try{
			
			jo =   JSONObject.fromObject(buffer.toString());
			
		} catch(Exception e) {
			
			logger.error("message is not wrapped using json, please check ");
			UnifiedResponse.sendErrResponse(response);
			return;
		}
		String type = (String) jo.get("type");
		
		if(DBOperationType.fromString(type) == DBOperationType.query) {
			
//			String key = (String) jo.get("key");
//			String mode = (String) jo.get("mode");
			TimeInterval ti = new TimeInterval(20121101004252l,20121101095016l);
			QueryObject qo = new QueryObject(ti, 130.3209986,35.361769 ,116.564177 ,40.020933);
//			HashSet<Segement> segements = qo.searchTrajectory();
			int num = qo.getTrajectoryNum();
//			if(segements == null || segements.size() == 0){
//				
//				UnifiedResponse.sendErrResponse(response);
//				return;
//			}
//			UnifiedResponse.sendResponse(response, segements, 0);
		}
		
		UnifiedResponse.sendErrResponse(response);
	}

}
