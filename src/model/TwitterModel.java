package model;

import java.awt.Image;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import twitter4j.DirectMessage;
import twitter4j.GeoLocation;
import twitter4j.GeoQuery;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

/****************************************************
 * Twitter model.
 ***************************************************/
public class TwitterModel {

    /** Twitter. */
    private Twitter t;

    /** name. */
    private String name;

    /**  screen name. */
    private String screenName;

    /** description. */
    private String description;

    /** url. */
    private String url;

    /** location. */
    private String location;

    /** profileImage. */
    private ImageIcon profileImage;

    /** smaller image. */
    private ImageIcon smallerProfileImage;

    /** profile banner. */
    private Image profileBanner;

    /** tweet Count. */
    private int tweetCount = 0;

    /** friends ID. */
    private long[] friendsIDs;

    /** followers. */
    private List<User> followers;

    /** following. */
    private List<User> following;

    /** followers count. */
    private int followersCount;

    /** friends Count. */
    private int friendsCount;

    /** trending. */
    private Trends trending;

    /** homeTimeline. */
    private Tweets homeTimeline;

    /** userTimeline. */
    private Tweets userTimeline;

    /** message. */
    private List<DirectMessage> messages;

    /****************************************************
     * Twitter model.
     * @param twitter Twitter
     ***************************************************/
    public TwitterModel(final Twitter twitter) {
        t = twitter;
        refresh();

    }

