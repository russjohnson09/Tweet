package model;

import java.util.ArrayList;

import javax.swing.AbstractListModel;

import twitter4j.User;

public class Users extends AbstractListModel<String> {

	private static final long serialVersionUID = 1L;

	private ArrayList<User> followers;

	public Users(ArrayList<User> f) {
		super();
		followers = f;
	}

	@Override
	public String getElementAt(int i) {
		User u = followers.get(i);

		String str = "Name: ";
		str += u.getName();
		str += ", Screen Name: ";
		str += u.getScreenName();

		return str;
	}

	@Override
	public int getSize() {
		return followers.size();
	}

	public void add(User u) {
		followers.add(u);
	}

	public long remove(int index) {
		long l = followers.get(index).getId();
		followers.remove(index);
		fireIntervalRemoved(this, 0, followers.size());
		return l;

	}

}
