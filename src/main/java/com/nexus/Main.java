package com.nexus;

//import spark.Filter;
//import spark.Request;
//import spark.Response;
import spark.Spark;
import static spark.Spark.*;

/**
 * This is the Main method for the middle-tier using the Java Spark Frameworks. 
 * @author David Kopp
 *
 */
public class Main {
	
	/**
	 * Main method for the middle-tier Java Spark. CORS is also enabled with a 
	 * preflight check.
	 * @param args Standard main args
	 * @throws Exception if error
	 */
    public static void main(String[] args) throws Exception {
    		//enableCORS("*", "*", "*");
    	staticFileLocation("/public"); 
    	
    	/**
    	 * CORS filter for all posts, gets, etc
    	 */
    	Spark.options("/*", (request,response)->{
    			 
    		    String accessControlRequestHeaders = request.headers("Access-Control-Request-Headers");
    		    if (accessControlRequestHeaders != null) {
    		        response.header("Access-Control-Allow-Headers", accessControlRequestHeaders);
    		    }
    		 
    		    String accessControlRequestMethod = request.headers("Access-Control-Request-Method");
    		    if(accessControlRequestMethod != null){
    		    response.header("Access-Control-Allow-Methods", accessControlRequestMethod);
    		    }
    		 
    		    return "OK";
    		});
    		 
    		Spark.before((request,response)->{
    		    response.header("Access-Control-Allow-Origin", "*");
    		});
        
    		/**
    		 * Creating all the controllers and services below
    		 */
    	   	new LoginController(new LoginService());
    	   	new CreateController(new CreateService());
    	   	new DeleteController(new DeleteService());
    	   	new ProfileController(new ProfileService());
    }
    
//	private static void enableCORS(final String origin, final String methods, final String headers) {
//	    before(new Filter() {
//	        @Override
//	        public void handle(Request request, Response response) {
//	            response.header("Access-Control-Allow-Origin", origin);
//	            response.header("Access-Control-Request-Method", methods);
//	            response.header("Access-Control-Allow-Headers", headers);
//	        }
//	    });
//	}
}