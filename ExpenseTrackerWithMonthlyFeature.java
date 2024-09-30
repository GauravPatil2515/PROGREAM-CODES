import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExpenseTrackerWithMonthlyFeature extends JFrame {
    private JTextField salaryField;
    private JTextField amountField;
    private JComboBox<String> categoryComboBox;
    private JLabel balanceLabel;
    private JLabel salaryBalanceLabel;
    private JLabel savingsLabel;
    private Map<String, Integer> expenses;
    private int totalSalary = 0;
    private int totalExpenses = 0;
    private int totalSavings = 0;
    private ArrayList<String> transactionHistory;
    private Map<String, ArrayList<String>> monthlyHistories; // To store history of each month

    public ExpenseTrackerWithMonthlyFeature() {
        // Set up the window
        setTitle("Expense Tracker with Monthly Feature");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Initialize expense categories and transaction history
        expenses = new HashMap<>();
        transactionHistory = new ArrayList<>();
        monthlyHistories = new HashMap<>();
        String[] categories = {"Food", "Travel", "Education", "Medical", "Shopping"};
        for (String category : categories) {
            expenses.put(category, 0);
        }

        // Set up GUI components
        salaryField = new JTextField(10);
        amountField = new JTextField(10);
        categoryComboBox = new JComboBox<>(categories);
        JButton setSalaryButton = new JButton("Set Salary");
        JButton addButton = new JButton("Add Expense");
        JButton showHistoryButton = new JButton("Show This Month's History");
        JButton endMonthButton = new JButton("End Month");
        JButton showPreviousMonthButton = new JButton("Show Previous Month History");
        JButton yearlyReportButton = new JButton("Show Yearly Report");
        balanceLabel = new JLabel("Total Expenses: ₹0");
        salaryBalanceLabel = new JLabel("Remaining Salary: ₹0");
        savingsLabel = new JLabel("Savings: ₹0");

        // Set up panel for salary input
        JPanel salaryPanel = new JPanel();
        salaryPanel.add(new JLabel("Monthly Salary:"));
        salaryPanel.add(salaryField);
        salaryPanel.add(setSalaryButton);

        // Set up panel for input
        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Amount:"));
        inputPanel.add(amountField);
        inputPanel.add(new JLabel("Category:"));
        inputPanel.add(categoryComboBox);
        inputPanel.add(addButton);
        inputPanel.add(balanceLabel);
        inputPanel.add(salaryBalanceLabel);
        inputPanel.add(savingsLabel);

        // Add action listeners
        setSalaryButton.addActionListener(e -> setSalary());
        addButton.addActionListener(e -> addExpense());
        showHistoryButton.addActionListener(e -> showTransactionHistory());
        endMonthButton.addActionListener(e -> endMonth());
        showPreviousMonthButton.addActionListener(e -> showPreviousMonthHistory());
        yearlyReportButton.addActionListener(e -> showYearlyReport());

        // Add salary panel, input panel, and graph to frame
        add(salaryPanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(new GraphPanel(), BorderLayout.SOUTH);

        // Add buttons for showing history, previous month, and yearly report
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(showHistoryButton);
        bottomPanel.add(showPreviousMonthButton);
        bottomPanel.add(yearlyReportButton);
        bottomPanel.add(endMonthButton);
        add(bottomPanel, BorderLayout.PAGE_END);
    }

    private void setSalary() {
        try {
            totalSalary = Integer.parseInt(salaryField.getText());
            salaryBalanceLabel.setText("Remaining Salary: ₹" + (totalSalary - totalExpenses));
            salaryField.setEnabled(false);  // Disable after setting salary
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid salary.");
        }
    }

    private void addExpense() {
        try {
            int amount = Integer.parseInt(amountField.getText());
            String category = (String) categoryComboBox.getSelectedItem();
            expenses.put(category, expenses.get(category) + amount);
            totalExpenses += amount;
            balanceLabel.setText("Total Expenses: ₹" + totalExpenses);
            salaryBalanceLabel.setText("Remaining Salary: ₹" + (totalSalary - totalExpenses));
            
            // Record the transaction
            transactionHistory.add("₹" + amount + " spent on " + category);
            amountField.setText("");

            // Update savings
            totalSavings = totalSalary - totalExpenses;
            savingsLabel.setText("Savings: ₹" + totalSavings);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid amount.");
        }
    }

    private void showTransactionHistory() {
        if (transactionHistory.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No expenses recorded for this month.");
        } else {
            StringBuilder history = new StringBuilder("Monthly Expenses:\n");
            for (String transaction : transactionHistory) {
                history.append(transaction).append("\n");
            }
            JOptionPane.showMessageDialog(this, history.toString());
        }
    }

    private void endMonth() {
        int confirmation = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to close this month and start fresh?", 
                "End Month Confirmation", 
                JOptionPane.YES_NO_OPTION);
        
        if (confirmation == JOptionPane.YES_OPTION) {
            // Store the current month's history
            String currentMonth = java.time.LocalDate.now().getMonth().toString();
            monthlyHistories.put(currentMonth, new ArrayList<>(transactionHistory));

            expenses.replaceAll((k, v) -> 0);  // Reset all expenses
            transactionHistory.clear();  // Clear the transaction history
            totalExpenses = 0;  // Reset total expenses
            balanceLabel.setText("Total Expenses: ₹0");
            salaryBalanceLabel.setText("Remaining Salary: ₹" + totalSalary);
            savingsLabel.setText("Savings: ₹0");
            JOptionPane.showMessageDialog(this, "Month ended. Ready to track next month.");
        }
    }

    private void showPreviousMonthHistory() {
        String previousMonth = java.time.LocalDate.now().minusMonths(1).getMonth().toString();
        ArrayList<String> previousMonthHistory = monthlyHistories.get(previousMonth);
        
        if (previousMonthHistory == null || previousMonthHistory.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No expenses recorded for " + previousMonth + ".");
        } else {
            StringBuilder history = new StringBuilder(previousMonth + " Expenses:\n");
            for (String transaction : previousMonthHistory) {
                history.append(transaction).append("\n");
            }
            JOptionPane.showMessageDialog(this, history.toString());
        }
    }

    private void showYearlyReport() {
        StringBuilder yearlyReport = new StringBuilder("Yearly Expenses Report:\n");
        int yearlyTotalExpenses = 0;
        for (Map.Entry<String, ArrayList<String>> entry : monthlyHistories.entrySet()) {
            int monthlyTotal = entry.getValue().size(); // Count of transactions for each month
            yearlyTotalExpenses += monthlyTotal; // This assumes a fixed cost of ₹1 for each recorded expense for demonstration
            yearlyReport.append(entry.getKey()).append(": ").append(monthlyTotal).append(" transactions\n");
        }
        yearlyReport.append("Total Yearly Expenses: ₹").append(yearlyTotalExpenses);
        JOptionPane.showMessageDialog(this, yearlyReport.toString());
    }

    private class GraphPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // Set up basic drawing parameters
            int width = getWidth();
            int height = getHeight();
            int barWidth = width / 2;
            int maxAmount = Math.max(totalSalary, totalExpenses);

            // Draw bar for total salary
            int salaryBarHeight = (int) ((double) totalSalary / maxAmount * (height - 50));
            g2d.setColor(Color.GREEN);
            g2d.fillRect(50, height - salaryBarHeight, barWidth - 100, salaryBarHeight);
            g2d.setColor(Color.BLACK);
            g2d.drawString("Salary: ₹" + totalSalary, 60, height - salaryBarHeight - 5);

            // Draw bar for total expenses
            int expenseBarHeight = (int) ((double) totalExpenses / maxAmount * (height - 50));
            g2d.setColor(Color.RED);
            g2d.fillRect(250, height - expenseBarHeight, barWidth - 100, expenseBarHeight);
            g2d.setColor(Color.BLACK);
            g2d.drawString("Expenses: ₹" + totalExpenses, 260, height - expenseBarHeight - 5);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(600, 200);  // Adjust graph size
        }
    }

    // Main method to run the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ExpenseTrackerWithMonthlyFeature().setVisible(true);
        });
    }
}

