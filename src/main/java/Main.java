import static spark.Spark.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import spark.ResponseTransformer;

public class Main {
	
    public static void main(String[] args) {
    	   	
    	// Guess at the implementation of login
    	post("/login", (req, res) -> {
    		Gson gson = new Gson();
    		JsonObject json = new JsonObject();
    		if (req.params("password").equals("12345"))
    			{
    				json.addProperty("result", true);
    				return json;
    			}
    		else
    			{
    				json.addProperty("result", false);
    				return json;
    			}
    	});
    	
    	get("/hello", (req, res) -> "Hello World!");
    }
    
    public class JsonTransformer implements ResponseTransformer {

        private Gson gson = new Gson();

        @Override
        public String render(Object model) {
            return gson.toJson(model);
        }

    }
}