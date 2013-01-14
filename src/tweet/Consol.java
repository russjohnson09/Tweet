package tweet;

import java.util.List;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

public class Consol {

	public static void main(String[] args) throws IllegalStateException,
			TwitterException {

		Tweet tweet = new Tweet();

		try {
			// gets Twitter instance with default credentials
			Twitter twitter = tweet.twitter;
			User user = twitter.verifyCredentials();
			List<Status> statuses = twitter.getHomeTimeline();
			System.out.println("Showing @" + user.getScreenName()
					+ "'s home timeline.");
			for (Status status : statuses) {
				System.out.println("@" + status.getUser().getScreenName()
						+ " - " + status.getText());
			}
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to get timeline: " + te.getMessage());
			System.exit(-1);
		}

	}

}
