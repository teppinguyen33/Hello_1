package com.music.common;

import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;

public class CallAPI {

	private byte[] responseBody;
	private int statusCode;

	public byte[] getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(byte[] responseBody) {
		this.responseBody = responseBody;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public CallAPI() {
	}

	public CallAPI(String url) throws HttpException, IOException {
		HttpClient httpClient = new HttpClient();
		GetMethod getMethod = new GetMethod(url);
		Header header = new Header();
		header.setName("Accept-Encoding");
		header.setValue("gzip, deflate, sdch");
		try {
			this.statusCode = httpClient.executeMethod(getMethod);
			this.responseBody = getMethod.getResponseBody();
		} finally {
			getMethod.releaseConnection();
		}
	}

}
