package com.nexus;

import static spark.Spark.*;
import static com.nexus.JsonUtility.*; 

public class LoginController {
	
	public LoginController(final LoginService loginService) {
				
		get("/login", (req, res) -> 
			loginService.test(), json());
		
		post("/login", (req, res) ->
		loginService.createLogin(
				req.body()), json());
		
		get("/hello", (req, res) -> "Hello World!");
	}		
}