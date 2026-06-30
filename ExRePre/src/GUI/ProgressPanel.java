import javax.swing.*;
import java.awt.*;

/**
 * Progress Dashboard — overview of trends over time.
 * TODO: replace placeholder boxes with real charts (e.g. using JFreeChart, or hand-drawn on a JPanel).
 */
public class ProgressPanel extends JPanel {

    public ProgressPanel(MainApp app) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- Top bar ---
        JPanel topBar = new JPanel(new BorderLayout());
        JButton backBtn = new JButton("< Back");
        backBtn.addActionListener(e -> app.showScreen(MainApp.DASHBOARD));
        JLabel title = new JLabel("Your Progress", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        topBar.add(backBtn, BorderLayout.WEST);
        topBar.add(title, BorderLayout.CENTER);
        add(topBar, BorderLayout.NORTH);

        // --- Center: placeholder stat cards + chart areas ---
        JPanel statsRow = new JPanel(new GridLayout(1, 3, 15, 0));
        statsRow.add(statCard("Total Sessions", "0"));
        statsRow.add(statCard("Triggers Tracked", "0"));
        statsRow.add(statCard("Avg. Mood (7d)", "—"));

        JPanel chartsArea = new JPanel(new GridLayout(2, 1, 0, 15));
        chartsArea.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
        chartsArea.add(placeholderChart("Mood over time (weekly/monthly)"));
        chartsArea.add(placeholderChart("SUDS improvement per trigger (first session vs. latest)"));

        JPanel center = new JPanel(new BorderLayout());
        center.add(statsRow, BorderLayout.NORTH);
        center.add(chartsArea, BorderLayout.CENTER);

        add(center, BorderLayout.CENTER);
    }

    private JPanel statCard(String label, String value) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);
        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        JLabel textLabel = new JLabel(label, SwingConstants.CENTER);
        textLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        card.add(Box.createVerticalStrut(10));
        card.add(valueLabel);
        card.add(textLabel);
        card.add(Box.createVerticalStrut(10));
        return card;
    }

    private JPanel placeholderChart(String label) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(label));
        JLabel placeholder = new JLabel("(chart goes here)", SwingConstants.CENTER);
        placeholder.setForeground(Color.GRAY);
        panel.add(placeholder, BorderLayout.CENTER);
        return panel;
    }
}
