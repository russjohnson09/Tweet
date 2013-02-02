package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import controller.TwitterController;

/***********************************************************************
 * 
 * Twittter GUI
 * 
 **********************************************************************/
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
	
	String displayName, twitterName, description, location, website;
	ImageIcon profileImage;
	Image headerImage;
	
		

	public TwitterGUI() {
		controller = new TwitterController();
		
		frame = new JFrame ("Desktop Tweets");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		// Create components
		createProfilePanel();
		createTweetPanel();
		createFollowingPanel();
		createFollowersPanel();
		
		createMenu();
		createTabbedPane();
		
		setSize(700, 450);
		setLocation(500, 250);
		setVisible(true);
	}
	
	
	
	
	
	
	
	//TODO
	private void createTweetPanel() {
			tweetPanel = new JPanel();
			tweetPanel.setBackground(Color.WHITE);
			
	}
		
	private void createFollowersPanel() {
		followersPanel = new JPanel();
		followersPanel.setBackground(Color.WHITE);
			
			
	}
		
	private void createFollowingPanel() {
		followingPanel = new JPanel();
		followingPanel.setBackground(Color.WHITE);

			
	}
		
	
	/*******************************************
	 * Nick O is working on this method 
	 ******************************************/	
	private void createProfilePanel() {
		profilePanel = new JPanel();
		profilePanel.setLayout(new BorderLayout());
		
		/************************ INFO PANEL ***************************/
		JPanel infoPanel = new JPanel();
		infoPanel.setBackground(Color.WHITE);
		infoPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
			
		// Get User Information
		displayName = controller.getDisplayName();
		twitterName = controller.getTwitterName();
		description = controller.getDescription();
		location = controller.getLocation();
		website = controller.getWebsite();
		profileImage = controller.getProfileImage();
		headerImage = controller.getHeaderImage();
		
		c.gridy = 0;
		//add profile image here
		
		
		// Display Name
		c.ipady = 5;
		c.gridy = 1;
		JLabel displayNameLbl = new JLabel(displayName);
		displayNameLbl.setFont(new Font("arial", Font.BOLD, 24));
		infoPanel.add(displayNameLbl, c);
			
		// Twitter Name
		c.gridy = 2;
		JLabel twitterNameLbl = new JLabel(twitterName);		
		twitterNameLbl.setFont(new Font("arial", Font.PLAIN, 15));
		infoPanel.add(twitterNameLbl, c);
		
		c.ipady =  15;	//more space in between
		
		//Description
		c.gridy = 3;
		JLabel descriptionLbl = new JLabel(description);
		descriptionLbl.setFont(new Font("arial", Font.PLAIN, 15));
		infoPanel.add(descriptionLbl, c);	
		
		c.ipady = 5; //less space in between
		
		// Location
		c.gridy = 4;
		JLabel locationLbl = new JLabel(location);
		locationLbl.setFont(new Font("arial", Font.BOLD, 15));
		infoPanel.add(locationLbl, c);
		
		
		//Website
		c.gridy = 5;
		JLabel websiteLbl = new JLabel(website);
		websiteLbl.setFont(new Font("arial", Font.PLAIN, 14));
		infoPanel.add(websiteLbl, c);
		
		
		
		
		/***************************** COUNT PANEL ***********************************/
		JPanel countPanel = new JPanel();
		countPanel.setLayout(new BoxLayout(countPanel, BoxLayout.X_AXIS));

		countPanel.setBackground(Color.WHITE);
		JLabel numTweets = new JLabel(controller.getNumberTweets() + " Tweets    ");
		JLabel numFollowing = new JLabel(controller.getNumberFollowing() + " Following    ");
		JLabel numFollowers = new JLabel(controller.getNumberFollowers() + " Followers    ");
		
		countPanel.add(numTweets);
		countPanel.add(numFollowing);
		countPanel.add(numFollowers);
		
		
		
		
		/**************************** PROFILE PANEL **********************************/
		profilePanel.add(infoPanel, BorderLayout.CENTER);
		profilePanel.add(countPanel, BorderLayout.SOUTH);

		
			
			
		}
		
		
		
		
		
	
	
	
	
	
	
	
	
	private void createMenu() {
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
	
	private void createTabbedPane() {
		tabbedPane = new JTabbedPane();
		
		tabbedPane.addTab("Profile", profilePanel);
		tabbedPane.addTab("Tweet", tweetPanel);
		tabbedPane.addTab("Followers", followersPanel);
		tabbedPane.addTab("Following", followingPanel);
		
		add(tabbedPane);
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
