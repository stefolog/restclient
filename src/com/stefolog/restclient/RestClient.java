package com.stefolog.restclient;

import java.io.EOFException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class RestClient {

	private static ObjectMapper mapper = new ObjectMapper();
	private static String baseURL = "http://localhost:8080";

	public static void initBaseURL(String baseURL) {
		RestClient.baseURL = baseURL;
	}

	public static void initJsonMapper(ObjectMapper mapper) {
		RestClient.mapper = mapper;
	}

	public static <T> T get(String serviceURL, Class<T> expected) {
		try {
			System.out.println("GET to [" + toAbsoluteURL(serviceURL) + "]");
			
			return mapper.readValue(toAbsoluteURL(serviceURL), expected);
		} catch (EOFException eofEx) {
			return null;
		} catch (Exception e) {
			throw new RestException(e);
		}
	}

	public static <T> List<T> getList(String serviceURL, Class<T> expected) {
		try {
			System.out.println("GET to [" + toAbsoluteURL(serviceURL) + "]");
			
			return mapper.readValue(toAbsoluteURL(serviceURL), new TypeReference<List<T>>() {});
		} catch (EOFException eofEx) {
			return null;
		} catch (Exception e) {
			throw new RestException(e);
		}
	}

	public static <IN, OUT> OUT post(String serviceURL, IN object, Class<OUT> outputType) {
		try {
			final String json = toJson(object);
			System.out.println("POST to [" + toAbsoluteURL(serviceURL) + "] " + json);
			
			final PostRequest request = new PostRequest(toAbsoluteURL(serviceURL));
			return mapper.readValue(request.send(json), outputType);
		} catch (EOFException eofEx) {
			return null;
		} catch (Exception e) {
			throw new RestException(e);
		}
	}

	private static URL toAbsoluteURL(String serviceURL) throws MalformedURLException {
		return new URL(baseURL + serviceURL);
	}

	private static <T> String toJson(T object) throws Exception {
		return mapper.writeValueAsString(object);
	}
}
