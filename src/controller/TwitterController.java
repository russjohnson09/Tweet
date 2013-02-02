package controller;

import java.awt.*;
import java.util.*;
import javax.swing.*;
import model.TwitterModel;
 

public class TwitterController {
	
	/** instance variables */
	private TwitterModel model;
	
	
	public static void main (String[] args) {
		//model = new TwitterModel();         	**This gives me an error, I'm not sure why**
	}
	
	
	
/**
 * Methods that will be called by the GUI. 
 * They are currently returning bogus values for GUI testing purposes
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
	
	
	public ImageIcon getProfileImage () {
		ImageIcon tmp = null; 
		return tmp;
	}
	
	/** return null if user doesn't have one */
	public Image getHeaderImage () {
		Image tmp = null; 
		return tmp;
	}
	
	public int getNumberTweets() {
		return 10;
	}
	
	public int getNumberFollowers() {
		return 123;
	}
	
	public int getNumberFollowing() {
		return 456;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
