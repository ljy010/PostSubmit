package common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	private static String DEFAULT_MONTH_PATERN = "M‘¬dd»’";
	
	public static String getCurrentMonthDay(){
		DateFormat dateFormat = new SimpleDateFormat(DEFAULT_MONTH_PATERN);
		return dateFormat.format(new Date());
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
