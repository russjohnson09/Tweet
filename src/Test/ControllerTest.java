package Test;

import static org.junit.Assert.*;

import org.easymock.EasyMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import org.junit.Before;
import org.junit.Test;

import controller.TwitterController;

import twitter4j.Twitter;
import twitter4j.User;
import view.TwitterGUI;

public class ControllerTest {
	
	private TwitterController c;
	private TwitterGUI gui;
	
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
	    // Setting up the expected value of the method call calc
		c.setUser(user);
		assertEquals(c.getDisplayName(),"Carl");
	}

}
