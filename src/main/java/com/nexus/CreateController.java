package com.nexus;
import java.util.Date;
import static spark.Spark.*;
import static com.nexus.JsonUtility.*; 

public class CreateController {
	
	public CreateController(final CreateService createService)  {
		
		
		get("/create", (req, res) -> 
			createService.test(), json());
		
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