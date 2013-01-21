package gui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import tweet.Tweet;

public class TweetDialog extends JDialog implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JTextArea tweetTxt;
	private JButton okButton;
	private JButton cancelButton;
	private Tweet tweet;

	public TweetDialog(JFrame parent, Tweet tweet) {
		super(parent, true);

		this.tweet = tweet;

		setTitle("Tweet");

		setSize(300, 300);
		setLocationRelativeTo(parent);

		JPanel txtPanel = new JPanel();

		tweetTxt = new JTextArea();
		tweetTxt.setColumns(20);
		tweetTxt.setLineWrap(true);
		tweetTxt.setRows(5);
		tweetTxt.setWrapStyleWord(true);

		txtPanel.add(new JLabel("Tweet"));
		txtPanel.add(tweetTxt);

		getContentPane().add(txtPanel, BorderLayout.CENTER);

		okButton = new JButton("OK");
		cancelButton = new JButton("CANCEL");

		JPanel buttonPanel = new JPanel();

		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);

		okButton.addActionListener(this);
		cancelButton.addActionListener(this);

		getContentPane().add(buttonPanel, BorderLayout.SOUTH);

		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == okButton) {
			tweet.tweet(tweetTxt.getText());
		}

		if (e.getSource() == cancelButton) {
		}

		this.dispose();

	}

}
