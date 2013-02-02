package view;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.*;
import javax.swing.*;

import controller.TwitterController;

public class TwitterGUI extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private TwitterController controller;
	private JFrame frame;
	private JPanel profilePanel, tweetPanel, followingPanel, followersPanel;

	// Menu
	private JMenuBar menuBar;
	private JMenu fileMenu, tweetMenu, aboutMenu;
	private JMenuItem exit, newTweet, delete, about;

	// Tabbed Pane
	private JTabbedPane tabbedPane;

	// Profile Panel
	String displayName, twitterName, description, location, website;
	ImageIcon profileImage;

	Image headerImage;

	// Tweet Panel
	JButton cancel, tweetSubmit, tweetShow;
	JLabel tweetTotal;
	JTextArea tweetText;

	public TwitterGUI() {
		frame = new JFrame("Desktop Tweets");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

		setUpController();

		// Create components
		createProfilePanel();
		createTweetPanel();
		createFollowingPanel();
		createFollowersPanel();

		createMenu();
		createTabbedPane();

		setSize(700, 450);
		setLocation(500, 250);
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

	private void createTweetPanel() {
		tweetPanel = new JPanel();
		tweetPanel.setBackground(Color.WHITE);
		tweetPanel.setLayout(new BoxLayout(tweetPanel, BoxLayout.Y_AXIS));

		// Instantiate vars
		cancel = new JButton("Cancel");
		tweetSubmit = new JButton("Tweet");
		tweetShow = new JButton("Show Tweets");
		tweetText = new JTextArea();

		tweetTotal = new JLabel(controller.getTweetTotal() + " Tweets");
		tweetTotal.setSize(450, 300);

		tweetPanel.add(tweetTotal);
		tweetPanel.add(cancel);
		tweetPanel.add(tweetSubmit);
		tweetPanel.add(tweetShow);
		tweetPanel.add(tweetText);
	}

	// TODO
	private void createFollowersPanel() {

	}

	private void createFollowingPanel() {

	}

	private void createProfilePanel() {
		profilePanel = new JPanel();
		profilePanel.setBackground(Color.WHITE);
		profilePanel.setLayout(new BoxLayout(profilePanel, BoxLayout.Y_AXIS));

		displayName = controller.getDisplayName();
		twitterName = controller.getTwitterName();
		description = controller.getDescription();
		location = controller.getLocation();
		website = controller.getWebsite();
		profileImage = controller.getProfileImage();
		headerImage = controller.getHeaderImage();

		profilePanel.setAlignmentX(CENTER_ALIGNMENT);
		// JLabel dispN = new JLabel(displayName);
		// dispN.setAlignmentX(CENTER_ALIGNMENT);
		// profilePanel.add(dispN);
		profilePanel.add(new JLabel(displayName));
		profilePanel.add(new JLabel(twitterName));
		profilePanel.add(new JLabel(description));
		profilePanel.add(new JLabel(location));
		profilePanel.add(new JLabel(website));

	}

	private void createMenu() {
		menuBar = new JMenuBar();

		// File Menu
		fileMenu = new JMenu("File");
		exit = new JMenuItem("Exit");

		// File Menu Action Listeners & Shortcuts
		exit.addActionListener(this);
		exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
				Event.CTRL_MASK));

		// Add MenuItems to File Menu
		fileMenu.add(exit);

		// Tweet Menu
		tweetMenu = new JMenu("Tweet");
		newTweet = new JMenuItem("New Tweet");
		delete = new JMenuItem("Delete");

		// Tweet Menu Action Listeners & Shortcuts
		newTweet.addActionListener(this);
		delete.addActionListener(this);

		newTweet.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
				Event.CTRL_MASK));
		delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
				Event.CTRL_MASK));

		// Add MenuItems to Tweet Menu
		tweetMenu.add(newTweet);
		tweetMenu.add(delete);

		// About Menu
		aboutMenu = new JMenu("About");
		about = new JMenuItem("About Desktop Tweets");

		// File Menu Action Listeners & Shortcuts
		about.addActionListener(this);
		about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				Event.CTRL_MASK));

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
