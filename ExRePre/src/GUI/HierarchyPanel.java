import javax.swing.*;
import java.awt.*;

/**
 * Hierarchy Builder — list of triggers, sorted easiest -> hardest (low SUDS to high SUDS).
 * Each item shows: action (bold), reason (smaller), days tracked, SUDS score.
 * TODO: wire this up to a real Trigger list (currently just placeholder UI).
 */
public class HierarchyPanel extends JPanel {

    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private final JList<String> triggerList = new JList<>(listModel);

    public HierarchyPanel(MainApp app) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- Top bar: title + back button ---
        JPanel topBar = new JPanel(new BorderLayout());
        JButton backBtn = new JButton("< Back");
        backBtn.addActionListener(e -> app.showScreen(MainApp.DASHBOARD));
        JLabel title = new JLabel("Your Trigger Hierarchy", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        topBar.add(backBtn, BorderLayout.WEST);
        topBar.add(title, BorderLayout.CENTER);
        add(topBar, BorderLayout.NORTH);

        // --- Placeholder sample data so the list isn't empty ---
        listModel.addElement(sampleItem("Touch the doorknob without re-checking", "Fear it isn't locked", 3, 12));
        listModel.addElement(sampleItem("Touch the light switch without repeating", "Fear of a traffic accident", 6, 47));
        listModel.addElement(sampleItem("Leave without checking the gas valve twice", "Fear of a gas leak / fire", 8, 5));

        triggerList.setCellRenderer(new TriggerCellRenderer());
        triggerList.setFixedCellHeight(80);
        JScrollPane scrollPane = new JScrollPane(triggerList);
        add(scrollPane, BorderLayout.CENTER);

        // --- Bottom bar: add new trigger + start session ---
        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton addBtn = new JButton("+ Add Trigger");
        JButton startBtn = new JButton("Start Session ->");
        addBtn.addActionListener(e -> showAddTriggerDialog());
        startBtn.addActionListener(e -> app.showScreen(MainApp.SESSION));
        bottomBar.add(addBtn);
        bottomBar.add(startBtn);
        add(bottomBar, BorderLayout.SOUTH);
    }

    private String sampleItem(String action, String reason, int suds, int days) {
        // Encoding fields as a delimited string for now — replace with a real Trigger object later.
        return action + "||" + reason + "||" + suds + "||" + days;
    }

    private void showAddTriggerDialog() {
        JTextField actionField = new JTextField();
        JTextField reasonField = new JTextField();
        JSpinner sudsSpinner = new JSpinner(new SpinnerNumberModel(5, 0, 10, 1));

        JPanel form = new JPanel(new GridLayout(0, 1, 5, 5));
        form.add(new JLabel("Action (what do you do?)"));
        form.add(actionField);
        form.add(new JLabel("Reason (why does it feel necessary?)"));
        form.add(reasonField);
        form.add(new JLabel("Estimated SUDS (0-10)"));
        form.add(sudsSpinner);

        int result = JOptionPane.showConfirmDialog(this, form, "Add New Trigger",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION && !actionField.getText().isBlank()) {
            listModel.addElement(sampleItem(actionField.getText(), reasonField.getText(),
                    (Integer) sudsSpinner.getValue(), 0));
        }
    }

    /** Custom renderer to draw each trigger as: bold action / small reason / days+SUDS */
    private static class TriggerCellRenderer extends JPanel implements ListCellRenderer<String> {
        private final JLabel actionLabel = new JLabel();
        private final JLabel reasonLabel = new JLabel();
        private final JLabel metaLabel = new JLabel();

        TriggerCellRenderer() {
            setLayout(new BorderLayout());
            JPanel textPanel = new JPanel(new GridLayout(2, 1));
            actionLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
            reasonLabel.setFont(new Font("SansSerif", Font.PLAIN, 11));
            reasonLabel.setForeground(Color.GRAY);
            textPanel.add(actionLabel);
            textPanel.add(reasonLabel);
            add(textPanel, BorderLayout.CENTER);
            metaLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
            metaLabel.setHorizontalAlignment(SwingConstants.RIGHT);
            add(metaLabel, BorderLayout.EAST);
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                    BorderFactory.createEmptyBorder(8, 10, 8, 10)
            ));
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends String> list, String value,
                                                        int index, boolean isSelected, boolean cellHasFocus) {
            String[] parts = value.split("\\|\\|");
            actionLabel.setText(parts[0]);
            reasonLabel.setText(parts[1]);
            metaLabel.setText("<html>SUDS: " + parts[2] + "<br>Tracked: " + parts[3] + " days</html>");
            setBackground(isSelected ? new Color(230, 240, 255) : Color.WHITE);
            setOpaque(true);
            return this;
        }
    }
}
