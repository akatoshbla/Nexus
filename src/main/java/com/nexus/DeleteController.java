package com.nexus;
import java.util.Date;
import static spark.Spark.*;
import static com.nexus.JsonUtility.*; 

/**
 * This class has the posts and gets for deactivating 
 * 	 and reactivating a user's account.
 * @author David Kopp
 *
 */
public class DeleteController {
	
	/**
	 * POST /deactivate takes a String username.
	 * <p>Returns a Json with a boolean:
	 * <p>{"results": True}
	 * <p>True - successful, False - failed
	 * <br> 
	 * POST /activate takes a String username.
	 * <p>Returns a Json with a boolean:
	 * <p>{"results": True}
	 * <p>True - successful, False - failed
	 * @param deleteService class
	 * 
	 */
	public DeleteController(final DeleteService deleteService)  {
		
		
		get("/delete", (req, res) -> 
			deleteService.test(), json());
		
		post("/deactivate",(req,res) -> {
			//add connection requested by printout
			Date date = new Date();
			System.out.println(date.toString() + ": " + req.ip());

			try {
				return deleteService.deactivateResult(req.body());
			}
			catch (Exception e){
				e.printStackTrace();
				return null;
			}
		},json());
		post("/activate",(req,res) -> {
			//add connection requested by printout
			Date date = new Date();
			System.out.println(date.toString() + ": " + req.ip());

			try {
				return deleteService.activateResult(req.body());
			}
			catch (Exception e){
				e.printStackTrace();
				return null;
			}
		},json());
		
	}		
}