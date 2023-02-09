package coffeemachine;

public class CoffeeMachine {
    public static final String SUCCESS = "OK";
    private int water;
    private int milk;
    private int beans;
    private int cups;
    private int cash;

    CoffeeMachine(int water, int milk, int beans, int cups, int cash) {
        this.water = water;
        this.milk = milk;
        this.beans = beans;
        this.cups = cups;
        this.cash = cash;
    }

    public void fillWater(int addedWater) {
        water += addedWater;
    }

    public void fillMilk(int addedMilk) {
        milk += addedMilk;
    }

    public void fillBeans(int addedBeans) {
        beans += addedBeans;
    }

    public void fillCups(int addedCups) {
        cups += addedCups;
    }

    public void takeCash() {
        cash = 0;
    }

    public int getWater() {
        return water;
    }

    public int getMilk() {
        return milk;
    }

    public int getBeans() {
        return beans;
    }

    public int getCups() {
        return cups;
    }

    public int getCash() {
        return cash;
    }

    public String makeCoffee(Coffee coffee) {

        int waterNeeded = coffee.waterMilliLitrePerCup();
        int milkNeeded = coffee.milkMilliLitrePerCup();
        int beansNeeded = coffee.beansGramPerCup();
        int price = coffee.pricePerCup();

        if (canMakeCoffee(coffee)) {
            water -= waterNeeded;
            milk -= milkNeeded;
            beans -= beansNeeded;
            cups--;
            cash += price;
            return SUCCESS;
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
            return sb.toString();
        }
    }

    public boolean canMakeCoffee(Coffee coffee) {
        int waterNeeded = coffee.waterMilliLitrePerCup();
        int milkNeeded = coffee.milkMilliLitrePerCup();
        int beansNeeded = coffee.beansGramPerCup();

        return water >= waterNeeded &&
                milk >= milkNeeded &&
                beans >= beansNeeded &&
                cups >= 1;
    }

    private boolean canMakeCoffee(int waterNeeded, int milkNeeded, int beansNeeded) {
        return water >= waterNeeded &&
                milk >= milkNeeded &&
                beans >= beansNeeded &&
                cups >= 1;
    }

}

