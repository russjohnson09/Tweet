package controller;

import java.awt.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	/**
	 * Methods that will be called by the GUI
	 */

	public String getDisplayName() {
		// TODO not sure about difference between name and twitter name. Can't
		// find method to call for real name.
		return "Name";
	}

	public String getTwitterName() {
		try {
			return twitter.getScreenName();
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
		return "Description...........................................................";
	}

	public String getWebsite() {
		return "www.website.com";
	}

	public String getLocation() {
		return null;
	}

	public ImageIcon getProfileImage() {
		try {
			User user = twitter.showUser(twitter.getId());
			String url = user.getProfileImageURL();
			ImageIcon img = new ImageIcon(url);

			return img;
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	/** return null if user doesn't have one */
	public Image getHeaderImage() {
		Image tmp = null;
		return tmp;
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
		return 86;
	}

	public int getFriendsCount() {
		return 34;
	}

	public int getFollowersCount() {
		return 32;
	}

	public String getAuthUrl() {
		return requestToken.getAuthorizationURL();

	}

	public void setUpUser(String pin) {
		AccessToken accessToken = null;
		try {
			accessToken = twitter.getOAuthAccessToken(requestToken, pin);

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