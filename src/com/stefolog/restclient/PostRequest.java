package com.stefolog.restclient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class PostRequest {

	private URL url;

	public PostRequest(URL url) {
		this.url = url;
	}

	public String send(String payload) throws Exception {
		URLConnection conn = url.openConnection();
		conn.addRequestProperty("content-type", "application/json");
		conn.setDoOutput(true);
		conn.setDoInput(true);

		OutputStream outputStream = conn.getOutputStream();
		outputStream.write(payload.getBytes());
		outputStream.close();

		conn.connect();

		InputStream connIS = conn.getInputStream();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(connIS));
			String res = "";
			while (true) {
				final String line = reader.readLine();
				if (line == null) {
					return res;
				}
				res += line;
			}
		} finally {
			connIS.close();
		}
	}

}