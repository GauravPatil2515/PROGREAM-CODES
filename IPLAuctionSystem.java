import javax.swing.*;
import java.awt.*;
import java.util.*;

public class IPLAuctionSystem extends JFrame {
    private final int MAX_PLAYERS = 5;
    private final int TEAM_BUDGET = 10000;
    private final double BID_INCREMENT = 1.5;
    Color PURPLE = new Color(128, 0, 128);

    // Teams and Players
    private String[] teams = {
            "Chennai Super Kings", "Mumbai Indians", "Kolkata Knight Riders", "Royal Challengers Bangalore",
            "Delhi Capitals", "Sunrisers Hyderabad", "Punjab Kings", "Lucknow Super Giants",
            "Gujarat Titans", "Rajasthan Royals"
    };

    private Map<String, Integer> teamBudgets;
    private Map<String, ArrayList<String>> teamPlayers;
    private Map<String, Integer> teamComposition; // To track roles composition for each team

    private String[] players = {
            "Virat Kohli (Batsman)", "Rohit Sharma (Batsman)", "MS Dhoni (Wicketkeeper)", "KL Rahul (Batsman)",
            "Jasprit Bumrah (Bowler)", "Hardik Pandya (All-Rounder)", "Ravindra Jadeja (All-Rounder)",
            "Rishabh Pant (Wicketkeeper)", "Shubman Gill (Batsman)", "Mohammad Shami (Bowler)",
            "Shreyas Iyer (Batsman)", "Axar Patel (All-Rounder)", "Prithvi Shaw (Batsman)", "Sanju Samson (Wicketkeeper)",
            "Suryakumar Yadav (Batsman)", "Ishan Kishan (Wicketkeeper)", "Yuzvendra Chahal (Bowler)",
            "Deepak Chahar (Bowler)", "Bhuvneshwar Kumar (Bowler)", "Washington Sundar (All-Rounder)"
    };

    private String[] roles = {"Batsman", "Bowler", "All-Rounder", "Wicketkeeper"};

    private int currentPlayerIndex = 0;
    private double currentBid = 0;
    private String highestBidder = null;

    // GUI components
    private JLabel currentPlayerLabel, currentBidLabel, roleLabel;
    private JButton[] bidButtons;
    private JButton lockBidButton, nextPlayerButton, unsoldButton, showTeamsButton;

    public IPLAuctionSystem() {
        setTitle("IPL Auction System");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        teamBudgets = new HashMap<>();
        teamPlayers = new HashMap<>();
        teamComposition = new HashMap<>(); // To track roles

        // Initialize team budgets and player lists
        for (String team : teams) {
            teamBudgets.put(team, TEAM_BUDGET);
            teamPlayers.put(team, new ArrayList<>());
            teamComposition.put(team, 0); // Track composition for role constraints
        }

        setLayout(new BorderLayout());

        // Build the panels and GUI elements
        buildPlayerPanel();
        buildBidPanel();
        buildActionPanel();

        updateUI();
    }

    // Build player panel
    private void buildPlayerPanel() {
        currentPlayerLabel = new JLabel("Player: " + getPlayerName(currentPlayerIndex));
        currentPlayerLabel.setFont(new Font("Arial", Font.BOLD, 16));

        roleLabel = new JLabel("Role: " + getPlayerRole(currentPlayerIndex));
        roleLabel.setFont(new Font("Arial", Font.ITALIC, 14));

        currentBidLabel = new JLabel("Current Bid: ₹0");
        currentBidLabel.setFont(new Font("Arial", Font.BOLD, 16));
        currentBidLabel.setForeground(Color.RED);

        JPanel playerPanel = new JPanel();
        playerPanel.setBackground(new Color(240, 255, 255));
        playerPanel.add(currentPlayerLabel);
        playerPanel.add(roleLabel);
        playerPanel.add(currentBidLabel);
        add(playerPanel, BorderLayout.NORTH);
    }

    // Build bidding panel
    private void buildBidPanel() {
        JPanel bidPanel = new JPanel(new GridLayout(3, 4));
        bidPanel.setBorder(BorderFactory.createTitledBorder("Teams Bidding"));
        bidPanel.setBackground(new Color(255, 250, 205));

        bidButtons = new JButton[teams.length];
        for (int i = 0; i < teams.length; i++) {
            String team = teams[i];
            bidButtons[i] = new JButton("Bid: " + team);
            bidButtons[i].setBackground(getTeamColor(team));
            bidButtons[i].setFont(new Font("Arial", Font.PLAIN, 12));
            bidButtons[i].addActionListener(e -> placeBid(team));
            bidButtons[i].setToolTipText("Click to place bid for " + team);
            bidPanel.add(bidButtons[i]);
        }
        add(bidPanel, BorderLayout.CENTER);
    }

