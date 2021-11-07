import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class Snow {
	// The location of the flake
		private int left;
		private int top;
		
		// The lowest visible point on the screen
		private int screenHeight;
		
		// How much to fall in each frame
		private int fallOffset;
		
		// The picture of the flake
		private ImageIcon snow;
		

		public Snow(ImageIcon snow, int left, int screenHeight, int fallOffset) {
			this.snow = snow;
			this.left = left;
			this.top = -snow.getIconHeight();
			this.screenHeight = screenHeight;
			this.fallOffset = fallOffset;
		}
		
		public void nextFrame() {

			if (top < screenHeight) {
				top = top + fallOffset;
			}
		}
		

		public void paint (JComponent component, Graphics g) {
			snow.paintIcon(component, g, left, top);
		}
}
