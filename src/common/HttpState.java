package common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class HttpState {
	
	private boolean isLogin = false;
	
	private Map<String, String> cookies = new HashMap<String, String>();
	
	private String url = null;
	
	public String getUrl() {
		return url;
	}



	public void setUrl(String url) {
		this.url = url;
	}



	public boolean isLogin() {
		return isLogin;
	}



	public void setLogin(boolean isLogin) {
		this.isLogin = isLogin;
	}



	public String getCookie() {
		Set<Entry<String, String>> entrySet = cookies.entrySet();
		Iterator<Entry<String, String>> itr = entrySet.iterator();
		StringBuffer sb = new StringBuffer("");
		while(itr.hasNext()){
			Entry<String, String> e = itr.next();
			String cookieOne = e.getKey() + "=" + e.getValue();
			if(!"".equals(sb.toString())){
				sb.append(";");
			}
			sb.append(cookieOne);
		}
		return sb.toString();
	}

	private void addCookie(String name, String value){
		if((name != null) && (!"".equals(name))){
			cookies.put(name, value);
		}
	}


	public void setCookie(String cookie) {
		String[] oneCookieItems = cookie.split(";");
		for(String cookieStr: oneCookieItems){
			String[] cookieKeyVal = cookieStr.split("=");
			if((cookieKeyVal != null) && (cookieKeyVal.length == 2)){
				addCookie(cookieKeyVal[0], cookieKeyVal[1]);
			}
		}
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
