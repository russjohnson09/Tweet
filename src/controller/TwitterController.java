package controller;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.ListModel;

import model.Tweets;
import model.TwitterModel;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import twitter4j.DirectMessage;
import twitter4j.ResponseList;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/****************************************************
 * Tweets Class.
 * 
 * @author Nick, Vincenzo, Corey, Russ
 * @date March 18, 2013
 ***************************************************/
public class TwitterController {

    private Tweets timeline;
    private boolean isHomeTimeline;

    /** TwitterModel. */
    private static TwitterModel model;

    /** Boolean if the controller is set up. */
    private boolean isSetUp = false;

    /** Twitter. */
    private Twitter twitter;

    /** RequestToken. */
    private RequestToken requestToken;

    /** Consumer key. */
    public static final String CONSUMER_KEY = "yNBLlBrsFHz89PyCfjrAw";

    /** Consumer key secret. */
    public static final String CONSUMER_KEY_SECRET = "8SIq5OXfeIKabtB3B2CBHJVIkrjQbSPloHoTmxtis4";
    
    /** Trending WOEIDs */
    private int[] woeidArray = {1,2,3,4,5,6,7,8};

    /****************************************************
     * Twitter Controller constructor.
     ***************************************************/
    public TwitterController() {
        twitter = new TwitterFactory().getInstance();
        twitter.setOAuthConsumer(CONSUMER_KEY, CONSUMER_KEY_SECRET);

        String token = getAccessToken();
        String tokenSecret = getAccessTokenSecret();

        if (token != null && tokenSecret != null) {
            twitter.setOAuthAccessToken(new AccessToken(token, tokenSecret));
            isSetUp = true;

        } else {
            try {
                requestToken = twitter.getOAuthRequestToken();
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        }

        model = new TwitterModel(twitter);
    }

    /****************************************************
     * Gets Display Name.
     * 
     * @return String
     ****************************************************/
    public final String getDisplayName() {
        return model.getName();
    }

    /****************************************************
     * Gets twitter name.
     * 
     * @return String
     ***************************************************/
    public final String getTwitterName() {
        return model.getScreenName();
    }

    /****************************************************
     * Gets twitter name.
     * 
     * @param Long
     *            l
     * @return String
     ***************************************************/
    public final String getTwitterName(final long l) {
        return model.getScreenName(l);
    }

    /****************************************************
     * Gets description.
     * 
     * @return String
     ***************************************************/
    public final String getDescription() {
        return model.getDescription();
    }

    /****************************************************
     * Gets website.
     * 
     * @return String
     ***************************************************/
    public final String getWebsite() {
        return model.getURL();
    }

    /****************************************************
     * Gets location.
     * 
     * @return String
     ***************************************************/
    public final String getLocation() {
        return model.getLocation();
    }

    /****************************************************
     * gets Profile image.
     * 
     * @return ImageIcon profile picture
     ***************************************************/
    public final ImageIcon getProfileImage() {
        return model.getProfileImage();
    }

    /****************************************************
     * gets Smaller Profile image.
     * 
     * @return ImageIcon profile picture
     ***************************************************/
    public final ImageIcon getSmallerProfileImage() {
        return model.getSmallerProfileImage();
    }

    /****************************************************
     * gets Smaller Profile image.
     * 
     * @param long userId
     * @return ImageIcon profile picture
     ***************************************************/
    public final ImageIcon getSmallerProfileImage(long userId) {
        return model.getSmallerProfileImage(userId);
    }

    /****************************************************
     * Gets profile banner image.
     * 
     * @return image
     ***************************************************/
    public final Image getProfileBanner() {
        return model.getProfileBanner();
    }

    /****************************************************
     * Gets text color.
     * 
     * @return Color white
     ***************************************************/
    public final Color getTextColor() {
        return Color.WHITE;
    }

    /****************************************************
     * Gets background image.
     * 
     * @return Banner
     ***************************************************/
    public final Image getBackgroundImage() {
        Image img = new ImageIcon("src/background.jpeg").getImage();
        return img;
    }

    /****************************************************
     * Post a new Tweet.
     * 
     * @param String
     *            str
     * @return Boolean
     ***************************************************/
    public final boolean tweet(final String str) {
        return model.updateStatus(str);
    }

    /****************************************************
     * Post an image as a Tweet.
     * 
     * @param File
     *            img
     * @return Boolean
     ***************************************************/
    public boolean tweetImage(File img, String message) {
        return model.tweetImage(img, message);
    }

    /****************************************************
     * Gets tweet count.
     * 
     * @return int
     ***************************************************/
    public final int getTweetCount() {
        return model.getTweetCount();
    }

    /****************************************************
     * Gets friends IDs.
     * 
     * @return long[]
     ***************************************************/
    public final long[] getFriendsIDs() {
        return model.getFriendsIDs();
    }

    /****************************************************
     * Gets friend count.
     * 
     * @return int number of friends
     ***************************************************/
    public final int getFriendsCount() {
        return model.getFriendsCount();
    }

    /****************************************************
     * Gets followers.
     * 
     * @return ArrayList<User>
     ***************************************************/
    public final List<User> getFollowers() {
        return model.getFollowers();
    }

    /****************************************************
     * Gets following.
     * 
     * @return ArrayList<User>
     ***************************************************/
    public final List<User> getFollowing() {
        return model.getFollowing();
    }

    /****************************************************
     * Unfollows user.
     * 
     * @param long l
     * @return boolean
     ***************************************************/
    public final boolean unfollow(final long l) {
        return model.unfollow(l);
    }

    /****************************************************
     * ShowsUser.
     * 
     * @param long l
     * @return User
     ***************************************************/
    public final User showUser(final long l) {
        return model.showUser(l);
    }

    /****************************************************
     * Gets follower Count.
     * 
     * @return int number of followers
     ***************************************************/
    public final int getFollowersCount() {
        return model.getFollowersCount();
    }

    /****************************************************
     * destroys Status.
     * 
     * @param long l
     ***************************************************/
    public final boolean destroyStatus(final Long l) {
        return model.destroyStatus(l);
    }

    /****************************************************
     * Gets trending
     * @param woeid 
     * 
     * @return Trends
     ***************************************************/
    public final Trends getTrending(int position) {
        return model.getTrending(woeidArray[position]);
    }

    /****************************************************
     * Gets home timeline.
     * 
     * @return tweets from timeline
     ***************************************************/
    public final Tweets getHomeTimeline() {
        return model.getHomeTimeline();
    }

    /****************************************************
     * gets users timeline.
     * 
     * @return Tweets
     ***************************************************/
    public final Tweets getUserTimeline() {
        return model.getUserTimeline();
    }

    /****************************************************
     * gets authorsURL.
     * 
     * @return String Users URL
     ***************************************************/
    public final String getAuthUrl() {
        return requestToken.getAuthorizationURL();

    }

    /****************************************************
     * Gets all of the user's messages
     * 
     * @return String Users URL
     ***************************************************/
    public List<DirectMessage> getAllMessages() {
        return model.getAllMessages();
    }

    /****************************************************
     * Gets Direct Messages to the user
     * 
     * @param long messageId
     * @return String Users URL
     ***************************************************/
    public DirectMessage showDirectMessage(long messageId) {
        return model.showDirectMessage(messageId);
    }

    /****************************************************
     * Gets Direct Messages to the user
     * 
     * @param long userId
     * @param String
     *            text
     * @return String Users URL
     ***************************************************/
    public boolean sendDirectMessage(long userId, String text) {
        return model.sendDirectMessage(userId, text);
    }

    /***************************************************
     * Get the current user's ID.
     * 
     * @return
     **************************************************/
    public long getCurrentUserID() {
        return model.getCurrentUserID();
    }

    /***************************************************
     * Get the a specified user.
     * 
     * @return
     **************************************************/
    public User getUser(long id) {
        return model.getUser(id);
    }

    /****************************************************
     * adds access token to file.
     * 
     * @param accessToken
     ***************************************************/
    @SuppressWarnings("unchecked")
    private void addToFile(final AccessToken accessToken) {
        JSONObject obj = new JSONObject();

        obj.put("AccessToken", accessToken.getToken());
        obj.put("AccessTokenSecret", accessToken.getTokenSecret());

        try {
            FileWriter file = new FileWriter("./auth.json");
            file.write(obj.toJSONString());
            file.flush();
            file.close();

            isSetUp = true;

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /****************************************************
     * Gets access token.
     * 
     * @return String access token
     ***************************************************/
    private String getAccessToken() {
        JSONParser parser = new JSONParser();
        try {

            Object obj = parser.parse(new FileReader("./auth.json"));

            JSONObject jsonObject = (JSONObject) obj;

            return (String) jsonObject.get("AccessToken");

        } catch (Throwable e) {
            return null;
        }
    }

    /****************************************************
     * Gets access token.
     * 
     * @return String access token
     ***************************************************/
    private String getAccessTokenSecret() {
        JSONParser parser = new JSONParser();
        try {

            Object obj = parser.parse(new FileReader("./auth.json"));

            JSONObject jsonObject = (JSONObject) obj;

            return (String) jsonObject.get("AccessTokenSecret");

        } catch (Throwable e) {
            return null;
        }
    }

    /****************************************************
     * Boolean if controller is setup.
     * 
     * @return Boolean is set up
     ***************************************************/
    public final boolean getIsSetUp() {
        return isSetUp;
    }

    /****************************************************
     * sets up controller.
     * 
     * @param String
     *            pin
     ***************************************************/
    public final void setUp(final String pin) {
        AccessToken accessToken = null;
        try {
            accessToken = twitter.getOAuthAccessToken(requestToken, pin);

        } catch (TwitterException te) {
            te.printStackTrace();
        }
        addToFile(accessToken);

    }

    public ResponseList<User> searchUsers(String text) throws TwitterException {
        return model.searchUsers(text);
    }

    public User follow(long l) {
        return model.follow(l);

    }

    public ListModel<String> initTimeline() {
        timeline = new Tweets(twitter);
        timeline.homeTimeline();
        isHomeTimeline = true;
        return timeline;
    }

    public void homeTimeline() {
        if (!isHomeTimeline) {
            timeline.homeTimeline();
            isHomeTimeline = true;
        }

    }

    public void userTimeline() {
        if (isHomeTimeline) {
            timeline.userTimeline();
            isHomeTimeline = false;
        }

    }

}
