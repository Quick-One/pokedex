import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ContainerAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SearchPokemon {
    private JPanel MainPanel;
    private JTextField tfName;
    private JTextField tfType;
    private JTextField tfGeneration;
    private JButton btSearch;
    private JButton btCancel;
    private JPanel SearchCol;
    private JLabel lblName;
    private JLabel lblType;
    private JLabel lblGeneration;
    private JPanel QueryCol;
    private JPanel ResultCol;
    private JList list1;
    private JComboBox comboBox1;
    private PokemonConnectorDB connector;

    public SearchPokemon() {
        // print the items of the list list1
//        var x = list1.getModel();
//        for (int i = 0; i < x.getSize(); i++) {
//            System.out.println(x.getElementAt(i));
//        }
        String[] types = {"Normal", "Fire", "Water", "Electric", "Grass", "Ice", "Fighting", "Poison", "Ground", "Flying", "Psychic", "Bug", "Rock", "Ghost", "Dragon", "Dark", "Steel", "Fairy"};
        DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>(types);
        comboBox1.setModel(model);



        list1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println(list1.getSelectedValue());
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Search Pokemon");
        frame.setContentPane(new SearchPokemon().MainPanel);
        // increase the size of the frame
        frame.setSize(1000, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
        // print the item which is clicked in the list list1



        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
