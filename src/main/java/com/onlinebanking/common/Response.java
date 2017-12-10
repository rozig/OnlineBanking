package com.onlinebanking.common;

import com.google.gson.JsonObject;

import java.util.Map;

public class Response {
	private int code;
	private String message;
	private Map<String, Object> data;

	public Response(int code, String message, Map<String, Object> data){
		this.code = code;
		this.message = message;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}
}
