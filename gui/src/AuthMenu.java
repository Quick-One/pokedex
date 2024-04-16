import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthMenu extends JFrame {
    private JButton btnLogin;
    private JButton btnSignUp;
    private JLabel WelcomeMsg;
    private JPanel AuthPanel;

    public AuthMenu() {
        setTitle("Authentication Menu");
        setContentPane(AuthPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        btnSignUp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SignUp(AuthMenu.this);
            }
        });

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login(AuthMenu.this);
                if (User.getInstance().isLoggedIn) {
                    dispose();
                    new MainMenu();
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) {
        new AuthMenu();
    }
}
