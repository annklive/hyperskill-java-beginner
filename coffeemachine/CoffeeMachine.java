package coffeemachine;

import java.util.Scanner;


public class CoffeeMachine {
    enum Coffee { ESPRESSO, LATTE, CAPPUCCINO }
    static final int ESPRESSO_WATER_ML_PER_CUP = 250;
    static final int ESPRESSO_MILK_ML_PER_CUP = 0;
    static final int ESPRESSO_BEANS_G_PER_CUP = 16;
    static final int ESPRESSO_PRICE = 4;
    static final int LATTE_WATER_ML_PER_CUP = 350;
    static final int LATTE_MILK_ML_PER_CUP = 75;
    static final int LATTE_BEANS_G_PER_CUP = 20;
    static final int LATTE_PRICE = 7;
    static final int CAPPUCCINO_WATER_ML_PER_CUP = 200;
    static final int CAPPUCCINO_MILK_ML_PER_CUP = 100;
    static final int CAPPUCCINO_BEANS_G_PER_CUP = 12;
    static final int CAPPUCCINO_PRICE = 6;
    private int water;
    private int milk;
    private int beans;
    private int cups;
    private int cash;
    private static final Scanner scanner = new Scanner(System.in);
    
    CoffeeMachine(int water, int milk, int beans, int cups, int cash) {
        this.water = water;
        this.milk = milk;
        this.beans = beans;
        this.cups = cups;
        this.cash = cash;
    }

    public void printState() {
        System.out.println("\nThe coffee machine has:");
        System.out.printf("%d ml of water\n", water);
        System.out.printf("%d ml of milk\n", milk);
        System.out.printf("%d g of coffee beans%n", beans);
        System.out.printf("%d disposable cups%n", cups);
        System.out.printf("$%d of money%n", cash);

    }

    boolean canMakeCoffee(int waterNeeded, int milkNeeded, int beansNeeded) {
        return water >= waterNeeded &&
                milk >= milkNeeded &&
                beans >= beansNeeded &&
                cups >= 1;
    }
    public void makeCoffee(int waterNeeded, int milkNeeded, int beansNeeded, int price) {
        if (canMakeCoffee(waterNeeded, milkNeeded, beansNeeded)) {
            System.out.println("I have enough resources, making you a coffee!");
            water -= waterNeeded;
            milk -= milkNeeded;
            beans -= beansNeeded;
            cups--;
            cash += price;
        } else {
            StringBuilder sb = new StringBuilder();
            if (water < waterNeeded) {
                sb.append("water");
            } else if (milk < milkNeeded) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append("milk");
            } else if (beans < beansNeeded) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append("coffee beans");
            } else if (cups == 0) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append("disposable cups");
            }
            System.out.printf("Sorry, not enough %s!\n", sb.toString());
        }
    }

    public void makeEspresso() {
        makeCoffee(ESPRESSO_WATER_ML_PER_CUP,
                ESPRESSO_MILK_ML_PER_CUP,
                ESPRESSO_BEANS_G_PER_CUP,
                ESPRESSO_PRICE);
    }

    public void makeLatte() {
        makeCoffee(LATTE_WATER_ML_PER_CUP,
                LATTE_MILK_ML_PER_CUP,
                LATTE_BEANS_G_PER_CUP,
                LATTE_PRICE);
    }

    public void makeCappuccino() {
        makeCoffee(CAPPUCCINO_WATER_ML_PER_CUP,
                CAPPUCCINO_MILK_ML_PER_CUP,
                CAPPUCCINO_BEANS_G_PER_CUP,
                CAPPUCCINO_PRICE);
    }

    public void doBuyCoffee() {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        String option = scanner.next();
        if (option.equals("back")) {
            // go back to main
        } else {
            int coffeeType = Integer.parseInt(option);
            Coffee coffee = Coffee.values()[coffeeType - 1];

            switch (coffee) {
                case ESPRESSO -> makeEspresso();
                case LATTE -> makeLatte();
                case CAPPUCCINO -> makeCappuccino();
                default -> {
                }
                // do nothing
            }
        }
    }

    public void doFilling() {
        System.out.println("Write how many ml of water you want to add:");
        int addedWater = scanner.nextInt();
        water += addedWater;

        System.out.println("Write how many ml of milk you want to add:");
        int addedMilk = scanner.nextInt();
        milk += addedMilk;

        System.out.println("Write how many grams of coffee beans you want to add:");
        int addedBeans = scanner.nextInt();
        beans += addedBeans;

        System.out.println("Write how many disposable cups you want to add:");
        int addedCups = scanner.nextInt();
        cups += addedCups;
    }

    public void doTakeMoney() {
        System.out.println("I gave you $" + cash);
        cash = 0;
    }

    public boolean execute(String action) {
        boolean done = false;
        switch (action) {
            case "buy" -> doBuyCoffee();
            case "fill" -> doFilling();
            case "take" -> doTakeMoney();
            case "remaining" -> printState();
            case "exit" -> {
                done = true;
            }
            default -> {
            }
            // do nothing
        }
        return done;
    }
    public static void main(String[] args) {
        CoffeeMachine machine = new CoffeeMachine(400, 540, 120, 9, 550);
        boolean done = false;
        do {
            System.out.println("\nWrite action (buy, fill, take, remaining, exit): ");
            String action = scanner.next();
            done = machine.execute(action);
        } while (!done);
    }
}

