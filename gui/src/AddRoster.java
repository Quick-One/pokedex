import javax.swing.*;

public class AddRoster extends JDialog{
    private JTextField tfRosterName;
    private JButton addRosterButton;
    private JButton cancelButton;
    private JPanel addRosterPanel;

    public AddRoster(JFrame parent) {
        super(parent);
        setTitle("Add Roster");
        setContentPane(addRosterPanel);
        pack();
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setMinimumSize(addRosterPanel.getMinimumSize());
        setModal(true);
        setLocationRelativeTo(parent);

        addRosterButton.addActionListener(e -> {
            RosterQuery roster = addRoster();
            if (roster != null) {
                dispose();
            }
        });

        cancelButton.addActionListener(e -> {
            dispose();
        });

        setVisible(true);
    }

    private RosterQuery addRoster() {
        String rosterName = tfRosterName.getText();
        if (rosterName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields!");
        }

        return null;
    }
}
