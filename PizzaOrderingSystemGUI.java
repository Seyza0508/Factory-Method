import javax.swing.*;
import javax.swing.plaf.TreeUI;

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
        
        // panel initiation //
        JPanel panel = new JPanel();
        pizzaTypesComboBox = new JComboBox<>(new String[]{"Select a Pizza", "Pepperoni", "Margherita", "Veggie","Custom"});
        orderButton = new JButton("Place Order");
        orderDetailsTextArea = new JTextArea(10, 30);

         // Initialize topping checkboxes //
         sausageCheckBox = new JCheckBox("Sausage");
         baconCheckBox = new JCheckBox("Bacon");
         chickenCheckBox = new JCheckBox("Chicken");

        // Set the visibility to false because we only want them for custom //
        sausageCheckBox.setVisible(false);
        baconCheckBox.setVisible(false);
        chickenCheckBox.setVisible(false);

        
        
 
         // Add checkboxes to the panel - I think these might need to be added into the "else if Custom pizza" statement // 
         panel.add(sausageCheckBox);
         panel.add(baconCheckBox);
         panel.add(chickenCheckBox);

        
         // Initialize the ordering queue //
        pizzaQueue = new LinkedList<>(); 



        // if this isn't initiated before the orderButton action Listener, the toppings will only appear after running the Custom factory //
        // if pizzaTypesComboBox = Custom, set chicken/sausage/bacon checkbox to .setVisible(false) //
        // else, set to .setVisible(true) //

    pizzaTypesComboBox.addItemListener(new ItemListener() {
        public void itemStateChanged(ItemEvent boxVal) {
            if (boxVal.getStateChange() == ItemEvent.SELECTED) {
                String selectedPizza = (String) pizzaTypesComboBox.getSelectedItem();
                // Check if the selected pizza is "Custom" and set checkbox visibility accordingly
                if (selectedPizza.equals("Custom")) {
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
        }
    );



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
                } else if (selectedPizza.equals("Custom")) {
                   

                    // set to visible here -> the only issue is it'll only show up after the method is ran //
                    // I think we need to set an actionListener to the actionPerformed(ActionEvent e) = Custom, then set the visibility accordingly //
        
                    
                    // add booleans for the topping checkbox value //
                    boolean addSausage = sausageCheckBox.isSelected();
                    boolean addBacon = baconCheckBox.isSelected();
                    boolean addChicken = chickenCheckBox.isSelected();
                
                    // Create CustomPizza based on selected toppings
                    StringBuilder customPizzaDescription = new StringBuilder("Custom Pizza with: ");
                
                    if (addSausage) {
                        customPizzaDescription.append("Sausage, ");
                    }
                    if (addBacon) {
                        customPizzaDescription.append("Bacon, ");
                    }
                    if (addChicken) {
                        customPizzaDescription.append("Chicken, ");
                    }
                
                    // If no toppings are selected, set the default description
                    if (!(addSausage || addBacon || addChicken)) {
                        customPizzaDescription = new StringBuilder("Custom Pizza");
                    } 
                    
                    else {
                        customPizzaDescription.delete(customPizzaDescription.length() - 2, customPizzaDescription.length()); // Remove the last comma and space
                        customPizzaDescription.append(".");
                    }

                    // store the description through this instance //
                    final String finalDescription = customPizzaDescription.toString();
                
                    // Create the object of customPizza with the new discription //
                    CustomPizza customPizza = new CustomPizza() {
                        @Override
                        public String getDescription() {
                            return finalDescription; // 
                        }
                    };

                    // Add the custom pizza object (with description) to the order details //
                    pizzaQueue.add(customPizza); 
                    orderDetailsTextArea.setText("Ordered: " + customPizza.getDescription());
                
                 // We need this order detial display otherwise it will erase the queue if we switch out of custom // 
                    StringBuilder cues = new StringBuilder("Pizzas to be made:\n");
                    for (Pizza p : pizzaQueue) {
                        cues.append("- ").append(p.getDescription()).append("\n");
                    }
                    orderDetailsTextArea.append("\n\n" + cues.toString());
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

        // adds the copmponents  // 
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
