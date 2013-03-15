package test;

import static org.junit.Assert.*;
import TwitterController;
import TwitterGUI;

import org.easymock.EasyMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import org.junit.Before;
import org.junit.Test;


import twitter4j.Twitter;
import twitter4j.User;

/**
 * Tests the controller for twitter.
 * 
 * @author Corey
 * 
 */
public class ControllerTest {

	private TwitterController c;

	private User user;

	private Twitter t;

	@Before
	public void setUp() throws Exception {
		t = EasyMock.createMock(Twitter.class);
		user = EasyMock.createMock(User.class);
		c = new TwitterController();
	}

	@Test
	public void displayName() {
		expect(user.getName()).andReturn("Carl");
		replay(user);
		assertEquals(c.getDisplayName(), "Carl");
	}

	@Test
	public void twitterName() {
		expect(user.getScreenName()).andReturn("carl");
		replay(user);
		assertEquals(c.getTwitterName(), "@carl");
	}

}
