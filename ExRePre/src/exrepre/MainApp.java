import javax.swing.*;
import java.awt.*;

/**
 * Entry point. Holds a CardLayout that swaps between screens.
 * Add new screens by creating a JPanel class and registering it in cardPanel.
 */
public class MainApp extends JFrame {

    public static final String DASHBOARD = "DASHBOARD";
    public static final String HIERARCHY = "HIERARCHY";
    public static final String SESSION = "SESSION";
    public static final String MOOD_JOURNAL = "MOOD_JOURNAL";
    public static final String PROGRESS = "PROGRESS";

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);

    public MainApp() {
        setTitle("ERP Support Tool");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 650);
        setMinimumSize(new Dimension(700, 500));
        setLocationRelativeTo(null);

        cardPanel.add(new DashboardPanel(this), DASHBOARD);
        cardPanel.add(new HierarchyPanel(this), HIERARCHY);
        cardPanel.add(new SessionPanel(this), SESSION);
        cardPanel.add(new MoodJournalPanel(this), MOOD_JOURNAL);
        cardPanel.add(new ProgressPanel(this), PROGRESS);

        add(cardPanel);
        showScreen(DASHBOARD);
    }

    /** Call this from any screen to navigate, e.g. mainApp.showScreen(MainApp.HIERARCHY); */
    public void showScreen(String screenName) {
        cardLayout.show(cardPanel, screenName);
    }

    public static void main(String[] args) {
        // Use system look and feel so it doesn't look like default ugly Swing
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> new MainApp().setVisible(true));
    }
}
