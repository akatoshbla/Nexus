package com.nexus;
import static spark.Spark.*;
import static com.nexus.JsonUtility.*; 

/**
 * This class contains the RESTful API methods for messaging.
 * @author Alex Hall
 *
 */
public class MessageController {
	public MessageController (final MessageService messageService) {
		//TODO add session verification
		post("/sendMessage",(req,res) -> {
			try {
				return messageService.sendMessage(req.body());
			}
			catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		},json());
		
		//TODO add session verification
		post("/getMessages",(req,res) -> {
			try {
				return messageService.getMessages(req.body());
			}
			catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		},json());
	}
}
