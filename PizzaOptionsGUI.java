import javax.swing.*;
import java.awt.*;
// ActionEvent interacts with ActionLister to record the button being pressed //
// this will allow us to change the price and everything //
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PizzaOptionsGUI extends JFrame implements ActionListener {
    // window attributes //
    private final int WINDOW_HEIGHT = 500;
    private final int WINDOW_WIDTH = 500;

    // combo box creates the dropdown for different pizza types //
    private JComboBox<String> pizzaTypes;
    private JLabel toppingsLabel;

    // topping checkboxes //
    private JCheckBox pepperoniCheckbox;
    private JCheckBox mushroomsCheckbox;
    private JCheckBox baconCheckbox;
    private JCheckBox sausageCheckBox;

    // constructor //
    public PizzaOptionsGUI() {
        setTitle("Pizza Ordering System"); // window title //
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
        setSize(WINDOW_HEIGHT,WINDOW_WIDTH); 
        setLayout(new FlowLayout());

        // create pizza types // 
        String[] pizzaTypeOptions = {"Select Pizza Type", "Margherita", "Pepperoni", "Veggie", "Custom"};
        // create instance of the pizza types in the combo box menu //
        pizzaTypes = new JComboBox<>(pizzaTypeOptions);
        pizzaTypes.addActionListener(this);
        add(pizzaTypes);

        // toppings with their labels being created from JLabel //
        toppingsLabel = new JLabel("Toppings:");
        add(toppingsLabel);

        pepperoniCheckbox = new JCheckBox("Pepperoni");
        add(pepperoniCheckbox);

        sausageCheckBox = new JCheckBox("Sausage");
        add(sausageCheckBox);

        baconCheckbox = new JCheckBox("Bacon");
        add(baconCheckbox);

        mushroomsCheckbox = new JCheckBox("Mushrooms");
        add(mushroomsCheckbox);

        // we can add more as needed //

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pizzaTypes) {
            String selectedPizza = (String) pizzaTypes.getSelectedItem();

            // this alters the visibility based on which pizza type is select in the combo box //
            // I'm not sure if we need this entirely // 

            if (selectedPizza.equals("Custom")) {
                toppingsLabel.setVisible(true);
                pepperoniCheckbox.setVisible(true);
                mushroomsCheckbox.setVisible(true);
                baconCheckbox.setVisible(true);
                sausageCheckBox.setVisible(true);
            } else {
                toppingsLabel.setVisible(false);
                pepperoniCheckbox.setVisible(false);
                mushroomsCheckbox.setVisible(false);
                baconCheckbox.setVisible(false);
                sausageCheckBox.setVisible(false);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PizzaOptionsGUI());
    }
}