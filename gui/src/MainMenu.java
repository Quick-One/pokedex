import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends  JFrame{
    private JButton btnSearchPokemon;
    private JButton btnAddRosters;
    private JLabel lblMainHeader;
    private JPanel MainMenuPanel;
    private JLabel lblHeader1;
    private JButton btnUpdateRoster;
    private JButton btnDeleteRoster;
    private JButton btnShowRosters;
    private JLabel lblHeader2;

public MainMenu() {
    setTitle("Main Menu");
    setContentPane(MainMenuPanel);
    pack();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    btnSearchPokemon.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            new SearchPokemon(MainMenu.this);
        }
    });


    setVisible(true);
    btnAddRosters.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            new AddRoster(MainMenu.this);
        }
    });
    btnShowRosters.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            new ShowRoster(MainMenu.this);
        }
    });
}

    public static void main(String[] args) {
        User.getInstance().setFirstName("test");


        new MainMenu();
    }
}
