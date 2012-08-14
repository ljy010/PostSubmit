package simulate.submit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import simulate.AutoLoginRunner;
import simulate.AutoPostFloorRunner;

import common.HttpState;

/**
 * ���ض�¥
 * @author linjy
 *
 */

public class AutoGrabFloorSubmit implements Runnable {
	
	private int [] floors = null;
	
	private String loginUser = null;
	
	private String getURL = null;
	
	public AutoGrabFloorSubmit(String loginUser, String getURL, int[] floors){
		this.loginUser = loginUser;
		this.getURL = getURL;
		this.floors = floors;
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
			
			state.setUrl(this.getURL);
			
			if((state.getUrl() != null) && (!state.getUrl().equals(""))){
				AutoPostFloorRunner postFloor = new AutoPostFloorRunner(state, this.floors, null);
				postFloor.call();	
			}
//			long end = System.currentTimeMillis();
//			System.out.print("time:" + (end - start));
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String url = "http://bj.jiehun.com.cn/bbs/topic/29761.html";
		int [] floors = {};
		AutoGrabFloorSubmit post = new AutoGrabFloorSubmit(AutoLoginRunner.LOGIN_USER_LJY, url, floors);
		ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.execute(post);
		executorService.shutdown();

	}

}