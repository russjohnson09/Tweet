package view;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import controller.TwitterController;

/**********************************************************************
 * Twitter GUI
 * 
 * @author Nick, Vincenzo, Corey, Russ
 *********************************************************************/
public class TwitterGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private final int FRAME_HEIGHT /*450*/= 450;
	private final int FRAME_WIDTH /*700*/ = 700;

	private TwitterController controller;
	private JFrame frame;
	private JPanel profilePanel, tweetPanel, followingPanel, followersPanel;

	private JMenuBar menuBar;
	private JMenu fileMenu, tweetMenu, aboutMenu;
	private JMenuItem exit, newTweet, delete, about;

	private JTabbedPane tabbedPane;

	// Profile Panel
	private String displayName, twitterName, description, location, website;
	private JButton followersBtn,followingBtn, tweetsBtn;
	private ImageIcon profileImage;
	private Image profileBanner, backgroundImage;

	// Tweet Panel
	private JButton cancel, tweetSubmit, tweetShow;
	private JLabel tweetTotal;
	private JTextArea tweetText;

	/****************************************************
	 * GUI
	 ***************************************************/
	public TwitterGUI() { 
		frame = new JFrame();
		setTitle("Desktop Tweets");
		setBackground(Color.WHITE);
		setUpController();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setMaximumSize(new Dimension (FRAME_WIDTH, FRAME_HEIGHT));
		setMinimumSize(new Dimension (FRAME_WIDTH, FRAME_HEIGHT));
		setResizable(false);
		//setUndecorated(true); //Something cool we might want to look into? (No Title Menu on Frame)
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
			String pin = JOptionPane.showInputDialog(
							"Please follow this link to authenticate this App.\nEnter Pin:",
							authUrl);
			controller.setUpUser(pin);
		}
	}
	
	
	private void createFollowersPanel() {
		followersPanel = new JPanel();
		followersPanel.setBackground(Color.WHITE);
	    JScrollPane jsp = new JScrollPane(followersPanel);  
	    jsp.createVerticalScrollBar();
	    jsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    frame.setContentPane(jsp);
	    
	    long[] followers = controller.getFriendsIDs();
	}
	
	
	private void createFollowingPanel() {
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
	
	@SuppressWarnings("serial")
	private void createProfilePanel() {
		// Get User Information
		displayName = controller.getDisplayName();
		twitterName = controller.getTwitterName();
		description = controller.getDescription();
		location = controller.getLocation();
		website = controller.getWebsite();
		profileImage = controller.getProfileImage();
		profileBanner = controller.getProfileBanner();	
		backgroundImage = controller.getBackgroundImage();
		
		
		/** INFO PANEL */
		JPanel infoPanel = new JPanel() {
			protected void paintComponent(Graphics g) {
					g.drawImage(profileBanner, (FRAME_WIDTH/2)-profileBanner.getWidth(null)/2, 
							FRAME_HEIGHT/3+10-profileBanner.getHeight(null)/2, null);
				}
		};
		infoPanel.setOpaque(false);
		infoPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		infoPanel.paintComponents(null);
		c.fill = GridBagConstraints.NONE;
			
		// Profile Image
		ImageIcon img = profileImage;
		c.gridy = 0; 
		JButton profImgBtn = getPlainButton();
		profImgBtn.setPreferredSize(new Dimension(img.getIconWidth()+2,img.getIconHeight()+2));
		profImgBtn.setBorder(new LineBorder(Color.WHITE, 4));
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
		c.ipady = 0;
		createDescriptionPanel(infoPanel, c); 
		
		// Location		
		c.ipady = 5; 
		c.gridy = 6;
		JLabel locationLbl = new JLabel(location);
		locationLbl.setFont(new Font("arial", Font.BOLD, 15));
		locationLbl.setForeground(Color.WHITE);
		infoPanel.add(locationLbl, c);
		
		// Website 
		c.gridy = 7;
		JLabel websiteLbl = new JLabel(website);
		websiteLbl.setFont(new Font("arial", Font.PLAIN, 14));
		websiteLbl.setForeground(Color.WHITE);
		infoPanel.add(websiteLbl, c);
		
		
		/** COUNT PANEL */
		JPanel countPanel = new JPanel();
		countPanel.setLayout(new GridBagLayout());
		GridBagConstraints cpc = new GridBagConstraints();
		cpc.fill = GridBagConstraints.NONE;
		cpc.ipadx = 30; 
		countPanel.setOpaque(false);
		//countPanel.setBackground(Color.WHITE);
		
		followersBtn = getPlainButton(); 
		followersBtn.setText("<html>" + controller.getFollowersCount() + "<br/>Followers</html>");
		followersBtn.addActionListener(this);
		followingBtn = getPlainButton();
		followingBtn.setText("<html>" + controller.getFriendsCount() + "<br/>Following</html>");
		followingBtn.addActionListener(this);
		tweetsBtn = getPlainButton();
		tweetsBtn.setText("<html>" + controller.getTweetCount() + "<br/>Tweets</html>");
		
		cpc.gridy = 0;
		JButton blankBtn1 = getPlainButton();
		blankBtn1.setBorderPainted(false);
		JButton blankBtn2 = getPlainButton();
		blankBtn2.setBorderPainted(false);
		countPanel.add(followersBtn, cpc);
		countPanel.add(new JLabel(" "), cpc);		
		countPanel.add(followingBtn, cpc);
		countPanel.add(new JLabel(" "), cpc);
		countPanel.add(tweetsBtn, cpc);
		cpc.gridy = 1;
		countPanel.add(new JLabel(" "), cpc);
		
		
		/** PROFILE PANEL */
		profilePanel = new JPanel() {
			protected void paintComponent(Graphics g) {
				g.drawImage(backgroundImage, 0, 0, null);
				super.paintComponent(g);
			}
		};
		profilePanel.setOpaque(false); // true -> hides the background image
		profilePanel.setLayout(new BorderLayout());
		profilePanel.add(infoPanel, BorderLayout.CENTER);
		profilePanel.add(countPanel, BorderLayout.SOUTH);
	}

	
	private JButton getPlainButton() {
		JButton tmp= new JButton();
		tmp.setFont(new Font("Arial", Font.ITALIC+Font.BOLD, 15));
		tmp.setBackground(Color.WHITE);
		tmp.setForeground(Color.DARK_GRAY);
		tmp.setBorder(new LineBorder(Color.GRAY, 1, true));
		tmp.setFocusable(false);
		tmp.setFocusPainted(false); 
		tmp.setRequestFocusEnabled(false);
		return tmp; 
	}
	private void createDescriptionPanel(JPanel panel, GridBagConstraints c) {

		if (description.length() < 55) {
			JLabel descriptionLbl = new JLabel(description);
			descriptionLbl.setFont(new Font("arial", Font.PLAIN, 14));
			descriptionLbl.setForeground(Color.WHITE);
			panel.add(descriptionLbl, c);
		}
		else  if (description.length() < 110) {
			String d1, d2;
			
			String[] w = description.split(" ");
			int wMid = (w.length / 2) + 1;
			int dMid = description.length() / 2;
			d1 = description.substring(0, description.indexOf(w[wMid], dMid));
			d2 = description.substring(description.indexOf(w[wMid], dMid));
			
			JLabel description1Lbl = new JLabel(d1);
			JLabel description2Lbl = new JLabel(d2);
			description1Lbl.setFont(new Font("arial", Font.PLAIN, 14));
			description1Lbl.setForeground(Color.WHITE);
			description2Lbl.setFont(new Font("arial", Font.PLAIN, 14));
			description2Lbl.setForeground(Color.WHITE);
			panel.add(description1Lbl, c);
			c.gridy = 4;
			panel.add(description2Lbl, c);
		}
		else {		
			String d1, d2, d3; 
			String[] w = description.split(" ");
			int wThird = (w.length / 3) + 1;
			int dThird = description.length() / 3;
			
			d1 = description.substring(0, description.indexOf(w[wThird], dThird));
			d2 = description.substring(description.indexOf(w[wThird], dThird),
									   description.indexOf(w[wThird*2], dThird*2));
			d3 = description.substring(description.indexOf(w[wThird*2], dThird*2));
			
			JLabel description1Lbl = new JLabel(d1);
			description1Lbl.setFont(new Font("arial", Font.PLAIN, 14));
			description1Lbl.setForeground(Color.WHITE);
			JLabel description2Lbl = new JLabel(d2);
			description2Lbl.setFont(new Font("arial", Font.PLAIN, 14));
			description2Lbl.setForeground(Color.WHITE);
			JLabel description3Lbl = new JLabel(d3);
			description3Lbl.setFont(new Font("arial", Font.PLAIN, 14));
			description3Lbl.setForeground(Color.WHITE);
			
			panel.add(description1Lbl, c);
			c.gridy = 4;
			panel.add(description2Lbl, c);
			c.gridy = 5;
			panel.add(description3Lbl, c);
		}
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
		about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, Event.CTRL_MASK));
		aboutMenu.add(about);

		// Add To MenuBar
		menuBar.add(fileMenu);
		menuBar.add(tweetMenu);
		menuBar.add(aboutMenu);
		setJMenuBar(menuBar);
	}

	private void createTabbedPane() {
		//UIManager.put("TabbedPane.selected",  Color.WHITE);
		UIManager.put("TabbedPane.contentAreaColor", Color.WHITE);
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setFocusable(false);
		tabbedPane.setBackground(Color.WHITE);
		tabbedPane.setForeground(Color.BLACK);
		tabbedPane.setFont(new Font("arial", Font.BOLD, 14));
		tabbedPane.setTabLayoutPolicy(JTabbedPane.CENTER);
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

		if (source == exit)
			System.exit(0);
		
		if (source == newTweet)
			tabbedPane.setSelectedComponent(tweetPanel);

		if (source == tweetSubmit)
			if (!controller.tweet(tweetText.getText()))
				JOptionPane.showMessageDialog(null, "Status could not be sent.");
		
		if (source == about)
			JOptionPane.showMessageDialog(null,  "This is a desktop twitter application");
		
		if (source == followersBtn)
			tabbedPane.setSelectedComponent(followersPanel);
		
		if (source == followingBtn)
			tabbedPane.setSelectedComponent(followingPanel);
	}

	public static void main(String[] args) {
		new TwitterGUI();
	}
}
