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
import java.awt.GridLayout;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import twitter4j.DirectMessage;
import twitter4j.ResponseList;
import twitter4j.Trends;
import twitter4j.Trend;
import twitter4j.TwitterException;
import twitter4j.User;

import model.Tweets;
import model.Users;
import controller.TwitterController;

/**********************************************************************
 * Twitter GUI.
 * 
 * @author Nick, Vincenzo, Corey, Russ
 * @date March 18, 2013
 *********************************************************************/
public class TwitterGUI extends JFrame implements ActionListener, KeyListener,
        MouseListener {

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
    private JMenu fileMenu, tweetMenu, messagesMenu, aboutMenu;

    /** Items in the menu bar. */
    private JMenuItem exit, newTweet, delete, about, showAll, compose, inbox;

    /** Tabbed pane. */
    static JTabbedPane tabbedPane;

    /** Loading panel. */
    static LoadingPanel loadingPanel;

    /** Background image. */
    private Image backgroundImage;

    /** Add following panel. */
    private JPanel addFollowingPanel;

    /** Following Search. */
    private Users followingSearch;

    /** Add following text area. */
    private JTextArea addFollowingSearchTextArea;

    /** Add following JList. */
    private JList<String> jlistAddFollowing;

    /** Add following search button. */
    private JButton addFollowingSearchButton;

    /** Add following button. */
    private JButton addFollowingBtn;

    // Timeline Panel ******************************************************
    /** Final frame height. */
    private static final int TIMELINE_HEIGHT = 300;

    /** Final frame width. */
    private static final int TIMELINE_WIDTH = 600;

    /** Home table button. */
    private JButton homeTlBtn;

    /** User table button. */
    private JButton userTlBtn;

    /** Timeline JList. */
    private JList<String> jlistTimeline;

    /** Tweets. */
    private Tweets tweets;

    /** Timeline JScrollPane. */
    private JScrollPane TimelineScrollPane;

    // Tweet Panel *********************************************************
    /** GBC Layout. */
    private GridBagConstraints tweetgbc;

    /** Chars remaining. */
    private int remaining = CHAR_LIMIT;

    /** JButtons for tweet panel. */
    private JButton cancel, tweetSubmit, tweetShow, attachImg;

    /** Labels for characters remaining and total tweets. */
    static JLabel charsRemaining, tweetTotal;

    /** Text area to type outgoing tweet. */
    private JTextArea tweetText;

    /** Image File to be uploaded with tweet. */
    private File attachedFile;

    /** Final frame height. */
    private static final int TRENDING_HEIGHT = 250;

    /** Final frame width. */
    private static final int TRENDING_WIDTH = 200;

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

    /** Display all followers. */
    private JButton fersAllButton;
    
    /** Displays users profile information. */
    private JButton showProfile;

    // Following Panel *****************************************************
    /** Final frame height. */
    private static final int FOLLOWING_HEIGHT = 300;

    /** Final frame width. */
    private static final int FOLLOWING_WIDTH = 450;

    /** Small profile image size. */
    private static final int SMALL_PROFILE_IMAGE = 48;

    /** Display all following. */
    private JButton fingAllButton;

    /** Following Search Button. */
    private JButton fingSearchButton;

    /** Following ScrollPane. */
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

        try {
            frame = new JFrame();
            setTitle("HashTagSwag");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(FRAME_WIDTH, FRAME_HEIGHT);
            setResizable(false);
            setLocationRelativeTo(null);
            loadingPanel = new LoadingPanel();
            add(loadingPanel);
            setVisible(true);

            setUpController();

            backgroundImage = controller.getBackgroundImage();
            loadingPanel.incrementLoadingScreen();

            // Create components
            profilePanel = new ProfilePanel(controller);
            createTweetPanel();
            loadingPanel.incrementLoadingScreen();
            createTimelinePanel();
            loadingPanel.incrementLoadingScreen();
            createFollowingPanel();
            loadingPanel.incrementLoadingScreen();
            createFollowersPanel();
            loadingPanel.incrementLoadingScreen();
            createAddFollowingPanel();
            loadingPanel.incrementLoadingScreen();
            createMessagesPanel();
            createMenu();
            loadingPanel.incrementLoadingScreen();
            createTabbedPane();
            loadingPanel.incrementLoadingScreen();
            remove(loadingPanel);

            // fixes bug in linux
            String os = System.getProperty("os.name").toLowerCase();
            if (os.indexOf("nix") >= 0 || os.indexOf("nux") >= 0
                    || os.indexOf("aix") > 0) {
                setVisible(false);
                setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
                public void actionPerformed(final ActionEvent arg0) {
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
                public void actionPerformed(final ActionEvent arg0) {
                    if (Desktop.isDesktopSupported()) {
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

            final int AUTH_WIDTH = 500;
            final int AUTH_HEIGHT = 200;

            authFrame.setSize(AUTH_WIDTH, AUTH_HEIGHT);
            authFrame.setLocationRelativeTo(null);
            pnl.add(new JLabel(
                    "<html><center>Before you use this application, you "
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
        fersAllButton.setFocusPainted(false);
        fersAllButton.addActionListener(this);
        
        showProfile = new JButton("Show Profile");
        showProfile.setFocusPainted(false);
        showProfile.addActionListener(this);
        
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

        final int FERS_SIZE = 18;
        fersSearchTextArea = new JTextArea();
        fersSearchTextArea.setFont(new Font("arial", Font.PLAIN, FERS_SIZE));
        fersSearchTextArea
                .setBorder(BorderFactory.createLineBorder(Color.GRAY));
        fersgbc.gridx = 2;
        fersgbc.gridy = 0;
        fersgbc.gridwidth = 1;
        fersgbc.fill = 1;
        followersPanel.add(fersSearchTextArea, fersgbc);
        
        
        
        jlistFollowers = new JList<String>(followers);
        jlistFollowers.addMouseListener(this);
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

        JScrollPane scrollPane = new JScrollPane(jlistFollowers);
        scrollPane.setPreferredSize(new Dimension(FOLLOWERS_WIDTH,
                FOLLOWERS_HEIGHT));

        followersPanel.add(scrollPane, fersgbc);
        
        fersgbc.gridx = 3;
        fersgbc.gridy = 3;
        followersPanel.add(showProfile, fersgbc);

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
        fingSearchTextArea.setFont(new Font("arial", Font.PLAIN, 18));
        fingSearchTextArea
                .setBorder(BorderFactory.createLineBorder(Color.GRAY));
        finggbc.gridx = 2;
        finggbc.gridy = 0;
        finggbc.gridwidth = 1;
        finggbc.fill = 1;
        followingPanel.add(fingSearchTextArea, finggbc);

        jlistFollowing = new JList<String>(following);
        jlistFollowing.addMouseListener(this);
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
     * Display a temporary profile panel for any user
     * 
     * @param user
     ***************************************************/
    private void displayUserProfile(User usr) {
        JFrame fr = new JFrame();
        fr.setTitle(usr.getName());
        fr.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        fr.setLocationRelativeTo(null);
        fr.setResizable(false);
        JPanel pnl = new ProfilePanel(usr);
        fr.add(pnl);

        fr.setVisible(true);
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

        GridBagConstraints panelgbc = new GridBagConstraints();

        // This is the tweet half
        JPanel tweetPnl = new JPanel();
        tweetPnl.setLayout(new GridBagLayout());

        tweetgbc = new GridBagConstraints();
        tweetgbc.fill = GridBagConstraints.HORIZONTAL;
        tweetgbc.gridx = 0;
        tweetgbc.gridy = 0;

        cancel = new JButton("Cancel");
        cancel.setFocusable(false);
        cancel.addActionListener(this);
        tweetPnl.add(cancel, tweetgbc);

        attachImg = new JButton("Attach Image");
        attachImg.setFocusable(false);
        attachImg.addActionListener(this);
        tweetgbc.gridx = 1;
        tweetPnl.add(attachImg, tweetgbc);

        tweetSubmit = new JButton("Send Tweet");
        tweetSubmit.setFocusable(false);
        tweetSubmit.addActionListener(this);
        tweetgbc.gridx = 2;
        tweetPnl.add(tweetSubmit, tweetgbc);

        tweetText = new JTextArea();
        tweetText.addKeyListener(this);
        tweetText.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        tweetText.setPreferredSize(new Dimension(300, 195));
        tweetText.setFocusable(true);
        final int col = 30;
        tweetText.setColumns(col);
        final int row = 8;
        tweetText.setRows(row);
        tweetText.setLineWrap(true);
        tweetgbc.gridx = 0;
        tweetgbc.gridy = 1;
        final int w = 3;
        tweetgbc.gridwidth = w;
        tweetPnl.add(tweetText, tweetgbc);

        charsRemaining = new JLabel("140", JLabel.RIGHT);
        tweetgbc.gridx = 2;
        tweetgbc.gridy = 2;
        tweetPnl.add(charsRemaining, tweetgbc);

        tweetTotal = new JLabel(controller.getTweetCount() + " Tweets");
        tweetgbc.gridx = 0;
        final int gridy = 3;
        tweetgbc.gridy = gridy;
        tweetgbc.gridwidth = 1;
        tweetPnl.add(tweetTotal, tweetgbc);

        tweetShow = new JButton("Show Tweets");
        tweetShow.setFocusable(false);
        tweetShow.addActionListener(this);
        tweetgbc.gridx = 1;
        tweetgbc.gridy = gridy;
        tweetgbc.gridwidth = 1;
        tweetgbc.anchor = GridBagConstraints.PAGE_END;
        tweetPnl.add(tweetShow, tweetgbc);
        tweetPnl.setOpaque(false);
        tweetPanel.add(tweetPnl, panelgbc);

        panelgbc.insets = new Insets(0, 15, 0, 0);// padding

        // This is the trending half
        JPanel trendingPnl = new JPanel();

        Trends rawTrends = controller.getTrending();
        Trend[] trendArray = rawTrends.getTrends();
        String[] strTrends = new String[10];
        for (int x = 0; x < 10; x++) {
            strTrends[x] = trendArray[x].getName();
        }
        JList<String> jlistTrending = new JList<String>(strTrends);
        JScrollPane TrendingScrollPane = new JScrollPane(jlistTrending);

        trendingPnl.add(TrendingScrollPane);
        TrendingScrollPane.setPreferredSize(new Dimension(TRENDING_WIDTH,
                TRENDING_HEIGHT));
        trendingPnl.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panelgbc.gridx = 1;
        tweetPanel.add(trendingPnl, panelgbc);

    }

    /****************************************************
     * Creates Timeline panel and its components.
     ***************************************************/
    private void createTimelinePanel() {
        timelinePanel = new JPanel() {
            protected void paintComponent(final Graphics g) {
                g.drawImage(backgroundImage, 0, 0, null);
                super.paintComponent(g);
            }
        };

        timelinePanel.setOpaque(false);
        timelinePanel.setLayout(new GridBagLayout());
        GridBagConstraints timelinePanelgbc = new GridBagConstraints();

        // Button Panel
        JPanel tlButtonPnl = new JPanel();
        tlButtonPnl.setLayout(new GridLayout(1, 2));
        homeTlBtn = new JButton("Home Timeline");
        userTlBtn = new JButton("User Timeline");
        homeTlBtn.addActionListener(this);
        userTlBtn.addActionListener(this);
        tlButtonPnl.add(homeTlBtn);
        tlButtonPnl.add(userTlBtn);

        timelinePanelgbc.gridx = 0;
        timelinePanelgbc.gridy = 0;
        timelinePanel.add(tlButtonPnl, timelinePanelgbc);

        // Timeline Panel
        GridBagConstraints timelinegbc = new GridBagConstraints();

        JPanel timelinePnl = new JPanel();
        tweets = controller.getHomeTimeline();
        jlistTimeline = new JList<String>(tweets);

        TimelineScrollPane = new JScrollPane(jlistTimeline);
        TimelineScrollPane.setPreferredSize(new Dimension(TIMELINE_WIDTH,
                TIMELINE_HEIGHT));

        timelinePnl.add(TimelineScrollPane);

        timelinePanelgbc.gridy = 1;
        timelinegbc.fill = GridBagConstraints.HORIZONTAL;
        timelinePnl.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        timelinePanel.add(timelinePnl, timelinePanelgbc);

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

        ResponseList<DirectMessage> rl = controller.getAllMessages();
        
        // EXAMPLE
        //DirectMessage dm = rl.get(0);
        //User sender = dm.getSender();
        //String text = dm.getText();


        final JPanel mlPanel = new JPanel();
        mlPanel.setBackground(Color.WHITE);
        mlPanel.setSize(FRAME_WIDTH, FRAME_HEIGHT);

        String[] cols = { "Date", "Sender", "Message" };

        final Object[][] messages = new Object[rl.size()][5];

        for (int i = 0; i < rl.size(); i++) {
            DirectMessage direct = rl.get(i);
            String created = new SimpleDateFormat("MM/dd/yyyy hh:mm a")
                    .format(direct.getCreatedAt());

            messages[i][0] = created;
            messages[i][1] = direct.getSenderScreenName();
            messages[i][2] = direct.getText();
            messages[i][3] = direct.getId();
            messages[i][4] = direct.getSenderId();
        }

        // Table definitions
        final JTable table = new JTable(messages, cols);
        table.setPreferredScrollableViewportSize(new Dimension(FRAME_HEIGHT,
                FRAME_WIDTH));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                viewMessage(row);
            }

            private void viewMessage(int row) {
                mlPanel.setVisible(false);
                long messageId = (long) messages[row][3];

                DirectMessage directMessage = controller
                        .showDirectMessage(messageId);

                // Display Message
                JPanel msgPanel = new JPanel();
                msgPanel.setLayout(new GridBagLayout());
                msgPanel.setSize(300, 500);
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.fill = GridBagConstraints.HORIZONTAL;

                // Date
                JLabel created = new JLabel(messages[row][0].toString());
                gbc.gridx = 1; /* Right */
                gbc.gridy = 0; /* Below */
                msgPanel.add(created, gbc);

                // Picture
                ImageIcon icon = controller
                        .getSmallerProfileImage((long) messages[row][4]);
                gbc.gridx = 0;
                gbc.gridy = 1;
                JButton pic = new JButton();
                pic.setIcon(icon);
                pic.setPreferredSize(new Dimension(SMALL_PROFILE_IMAGE,
                        SMALL_PROFILE_IMAGE));
                msgPanel.add(pic, gbc);

                // Text
                JTextArea text = new JTextArea();
                text.setText(messages[row][2].toString());
                gbc.gridx = 2;
                gbc.gridy = 1;
                msgPanel.add(text, gbc);

                // Reply stuff
                tweetSubmit = new JButton("Send Message");
                tweetSubmit.setFocusable(false);
                // tweetSubmit.addActionListener(this);
                gbc.gridx = 0;
                gbc.gridy = 3;
                msgPanel.add(tweetSubmit, gbc);

                tweetText = new JTextArea();
                // tweetText.addKeyListener(this);
                tweetText.setBorder(BorderFactory.createLineBorder(Color.GRAY));
                // tweetText.setPreferredSize(new Dimension(300,195));
                tweetText.setFocusable(true);
                final int col = 30;
                tweetText.setColumns(col);
                final int row2 = 8;
                tweetText.setRows(row2);
                tweetText.setLineWrap(true);
                gbc.gridx = 0;
                gbc.gridy = 3;
                final int w = 2;
                gbc.gridwidth = w;
                msgPanel.add(tweetText, gbc);

                charsRemaining = new JLabel("140", JLabel.RIGHT);
                gbc.gridx = 2;
                gbc.gridy = 3;
                msgPanel.add(charsRemaining, gbc);

                messagesPanel.add(msgPanel);
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);

        mlPanel.add(scrollPane);
        // mlPanel.add(view);

        messagesPanel.add(mlPanel);

        /*
         * // Build 2D array for the list for (int i = 0; i < rl.size(); i++) {
         * DirectMessage dm = rl.get(i);
         * 
         * // Cast everything as a string to make it fit in the array // Is
         * there a better way to do this? Maybe a struct-like object for Java?
         * messages[i][0] = "" + dm.getCreatedAt() + ""; messages[i][1] = "" +
         * dm.getId() + ""; messages[i][2] = "" + dm.getSenderId() + "";
         * messages[i][3] = dm.getSenderScreenName(); messages[i][4] =
         * dm.getText(); }
         */

    }

    private void createAddFollowingPanel() {
        addFollowingPanel = new JPanel() {
            protected void paintComponent(final Graphics g) {
                g.drawImage(backgroundImage, 0, 0, null);
                super.paintComponent(g);
            }
        };

        addFollowingPanel.setOpaque(false);
        addFollowingPanel.setLayout(new GridBagLayout());
        fersgbc = new GridBagConstraints();

        followingSearch = new Users();

        addFollowingSearchButton = new JButton("Search");
        addFollowingSearchButton.setFocusable(false);
        addFollowingSearchButton.addActionListener(this);
        fersgbc.gridx = 1;
        fersgbc.gridy = 0;
        fersgbc.gridwidth = 1;
        fersgbc.fill = 1;
        addFollowingPanel.add(addFollowingSearchButton, fersgbc);

        addFollowingSearchTextArea = new JTextArea();
        addFollowingSearchTextArea.setFont(new Font("arial", Font.PLAIN, 18));
        addFollowingSearchTextArea.setBorder(BorderFactory
                .createLineBorder(Color.GRAY));
        fersgbc.gridx = 2;
        addFollowingPanel.add(addFollowingSearchTextArea, fersgbc);

        jlistAddFollowing = new JList<String>(followingSearch);
        jlistAddFollowing.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList list,
                    Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                try {
                    label.setIcon(new ImageIcon(new URL(followingSearch
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
        JScrollPane scrollPane = new JScrollPane(jlistAddFollowing);
        scrollPane.setPreferredSize(new Dimension(FOLLOWERS_WIDTH,
                FOLLOWERS_HEIGHT));

        addFollowingPanel.add(scrollPane, fersgbc);

        addFollowingBtn = new JButton("Add");
        addFollowingBtn.setFocusable(false);
        addFollowingBtn.addActionListener(this);
        fersgbc.gridy = 3;
        fersgbc.fill = GridBagConstraints.VERTICAL;
        // fersgbc.gridx = 3;

        addFollowingPanel.add(addFollowingBtn, fersgbc);
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
        showAll = new JMenuItem("Show All");
        showAll.addActionListener(this);
        showAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_AT,
                Event.CTRL_MASK));
        tweetMenu.add(newTweet);
        tweetMenu.add(delete);
        tweetMenu.add(showAll);

        // Messages Menu
        messagesMenu = new JMenu("Messages");
        compose = new JMenuItem("Compose");
        compose.addActionListener(this);
        compose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M,
                Event.CTRL_MASK));
        inbox = new JMenuItem("Inbox");
        inbox.addActionListener(this);
        inbox.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,
                Event.CTRL_MASK));
        messagesMenu.add(compose);
        messagesMenu.add(inbox);

        // About Menu
        aboutMenu = new JMenu("About");
        about = new JMenuItem("About HashTagSwag");
        about.addActionListener(this);
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
                Event.CTRL_MASK));
        aboutMenu.add(about);

        // Add To MenuBar
        menuBar.add(fileMenu);
        menuBar.add(tweetMenu);
        menuBar.add(messagesMenu);
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
        tabbedPane.addTab("Add Following", addFollowingPanel);
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
        if (source == delete || source == showAll) {
            DialogTweets x = new DialogTweets(this,
                    controller.getUserTimeline());
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
                    JOptionPane.showMessageDialog(null,
                            "Tweet is still empty.", "Oops!",
                            JOptionPane.PLAIN_MESSAGE);
                else if (controller.tweetImage(attachedFile,
                        tweetText.getText())) {
                    updateTweetCount();
                    JOptionPane.showMessageDialog(null, "Tweet Sent.",
                            "Confirmation", JOptionPane.PLAIN_MESSAGE);
                }
            } else if (length > 140) {
                JOptionPane.showMessageDialog(null, "Tweet is too long.",
                        "Oops!", JOptionPane.PLAIN_MESSAGE);
            }
            e.setSource(cancel);
            actionPerformed(e);
        }

        if (source == about) {
            JOptionPane
                    .showMessageDialog(
                            null,
                            "HashTagSwag\n\nCorey Alberda\nRuss Johnson\nNick Olesak\nVincenzo Pavano\n\n03/18/2013\nv.2.0");
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

        if (source == compose) {
            tabbedPane.setSelectedComponent(messagesPanel);
        }

        if (source == inbox) {
            tabbedPane.setSelectedComponent(messagesPanel);
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
        
        if (source == showProfile) {
            User u = controller.getUser(followers.showId(jlistFollowers
                    .getSelectedIndex()));
            displayUserProfile(u);
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

        if (source == addFollowingSearchButton) {
            followingSearch.searchTwitter(addFollowingSearchTextArea.getText(),
                    controller);
        }

        if (source == addFollowingBtn) {
            int i = jlistAddFollowing.getSelectedIndex();
            if (i >= 0) {
                long l = followingSearch.showId(i);
                User u = controller.follow(l);
                following.add(u);
                followingTotal.setText("  Following "
                        + controller.getFriendsCount());
                ((ProfilePanel) profilePanel).setFollowingCount(controller
                        .getFriendsCount());
            } else {
                JOptionPane.showMessageDialog(null,
                        "Please first select a person or group", "Oops!",
                        JOptionPane.PLAIN_MESSAGE);
            }
        }

        if (source == unfollow) {
            int i = jlistFollowing.getSelectedIndex();
            if (i >= 0) {
                long l = following.remove(jlistFollowing.getSelectedIndex());
                controller.unfollow(l);
                followingTotal.setText("  Following "
                        + controller.getFriendsCount());

                ((ProfilePanel) profilePanel).setFollowingCount(controller
                        .getFriendsCount());
            } else {
                JOptionPane.showMessageDialog(null,
                        "Please first select a person or group", "Oops!",
                        JOptionPane.PLAIN_MESSAGE);
            }
        }

        if (source == inbox) {
            tabbedPane.setSelectedComponent(messagesPanel);
        }

        if (source == compose) {

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

    public void keyPressed(final KeyEvent e) {
    }

    public void keyReleased(final KeyEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2 && e.getSource() == jlistFollowing) {
            User u = controller.getUser(following.showId(jlistFollowing
                    .getSelectedIndex()));
            displayUserProfile(u);
        }
        if (e.getClickCount() == 2 && e.getSource() == jlistFollowers) {
            User u = controller.getUser(followers.showId(jlistFollowers
                    .getSelectedIndex()));
            displayUserProfile(u);
        }
    }

    public void mouseEntered(MouseEvent arg0) {
    }

    public void mouseExited(MouseEvent arg0) {
    }

    public void mousePressed(MouseEvent arg0) {
    }

    public void mouseReleased(MouseEvent arg0) {
    }

}
