
public class T1 extends Thread {
	private boolean active;
	private boolean alive;
	
	public T1() {
		this.active = true;
		this.alive = true;
	}
	
	public void run() {
		while(alive) {
			while(active) {
				System.out.println("Thread T1");
			      try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
			}
		}
	}
	
	public void pause() {
		this.active = false;
	}
	
	public void terminate() {
		this.alive = false;
		this.active = false;
	}
	
	public void activate() {
		this.active = true;
	}
}
