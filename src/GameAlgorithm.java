import java.io.*;
import java.util.*;

/**
 * This class includes the game algorithm, design and everything!
 * @author Shima Zahabi
 */
public class GameAlgorithm {
    private final Scanner input = new Scanner(System.in);
    private String[][] board;
    private final ArrayList<Integer> emptyCells = new ArrayList<>();
    private String winner;
    private String turn;
    private String move;
    private final int setRow = 4;
    private final int setColumn = 4;
    private final int setBlocked = 3;
    private final int setWinNum = 3;
    private final File Accounts = new File("TicTacToeAccounts.txt");
    private final ArrayList<String> activeUsers = new ArrayList<>();
    private boolean flag = false;

    /**
     * This method includes the main menu options.
     */
    public void mainMenu() {
        int command;

        loginSignUpMenu();

        do {
            clearConsole();

            System.out.println(ANSI_CYAN + "\t||<< Tic Tac Toe >>||\t" + ANSI_RESET);
            System.out.printf(ANSI_PURPLE + "%s%n%s%n%s%n%s%n", "1- START A GAME", "2- Profile", "3- INFORMATION", "4- EXIT" + ANSI_RESET);

            command = input.nextInt();

            switch (command) {
                case 1 -> gameMenu();
                case 2 -> profile();
                case 3 -> info();
                default -> {
                }
            }
        } while (command != 4);
    }

