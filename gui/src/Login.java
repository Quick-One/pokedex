import javax.swing.*;

public class Login extends JDialog {
    private JPanel LoginPanel;
    private JTextField tfUsername;
    private JTextField tfPassword;
    private JButton btnLogin;
    private JButton btnCancel;
    private JLabel LoginHeader;
    private JLabel Username;

    public Login(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(LoginPanel);
        pack();
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setMinimumSize(LoginPanel.getMinimumSize());
        setModal(true);
        setLocationRelativeTo(parent);

        btnLogin.addActionListener(e -> {
            login();
            if (User.getInstance().isLoggedIn) {
                dispose();
            }
        });

        btnCancel.addActionListener(e -> {
            dispose();
        });

        setVisible(true);
    }

    private void login() {
        String username = tfUsername.getText();
        String password = tfPassword.getText();
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields!");
        }

        boolean loginSucc = AuthConnectorDB.checkLogin(username, password);
        if (!loginSucc) {
            JOptionPane.showMessageDialog(null, "Invalid username or password!");
        } else {
            JOptionPane.showMessageDialog(null, "Login successful!");
        }
    }
}
