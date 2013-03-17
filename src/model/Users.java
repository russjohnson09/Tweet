package model;

import java.util.ArrayList;
import javax.swing.AbstractListModel;
import javax.swing.ListModel;

import controller.TwitterController;

import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;

/****************************************************
 * Users Class.
 ***************************************************/
public class Users extends AbstractListModel<String> {

    /** serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** Arraylist of users. */
    private ArrayList<User> users;
    
    /** ArrayList of users currently being viewed */
    private ArrayList<User> visible;

    /****************************************************
     * Users Constructor.
     *
     * @param f
     *            ArrayList<User>
     ***************************************************/
    public Users(final ArrayList<User> f) {
        super();
        users = f;
        visible = new ArrayList<User>(f);
    }

    public Users() {
        users = new ArrayList<User>();
        visible = new ArrayList<User>();
    }

    @Override
    public final String getElementAt(final int i) {
        User u = visible.get(i);
        String str = u.getName() + "  (@" +  u.getScreenName() + ")";
        return str;
    }
    
    public final User getUser(final int i) {
        if (i < visible.size())
            return visible.get(i);
        else
            return null; 
    }
    

    @Override
    /****************************************************
     * Gets user size.
     ***************************************************/
    public final int getSize() {
        return visible.size();
    }

    /****************************************************
     * Adds User.
     *
     * @param u
     *            User
     ***************************************************/
    public final void add(final User u) {
        users.add(u);
        visible.add(u);
        fireIntervalAdded(this, 0, visible.size());
    }

    /****************************************************
     * Removes user.
     *
     * @param index
     * @return long
     ***************************************************/
    public final long remove(final int index) {
        long l = visible.get(index).getId();       
        for (int i = 0; i< users.size(); i++) {
            if (l == users.get(i).getId()){
                users.remove(i);
                break;
            }
        }
        visible.remove(index);
        fireIntervalRemoved(this, 0, visible.size());
        return l;
    }
    
    public final void showAll() {
        visible = new ArrayList<User>(users);
        fireIntervalAdded(this, 0, visible.size());
    }

    /****************************************************
     * Returns a subset of users based on search.
     ***************************************************/
    public void search(String query) {
        query = query.toLowerCase();
        String str1;
        String str2;
        for (int i = visible.size() - 1; i >= 0; i--) {
            str1 = visible.get(i).getScreenName().toLowerCase();
            str2 = visible.get(i).getName().toLowerCase();
            if (!( str1.startsWith(query) || str2.startsWith(query))) {
                visible.remove(i);
            }
        }
        fireIntervalRemoved(this, 0, visible.size());
    }

    public void searchTwitter(String text, TwitterController controller){
        users.clear();
        visible.clear();
        try {
            for (User u : controller.searchUsers(text)) {
                add(u);
            }
        } catch (TwitterException e) {
        }
        
    }

    public long showId(int index) {
        return visible.get(index).getId();
    }
}
