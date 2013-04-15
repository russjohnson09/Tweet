package test;

import controller.TwitterController;

import org.easymock.EasyMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


import twitter4j.Twitter;
import twitter4j.User;

/**
 * Tests the controller for twitter.
 */
public class ControllerTest {

    /** twitter Controller. */
    private TwitterController c;

    /** user. */
    private User user;

    /** twitter. */
    private Twitter t;

    /************************************************************
     * Set up.
     * @throws Exception exception
     ***********************************************************/
    @Before
    public final void setUp() throws Exception {
        t = EasyMock.createMock(Twitter.class);
        user = EasyMock.createMock(User.class);
        c = new TwitterController();
    }

    /************************************************************
     * display name.
     ***********************************************************/
    @Test
    public final void displayName() {
        expect(user.getName()).andReturn("Carl");
        replay(user);
        assertEquals(c.getDisplayName(), "Carl");
    }

     /************************************************************
     * twitter name.
     ***********************************************************/
    @Test
    public final void twitterName() {
        expect(user.getScreenName()).andReturn("carl");
        replay(user);
        assertEquals(c.getTwitterName(), "@carl");
    }

}
