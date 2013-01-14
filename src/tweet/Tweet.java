package tweet;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

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
	public Twitter twitter;

	public Tweet() {
		twitter = start();
	}

	/*
	 * Sends tweet.
	 * 
	 * @return boolean tweet success
	 */
	public boolean tweet(Twitter twitter, String str) {
		try {
			twitter.updateStatus(str);
			return true;
		} catch (TwitterException e) {
			return false;
		}

	}

	/*
	 * Sets up the user for tweets. If user is not authorized use pin to
	 * authorize.
	 */
	private Twitter start() {
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(Info.CONSUMER_KEY, Info.CONSUMER_KEY_SECRET);

		try {
			twitter.setOAuthAccessToken(getOathAccessToken(twitter));
		} catch (TwitterException e) {
			e.printStackTrace();
			return null;
		}

		return twitter;
	}

	private AccessToken getOathAccessToken(Twitter twitter)
			throws TwitterException {
		String token = getAccessToken();
		String tokenSecret = getAccessTokenSecret();
		if (token == null || tokenSecret == null) {
			return setAccessToken(twitter);
		}
		return new AccessToken(token, tokenSecret);
	}

	private AccessToken setAccessToken(Twitter twitter) throws TwitterException {
		RequestToken requestToken = twitter.getOAuthRequestToken();
		System.out.println("Authorization URL: \n"
				+ requestToken.getAuthorizationURL());

		AccessToken accessToken = null;
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		while (null == accessToken) {
			try {
				System.out.print("Input PIN here: ");
				String pin = br.readLine();

				accessToken = twitter.getOAuthAccessToken(requestToken, pin);

			} catch (TwitterException te) {

				System.out.println("Failed to get access token, caused by: "
						+ te.getMessage());

				System.out.println("Retry input PIN");

			} catch (IOException e) {
				System.out.println("Retry input PIN");
			}
		}

		addToFile(accessToken);

		return accessToken;
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
