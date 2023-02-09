package coffeemachine;

import java.util.Scanner;

public class CoffeeMachineTextUI {
    private static final Scanner scanner = new Scanner(System.in);
    private static CoffeeMachine machine;
    public static void printState() {
        System.out.println("\nThe coffee machine has:");
        System.out.printf("%d ml of water\n", machine.getWater());
        System.out.printf("%d ml of milk\n", machine.getMilk());
        System.out.printf("%d g of coffee beans%n", machine.getBeans());
        System.out.printf("%d disposable cups%n", machine.getCups());
        System.out.printf("$%d of money%n", machine.getCash());

    }
    public static void doBuyCoffee() {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        String option = scanner.next();
        if (!option.equals("back")) {
            int coffeeType = Integer.parseInt(option);
            Coffee coffee = switch (coffeeType) {
                case 1 -> Coffee.ESPRESSO;
                case 2 -> Coffee.LATTE;
                case 3 -> Coffee.CAPPUCCINO;
                default -> null;
            };
            if (coffee != null) {
                String result = machine.makeCoffee(coffee);
                if (result.equals(CoffeeMachine.SUCCESS)) {
                    System.out.println("Your cup of coffee is served!");
                } else {
                    System.out.printf("Sorry, not enough %s!\n", result);
                }
            }
        }
    }
    public static void doFilling() {
        System.out.println("Write how many ml of water you want to add:");
        int addedWater = scanner.nextInt();
        machine.fillWater(addedWater);

        System.out.println("Write how many ml of milk you want to add:");
        int addedMilk = scanner.nextInt();
        machine.fillMilk(addedMilk);

        System.out.println("Write how many grams of coffee beans you want to add:");
        int addedBeans = scanner.nextInt();
        machine.fillBeans(addedBeans);

        System.out.println("Write how many disposable cups you want to add:");
        int addedCups = scanner.nextInt();
        machine.fillCups(addedCups);
    }
    public static void doTakeMoney() {
        System.out.println("I gave you $" + machine.getCash());
        machine.takeCash();
    }
    public static boolean execute(String action) {
        boolean done = false;
        switch (action) {
            case "buy" -> doBuyCoffee();
            case "fill" -> doFilling();
            case "take" -> doTakeMoney();
            case "remaining" -> printState();
            case "exit" -> done = true;
            default -> {
            }
            // do nothing
        }
        return done;
    }
    public static void main(String[] args) {
        machine = new CoffeeMachine(400, 540, 120, 9, 550);
        boolean done;
        do {
            System.out.println("\nWrite action (buy, fill, take, remaining, exit): ");
            String action = scanner.next();
            done = execute(action);
        } while (!done);
    }
}
