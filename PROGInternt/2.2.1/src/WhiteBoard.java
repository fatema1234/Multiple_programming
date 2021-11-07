import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.HashSet;
import java.util.Iterator;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class WhiteBoard extends JPanel {
	private HashSet<Point> hashSet = new HashSet<Point>();
	private int mouseKey = 0;
	
	public WhiteBoard() {
		setBackground(Color.white);
		addMouseListener(new Listener1());
		addMouseMotionListener(new Listener2());
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.black);
		Iterator<Point> i = hashSet.iterator();
		while (i.hasNext()) {
			Point p = i.next();
			g.fillOval(p.x, p.y, 2, 2);
		}
	}
	
	class Listener1 extends MouseAdapter {
		public void mousePressed(MouseEvent me) {
			mouseKey = me.getButton();
			if(me.getButton() == 3){
				deletePoint(me.getPoint());
			}
			else{
				addPoint(me.getPoint());
			}
		}
	}
	
	class Listener2 extends MouseMotionAdapter {
		public void mouseDragged(MouseEvent me) {
			if(mouseKey == 3){
				deletePoint(me.getPoint());
			}
			else{
				addPoint(me.getPoint());
			}
		}
	}
	
	private void addPoint(Point p) {
		hashSet.add(p);
		repaint();
	}
	
	private void deletePoint(Point p) {
		hashSet.remove(p);
		repaint();
	}
}
