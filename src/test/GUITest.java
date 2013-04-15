package test;
import controller.TwitterController;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JFrame;
import javax.swing.JPanel;

/****************************************************
 * GUITest Class.
 ***************************************************/
public class GUITest {

    /****************************************************
    * Creates controller.
    ***************************************************/
    private static TwitterController controller = new TwitterController();

    /****************************************************
     * Main.
     * @param args String[]
     ***************************************************/
    public static void main(final String[] args) {
        final Image img = controller.getBackgroundImage();
        JFrame frame = new JFrame();
        JPanel panel = new JPanel() {
            protected void paintComponent(final Graphics g) {
                g.drawImage(img, 0, 0, null);
                super.paintComponent(g);
            }
        };
        panel.setOpaque(false);
        frame.add(panel);
        frame.setSize(400, 400);
        frame.setVisible(true);

    }

}
