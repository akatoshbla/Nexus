package com.nexus;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import spark.ResponseTransformer;

/**
 * This class is a transformer for the gson to json conversions. 
 * @author David Kopp
 *
 */
public class JsonUtility {

	public static String toJson(Object object) {
		return new Gson().toJson(object);
	}

	public static ResponseTransformer json() {
		return JsonUtility::toJson;
	}
}