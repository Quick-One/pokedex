import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShowRoster extends JDialog {
    private JTextField tfNumberOfRoster;
    private JList<RosterQuery> listRosters;
    private JPanel SelectRosterPanel;
    private JButton closeButton;
    private JTree InfoTree;
    private JPanel SelectRosterCol;
    private JPanel RosterInfoCol;

    public ShowRoster(JFrame parent) {
        setTitle("Show Roster");
        setContentPane(SelectRosterPanel);
        setSize(600, 500);
        RosterInfoCol.setMinimumSize(SelectRosterCol.getMinimumSize());
        SelectRosterCol.setMinimumSize(SelectRosterCol.getMinimumSize());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setMinimumSize(SelectRosterPanel.getMinimumSize());
        setLocationRelativeTo(parent);
        setModal(true);

        InfoTree.setModel(null);

        RosterConnecterDB connector = new RosterConnecterDB();
        RosterQuery[] allRoster = connector.getAllRosters();
        listRosters.setListData(allRoster);

        int NumberOfRosters = allRoster.length;
        tfNumberOfRoster.setText(String.valueOf(NumberOfRosters));

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
                RosterQuery selectedRoster = listRosters.getSelectedValue();
                if (selectedRoster == null) {return;}
                Roster roster = connector.getRosterById(selectedRoster);
                displayRosterInfo(roster);
            }
        });

        setVisible(true);
    }

    private void displayRosterInfo(Roster roster){
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(roster.name);
        for (Pokemon pokemon : roster.pokemon){
            DefaultMutableTreeNode pokemonNode = new DefaultMutableTreeNode(pokemon.name);
            for (Move move : pokemon.moves){
                DefaultMutableTreeNode moveNode = new DefaultMutableTreeNode(move.name);
                pokemonNode.add(moveNode);
            }
            root.add(pokemonNode);
        }
        InfoTree.setModel(new JTree(root).getModel());
    }
}
