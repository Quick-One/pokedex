import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenu extends JFrame {
    private JButton btnSearchPokemon;
    private JButton btnAddRosters;
    private JLabel lblMainHeader;
    private JPanel MainMenuPanel;
    private JButton btnUpdateRoster;
    private JButton btnDeleteRoster;
    private JButton btnShowRosters;
    private JButton logoutButton;

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

        btnDeleteRoster.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DeleteRoster(MainMenu.this);
            }
        });
        btnUpdateRoster.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new UpdateRoster(MainMenu.this);
            }
        });
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                User.getInstance().setIsLoggedIn(false);
                User.getInstance().setUserId(0);
                User.getInstance().setUsername("");
                User.getInstance().setFirstName("");
                User.getInstance().setLastName("");
                new AuthMenu();
                dispose();
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        User.getInstance().setFirstName("admin");
        User.getInstance().setLastName("admin");
        User.getInstance().setUsername("admin");
        User.getInstance().setIsLoggedIn(true);
        User.getInstance().setUserId(5);
        new MainMenu();
    }
}
