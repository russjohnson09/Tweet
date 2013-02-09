package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import model.Tweets;

public class DialogTweets extends JDialog implements ActionListener {

	JList<String> list;

	public DialogTweets(JFrame parent, Tweets tweets) {
		super(parent, true);

		setTitle("Tweets");

		setSize(700, 700);
		setLocationRelativeTo(parent);

		list = new JList<String>(tweets);

		JScrollPane scrollpane = new JScrollPane(list);
		add(scrollpane);
		pack();
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}

}