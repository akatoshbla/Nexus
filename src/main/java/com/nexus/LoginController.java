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
			
			//res.header("Access-Control-Allow-Origin","*");
			//res.header("Access-Control-Allow-Headers", "accept, content-type");
			//res.header("Access-Control-Allow-Methods", "POST, OPTIONS");
			try {
				return loginService.createLogin(req.body());
			}
			catch (Exception e){
				return null;
			}
		},json());
		
		get("/hello", (req, res) -> "Hello World!");
	}		
}