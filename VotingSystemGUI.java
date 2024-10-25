import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;

public class VotingSystemGUI extends JFrame {

    private Map<String, Integer> candidates = new HashMap<>();
    private HashSet<String> voterIds = new HashSet<>();  // To store valid voter IDs
    private JPanel votingPanel;
    private JButton resultButton;
    private JTextField voterIdField;
    private String currentVoterId;

    // Constructor to set up the GUI
    public VotingSystemGUI(String[] candidateNames, String[] validVoterIds) {
        setTitle("Voting System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Center the window

        // Initialize candidates with zero votes
        for (String name : candidateNames) {
            candidates.put(name, 0);
        }

        // Add valid voter IDs to the set
        for (String id : validVoterIds) {
            voterIds.add(id);
        }

        // Create the main voting panel
        votingPanel = new JPanel();
        votingPanel.setLayout(new GridLayout(candidateNames.length + 3, 1));

        JLabel instructionLabel = new JLabel("Please enter your Voter ID and vote for your candidate:");
        votingPanel.add(instructionLabel);

        // Input field for Voter ID
        voterIdField = new JTextField(10);
        votingPanel.add(new JLabel("Enter Voter ID:"));
        votingPanel.add(voterIdField);

        // Add candidate buttons for voting
        for (String candidate : candidateNames) {
            JButton candidateButton = new JButton(candidate);
            candidateButton.addActionListener(new VoteButtonListener(candidate));
            votingPanel.add(candidateButton);
        }

        // Button to display results
        resultButton = new JButton("View Results");
        resultButton.addActionListener(new ResultButtonListener());
        votingPanel.add(resultButton);

        // Add the voting panel to the frame
        add(votingPanel);
    }

    // Inner class for handling vote button clicks
    private class VoteButtonListener implements ActionListener {
        private String candidate;

        public VoteButtonListener(String candidate) {
            this.candidate = candidate;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            // Check if the voter has a valid ID
            currentVoterId = voterIdField.getText().trim();
            if (voterIds.contains(currentVoterId)) {
                int currentVotes = candidates.get(candidate);
                candidates.put(candidate, currentVotes + 1);
                JOptionPane.showMessageDialog(null, "You voted for " + candidate + "!");
                voterIds.remove(currentVoterId); // Remove the voter after voting
            } else {
                JOptionPane.showMessageDialog(null, "Invalid or already used Voter ID.");
            }
        }
    }

    // Inner class for handling result button click
    private class ResultButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            StringBuilder results = new StringBuilder("Election Results:\n\n");
            for (Map.Entry<String, Integer> entry : candidates.entrySet()) {
                results.append(entry.getKey()).append(": ").append(entry.getValue()).append(" votes\n");
            }

            // Display results in a new window
            JFrame resultFrame = new JFrame("Results");
            resultFrame.setSize(300, 200);
            resultFrame.setLocationRelativeTo(null);
            JTextArea resultArea = new JTextArea(results.toString());
            resultArea.setEditable(false);
            resultFrame.add(new JScrollPane(resultArea));
            resultFrame.setVisible(true);
        }
    }

    // Main method to start the program
    public static void main(String[] args) {
        // Prompt for the number of candidates
        int numCandidates = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of candidates:"));
        String[] candidateNames = new String[numCandidates];

        // Input the names of candidates
        for (int i = 0; i < numCandidates; i++) {
            candidateNames[i] = JOptionPane.showInputDialog("Enter the name of candidate " + (i + 1) + ":");
        }

        // Prompt for the number of valid voters
        int numVoters = Integer.parseInt(JOptionPane.showInputDialog("Enter the number of voters:"));
        String[] validVoterIds = new String[numVoters];

        // Input the Voter IDs
        for (int i = 0; i < numVoters; i++) {
            validVoterIds[i] = JOptionPane.showInputDialog("Enter the Voter ID for voter " + (i + 1) + ":");
        }

        // Launch the voting system GUI
        SwingUtilities.invokeLater(() -> {
            VotingSystemGUI votingSystem = new VotingSystemGUI(candidateNames, validVoterIds);
            votingSystem.setVisible(true);
        });
    }
}
