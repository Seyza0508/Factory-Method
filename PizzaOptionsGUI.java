import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
// ActionEvent interacts with ActionLister to record the button being pressed //
// this will allow us to change the price and everything //
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class PizzaOptionsGUI extends JFrame implements ActionListener {
    // window attributes //
    private final int WINDOW_HEIGHT = 500;
    private final int WINDOW_WIDTH = 500;

    // combo box creates the dropdown for different pizza types //
    private JComboBox<String> pizzaTypes;
    private JLabel toppingsLabel;

    // topping checkboxes //
    private JCheckBox chickenCheckbox;
    private JCheckBox mushroomsCheckbox;
    private JCheckBox baconCheckbox;
    private JCheckBox sausageCheckBox;

    // Images //
    private JLabel pizzaImageLabel;
    private JLabel pizzaImage;
    

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

        // adds pizza image //
        pizzaImage = new JLabel();
        add(pizzaImage); 

        // toppings with their labels being created from JLabel //
        toppingsLabel = new JLabel("Toppings:");
        add(toppingsLabel);

        chickenCheckbox = new JCheckBox("Chicken");
        add(chickenCheckbox);

        sausageCheckBox = new JCheckBox("Sausage");
        add(sausageCheckBox);

        baconCheckbox = new JCheckBox("Bacon");
        add(baconCheckbox);

        mushroomsCheckbox = new JCheckBox("Mushrooms");
        add(mushroomsCheckbox);

        // we can add more as needed //

         // Image label setup
         pizzaImageLabel = new JLabel();
         add(pizzaImageLabel);

        setVisible(true);  
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == pizzaTypes) {
            String selectedPizza = (String) pizzaTypes.getSelectedItem();
            // this alters the visibility based on which pizza type is select in the combo box //

            // put boolean of isChecked to use for pricing //

            if (selectedPizza.equals("Custom")) {
                toppingsLabel.setVisible(true);
                chickenCheckbox.setVisible(true);
                mushroomsCheckbox.setVisible(true);
                baconCheckbox.setVisible(true);
                sausageCheckBox.setVisible(true);
                pizzaImage.setVisible(false);
            } else if(selectedPizza.equals("Margherita")){
                toppingsLabel.setVisible(true);
                chickenCheckbox.setVisible(true);
                mushroomsCheckbox.setVisible(true);
                baconCheckbox.setVisible(true);
                sausageCheckBox.setVisible(true);
                // Load the image
                ImageIcon icon = new ImageIcon("marg.jpeg");
                Image image = icon.getImage();
                // Resize the image to 200x200
                Image resizedImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                // Set the resized image as an icon for the label
                pizzaImage.setIcon(new ImageIcon(resizedImage));
                pizzaImage.setVisible(true);
            }
            else if(selectedPizza.equals("Pepperoni")){
                toppingsLabel.setVisible(true);
                chickenCheckbox.setVisible(true);
                mushroomsCheckbox.setVisible(true);
                baconCheckbox.setVisible(true);
                sausageCheckBox.setVisible(true);
                // Load the image
                ImageIcon icon = new ImageIcon("pepperoni.png");
                Image image = icon.getImage();
                // Resize the image to 200x200
                Image resizedImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                // Set the resized image as an icon for the label
                pizzaImage.setIcon(new ImageIcon(resizedImage));
                pizzaImage.setVisible(true);
            }
            else if(selectedPizza.equals("Veggie")){
                toppingsLabel.setVisible(true);
                chickenCheckbox.setVisible(true);
                mushroomsCheckbox.setVisible(true);
                baconCheckbox.setVisible(true);
                sausageCheckBox.setVisible(true);
              // Load the image
                ImageIcon icon = new ImageIcon("veggie.png");
                Image image = icon.getImage();
                // Resize the image to 200x200
                Image resizedImage = image.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                // Set the resized image as an icon for the label
                pizzaImage.setIcon(new ImageIcon(resizedImage));
                pizzaImage.setVisible(true);
            }
            else{
                toppingsLabel.setVisible(false);
                chickenCheckbox.setVisible(false);
                mushroomsCheckbox.setVisible(false);
                baconCheckbox.setVisible(false);
                sausageCheckBox.setVisible(false);
                pizzaImage.setVisible(false);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PizzaOptionsGUI());
        
    }
}
