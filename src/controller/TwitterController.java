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

public class TwitterController {

	private static TwitterModel model = new TwitterModel();

	private boolean isSetUp = false;

	private Twitter twitter;
	private RequestToken requestToken;

	public final static String CONSUMER_KEY = "yNBLlBrsFHz89PyCfjrAw";
	public final static String CONSUMER_KEY_SECRET = "8SIq5OXfeIKabtB3B2CBHJVIkrjQbSPloHoTmxtis4";

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

	/**
	 * Methods that will be called by the GUI
	 */

	public String getDisplayName() {
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

	public String getTwitterName() {
		try {
			return "@" + twitter.showUser(twitter.getId()).getScreenName();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String getTwitterName(long l) {
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

	public String getDescription() {
		try {
			return twitter.showUser(twitter.getId()).getDescription();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String getWebsite() {
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

	public String getLocation() {
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

	public ImageIcon getProfileImage() {
		try {
			return new ImageIcon(new URL(twitter.showUser(twitter.getId())
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

	public Color getTextColor() {
		return Color.WHITE;
	}

	public Image getBackgroundImage() {
		Image img = new ImageIcon("src/background.jpeg").getImage();
		return img;
	}

	public boolean tweet(String str) {
		try {
			twitter.updateStatus(str);
			return true;
		} catch (TwitterException e) {
			return false;
		}
	}

	public int getTweetCount() {
		try {
			return twitter.showUser(twitter.getId()).getStatusesCount();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public long[] getFriendsIDs() {
		try {
			return twitter.getFriendsIDs(-1).getIDs();
		} catch (TwitterException e) {
			return null;
		}
	}

	public int getFriendsCount() {
		try {
			return twitter.showUser(twitter.getId()).getFriendsCount();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public ArrayList<User> getFollowers() {
		ArrayList<User> users = new ArrayList<User>();
		try {
			long[] list = twitter.getFollowersIDs(-1).getIDs();
			for (long l : list) {
				users.add(twitter.showUser(l));
			}
		} catch (TwitterException e) {
		}
		return users;
	}


	public ArrayList<User> getFollowing() {
		ArrayList<User> users = new ArrayList<User>();
		try {
			long[] list = twitter.getFriendsIDs(-1).getIDs();
			for (long l : list) {
				users.add(twitter.showUser(l));
			}
		} catch (TwitterException e) {
		}
		return users;
	}

	public boolean unfollow(long l) {
		try {
			twitter.destroyFriendship(l);
			return true;
		} catch (TwitterException e) {
		}
		return false;
	}

	public User showUser(long l) {
		try {
			return twitter.showUser(l);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public int getFollowersCount() {
		try {
			return twitter.showUser(twitter.getId()).getFollowersCount();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}

	public void destroyStatus(Long l) {
		try {
			twitter.destroyStatus(l);
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return
	 */
	public Tweets getHomeTimeline() {
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

	/**
	 * 
	 * @return
	 */
	public Tweets getUserTimeline() {
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

	public String getAuthUrl() {
		return requestToken.getAuthorizationURL();

	}

	private void addToFile(AccessToken accessToken) {
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

	public boolean getIsSetUp() {
		return isSetUp;
	}

	public void setUp(String pin) {
		AccessToken accessToken = null;
		try {
			accessToken = twitter.getOAuthAccessToken(requestToken, pin);

		} catch (TwitterException te) {
			te.printStackTrace();
		}
		addToFile(accessToken);

	}

}