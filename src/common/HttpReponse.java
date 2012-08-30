package common;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

import javax.xml.ws.Response;

public class HttpReponse {

	private InputStream inputStream = null;
	
	private int responseCode;
	
	private int contentlength;
	
	private String responseMsg;
	
	public InputStream getInputStream() {
		return inputStream;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public int getContentlength() {
		return contentlength;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public HttpReponse(HttpURLConnection connection) throws IOException{
		responseCode = connection.getResponseCode();
		contentlength = connection.getContentLength();
		responseMsg = connection.getResponseMessage();
		if(responseCode == HttpURLConnection.HTTP_OK){
		    inputStream = connection.getInputStream();
		}
	}
	
	public String toString(){
		StringBuffer sb = new StringBuffer();
		
		
		return sb.toString();
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		StringBuffer sb = new StringBuffer();
		sb.append("responseCode:" + 200).append("\n");
		sb.append("responseLength:" + 300).append("\n");
		System.out.println(sb.toString());
	}

}
