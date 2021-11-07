import javax.swing.JComponent;

@SuppressWarnings("serial")
public abstract class Animation extends JComponent implements Runnable {
	// Default amount of time between frame updates.
		private static final int DEFAULT_MILLIS_BETWEEN_FRAMES = 30;
		
		// The thread in which the animation is running.
		private Thread animationThread;
		
		// Amount of time paused between frames
		private int pauseTime;
		
		public Animation() {
			this(DEFAULT_MILLIS_BETWEEN_FRAMES);
		}
		
		public Animation(int pauseTime) {
			assert pauseTime > 0;
			this.pauseTime = pauseTime;
		}
		
		public void start() {
			if (animationThread == null) {
				animationThread = new Thread(this);
				animationThread.start();
			}
		}
		
		public void stop() {
			if (animationThread != null) {
				animationThread = null;
			}
		}
		
		public void run() {
			// Update the display periodically.
			while (Thread.currentThread() == animationThread) {
				nextFrame();
				repaint();
				
				try {
					Thread.sleep(pauseTime);
				} catch (InterruptedException e) {
					
				}
			}
		}
		
		protected abstract void nextFrame();
}
