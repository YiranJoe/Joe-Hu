package Class;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JFrame;

public class ShapeRunner {
	
	private Shape[] shapes;
	
	// this method should move all the shapes in the list
	public void moveShapes() {
		for(int i=0;i<shapes.length;i++) {
			shapes[i].move(1, 1);
		}
		// your code here
	}
	
	// fill in your shape list here
	public void setup() {
		shapes=new Shape[3];
		shapes[0]=new Rect(70,70,10,30);
		shapes[1]=new Oval(90,90,15);
		shapes[2]=new Square(110,110,30);
		// your code here
	}
	
	// DON'T TOUCH BELOW CODE
	
	public void run() {
		while (true ) {
			moveShapes();
			frame.repaint();
			try {
				Thread.sleep(75);
			} catch (Exception ex) {}
		}
	}
	
	public static void main(String[] args) {
		new ShapeRunner(); 
	}
	private JFrame frame;
	public ShapeRunner() {
		setup();
		frame = new JFrame() {
			public void paint(Graphics g) {
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, 600, 600);
				
				for (Shape s: shapes)
					s.draw(g);
			}
		};
		frame.setSize(600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		run();
	}
}