package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class LoadingPanel extends JPanel {
    
    private static final long serialVersionUID = 1L;
    
    int loadPercent;
    
    JPanel loadBar, unloadBar, bottomBar;
    
    final int BAR_HEIGHT = 30;


    public LoadingPanel() {
        loadPercent = 0;
        super.setVisible(true);
        super.setLayout(new BorderLayout());
        super.setOpaque(false);
        Font font = new Font("arial", Font.BOLD, 20);
        
        JLabel waitLbl = new JLabel ("Please Wait");
        waitLbl.setFont(font);
        waitLbl.setForeground(Color.WHITE);
        JLabel loadLbl = new JLabel("The Application is Loading");
        loadLbl.setForeground(Color.WHITE);
        loadLbl.setFont(font);
        
        JPanel loadPnl = new JPanel();
        loadPnl.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        loadPnl.add(waitLbl, gbc);
        loadPnl.setOpaque(false);
        gbc.gridy = 1;
        loadPnl.add(loadLbl, gbc);
        super.add(loadPnl, BorderLayout.CENTER);
        
        loadBar = new JPanel();
        loadBar.setPreferredSize(new Dimension(0,BAR_HEIGHT));
        loadBar.setBackground(Color.GRAY);

        bottomBar = new JPanel();
        bottomBar.setLayout(new BoxLayout(bottomBar, BoxLayout.X_AXIS));
        bottomBar.setBackground(Color.GRAY);
        bottomBar.add(loadBar);
        super.add(bottomBar, BorderLayout.SOUTH);
        
        
    }
    
    protected void paintComponent(final Graphics g) {
        g.drawImage(new ImageIcon("src/background.jpeg").getImage(), 0, 0, null);
        super.paintComponent(g);
    }
    
    
    public void incrementLoadingScreen() {
        if (loadPercent > 0) 
            loadBar.setBackground(Color.GREEN);
        loadBar.setSize(loadPercent, BAR_HEIGHT);
        
        loadPercent += super.getWidth()/10 + 1;
        
    }
}