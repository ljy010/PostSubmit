package common;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class URLConnectinUtils {
	
	
	public static String getCookie(HttpURLConnection connection){
		List<String> cookies = connection.getHeaderFields().get("Set-Cookie");
		StringBuffer sb = new StringBuffer("");
		if((cookies != null) && (cookies.size() > 0)){
			for(String cookie: cookies){
				if((cookie != null) && (!"".equals(cookie)))
				    sb.append(cookie).append(";");
			}	
		}
		
        return sb.toString(); 
	}
	
	public static void setCookie(HttpURLConnection connection, String cookie){
		connection.setRequestProperty("Cookie", cookie);  
	}
	
	public static URLConnection createURLConnection(URL url) throws IOException{
	    return url.openConnection();
	}
	
	public static void closeHttpConnection(HttpURLConnection connection){
		connection.disconnect();
	}
	
	public static HttpURLConnection createHttpURLConnection(String url)throws IOException{
		URL netURL = new URL(url);
		return createHttpURLConnection(netURL);
	}
	
	public static HttpURLConnection createHttpURLConnection(URL url) throws IOException{
		URLConnection connection = createURLConnection(url);
		if (connection instanceof HttpURLConnection) {
			return (HttpURLConnection) connection;
		}
		else{
			return null;
		}
	}
	
	public static void printStringInputStream(InputStream inputStream) throws IOException{
		System.out.println(inputStream.toString());
	}
	
	public static void printHttpResponse(HttpReponse response) throws IOException{
		if(response != null){
//			InputStream is = response.getInputStream();
//			byte[] buffer = new byte[1024];
//			int length = is.read(buffer);
//			String r = "";
//			while(length != -1){
//				String s = new String(buffer, 0, length);
//				r = r + s;
//				length = is.read(buffer);
//			}
//			System.out.println(r);
			printInputStream(response.getInputStream());
		}
		else{
			System.out.println("返回状态值不是200");
			throw new RuntimeException("返回异常!");
		}
	}
	
	public static void printInputStream(InputStream inputStream) throws IOException{
		BufferedReader bfIns = new BufferedReader(new InputStreamReader(inputStream));
		String line = "";
		while((line = bfIns.readLine()) != null){
			System.out.println(line);
		}
		bfIns.close();
	}
	
	private static void initHttpConnectionHeader(HttpURLConnection httpConnection, HttpRequestHeader header){
		Map<String, String> headerMap = header.getHeadParamHash();
		Set<Entry<String, String>> entrySet = headerMap.entrySet();
		Iterator<Entry<String, String>> itr = entrySet.iterator();
		while(itr.hasNext()){
			Entry<String, String> e = itr.next();
			httpConnection.setRequestProperty(e.getKey(), e.getValue());
		}
	}
	
	public static HttpReponse doGetMethod(HttpURLConnection httpConnection, HttpRequestHeader header){
		if(httpConnection != null){
			initHttpConnectionHeader(httpConnection, header);
			
			httpConnection.setDoInput(true);
			
			try {
				httpConnection.setRequestMethod("GET");
				httpConnection.setUseCaches(false);
				
				httpConnection.setInstanceFollowRedirects(true);
				
				return new HttpReponse(httpConnection);
			} catch (ProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static void updateCookie(HttpURLConnection httpConnection, HttpState httpState){
		String cookie = URLConnectinUtils.getCookie(httpConnection);
//		System.out.println("update Cookie:" + cookie);
		httpState.setCookie(cookie);
	}
	
	
	public static HttpReponse doPostMethod(HttpURLConnection httpConnection, HttpRequestHeader header,  String content){
	    if (httpConnection != null) {
	    	try
	    	{
				
	    		initHttpConnectionHeader(httpConnection, header);
//	    		System.out.println("content-length:" + String.valueOf(content.getBytes().length));
	    		httpConnection.setRequestProperty("Content-Length", String.valueOf(content.getBytes().length));
	    		
				httpConnection.setDoInput(true);
				httpConnection.setDoOutput(true);
				
				httpConnection.setRequestMethod("POST");
				httpConnection.setUseCaches(false);	
				
				httpConnection.setInstanceFollowRedirects(true);
				
				DataOutputStream dataOutPut = new DataOutputStream(httpConnection.getOutputStream());
				
				dataOutPut.write(content.getBytes());
				
				dataOutPut.flush();
			    dataOutPut.close();
			    
			    
			    return new HttpReponse(httpConnection);

	    	}catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			
			
		}else{
			return null;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			URL netURL = new URL("http://bj.jiehun.com.cn/accounts/login?u=%2Fbbs%2F");
			HttpURLConnection httpConnection = createHttpURLConnection(netURL);
//			httpConnection.setRequestProperty("Accept-Language", "zh-CN,zh");
			httpConnection.connect();
			try{
				String cookie = getCookie(httpConnection);
				System.out.println(cookie);
//				BufferedReader bfIns = new BufferedReader(new InputStreamReader(httpConnection.getInputStream(), "UTF-8"));
//				String line = "";
//				while((line = bfIns.readLine()) != null){
//					System.out.println(line);
//				}
//				bfIns.close();
					
			}
			finally{
				closeHttpConnection(httpConnection);
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
