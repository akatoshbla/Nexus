package com.nexus;
import java.util.Date;
import static spark.Spark.*;
import static com.nexus.JsonUtility.*; 

/**
 * This class has the posts and gets for creating a new user.
 * @author David Kopp
 *
 */
public class CreateController {
	
	/**
	 * The post /create needs two Strings: username and password.
	 * <p>Returns a Json with a boolean inside:
	 * <p>{"result": True} 
	 * <p>True - if created ok, False - if it fails
	 * @param createService Class
	 */
	public CreateController(final CreateService createService)  {
		
		/**
		 * Test get for loop back testing.
		 */
		get("/create", (req, res) -> 
			createService.test(), json());
		
		/**
		 * This post /create needs two Strings: username and password.
		 * Returns a Json with a boolean inside. 
		 * True - if created ok, False - if it fails
		 */
		post("/create",(req,res) -> {
			//add connection requested by printout
			Date date = new Date();
			System.out.println(date.toString() + ": " + req.ip());

			try {
				return createService.createResultr(req.body());
			}
			catch (Exception e){
				e.printStackTrace();
				return null;
			}
		},json());
		
	}		
}