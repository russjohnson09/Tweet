package model;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/****************************************************
 * Tweets Class.
 ***************************************************/
public class Tweets extends AbstractListModel<String> {

    /** twitter. */
    private Twitter t;

    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** List tweets. */
    private List<Status> tweets;

    /****************************************************
     * Tweets Constructor.
     * @param t Twitter
     ***************************************************/
    public Tweets(final Twitter t) {
        super();
        this.t = t;
        tweets = new ArrayList<Status>();
    }

    /****************************************************
     * returns Tweets arraylist.
     * @return tweets
     ***************************************************/
    public final List<Status> getTweets() {
        return tweets;
    }

    /****************************************************
     * gets element at specified location.
     * @param i int
     * @return string
     ***************************************************/
    @Override
    public final String getElementAt(final int i) {
        Status s = tweets.get(i);

        SimpleDateFormat df = new SimpleDateFormat();
        df.applyPattern("MM/dd/yyyy");

        String str = s.getUser().getName() + " (" + df.format(s.getCreatedAt())
                + ") --- " + s.getText();
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
     * @param s
     *            Status
     ***************************************************/
    public final void add(final Status s) {
        tweets.add(s);
        fireIntervalAdded(this, 0, tweets.size());
    }

    /****************************************************
     * Removes Tweet.
     * @param index int
     * @return l long
     ***************************************************/
    public final long remove(final int index) {
        long l = tweets.get(index).getId();
        tweets.remove(index);
        fireIntervalRemoved(this, 0, tweets.size());
        return l;
    }

    /****************************************************
     * homeTimeline.
     ***************************************************/
    public final void homeTimeline() {
        List<Status> statuses = null;
        try {
            statuses = t.getHomeTimeline();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        if (statuses != null) {
            tweets.clear();
            for (Status s : statuses) {
                tweets.add(s);
                fireContentsChanged(this, 0, tweets.size());
            }
        }
    }

    /****************************************************
     * User Timeline.
     ***************************************************/
    public final void userTimeline() {
        List<Status> statuses = null;
        try {
            statuses = t.getUserTimeline();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        if (statuses != null) {
            tweets.clear();
            for (Status s : statuses) {
                tweets.add(s);
                fireContentsChanged(this, 0, tweets.size());
            }
        }
    }

    /****************************************************
     * Show Id.
     * @param index int
     * @return l long
     ***************************************************/
    public final long showId(final int index) {
        return tweets.get(index).getId();
    }
}
