package ex5;

public class WaitThread extends Thread {

	// the time before a click is generated
	// THIS IS in units of 10ms, i.e. a value of 100 will wait for 100*10ms = 1 second
	public static final int WAITTIME = 70;
	
	protected boolean toberestarted = false;
	protected boolean stopped = false;

	protected WaysOfInput woi;
	
	
	public WaitThread(WaysOfInput woi) {
		this.woi = woi;
	}
	
	public void ping() {
		toberestarted = true;
	}
	
	public void stopThread() {
		stopped = true;
	}
	
	public void run() {
		while (!stopped) {
			int i = 0;
			while (i < WAITTIME) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// ignore
				}
				if (stopped)
					return;
				
				if (toberestarted) {
					i = 0;
					toberestarted = false;
				} else
					i++;
			}
			woi.dwelled();
		}
	}
}