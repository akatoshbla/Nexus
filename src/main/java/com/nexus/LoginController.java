package com.nexus;

import static spark.Spark.*;
import static com.nexus.JsonUtility.*;

public class LoginController {
	
	public LoginController(final LoginService loginService) {
		
		get("/login", (req, res) -> loginService.isSuccess(), json());
		
		post("/login", (req, res) -> loginService.createLogin(
				req.queryParams("name"),
				req.queryParams("password")
		), json());
		
		get("/hello", (req, res) -> "Hello World!");
	}		
}