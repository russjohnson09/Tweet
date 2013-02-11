package model;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

import twitter4j.User;

public class Users extends AbstractListModel<String> {

	private static final long serialVersionUID = 1L;

	private ArrayList<User> users;

	public Users(ArrayList<User> f) {
		super();
		users = f;
	}

	@Override
	public String getElementAt(int i) {
		User u = users.get(i);

		String str = "Name: ";
		str += u.getName();
		str += ", Screen Name: ";
		str += u.getScreenName();

		return str;
	}

	@Override
	public int getSize() {
		return users.size();
	}

	public void add(User u) {
		users.add(u);
	}

	public long remove(int index) {
		long l = users.get(index).getId();
		users.remove(index);
		fireIntervalRemoved(this, 0, users.size());
		return l;

	}

}
