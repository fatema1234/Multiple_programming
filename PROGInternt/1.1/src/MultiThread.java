
public class MultiThread {
	public static void main(String[] args) {
		MultiThread m = new MultiThread();
		T1 t1 = new T1();
		T2 t2 = new T2();
		
		t1.start(); // start t1
		m.waitFiveSec(); // wait for five seconds
		
		
		Thread thread2 = new Thread(t2);
		thread2.start(); // start t2
		m.waitFiveSec(); // wait for five seconds
		
		t2.pause(); // break t2
		m.waitFiveSec(); // wait for five seconds

		t2.activate(); // activate t2
		m.waitFiveSec(); // wait for five seconds
		
		t1.terminate(); // stop t1
		m.waitFiveSec(); // wait for five seconds
		
		t2.terminate(); // stop t2
	}

	private void waitFiveSec() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
