import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;

/**
 * Session screen — used while doing a real-life exposure exercise.
 * User starts a timer, then periodically logs a SUDS (0-10) anxiety rating.
 * The history list at the bottom shows the curve going down over time.
 *
 * NOTE: this does NOT do the exposure for the user — it's just a stopwatch + log.
 * TODO: replace the JList history with an actual line chart later.
 */
public class SessionPanel extends JPanel {

    private final JLabel timerLabel = new JLabel("00:00:00", SwingConstants.CENTER);
    private final JSlider sudsSlider = new JSlider(0, 10, 5);
    private final DefaultListModel<String> historyModel = new DefaultListModel<>();
    private final JList<String> historyList = new JList<>(historyModel);

    private Timer swingTimer;
    private int secondsElapsed = 0;
    private final List<int[]> history = new ArrayList<>(); // [secondsElapsed, sudsValue]

    public SessionPanel(MainApp app) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- Top bar ---
        JPanel topBar = new JPanel(new BorderLayout());
        JButton backBtn = new JButton("< Back");
        backBtn.addActionListener(e -> {
            stopTimer();
            app.showScreen(MainApp.HIERARCHY);
        });
        JLabel title = new JLabel("ERP Session", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        topBar.add(backBtn, BorderLayout.WEST);
        topBar.add(title, BorderLayout.CENTER);
        add(topBar, BorderLayout.NORTH);

        // --- Center: trigger info + timer + slider ---
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        JLabel triggerLabel = new JLabel("Trigger: (selected trigger goes here)");
        triggerLabel.setFont(new Font("SansSerif", Font.PLAIN, 14));
        triggerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(triggerLabel);
        center.add(Box.createVerticalStrut(20));

        timerLabel.setFont(new Font("SansSerif", Font.BOLD, 48));
        timerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(timerLabel);
        center.add(Box.createVerticalStrut(10));

        JPanel timerButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton startBtn = new JButton("Start");
        JButton stopBtn = new JButton("Stop");
        startBtn.addActionListener(e -> startTimer());
        stopBtn.addActionListener(e -> stopTimer());
        timerButtons.add(startBtn);
        timerButtons.add(stopBtn);
        timerButtons.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(timerButtons);
        center.add(Box.createVerticalStrut(30));

        JLabel sudsTitle = new JLabel("How anxious do you feel right now? (0-10)");
        sudsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(sudsTitle);

        sudsSlider.setMajorTickSpacing(1);
        sudsSlider.setPaintTicks(true);
        sudsSlider.setPaintLabels(true);
        sudsSlider.setMaximumSize(new Dimension(400, 60));
        sudsSlider.setAlignmentX(Component.CENTER_ALIGNMENT);
        center.add(sudsSlider);

        JButton logBtn = new JButton("Log this rating");
        logBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        logBtn.addActionListener(e -> logRating());
        center.add(Box.createVerticalStrut(10));
        center.add(logBtn);

        add(center, BorderLayout.CENTER);

        // --- Bottom: history of ratings this session ---
        JPanel historyPanel = new JPanel(new BorderLayout());
        historyPanel.setBorder(BorderFactory.createTitledBorder("This session's ratings"));
        historyPanel.add(new JScrollPane(historyList), BorderLayout.CENTER);
        historyPanel.setPreferredSize(new Dimension(0, 150));
        add(historyPanel, BorderLayout.SOUTH);
    }

    private void startTimer() {
        if (swingTimer != null && swingTimer.isRunning()) return;
        swingTimer = new Timer(1000, (ActionEvent e) -> {
            secondsElapsed++;
            updateTimerLabel();
        });
        swingTimer.start();
    }

    private void stopTimer() {
        if (swingTimer != null) swingTimer.stop();
    }

    private void updateTimerLabel() {
        int h = secondsElapsed / 3600;
        int m = (secondsElapsed % 3600) / 60;
        int s = secondsElapsed % 60;
        timerLabel.setText(String.format("%02d:%02d:%02d", h, m, s));
    }

    private void logRating() {
        int value = sudsSlider.getValue();
        history.add(new int[]{secondsElapsed, value});
        historyModel.addElement(formatTime(secondsElapsed) + "  ->  SUDS: " + value);
    }

    private String formatTime(int seconds) {
        int m = seconds / 60;
        int s = seconds % 60;
        return String.format("%02d:%02d", m, s);
    }
}