    /**
     * This method includes the information of the game.
     * Programmer Info and Game Rules
     */
    public void info() {
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
    public void gameMenu() {
        int command;

        do {
            clearConsole();
            flag = false;

            System.out.println(ANSI_CYAN + "\t||<< Game Menu >>||\t\n" + ANSI_RESET);
            System.out.println(ANSI_GREEN + "# Pick your challenger :)");
            System.out.printf("%s%n%s%n%s%n", "1- HUMAN", "2- COMPUTER", "3- RETURN" + ANSI_RESET);

            command = input.nextInt();

            switch (command) {
                case 1 -> {
                    human();
                    pressKey();
                }
                case 2 -> {
                    computer();
                    pressKey();
                }
                default -> {
                }
            }
        } while (command != 3);
    }

    /**
     * This method includes the login-Sign up options.
     */
    public void loginSignUpMenu() {
        int command;

            clearConsole();

            System.out.println(ANSI_CYAN + "\t||<< Tic Tac Toe >>||\t");
            System.out.printf(ANSI_YELLOW + "%s%n%n", "WELCOME !");
            System.out.printf(ANSI_BLUE + "%s%n", "1- LOGIN");
            System.out.printf(ANSI_RED + "%s%n%s%n", "Don't have an account yet?!", "2- SIGN UP");

            command = input.nextInt();

        switch (command) {
            case 1 -> login();
            case 2 -> signUp();
            default -> {
            }
        }
    }

    /**
     * This method is for the login option. (In fact, it checks whether the entered username and password exist or not.)
     */
    public void login() {
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

        activeUsers.add(userName);
        pressKey();
    }

    /**
     * This method is for the sign uo option. (It writes the entered information in a txt file.)
     */
    public void signUp() {
        clearConsole();

        System.out.println(ANSI_CYAN + "\t||<< Sign Up >>||\t");

        String userName;
        String password;
        String confirmPassword;
        String win;
        String loss;
        String tie;
        String Games;

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
        Games = "0";

        try {
            FileWriter writer = new FileWriter(Accounts, true);
            BufferedWriter bufferedWriter = new BufferedWriter(writer);

            bufferedWriter.write(userName + '\n');
            bufferedWriter.write(password + '\n');
            bufferedWriter.write(win + '\n');
            bufferedWriter.write(loss + '\n');
            bufferedWriter.write(tie + '\n');
            bufferedWriter.write(Games + '\n');
            bufferedWriter.write("#" + '\n'); //separator

            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        activeUsers.add(userName);
        System.out.println("\nSigning Up Successfully Completed !\n ENJOY YOUR GAME!");
        pressKey();
    }

    /**
     * This method is for the player 2 to sign up or login.
     */
    public void signUpPlayer2() {
        int command;

        clearConsole();

        System.out.printf(ANSI_YELLOW + "%s%n", "** Your opponent needs to sign up or login first !!");
        System.out.printf(ANSI_GREEN + "%s%n", "1- LOGIN");
        System.out.printf(ANSI_RED + "%s%n%s%n", "Don't have an account yet?!", "2- SIGN UP");

        command = input.nextInt();

        switch (command) {
            case 1 -> login();
            case 2 -> signUp();
            default -> {
            }
        }
    }

    /**
     * This method updates the win-loss-tie record after every game.
     * @param userName the username of the player in order to save their record
     * @param status it can be win, loss or tie
     */
    public void updateScores(String userName, String status) {
        List<String> lines = new ArrayList<>();
        String line = null;

        int changeLine = 0;
        switch (status) {
            case "win" -> changeLine = 2;
            case "loss" -> changeLine = 3;
            case "tie" -> changeLine = 4;
            default -> {
            }
        }

        try {
            FileReader reader = new FileReader(Accounts);
            BufferedReader bufferedReader = new BufferedReader(reader);

            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);

                if (line.equals(userName)) {
                    for (int i = 1; i < 5; i++) {
                        line = bufferedReader.readLine();
                        if (i == changeLine) {
                            line = String.valueOf(Integer.parseInt(line) + 1);
                        }
                        lines.add(line);
                    }
                    line = bufferedReader.readLine();
                    line = String.valueOf(Integer.parseInt(line) + 1);
                    lines.add(line);
                }
            }

            reader.close();
            bufferedReader.close();

            try {
                FileWriter writer = new FileWriter(Accounts);
                BufferedWriter bufferedWriter = new BufferedWriter(writer);

                for (String s : lines) {
                    bufferedWriter.write(s + '\n');
                }

                bufferedWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method shows the profiles of the active users. (username and their win-loss-tie record)
     */
    public void profile() {
        clearConsole();

        System.out.println(ANSI_CYAN + "\t||<< Profile >>||\t");
        System.out.printf(ANSI_YELLOW + "%s%n%n", "=> Active Users and their win-loss-tie record : ");

        int size = activeUsers.size();
        for (int i = 0; i < size; i++) {
            try {
                FileReader reader = new FileReader(Accounts);
                BufferedReader bufferedReader = new BufferedReader(reader);

                String line;

                System.out.println(ANSI_PURPLE + "~* ACTIVE USER " + (i + 1) + " *~");
                while ((line = bufferedReader.readLine()) != null) {

                    if(Objects.equals(line, activeUsers.get(i))) {
                        for (int j = 0; j < 6; j++) {
                            switch (j) {
                                case 0 -> System.out.printf("|#| Username : %s%n", line);
                                case 2 -> System.out.printf("|#| Win : %s%n", line);
                                case 3 -> System.out.printf("|#| Loss : %s%n", line);
                                case 4 -> System.out.printf("|#| Tie : %s%n", line);
                                case 5 -> System.out.printf("|#| Games : %s%n", line);
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
    public void showBoard() {
        String line = "+-----";
        for (int i = 0; i < setRow; i++) {
            System.out.println(ANSI_PURPLE + line.repeat(setColumn) + "+" + ANSI_RESET);
            for (int j = 0; j < setColumn; j++) {
                System.out.printf(ANSI_PURPLE + "%s", "|" + ANSI_RESET);
                System.out.printf(" %8s ", board[i][j]);
            }
            System.out.printf(ANSI_PURPLE + "%s%n", "|" + ANSI_RESET);
        }
        System.out.println(ANSI_PURPLE + line.repeat(setColumn) + "+" + ANSI_RESET);
    }

    /**
     * This method includes the game algorithm when the opponent is a human.
     */
    public void human() {
        if(!flag) {
            signUpPlayer2();
            flag = true;
        }

        setMatrixArray();

        while (emptyCells.size() != 0){
            clearConsole();

            showBoard();

            System.out.println(ANSI_BLUE + "This is " + turn + "'s turn!\n" + ANSI_RESET + "Enter your cell number: \n");

            int cellNum = input.nextInt();

            while (true) {
                if(checkEmptyCells(cellNum - 1)) {
                    board[(cellNum - 1) / setColumn][(cellNum - 1) % setColumn] = move;
                    break;
                } else {
                    System.out.println(ANSI_RED + "** Attention => Chosen cell isn't available.");
                    System.out.println("Try another cell number: " + ANSI_RESET);
                    cellNum = input.nextInt();
                }
            }

            winner = winnerCheck((cellNum - 1) / setColumn, (cellNum - 1) % setColumn);

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

            updateScores(activeUsers.get(0), "tie");
            updateScores(activeUsers.get(1), "tie");
        }

        switch (winner) {
            case "X" -> {
                updateScores(activeUsers.get(0), "win");
                updateScores(activeUsers.get(1), "loss");
            }
            case "O" -> {
                updateScores(activeUsers.get(1), "win");
                updateScores(activeUsers.get(0), "loss");
            }
            default -> {
            }
        }

        if(Objects.equals(reStart(), "R")){
            human();
        }
    }

    /**
     * This method includes the game algorithm when the opponent is a computer.
     */
    public void computer() {
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
                        board[(cellNum - 1) / setColumn][(cellNum - 1) % setColumn] = move;
                        winner = winnerCheck((cellNum - 1) / setColumn, (cellNum - 1) % setColumn);
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
                        board[cellNum / setColumn][cellNum % setColumn] = move;
                        winner = winnerCheck(cellNum / setColumn, cellNum % setColumn);
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

            updateScores(activeUsers.get(0), "tie");
        }

        switch (winner) {
            case "X" -> updateScores(activeUsers.get(0), "win");
            case "O" -> updateScores(activeUsers.get(0), "loss");
            default -> {
            }
        }

        if(Objects.equals(reStart(), "R")){
            computer();
        }
    }

    /**
     * This method is for restarting the same game.
     * @return 'R' for restarting the game and 'E' for returning to the previous menu.
     */
    public String reStart() {
        clearConsole();

        String command;
        System.out.println(ANSI_BLUE + "\nDo you want to restart the game? Enter 'R': ");
        System.out.println(ANSI_GREEN + "Otherwise, to return to the challenger menu, Enter 'E': ");

        command = input.nextLine();
        command = input.nextLine();
        while (!(Objects.equals(command, "R") || Objects.equals(command, "E"))) {
            System.out.println(ANSI_RED + "** Wrong command! Try Again :)");
            command = input.nextLine();
        }
        return command;
    }

    /**
     * This method sets the initial value of the Matrix (board) and the Array (the array that includes the empty cells).
     */
    public void setMatrixArray() {
        winner = null;
        turn = "X";
        move = ANSI_CYAN + "X";
        emptyCells.clear();
        board = new String[setRow][setColumn];

        int number = 1;
        for (int i = 0; i < setRow; i++) {
            for (int j = 0; j < setColumn; j++) {
                board[i][j] = ANSI_YELLOW + number;
                number++;
            }
        }

        for (int i = 0; i < (setRow * setColumn); i++) {
            emptyCells.add(i);
        }

        for (int i = 0; i < setBlocked; i++) {
            int blockCellNum = emptyCells.get(new Random().nextInt(emptyCells.size()));
            board[blockCellNum / setColumn][blockCellNum % setColumn] = ANSI_RED + "#";
            emptyCells.remove((Integer) blockCellNum);
        }
    }

    /**
     * This method checks whether the chosen cell is empty or not.
     * @param cellNum It is the index of the chosen cell.
     * @return true if the chosen cell is empty
     */
    public boolean checkEmptyCells(int cellNum) {
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
    public String winnerCheck(int row, int column) {
        //Row
        int countSimilar = 0;
        for (int i = 0; i < setColumn; i++) {

            if(Objects.equals(board[row][i], move)){
                countSimilar++;
            } else {
                countSimilar = 0;
            }
            if(countSimilar == setWinNum){
                winner = turn;
                return winner;
            }
        }

        //Column
        countSimilar = 0;
        for (int i = 0; i < setRow; i++) {

            if(Objects.equals(board[i][column], move)){
                countSimilar++;
            } else {
                countSimilar = 0;
            }
            if(countSimilar == setWinNum){
                winner = turn;
                return winner;
            }
        }

        //Minor Diameter
        int diametersNum = (setRow + setColumn) - 1;
        int cellsNumsInDiameter = 0;
        int startRow = 0;
        int startColumn;

        for (int i = 0; i < diametersNum; i++) {
            if(i >= setColumn){
                startRow++;
            }

            startColumn = maximum(0, (i + 1 - setRow));
            cellsNumsInDiameter = minimum((i + 1), setColumn - startColumn, setRow);

            if(row + column == i){
                if(diagonalWinStatus(startRow, (startRow + cellsNumsInDiameter), i, -1)){
                    winner = turn;
                    return winner;
                }
            }
        }

        //Main Diameter
        startRow = 0;
        for (int i = 0, j = 1 - setColumn; i < diametersNum; i++, j++) {
            if(i >= setColumn){
                startRow++;
            }

            cellsNumsInDiameter = minimum((i + 1), setRow - startRow, setColumn);

            if(row - column == j){
                if(diagonalWinStatus(startRow, (startRow + cellsNumsInDiameter), ((-1) * j), 1)){
                    winner = turn;
                    return winner;
                }
            }
        }
        return  winner;
    }

    /**
     * This method calculates the maximum of two integers.
     * @param a the first integer
     * @param b the second integer
     * @return the maximum
     */
    public static int maximum(int a, int b) {
        return Math.max(a, b);
    }

    /**
     * This method calculates the minimum of three integers.
     * @param a the first integer
     * @param b the second integer
     * @param c the third integer
     * @return the minimum
     */
    public static int minimum(int a, int b, int c) {
        int min1 = Math.min(a, b);
        return Math.min(min1, c);
    }


    /**
     * This method checks win status in a diameter.
     * @param start the index of the first cell in a diameter
     * @param end the index of the last cell in a diameter is less than this value
     * @param add this value is added to the row to calculate the column
     * @param sign this value is multiplied by the row to calculate the column
     * @return true if three same symbols founded in a diameter
     */
    public boolean diagonalWinStatus(int start, int end, int add, int sign) {
        int countSimilar = 0;

        for (int i = start; i < end; i++) {

            if(Objects.equals(board[i][(sign * i) + add], move)){
                countSimilar++;
            } else {
                countSimilar = 0;
            }
            if(countSimilar == setWinNum){
                return true;
            }
        }
        return false;
    }

    /**
     * This method clears the console. (However it doesn't work in intellij)
     */
    public void clearConsole() {
        System.out.print("\033[H\033[2J");
    }

    /**
     * This method waits for pressing a key to continue.
     */
    public void pressKey() {
        System.out.println("Please press a key to continue...");
        try{System.in.read();}
        catch(Exception e){	e.printStackTrace();}
    }

    /**
     * These are the Ansi Colors used for colorful console.
     */
    public final String ANSI_RESET = "\u001B[0m";
    public final String ANSI_RED = "\u001B[31m";
    public final String ANSI_GREEN = "\u001B[32m";
    public final String ANSI_YELLOW = "\u001B[33m";
    public final String ANSI_BLUE = "\u001B[34m";
    public final String ANSI_PURPLE = "\u001B[35m";
    public final String ANSI_CYAN = "\u001B[36m";
}