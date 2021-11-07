import java.awt.BorderLayout;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Draw extends JFrame {
	private WhiteBoard whiteBoard = new WhiteBoard();
	
	public Draw() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().add(whiteBoard, BorderLayout.CENTER);

		setSize(500, 500);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Draw();
	}

}
