package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.Event;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.ScrollPane;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.border.LineBorder;

import twitter4j.TwitterException;

import model.Tweets;
import model.Users;
import controller.TwitterController;

/**********************************************************************
 * Twitter GUI.
 * 
 * @author Nick, Vincenzo, Corey, Russ
 * @date 17 February, 2013
 *********************************************************************/
public class TwitterGUI extends JFrame implements ActionListener, KeyListener {

    /** serialVersionUID. */
    static final long serialVersionUID = 1L;

    /** Final frame height. */
    static final int FRAME_HEIGHT = 450;

    /** Final frame width. */
    static final int FRAME_WIDTH = 700;

    /** Final character limit: at most 140 characters per tweet. */
    static final int CHAR_LIMIT = 140;

    /** Twitter Controller. */
    private TwitterController controller;

    /** Main frame. */
    static JFrame frame;

    /** Each panel of the GUI. */
    static JPanel profilePanel, timelinePanel, tweetPanel, followingPanel,
            followersPanel, messagesPanel;

    /** Menu Bar. */
    private JMenuBar menuBar;

    /** Categories of the menu bar. */
    private JMenu fileMenu, tweetMenu, aboutMenu;

    /** Items in the menu bar. */
    private JMenuItem exit, newTweet, delete, about;

    /** Tabbed pane. */
    static JTabbedPane tabbedPane;
    
    /** Loading panel. */
    private LoadingPanel loadingPanel;
    
    /** Background image. */
    private Image backgroundImage;
    

    // Profile Panel *******************************************************
    /** Buttons for profile: Followers, Following, and number of tweets. */
   // private JButton followersBtn, followingBtn, tweetsBtn;

    

    // Timeline Panel ******************************************************
    /** Final frame height. */
    private static final int TIMELINE_HEIGHT = 300;

    /** Final frame width. */
    private static final int TIMELINE_WIDTH = 450;

    /** Final frame height. */
    private static final int TRENDING_HEIGHT = 300;

    /** Final frame width. */
    private static final int TRENDING_WIDTH = 150;

    // Tweet Panel *********************************************************
    /** GBC Layout. */
    private GridBagConstraints gbc;

    /** Chars remaining. */
    private int remaining = CHAR_LIMIT;

    /** JButtons for tweet panel. */
    private JButton cancel, tweetSubmit, tweetShow, attachImg;

    /** Labels for characters remaining and total tweets. */
    static JLabel charsRemaining, tweetTotal;

    /** Text area to type outgoing tweet. */
    private JTextArea tweetText;

    /** Image File to be uploaded with tweet */
    private File attachedFile;

    // Followers Panel *****************************************************
    /** Final frame height. */
    private static final int FOLLOWERS_HEIGHT = 300;

    /** Final frame width. */
    private static final int FOLLOWERS_WIDTH = 450;

    /** Following Search Button. */
    private JButton fersSearchButton;

    /** Following Search text area. */
    private JTextArea fersSearchTextArea;

    /** serialVersionUID. */
    private GridBagConstraints fersgbc;

    /** serialVersionUID. */
    private Users followers;

    /** serialVersionUID. */
    private JLabel followersTotal;

    /** serialVersionUID. */
    private JList<String> jlistFollowers;

    private JList<Icon> followersIconsJL;

    /** Display all followers. */
    private JButton fersAllButton;

    // Following Panel *****************************************************
    /** Final frame height. */
    private static final int FOLLOWING_HEIGHT = 300;

    /** Final frame width. */
    private static final int FOLLOWING_WIDTH = 450;

    /** Display all following. */
    private JButton fingAllButton;

    /** Following Search Button. */
    private JButton fingSearchButton;

    private JScrollPane fingScrollPane;

    /** Following Search text area. */
    private JTextArea fingSearchTextArea;

    /** serialVersionUID. */
    private JList<String> jlistFollowing;

    /** serialVersionUID. */
    private JButton unfollow;

    /** serialVersionUID. */
    private Users following;

