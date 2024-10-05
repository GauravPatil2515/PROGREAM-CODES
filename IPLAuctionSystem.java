import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class IPLAuctionSystem extends JFrame {
    private final int MAX_PLAYERS = 5;
    private final int TEAM_BUDGET = 10000;
    private final double BID_INCREMENT = 1.5;
    
    // Teams and Players
    private String[] teams = {"Chennai Super Kings", "Mumbai Indians", "Kolkata Knight Riders", "Royal Challengers Bangalore"};
    private Map<String, Integer> teamBudgets;
    private Map<String, ArrayList<String>> teamPlayers;
    
    private String[] players = {
            "Virat Kohli", "Rohit Sharma", "MS Dhoni", "KL Rahul", "Jasprit Bumrah", 
            "Hardik Pandya", "Ravindra Jadeja", "Rishabh Pant", "Shubman Gill", "Mohammad Shami",
            "Shreyas Iyer", "Axar Patel", "Prithvi Shaw", "Sanju Samson", "Suryakumar Yadav", 
            "Ishan Kishan", "Yuzvendra Chahal", "Deepak Chahar", "Bhuvneshwar Kumar", "Washington Sundar"
    };
    private int currentPlayerIndex = 0;
    private double currentBid = 0;
    private String highestBidder = null;

    // GUI components
    private JLabel currentPlayerLabel, currentBidLabel;
    private JButton[] bidButtons;
    private JButton lockBidButton, nextPlayerButton, unsoldButton, showTeamsButton;

    public IPLAuctionSystem() {
        setTitle("IPL Auction System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        teamBudgets = new HashMap<>();
        teamPlayers = new HashMap<>();
        
        // Initialize team budgets and player lists
        for (String team : teams) {
            teamBudgets.put(team, TEAM_BUDGET);
            teamPlayers.put(team, new ArrayList<>());
        }

        setLayout(new BorderLayout());
        
        // Player and Bid Section
        currentPlayerLabel = new JLabel("Player: " + players[currentPlayerIndex]);
        currentBidLabel = new JLabel("Current Bid: ₹0");
        
        JPanel playerPanel = new JPanel();
        playerPanel.add(currentPlayerLabel);
        playerPanel.add(currentBidLabel);
        add(playerPanel, BorderLayout.NORTH);
        
        // Team Bid Buttons
        JPanel bidPanel = new JPanel(new GridLayout(2, 2));
        bidButtons = new JButton[teams.length];
        for (int i = 0; i < teams.length; i++) {
            String team = teams[i];
            bidButtons[i] = new JButton("Bid: " + team);
            bidButtons[i].setBackground(getTeamColor(team));
            bidButtons[i].addActionListener(e -> placeBid(team));
            bidPanel.add(bidButtons[i]);
        }
        add(bidPanel, BorderLayout.CENTER);

        // Action Buttons
        JPanel actionPanel = new JPanel();
        lockBidButton = new JButton("Lock Bid");
        nextPlayerButton = new JButton("Next Player");
        unsoldButton = new JButton("Mark Unsold");
        showTeamsButton = new JButton("Show Teams");

        lockBidButton.addActionListener(e -> lockBid());
        nextPlayerButton.addActionListener(e -> nextPlayer());
        unsoldButton.addActionListener(e -> markUnsold());
        showTeamsButton.addActionListener(e -> showTeams());

        actionPanel.add(lockBidButton);
        actionPanel.add(nextPlayerButton);
        actionPanel.add(unsoldButton);
        actionPanel.add(showTeamsButton);
        add(actionPanel, BorderLayout.SOUTH);

        updateUI();
    }

    // Handle bidding
    private void placeBid(String team) {
        int budget = teamBudgets.get(team);
        double newBid = (highestBidder == null) ? 100 : currentBid * BID_INCREMENT;
        
        if (budget >= newBid) {
            currentBid = newBid;
            highestBidder = team;
            currentBidLabel.setText("Current Bid: ₹" + (int) currentBid + " by " + highestBidder);
        } else {
            JOptionPane.showMessageDialog(this, team + " doesn't have enough money.");
        }
    }

    // Lock the current bid
    private void lockBid() {
        if (highestBidder != null) {
            teamBudgets.put(highestBidder, teamBudgets.get(highestBidder) - (int) currentBid);
            teamPlayers.get(highestBidder).add(players[currentPlayerIndex]);
            JOptionPane.showMessageDialog(this, highestBidder + " bought " + players[currentPlayerIndex] + " for ₹" + (int) currentBid);
            nextPlayer();
        } else {
            JOptionPane.showMessageDialog(this, "No team has bid yet.");
        }
    }

    // Move to the next player
    private void nextPlayer() {
        highestBidder = null;
        currentBid = 0;
        currentPlayerIndex++;
        if (currentPlayerIndex < players.length) {
            updateUI();
        } else {
            JOptionPane.showMessageDialog(this, "Auction finished!");
        }
    }

    // Mark current player as unsold
    private void markUnsold() {
        JOptionPane.showMessageDialog(this, players[currentPlayerIndex] + " is unsold.");
        nextPlayer();
    }

    // Show teams and their acquired players
    private void showTeams() {
        StringBuilder report = new StringBuilder("Team Report:\n");
        for (String team : teams) {
            report.append(team).append(" - Budget: ₹").append(teamBudgets.get(team)).append("\nPlayers:\n");
            for (String player : teamPlayers.get(team)) {
                report.append(player).append("\n");
            }
            report.append("\n");
        }
        JOptionPane.showMessageDialog(this, report.toString());
    }

    // Update UI elements
    private void updateUI() {
        if (currentPlayerIndex < players.length) {
            currentPlayerLabel.setText("Player: " + players[currentPlayerIndex]);
            currentBidLabel.setText("Current Bid: ₹0");
        }
    }

    // Get team color for GUI
    private Color getTeamColor(String team) {
        switch (team) {
            case "Chennai Super Kings":
                return Color.YELLOW;
            case "Mumbai Indians":
                return Color.BLUE;
            case "Kolkata Knight Riders":
                return Color.PURPLE;
            case "Royal Challengers Bangalore":
                return Color.RED;
            default:
                return Color.GRAY;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            IPLAuctionSystem auctionSystem = new IPLAuctionSystem();
            auctionSystem.setVisible(true);
        });
    }
}
