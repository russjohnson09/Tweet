package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import model.Tweets;

/****************************************************
 * DialogTweets Class.
 ***************************************************/
public class DialogTweets extends JDialog implements ActionListener {

    /** Remove button. */
    private JButton remove;

    /** Jlist of tweets. */
    private JList<String> list;

    /** list of tweets to be removed. */
    private ArrayList<Long> removeList = new ArrayList<Long>();

    /** Tweet. */
    private Tweets tweets;

    /** Width of JDialog. */
    private final int w = 400;
    /** Length of JDialog. */
    private final int l = 300;

    /****************************************************
     * DialogTweets Class.
     * 
     * @param parent
     *            JFrame
     * @param t
     *            Tweets
     ***************************************************/
    public DialogTweets(final JFrame parent, final Tweets t) {
        super(parent, true);

        setLayout(new BorderLayout());

        tweets = t;

        setTitle("Tweets");
        setPreferredSize(new Dimension(w, l));

        list = new JList<String>(tweets);

        JScrollPane scrollpane = new JScrollPane(list);
        add(scrollpane, BorderLayout.CENTER);

        remove = new JButton("Remove");
        remove.addActionListener(this);

        add(remove, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    @Override
    public final void actionPerformed(final ActionEvent e) {
        if (e.getSource() == remove) {
            int i = list.getSelectedIndex();
            if (i >= 0) {
                removeList.add(tweets.remove(i));
            } else {
                JOptionPane.showMessageDialog(null,
                        "Please first select a tweet", "Oops!",
                        JOptionPane.PLAIN_MESSAGE);
            }
        }
    }

    /****************************************************
     * Removes list.
     * 
     * @return ArrayList<Lsong>
     ***************************************************/
    public final ArrayList<Long> getRemoveList() {
        return removeList;
    }
}