    /****************************************************
     * Refresh.
     ***************************************************/
    public final void refresh() {
        List<Status> statuses;
        followers = new ArrayList<User>();
        following = new ArrayList<User>();
        homeTimeline = new Tweets(t);
        userTimeline = new Tweets(t);
        try {
            User u = t.showUser(t.getId());
            name = u.getName();
            screenName = "@" + u.getScreenName();
            description = u.getDescription();
            url = u.getURL();
            location = u.getLocation();
            tweetCount = u.getStatusesCount();
            friendsCount = u.getFriendsCount();
            followersCount = u.getFollowersCount();
            trending = t.getPlaceTrends(1);

            statuses = t.getHomeTimeline();
            if (statuses != null) {
                for (Status s : statuses) {
                    homeTimeline.add(s);
                }
            }
            statuses = t.getUserTimeline();
            if (statuses != null) {
                for (Status s : statuses) {
                    userTimeline.add(s);
                }
            }

            friendsIDs = t.getFriendsIDs(-1).getIDs();

            long[] list = t.getFollowersIDs(-1).getIDs();
            for (long l : list) {
                followers.add(t.showUser(l));
            }
            list = t.getFriendsIDs(-1).getIDs();
            for (long l : list) {
                following.add(t.showUser(l));
            }
            messages = t.getDirectMessages();

            refreshImage(u);

        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (TwitterException e) {
            e.printStackTrace();
        }

    }

    /****************************************************
     * Refresh Image.
     * @param u User
     ***************************************************/
    private void refreshImage(final User u) {
        try {
            profileImage = new ImageIcon(new URL(u.getBiggerProfileImageURL()));
            smallerProfileImage = new ImageIcon(
                    new URL(u.getProfileImageURL()));
            profileBanner = (new ImageIcon(new URL(u.getProfileBannerURL()))
                    .getImage());
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            // e.printStackTrace();
            System.out.println();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (profileBanner == null) {
            profileBanner = (new ImageIcon("src/banner.jpeg")).getImage();
        }

    }

    /****************************************************
     * getName.
     * @return name
     ***************************************************/
    public final String getName() {
        return name;
    }

    /****************************************************
     * get Screen Name.
     * @return screenName
     ***************************************************/
    public final String getScreenName() {
        return screenName;
    }

    /****************************************************
     * get Screen Name.
     * @param l long
     * @return name
     ***************************************************/
    public final String getScreenName(final long l) {
        try {
            return "@" + t.showUser(l).getScreenName();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /****************************************************
     * get Description.
     * @return description
     ***************************************************/
    public final String getDescription() {
        return description;
    }

    /****************************************************
     * get URL.
     * @return url
     ***************************************************/
    public final String getURL() {
        return url;
    }

    /****************************************************
     * get Location.
     * @return location
     ***************************************************/
    public final String getLocation() {
        return location;
    }

    /****************************************************
     * get profile Image.
     * @return image
     ***************************************************/
    public final ImageIcon getProfileImage() {
        return profileImage;
    }

    /****************************************************
     * get small image.
     * @return smaller image
     ***************************************************/
    public final ImageIcon getSmallerProfileImage() {
        return smallerProfileImage;
    }

    /****************************************************
     * get smaller image.
     * @param userId long
     * @return smaller image
     ***************************************************/
    public final ImageIcon getSmallerProfileImage(final long userId) {
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

    /****************************************************
     * get profile banner.
     * @return banner
     ***************************************************/
    public final Image getProfileBanner() {
        return profileBanner;
    }

    /****************************************************
     * update status.
     * @param str string
     * @return boolean
     ***************************************************/
    public final boolean updateStatus(final String str) {
        try {
            t.updateStatus(str);
            tweetCount++;
            return true;

        } catch (TwitterException e) {
            return false;
        }
    }

    /****************************************************
     * tweetImage.
     * @param img File
     * @param message String
     * @return description
     ***************************************************/
    public final boolean tweetImage(final File img, final String message) {
        try {
            StatusUpdate status = new StatusUpdate(message);
            double[] location = getCoords();
            GeoLocation gl = new GeoLocation(location[0], location[1]);
            status.setLocation(gl);
            status.setMedia(img);
            t.updateStatus(status);
            tweetCount++;
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * Gets the GPS coordinates based on external script.
     * @return double[] GPS coordinates
     */
    public final double[] getCoords() {
        // Determine coordinates based on a script on Vincenzo's server
        String ip = "";
        double[] location = new double[2];
        try {
            URL ipURL = new URL("http://www.vincenzopavano.com/ip.php");
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(ipURL.openStream()));
            ip = br.readLine();
            String[] coords = ip.split(",");
            for (int i = 0; i < coords.length; i++) {
                location[i] = Double.parseDouble(coords[i]);
            }
            return location;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }

    /****************************************************
     * get tweet count.
     * @return tweet count
     ***************************************************/
    public final int getTweetCount() {
        return tweetCount;
    }

    /****************************************************
     * get Friends ID.
     * @return friends ID
     ***************************************************/
    public final long[] getFriendsIDs() {
        return friendsIDs;
    }

    /****************************************************
     * get Friends Count.
     * @return friend Count
     ***************************************************/
    public final int getFriendsCount() {
        return friendsCount;
    }

    /****************************************************
     * get followers.
     * @return followers
     ***************************************************/
    public final List<User> getFollowers() {
        return followers;
    }

    /****************************************************
     * get following.
     * @return following
     ***************************************************/
    public final List<User> getFollowing() {
        return following;
    }

    /****************************************************
     * unfollow.
     * @param l long
     * @return boolean
     ***************************************************/
    public final boolean unfollow(final long l) {
        try {
            t.destroyFriendship(l);
            friendsCount--;
            return true;
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return false;
    }

    /****************************************************
     * get Description.
     * @param l long
     * @return discription
     ***************************************************/
    public final User showUser(final long l) {
        try {
            return t.showUser(l);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /****************************************************
     * get Followers count.
     * @return followersCount
     ***************************************************/
    public final int getFollowersCount() {
        return followersCount;
    }

    /****************************************************
     * get Description.
     * @param l long
     * @return boolean
     ***************************************************/
    public final boolean destroyStatus(final Long l) {
        try {
            t.destroyStatus(l);
            tweetCount--;
            return true;
        } catch (TwitterException e) {
            return false;
        }
    }

    /****************************************************
     * get Trending.
     * @param woeid int
     * @return trending
     ***************************************************/
    public final Trends getTrending(final int woeid) {
        try {
            trending = t.getPlaceTrends(woeid);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return trending;
    }

    /****************************************************
     * get Home timeline.
     * @return home timeline
     ***************************************************/
    public final Tweets getHomeTimeline() {
        return homeTimeline;
    }

    /****************************************************
     * get user timeline.
     * @return user timeline
     ***************************************************/
    public final Tweets getUserTimeline() {
        return userTimeline;
    }

    /****************************************************
     * get Description.
     * @return discription
     ***************************************************/
    public final List<DirectMessage> getAllMessages() {
        return messages;
    }

    /****************************************************
     * get Description.
     * @param messageId long
     * @return description
     ***************************************************/
    public final DirectMessage showDirectMessage(final long messageId) {
        try {
            return t.showDirectMessage(messageId);
        } catch (TwitterException e) {
            System.out.println("Error");
        }
        return null;

    }

    /****************************************************
     * get Description.
     * @param userId long
     * @param text String
     * @return description
     ***************************************************/
    public final boolean sendDirectMessage(final long userId,
            final String text) {
        try {
            t.sendDirectMessage(userId, text);
            return true;
        } catch (TwitterException e) {
            return false;
        }
    }

    /****************************************************
     * get CurrentUser ID.
     * @return userId
     ***************************************************/
    public final long getCurrentUserID() {
        try {
            return t.getId();
        } catch (IllegalStateException e) {
            System.out.println("Error");
        } catch (TwitterException e) {
            System.out.println("Error");
        }
        return 0;
    }

    /****************************************************
     * get Description.
     * @param id Long
     * @return discription
     ***************************************************/
    public final User getUser(final long id) {
        try {
            return t.showUser(id);
        } catch (TwitterException e) {
            System.out.println("Error");
        }
        return null;
    }

    /****************************************************
     * follow.
     * @param l long
     * @return discription
     ***************************************************/
    public final User follow(final long l) {
        try {
            User u = t.createFriendship(l);
            friendsCount++;
            return u;
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /****************************************************
     * search Users.
     * @param text String
     * @return discription
     ***************************************************/
    public final ResponseList<User> searchUsers(final String text) {
        try {
            return t.searchUsers(text, 1);
        } catch (TwitterException e) {
            System.out.println("Error");
        }
        return null;
    }

}
