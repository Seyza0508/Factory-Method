import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// Unified Pizza class with detailed attributes and methods
abstract class Pizza {
    private List<String> toppings = new ArrayList<>();
    private String cheese;
    private String sauce;

    abstract String getDescription();

    public void bake() {
        System.out.println("Baking " + getDescription());
    }

    public void cut() {
        System.out.println("Cutting " + getDescription());
    }

    public void box() {
        System.out.println("Boxing " + getDescription());
    }

    public void addTopping(String topping) {
        toppings.add(topping);
    }

    public void setCheese(String cheese) {
        this.cheese = cheese;
    }

    public void setSauce(String sauce) {
        this.sauce = sauce;
    }

    protected String getToppings() {
        return String.join(", ", toppings);
    }

    protected String getCheese() {
        return cheese;
    }

    protected String getSauce() {
        return sauce;
    }
}

// Concrete Pizza classes
class PepperoniPizza extends Pizza {
    @Override
    public String getDescription() {
        return "Pepperoni Pizza";
    }
}

class VeggiePizza extends Pizza {
    @Override
    public String getDescription() {
        return "Veggie Pizza";
    }
}

class MargheritaPizza extends Pizza {
    @Override
    public String getDescription() {
        return "Margherita Pizza";
    }
}

class CustomPizza extends Pizza {
    private String description;

    public CustomPizza(String description) {
        this.description = description;
    }

    @Override
    public String getDescription() {
        return description;
    }
}

// PizzaFactory interface
interface PizzaFactory {
    Pizza createPizza();
}

// Concrete PizzaFactory classes
class PepperoniPizzaFactory implements PizzaFactory {
    @Override
    public Pizza createPizza() {
        return new PepperoniPizza();
    }
}

class MargheritaPizzaFactory implements PizzaFactory {
    @Override
    public Pizza createPizza() {
        return new MargheritaPizza();
    }
}

class VeggiePizzaFactory implements PizzaFactory {
    @Override
    public Pizza createPizza() {
        return new VeggiePizza();
    }
}

class CustomPizzaFactory implements PizzaFactory {
    private String description;

    public CustomPizzaFactory(String description) {
        this.description = description;
    }

    @Override
    public Pizza createPizza() {
        return new CustomPizza(description);
    }
}

// Main class with GUI
public class PizzaOrderingSystemGUI2 extends JFrame {
    private JComboBox<String> pizzaTypesComboBox;
    private JButton orderButton;
    private JTextArea orderDetailsTextArea;
    private Queue<Pizza> pizzaQueue;
    private JCheckBox sausageCheckBox;
    private JCheckBox baconCheckBox;
    private JCheckBox chickenCheckBox;

    public PizzaOrderingSystemGUI2() {
        setTitle("Pizza Ordering System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        pizzaTypesComboBox = new JComboBox<>(new String[]{"Select a Pizza", "Pepperoni", "Margherita", "Veggie", "Custom"});
        orderButton = new JButton("Place Order");
        orderDetailsTextArea = new JTextArea(10, 30);
        sausageCheckBox = new JCheckBox("Sausage");
        baconCheckBox = new JCheckBox("Bacon");
        chickenCheckBox = new JCheckBox("Chicken");

        sausageCheckBox.setVisible(false);
        baconCheckBox.setVisible(false);
        chickenCheckBox.setVisible(false);

        pizzaQueue = new LinkedList<>();

        pizzaTypesComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent boxVal) {
                if (boxVal.getStateChange() == ItemEvent.SELECTED) {
                    String selectedPizza = (String) pizzaTypesComboBox.getSelectedItem();
                    if ("Custom".equals(selectedPizza)) {
                        sausageCheckBox.setVisible(true);
                        baconCheckBox.setVisible(true);
                        chickenCheckBox.setVisible(true);
                    } else {
                        sausageCheckBox.setVisible(false);
                        baconCheckBox.setVisible(false);
                        chickenCheckBox.setVisible(false);
                    }
                }
            }
        });

        orderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedPizza = (String) pizzaTypesComboBox.getSelectedItem();
                PizzaFactory pizzaFactory;
                switch (selectedPizza) {
                    case "Pepperoni":
                        pizzaFactory = new PepperoniPizzaFactory();
                        break;
                    case "Margherita":
                        pizzaFactory = new MargheritaPizzaFactory();
                        break;
                    case "Veggie":
                        pizzaFactory = new VeggiePizzaFactory();
                        break;
                    case "Custom":
                        StringBuilder description = new StringBuilder("Custom Pizza with: ");
                        if (sausageCheckBox.isSelected()) description.append("Sausage, ");
                        if (baconCheckBox.isSelected()) description.append("Bacon, ");
                        if (chickenCheckBox.isSelected()) description.append("Chicken, ");
                        if (description.toString().equals("Custom Pizza with: "))
                            description = new StringBuilder("Custom Pizza");
                        else
                            description = new StringBuilder(description.substring(0, description.length() - 2) + ".");
                        pizzaFactory = new CustomPizzaFactory(description.toString());
                        break;
                    default:
                        return; // Do nothing if no valid selection is made
                }

                Pizza pizza = pizzaFactory.createPizza();
                pizzaQueue.add(pizza);
                pizza.bake();
                pizza.cut();
                pizza.box();

                StringBuilder orderDetails = new StringBuilder();
                for (Pizza p : pizzaQueue) {
                    orderDetails.append(p.getDescription()).append("\n");
                }
                orderDetailsTextArea.setText(orderDetails.toString());
            }
        });

        panel.add(new JLabel("Select Pizza Type:"));
        panel.add(pizzaTypesComboBox);
        panel.add(sausageCheckBox);
        panel.add(baconCheckBox);
        panel.add(chickenCheckBox);
        panel.add(orderButton);
        panel.add(new JScrollPane(orderDetailsTextArea));

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PizzaOrderingSystemGUI2());
    }
}
