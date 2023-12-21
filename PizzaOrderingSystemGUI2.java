import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

// Unified Pizza class with detailed attributes, methods, and pricing
abstract class Pizza {
    private List<String> toppings = new ArrayList<>();
    private String cheese;
    private String sauce;

    abstract String getDescription();
    abstract double getPrice();

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

    protected String getToppings() {
        return String.join(", ", toppings);
    }

    public void setCheese(String cheese) {
        this.cheese = cheese;
    }

    public void setSauce(String sauce) {
        this.sauce = sauce;
    }

    protected String getCheese() {
        return cheese;
    }

    protected String getSauce() {
        return sauce;
    }
}

// Concrete Pizza classes with pricing
class PepperoniPizza extends Pizza {
    @Override
    public String getDescription() {
        return "Pepperoni Pizza";
    }

    @Override
    public double getPrice() {
        return 12.99;
    }
}

class VeggiePizza extends Pizza {
    @Override
    public String getDescription() {
        return "Veggie Pizza";
    }

    @Override
    public double getPrice() {
        return 11.99;
    }
}

class MargheritaPizza extends Pizza {
    @Override
    public String getDescription() {
        return "Margherita Pizza";
    }

    @Override
    public double getPrice() {
        return 10.99;
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

    @Override
    public double getPrice() {
        double basePrice = 8.99;
        double toppingPrice = 0.50;
        return basePrice + (getToppings().split(", ").length * toppingPrice);
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
        CustomPizza pizza = new CustomPizza(description);
        if (description.contains("Sausage")) pizza.addTopping("Sausage");
        if (description.contains("Bacon")) pizza.addTopping("Bacon");
        if (description.contains("Chicken")) pizza.addTopping("Chicken");
        return pizza;
    }
}

// Main class with GUI
public class PizzaOrderingSystemGUI2 extends JFrame {
    private JComboBox<String> pizzaTypesComboBox;
    private JButton orderButton, clearOrderButton;
    private JTextArea orderDetailsTextArea;
    private Queue<Pizza> pizzaQueue;
    private JCheckBox sausageCheckBox, baconCheckBox, chickenCheckBox;
    private JLabel totalPriceLabel, pizzaImageLabel;

    public PizzaOrderingSystemGUI2() {
        setTitle("Pizza Ordering System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        pizzaTypesComboBox = new JComboBox<>(new String[]{"Select a Pizza", "Pepperoni", "Margherita", "Veggie", "Custom"});
        orderButton = new JButton("Place Order");
        clearOrderButton = new JButton("Clear Order");
        orderDetailsTextArea = new JTextArea(10, 30);
        sausageCheckBox = new JCheckBox("Sausage");
        baconCheckBox = new JCheckBox("Bacon");
        chickenCheckBox = new JCheckBox("Chicken");
        totalPriceLabel = new JLabel("Total Price: $0.00");
        pizzaImageLabel = new JLabel();

        sausageCheckBox.setVisible(false);
        baconCheckBox.setVisible(false);
        chickenCheckBox.setVisible(false);

        pizzaQueue = new LinkedList<>();

        pizzaTypesComboBox.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent boxVal) {
                if (boxVal.getStateChange() == ItemEvent.SELECTED) {
                    String selectedPizza = (String) pizzaTypesComboBox.getSelectedItem();
                    switch (selectedPizza) {
                        case "Margherita":
                            pizzaImageLabel.setIcon(new ImageIcon("marg.jpeg"));
                            break;
                        case "Pepperoni":
                            pizzaImageLabel.setIcon(new ImageIcon("pepperoni.png"));
                            break;
                        case "Veggie":
                            pizzaImageLabel.setIcon(new ImageIcon("veggie.png"));
                            break;
                        default:
                            pizzaImageLabel.setIcon(null);
                            break;
                    }
                    boolean showToppings = "Custom".equals(selectedPizza);
                    sausageCheckBox.setVisible(showToppings);
                    baconCheckBox.setVisible(showToppings);
                    chickenCheckBox.setVisible(showToppings);
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

                clearOrderButton.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        pizzaQueue.clear();
                        orderDetailsTextArea.setText("");
                        totalPriceLabel.setText("Total Price: $0.00");
                        pizzaImageLabel.setIcon(null);
                    }
                });

                Pizza pizza = pizzaFactory.createPizza();
                pizzaQueue.add(pizza);
                pizza.bake();
                pizza.cut();
                pizza.box();

                double totalPrice = 0.0;
                StringBuilder orderDetails = new StringBuilder();
                for (Pizza p : pizzaQueue) {
                    orderDetails.append(p.getDescription()).append("\n");
                    totalPrice += p.getPrice();
                }
                orderDetailsTextArea.setText(orderDetails.toString());
                totalPriceLabel.setText("Total Price: $" + String.format("%.2f", totalPrice));
            }
        });

        panel.add(new JLabel("Select Pizza Type:"));
        panel.add(pizzaTypesComboBox);
        panel.add(sausageCheckBox);
        panel.add(baconCheckBox);
        panel.add(chickenCheckBox);
        panel.add(orderButton);
        panel.add(clearOrderButton);
        panel.add(new JScrollPane(orderDetailsTextArea));
        panel.add(totalPriceLabel);
        panel.add(pizzaImageLabel);


        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PizzaOrderingSystemGUI2());
    }
}
