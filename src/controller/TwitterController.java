package controller;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import model.TwitterModel;
 

public class TwitterController {
	
	/** instance variables */
<<<<<<< HEAD
	private TwitterModel model;
	
	
	public static void main (String[] args) {
		//model = new TwitterModel();         	**This gives me an error, I'm not sure why**
	}
	
	
	
/**
 * Methods that will be called by the GUI. 
 * They are currently returning bogus values for GUI testing purposes
 */
=======
	private static TwitterModel model = new TwitterModel();
>>>>>>> 6152c67027fb253c57b02de7f1c0e4d9428dfd0c
	
	/**
	 * Methods that will be called by the GUI
	 */
	public String getDisplayName() {
		return "Display name";
	}
	
	
	public String getTwitterName() {
		return "@TwitterName";
	}
	
	
	public String getDescription() {
		return "Description...........................................................";
	}
	
	
	public String getWebsite() {
		return "www.website.com";
	}
	
	
	public String getLocation() {
		return "Allendale, MI";
	}
	
	
<<<<<<< HEAD
	public ImageIcon getProfileImage () {
		ImageIcon tmp = null; 
=======
	public Image getProfileImage() {
		Image tmp = null; 
>>>>>>> 6152c67027fb253c57b02de7f1c0e4d9428dfd0c
		return tmp;
	}
	
	/** return null if user doesn't have one */
	public Image getHeaderImage() {
		Image tmp = null; 
		return tmp;
	}
	
<<<<<<< HEAD
	public int getNumberTweets() {
		return 10;
	}
	
	public int getNumberFollowers() {
		return 123;
	}
	
	public int getNumberFollowing() {
		return 456;
	}
	
=======
	public int getTweetTotal() {
		return 86;
	}
>>>>>>> 6152c67027fb253c57b02de7f1c0e4d9428dfd0c
	
	public int getFriendsCount() {
		return 34;
	}
	
	public int getFollowersCount() {
		return 32;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
