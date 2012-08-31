package simulate.submit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import common.DateUtils;

import simulate.AutoLoginRunner;

public class QianDao {
	private static String dateStr = DateUtils.getCurrentMonthDay() + "ǩ��";;
	
	private static String getReplyUser(){
		return AutoLoginRunner.LOGIN_USER_DR;
	}
	
	private static String getReplyDate(){
		return dateStr;
	}
	
	private static String getReplyContent(){
		return "ǩ����ǩ���˹�";	
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		AutoPostSubmitConfig submitConfig = new AutoPostSubmitConfig();
		submitConfig.setPostReplyInterval(3500);
		AutoPostSubmit post = new AutoPostSubmit(getReplyUser(), getReplyDate(), getReplyContent(), submitConfig);
		ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.execute(post);
		executorService.shutdown();
	}

}
