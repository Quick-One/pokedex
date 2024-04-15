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

    public SearchPokemon() {

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
}
