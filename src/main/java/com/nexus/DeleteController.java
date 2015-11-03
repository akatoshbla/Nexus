package com.nexus;
import java.util.Date;
import static spark.Spark.*;
import static com.nexus.JsonUtility.*; 

/**
 * This class has the posts and gets for deactivating a user's account.
 * @author David Kopp
 *
 */
public class DeleteController {
	
	/**
	 * Post /delete takes a String username.
	 * <p>Returns a Json with a boolean:
	 * <p>{"results": True}
	 * <p>True - successul, False - failed
	 * @param deleteService class
	 */
	public DeleteController(final DeleteService deleteService)  {
		
		
		get("/delete", (req, res) -> 
			deleteService.test(), json());
		
		post("/delete",(req,res) -> {
			//add connection requested by printout
			Date date = new Date();
			System.out.println(date.toString() + ": " + req.ip());

			try {
				return deleteService.deleteResult(req.body());
			}
			catch (Exception e){
				e.printStackTrace();
				return null;
			}
		},json());
		
	}		
}