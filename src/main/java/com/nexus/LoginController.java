package com.nexus;
import java.util.Date;
import java.util.HashMap;

import com.google.gson.Gson;

import spark.ModelAndView;

import static spark.Spark.*;
import static com.nexus.JsonUtility.*; 

public class LoginController {
	
	public LoginController(final LoginService loginService)  {
		
		get("/login", (req, res) -> {
			if (req.session().attribute("username") != null)
			{
				System.out.println("Username: " + req.session().attribute("username"));
				System.out.println("Has a session id: " + req.session().id());
			}
			else
			{
				System.out.println("Hell no!");
			}
			return loginService.test();
		}, json());
		
		post("/login",(req,res) -> {
			//add connection requested by printout
			Date date = new Date();
			System.out.println(date.toString() + ": " + req.ip());
			
			try {
				if (loginService.loginResult(req.body()).get("result").getAsBoolean()) 
				{
					String username = new Gson().fromJson(req.body(), User.class).getUsername();
					req.session().attribute("username", username); // TODO: Check sessionId = username
					System.out.println("User session id: " +req.session().id());
				}
				return loginService.loginResult(req.body());
			}
			catch (Exception e){
				e.printStackTrace();
				return null;
			}
		},json());
		
	}		
}