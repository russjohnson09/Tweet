package controller;

import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import model.TwitterModel;

public class TwitterController {

	/** instance variables */
	private TwitterModel model;

	private boolean isSetUp = false;

	private Twitter twitter;
	private RequestToken requestToken;

	private User user;

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

			try {
				user = twitter.showUser(twitter.getId());
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			isSetUp = true;

		} else {
			try {
				requestToken = twitter.getOAuthRequestToken();
			} catch (TwitterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * Methods that will be called by the GUI
	 */

	public String getDisplayName() {
		return user.getName();
	}

	public String getTwitterName() {
		return user.getScreenName();
	}

	public String getDescription() {
		return user.getDescription();
	}

	public String getWebsite() {
		String str = user.getURL();
		return (str == null) ? "" : str;
	}

	public String getLocation() {
		return user.getLocation();
	}

	public ImageIcon getProfileImage() {

		try {
			return new ImageIcon(new URL(user.getBiggerProfileImageURL()));
		} catch (MalformedURLException e) {
			return null;
		}
	}

	/** return null if user doesn't have one */
	// TODO I don't know what this is
	public ImageIcon getHeaderImage() {
		return null;
	}

	public boolean tweet(String str) {
		try {
			twitter.updateStatus(str);
			return true;
		} catch (TwitterException e) {
			return false;
		}

	}

	public int getTweetTotal() {
		return user.getStatusesCount();
	}

	public int getFriendsCount() {
		return user.getFriendsCount();
	}

	public int getFollowersCount() {
		return user.getFollowersCount();
	}

	public String getAuthUrl() {
		return requestToken.getAuthorizationURL();

	}

	public void setUpUser(String pin) {
		AccessToken accessToken = null;
		try {
			accessToken = twitter.getOAuthAccessToken(requestToken, pin);

			user = twitter.showUser(twitter.getId());

		} catch (TwitterException te) {
			te.printStackTrace();
		}
		addToFile(accessToken);
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

}