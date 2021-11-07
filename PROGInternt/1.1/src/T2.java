
public class T2 implements Runnable{
	private volatile boolean active;
	private boolean alive;
	
	public T2() {
		this.active = true;
		this.alive = true;
	}
	public void run() {
		while(alive) {
			while(active) {
				System.out.println("Thread T2");
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
