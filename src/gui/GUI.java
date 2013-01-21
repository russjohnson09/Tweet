package gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import tweet.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class GUI extends JFrame implements ActionListener {

	private JMenuBar menuBar;

	private JMenu fileMenu;

	private JMenuItem tweetMenuItem;
	private JMenuItem view;

	private JMenu editMenu;

	private JMenuItem configure;

	private Tweet tweet;

	public GUI() {
		setTitle("Tweet");
		setPreferredSize(new Dimension(600, 600));

		createMenuBar();

		setJMenuBar(menuBar);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
		setResizable(false);

		setUpTweet();

	}

	private void setUpTweet() {
		try {
			tweet = new Tweet();
		} catch (TwitterException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Twitter twitter = tweet.getTwitter();

		try {
			twitter.getOAuthAccessToken();
		} catch (TwitterException e) {
			String authUrl = tweet.getAuthUrl();
			// copy to clipboard
			Toolkit.getDefaultToolkit().getSystemClipboard()
					.setContents(new StringSelection(authUrl), null);
			String pin = JOptionPane.showInputDialog(authUrl, authUrl);
			tweet.setUpUser(pin);
		}

	}

	private void createMenuBar() {
		menuBar = new JMenuBar();

		fileMenu = new JMenu("File");
		editMenu = new JMenu("Edit");

		tweetMenuItem = new JMenuItem("Tweet");
		view = new JMenuItem("View Tweets");
		configure = new JMenuItem("Configure");

		menuBar.add(fileMenu);
		menuBar.add(editMenu);

		fileMenu.add(tweetMenuItem);
		fileMenu.add(view);

		editMenu.add(configure);

		tweetMenuItem.addActionListener(this);
		view.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == tweetMenuItem) {
			new TweetDialog(this, tweet);
		}

	}

	public static void main(String args[]) {

		new GUI();

	}

}