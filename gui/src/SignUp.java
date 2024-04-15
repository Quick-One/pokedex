import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUp extends JDialog {
    private JTextField tfUsername;
    private JPasswordField pfPassword;
    private JPasswordField pfConfirmPassword;
    private JButton btnRegister;
    private JButton btnCancel;
    private JPanel SignUpPanel;
    private JTextField tfUserName;

    public SignUp(JFrame parent) {
        super(parent);
        setTitle("Sign Up");
        setContentPane(SignUpPanel);
        pack();
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setMinimumSize(SignUpPanel.getMinimumSize());
        setModal(true);
        setLocationRelativeTo(parent);

        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                registerUser();
            }
        });

        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    private void registerUser() {
        String username = tfUserName.getText();
        String password = new String(pfPassword.getPassword());
        String confirmPassword = new String(pfConfirmPassword.getPassword());

        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill in all fields!");
        } else if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(null, "Passwords do not match!");
        } else {
            boolean userAddStatus = DatabaseConnector.checkSignUp(username, password);
            if (!userAddStatus) {
                JOptionPane.showMessageDialog(null, "Username already exists!");
            }
            else{
                JOptionPane.showMessageDialog(null, "Registration successful!");
                dispose();
            }
        }
    }

    public static void main(String[] args) {
        new SignUp(null);
    }
}
