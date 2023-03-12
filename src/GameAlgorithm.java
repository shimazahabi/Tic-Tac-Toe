import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import java.util.Scanner;


/**
 * This class includes the game algorithm, design and everything!
 * @author Shima Zahabi
 */
public class GameAlgorithm {
    private static Scanner input = new Scanner(System.in);
    private static String[][] board = new String[4][4];
    private static ArrayList<Integer> emptyCells = new ArrayList<>();
    private static String winner;
    private static String turn;
    private static String move;
    private static File Accounts = new File("TicTacToeAccounts.txt");
    private static String[] activeUsers = new String[4];

    /**
     * This method includes the main menu options.
     */
    public static void mainMenu() {
        int command;
        loginSignUpMenu();
        do {
            clearConsole();

            System.out.println(ANSI_CYAN + "\t||<< Tic Tac Toe >>||\t" + ANSI_RESET);
            System.out.printf(ANSI_PURPLE + "%s%n%s%n%s%n%s%n", "1- START A GAME", "2- Profile", "3- INFORMATION", "4- EXIT" + ANSI_RESET);

            command = input.nextInt();

            switch (command) {
                case 1:
                    gameMenu();
                    break;
                case 2:
                    profile();
                    break;
                case 3:
                    info();
                    break;
                case 4:
                    break;
                default:
                    break;
            }
        } while (command != 4);
    }

    /**
     * This method includes the information of the game.
     * Programmer Info and Game Rules
     */
    public static void info() {
        clearConsole();

        System.out.println(ANSI_CYAN + "\t||<< Information >>||\t\n" + ANSI_RESET);
        System.out.printf(ANSI_BLUE + "# %s : %s %n", "Programmer","Shima Zahabi");
        System.out.printf("# %s => %s %n%n", "Email", "shimazahabi83@gmail.com" + ANSI_RESET);
        System.out.println( ANSI_YELLOW + "~ABOUT TIC-TAC-TOE :\n" +
                " This game is very popular and is fairly simple!\n" +
                "There are a few differences with the classical one:\n" +
                "• The board is square with 16 cells (4*4).\n" +
                "• Some cells will be randomly locked (no player can use these cells).\n" +
                "• The goal of Tic-Tac-Toe is to be one of the players to get three same \n" +
                "symbols in a row (horizontally, vertically, or diagonally).\n" +
                "• Up to 2 players can play simultaneously. (For single player, the other \n" +
                "player can be an AI). There are two options for players:\n" +
                "1 Human\n" +
                "2 Computer" + ANSI_RESET);

        pressKey();
    }

    /**
     * This method includes the game menu options.
     */
    public static void gameMenu() {
        int command;

        do {
            clearConsole();

            System.out.println(ANSI_CYAN + "\t||<< Game Menu >>||\t\n" + ANSI_RESET);
            System.out.println(ANSI_GREEN + "# Pick your challenger :)");
            System.out.printf("%s%n%s%n%s%n", "1- HUMAN", "2- COMPUTER", "3- RETURN" + ANSI_RESET);

            command = input.nextInt();

            switch (command){
                case 1:
                    human();
                    pressKey();
                    break;
                case 2:
                    computer();
                    pressKey();
                    break;
                case 3:
                    break;
                default:
                    break;
            }
        } while (command != 3);
    }

    public static void loginSignUpMenu() {
        int command;

            clearConsole();

            System.out.println(ANSI_CYAN + "\t||<< Tic Tac Toe >>||\t");
            System.out.printf(ANSI_YELLOW + "%s%n%n", "WELCOME !");
            System.out.printf(ANSI_BLUE + "%s%n", "1- LOGIN");
            System.out.printf(ANSI_RED + "%s%n%s%n", "Don't have an account yet?!", "2- SIGN UP");

            command = input.nextInt();

            switch (command) {
                case 1:
                    login();
                    break;
                case 2:
                    signUp();
                    break;
                default:
                    break;
            }
    }

