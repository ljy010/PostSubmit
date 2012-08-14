package common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class HttpRequestHeader {
	
	private Map<String, String> headParamHash = new HashMap<String, String>();
	
	public static HttpRequestHeader DefaultHeader = new HttpRequestHeader();
	static{
		DefaultHeader.addHeadProperty("HOST", "bj.jiehun.com.cn");
		DefaultHeader.addHeadProperty("connection", "keep-alive");
		DefaultHeader.addHeadProperty("origin", "http://bj.jiehun.com.cn");
		DefaultHeader.addHeadProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
		DefaultHeader.addHeadProperty("Content-type", "application/x-www-form-urlencoded");
		DefaultHeader.addHeadProperty("Accept-Encoding", "gzip,deflate,sdch");
		DefaultHeader.addHeadProperty("Accept-Language", "zh-CN,zh;q=0.8");
		DefaultHeader.addHeadProperty("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
	}
	
	public void addHeadProperty(String properName, String properValue){
		headParamHash.put(properName, properValue);
	}
	
	
	
	public Map<String, String> getHeadParamHash() {
		return headParamHash;
	}



	public String toString(){
		StringBuffer sb = new StringBuffer();
		Set<Entry<String, String>> entries = headParamHash.entrySet();
		Iterator<Entry<String, String>> itr = entries.iterator();
		boolean isFirst = true;
		while(itr.hasNext()){
			Entry<String, String> e = itr.next();
			String paramName = e.getKey();
			String paramValue = e.getValue();
			String oneParam = paramName + "=" + paramValue;
			if (!isFirst) {
				sb.append("/r/n");
			}
			sb.append(oneParam);
			isFirst = false;
			
		}
		return sb.toString();
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
