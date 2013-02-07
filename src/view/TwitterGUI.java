package view;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import twitter4j.TwitterException;
import controller.TwitterController;

/**********************************************************************
 * Twitter GUI
 * 
 * @author Nick, Vincenzo, Corey, Russ
 *********************************************************************/
public class TwitterGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private TwitterController controller;
	private JFrame frame;
	private JPanel profilePanel, tweetPanel, followingPanel, followersPanel;

	private JMenuBar menuBar;
	private JMenu fileMenu, tweetMenu, aboutMenu;
	private JMenuItem exit, newTweet, delete, about;

	private JTabbedPane tabbedPane;

	private String displayName, twitterName, description, location, website;
	private ImageIcon profileImage;
	private Image profileBanner;

	// Tweet Panel
	private JButton cancel, tweetSubmit, tweetShow;
	private JLabel tweetTotal;
	private JTextArea tweetText;

	/****************************************************
	 * GUI
	 ***************************************************/
	public TwitterGUI() throws TwitterException {
		frame = new JFrame();
		setTitle("Desktop Tweets");
		setBackground(Color.WHITE);
		setUpController();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700, 450);
		setMaximumSize(new Dimension (700, 450));
		setMinimumSize(new Dimension (700, 450));
		setResizable(false);
		//setUndecorated(true); Something cool we might want to look into? (No Title Menu on Frame)
		setLocationRelativeTo(null);


		// Create components
		createProfilePanel();
		createTweetPanel();
		createFollowingPanel();
		createFollowersPanel();
		createMenu();
		createTabbedPane();

		
		setVisible(true);
	}
	
	private void setUpController() {
		controller = new TwitterController();

		if (!controller.getIsSetUp()) {
			String authUrl = controller.getAuthUrl();
			// copy to clipboard
			Toolkit.getDefaultToolkit().getSystemClipboard()
					.setContents(new StringSelection(authUrl), null);
			String pin = JOptionPane
					.showInputDialog(
							"Please follow this link to authenticate this App.\nEnter Pin:",
							authUrl);
			controller.setUpUser(pin);
		}
	}
	
	
	private void createFollowersPanel() throws TwitterException {
		followersPanel = new JPanel();
		followersPanel.setBackground(Color.WHITE);
	    JScrollPane jsp = new JScrollPane(followersPanel);  
	    jsp.createVerticalScrollBar();
	    jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    frame.setContentPane(jsp);
	    
	    long[] followers = controller.getFriendsIDs();
	}
	

	private void createFollowingPanel() throws TwitterException {
		followingPanel = new JPanel();
		followingPanel.setBackground(Color.WHITE);	
		/*
		long[] followers = controller.getFriendsIDs();
		if (followers.length > 0)
			followingPanel.add(new JLabel(Integer.toString(followers.length)));
		else
			followingPanel.add(new JLabel(" Not > 0 "));
		*/
	}
	
	private void createTweetPanel() {
		tweetPanel = new JPanel();
		tweetPanel.setBackground(Color.WHITE);
		tweetPanel.setLayout(new BoxLayout(tweetPanel, BoxLayout.Y_AXIS));

		// Instantiate vars
		cancel = new JButton("Cancel");
		tweetSubmit = new JButton("Tweet");
		tweetShow = new JButton("Show Tweets");
		tweetText = new JTextArea();

		tweetTotal = new JLabel(controller.getTweetCount() + " Tweets");
		tweetTotal.setSize(450, 300);

		tweetPanel.add(tweetTotal);
		tweetPanel.add(cancel);
		tweetPanel.add(tweetSubmit);
		tweetPanel.add(tweetShow);
		tweetPanel.add(tweetText);

		tweetSubmit.addActionListener(this);
	}
	
	private void createProfilePanel() {
		// Get User Information
		displayName = controller.getDisplayName();
		twitterName = controller.getTwitterName();
		description = controller.getDescription();
		location = controller.getLocation();
		website = controller.getWebsite();
		profileImage = controller.getProfileImage();
		profileBanner = controller.getProfileBanner();	
		
		
		/** INFO PANEL */
		JPanel infoPanel = new JPanel() {
			protected void paintComponent(Graphics g) {
					g.drawImage(profileBanner, 80, 40, null);
					super.paintComponent(g);
				}
		};
		infoPanel.setOpaque(false);
		infoPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.NONE;
			
		// Profile Image
		ImageIcon img = controller.getProfileImage();
		c.gridy = 0; 
		JButton profImgBtn = new JButton();
		profImgBtn.setPreferredSize(new Dimension(img.getIconWidth()+1,img.getIconHeight()+1));
		profImgBtn.setBackground(Color.WHITE);
		profImgBtn.setBorder(new LineBorder(Color.WHITE, 2));
		profImgBtn.setFocusable(false);
		profImgBtn.setIcon(img);
		infoPanel.add(profImgBtn, c);
		
		// Display Name
		c.ipady = 5;
		c.gridy = 1;
		JLabel displayNameLbl = new JLabel(displayName);
		displayNameLbl.setFont(new Font("arial", Font.BOLD, 24));
		displayNameLbl.setForeground(Color.WHITE);
		infoPanel.add(displayNameLbl, c);
			
		// Twitter Name
		c.gridy = 2;
		JLabel twitterNameLbl = new JLabel(twitterName);		
		twitterNameLbl.setFont(new Font("arial", Font.PLAIN, 15));
		twitterNameLbl.setForeground(Color.WHITE);
		infoPanel.add(twitterNameLbl, c);
		c.ipady =  30;	//more padding
		
		//Description
		c.gridy = 3;
		JLabel descriptionLbl = new JLabel(description);
		descriptionLbl.setFont(new Font("arial", Font.PLAIN, 15));
		descriptionLbl.setForeground(Color.WHITE);
		infoPanel.add(descriptionLbl, c);	
		c.ipady = 5; //less padding
		
		// Location
		c.gridy = 4;
		JLabel locationLbl = new JLabel(location);
		locationLbl.setFont(new Font("arial", Font.BOLD, 15));
		locationLbl.setForeground(Color.WHITE);
		infoPanel.add(locationLbl, c);
		
		//Website
		c.gridy = 5;
		JLabel websiteLbl = new JLabel(website);
		websiteLbl.setFont(new Font("arial", Font.PLAIN, 14));
		websiteLbl.setForeground(Color.WHITE);
		infoPanel.add(websiteLbl, c);
		
		
		/** COUNT PANEL */
		JPanel countPanel = new JPanel();
		countPanel.setLayout(new BoxLayout(countPanel, BoxLayout.X_AXIS));
		countPanel.setBackground(Color.WHITE);
		
		Font f = new Font("Arial", Font.ITALIC, 12);
		JLabel numTweets = new JLabel(controller.getTweetCount() + " Tweets    ");
		numTweets.setFont(f); 
		JLabel numFollowing = new JLabel(controller.getFriendsCount() + " Following    ");
		numFollowing.setFont(f);
		JLabel numFollowers = new JLabel(controller.getFollowersCount() + " Followers    ");
		numFollowers.setFont(f);
		
		countPanel.add(numTweets);
		countPanel.add(numFollowing);
		countPanel.add(numFollowers);
		
		/** PROFILE PANEL */
		profilePanel = new JPanel();
		profilePanel.setLayout(new BorderLayout());
		profilePanel.setBackground(Color.WHITE);
		profilePanel.add(infoPanel, BorderLayout.CENTER);
		profilePanel.add(countPanel, BorderLayout.SOUTH);
	}

	
	private void createMenu() {
		menuBar = new JMenuBar();

		// File Menu
		fileMenu = new JMenu("File");
		exit = new JMenuItem("Exit");
		exit.addActionListener(this);
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,Event.CTRL_MASK));
		fileMenu.add(exit);

		// Tweet Menu
		tweetMenu = new JMenu("Tweet");
		newTweet = new JMenuItem("New Tweet");
		newTweet.addActionListener(this);
		newTweet.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));
		delete = new JMenuItem("Delete");
		delete.addActionListener(this);
		delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, Event.CTRL_MASK));
		tweetMenu.add(newTweet);
		tweetMenu.add(delete);

		// About Menu
		aboutMenu = new JMenu("About");
		about = new JMenuItem("About Desktop Tweets");
		about.addActionListener(this);
		about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				Event.CTRL_MASK));
		aboutMenu.add(about);

		// Add To MenuBar
		menuBar.add(fileMenu);
		menuBar.add(tweetMenu);
		menuBar.add(aboutMenu);
		setJMenuBar(menuBar);
	}

	private void createTabbedPane() {
		tabbedPane = new JTabbedPane(JTabbedPane.BOTTOM);
		tabbedPane.addTab("Profile", profilePanel);
		tabbedPane.addTab("Tweet", tweetPanel);
		tabbedPane.addTab("Followers", followersPanel);
		tabbedPane.addTab("Following", followingPanel);
		add(tabbedPane);
	}

	/**
	 * This is where all actions should be delegated and 
	 * sent to the controller
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == exit) {
			System.exit(0);
		}

		if (source == tweetSubmit) {
			if (!controller.tweet(tweetText.getText())) {
				JOptionPane
						.showMessageDialog(null, "Status could not be sent.");
			}
			return;
		}
	}

	public static void main(String[] args) throws TwitterException {
		new TwitterGUI();
	}
}
