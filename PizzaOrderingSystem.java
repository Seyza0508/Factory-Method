import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
abstract class Pizza {
    private List<String> toppings = new ArrayList<>();
    private String cheese;
    private String sauce;

    // Existing methods
    abstract void prepare();
    public void bake() {
        System.out.println("Baking the pizza");
    }
    public void cut() {
        System.out.println("Cutting the pizza");
    }
    public void box() {
        System.out.println("Boxing the pizza");
    }

    // New methods for custom pizza
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

class MargheritaPizza extends Pizza{
    @Override
    public void prepare(){
        System.out.println("Preparing Margherita Pizza");
    }
}
class PepperoniPizza extends Pizza{
    @Override
    public void prepare(){
        System.out.println("Preparing Pepperoni Pizza");
    }
}
class VeggiePizza extends Pizza{
    @Override
    public void prepare(){
        System.out.println("Preparing Veggie Pizza");
    }
}
class CustomPizza extends Pizza {
    @Override
    public void prepare() {
        System.out.println("Preparing Custom Pizza with " + getCheese() + " cheese, " + getSauce() + " sauce, and toppings: " + getToppings());
    }
}
interface PizzaFactory{
    Pizza createPizza();
}
class MargheritaPizzaFactory implements PizzaFactory{
    @Override
    public Pizza createPizza(){
        return new MargheritaPizza();
    }
}
class PepperoniPizzaFactory implements PizzaFactory{
    @Override
    public Pizza createPizza(){
        return new PepperoniPizza();
    }
}
class VeggiePizzaFactory implements PizzaFactory{
    @Override
    public Pizza createPizza(){
        return new VeggiePizza();
    }
}
class CustomPizzaFactory implements PizzaFactory {
    private String cheese;
    private String sauce;
    private List<String> toppings;

    public CustomPizzaFactory(String cheese, String sauce, List<String> toppings) {
        this.cheese = cheese;
        this.sauce = sauce;
        this.toppings = toppings;
    }

    @Override
    public Pizza createPizza() {
        CustomPizza pizza = new CustomPizza();
        pizza.setCheese(cheese);
        pizza.setSauce(sauce);
        toppings.forEach(pizza::addTopping);
        return pizza;
    }
}
public class PizzaOrderingSystem {
    public static void main(String[] args) {
        // Example of ordering a predefined pizza
        PizzaFactory pepperoniPizzaFactory = getPizzaFactory("pepperoni");
        Pizza pepperoniPizza = pepperoniPizzaFactory.createPizza();
        processPizzaOrder(pepperoniPizza);

        // Example of ordering a custom pizza
        List<String> toppings = Arrays.asList("Olives", "Pepperoni", "Mushrooms");
        PizzaFactory customPizzaFactory = new CustomPizzaFactory("Mozzarella", "Tomato", toppings);
        Pizza customPizza = customPizzaFactory.createPizza();
        processPizzaOrder(customPizza);
    }

    private static PizzaFactory getPizzaFactory(String type) {
        switch (type.toLowerCase()) {
            case "margherita":
                return new MargheritaPizzaFactory();
            case "pepperoni":
                return new PepperoniPizzaFactory();
            case "veggie":
                return new VeggiePizzaFactory();
            default:
                throw new IllegalArgumentException("Unknown pizza type: " + type);
        }
    }

    private static void processPizzaOrder(Pizza pizza) {
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
    }
}