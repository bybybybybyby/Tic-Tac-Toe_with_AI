package tictactoe;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Board {

    private Point[] points;
    Map<Point, Character> field;
    int xCount;
    int oCount;
    String xMove = "";
    String oMove = "";

    public Board() {
        xCount = 0;
        oCount = 0;
        points = new Point[9];
        points[0] = new Point(1, 3);
        points[1] = new Point(2, 3);
        points[2] = new Point(3, 3);
        points[3] = new Point(1, 2);
        points[4] = new Point(2, 2);
        points[5] = new Point(3, 2);
        points[6] = new Point(1, 1);
        points[7] = new Point(2, 1);
        points[8] = new Point(3, 1);
        field = new HashMap<>();
    }


    public void initializeBoard(String s) {
        for (int i = 0; i < s.length(); i++) {
            field.put(points[i], s.charAt(i) == '_' ? ' ' : s.charAt(i));
        }
    }


    /**
     * @return true if game ends with winner or draw
     */
    public boolean checkStatus() {
        char winner = ' ';

        char[] cells = new char[9];
        for (int i = 0; i < cells.length; i++) {
            cells[i] = field.get(points[i]);
        }

        // Check the 8 ways that match, but is not a blank ' ' match.
        if (cells[0] == cells[1] && cells[1] == cells[2] && cells[0] != ' ') {
            winner = cells[0];
        } else if (cells[3] == cells[4] && cells[4] == cells[5] && cells[3] != ' ') {
            winner = cells[3];
        } else if (cells[6] == cells[7] && cells[7] == cells[8] && cells[6] != ' ') {
            winner = cells[6];
        } else if (cells[0] == cells[3] && cells[3] == cells[6] && cells[0] != ' ') {
            winner = cells[0];
        } else if (cells[1] == cells[4] && cells[4] == cells[7] && cells[1] != ' ') {
            winner = cells[1];
        } else if (cells[2] == cells[5] && cells[5] == cells[8] && cells[2] != ' ') {
            winner = cells[2];
        } else if (cells[0] == cells[4] && cells[4] == cells[8] && cells[0] != ' ') {
            winner = cells[0];
        } else if (cells[2] == cells[4] && cells[4] == cells[6] && cells[2] != ' ') {
            winner = cells[2];
        } else {
            boolean gameFinished = true;

            for (Point p : field.keySet()) {
                if (field.get(p) == ' ') {
                    // Spots open so game not finshed
                    gameFinished = false;
                }
            }
            if (gameFinished) {
                System.out.println("Draw");
                return true;
            } else {
                System.out.println("Game not finished");
            }
        }

        if (winner != ' ') {
            System.out.println(winner + " wins");
            return true;
        }

        return false;
    }


    public boolean inputCommand(Scanner scanner) {
        while (true) {
            System.out.print("Input command: ");
            String[] args = scanner.nextLine().split("\\s+");
            if (args[0].equals("exit")) {
                return false;
            } else if (args.length == 3 && args[0].equals("start")) {
                switch (args[1]) {
                    case "easy":
                        xMove = "aiEasy";
                        break;
                    case "user":
                        xMove = "user";
                        break;
                }
                switch (args[2]) {
                    case "easy":
                        oMove = "aiEasy";
                        break;
                    case "user":
                        oMove = "user";
                        break;
                }
                playGame(scanner);
                return true;
            } else {
                System.out.println("Bad parameters!");
            }
        }
    }


    public void playGame(Scanner sc) {
        initializeBoard("_________");
        while (true) {
            if (getxCount() <= getoCount()) {    // 'X' turn
                if (xMove.equals("user")) {
                    userMove(sc);
                } else {
                    aiMoveEasy('X');
                }
            } else {
                if (oMove.equals("user")) {
                    userMove(sc);
                } else {
                    aiMoveEasy('O');    //    'O' turn
                }
            }

            drawBoard();
            if (checkStatus()) {
                break;
            }
        }
    }


    public void aiMoveEasy(char letter) {
        System.out.println("Making move level \"easy\"");
        Random rand = new Random();
        boolean valid = false;

        while (!valid) {
            int x = rand.nextInt(3) + 1;
            int y = rand.nextInt(3) + 1;

            Point p = new Point(x, y);
            if (field.get(p) == ' ') {
                field.put(p, letter);
                valid = true;
            }
        }
    }

    public void userMove(Scanner sc) {
        Pattern pattern13 = Pattern.compile("[1-3] [1-3]");    // This is valid case
        Pattern pattern09 = Pattern.compile("\\d \\d");     // Digits, but not in range
        boolean valid = false;

        while (!valid) {
            System.out.println("Enter the coordinates: ");
            String coordinates = sc.nextLine();
            Matcher matcher13 = pattern13.matcher(coordinates);
            Matcher matcher09 = pattern09.matcher(coordinates);

            // Check validity of coordinates
            if (matcher13.matches()) {
                int x = Integer.valueOf(coordinates.substring(0, 1));
                int y = Integer.valueOf(coordinates.substring(2, 3));
                Point p = new Point(x, y);
                if (field.get(p) != ' ') {
                    System.out.println("This cell is occupied! Choose another one!");
                } else {
                    char mover = xCount > oCount ? 'O' : 'X';
                    field.put(p, mover);
                    valid = true;
                }
            } else if (matcher09.matches()) {
                System.out.println("Coordinates should be from 1 to 3!");
            } else {
                System.out.println("You should enter numbers!");
            }
        }
    }


    public void drawBoard() {
        // Get count of X's and O's to know whose turn it is
        this.xCount = 0;
        this.oCount = 0;
        for (Point p : field.keySet()) {
            if (field.get(p) == 'X') {
                this.xCount++;
            }
            if (field.get(p) == 'O') {
                this.oCount++;
            }
        }

        System.out.println("---------");
        System.out.println("| " + field.get(points[0]) + " " + field.get(points[1]) + " " + field.get(points[2]) + " |");
        System.out.println("| " + field.get(points[3]) + " " + field.get(points[4]) + " " + field.get(points[5]) + " |");
        System.out.println("| " + field.get(points[6]) + " " + field.get(points[7]) + " " + field.get(points[8]) + " |");
        System.out.println("---------");
    }

    public int getxCount() {
        return xCount;
    }

    public int getoCount() {
        return oCount;
    }


}
