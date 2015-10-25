package com.nexus;
import java.util.Date;
import static spark.Spark.*;
import static com.nexus.JsonUtility.*; 

public class LoginController {
	
	public LoginController(final LoginService loginService)  {
		
		
		get("/login", (req, res) -> 
			loginService.test(), json());
		
		post("/login",(req,res) -> {
			//add connection requested by printout
			Date date = new Date();
			System.out.println(date.toString() + ": " + req.ip());

			try {
				return loginService.loginResult(req.body());
			}
			catch (Exception e){
				e.printStackTrace();
				return null;
			}
		},json());
		
	}		
}