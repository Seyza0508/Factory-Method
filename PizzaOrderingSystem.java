abstract class Pizza{
    abstract void prepare();
    public void bake(){
        System.out.println("Baking the pizza");
    }
    public void cut(){
        System.out.println("Cutting the pizza");
    }
    public void box(){
        System.out.println("Boxing the pizza");
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
public class PizzaOrderingSystem{
    public static void main(String[] args) {
        PizzaFactory pizzaFactory = getPizzaFactory("pepperoni");
        Pizza pizza = pizzaFactory.createPizza();
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
    }
    private static PizzaFactory getPizzaFactory(String type){
        switch (type.toLowerCase()){
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
}