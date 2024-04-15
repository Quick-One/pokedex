import javax.swing.*;

public class Login extends JDialog{
    private JPanel LoginPanel;
    private JTextField tfUsername;
    private JTextField tfPassword;
    private JButton btnLogin;
    private JButton btnCancel;
    private JLabel LoginHeader;
    private JLabel Username;
    User user = null;

    public Login(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(LoginPanel);
        pack();
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setModal(true);
        setLocationRelativeTo(parent);

        btnLogin.addActionListener(e -> {
            user = login();
            if (user != null) {
                dispose();
            }
        });

        btnCancel.addActionListener(e -> {
            dispose();
        });

        setVisible(true);
    }

    private User login() {
        User user = null;

        String username = tfUsername.getText();
        String password = tfPassword.getText();
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields!");
            return user;
        }

        user = DatabaseConnector.checkLogin(username, password);
        if (user == null) {
            JOptionPane.showMessageDialog(null, "Invalid username or password!");
        }
        else{
            JOptionPane.showMessageDialog(null, "Login successful!");
        }
        return user;
    }
}
