package controller;

import java.awt.*;
import java.util.*;
import model.TwitterModel;
 

public class TwitterController {
	
	/** instance variables */
	private static TwitterModel model = new TwitterModel();
	
	/**
	 * Methods that will be called by the GUI
	 */
	public String getDisplayName() {
		return "Display name";
	}
	
	
	public String getTwitterName() {
		return "Twitter name";
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
	
	
	public Image getProfileImage() {
		Image tmp = null; 
		return tmp;
	}
	
	/** return null if user doesn't have one */
	public Image getHeaderImage() {
		Image tmp = null; 
		return tmp;
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
