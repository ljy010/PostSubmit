package simulate.submit;

import simulate.AutoLoginRunner;
import simulate.AutoParseURLRunner;
import simulate.AutoPostRunner;
import simulate.test.PerformTest;

import common.HttpState;

/**
 * 
 * @author linjy
 * «¿…≥∑¢
 */
public class AutoPostSubmit implements Runnable {

	private String loginUser = null;
	
	private String titleKeyWord = null;
	
	private String replyContent = null;
	
	private AutoPostSubmitConfig submitConfig = null;
	
	public AutoPostSubmit(String loginUser, String titleKeyWord, String replyContent, AutoPostSubmitConfig autoPostSubmitConfig){
		this.loginUser = loginUser;
		this.titleKeyWord = titleKeyWord;
		this.replyContent = replyContent;
		this.submitConfig = autoPostSubmitConfig;
	}
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
//		AutoPostSubmit post = new AutoPostSubmit(AutoLoginRunner.LOGIN_USER_DR, "8‘¬28»’«©ÕÀ", "«©ÕÀ¿≤«©ÕÀ¿≤π˛");
//		ExecutorService executorService = Executors.newCachedThreadPool();
//		executorService.execute(post);
//		executorService.shutdown();
	}

	@Override
	public void run() {
		try{
//			long start = System.currentTimeMillis();
			HttpState state = new HttpState(this.loginUser);

			while(!state.isLogin()){
				AutoLoginRunner login = new AutoLoginRunner(state);
				state = login.call();
				if(!state.isLogin()){
					Thread.sleep(submitConfig.getLoginInterval());	
					System.out.println("login....");
				}
			}
			
			while(state.getUrl() == null){
				AutoParseURLRunner url =  new AutoParseURLRunner(state, this.titleKeyWord);
				state = url.call();
				if(state.getUrl() == null){
					Thread.sleep(submitConfig.getParseURLInterval());	
					System.out.println("URLParsing....");
				}
			
			}
			
			Thread.sleep(submitConfig.getPostReplyInterval());	
			if((state.getUrl() != null) && (!state.getUrl().equals(""))){
//				long start = System.currentTimeMillis();
				AutoPostRunner post = new AutoPostRunner(state, this.replyContent);
			    post.call();	
//			    long end = System.currentTimeMillis();
			}
//			long end = System.currentTimeMillis();
//			System.out.print("time:" + (end - start));
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

}
