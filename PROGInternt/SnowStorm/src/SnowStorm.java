import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class SnowStorm extends Animation {
	// dimensions of the screen
    private static final int SCREEN_HEIGHT = 400;
    private static final int SCREEN_WIDTH = 500;
    
    // colors of the sky
    private final Color NIGHTSKY = new Color(50, 50, 100);

    // The snow that have been created.
	private static final int MAX_FLAKES = 500;
	private Snow[] flakes = new Snow[MAX_FLAKES];
	
	// The number of snow created so far.
	private int numFlakes = 0;
	
	// Create a cloud, which drops snow.
	public SnowStorm(ImageIcon aSnowPic, int x,
			int aScreenHeight) {
		new Cloud(this, aSnowPic, x, aScreenHeight);
	}

	public void nextFrame() {
		for (int i = 0; i < numFlakes; i++) {
			flakes[i].nextFrame();
		}
	}
	
	public void paint (Graphics g) {
		Dimension d = getSize();
	    int skyline = d.height*85/100 + d.height*4/100;;

		// draw solid sky, mortar, and moon
		g.setColor(NIGHTSKY);
		g.fillRect(0, 0, d.width, skyline);
		g.fillRect(0, skyline, d.width, d.height);
		g.setColor(Color.WHITE);

		// Draw the snow
		for (int i = 0; i < numFlakes; i++) {
			flakes[i].paint(this, g);
		}
	}
	
	public void add (Snow flake) {
		assert flake != null;
		if (numFlakes < MAX_FLAKES) {
			flakes[numFlakes] = flake;
			numFlakes++;
		}
	}
	
	// Creates the user interface
	public static void main(String[] args) {
		// Create the window and set its size.
		JFrame f = new JFrame();
		f.setSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		
		// Exit the application when the user closes the frame.
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		SnowStorm snowStorm = new SnowStorm(new ImageIcon("src/snow.gif"), SCREEN_WIDTH, SCREEN_HEIGHT);

		// Add the snow panel to the center of the window
		Container contentPane = f.getContentPane();
		contentPane.add(snowStorm, BorderLayout.CENTER);

		// Display the window.
		f.setVisible(true);
		
		// Start the animation
		snowStorm.start();
	}
}
 
