package com.moekr.shadow.panel.logic.rpc.impl;

import org.springframework.http.HttpHeaders;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.net.HttpURLConnection;

public class AuthorizationClientHttpRequestFactory extends SimpleClientHttpRequestFactory {
	private static final String HTTP_BASIC_PREFIX = "Basic ";

	private String authorization;

	AuthorizationClientHttpRequestFactory(String username, String password) {
		this.authorization = HTTP_BASIC_PREFIX + Base64Utils.encodeToString((username + ":" + password).getBytes());
	}

	@Override
	protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
		super.prepareConnection(connection, httpMethod);
		connection.setRequestProperty(HttpHeaders.AUTHORIZATION, authorization);
	}
}
