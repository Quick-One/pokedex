import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SelectRoster extends JDialog {
    private JTextField tfNumberOfRoster;
    private JList<RosterQuery> listRosters;
    private JPanel SelectRosterPanel;
    private JButton closeButton;
    public RosterQuery selectedRoster = null;

    public SelectRoster() {
        setTitle("Select Roster");
        setContentPane(SelectRosterPanel);
        pack();
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setMinimumSize(SelectRosterPanel.getMinimumSize());
        setModal(true);

        RosterConnecterDB connector = new RosterConnecterDB();
        listRosters.setListData(connector.getAllRosters());

        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        listRosters.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                selectedRoster = listRosters.getSelectedValue();
                if (selectedRoster != null) {
                    dispose();
                }
            }
        });
    }
}
