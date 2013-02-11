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

import model.Users;

public class DialogFollowing extends JDialog implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton remove;

	private JList<String> list;

	private ArrayList<Long> removeList = new ArrayList<Long>();

	private Users users;

	public DialogFollowing(JFrame parent, Users users) {
		super(parent, true);

		setLayout(new BorderLayout());

		this.users = users;

		setTitle("Following");

		setSize(700, 700);
		setLocationRelativeTo(parent);

		list = new JList<String>(users);

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
			removeList.add(users.remove(i));
			// dispose();
		}

	}

	public ArrayList<Long> getRemoveList() {
		return removeList;
	}

}
