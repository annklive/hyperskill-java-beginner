package battleship;

import java.util.Scanner;
import java.util.ArrayList;

public class BattleshipGame {
    ArrayList<BattleshipPlayer> players;
    private static final int NUM_PLAYER = 2;

    private static final Scanner scanner = new Scanner(System.in);
    BattleshipGame() {
        players = new ArrayList<>();
        addDefaultPlayers();
    }
    private void addDefaultPlayers() {
        for (int i = 0; i < NUM_PLAYER; i++) {
            BattleshipPlayer player = new BattleshipPlayer("Player " + (i + 1));
            players.add(player);
        }
    }
    public void start() {
        doPlaceShips();
        doGameTurns();
    }

    private void doGameTurns() {
        BattleshipPlayer player1 = players.get(0);
        BattleshipPlayer player2 = players.get(1);
        BattleshipPlayer shooter;
        BattleshipPlayer opponent;

        int counter = 1;
        do {
            if (counter % 2 != 0) {
                shooter = player1;
                opponent = player2;
            } else {
                shooter = player2;
                opponent = player1;
            }
            showGameFields(shooter, opponent);
            System.out.print(shooter.getPlayerName() + ", it's your turn:");
            doShooting(opponent.getBattleshipField());
            if (gameOver()) {
                System.out.println(shooter.getPlayerName() + ", you sank the last ship. You won.\nCongratulations!");
                break;
            }
            counter++;
            System.out.println("Press Enter and pass the move to another player\n...");
            scanner.nextLine();
        } while (true);
    }

    private boolean gameOver() {

        for (BattleshipPlayer player : players) {
            if (! player.hasActiveShips()) {
                return true;
            }
        }
        return false;
    }

    private void doShooting(BattleshipField theField) {
        boolean shootingDone = false;
        do {
            System.out.print("\n\n");
            FieldCoordinate shotLocation = new FieldCoordinate(scanner.nextLine());

            if (!theField.isValidCoordinate(shotLocation)) {
                System.out.println("Error! You entered the wrong coordinates! Try again:\n");
            } else {
                char status = theField.takeAShot(shotLocation);

                if (status == BattleshipField.HIT) {
                    System.out.println("\nYou hit a ship!");
                } else if (status == BattleshipField.MISS) {
                    System.out.println("\nYou missed!");
                } else if (theField.hasActiveShips() && status == BattleshipField.SUNK) {
                    System.out.println("\nYou sank a ship!");
                }
                shootingDone = true;
            }
        } while (!shootingDone);
    }

    private void showGameFields(BattleshipPlayer shooter, BattleshipPlayer opponent) {
        String opponentField = opponent.battleFieldState(true);
        String shooterField = shooter.battleFieldState();
        String fields = "\n" + opponentField +
                "---------------------\n" +
                shooterField;
        System.out.println(fields);
    }

    private void doPlaceShips() {
        for (BattleshipPlayer player : players) {
            System.out.println(player.getPlayerName()  + ", place your ships on the game field\n");
            player.setupBattleField();
            System.out.print("Press Enter and pass the move to another player\n...");
            scanner.nextLine();
        }
    }
}
