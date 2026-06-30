import javax.swing.*;
import java.awt.*;

/**
 * Main Dashboard — landing screen with 3 buttons into each module.
 */
public class DashboardPanel extends JPanel {

    public DashboardPanel(MainApp app) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        JLabel title = new JLabel("ERP Support Tool", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        add(title, BorderLayout.NORTH);

        JPanel buttonGrid = new JPanel(new GridLayout(1, 3, 20, 20));
        buttonGrid.setBorder(BorderFactory.createEmptyBorder(40, 0, 40, 0));

        JButton erpBtn = bigButton("ERP Tracker", "Build your trigger hierarchy and run sessions");
        JButton moodBtn = bigButton("Mood Journal", "Log how you're feeling today");
        JButton progressBtn = bigButton("Progress", "See your trends over time");

        erpBtn.addActionListener(e -> app.showScreen(MainApp.HIERARCHY));
        moodBtn.addActionListener(e -> app.showScreen(MainApp.MOOD_JOURNAL));
        progressBtn.addActionListener(e -> app.showScreen(MainApp.PROGRESS));

        buttonGrid.add(erpBtn);
        buttonGrid.add(moodBtn);
        buttonGrid.add(progressBtn);

        add(buttonGrid, BorderLayout.CENTER);

        JLabel disclaimer = new JLabel(
            "<html><center>This tool supports — it does not replace — therapy or medication.<br>" +
            "Always follow guidance from your psychiatrist or therapist.</center></html>",
            SwingConstants.CENTER
        );
        disclaimer.setFont(new Font("SansSerif", Font.ITALIC, 12));
        disclaimer.setForeground(Color.GRAY);
        add(disclaimer, BorderLayout.SOUTH);
    }

    private JButton bigButton(String title, String subtitle) {
        JButton button = new JButton(
            "<html><center><b style='font-size:14px'>" + title + "</b><br>" +
            "<span style='font-size:11px'>" + subtitle + "</span></center></html>"
        );
        button.setPreferredSize(new Dimension(200, 150));
        button.setFocusPainted(false);
        return button;
    }
}
