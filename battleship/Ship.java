package battleship;

public class Ship {
    private final int cells;
    private final String type;

    private int remainingCells;

    Ship(int cells, String type) {
        this.cells = cells;
        this.remainingCells = cells;
        this.type = type;
    }

    public String getType() {
        return type;
    }
    public int getCells() {
        return cells;
    }
    public boolean validSpace(int space) {
        return space == cells;
    }

    public boolean alreadySunk() {
        return remainingCells == 0;
    }

    public void wasHit() {
        remainingCells--;
    }
}

class AircraftCarrier extends Ship {
    final static int CELLS = 5;
    final static String TYPE = "Aircraft Carrier";
    AircraftCarrier() {
        super(CELLS, TYPE);
    }
}

class Battleship extends Ship {
    final static int CELLS = 4;
    final static String TYPE = "Battleship";
    Battleship() {
        super(CELLS, TYPE);
    }

}

class Submarine extends Ship {
    final static int CELLS = 3;
    final static String TYPE = "Submarine";
    Submarine() {
        super(CELLS, TYPE);
    }
}

class Cruiser extends Ship {
    final static int CELLS = 3;
    final static String TYPE = "Cruiser";
    Cruiser() {
        super(CELLS, TYPE);
    }
}

class Destroyer extends Ship {
    final static int CELLS = 2;
    final static String TYPE = "Destroyer";
    Destroyer() {
        super(CELLS, TYPE);
    }
}
