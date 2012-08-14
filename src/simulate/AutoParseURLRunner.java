package simulate;

import java.net.HttpURLConnection;
import java.util.concurrent.Callable;

import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.AndFilter;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.filters.HasParentFilter;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.util.NodeList;

import common.HttpState;
import common.URLConnectinUtils;

public class AutoParseURLRunner implements Callable<HttpState> {
	
	private static String MAIN_PAGE_URL = "http://bj.jiehun.com.cn/bbs/65/164";
	
	private HttpState httpState;
	
	private String keyStr = null;
	
	public AutoParseURLRunner(HttpState state, String keyStr){
		this.httpState = state;
		this.keyStr = keyStr;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public HttpState call() throws Exception {
		if(httpState.getUrl() != null){
			return httpState;
		}
		HttpURLConnection httpConnection = URLConnectinUtils.createHttpURLConnection(MAIN_PAGE_URL);
		try{
			if(httpConnection != null){
				NodeFilter linkFilter = new NodeClassFilter(LinkTag.class);
			    
			    NodeFilter ppFilter = new NodeClassFilter(Div.class);
			    NodeFilter ppAttrFilter = new HasAttributeFilter("class", "title");
			    NodeFilter ppAndFilter = new AndFilter(ppFilter, ppAttrFilter);
			    			    
			    NodeFilter linkAndFilter = new AndFilter(linkFilter, new HasParentFilter(ppAndFilter));
				
			    Parser parser;
			    parser = new Parser(httpConnection);
				NodeList nodes = parser.extractAllNodesThatMatch(linkAndFilter);
				
				String url = null;
				for(int i = 0; i < nodes.size(); i++){
					Node node = nodes.elementAt(i);
					if(node instanceof LinkTag){
						LinkTag l = (LinkTag)node;
						String context = l.getLinkText();
						if(context.indexOf(keyStr) != -1){
							url = l.getLink();
							break;
						}
					}
				}
				URLConnectinUtils.updateCookie(httpConnection, httpState);
				httpState.setUrl(url);
				System.out.println("getUrl:" + url);
			}
		}finally{
			URLConnectinUtils.closeHttpConnection(httpConnection);
		}
		return httpState;
	}

}
