import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteRoster extends JDialog{
    private JComboBox<RosterQuery> cbxRosters;
    private JButton deleteButton;
    private JButton cancelButton;
    private JPanel DeleteRosterPanel;

    public DeleteRoster(JFrame parent){
        super(parent);
        setModal(true);
        setTitle("Delete Roster");
        setContentPane(DeleteRosterPanel);
        setSize(400, 200);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);

        RosterConnecterDB connector = new RosterConnecterDB();
        RosterQuery[] allRosters = connector.getAllRosters();
        cbxRosters.addItem(null);
        for (RosterQuery roster : allRosters){
            cbxRosters.addItem(roster);
        }

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RosterQuery selectedRoster = (RosterQuery) cbxRosters.getSelectedItem();
                if (selectedRoster == null) {
                    JOptionPane.showMessageDialog(DeleteRoster.this, "Please select a roster to delete");
                }
                else {
                    int confirm = JOptionPane.showConfirmDialog(DeleteRoster.this, "Are you sure you want to delete this roster?");
                    if (confirm != JOptionPane.YES_OPTION) {
                        return;
                    }
                    connector.deleteRoster(selectedRoster);
                    dispose();
                }
            }
        });
        setVisible(true);
    }

}
