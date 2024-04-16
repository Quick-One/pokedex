import javax.swing.*;
import java.util.function.Function;

public class MoveInfo extends JDialog{
    private JPanel MoveInfoPanel;
    private JTable tableMoveInfo;

    public MoveInfo(JFrame parent, Move move){
        super(parent);
        setModal(true);
        setTitle("Move Info");
        setContentPane(MoveInfoPanel);
        setSize(400, 300);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);
        populateTable(move);
        setVisible(true);
    }

    private void createUIComponents() {
        setupTable();
    }

    private void setupTable(){
        String[] columnNames = {"Attribute", "Value"};
        String[][] data = {
                {"Name", ""},
                {"Move ID", ""},
                {"Type", ""},
                {"Power", ""},
                {"PP", ""},
                {"Accuracy", ""},
                {"Priority", ""},
                {"Damage Class", ""}
        };
        tableMoveInfo = new JTable(data, columnNames);
    }

    private void populateTable(Move move){
        Function<Integer, String> IntegerToString = Object::toString;
        tableMoveInfo.setValueAt(move.name, 0, 1);
        tableMoveInfo.setValueAt(IntegerToString.apply(move.id), 1, 1);
        tableMoveInfo.setValueAt(move.type, 2, 1);
        tableMoveInfo.setValueAt(IntegerToString.apply(move.power), 3, 1);
        tableMoveInfo.setValueAt(IntegerToString.apply(move.pp), 4, 1);
        tableMoveInfo.setValueAt(IntegerToString.apply(move.accuracy), 5, 1);
        tableMoveInfo.setValueAt(IntegerToString.apply(move.priority), 6, 1);
        tableMoveInfo.setValueAt(move.damageClass, 7, 1);
    }
}
