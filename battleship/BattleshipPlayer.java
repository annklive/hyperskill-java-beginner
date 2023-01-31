package battleship;

import java.util.Scanner;


public class BattleshipPlayer {
    final static String[] prompts = {
            "Enter the coordinates of the Aircraft Carrier (5 cells):\n\n",
            "Enter the coordinates of the Battleship (4 cells):\n\n",
            "Enter the coordinates of the Submarine (3 cells):\n\n",
            "Enter the coordinates of the Cruiser (3 cells):\n\n",
            "Enter the coordinates of the Destroyer (2 cells):\n\n"
    };
    final Ship[] ships = new Ship[] {
            new AircraftCarrier(),
            new Battleship(),
            new Submarine(),
            new Cruiser(),
            new Destroyer()
    };
    private static final Scanner scanner = new Scanner(System.in);
    private BattleshipField theField;

    private String playerName;

    BattleshipPlayer(String playerName) {
        this.playerName = playerName;
    }
    public void setupBattleField() {
        theField = new BattleshipField();
        System.out.println(theField.fieldState());

        for (int i = 0; i < ships.length; i++) {
            System.out.print(prompts[i]);
            int spaceNeeded;
            FieldCoordinate startCoordinate, endCoordinate;
            boolean shipPlacementOK = false;
            do {
                startCoordinate = new FieldCoordinate(scanner.next());
                endCoordinate = new FieldCoordinate(scanner.next());
                spaceNeeded = FieldCoordinate.fillLength(startCoordinate, endCoordinate);
                if (!ships[i].validSpace(spaceNeeded)) {
                    System.out.printf("\nError! Wrong length of the %s! Try again:\n\n",
                            ships[i].getType());
                } else if (!theField.validShipPlacement(startCoordinate, endCoordinate)) {
                    System.out.print("\nError! Wrong ship location! Try again:\n\n");
                } else if (theField.isTooClose(startCoordinate, endCoordinate)) {
                    System.out.print("\nError! You placed it too close to another one. Try again:\n\n");
                } else {
                    shipPlacementOK = true;
                }
            } while (!shipPlacementOK);
            theField.placeShip(ships[i], startCoordinate, endCoordinate);
            System.out.println();
            System.out.println(theField.fieldState());
        }
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getActiveShips() {
        return theField.getActiveShips();
    }
    public boolean hasActiveShips() {
        return theField.hasActiveShips();
    }
    public BattleshipField getBattleshipField() {
        return theField;
    }
    public String battleFieldState() {
        return battleFieldState(false);
    }
    public String battleFieldState(boolean coverWithFog) {
        return theField.fieldState(coverWithFog);
    }
    public void singlePlayerGame(BattleshipField theField) {
        System.out.println("\nThe game starts!\n");
        System.out.println(theField.fieldState(true));
        System.out.print("Take a shot!");
        while (theField.hasActiveShips()) {
            boolean shootingDone = false;
            do {
                System.out.print("\n\n> ");
                FieldCoordinate shotLocation = new FieldCoordinate(scanner.next());
                if (!theField.isValidCoordinate(shotLocation)) {
                    System.out.println("Error! You entered the wrong coordinates! Try again:\n");
                } else {
                    char status = theField.takeAShot(shotLocation);

                    System.out.println(theField.fieldState(true));

                    if (status == BattleshipField.HIT) {
                        System.out.print("You hit a ship! Try again:");
                    } else if (status == BattleshipField.MISS) {
                        System.out.print("You missed! Try again:");
                    } else if (theField.hasActiveShips() && status == BattleshipField.SUNK) {
                        System.out.print("You sank a ship! Specify a new target:");
                    }

                    shootingDone = true;
                }
            } while (!shootingDone);
        }
        System.out.println("You sank the last ship. You won.\nCongratulations!");
    }

}
