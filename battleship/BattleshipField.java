package battleship;

import java.util.Scanner;
import java.util.ArrayList;


public class BattleshipField {
    private char[][] field;
    private final ArrayList<Ship> ships;
    private static final int FIELD_ROW = 10;
    private static final int FIELD_COLUMN = 10;
    private static final char FOG = '~';
    private static final char SHIP = 'O';
    public static final char HIT = 'X';
    public static final char MISS = 'M';
    public static final char SUNK = 'K';

    private static final char FIRST_SHIP_ID = 'a';

    private int activeShips;

    BattleshipField() {
        initField();
        ships = new ArrayList<>();
    }
    private void initField() {
        field = new char[FIELD_ROW][FIELD_COLUMN];
        for (int i = 0; i < FIELD_ROW; i++) {
            for (int j = 0; j < FIELD_COLUMN; j++) {
                field[i][j] = FOG;
            }
        }
    }
    private boolean isShip(char symbol) {
        return symbol != MISS && symbol != FOG;
    }
    public String fieldState() {
        return fieldState(false);
    }

    public String fieldRawState() {
        StringBuilder sb = new StringBuilder();
        sb.append("  ");
        for (int c = 0; c < FIELD_COLUMN; c++) {
            sb.append(String.format("%s ", FieldCoordinate.colSymbol(c)));
        }
        sb.append("\n");
        for (int r = 0; r < FIELD_ROW; r++) {
            String row = FieldCoordinate.rowSymbol(r);
            sb.append(row).append(" ");
            for (int c = 0; c < FIELD_COLUMN; c++) {
                sb.append(String.format("%s ", field[r][c]));
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public String fieldState(boolean coverWithFog) {
        StringBuilder sb = new StringBuilder();
        sb.append("  ");
        for (int c = 0; c < FIELD_COLUMN; c++) {
            sb.append(String.format("%s ", FieldCoordinate.colSymbol(c)));
        }
        sb.append("\n");
        for (int r = 0; r < FIELD_ROW; r++) {
            String row = FieldCoordinate.rowSymbol(r);
            sb.append(row).append(" ");
            for (int c = 0; c < FIELD_COLUMN; c++) {
                if (coverWithFog) {
                    if (field[r][c] == HIT || field[r][c] == MISS) {
                        sb.append(String.format("%s ", field[r][c]));
                    } else {
                        sb.append(String.format("%s ", FOG));
                    }
                } else {
                    char symbol = field[r][c];
                    if (field[r][c] != HIT && isShip(symbol)) {
                        symbol = SHIP;
                    }

                    sb.append(String.format("%s ", symbol));
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }
    private char generateShipId() {
        return (char)((int) FIRST_SHIP_ID + (ships.size() - 1));
    }

    private int shipIdToShipIndex(char shipId) {
        return shipId - FIRST_SHIP_ID;
    }

    public char doPlaceShip(Ship ship) {
        ships.add(ship);
        activeShips++;
        return generateShipId();
    }
    public void doSunkShip() {
        activeShips--;
    }

    public int getActiveShips() {
        return activeShips;
    }
    public boolean hasActiveShips() {
        /*
        for (Ship ship : ships) {
            if (!ship.alreadySunk()) {
                return true;
            }
        }
        return false;
        */
        return activeShips > 0;
    }

    public void placeShip(Ship ship, FieldCoordinate startCoordinate, FieldCoordinate endCoordinate) {
        if (FieldCoordinate.isHorizontal(startCoordinate, endCoordinate)) {
            char shipId = doPlaceShip(ship);

            int rowIndex = startCoordinate.getRowIndex();
            int startCol = startCoordinate.getColIndex();
            int endCol = endCoordinate.getColIndex();
            if (startCol > endCol) {
                startCol = endCol;
            }
            endCol = startCol + ship.getCells() - 1;
            for (int i = startCol; i <= endCol; i++) {
                field[rowIndex][i] = shipId;
            }
        } else if (FieldCoordinate.isVertical(startCoordinate, endCoordinate)) {
            char shipId = doPlaceShip(ship);

            int colIndex = startCoordinate.getColIndex();
            int startRow = startCoordinate.getRowIndex();
            int endRow = endCoordinate.getRowIndex();
            if (startRow > endRow) {
                startRow = endRow;
            }
            endRow = startRow + ship.getCells() - 1;
            for (int i = startRow; i <= endRow; i++) {
                field[i][colIndex] = shipId;
            }
        }
    }

    public boolean isCellOccupied(int rowIndex, int colIndex) {
        return field[rowIndex][colIndex] != FOG;
    }
    public boolean isSpaceOccupied(FieldCoordinate p1, FieldCoordinate p2) {
        assert FieldCoordinate.isHorizontal(p1, p2) ||
                FieldCoordinate.isVertical(p1, p2) : "Invalid coordinates";

        if (FieldCoordinate.isHorizontal(p1, p2)) {
            int rowIndex = p1.getRowIndex();
            int startCol = p1.getColIndex();
            int endCol = p2.getColIndex();
            if (startCol > endCol) {
                int temp = startCol;
                startCol = endCol;
                endCol = temp;
            }
            for (int i = startCol; i <= endCol; i++) {
                if (isCellOccupied(rowIndex, i)) {
                    return true;
                }
            }
            return false;
        } else if (FieldCoordinate.isVertical(p1, p2)) {
            int colIndex = p1.getColIndex();
            int startRow = p1.getRowIndex();
            int endRow = p2.getRowIndex();
            if (startRow > endRow) {
                int temp = startRow;
                startRow = endRow;
                endRow = temp;
            }
            for (int i = startRow; i <= endRow; i++) {
                if (isCellOccupied(i, colIndex)) {
                    return true;
                }
            }
            return false;
        }
        return false; // should never reach here
    }

    public boolean emptySurrounding(FieldCoordinate p) {
        int startRow = p.getRowIndex() - 1;
        int endRow = p.getRowIndex() + 1;
        if (startRow < 0) {
            startRow = 0;
        }
        if (endRow >= FIELD_ROW) {
            endRow = FIELD_ROW - 1;
        }
        int startCol = p.getColIndex() - 1;
        int endCol = p.getColIndex() + 1;
        if (startCol < 0) {
            startCol = 0;
        }
        if (endCol >= FIELD_COLUMN) {
            endCol = FIELD_COLUMN - 1;
        }


        for (int i = startRow; i <= endRow; i++) {
            for (int j = startCol; j <= endCol; j++) {
                if (isCellOccupied(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }
    public boolean isTooClose(FieldCoordinate p1, FieldCoordinate p2) {
        return !(emptySurrounding(p1) && emptySurrounding(p2));
    }
    public boolean validShipPlacement(FieldCoordinate p1, FieldCoordinate p2) {
        return (FieldCoordinate.isVertical(p1, p2) || FieldCoordinate.isHorizontal(p1, p2))
                && !isSpaceOccupied(p1, p2);
    }
    public boolean isValidCoordinate(FieldCoordinate location) {
        return location.getRowIndex() >= 0 && location.getRowIndex() < FIELD_ROW
                && location.getColIndex() >= 0 && location.getColIndex() < FIELD_COLUMN;
    }
    public char takeAShot(FieldCoordinate location) {
        int row = location.getRowIndex();
        int col = location.getColIndex();
        if (isShip(field[row][col])) {
            char shipId = field[row][col];
            if (field[row][col] != HIT) { // first hit
                int shipIndex = shipIdToShipIndex(shipId);
                Ship theShip = ships.get(shipIndex);
                theShip.wasHit();
                field[row][col] = HIT;
                if (theShip.alreadySunk()) {
                    doSunkShip();
                    return SUNK;
                } else {
                    return HIT;
                }
            } else {
                return HIT;
            }
        } else {
            field[row][col] = MISS;
            return MISS;
        }
    }
}

class FieldCoordinate {
    private final String coordinate;
    private final int rowIndex;
    private final String rowSymbol;
    private final int colIndex;
    private final String colSymbol;
    FieldCoordinate(String coordinate) {
        this.coordinate = coordinate;
        this.rowSymbol = rowSymbolFromCoordinate(coordinate);
        this.colSymbol = colSymbolFromCoordinate(coordinate);
        this.rowIndex = rowIndex(this.rowSymbol);
        this.colIndex = colIndex(this.colSymbol);
    }

    public String getCoordinate() {
        return coordinate;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public String getRowSymbol() {
        return rowSymbol;
    }

    public int getColIndex() {
        return colIndex;
    }

    public String getColSymbol() {
        return colSymbol;
    }


    public static String rowSymbolFromCoordinate(String coordinate) {
        return coordinate.substring(0, 1);
    }

    public static String colSymbolFromCoordinate(String coordinate) {
        return coordinate.substring(1);
    }
    public static String rowSymbol(int index) {
        return String.format("%s", (char)('A' + index));
    }

    public static int rowIndex(String symbol) {
        return symbol.charAt(0) - 'A';
    }

    public static String colSymbol(int index) {
        return String.format("%d", index+1);
    }
    public static int colIndex(String symbol) {
        return Integer.parseInt(symbol) - 1;
    }

    public static boolean isHorizontal(FieldCoordinate p1, FieldCoordinate p2) {
        return p1.rowIndex == p2.rowIndex;
    }

    public static boolean isVertical(FieldCoordinate p1, FieldCoordinate p2) {
        return p1.colIndex == p2.colIndex;
    }

    public static boolean isDiagonal(FieldCoordinate p1, FieldCoordinate p2) {
        return !(isVertical(p1, p2) || isHorizontal(p1, p2));
    }

    public static int verticalLength(FieldCoordinate p1, FieldCoordinate p2) {
        return Math.abs(p1.rowIndex - p2.rowIndex) + 1;
    }
    public static int horizontalLength(FieldCoordinate p1, FieldCoordinate p2) {
        return Math.abs(p1.colIndex - p2.colIndex) + 1;
    }
    public static int fillLength(FieldCoordinate p1, FieldCoordinate p2) {

        if (isHorizontal(p1, p2)) {
            return horizontalLength(p1, p2);
        } else if (isVertical(p1, p2)) {
            return verticalLength(p1, p2);
        } else {
            int hLen = horizontalLength(p1, p2);
            int vLen = verticalLength(p1, p2);
            return Math.max(hLen, vLen);
        }
    }

}
