package com.nexus;

import com.google.gson.Gson;
import spark.ResponseTransformer;

public class JsonUtility {

	public static String toJson(Object object) {
		return new Gson().toJson(object);
	}

	public static ResponseTransformer json() {
		return JsonUtility::toJson;
	}
}