    public static void login() {
        clearConsole();

        System.out.println(ANSI_CYAN + "\t||<< Login >>||\t");

        String userName;
        String password;
        boolean found = false;

        input.nextLine();
        while (true) {
            System.out.print(ANSI_BLUE + "Username: ");
            userName = input.nextLine();
            System.out.print("\nPassword: " + ANSI_RESET);
            password = input.nextLine();

            try {
                FileReader reader = new FileReader(Accounts);
                BufferedReader bufferedReader = new BufferedReader(reader);

                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    if (Objects.equals(userName, line)) {
                        line = bufferedReader.readLine();
                        if(Objects.equals(line, password)) {
                            found = true;
                            break;
                        }
                    }
                    System.out.println(line);
                }
                reader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            if (found) {
                System.out.println(ANSI_GREEN + "\nWELCOME USER => " + userName);
                break;
            } else {
                System.out.println(ANSI_RED + "USER NOT FOUND! TRY AGAIN!");
            }
        }
        activeUsers[0] = userName;
        pressKey();
    }
    public static void signUp() {
        clearConsole();
        System.out.println(ANSI_CYAN + "\t||<< Sign Up >>||\t");

        String userName;
        String password;
        String confirmPassword;
        String win;
        String loss;
        String tie;

        input.nextLine();
        System.out.print(ANSI_BLUE + "Enter Username: ");
        userName = input.nextLine();

        while(true) {
            System.out.print("\nEnter Password: ");
            password = input.nextLine();
            System.out.print("\nConfirm Your Password: ");
            confirmPassword = input.nextLine();
            if(Objects.equals(password, confirmPassword)) {
                break;
            } else {
                System.out.println("\nPasswords do NOT match...Try Again :)\n");
            }
        }

        win = "0";
        loss = "0";
        tie = "0";

        try {
            FileWriter writer = new FileWriter(Accounts, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            bufferedWriter.write(userName + '\n');
            bufferedWriter.write(password + '\n');
            bufferedWriter.write(win + '\n');
            bufferedWriter.write(loss + '\n');
            bufferedWriter.write(tie + '\n');
            bufferedWriter.write("#" + '\n'); //separator

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        activeUsers[0] = userName;
        System.out.println("\nSigning Up Successfully Completed !\n ENJOY YOUR GAME!");
        pressKey();
    }
    public static void profile() {
        System.out.println(ANSI_CYAN + "\t||<< Profile >>||\t");
        System.out.printf(ANSI_YELLOW + "%s%n%n", "=> Active Users and their win-loss-tie record : ");

        int size = activeUsers.length;
        for (int i = 0; i < size; i++) {
            try {
                FileReader reader = new FileReader(Accounts);
                BufferedReader bufferedReader = new BufferedReader(reader);

                String line;

                System.out.println(ANSI_PURPLE + "~* ACTIVE USER " + (i+1) + "*~");
                while ((line = bufferedReader.readLine()) != null) {

                    if(Objects.equals(line = bufferedReader.readLine(), activeUsers[i])) {
                        for (int j = 0; j < 5; j++) {
                            switch (j) {
                                case 0 -> System.out.printf("|#| Username : %s%n", line);
                                case 2 -> System.out.printf("|#| Win : %s%n", line);
                                case 3 -> System.out.printf("|#| Loss : %s%n", line);
                                case 4 -> System.out.printf("|#| Tie : %s%n", line);
                                default -> {
                                }
                            }
                            line = bufferedReader.readLine();
                        }
                        break;
                    }
                }
                System.out.println("+---------------------------+\n");
                reader.close();
                System.out.print(ANSI_RESET);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        pressKey();
    }
    /**
     * This method prints the board.
     */
    public static void showBoard() {

        for (int i = 0; i < 4; i++) {
            System.out.println(ANSI_PURPLE + "+-----+-----+-----+-----+" + ANSI_RESET);
            for (int j = 0; j < 4; j++) {
                System.out.printf(ANSI_PURPLE + "%s", "|" + ANSI_RESET);
                System.out.printf(" %8s ", board[i][j]);
            }
            System.out.printf(ANSI_PURPLE + "%s%n", "|" + ANSI_RESET);
        }
        System.out.println(ANSI_PURPLE + "+-----+-----+-----+-----+" + ANSI_RESET);
    }

    /**
     * This method includes the game algorithm when the opponent is a human.
     */
    public static void human() {
        setMatrixArray();

        while (emptyCells.size() != 0){
            clearConsole();

            showBoard();

            System.out.println(ANSI_BLUE + "This is " + turn + "'s turn!\n" + ANSI_RESET + "Enter your cell number: \n");

            int cellNum = input.nextInt();

            while (true) {
                if(checkEmptyCells(cellNum - 1)) {
                    board[(cellNum - 1) / 4][(cellNum - 1) % 4] = move;
                    break;
                } else {
                    System.out.println(ANSI_RED + "** Attention => Chosen cell isn't available.");
                    System.out.println("Try another cell number: " + ANSI_RESET);
                    cellNum = input.nextInt();
                }
            }

            winner = winnerCheck((cellNum - 1) / 4, (cellNum - 1) % 4);

            if(winner != null){
                clearConsole();
                showBoard();
                System.out.println(ANSI_YELLOW + "\n||# WINNER : " + turn + ANSI_RESET);
                break;
            }

            if(Objects.equals(turn, "X")){
                turn = "O";
                move = ANSI_GREEN + "O";
            } else if(Objects.equals(turn, "O")){
                move = ANSI_CYAN + "X";
                turn = "X";
            }
        }

        if (winner == null){
            clearConsole();
            showBoard();
            System.out.println(ANSI_GREEN + "\n||# It's a tie!" + ANSI_RESET);
        }
    }

    /**
     * This method includes the game algorithm when the opponent is a computer.
     */
    public static void computer() {
        setMatrixArray();

        while (emptyCells.size() != 0){
            clearConsole();

            showBoard();

            int cellNum = 1;
            if(Objects.equals(turn, "X")){
                System.out.println(ANSI_BLUE + "It's YOUR turn!\n" + ANSI_RESET + "Enter your cell number: ");

                cellNum = input.nextInt();

                while (true) {

                    if(checkEmptyCells(cellNum - 1)) {
                        board[(cellNum - 1) / 4][(cellNum - 1) % 4] = move;
                        winner = winnerCheck((cellNum - 1) / 4, (cellNum - 1) % 4);
                        break;
                    } else {
                        System.out.println(ANSI_RED + "** Attention => Chosen cell isn't available.");
                        System.out.println("Try another cell number: " + ANSI_RESET);
                        cellNum = input.nextInt();
                    }
                }
            } else if(Objects.equals(turn, "O")){
                System.out.println(ANSI_BLUE + "It's My turn!" + ANSI_RESET);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                cellNum = emptyCells.get(new Random().nextInt(emptyCells.size()));

                while (true) {
                    if(checkEmptyCells(cellNum)) {
                        board[cellNum / 4][cellNum % 4] = move;
                        winner = winnerCheck(cellNum / 4, cellNum % 4);
                        break;
                    } else {
                        cellNum = emptyCells.get(new Random().nextInt(emptyCells.size()));
                    }
                }
            }

            if(winner != null){
                clearConsole();
                showBoard();
                System.out.println(ANSI_YELLOW + "||# WINNER : " + turn + ANSI_RESET);
                break;
            }

            if(Objects.equals(turn, "X")){
                turn = "O";
                move = ANSI_GREEN + "O";
            } else if(Objects.equals(turn, "O")){
                turn = "X";
                move = ANSI_CYAN + "X";
            }
        }

        if (winner == null){
            clearConsole();
            showBoard();
            System.out.println(ANSI_GREEN + "||# It’s a tie!" + ANSI_RESET);
        }
    }

    /**
     * This method sets the initial value of the Matrix (board) and the Array (the array that includes the empty cells).
     */
    public static void setMatrixArray() {
        winner = null;
        turn = "X";
        move = ANSI_CYAN + "X";
        emptyCells.clear();

        int number = 1;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                board[i][j] = ANSI_YELLOW + number;
                number++;
            }
        }

        for (int i = 0; i < 16; i++) {
            emptyCells.add(i);
        }

        for (int i = 0; i < 3; i++) {
            int blockCellNum = emptyCells.get(new Random().nextInt(emptyCells.size()));
            board[blockCellNum / 4][blockCellNum % 4] = ANSI_RED + "#";
            emptyCells.remove((Integer) blockCellNum);
        }
    }

    /**
     * This method checks whether the chosen cell is empty or not.
     * @param cellNum It is the index of the chosen cell.
     * @return true if the chosen cell is empty
     * @return false if the chosen cell is already used.
     */
    public static boolean checkEmptyCells(int cellNum) {
        if(emptyCells.contains(cellNum)){
            emptyCells.remove((Integer) cellNum);
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method checks if three same
     * symbols are in a row (horizontally, vertically, or diagonally). (around the chosen cell)
     * @param row the row of the chosen cell
     * @param column the column of the chosen cell
     * @return the winner's symbol (returns null if nobody has won the game).
     */
    static String winnerCheck(int row, int column) {
        // row
        int countSimilar = 0;
        for (int i = 0; i < 4; i++) {

            if(Objects.equals(board[row][i], move)){
                countSimilar++;
            } else {
                countSimilar = 0;
            }
            if(countSimilar == 3){
                winner = turn;
                return winner;
            }
        }

        //column
        countSimilar = 0;
        for (int i = 0; i < 4; i++) {

            if(Objects.equals(board[i][column], move)){
                countSimilar++;
            } else {
                countSimilar = 0;
            }
            if(countSimilar == 3){
                winner = turn;
                return winner;
            }
        }

        if (row == column) {

            if(diagonalWinStatus(0, 4, 0, 1)){
                winner = turn;
                return winner;
            }
        }

        if(row + column == 3) {

            if(diagonalWinStatus(0, 4, 3, -1)){
                winner = turn;
                return winner;
            }
        }

        if(column - row == 1) {

            if(diagonalWinStatus(0, 3, 1, 1)){
                winner = turn;
                return winner;
            }
        }

        if(row - column == 1) {

            if(diagonalWinStatus(1, 4, -1, 1)){
                winner = turn;
                return winner;
            }
        }

        if(row + column == 2) {

            if(diagonalWinStatus(0, 3, 2, -1)){
                winner = turn;
                return winner;
            }
        }

        if(row + column == 4) {

            if(diagonalWinStatus(1, 4, 4, -1)){
                winner = turn;
                return winner;
            }
        }
        return  winner;
    }

    /**
     * This method checks win status in a diameter.
     * @param start the index of the first cell in a diameter
     * @param end the index of the last cell in a diameter is less than this value
     * @param add this value is added to the row to calculate the column
     * @param sign this value is multiplied by the row to calculate the column
     * @return true if three same symbols founded in a diameter
     * @return false if three same symbols not founded in a diameter
     */
    public static boolean diagonalWinStatus(int start, int end, int add, int sign) {
        boolean win = false;
        int countSimilar = 0;

        for (int i = start; i < end; i++) {

            if(Objects.equals(board[i][(sign * i) + add], move)){
                countSimilar++;
            } else {
                countSimilar = 0;
            }
            if(countSimilar == 3){
                win = true;
                return win;
            }
        }
        return win;
    }

    /**
     * This method clears the console. (However it doesn't work in intellij)
     */
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
    }

    /**
     * This method waits for pressing a key to continue.
     */
    public static void pressKey() {
        System.out.println("Please press a key to continue...");
        try{System.in.read();}
        catch(Exception e){	e.printStackTrace();}
    }

    /**
     * These are the Ansi Colors used for colorful console.
     */
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
}