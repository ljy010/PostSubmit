package simulate.submit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import simulate.AutoLoginRunner;

import common.DateUtils;

public class QianTui {
	
	private static String dateStr = DateUtils.getCurrentMonthDay() + "签退";;
	
	private static String getReplyUser(){
		return AutoLoginRunner.LOGIN_USER_DR;
	}
	
	private static String getReplyDate(){
		return dateStr; 
	}
	
	private static String getReplyContent(){
		return "<p><span style=\"color: rgb(255, 0, 0); " 
		 + "font-family: 'microsoft yahei'; font-size: 16px; line-height: 22px; white-space: normal; \">"
		 + "<span style=\"font-size: 16px; line-height: 22px; white-space: normal; \">"
		 + "<img style=\"width: 291px; height: 287px; visibility: visible; \" alt=\"\" src=\"http://d.c.jiehun.com.cn/origin/824/372/82437228453750866990.jpg\"/>"
		 + "LA SPOSA</span>我最想试穿的婚纱</span></p>";	
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		System.out.println(getReplyDate());
		
		AutoPostSubmitConfig submitConfig = new AutoPostSubmitConfig();
		AutoPostSubmit post = new AutoPostSubmit(getReplyUser(), getReplyDate(), getReplyContent(), submitConfig);
		ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.execute(post);
		executorService.shutdown();
	}

}
