package model;

import java.awt.Image;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

/****************************************************
 * Twitter model.
 ***************************************************/
public class TwitterModel {

    Twitter t;

    private String name;
    private String screenName;
    private String description;
    private String url;
    private String location;
    private ImageIcon profileImage;
    private ImageIcon smallerProfileImage;

    private Image profileBanner;

    private int tweetCount = 0;

    private long[] friendsIDs;

    private int friendsCount;

    private List<User> followers;

    /****************************************************
     * Twitter model.
     ***************************************************/

    public TwitterModel(Twitter twitter) {
        t = twitter;
        followers = new ArrayList<User>();
        refresh();

    }

    public void refresh() {
        try {
            User u = t.showUser(t.getId());
            name = u.getName();
            screenName = "@" + u.getScreenName();
            description = u.getDescription();
            url = u.getURL();
            location = u.getLocation();
            tweetCount = u.getStatusesCount();

            friendsIDs = t.getFriendsIDs(-1).getIDs();
            friendsCount = u.getFriendsCount();

            long[] list = t.getFollowersIDs(-1).getIDs();
            for (long l : list)
                followers.add(t.showUser(l));

            refreshImage(u);

        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (TwitterException e) {
            e.printStackTrace();
        }

    }

    private void refreshImage(User u) {
        try {
            profileImage = new ImageIcon(new URL(u.getBiggerProfileImageURL()));
            smallerProfileImage = new ImageIcon(new URL(u.getProfileImageURL()));
            profileBanner = (new ImageIcon(new URL(u.getProfileBannerURL()))
                    .getImage());
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (profileBanner == null) {
            profileBanner = (new ImageIcon("src/banner.jpeg")).getImage();
        }

    }

    public String getName() {
        return name;

    }

    public String getScreenName() {
        return screenName;
    }

    public String getScreenName(long l) {
        try {
            return "@" + t.showUser(l).getScreenName();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getDescription() {
        return description;
    }

    public String getURL() {
        return url;
    }

    public String getLocation() {
        return location;
    }

    public ImageIcon getProfileImage() {
        return profileImage;
    }

    public ImageIcon getSmallerProfileImage() {
        return smallerProfileImage;
    }

    public ImageIcon getSmallerProfileImage(long userId) {
        try {
            return new ImageIcon(new URL(t.showUser(userId)
                    .getProfileImageURL()));
        } catch (MalformedURLException e) {
            return null;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Image getProfileBanner() {
        return profileBanner;
    }

    public boolean updateStatus(String str) {
        try {
            t.updateStatus(str);
            return true;

        } catch (TwitterException e) {
            return false;
        }
    }

    public boolean tweetImage(File img, String message) {
        try {
            StatusUpdate status = new StatusUpdate(message);
            status.setMedia(img);
            t.updateStatus(status);
            return true;

        } catch (TwitterException e) {
            return false;
        }
    }

    public int getTweetCount() {
        return tweetCount;
    }

    public long[] getFriendsIDs() {
        return friendsIDs;
    }

    public int getFriendsCount() {
        return friendsCount;
    }

    public List<User> getFollowers() {
        return followers;
    }

}
