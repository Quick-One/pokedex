import javax.swing.*;

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

        setVisible(true);
    }

    public static void main(String[] args) {
        new MainMenu();
    }
}
