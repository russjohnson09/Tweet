package model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.AbstractListModel;

import twitter4j.Status;

public class Tweets extends AbstractListModel<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Status> tweets;

	public Tweets() {
		super();
		tweets = new ArrayList<Status>();
	}

	@Override
	public String getElementAt(int i) {
		Status s = tweets.get(i);

		SimpleDateFormat df = new SimpleDateFormat();
		df.applyPattern("MM/dd/yyyy");

		String str = "Time: ";
		str += df.format(s.getCreatedAt());
		str += ", Status: ";
		str += s.getText();

		return str;
	}

	@Override
	public int getSize() {
		return tweets.size();
	}

	public void add(Status s) {
		tweets.add(s);
	}

	public long remove(int index) {
		long l = tweets.get(index).getId();
		tweets.remove(index);
		fireIntervalRemoved(this, 0, tweets.size());
		return l;

	}

}