package model;

import java.util.ArrayList;
import javax.swing.AbstractListModel;
import twitter4j.User;

/****************************************************
 * Users Class.
 ***************************************************/
public class Users extends AbstractListModel<String> {

    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** Arraylist of users. */
    private ArrayList<User> users;

    /****************************************************
     * Users Constructor.
     *
     * @param f
     *            ArrayList<User>
     ***************************************************/
    public Users(final ArrayList<User> f) {
        super();
        users = f;
    }

    @Override
    public final String getElementAt(final int i) {
        User u = users.get(i);
        String str = u.getName() + "  (@" +  u.getScreenName() + ")";
        return str;
    }
    
    public final User getUser(final int i) {
        if (i < users.size())
            return users.get(i);
        else
            return null; 
    }
    

    @Override
    /****************************************************
     * Gets user size.
     ***************************************************/
    public final int getSize() {
        return users.size();
    }

    /****************************************************
     * Adds User.
     *
     * @param u
     *            User
     ***************************************************/
    public final void add(final User u) {
        users.add(u);
    }

    /****************************************************
     * Removes user.
     *
     * @param index
     * @return long
     ***************************************************/
    public final long remove(final int index) {
        long l = users.get(index).getId();
        users.remove(index);
        fireIntervalRemoved(this, 0, users.size());
        return l;
    }
}
