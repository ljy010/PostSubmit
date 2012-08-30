package simulate.test;

import java.util.concurrent.Callable;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import simulate.AutoLoginRunner;
import simulate.AutoParseURLRunner;

import common.DateUtils;
import common.HttpState;

public class PerformTest {
	
	public static long effectTest(Callable<HttpState> run){
		
		try {
			long startTime = System.currentTimeMillis();
			run.call();
			long endTime = System.currentTimeMillis();
			return endTime - startTime;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	public static long testLoginTime(){
		HttpState state = new HttpState(AutoLoginRunner.LOGIN_USER_LJY);
		AutoLoginRunner login = new AutoLoginRunner(state);
		return effectTest(login);
	}
	
	public static long testParseTime(){
		HttpState state = new HttpState(null);
		AutoParseURLRunner parse = new AutoParseURLRunner(state, "9ÔÂ1ÈÕÇ©µ½");
		return effectTest(parse);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		double t = 0;
		for(int i = 0; i < 20; i++){
		  t = t + testParseTime();	
		}
		System.out.println("avg time : " + (t / 20));
	}

}
