package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import model.Tweets;

public class DialogTweets extends JDialog implements ActionListener {

	private JButton remove;

	private JList<String> list;

	private ArrayList<Long> removeList = new ArrayList<Long>();

	private Tweets tweets;

	public DialogTweets(JFrame parent, Tweets tweets) {
		super(parent, true);

		this.tweets = tweets;

		setTitle("Tweets");

		setSize(700, 700);
		setLocationRelativeTo(parent);

		list = new JList<String>(tweets);

		JScrollPane scrollpane = new JScrollPane(list);
		add(scrollpane);

		remove = new JButton("Remove");

		add(remove);

		pack();
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == remove) {
			int i = list.getSelectedIndex();
			removeList.add(tweets.remove(i));
		}

	}

	public ArrayList<Long> getRemoveList() {
		return removeList;
	}

}