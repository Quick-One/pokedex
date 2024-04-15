import javax.swing.*;

public class Login extends JDialog{
    private JPanel LoginPanel;
    private JTextField tfUsername;
    private JTextField tfPassword;
    private JButton btnLogin;
    private JButton btnCancel;
    private JLabel LoginHeader;
    private JLabel Username;
    User user;

    public Login(JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(LoginPanel);
        pack();
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setModal(true);
        setLocationRelativeTo(parent);

        btnLogin.addActionListener(e -> {
            login();
        });

        btnCancel.addActionListener(e -> {
            dispose();
        });

        setVisible(true);
    }

    private void login() {
        // check if fields are empty
        if (tfUsername.getText().isEmpty() || tfPassword.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields!");
            return;
        }


        String username = tfUsername.getText();
        String password = tfPassword.getText();
        // TODO: check for valid username and password
        int userId = 1; // Some random value for now
        user = new User(username, userId);
        JOptionPane.showMessageDialog(null, "Login successful!");
    }
}
