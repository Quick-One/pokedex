import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateRoster extends JDialog {
    private JPanel UpdateRosterPanel;
    private JTextField tfNewName;
    private JComboBox<RosterQuery> cbxRoster;
    private JButton cancelButton;
    private JButton updateButton;

    public UpdateRoster(JFrame parent) {
        super(parent);
        setModal(true);
        setTitle("Update Roster");
        setContentPane(UpdateRosterPanel);
        setSize(400, 200);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);

        RosterConnecterDB connector = new RosterConnecterDB();
        RosterQuery[] allRosters = connector.getAllRosters();
        cbxRoster.addItem(null);
        for (RosterQuery roster : allRosters) {
            cbxRoster.addItem(roster);
        }

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RosterQuery selectedRoster = (RosterQuery) cbxRoster.getSelectedItem();
                if (selectedRoster == null) {
                    JOptionPane.showMessageDialog(UpdateRoster.this, "Please select a roster to update");
                    return;
                }
                String newName = tfNewName.getText();
                if (newName.isEmpty()) {
                    JOptionPane.showMessageDialog(UpdateRoster.this, "Please enter a new name for the roster");
                    return;
                }
                connector.updateRosterName(selectedRoster, newName);
                dispose();
            }
        });

        setVisible(true);
    }

}
