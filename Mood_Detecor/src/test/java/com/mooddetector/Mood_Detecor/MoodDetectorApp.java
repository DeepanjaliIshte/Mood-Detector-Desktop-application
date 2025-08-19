package com.mooddetector.Mood_Detecor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MoodDetectorApp extends JFrame {
    private JTextArea textInput;
    private JLabel resultEmoji;
    private JLabel resultLabel;
    private JTextArea historyArea;
    private MoodAnalyzer moodAnalyzer;
    private List<MoodEntry> moodHistory;
    private static final String HISTORY_FILE = "mood_history.txt";

    public MoodDetectorApp() {
        moodAnalyzer = new MoodAnalyzer();
        moodHistory = new ArrayList<>();
        loadHistory();
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("🎭 Mood Detector - Fun Edition");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);

        // Gradient background panel
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(135, 206, 250),
                        getWidth(), getHeight(), new Color(255, 182, 193)
                );
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout(15, 15));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Header
        JLabel headerLabel = new JLabel(" Mood Detector", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Comic Sans MS", Font.BOLD, 30));
        headerLabel.setForeground(new Color(50, 0, 70));
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Center panel
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setOpaque(false);

        // Input text
        textInput = new JTextArea(6, 50);
        textInput.setFont(new Font("Arial", Font.PLAIN, 14));
        textInput.setLineWrap(true);
        textInput.setWrapStyleWord(true);
        textInput.setBorder(BorderFactory.createTitledBorder(" ✍️ Write your thoughts "));
        JScrollPane scrollPane = new JScrollPane(textInput);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Analyze button
        JButton analyzeButton = new JButton(" Analyze Mood ");
        analyzeButton.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
        analyzeButton.setBackground(new Color(123, 104, 238));
        analyzeButton.setForeground(Color.WHITE);
        analyzeButton.setFocusPainted(false);
        analyzeButton.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        analyzeButton.addActionListener(this::analyzeAction);
        centerPanel.add(analyzeButton, BorderLayout.SOUTH);

        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // Result panel
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setOpaque(false);
        resultPanel.setBorder(BorderFactory.createTitledBorder(" Mood Result "));

        resultEmoji = new JLabel("🤔", SwingConstants.CENTER);
        resultEmoji.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));
        resultPanel.add(resultEmoji, BorderLayout.CENTER);

        resultLabel = new JLabel("Your mood will appear here", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 18));
        resultPanel.add(resultLabel, BorderLayout.SOUTH);

        mainPanel.add(resultPanel, BorderLayout.SOUTH);

        // History panel
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setOpaque(false);
        historyPanel.setBorder(BorderFactory.createTitledBorder(" 📜 Mood History "));

        historyArea = new JTextArea(10, 25);
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Courier New", Font.PLAIN, 12));
        JScrollPane historyScrollPane = new JScrollPane(historyArea);
        historyPanel.add(historyScrollPane, BorderLayout.CENTER);

        JButton clearHistoryButton = new JButton("🧹 Clear History");
        clearHistoryButton.addActionListener(e -> clearHistory());
        historyPanel.add(clearHistoryButton, BorderLayout.SOUTH);

        mainPanel.add(historyPanel, BorderLayout.EAST);

        add(mainPanel);
        updateHistoryDisplay();
    }

    private void analyzeAction(ActionEvent e) {
        String text = textInput.getText().trim();
        if (text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter text to analyze 😅");
            return;
        }

        MoodResult result = moodAnalyzer.analyzeMood(text);
        displayResult(result);

        MoodEntry entry = new MoodEntry(text, result, LocalDateTime.now());
        moodHistory.add(entry);
        saveHistory();
        updateHistoryDisplay();
    }

    private void displayResult(MoodResult result) {
        String mood = result.getMood().toLowerCase();
        String emoji;

        switch (mood) {
            case "joy": case "happy": emoji = "😄"; break;
            case "sadness": case "sad": emoji = "😢"; break;
            case "anger": case "angry": emoji = "😠"; break;
            case "anxiety": case "anxious": emoji = "😰"; break;
            case "contentment": case "calm": emoji = "😌"; break;
            case "excited": emoji = "🎉"; break;
            default: emoji = "🤔"; break;
        }

        resultEmoji.setText(emoji);
        resultLabel.setText("Mood: " + result.getMood().toUpperCase() +
                " | Confidence: " + String.format("%.0f%%", result.getConfidence() * 100));
    }

    private void updateHistoryDisplay() {
        StringBuilder sb = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd HH:mm");

        for (int i = Math.max(0, moodHistory.size() - 10); i < moodHistory.size(); i++) {
            MoodEntry entry = moodHistory.get(i);
            sb.append(String.format("%s - %s (%.0f%%)\n",
                    entry.getTimestamp().format(formatter),
                    entry.getResult().getMood().toUpperCase(),
                    entry.getResult().getConfidence() * 100));
        }

        historyArea.setText(sb.toString());
    }

    private void clearHistory() {
        moodHistory.clear();
        saveHistory();
        updateHistoryDisplay();
    }

    private void saveHistory() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(HISTORY_FILE))) {
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
            for (MoodEntry entry : moodHistory) {
                writer.println(entry.getTimestamp().format(formatter) + "|" +
                        entry.getResult().getMood() + "|" +
                        entry.getResult().getConfidence() + "|" +
                        entry.getText().replace("\n", "\\n"));
            }
        } catch (IOException e) {
            System.err.println("Error saving history: " + e.getMessage());
        }
    }

    private void loadHistory() {
        File file = new File(HISTORY_FILE);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\|", 4);
                if (parts.length == 4) {
                    LocalDateTime timestamp = LocalDateTime.parse(parts[0], formatter);
                    String mood = parts[1];
                    double confidence = Double.parseDouble(parts[2]);
                    String text = parts[3].replace("\\n", "\n");

                    MoodResult result = new MoodResult(mood, confidence);
                    MoodEntry entry = new MoodEntry(text, result, timestamp);
                    moodHistory.add(entry);
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading history: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MoodDetectorApp().setVisible(true));
    }
}
