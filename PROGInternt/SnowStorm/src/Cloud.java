import java.util.Random;
import javax.swing.ImageIcon;

@SuppressWarnings("serial")
public class Cloud extends Animation {
    private ImageIcon snowPic; 
    private int screenWidth, screenHeight;
    private Random snowGen;  
    private SnowStorm snowStorm;

    public Cloud(SnowStorm storm, ImageIcon aSnowPic, int aScreenWidth, int aScreenHeight) {
    	super(900);

		// save the parameters for the "run" method
    	this.snowStorm = storm;
		snowPic = aSnowPic;
		screenWidth = aScreenWidth;
		screenHeight = aScreenHeight;

		snowGen = new Random();

		start();
	}

	public void nextFrame() {
		snowStorm.add (new Snow(snowPic, snowGen.nextInt(screenWidth), screenHeight,
				snowGen.nextInt(screenWidth) * 2 / screenWidth + 2));
	}
}
