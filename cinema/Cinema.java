package cinema;

import java.util.Scanner;

public class Cinema {
    static final int NUM_SEATS_IN_SMALL_THEATER = 60;
    static final int FRONT_SEAT_PRICE = 10;    
    static final int BACK_SEAT_PRICE = 8;   
    private int numRows = 0;
    private int seatsPerRow = 0;
    private int totalSeats = 0;
    private int totalIncome = 0;
    private int numPurchasedTickets = 0;
    private int currentIncome = 0;
    private double percentPurchased = 0.00;
    private char[][] cinema;

    Cinema(int numRows, int seatsPerRow) {
        this.numRows = numRows;
        this.seatsPerRow = seatsPerRow;    
        resetStatistics();
        initCinema();
    }
    
    void resetStatistics() {
        numPurchasedTickets = 0;
        totalSeats = numRows * seatsPerRow;
        currentIncome = 0;
        totalIncome = calculateTotalIncome();        
    }
    
    void initCinema() {
        cinema = new char[numRows][seatsPerRow];
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < seatsPerRow; j++) {
                cinema[i][j] = 'S';
            }
        }        
    }

    int calculateTotalIncome() {
        int totalIncome;
        
        if (totalSeats <= NUM_SEATS_IN_SMALL_THEATER) {
            totalIncome = totalSeats * FRONT_SEAT_PRICE;
        }
        else {
            int frontSeats = (numRows / 2) * seatsPerRow;
            totalIncome = frontSeats * FRONT_SEAT_PRICE + 
                                (totalSeats - frontSeats) * BACK_SEAT_PRICE;
        }
        return totalIncome;
    }

    void showSeats() {
        System.out.print("\nCinema:\n  ");
        for (int i = 1; i <= seatsPerRow; i++) {
            System.out.printf("%2d", i);
        }
        System.out.println();
        for (int i = 0; i < numRows; i++) {
            System.out.printf("%2d", i+1);
            for (int j = 0; j < seatsPerRow; j++) {
                System.out.printf(" %s", cinema[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }

    int getTicketPrice(int rowNo, int seatNo) {
        int ticketPrice;
        
        if (totalSeats <= NUM_SEATS_IN_SMALL_THEATER) {
            ticketPrice = FRONT_SEAT_PRICE;
        } else {
            int frontRows = (numRows / 2);
            if (rowNo <= frontRows) {
                ticketPrice = FRONT_SEAT_PRICE;
            } else {
                ticketPrice = BACK_SEAT_PRICE;
            }
        }        
        return ticketPrice;
    }

    boolean ticketSold(int rowNo, int seatNo) {
        return cinema[rowNo-1][seatNo-1] == 'B';
    }
    
    public boolean bookSeat(int rowNo, int seatNo) {
        boolean success = false;
        int ticketPrice = getTicketPrice(rowNo, seatNo);
        if (ticketSold(rowNo, seatNo)) {
            System.out.println("That ticket has already been purchased");
        } else {
            cinema[rowNo-1][seatNo-1] = 'B';
            currentIncome += ticketPrice;
            numPurchasedTickets++;        
            percentPurchased = numPurchasedTickets * 100.0 / totalSeats;        
            System.out.printf("\nTicket price: $%d\n", ticketPrice);
            success = true;
        }
        return success;
    }

    boolean validRow(int rowNo) {
        return rowNo >= 1 && rowNo <= numRows;
    }

    boolean validSeat(int seatNo) {
        return seatNo >= 1 && seatNo <= seatsPerRow;
    }

    boolean validSeat(int rowNo, int seatNo) {
        return validRow(rowNo) && validSeat(seatNo);
    }
    
    public void buyTicket(Scanner scanner) {
        boolean success = false;
        do {
            System.out.printf("Enter a row number:\n> ");
            int rowNo = scanner.nextInt();
            System.out.printf("Enter a seat number in that row:\n> ");
            int seatNo = scanner.nextInt();
            if (validSeat(rowNo, seatNo)) {
                success = bookSeat(rowNo, seatNo);        
            } else {
                System.out.println("\nWrong input!\n");
            }
        } while (!success);
    }

    public void showStatistics() {
        System.out.printf("Number of purchased tickets: %d\n", numPurchasedTickets);
        System.out.printf("Percentage: %.2f%%\n",  percentPurchased);
        System.out.printf("Current income: $%d\n", currentIncome);
        System.out.printf("Total income: $%d\n", totalIncome);
    }
    
    static void showMenu() {
        String menu = """            
            1. Show the seats
            2. Buy a ticket
            3. Statistics
            0. Exit
            > """;
        System.out.print(menu);
    }

    static int chooseMenu(Scanner scanner) {
        showMenu();
        return scanner.nextInt();
    }
    
    public static void main(String[] args) {
        // Write your code here        
        Scanner scanner = new Scanner(System.in);
        
        System.out.printf("Enter the number of rows:\n> ");
        int numRows = scanner.nextInt();
        System.out.printf("Enter the number of seats in each row:\n> ");
        int seatsPerRow = scanner.nextInt();
        
        Cinema cinema = new Cinema(numRows, seatsPerRow);        

        boolean exit = false;
        while (!exit) {
            int selectedMenu = chooseMenu(scanner);
            switch (selectedMenu) {
                case 1: 
                    cinema.showSeats();
                    break;
                case 2: 
                    cinema.buyTicket(scanner);
                    break;
                case 3:
                    cinema.showStatistics();
                    break;
                case 0:
                    exit = true;
                    break;
                default:
                    exit = true;
                    break;
            }            
        }        
    }
}
