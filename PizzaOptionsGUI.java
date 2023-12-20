import javax.swing.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Queue;

// The abstract class //
abstract class Pizza {
    public abstract String getDescription();
}


// These are the concrete classes of the pizza classes //
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
    @Override
    public String getDescription() {
        return "Custom Pizza";
    }
}



// The factory method itself //
interface PizzaFactory {
    Pizza createPizza();
}

// Concrete factories for pizza tpye //
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
    @Override
    public Pizza createPizza() {
        return new CustomPizza();
    }
}


// Constructor //

public class PizzaOrderingSystemGUI extends JFrame {
    private JComboBox<String> pizzaTypesComboBox;
    private JButton orderButton;
    private JTextArea orderDetailsTextArea;
    private Queue<Pizza> pizzaQueue; // Queue to manage pizzas to be made
    // New components for toppings
    private JCheckBox sausageCheckBox;
    private JCheckBox baconCheckBox;
    private JCheckBox chickenCheckBox;
    


    // GUI //

    public PizzaOrderingSystemGUI() {
        // window // 
        setTitle("Pizza Ordering System");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        
        // cdropdown box for the pizza types //
        JPanel panel = new JPanel();
        pizzaTypesComboBox = new JComboBox<>(new String[]{"Pepperoni", "Margherita", "Veggie","Custom"});
        orderButton = new JButton("Place Order");
        orderDetailsTextArea = new JTextArea(10, 30);

         // Initialize topping checkboxes //
         sausageCheckBox = new JCheckBox("Sausage");
         baconCheckBox = new JCheckBox("Bacon");
         chickenCheckBox = new JCheckBox("Chicken");
 
         // Add checkboxes to the panel - I think these might need to be added into the "else if Custom pizza" statement // 
         panel.add(sausageCheckBox);
         panel.add(baconCheckBox);
         panel.add(chickenCheckBox);

        
         // Initialize the ordering queue //
        pizzaQueue = new LinkedList<>(); 

    
        // this acts as the submit button -> takes the action listener to run through the queue // 
        orderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                PizzaFactory pizzaFactory = null;

                // Takes the value of the selected pizza and uses the associated concrete factory //
                // I think we need to add the pizza images + panel down here //
                // I also think the topping buttons might be better to add here //
                String selectedPizza = (String) pizzaTypesComboBox.getSelectedItem();
                if (selectedPizza.equals("Pepperoni")) {
                    pizzaFactory = new PepperoniPizzaFactory();
                } else if (selectedPizza.equals("Margherita")) {
                    pizzaFactory = new MargheritaPizzaFactory();
                } else if (selectedPizza.equals("Veggie")) {
                    pizzaFactory = new VeggiePizzaFactory();
                } else if (selectedPizza.equals("Custom")){
                    pizzaFactory = new CustomPizzaFactory();
                }

        

                // Displays order details  //
                if (pizzaFactory != null) {
                    Pizza pizza = pizzaFactory.createPizza();
                    pizzaQueue.add(pizza);
                    orderDetailsTextArea.setText("Ordered: " + pizza.getDescription());

                    // Displays the pizza being made. We'll need to add the cusotom pizza toppings option//
                    StringBuilder cues = new StringBuilder("Pizzas to be made:\n");
                    for (Pizza p : pizzaQueue) {
                        cues.append("- ").append(p.getDescription()).append("\n");
                    }
                    orderDetailsTextArea.append("\n\n" + cues.toString());
                    
                }
            }
        });

        // adds the panel after pizza is selected // 
        panel.add(new JLabel("Select Pizza Type:"));
        panel.add(pizzaTypesComboBox);
        panel.add(orderButton);
        panel.add(new JLabel("Order Details:"));
        panel.add(new JScrollPane(orderDetailsTextArea));


        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PizzaOrderingSystemGUI());
    }
}
