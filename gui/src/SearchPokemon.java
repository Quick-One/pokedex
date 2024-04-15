import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SearchPokemon extends JDialog{
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

    public SearchPokemon(JFrame parent) {
        super(parent);
        setTitle("Search Pokemon");
        setContentPane(MainPanel);
        setSize(900, 500);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);
        
        PokemonConnectorDB connector = new PokemonConnectorDB();
        cbxType.addItem(null);
        for (String type : connector.getAllTypes()){
            cbxType.addItem(type);
        }

        cbxGeneration.addItem(null);
        for (String generation : connector.getAllGenerations()){
            cbxGeneration.addItem(generation);
        }

        listQueryResult.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                PokemonQuery selectedValue = listQueryResult.getSelectedValue();
                if (selectedValue == null) return;
//                Pokemon pokemon = connector.getPokemonById(selectedValue.id);
            }
        });

        btCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
        btSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PokemonQuery[] res = connector.searchPokemon(tfName.getText(), (String) cbxType.getSelectedItem(), (String) cbxGeneration.getSelectedItem());
                listQueryResult.setListData(res);
            }
        });
        listQueryResult.addMouseListener(new MouseAdapter() {
        });
    }

    public static void main(String[] args) {
        new SearchPokemon(null);
    }
}
