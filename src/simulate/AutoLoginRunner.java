package simulate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.concurrent.Callable;

import common.HttpReponse;
import common.HttpRequestHeader;
import common.HttpState;
import common.URLConnectinUtils;

public class AutoLoginRunner implements Callable<HttpState> {
	
	private HttpState httpState;
	
	private static String LOGIN_URL = "http://bj.jiehun.com.cn/accounts/login?u=%2Fbbs%2F";
	
	private static String LOGIN_ACTION_URL = "http://bj.jiehun.com.cn/accounts/_login";
	
	private String login_user = "";
	
    public static String LOGIN_USER_LJY = "user_name=13240006799&psw=610fda4f5c7b4154eaf629499d48d7fd";
	
	public static String LOGIN_USER_DR = "user_name=18610318624&psw=388338eba7454b2922e5a677c3807542";
	
	public AutoLoginRunner(HttpState httpState){
		this.httpState = httpState;
		this.login_user = httpState.getLoginUser();
	}
	
	private void initCookie() throws IOException{
		HttpURLConnection httpConnection = URLConnectinUtils.createHttpURLConnection(LOGIN_URL);
		try{		
			httpConnection.connect();
			URLConnectinUtils.updateCookie(httpConnection, httpState);
		}finally{
			URLConnectinUtils.closeHttpConnection(httpConnection);
		}
	}
	
	private HttpRequestHeader createRequestHeader(){
		HttpRequestHeader header = HttpRequestHeader.DefaultHeader;
		header.addHeadProperty("Cookie", httpState.getCookie());
		return header;
	}
	
	@Override
	public HttpState call() throws Exception {
		initCookie();
		HttpURLConnection httpConnection = URLConnectinUtils.createHttpURLConnection(LOGIN_ACTION_URL);
		try{
		    HttpRequestHeader header = createRequestHeader();
		    HttpReponse reponse = URLConnectinUtils.doPostMethod(httpConnection, header, login_user);
//		    System.out.println("===================loing response begin=====================");
//		    System.out.println(reponse);
//		    System.out.println("===================loing response end=====================");
		    URLConnectinUtils.updateCookie(httpConnection, httpState);
//		    System.out.println("after login cookie:" + httpState.getCookie());
		    httpState.setLogin(true);
		}finally{
			URLConnectinUtils.closeHttpConnection(httpConnection);
		}
		return httpState;
	}

}
