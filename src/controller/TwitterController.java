package controller;

import java.awt.Color;
import java.awt.Image;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import model.Tweets;
import model.TwitterModel;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import twitter4j.DirectMessage;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;


/****************************************************
 * Tweets Class.
 * @author Nick, Vincenzo, Corey, Russ
 * @date 17 February, 2013
 ***************************************************/
public class TwitterController {

    /** TwitterModel. */
    private static TwitterModel model = new TwitterModel();

    /** Boolean if the controller is set up. */
    private boolean isSetUp = false;

    /** Twitter. */
    private Twitter twitter;

    /** RequestToken. */
    private RequestToken requestToken;

    /** Consumer key. */
    public static final String CONSUMER_KEY = "yNBLlBrsFHz89PyCfjrAw";

    /** Consumer key secret. */
    public static final String CONSUMER_KEY_SECRET =
            "8SIq5OXfeIKabtB3B2CBHJVIkrjQbSPloHoTmxtis4";

    /****************************************************
     * Twitter Controller constructor.
     ***************************************************/
    public TwitterController() {
    	model = new TwitterModel();

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
    }

    /****************************************************
     * Gets Display Name.
     * 
     * @return String
     ****************************************************/
    public final String getDisplayName() {
        try {
            return twitter.showUser(twitter.getId()).getName();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return null;
    }

    /****************************************************
     * Gets twitter name.
     *
     * @return String
     ***************************************************/
    public final String getTwitterName() {
        try {
            return "@" + twitter.showUser(twitter.getId()).getScreenName();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /****************************************************
     * Gets twitter name.
     * 
     * @param Long l
     * @return String
     ***************************************************/
    public final String getTwitterName(final long l) {
        try {
            return "@" + twitter.showUser(l).getScreenName();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /****************************************************
     * Gets description.
     * 
     * @return String
     ***************************************************/
    public final String getDescription() {
        try {
            return twitter.showUser(twitter.getId()).getDescription();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /****************************************************
     * Gets website.
     * 
     * @return String
     ***************************************************/
    public final String getWebsite() {
        String str = null;
        try {
            str = twitter.showUser(twitter.getId()).getURL();
            return str;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /****************************************************
     * Gets location.
     * 
     * @return String
     ***************************************************/
    public final String getLocation() {
        try {
            return twitter.showUser(twitter.getId()).getLocation();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /****************************************************
     * gets Profile image.
     * 
     * @return ImageIcon profile picture
     ***************************************************/
    public final ImageIcon getProfileImage() {
        try {
            return new ImageIcon(new URL(twitter.showUser(twitter.getId())
                    .getBiggerProfileImageURL()));
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
     * gets Smaller Profile image.
     * 
     * @return ImageIcon profile picture
     ***************************************************/
    public final ImageIcon getSmallerProfileImage() {
        try {
            return new ImageIcon(new URL(twitter.showUser(twitter.getId())
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
     * Gets profile banner image.
     * 
     * @return image
     ***************************************************/
    public final Image getProfileBanner() {
        Image img = null;
        try {
            img = (new ImageIcon(new URL(twitter.showUser(twitter.getId())
                    .getProfileBannerURL())).getImage());
        } catch (MalformedURLException e) {
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        if (img == null) {
            img = (new ImageIcon("src/banner.jpeg")).getImage();
        }
        return img;
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
     * @param String str
     * @return Boolean
     ***************************************************/
    public final boolean tweet(final String str) {
        try {
            twitter.updateStatus(str);
            return true;
            
        } catch (TwitterException e) {
            return false;
        }
    }
    
    
    /****************************************************
     * Post an image as a Tweet. 
     * 
     * @param File img
     * @return Boolean
     ***************************************************/
    public boolean tweetImage(File img, String message) {
        try{
            StatusUpdate status = new StatusUpdate(message);
            status.setMedia(img);
            twitter.updateStatus(status);
            return true;
            
        } catch(TwitterException e) {
            return false;
        }
    }

    /****************************************************
     * Gets tweet count.
     * 
     * @return int
     ***************************************************/
    public final int getTweetCount() {
        try {
            return twitter.showUser(twitter.getId()).getStatusesCount();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /****************************************************
     * Gets friends IDs.
     * 
     * @return long[]
     ***************************************************/
    public final long[] getFriendsIDs() {
        try {
            return twitter.getFriendsIDs(-1).getIDs();
        } catch (TwitterException e) {
            return null;
        }
    }

    /****************************************************
     * Gets friend count.
     * 
     * @return int number of friends
     ***************************************************/
    public final int getFriendsCount() {
        try {
            return twitter.showUser(twitter.getId()).getFriendsCount();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /****************************************************
     * Gets followers.
     * 
     * @return ArrayList<User>
     ***************************************************/
    public final ArrayList<User> getFollowers() {
        ArrayList<User> users = new ArrayList<User>();
        try {
            long[] list = twitter.getFollowersIDs(-1).getIDs();
            for (long l : list) {
                users.add(twitter.showUser(l));
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return users;
    }

    /****************************************************
     * Gets following.
     * 
     * @return ArrayList<User>
     ***************************************************/
    public final ArrayList<User> getFollowing() {
        ArrayList<User> users = new ArrayList<User>();
        try {
            long[] list = twitter.getFriendsIDs(-1).getIDs();
            for (long l : list) {
                users.add(twitter.showUser(l));
            }
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return users;
    }
    
    /****************************************************
     * 
     ***************************************************/
    public ArrayList<Status> getTimeline() {
        ArrayList<Status> timeline = new ArrayList<Status>();
        ResponseList <Status> t = null;
        try {
            t = twitter.getHomeTimeline();
        } catch (TwitterException e) {}
        
        for (Status s : t) {
            timeline.add(s);
        }
        return timeline;
    }
    
    
    /****************************************************
     * Unfollows user.
     * 
     * @param long l
     * @return boolean
     ***************************************************/
    public final boolean unfollow(final long l) {
        try {
            twitter.destroyFriendship(l);
            return true;
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return false;
    }

    /****************************************************
     * ShowsUser.
     * 
     * @param long l
     * @return User
     ***************************************************/
    public final User showUser(final long l) {
        try {
            return twitter.showUser(l);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return null;
    }

    /****************************************************
     * Gets follower Count.
     * 
     * @return int number of followers
     ***************************************************/
    public final int getFollowersCount() {
        try {
            return twitter.showUser(twitter.getId()).getFollowersCount();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /****************************************************
     * destroys Status.
     * 
     * @param long l
     ***************************************************/
    public final void destroyStatus(final Long l) {
        try {
            twitter.destroyStatus(l);
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }

    /****************************************************
     * Gets home timeline.
     * 
     * @return tweets from timeline
     ***************************************************/
    public final Tweets getHomeTimeline() {
        Tweets tweets = new Tweets();
        ResponseList<Status> statuses = null;
        try {
            statuses = twitter.getHomeTimeline();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        if (statuses != null) {
            for (Status s : statuses) {
                tweets.add(s);
            }
        }
        return tweets;
    }

    /****************************************************
     * gets users timeline.
     * 
     * @return Tweets
     ***************************************************/
    public final Tweets getUserTimeline() {
        Tweets tweets = new Tweets();
        ResponseList<Status> statuses = null;
        try {
            statuses = twitter.getUserTimeline();
        } catch (TwitterException e) {
            e.printStackTrace();
        }
        if (statuses != null) {
            for (Status s : statuses) {
                tweets.add(s);
            }
        }
        return tweets;
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
     * Gets Direct Messages to the user
     * 
     * @return String Users URL
     ***************************************************/
    public ResponseList<DirectMessage> getDirectMessages() {
        //ArrayList<User> users = new ArrayList<User>();
        ResponseList<DirectMessage> list = null;
        
        try {
            list = twitter.getDirectMessages();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return list;
    }

    /****************************************************
     * adds access token to file.
     * 
     * @param accessToken
     ***************************************************/
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
     * @param String pin
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

}
