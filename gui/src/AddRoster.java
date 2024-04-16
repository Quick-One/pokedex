import javax.swing.*;

public class AddRoster extends JDialog {
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
            Boolean additionSucc = addRoster();
            if (additionSucc) {
                dispose();
            }
        });

        cancelButton.addActionListener(e -> {
            dispose();
        });

        setVisible(true);
    }

    private Boolean addRoster() {
        String rosterName = tfRosterName.getText();
        if (rosterName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields!");
            return false;
        }

        RosterConnecterDB connector = new RosterConnecterDB();
        Boolean additionSucc = connector.addRoster(rosterName);
        if (additionSucc) {
            JOptionPane.showMessageDialog(null, "Roster added successfully!");
        } else {
            JOptionPane.showMessageDialog(null, "Failed to add roster!");
        }
        return additionSucc;
    }
}
