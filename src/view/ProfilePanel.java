package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.LineBorder;

import controller.TwitterController;

public class ProfilePanel extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1L;

    /** Strings for profile information. */
    private String displayName, twitterName, description, location, website;

    /** Buttons for profile: Followers, Following, and number of tweets. */
    private JButton followersBtn, followingBtn, tweetsBtn;

    /** Profile Image. */
    private ImageIcon profileImage;

    /** Background and banner image. */
    private Image profileBanner;
    
    private TwitterController controller;
    
    
    
    public ProfilePanel (TwitterController cntrlr) {
        controller = cntrlr;
        displayName = controller.getDisplayName();
        twitterName = controller.getTwitterName();
        description = controller.getDescription();
        location = controller.getLocation();
        website = controller.getWebsite();
        profileImage = controller.getProfileImage();
        profileBanner = controller.getProfileBanner();
        TwitterGUI.loadingPanel.incrementLoadingScreen();
        
        
        
        JPanel infoPanel = new JPanel() {
            protected void paintComponent(final Graphics g) {
                final int eight = 8;
                final int three = 3;
                final int ten = 10;
                g.drawImage(profileBanner,
                        (TwitterGUI.FRAME_WIDTH / 2) - profileBanner.getWidth(null) / 2
                                - eight, TwitterGUI.FRAME_HEIGHT / three + ten
                                - profileBanner.getHeight(null) / 2, null);
            }
        };
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        infoPanel.paintComponents(null);
        c.fill = GridBagConstraints.NONE;

        // Profile Image
        ImageIcon img = profileImage;
        c.gridy = 0;
        JButton profImgBtn = getCountButton(0, null);
        profImgBtn.setPreferredSize(new Dimension(img.getIconWidth() 
                + 2, img.getIconHeight() + 2));
        final int four = 4;
        profImgBtn.setBorder(new LineBorder(Color.WHITE, four));
        profImgBtn.setIcon(img);
        infoPanel.add(profImgBtn, c);

        // Display Name
        final int five = 5;
        c.ipady = five;
        c.gridy = 1;
        JLabel displayNameLbl = new JLabel(displayName);
        displayNameLbl.setFont(new Font("arial", Font.BOLD, five * five - 1));
        displayNameLbl.setForeground(Color.WHITE);
        infoPanel.add(displayNameLbl, c);

        // Twitter Name
        c.gridy = 2;
        JLabel twitterNameLbl = new JLabel(twitterName);
        twitterNameLbl
                .setFont(new Font("arial", Font.PLAIN, five + five + five));
        twitterNameLbl.setForeground(Color.WHITE);
        infoPanel.add(twitterNameLbl, c);
        final int thirty = 30;
        c.ipady = thirty; // more padding

        // Description
        c.gridy = 1 + 1 + 1;
        c.ipady = 0;
        createDescriptionPanel(infoPanel, c);
        
        TwitterGUI.loadingPanel.incrementLoadingScreen();

        // Location
        c.ipady = five;
        c.gridy = five + 1;
        JLabel locationLbl = new JLabel(location);
        final int fifteen = 15;
        locationLbl.setFont(new Font("arial", Font.BOLD, fifteen));
        locationLbl.setForeground(Color.WHITE);
        infoPanel.add(locationLbl, c);

        // Website
        final int seven = 7;
        c.gridy = seven;
        JLabel websiteLbl = new JLabel(website);
        websiteLbl.setFont(new Font("arial", Font.PLAIN, seven + seven));
        websiteLbl.setForeground(Color.WHITE);
        infoPanel.add(websiteLbl, c);

        /** COUNT PANEL */
        JPanel countPanel = new JPanel();
        countPanel.setLayout(new GridBagLayout());
        GridBagConstraints cpc = new GridBagConstraints();
        cpc.fill = GridBagConstraints.NONE;
        final int thrity = 30;
        cpc.ipadx = thrity;
        countPanel.setOpaque(false);

        followersBtn = getCountButton(controller.getFollowersCount(),
                "Followers");
        followersBtn.addActionListener(this);
        followingBtn = getCountButton(controller.getFriendsCount(),
                "Following");
        followingBtn.addActionListener(this);
        tweetsBtn = getCountButton(controller.getTweetCount(), "Tweets");
        tweetsBtn.addActionListener(this);

        cpc.gridy = 0;
        countPanel.add(followersBtn, cpc);
        countPanel.add(new JLabel(" "), cpc);
        countPanel.add(followingBtn, cpc);
        countPanel.add(new JLabel(" "), cpc);
        countPanel.add(tweetsBtn, cpc);
        cpc.gridy = 1;
        countPanel.add(new JLabel(" "), cpc);

        /** PROFILE PANEL */
 
        super.setOpaque(false);
        super.setLayout(new BorderLayout());
        super.add(infoPanel, BorderLayout.CENTER);
        super.add(countPanel, BorderLayout.SOUTH);
    }
    
    
    private void createDescriptionPanel(final JPanel panel,
            final GridBagConstraints c) {
        final int max = 55;
        final int fontsize = 14;

        // single line
        if (description.length() < max) {
            JLabel descriptionLbl = new JLabel(description);
            descriptionLbl.setFont(new Font("arial", Font.PLAIN, fontsize));
            descriptionLbl.setForeground(Color.WHITE);
            panel.add(descriptionLbl, c);

            // double line
        } else if (description.length() < max + max) {
            String d1, d2;

            String[] w = description.split(" ");
            int wMid = (w.length / 2) + 1;
            int dMid = description.length() / 2;
            d1 = description.substring(0,
                    description.indexOf(w[wMid], dMid - 1));
            d2 = description.substring(description.indexOf(w[wMid], dMid - 1));

            JLabel description1Lbl = new JLabel(d1);
            JLabel description2Lbl = new JLabel(d2);
            description1Lbl.setFont(new Font("arial", Font.PLAIN, fontsize));
            description1Lbl.setForeground(Color.WHITE);
            description2Lbl.setFont(new Font("arial", Font.PLAIN, fontsize));
            description2Lbl.setForeground(Color.WHITE);
            panel.add(description1Lbl, c);
            c.gridy = 2 + 2;
            panel.add(description2Lbl, c);

            // triple line
        } else {
            String d1, d2, d3;
            String[] w = description.split(" ");
            int wThird = (w.length / (1 + 2)) + 1;
            int dThird = description.length() / (1 + 2);

            d1 = description.substring(0,
                    description.indexOf(w[wThird], dThird));
            d2 = description.substring(description.indexOf(w[wThird], dThird),
                    description.indexOf(w[wThird * 2], dThird * 2));
            d3 = description.substring(description.indexOf(w[wThird * 2],
                    dThird * 2));

            JLabel description1Lbl = new JLabel(d1);
            description1Lbl.setFont(new Font("arial", Font.PLAIN, fontsize));
            description1Lbl.setForeground(Color.WHITE);
            JLabel description2Lbl = new JLabel(d2);
            description2Lbl.setFont(new Font("arial", Font.PLAIN, fontsize));
            description2Lbl.setForeground(Color.WHITE);
            JLabel description3Lbl = new JLabel(d3);
            description3Lbl.setFont(new Font("arial", Font.PLAIN, fontsize));
            description3Lbl.setForeground(Color.WHITE);

            panel.add(description1Lbl, c);
            c.gridy = 2 + 2;
            panel.add(description2Lbl, c);
            c.gridy = 2 + 2 + 1;
            panel.add(description3Lbl, c);
        }
    }
    
    
    /****************************************************
     * Gets plain button for the profile tab page.
     * 
     * @param String
     *            line1
     * @param String
     *            line2
     * @return JButton
     ***************************************************/
    private JButton getCountButton(final int count, final String name) {
        String text = "<html><center><font size=6>" + count + "</font><br><i>"
                      + name + "</i></center></html>";
        JButton tmp = new JButton();
        final int w = 120;
        final int l = 50;
        final int fontsize = 15;
        tmp.setPreferredSize(new Dimension(w, l));
        tmp.setFont(new Font("Arial", Font.BOLD, fontsize));
        tmp.setText(text);
        tmp.setBackground(Color.WHITE);
        tmp.setForeground(Color.DARK_GRAY);
        tmp.setBorder(new LineBorder(Color.GRAY, 1, true));
        tmp.setFocusable(false);
        tmp.setFocusPainted(false);
        return tmp;
    }
    
    
    

    public void setFollowersCount(int count) {
        followersBtn.setText("<html><center><font size=6>" + count 
                + "</font><br><i>Followers</i></center></html>");
    }


    public void setFollowingCount(int count) {
        followingBtn.setText("<html><center><font size=6>" + count 
                + "</font><br><i>Following</i></center></html>");
    }


    public void setTweetCount(int count) {
        tweetsBtn.setText("<html><center><font size=6>" + count 
                + "</font><br><i>Tweets</i></center></html>");
    }

    protected void paintComponent(final Graphics g) {
        g.drawImage(new ImageIcon("src/background.jpeg").getImage(), 0, 0, null);
        super.paintComponent(g);
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        Object source = arg0.getSource();
        JTabbedPane tabbedPane = TwitterGUI.tabbedPane;
        
        if (source == followersBtn) {
            tabbedPane.setSelectedComponent(TwitterGUI.followersPanel);
        }
        
        if (source == followingBtn) {
            tabbedPane.setSelectedComponent(TwitterGUI.followingPanel);
        } 
        
        if (source == tweetsBtn) {
            DialogTweets x = new DialogTweets(TwitterGUI.frame, controller.getUserTimeline());
            for (long l : x.getRemoveList()) {
                controller.destroyStatus(l);
            }
            setTweetCount(controller.getTweetCount());
            TwitterGUI.tweetTotal.setText(controller.getTweetCount() + " Tweets");
        }
    }
}
