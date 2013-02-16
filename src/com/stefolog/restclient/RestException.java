package com.stefolog.restclient;

public class RestException extends RuntimeException {

	public RestException(Exception e) {
		super(e);
	}

	private static final long serialVersionUID = 7553942018192633518L;

}
