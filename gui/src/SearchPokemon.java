import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SearchPokemon extends JDialog {
    private JPanel MainPanel;
    private JTextField tfName;
    private JButton btSearch;
    private JButton btCancel;
    private JPanel SearchCol;
    private JLabel lblName;
    private JLabel lblType;
    private JLabel lblGeneration;
    private JPanel QueryCol;
    private JPanel ResultCol;
    private JList<PokemonQuery> listQueryResult;
    private JComboBox<String> cbxGeneration;
    private JComboBox<String> cbxType;
    private JTabbedPane tabbedPane1;
    private JTable tableInfo;
    private JPanel tabInfo;
    private JPanel tabMoves;
    private JList<Move> listMove;
    private JButton addPokemonToRosterButton;

    public SearchPokemon(JFrame parent) {
        super(parent);
        setModal(true);
        setTitle("Search Pokemon");
        setContentPane(MainPanel);
        setSize(900, 500);
//        SearchCol.setMinimumSize(ResultCol.getMinimumSize());
//        QueryCol.setMinimumSize(ResultCol.getMinimumSize());
//        ResultCol.setMinimumSize(ResultCol.getMinimumSize());
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);

        PokemonConnectorDB connector = new PokemonConnectorDB();
        cbxType.addItem(null);
        for (String type : connector.getAllTypes()) {
            cbxType.addItem(type);
        }

        cbxGeneration.addItem(null);
        for (String generation : connector.getAllGenerations()) {
            cbxGeneration.addItem(generation);
        }


        listQueryResult.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                PokemonQuery selectedValue = listQueryResult.getSelectedValue();
                if (selectedValue == null) return;
                Pokemon pokemon = connector.getPokemonById(selectedValue.id);
                listMove.setListData(pokemon.moves);
                populateTable(pokemon);
            }
        });

        btCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        btSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PokemonQuery[] res = connector.searchPokemon(tfName.getText(), (String) cbxType.getSelectedItem(), (String) cbxGeneration.getSelectedItem());
                listQueryResult.setListData(res);
            }
        });

        addPokemonToRosterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PokemonQuery selectedValue = listQueryResult.getSelectedValue();
                if (selectedValue == null) return;
                Pokemon pokemon = connector.getPokemonById(selectedValue.id);
                new AddToRoster(parent, pokemon);
            }
        });
        setVisible(true);
    }

    public static void main(String[] args) {
        new SearchPokemon(null);
    }

    private void createUIComponents() {
        setupTable();
    }

    private void setupTable() {
        String[] columnNames = {"Attribute", "Value"};
        Object[][] data = {
                {"Name", ""},
                {"Pokemon ID", ""},
                {"Generation", ""},
                {"Height", ""},
                {"Weight", ""},
                {"Base Experience", ""},
                {"Color", ""},
                {"Habitat", ""},
                {"Shape", ""},
                {"Types", ""},
                {"Capture Rate", ""},
                {"Is Legendary", ""},
                {"Is Mythical", ""},
                {"Evolves From", ""},
                {"Evolutions", ""}
        };
        tableInfo = new JTable(data, columnNames);
    }

    private void populateTable(Pokemon pokemon) {
        java.util.function.Function<Object, String> nullToNA = (Object value) -> value == null ? "N/A" : value.toString();
        tableInfo.setValueAt(nullToNA.apply(pokemon.name), 0, 1);
        tableInfo.setValueAt(nullToNA.apply(pokemon.id), 1, 1);
        tableInfo.setValueAt(nullToNA.apply(pokemon.generation), 2, 1);
        tableInfo.setValueAt(nullToNA.apply(pokemon.height), 3, 1);
        tableInfo.setValueAt(nullToNA.apply(pokemon.weight), 4, 1);
        tableInfo.setValueAt(nullToNA.apply(pokemon.baseExperience), 5, 1);
        tableInfo.setValueAt(nullToNA.apply(pokemon.color), 6, 1);
        tableInfo.setValueAt(nullToNA.apply(pokemon.habitat), 7, 1);
        tableInfo.setValueAt(nullToNA.apply(pokemon.shape), 8, 1);
        tableInfo.setValueAt(nullToNA.apply(String.join(", ", pokemon.types)), 9, 1);
        tableInfo.setValueAt(nullToNA.apply(pokemon.captureRate), 10, 1);
        tableInfo.setValueAt(nullToNA.apply(pokemon.getIsLegendary()), 11, 1);
        tableInfo.setValueAt(nullToNA.apply(pokemon.getIsMythical()), 12, 1);
        tableInfo.setValueAt(nullToNA.apply(pokemon.evolvesFrom), 13, 1);
        tableInfo.setValueAt(pokemon.getEvolutions(), 14, 1);
    }
}
