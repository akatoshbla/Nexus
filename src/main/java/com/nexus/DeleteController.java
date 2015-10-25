package com.nexus;
import java.util.Date;
import static spark.Spark.*;
import static com.nexus.JsonUtility.*; 

public class DeleteController {
	
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