    // Build action panel
    private void buildActionPanel() {
        JPanel actionPanel = new JPanel();
        actionPanel.setBackground(new Color(245, 245, 245));

        lockBidButton = new JButton("Lock Bid");
        lockBidButton.setFont(new Font("Arial", Font.BOLD, 12));
        lockBidButton.setToolTipText("Lock the highest bid for the current player.");
        lockBidButton.addActionListener(e -> lockBid());

        nextPlayerButton = new JButton("Next Player");
        nextPlayerButton.setFont(new Font("Arial", Font.BOLD, 12));
        nextPlayerButton.setToolTipText("Move to the next player in the auction.");
        nextPlayerButton.addActionListener(e -> nextPlayer());

        unsoldButton = new JButton("Mark Unsold");
        unsoldButton.setFont(new Font("Arial", Font.BOLD, 12));
        unsoldButton.setToolTipText("Mark the current player as unsold.");
        unsoldButton.addActionListener(e -> markUnsold());

        showTeamsButton = new JButton("Show Teams");
        showTeamsButton.setFont(new Font("Arial", Font.BOLD, 12));
        showTeamsButton.setToolTipText("Show the current status of all teams.");
        showTeamsButton.addActionListener(e -> showTeams());

        actionPanel.add(lockBidButton);
        actionPanel.add(nextPlayerButton);
        actionPanel.add(unsoldButton);
        actionPanel.add(showTeamsButton);

        add(actionPanel, BorderLayout.SOUTH);
    }

    // Handle bidding
    private void placeBid(String team) {
        int budget = teamBudgets.get(team);
        double newBid = (highestBidder == null) ? 100 : currentBid * BID_INCREMENT;

        if (budget >= newBid) {
            currentBid = newBid;
            highestBidder = team;
            currentBidLabel.setText("Current Bid: ₹" + (int) currentBid + " by " + highestBidder);
            JOptionPane.showMessageDialog(this, team + " has placed a bid of ₹" + (int) currentBid);
        } else {
            JOptionPane.showMessageDialog(this, team + " doesn't have enough money to place this bid.");
        }
    }

    // Lock the current bid
    private void lockBid() {
        if (highestBidder != null) {
            teamBudgets.put(highestBidder, teamBudgets.get(highestBidder) - (int) currentBid);
            teamPlayers.get(highestBidder).add(getPlayerName(currentPlayerIndex));
            teamComposition.put(highestBidder, teamComposition.get(highestBidder) + 1);

            JOptionPane.showMessageDialog(this, highestBidder + " bought " + getPlayerName(currentPlayerIndex)
                    + " (" + getPlayerRole(currentPlayerIndex) + ")" + " for ₹" + (int) currentBid);
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
            JOptionPane.showMessageDialog(this, "Auction finished! All players have been auctioned.");
        }
    }

    // Mark current player as unsold
    private void markUnsold() {
        JOptionPane.showMessageDialog(this, getPlayerName(currentPlayerIndex) + " is unsold.");
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
        JOptionPane.showMessageDialog(this, report.toString(), "Team Report", JOptionPane.INFORMATION_MESSAGE);
    }

    // Utility methods to get player info
    private String getPlayerName(int index) {
        return players[index].split(" \\(")[0];
    }

    private String getPlayerRole(int index) {
        return players[index].split("\\(")[1].replace(")", "");
    }

    // Update UI labels
    private void updateUI() {
        currentPlayerLabel.setText("Player: " + getPlayerName(currentPlayerIndex));
        roleLabel.setText("Role: " + getPlayerRole(currentPlayerIndex));
        currentBidLabel.setText("Current Bid: ₹0");
    }

    // Color coding for teams
    private Color getTeamColor(String team) {
        switch (team) {
            case "Chennai Super Kings": return Color.YELLOW;
            case "Mumbai Indians": return Color.BLUE;
            case "Kolkata Knight Riders": return PURPLE;
            case "Royal Challengers Bangalore": return Color.RED;
            case "Delhi Capitals": return Color.CYAN;
            case "Sunrisers Hyderabad": return Color.ORANGE;
            case "Punjab Kings": return Color.MAGENTA;
            case "Lucknow Super Giants": return Color.LIGHT_GRAY;
            case "Gujarat Titans": return Color.DARK_GRAY;
            case "Rajasthan Royals": return new Color(255, 20, 147); // Hot pink
            default: return Color.WHITE;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            IPLAuctionSystem auctionSystem = new IPLAuctionSystem();
            auctionSystem.setVisible(true);
        });
    }
}
