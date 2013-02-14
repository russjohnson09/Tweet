package controller;

import java.awt.*;
import java.awt.List;
import java.util.*;
import javax.swing.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import twitter4j.*;
import twitter4j.auth.*;

import model.Tweets;
import model.TwitterModel;

/****************************************************
 * Tweets Class.
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
	public  static final String CONSUMER_KEY_SECRET
		= "8SIq5OXfeIKabtB3B2CBHJVIkrjQbSPloHoTmxtis4";

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
			twitter.setOAuthAccessToken(
					new AccessToken(token, tokenSecret));

			isSetUp = true;

		} else {
			try {
				requestToken = twitter.getOAuthRequestToken();
			} catch (TwitterException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Gets Display Name.
	 * @return String
	 */
	public final String getDisplayName() {
		try {
			return twitter.showUser(twitter.getId()).getName();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/****************************************************
	 * Gets twitter name.
	 * @return String
	 ***************************************************/
	public final String getTwitterName() {
		try {
			return "@" + twitter.showUser(
					twitter.getId()).getScreenName();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/****************************************************
	 * Gets twitter name.
	 * @param l Long
	 * @return String
	 ***************************************************/
	public final String getTwitterName(final long l) {
		try {
			return "@" + twitter.showUser(l).getScreenName();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/****************************************************
	 * Gets description.
	 * @return String
	 ***************************************************/
	public final String getDescription() {
		try {
			return twitter.showUser(twitter.getId())
					.getDescription();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/****************************************************
	 * Gets website.
	 * @return String
	 ***************************************************/
	public final String getWebsite() {
		String str = null;
		try {
			str = twitter.showUser(twitter.getId()).getURL();
			return str;
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/****************************************************
	 * Gets location.
	 * @return String
	 ***************************************************/
	public final String getLocation() {
		try {
			return twitter.showUser(twitter.getId()).getLocation();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/****************************************************
	 * gets Prfile image.
	 * @return ImageIcon profile picture
	 ***************************************************/
	public final ImageIcon getProfileImage() {
		try {
			return new ImageIcon(new URL(
					twitter.showUser(twitter.getId())
					.getBiggerProfileImageURL()));
		} catch (MalformedURLException e) {
			return null;
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	/****************************************************
	 * Gets profile banner image.
	 * @return image
	 ***************************************************/
	public Image getProfileBanner() {
		Image img = null;
		try {
			img = (new ImageIcon(new URL(twitter.showUser(twitter.getId())
					.getProfileBannerURL())).getImage());
		} catch (MalformedURLException e) {
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (img == null)
			img = (new ImageIcon("src/banner.jpeg")).getImage();
		return img;
	}

	/****************************************************
	 * Gets text color.
	 * @return Color white
	 ***************************************************/
	public final Color getTextColor() {
		return Color.WHITE;
	}

	/****************************************************
	 * Gets background image.
	 * @return Banner
	 ***************************************************/
	public final Image getBackgroundImage() {
		Image img = new ImageIcon("src/background.jpeg").getImage();
		return img;
	}

	/****************************************************
	 * Gets profile banner image.
	 * @param str tweet
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
	 * Gets tweet count.
	 * @return Int
	 ***************************************************/
	public final int getTweetCount() {
		try {
			return twitter.showUser(twitter.getId())
					.getStatusesCount();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/****************************************************
	 * Gets friends IDs.
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
	 * @return int number of friends
	 ***************************************************/
	public final int getFriendsCount() {
		try {
			return twitter.showUser(twitter.getId())
					.getFriendsCount();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return 0;
	}

	/****************************************************
	 * Gets follower.
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}

	/****************************************************
	 * Gets following.
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return users;
	}

	/****************************************************
	 * unfollows user.
	 * @param l Long
	 * @return boolean
	 ***************************************************/
	public final boolean unfollow(final long l) {
		try {
			twitter.destroyFriendship(l);
			return true;
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/****************************************************
	 * ShowsUser.
	 * @param l Long
	 * @return User
	 ***************************************************/
	public final User showUser(final long l) {
		try {
			return twitter.showUser(l);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/****************************************************
	 * Gets follower Count.
	 * @return Int number of followers
	 ***************************************************/
	public final int getFollowersCount() {
		try {
			return twitter.showUser(twitter.getId())
					.getFollowersCount();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	/****************************************************
	 * destroys Status.
	 * @param l Long
	 ***************************************************/
	public final void destroyStatus(final Long l) {
		try {
			twitter.destroyStatus(l);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/****************************************************
	 * Gets home timeline.
	 * @return tweets from timeline
	 ***************************************************/
	public final Tweets getHomeTimeline() {
		Tweets tweets = new Tweets();
		ResponseList<Status> statuses = null;
		try {
			statuses = twitter.getHomeTimeline();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
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
	 * @return Tweets
	 ***************************************************/
	public final Tweets getUserTimeline() {
		Tweets tweets = new Tweets();
		ResponseList<Status> statuses = null;
		try {
			statuses = twitter.getUserTimeline();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
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
	 * @return String Users URL
	 ***************************************************/
	public final String getAuthUrl() {
		return requestToken.getAuthorizationURL();

	}

	/****************************************************
	 * adds access token to file.
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
	 * @return String access token
	 ***************************************************/
	private String getAccessToken() {
		JSONParser parser = new JSONParser();
		try {

			Object obj = parser.parse(
					new FileReader("./auth.json"));

			JSONObject jsonObject = (JSONObject) obj;

			return (String) jsonObject.get("AccessToken");

		} catch (Throwable e) {
			return null;
		}
	}

	/****************************************************
	 * Gets access token.
	 * @return String access token
	 ***************************************************/
	private String getAccessTokenSecret() {
		JSONParser parser = new JSONParser();
		try {

			Object obj = parser.parse(
					new FileReader("./auth.json"));

			JSONObject jsonObject = (JSONObject) obj;

			return (String) jsonObject.get("AccessTokenSecret");

		} catch (Throwable e) {
			return null;
		}
	}

	/****************************************************
	 * Boolean if controller is setup.
	 * @return Boolean is set up
	 ***************************************************/
	public final boolean getIsSetUp() {
		return isSetUp;
	}

	/****************************************************
	 * sets up controller.
	 * @param pin String
	 ***************************************************/
	public final void setUp(final String pin) {
		AccessToken accessToken = null;
		try {
			accessToken = twitter
					.getOAuthAccessToken(requestToken, pin);

		} catch (TwitterException te) {
			te.printStackTrace();
		}
		addToFile(accessToken);

	}

}