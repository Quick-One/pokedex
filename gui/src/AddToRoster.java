import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddToRoster extends JDialog{
    private JComboBox<RosterQuery> cbxRoster;
    private JComboBox<Move> cbxMove1;
    private JComboBox<Move> cbxMove2;
    private JComboBox<Move> cbxMove3;
    private JComboBox<Move> cbxMove4;
    private JButton addPokemonButton;
    private JButton cancelButton;
    private JPanel AddToRosterPanel;

    public AddToRoster(JFrame parent, Pokemon pokemon){
        super(parent);
        setModal(true);
        setTitle("Add Pokemon to Roster");
        setContentPane(AddToRosterPanel);
        pack();
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);

        RosterConnecterDB connector = new RosterConnecterDB();
        cbxRoster.addItem(null);
        for (RosterQuery roster : connector.getAllRosters()) {
            cbxRoster.addItem(roster);
        }

        cbxMove1.addItem(null);
        cbxMove2.addItem(null);
        cbxMove3.addItem(null);
        cbxMove4.addItem(null);

        for (Move move : pokemon.moves) {
            cbxMove1.addItem(move);
            cbxMove2.addItem(move);
            cbxMove3.addItem(move);
            cbxMove4.addItem(move);
        }


        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });


        addPokemonButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Boolean addSucc = rosterAdd(pokemon);
                if (addSucc) {
                    JOptionPane.showMessageDialog(AddToRoster.this, "Pokemon added to roster", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose();
                }
            }
        });

        setVisible(true);
    }

    private Boolean rosterAdd(Pokemon pokemon) {
        RosterQuery selectedRoster = (RosterQuery) cbxRoster.getSelectedItem();
        Move move1 = (Move) cbxMove1.getSelectedItem();
        Move move2 = (Move) cbxMove2.getSelectedItem();
        Move move3 = (Move) cbxMove3.getSelectedItem();
        Move move4 = (Move) cbxMove4.getSelectedItem();

        if (selectedRoster == null || move1 == null || move2 == null || move3 == null || move4 == null) {
            JOptionPane.showMessageDialog(AddToRoster.this, "Please select a roster and 4 moves", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // ensure that the moves are unique
        if (move1.equals(move2) || move1.equals(move3) || move1.equals(move4) || move2.equals(move3) || move2.equals(move4) || move3.equals(move4)) {
            JOptionPane.showMessageDialog(AddToRoster.this, "Please select unique moves", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        RosterConnecterDB connector = new RosterConnecterDB();
        Move[] moveArray = {move1, move2, move3, move4};
        int retCode = connector.addPokemonToRoster(selectedRoster, pokemon, moveArray);
        if (retCode == 0) {
            JOptionPane.showMessageDialog(AddToRoster.this, "An error occurred while adding the Pokemon to the roster", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } else if (retCode == 2) {
            JOptionPane.showMessageDialog(AddToRoster.this, "The Pokemon or moves are already in the roster", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        PokedexLogger.addPokemonToRoster(pokemon, selectedRoster);
        return true;
    }
}