    /** serialVersionUID. */
    private GridBagConstraints finggbc;

    /** serialVersionUID. */
    private JLabel followingTotal;

    // Messages Panel *********************************************************
    /** Messages Panel */
    // private JPanel messages;

    /****************************************************
     * Graphical User Interface.
     ***************************************************/
    public TwitterGUI() {

        frame = new JFrame();
        setTitle("Desktop Tweets");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        // setMaximumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        // setMinimumSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        setResizable(false);
        setLocationRelativeTo(null);
        loadingPanel = new LoadingPanel();
        add(loadingPanel);
        setVisible(true);
        loadingPanel.incrementLoadingScreen();
        setUpController();


        loadingPanel.incrementLoadingScreen();
        backgroundImage = controller.getBackgroundImage();

        // Create components
        profilePanel = new ProfilePanel(controller);
        loadingPanel.incrementLoadingScreen();
        createTweetPanel();
        createTimelinePanel();
        loadingPanel.incrementLoadingScreen();
        createFollowingPanel();
        createFollowersPanel();
        loadingPanel.incrementLoadingScreen();
        createMessagesPanel();
        createMenu();
        loadingPanel.incrementLoadingScreen();
        createTabbedPane();
        loadingPanel.incrementLoadingScreen();
        remove(loadingPanel);
    }

