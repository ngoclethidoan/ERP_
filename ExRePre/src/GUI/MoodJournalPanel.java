import javax.swing.*;
import java.awt.*;

/**
 * Mood Journal — daily entry screen.
 * Mood score (0-10), descriptor adjectives (multi-select), free text notes.
 * TODO: hook up "Save Entry" to actually persist to a file/JSON.
 */
public class MoodJournalPanel extends JPanel {

    private final JSlider moodSlider = new JSlider(0, 10, 5);
    private final JTextArea notesArea = new JTextArea(6, 30);
    private final String[] adjectives = {
        "Calm", "Anxious", "Sad", "Happy", "Irritable", "Tired",
        "Energetic", "Overwhelmed", "Hopeful", "Numb"
    };
    private final java.util.List<JCheckBox> adjectiveBoxes = new java.util.ArrayList<>();

    public MoodJournalPanel(MainApp app) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // --- Top bar ---
        JPanel topBar = new JPanel(new BorderLayout());
        JButton backBtn = new JButton("< Back");
        backBtn.addActionListener(e -> app.showScreen(MainApp.DASHBOARD));
        JLabel title = new JLabel("Mood Journal", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        topBar.add(backBtn, BorderLayout.WEST);
        topBar.add(title, BorderLayout.CENTER);
        add(topBar, BorderLayout.NORTH);

        // --- Center form ---
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));

        JLabel moodLabel = new JLabel("Mood today (0 = very low, 10 = very high)");
        moodLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(moodLabel);

        moodSlider.setMajorTickSpacing(1);
        moodSlider.setPaintTicks(true);
        moodSlider.setPaintLabels(true);
        moodSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
        moodSlider.setMaximumSize(new Dimension(500, 60));
        form.add(moodSlider);
        form.add(Box.createVerticalStrut(20));

        JLabel adjLabel = new JLabel("How would you describe today? (select all that apply)");
        adjLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(adjLabel);

        JPanel adjPanel = new JPanel(new GridLayout(0, 3, 10, 5));
        adjPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        adjPanel.setMaximumSize(new Dimension(500, 150));
        for (String adj : adjectives) {
            JCheckBox box = new JCheckBox(adj);
            adjectiveBoxes.add(box);
            adjPanel.add(box);
        }
        form.add(adjPanel);
        form.add(Box.createVerticalStrut(20));

        JLabel notesLabel = new JLabel("Notes (optional)");
        notesLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        form.add(notesLabel);

        notesArea.setLineWrap(true);
        notesArea.setWrapStyleWord(true);
        JScrollPane notesScroll = new JScrollPane(notesArea);
        notesScroll.setAlignmentX(Component.LEFT_ALIGNMENT);
        notesScroll.setMaximumSize(new Dimension(500, 150));
        form.add(notesScroll);

        add(form, BorderLayout.CENTER);

        // --- Bottom: save button ---
        JPanel bottomBar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveBtn = new JButton("Save Entry");
        saveBtn.addActionListener(e -> {
            // TODO: persist this entry (mood value, selected adjectives, notes, date) to storage
            JOptionPane.showMessageDialog(this, "Entry saved! (placeholder — not yet persisted)");
        });
        bottomBar.add(saveBtn);
        add(bottomBar, BorderLayout.SOUTH);
    }
}
