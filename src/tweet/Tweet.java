package tweet;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

/*
 * This class contains the methods for interacting with twitter.
 */
public class Tweet {

	// twitter account
	private Twitter twitter;
	private RequestToken requestToken;

	public Twitter getTwitter() {
		return twitter;
	}

	public void setTwitter(Twitter twitter) throws TwitterException {
		this.twitter = twitter;
	}

	public Tweet() throws TwitterException {
		twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(Info.CONSUMER_KEY, Info.CONSUMER_KEY_SECRET);

		String token = getAccessToken();
		String tokenSecret = getAccessTokenSecret();
		if (token != null && tokenSecret != null) {
			twitter.setOAuthAccessToken(new AccessToken(token, tokenSecret));
			requestToken = null;
		} else {
			requestToken = twitter.getOAuthRequestToken();
		}
	}

	/*
	 * Sends tweet.
	 * 
	 * @return boolean tweet success
	 */
	public boolean tweet(String str) {
		try {
			twitter.updateStatus(str);
			return true;
		} catch (TwitterException e) {
			return false;
		}

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
}
