package test;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.*;
import controller.TwitterController;


public class GUITest {

	private static TwitterController controller = new TwitterController();
	
	
	public static void main(String[] args) {
		final Image img = controller.getBackgroundImage();
		JFrame frame = new JFrame();
		JPanel panel = new JPanel() {
			protected void paintComponent(Graphics g) {
				g.drawImage(img, 0, 0, null);
				super.paintComponent(g);
			}
		};
		panel.setOpaque(false);
		frame.add(panel);
		frame.setSize(400,400);
		frame.setVisible(true);

	}

}