    /****************************************************
     * Sets up controller.
     ***************************************************/
    private void setUpController() {
		controller = new TwitterController();

		if (!controller.getIsSetUp()) {
		    final String authUrl = controller.getAuthUrl();
		    final TextField pinTF = new TextField(10);
		    final JFrame authFrame = new JFrame();
		    JButton confirmPinButton = new JButton("OK");
		    confirmPinButton.setFocusable(false);
		    JButton browserButton = new JButton("Open Browser");
		    browserButton.setFocusable(false);
		    
		    /** Confirm Pin Button Action Listener */
		    confirmPinButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    if (pinTF.getText().length() > 0) {
                        controller.setUp(pinTF.getText());
                        if (controller.getIsSetUp()) {
                            authFrame.setVisible(false);
                            authFrame.dispose();
                        }
                    }
                }
            });
		    
		    /** Browser Button Action Listener */
		    browserButton.addActionListener(new ActionListener() {
		         public void actionPerformed(ActionEvent arg0) {
		             if(Desktop.isDesktopSupported()) {
		                 authFrame.toFront();
		                 try {
		                     Desktop.getDesktop().browse(new URI(authUrl));
		                 } catch (IOException e) {
		                     e.printStackTrace();
		                 } catch (URISyntaxException e) {
		                     e.printStackTrace();
		                 }
		             }
		         }
		    });
		    JPanel pnl = new JPanel();
		    
		    authFrame.setSize(500, 200);
            authFrame.setLocationRelativeTo(null);
		    pnl.add(new JLabel("<html><center>Before you use this application, you "
		            + "need to authorize your Twitter Account.<br>Click "
		            + "below to open your browser and sign into your account."
		            + "<br><br>Then copy the pin number that you receive and enter "
		            + "it in the text box.<br>Then click \"OK\" and enjoy using "
		            + " Desktop Tweets!<br><br><br></center></html>"));
		    pnl.add(browserButton);
		    pnl.add(new JLabel("     Enter pin:"));
		    pnl.add(pinTF);
		    pnl.add(confirmPinButton);
		    authFrame.add(pnl);
		    authFrame.setTitle("Authorization");
		    authFrame.setVisible(true);
		    while (!controller.getIsSetUp()) {
		        authFrame.toFront();
		    }
		}
	}

    /****************************************************
     * Creates followers panel and its components.
     ***************************************************/
    private void createFollowersPanel() {
        followersPanel = new JPanel() {
            protected void paintComponent(final Graphics g) {
                g.drawImage(backgroundImage, 0, 0, null);
                super.paintComponent(g);
            }
        };

        followersPanel.setOpaque(false);
        followersPanel.setLayout(new GridBagLayout());
        fersgbc = new GridBagConstraints();

        followers = new Users(controller.getFollowers());

        fersAllButton = new JButton("Show All");
        fersAllButton.addActionListener(this);
        fersgbc.gridx = 0;
        fersgbc.gridy = 0;
        fersgbc.gridwidth = 1;
        fersgbc.fill = 1;
        followersPanel.add(fersAllButton, fersgbc);

        fersSearchButton = new JButton("Search");
        fersSearchButton.addActionListener(this);
        fersgbc.gridx = 1;
        fersgbc.gridy = 0;
        fersgbc.gridwidth = 1;
        fersgbc.fill = 1;
        followersPanel.add(fersSearchButton, fersgbc);

        fersSearchTextArea = new JTextArea();
        fersSearchTextArea
                .setBorder(BorderFactory.createLineBorder(Color.GRAY));
        fersgbc.gridx = 2;
        fersgbc.gridy = 0;
        fersgbc.gridwidth = 1;
        fersgbc.fill = 1;
        followersPanel.add(fersSearchTextArea, fersgbc);

        jlistFollowers = new JList<String>(followers);
        jlistFollowers.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list,
                    Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                try {
                    label.setIcon(new ImageIcon(new URL(followers
                            .getUser(index).getMiniProfileImageURL())));

                } catch (MalformedURLException e) {
                }
                return label;
            }
        });

        fersgbc.gridx = 0;
        fersgbc.gridy = 1;
        final int w = 3;
        fersgbc.gridwidth = w;
        fersgbc.fill = GridBagConstraints.HORIZONTAL;

        // TODO
        JScrollPane scrollPane = new JScrollPane(jlistFollowers);
        scrollPane.setPreferredSize(new Dimension(FOLLOWERS_WIDTH,
                FOLLOWERS_HEIGHT));

        followersPanel.add(scrollPane, fersgbc);

        followersTotal = new JLabel(controller.getFollowersCount()
                + " Followers");
        fersgbc.gridx = 0;
        fersgbc.gridy = 2;
        fersgbc.gridwidth = 1;
        fersgbc.fill = 1;
        followersPanel.add(followersTotal, fersgbc);
    }

    /****************************************************
     * Creates following panel and its components.
     ***************************************************/
    private void createFollowingPanel() {
        followingPanel = new JPanel() {
            protected void paintComponent(final Graphics g) {
                g.drawImage(backgroundImage, 0, 0, null);
                super.paintComponent(g);
            }
        };

        followingPanel.setOpaque(false);
        followingPanel.setLayout(new GridBagLayout());
        finggbc = new GridBagConstraints();

        following = new Users(controller.getFollowing());

        fingAllButton = new JButton("Show All");
        fingAllButton.addActionListener(this);
        finggbc.gridx = 0;
        finggbc.gridy = 0;
        finggbc.gridwidth = 1;
        finggbc.fill = 1;
        followingPanel.add(fingAllButton, finggbc);

        fingSearchButton = new JButton("Search");
        fingSearchButton.addActionListener(this);
        finggbc.gridx = 1;
        finggbc.gridy = 0;
        finggbc.gridwidth = 1;
        finggbc.fill = 1;
        followingPanel.add(fingSearchButton, finggbc);

        fingSearchTextArea = new JTextArea();
        fingSearchTextArea
                .setBorder(BorderFactory.createLineBorder(Color.GRAY));
        finggbc.gridx = 2;
        finggbc.gridy = 0;
        finggbc.gridwidth = 1;
        finggbc.fill = 1;
        followingPanel.add(fingSearchTextArea, finggbc);

        jlistFollowing = new JList<String>(following);
        jlistFollowing.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list,
                    Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                try {
                    label.setIcon(new ImageIcon(new URL(following
                            .getUser(index).getMiniProfileImageURL())));

                } catch (MalformedURLException e) {
                }
                return label;
            }
        });
        finggbc.gridx = 0;
        finggbc.gridy = 1;
        final int w = 3;
        finggbc.gridwidth = w;
        finggbc.fill = GridBagConstraints.HORIZONTAL;

        // TODO
        fingScrollPane = new JScrollPane(jlistFollowing);
        fingScrollPane.setPreferredSize(new Dimension(FOLLOWING_WIDTH,
                FOLLOWING_HEIGHT));

        followingPanel.add(fingScrollPane, finggbc);

        unfollow = new JButton("Unfollow");
        unfollow.addActionListener(this);
        finggbc.gridx = 0;
        finggbc.gridy = 2;
        finggbc.gridwidth = 2;
        finggbc.fill = 1;
        followingPanel.add(unfollow, finggbc);

        followingTotal = new JLabel("  Following "
                + controller.getFriendsCount());
        finggbc.gridx = 2;
        finggbc.gridy = 2;
        finggbc.gridwidth = 1;
        finggbc.fill = 1;
        followingPanel.add(followingTotal, finggbc);

    }

    /****************************************************
     * Creates tweet panel and its components.
     ***************************************************/
    private void createTweetPanel() {
        tweetPanel = new JPanel() {
            protected void paintComponent(final Graphics g) {
                g.drawImage(backgroundImage, 0, 0, null);
                super.paintComponent(g);
            }
        };
        tweetPanel.setOpaque(false);
        tweetPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;

        cancel = new JButton("Cancel");
        cancel.setFocusable(false);
        cancel.addActionListener(this);
        tweetPanel.add(cancel, gbc);

        attachImg = new JButton("Attach Image");
        attachImg.setFocusable(false);
        attachImg.addActionListener(this);
        gbc.gridx = 1;
        tweetPanel.add(attachImg, gbc);

        tweetSubmit = new JButton("Send Tweet");
        tweetSubmit.setFocusable(false);
        tweetSubmit.addActionListener(this);
        gbc.gridx = 2;
        tweetPanel.add(tweetSubmit, gbc);

        tweetText = new JTextArea();
        tweetText.addKeyListener(this);
        tweetText.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        tweetText.setFocusable(true);
        final int col = 30;
        tweetText.setColumns(col);
        final int row = 8;
        tweetText.setRows(row);
        tweetText.setLineWrap(true);
        gbc.gridx = 0;
        gbc.gridy = 1;
        final int w = 3;
        gbc.gridwidth = w;
        tweetPanel.add(tweetText, gbc);

        charsRemaining = new JLabel("140", JLabel.RIGHT);
        gbc.gridx = 2;
        gbc.gridy = 2;
        tweetPanel.add(charsRemaining, gbc);

        tweetTotal = new JLabel(controller.getTweetCount() + " Tweets");
        gbc.gridx = 0;
        final int gridy = 3;
        gbc.gridy = gridy;
        gbc.gridwidth = 1;
        tweetPanel.add(tweetTotal, gbc);

        tweetShow = new JButton("Show Tweets");
        tweetShow.setFocusable(false);
        tweetShow.addActionListener(this);
        gbc.gridx = 1;
        gbc.gridy = gridy;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.PAGE_END;
        tweetPanel.add(tweetShow, gbc);
    }

    

    /****************************************************
     * Creates Timeline panel and its components.
     ***************************************************/
    // here
    private void createTimelinePanel() {
        timelinePanel = new JPanel() {
            protected void paintComponent(final Graphics g) {
                g.drawImage(backgroundImage, 0, 0, null);
                super.paintComponent(g);
            }
        };

        timelinePanel.setOpaque(false);
        timelinePanel.setLayout(new GridBagLayout());
        GridBagConstraints timelinegbc = new GridBagConstraints();

        // Timeline Panel
        JPanel timelinePnl = new JPanel();
        JList<String> jlistTimeline = new JList<String>();
        Tweets tweets = controller.getHomeTimeline();
        jlistTimeline = new JList<String>(tweets);

        JScrollPane scrollPane = new JScrollPane(jlistTimeline);
        scrollPane.setPreferredSize(new Dimension(TIMELINE_WIDTH,
                TIMELINE_HEIGHT));

        timelinePnl.add(scrollPane);

        timelinegbc.gridx = 0;
        timelinegbc.gridy = 0;
        timelinegbc.fill = GridBagConstraints.HORIZONTAL;
        timelinePnl.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        timelinePanel.add(timelinePnl, timelinegbc);

        timelinegbc.insets = new Insets(0, 15, 0, 0);

        // Trending Panel
        JPanel trendingPnl = new JPanel();
        JList jlistTrending = new JList();
        trendingPnl.add(jlistTrending);
        trendingPnl.setPreferredSize(new Dimension(TRENDING_WIDTH,
                TRENDING_HEIGHT));
        timelinegbc.gridx = 1;
        trendingPnl.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        timelinePanel.add(trendingPnl, timelinegbc);

    }



    /****************************************************
     * Creates Messages panel and its components.
     ***************************************************/
    private void createMessagesPanel() {
        messagesPanel = new JPanel() {
            protected void paintComponent(final Graphics g) {
                g.drawImage(backgroundImage, 0, 0, null);
                super.paintComponent(g);
            }
        };
        messagesPanel.setOpaque(false);
        messagesPanel.setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;

    }

    /****************************************************
     * Creates menu bar.
     ***************************************************/
    private void createMenu() {
        menuBar = new JMenuBar();

        // File Menu
        fileMenu = new JMenu("File");
        exit = new JMenuItem("Exit");
        exit.addActionListener(this);
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,
                Event.CTRL_MASK));
        fileMenu.add(exit);

        // Tweet Menu
        tweetMenu = new JMenu("Tweet");
        newTweet = new JMenuItem("New Tweet");
        newTweet.addActionListener(this);
        newTweet.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,
                Event.CTRL_MASK));
        delete = new JMenuItem("Delete");
        delete.addActionListener(this);
        delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D,
                Event.CTRL_MASK));
        tweetMenu.add(newTweet);
        tweetMenu.add(delete);

        // About Menu
        aboutMenu = new JMenu("About");
        about = new JMenuItem("About Desktop Tweets");
        about.addActionListener(this);
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
                Event.CTRL_MASK));
        aboutMenu.add(about);

        // Add To MenuBar
        menuBar.add(fileMenu);
        menuBar.add(tweetMenu);
        menuBar.add(aboutMenu);
        setJMenuBar(menuBar);
    }

    /****************************************************
     * Creates tabbed pane.
     ***************************************************/
    private void createTabbedPane() {
        // UIManager.put("TabbedPane.selected", Color.WHITE);
        // UIManager.put("TabbedPane.contentAreaColor", Color.WHITE);
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setFocusable(false);
        tabbedPane.setBackground(Color.WHITE);
        tabbedPane.setForeground(Color.BLACK);
        final int fontsize = 14;
        tabbedPane.setFont(new Font("arial", Font.BOLD, fontsize));
        tabbedPane.setTabLayoutPolicy(JTabbedPane.CENTER);
        tabbedPane.addTab("Profile", profilePanel);
        tabbedPane.addTab("Timeline", timelinePanel);
        tabbedPane.addTab("Tweet", tweetPanel);
        tabbedPane.addTab("Followers", followersPanel);
        tabbedPane.addTab("Following", followingPanel);
        tabbedPane.addTab("Messages", messagesPanel);
        add(tabbedPane);
    }

    /***************************************************
     * Where all actions are delegated
     * 
     * @param ActionEvent
     *            e
     **************************************************/
    @Override
    public final void actionPerformed(final ActionEvent e) {
        Object source = e.getSource();

        if (source == exit) {
            System.exit(0);
        }
        if (source == newTweet) {
            tabbedPane.setSelectedComponent(tweetPanel);
        }
        if (source == delete) {
            DialogTweets x = new DialogTweets(this, controller.getUserTimeline());
            for (long l : x.getRemoveList()) {
                controller.destroyStatus(l);
            }
            updateTweetCount();
        }

        if (source == tweetSubmit) {
            int length = tweetText.getText().length();
            if (length > 0 && length <= 140) {
                if (attachImg.isEnabled() == true) {
                    if (controller.tweet(tweetText.getText())) {
                        updateTweetCount();
                        JOptionPane.showMessageDialog(null, "Tweet Sent.",
                                "Confirmation", JOptionPane.PLAIN_MESSAGE);
                    }
                } else {
                    if (controller
                            .tweetImage(attachedFile, tweetText.getText())) {
                        updateTweetCount();
                        JOptionPane.showMessageDialog(null, "Tweet Sent.",
                                "Confirmation", JOptionPane.PLAIN_MESSAGE);
                    }
                }
            } else if (length == 0) {
                if (attachImg.isEnabled() == true)
                    JOptionPane.showMessageDialog(null, "Enter text first!");
                else if (controller.tweetImage(attachedFile,
                        tweetText.getText())) {
                    updateTweetCount();
                    JOptionPane.showMessageDialog(null, "Tweet Sent.",
                            "Confirmation", JOptionPane.PLAIN_MESSAGE);
                }
            } else if (length > 140) {
                JOptionPane.showMessageDialog(null, "Tweet is too long");
            }
            e.setSource(cancel);
            actionPerformed(e);
        }

        if (source == about) {
            JOptionPane.showMessageDialog(null, "Desktop twitter application");
        }

        if (source == cancel) {
            tweetText.setText("");
            remaining = 140;
            charsRemaining.setText("" + remaining);
            attachedFile = null;
            attachImg.setEnabled(true);
            attachImg.setText("Attach Image");
        }
        if (source == tweetShow) {
            DialogTweets x = new DialogTweets(this, 
                    controller.getUserTimeline());
            for (long l : x.getRemoveList()) {
                controller.destroyStatus(l);
            }
            updateTweetCount();
        }

        if (source == attachImg) {
            JFileChooser jfc = new JFileChooser();
            int option = jfc.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                attachedFile = jfc.getSelectedFile();
                attachImg.setText("File Attached");
                attachImg.setEnabled(false);
            }
        }

        if (source == fingAllButton) {
            following.showAll();
        }

        if (source == fingSearchButton) {
            following.search(fingSearchTextArea.getText());
        }

        if (source == fersAllButton) {
            followers.showAll();
        }

        if (source == fersSearchButton) {
            followers.search(fersSearchTextArea.getText());
        }

        if (source == unfollow) {
            int i = jlistFollowing.getSelectedIndex();
            if (i >= 0) {
                long l = following.remove(jlistFollowing.getSelectedIndex());
                controller.unfollow(l);

                ((ProfilePanel) profilePanel).setFollowingCount(controller.getFriendsCount());
            } else {
                JOptionPane.showMessageDialog(null,
                        "Please first select a person or group", "Oops!",
                        JOptionPane.PLAIN_MESSAGE);
            }
        }
    }
     

    /****************************************************
     * Updates tweet count.
     ***************************************************/
    private void updateTweetCount() {
        ((ProfilePanel) profilePanel).setTweetCount(controller.getTweetCount());
        tweetTotal.setText(controller.getTweetCount() + " Tweets");
    }

    /****************************************************
     * Main Method
     * 
     * @param String
     *            [] args
     ***************************************************/
    public static void main(final String[] args) {
        new TwitterGUI();
    }

    @Override
    public void keyTyped(final KeyEvent e) {
        if (tabbedPane.getSelectedComponent() == tweetPanel) {
            int charCount = tweetText.getText().length();

            if (charCount <= 0)
                charsRemaining.setText("" + 140);
            else
                charsRemaining.setText("" + (140 - charCount));
        }
    }

    @Override
    public void keyPressed(final KeyEvent e) {
    }

    @Override
    public void keyReleased(final KeyEvent e) {
    }
}
