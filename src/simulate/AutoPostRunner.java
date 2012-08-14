package simulate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.Callable;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.tags.InputTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import common.HttpHtmlParserUtils;
import common.HttpReponse;
import common.HttpRequestHeader;
import common.HttpState;
import common.URLConnectinUtils;

public class AutoPostRunner implements Callable<HttpState> {

	
	protected HttpState httpState;
	
	protected  String replyContent = "율율율율율율";
	
	protected String POST_URL = "http://bj.jiehun.com.cn/bbs/topic/_addpost";
	
	
	protected Map<String, String> paramPair = new HashMap<String, String>();
	
	public String paramStr(){
		StringBuffer sb = new StringBuffer();
		Set<Map.Entry<String, String>> entrySet = paramPair.entrySet();
		Iterator<Map.Entry<String, String>> itr = entrySet.iterator();
		boolean isFirst = true;
		while (itr.hasNext()) {
			Map.Entry<String, String> entry = itr.next();
			if (!isFirst) {
				sb.append("&");
			}
			sb.append(entry.getKey() + "=" + entry.getValue());
			isFirst = false;
		}
		return sb.toString().trim();
	}
	
	protected void initParamPair(){
		paramPair.clear();
		paramPair.put("topic_uid", "");
		paramPair.put("topic_id", "");
		paramPair.put("post_uid", "");
		paramPair.put("topic_creat_time", "");
		paramPair.put("topic_title", "");
		paramPair.put("city_host", "");
		paramPair.put("post_total", "");
		paramPair.put("rr_content", "");
	}
	
	public AutoPostRunner(HttpState httpState, String reply){
		this.httpState = httpState;
		this.replyContent = reply;
		initParamPair();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

	}
	
	protected void initParamPareValue(String replyContent) throws IOException, ParserException{
		if(httpState.getUrl() == null){
			return;
		}
		HttpURLConnection httpConnection = URLConnectinUtils.createHttpURLConnection(httpState.getUrl());
		try{
			httpConnection.setRequestProperty("Cookie", httpState.getCookie());
			httpConnection.setDoInput(true);
			httpConnection.setDoInput(true);
			httpConnection.setUseCaches(false);
//			URLConnectinUtils.printInputStream(httpConnection.getInputStream());
			
			Parser parser = new Parser(httpConnection);
			NodeList nodes = HttpHtmlParserUtils.getNodeListByInputType(parser, "hidden");
			for(int i = 0; i < nodes.size(); i++){
				Node node = nodes.elementAt(i);
				if(node instanceof InputTag){
					InputTag inputTag = (InputTag)node;
//					System.out.println(inputTag.toHtml());
					Set<Map.Entry<String, String>> mapEntry = paramPair.entrySet();
					Iterator<Entry<String, String>> itr = mapEntry.iterator();
					while(itr.hasNext()){
						Map.Entry<String, String> entry = itr.next();
						String attrName = entry.getKey();
						String attrMapValue = entry.getValue();
						if(attrMapValue.equals("")){
							String  attrValue = inputTag.getAttribute("name");
							if((attrValue != null) && (attrName.equals(attrValue))){
								String value = inputTag.getAttribute("value");
								if(attrName.equals("topic_title")){
									value = URLEncoder.encode(value, "utf-8");
								}
								paramPair.put(attrName, value);
								break;
							}	
						}
						
						
					}
				}
			}
			
			paramPair.put("rr_content", URLEncoder.encode(replyContent, "UTF-8"));	
		}finally{
			URLConnectinUtils.closeHttpConnection(httpConnection);
		}
	}
	
	public String getReplyParams() throws IOException, ParserException{
		String result = null;
		if(httpState.getUrl() == null){
			return result;
		}
		initParamPareValue(replyContent);
		result = paramStr();
		return result;
	}
	
	protected HttpRequestHeader createPostHeader(){
		HttpRequestHeader header = new HttpRequestHeader();
		header.addHeadProperty("Cookie", httpState.getCookie());
		header.addHeadProperty("HOST", "bj.jiehun.com.cn");
		header.addHeadProperty("connection", "keep-alive");
		header.addHeadProperty("User-Agent", "Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)");
		header.addHeadProperty("Content-type", "application/x-www-form-urlencoded");
		header.addHeadProperty("Accept-Encoding", "gzip,deflate,sdch");
		header.addHeadProperty("Accept-Language", "zh-CN");
		header.addHeadProperty("Accept-Charset", "GBK,utf-8;q=0.7,*;q=0.3");
		header.addHeadProperty("Referer", httpState.getUrl());
		header.addHeadProperty("X-Requested-With", "XMLHttpRequest");
		header.addHeadProperty("Cache-Control", "no-cache");
		header.addHeadProperty("Accept", "*/*");
		
		return header;
	}
	
	protected void replySubmit(String postParamContent) throws IOException{
		replySubmit(POST_URL, postParamContent);
	}
	
	protected void replySubmit(String url, String postParamContent) throws IOException{
		HttpURLConnection httpConnection = URLConnectinUtils.createHttpURLConnection(url);
		try{
			HttpRequestHeader header = createPostHeader();
			HttpReponse response = URLConnectinUtils.doPostMethod(httpConnection, header, postParamContent);
			System.out.println(response);
//			URLConnectinUtils.printHttpResponse(response);
		}finally{
			URLConnectinUtils.closeHttpConnection(httpConnection);
		}
	}

	public HttpState call() throws Exception {
		if(httpState.getUrl() == null){
			return httpState;
		}
		String content = getReplyParams();
		replySubmit(content);
		return httpState;
	}

}
