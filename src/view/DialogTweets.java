package view;

import java.awt.BorderLayout;
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

		setLayout(new BorderLayout());

		this.tweets = tweets;

		setTitle("Tweets");

		setSize(700, 700);
		setLocationRelativeTo(parent);

		list = new JList<String>(tweets);

		JScrollPane scrollpane = new JScrollPane(list);
		add(scrollpane, BorderLayout.CENTER);

		remove = new JButton("Remove");
		remove.addActionListener(this);

		add(remove, BorderLayout.SOUTH);

		pack();
		setVisible(true);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == remove) {
			int i = list.getSelectedIndex();
			removeList.add(tweets.remove(i));
			// dispose();
		}

	}

	public ArrayList<Long> getRemoveList() {
		return removeList;
	}

}