package model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.AbstractListModel;

import twitter4j.Status;

/****************************************************
 * Tweets Class.
 ***************************************************/
public class Tweets extends AbstractListModel<String> {

    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** ArrayList tweets. */
    private ArrayList<Status> tweets;

    /****************************************************
     * Tweets Constructor.
     ***************************************************/
    public Tweets() {
        super();
        tweets = new ArrayList<Status>();
    }

    @Override
    public final String getElementAt(final int i) {
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
    /****************************************************
     * Gets size of tweet.
     ***************************************************/
    public final int getSize() {
        return tweets.size();
    }

    /****************************************************
     * Adds tweet.
     *
     * @param s
     *            Status
     ***************************************************/
    public final void add(final Status s) {
        tweets.add(s);
    }

    /****************************************************
     * Removes Tweet.
     *
     * @param index
     *            int
     * @return l long
     ***************************************************/
    public final long remove(final int index) {
        long l = tweets.get(index).getId();
        tweets.remove(index);
        fireIntervalRemoved(this, 0, tweets.size());
        return l;
    }
}
