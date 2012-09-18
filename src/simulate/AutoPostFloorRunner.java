package simulate;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.tags.InputTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import common.HttpHtmlParserUtils;
import common.HttpState;
import common.URLConnectinUtils;

public class AutoPostFloorRunner extends AutoPostRunner {
	
	private int[] floor = null;
	

	public AutoPostFloorRunner(HttpState httpState, int[] floor, String reply) {
		super(httpState, reply);
		this.floor = floor;
	}
	
	public HttpState submit(String rc) throws Exception{
		String result = paramStr();
//		System.out.println(result);
		replySubmit(result);
		return this.httpState;
	}
	
	protected int getCurFloor(String rc) throws ParserException, IOException{
//		long start = System.currentTimeMillis();
		try{
			replyContent = rc;
			initParamPareValue(replyContent);
			String curReplyCount = paramPair.get("post_total");
			if((curReplyCount != null) && (!curReplyCount.equals(""))){
				int curFloor = Integer.valueOf(curReplyCount) + 1;
				System.out.println("当前楼层：" + curFloor);
			    return curFloor;
			}
			return 0;			
		}finally{
//		   long end = System.currentTimeMillis();
//		   System.out.println("getCurFloor time: " + (end - start));
		}
		
	}
	
	public String getReplyContent(int floor){
		String rp = String.valueOf(floor);
		rp = "两个黄鹂鸣翠柳，珂兰钻石五周年";
		return rp;
	}
	
	public boolean isReach(int floor) throws ParserException, IOException{
		String rp = getReplyContent(floor);
		int curFloor = getCurFloor(rp);
		return curFloor >= floor;
		
	}
	
	public HttpState call() throws Exception {
		if(httpState.getUrl() == null){
			return httpState;
		}
//		String rp = getReplyContent(553);
//		int curentFloor = getCurFloor(rp);
//		submit(getReplyContent(1));
		
		for(int curFloor: floor){
			int preFloor = curFloor - 5;

			String rp = getReplyContent(curFloor);
			int curentFloor = getCurFloor(rp);
			if(curFloor == 0){
				System.out.println("该帖已不能回复!");
				return httpState;
			}
				
			while((curentFloor < preFloor) && (curentFloor > 0)){
				curentFloor = getCurFloor(rp);
			}

			if((curFloor > curentFloor)){
				int minusFloor = curFloor - curentFloor;
				if(minusFloor > 2){
					Thread.sleep(100 * (minusFloor - 2));
				}
				submit(rp);
			}
			
			Thread.sleep(1000);
			
		}
		
		
		return httpState;
	}

}
