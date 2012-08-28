package simulate.submit;

public class AutoPostSubmitConfig {
	
	private long loginInterval = 500;
	
	private long parseURLInterval = 500;
	
	private long postReplyInterval = 4000;
	

	public long getLoginInterval() {
		return loginInterval;
	}



	public void setLoginInterval(long loginInterval) {
		this.loginInterval = loginInterval;
	}



	public long getParseURLInterval() {
		return parseURLInterval;
	}



	public void setParseURLInterval(long parseURLInterval) {
		this.parseURLInterval = parseURLInterval;
	}



	public long getPostReplyInterval() {
		return postReplyInterval;
	}



	public void setPostReplyInterval(long postReplyInterval) {
		this.postReplyInterval = postReplyInterval;
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
