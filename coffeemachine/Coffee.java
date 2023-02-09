package coffeemachine;

public enum Coffee {
    ESPRESSO(250, 0, 16, 4),
    LATTE(350, 75, 20, 7),
    CAPPUCCINO(200, 100, 12, 6);

    private final int water_ml;
    private final int milk_ml;
    private final int beans_g;
    private final int price;

    Coffee(int water_ml, int milk_ml, int beans_g, int price) {
        this.water_ml = water_ml;
        this.milk_ml = milk_ml;
        this.beans_g = beans_g;
        this.price = price;
    }

    int waterMilliLitrePerCup() { return water_ml; }
    int milkMilliLitrePerCup() { return milk_ml; }
    int beansGramPerCup() { return beans_g; }
    int pricePerCup() { return price; }
}
