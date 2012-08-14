package simulate.submit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import simulate.AutoLoginRunner;
import simulate.AutoParseURLRunner;
import simulate.AutoPostRunner;

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
	
	public AutoPostSubmit(String loginUser, String titleKeyWord, String replyContent){
		this.loginUser = loginUser;
		this.titleKeyWord = titleKeyWord;
		this.replyContent = replyContent;
	}
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		AutoPostSubmit post = new AutoPostSubmit(AutoLoginRunner.LOGIN_USER_LJY, "8‘¬14»’«©ÕÀ", "«©ÕÀ¿≤«©ÕÀ¿≤π˛");
		ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.execute(post);
		executorService.shutdown();
	}

	@Override
	public void run() {
		try{
//			long start = System.currentTimeMillis();
			HttpState state = new HttpState();

			while(!state.isLogin()){
				AutoLoginRunner login = new AutoLoginRunner(state, this.loginUser);
				state = login.call();
				if(!state.isLogin()){
					Thread.sleep(500);	
					System.out.println("login....");
				}
			}
			
			while(state.getUrl() == null){
				AutoParseURLRunner url =  new AutoParseURLRunner(state, this.titleKeyWord);
				state = url.call();
				if(state.getUrl() == null){
					Thread.sleep(500);	
					System.out.println("URLParsing....");
				}
			
			}
			
			Thread.sleep(4000);	
			if((state.getUrl() != null) && (!state.getUrl().equals(""))){
				AutoPostRunner post = new AutoPostRunner(state, this.replyContent);
			    post.call();	
			}
//			long end = System.currentTimeMillis();
//			System.out.print("time:" + (end - start));
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}

}
