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

    public ShowRoster() {
        setTitle("Show Roster");
        setContentPane(SelectRosterPanel);
        setSize(500, 500);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setMinimumSize(SelectRosterPanel.getMinimumSize());
        setModal(true);

        // empty the default InfoTree
        InfoTree.setModel(null);

        RosterConnecterDB connector = new RosterConnecterDB();
        listRosters.setListData(connector.getAllRosters());
        int NumberOfRosters = connector.getAllRosters().length;
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
                Roster roster = connector.getRosterById(selectedRoster.id);
                displayRosterInfo(roster);
            }
        });
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
