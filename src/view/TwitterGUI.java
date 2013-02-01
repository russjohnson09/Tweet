package view;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.event.*;

import javax.swing.*;

import controller.TwitterController;


public class TwitterGUI extends JFrame implements ActionListener {
	private TwitterController controller;
	private JFrame frame;
	private JPanel profilePanel, tweetPanel, followingPanel, followersPanel;
	
	// Menu
	private JMenuBar menuBar;
	private JMenu fileMenu, tweetMenu, aboutMenu;
	private JMenuItem exit, newTweet, delete, about;
	
	// Tabbed Pane
	private JTabbedPane tabbedPane;
		
	public TwitterGUI() {
		controller = new TwitterController();
		
		frame = new JFrame("Desktop Tweets");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		// Import other panels
		menu();
		tabbedPane();
		
		setSize(700, 450);
		setVisible(true);
	}
	
	private void menu() {
		menuBar = new JMenuBar();
		
		// File Menu
		fileMenu = new JMenu("File");
		exit = new JMenuItem("Exit");
		
		// File Menu Action Listeners & Shortcuts
		exit.addActionListener(this);
		exit.setAccelerator(KeyStroke.getKeyStroke(
			KeyEvent.VK_E, Event.CTRL_MASK));
		
		// Add MenuItems to File Menu
		fileMenu.add(exit);
		
		// Tweet Menu
		tweetMenu = new JMenu("Tweet");
		newTweet = new JMenuItem("New Tweet");
		delete = new JMenuItem("Delete");
		
		// Tweet Menu Action Listeners & Shortcuts
		newTweet.addActionListener(this);
		delete.addActionListener(this);
		
		newTweet.setAccelerator(KeyStroke.getKeyStroke(
			KeyEvent.VK_N, Event.CTRL_MASK));
		delete.setAccelerator(KeyStroke.getKeyStroke(
			KeyEvent.VK_D, Event.CTRL_MASK));
		
		// Add MenuItems to Tweet Menu
		tweetMenu.add(newTweet);
		tweetMenu.add(delete);
		
		// About Menu
		aboutMenu = new JMenu("About");
		about = new JMenuItem("About Desktop Tweets");
		
		// File Menu Action Listeners & Shortcuts
		about.addActionListener(this);
		about.setAccelerator(KeyStroke.getKeyStroke(
			KeyEvent.VK_A, Event.CTRL_MASK));
		
		// Add MenuItems to File Menu
		aboutMenu.add(about);
		
		// Add all menus to MenuBar
		menuBar.add(fileMenu);
		menuBar.add(tweetMenu);
		menuBar.add(aboutMenu);
		
		setJMenuBar(menuBar);
	}
	
	private void tabbedPane() {
		tabbedPane = new JTabbedPane();
		
		tabbedPane.addTab("Profile", profilePanel);
		tabbedPane.addTab("Tweet", tweetPanel);
		tabbedPane.addTab("Following", followersPanel);
		tabbedPane.addTab("Following", followingPanel);
		
		add(tabbedPane);
	}
	
	private void profilePane() {
		
	}

	/**
	 * This is where all actions should be delegated and sent to the controller
	 * 
	 * @param ActionEvent
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if (source == exit) {
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		new TwitterGUI();
	}
}
