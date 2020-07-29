package tictactoe;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Board {

    private Point[] points;
    Map<Point, Character> field;
    int xCount;
    int oCount;

    public void checkStatus() {
        char winner = ' ';
        char p0 = field.get(points[0]);
        char p1 = field.get(points[1]);
        char p2 = field.get(points[2]);
        char p3 = field.get(points[3]);
        char p4 = field.get(points[4]);
        char p5 = field.get(points[5]);
        char p6 = field.get(points[6]);
        char p7 = field.get(points[7]);
        char p8 = field.get(points[8]);

        // Check the 8 ways that match, but is not a blank ' ' match.
        if (p0 == p1 && p1 == p2 && p0 != ' ') {
            winner = p0;
        } else if (p3 == p4 && p4 == p5 && p3 != ' ') {
            winner = p3;
        } else if (p6 == p7 && p7 == p8 && p6 != ' ') {
            winner = p6;
        } else if (p0 == p3 && p3 == p6 && p0 != ' ') {
            winner = p0;
        } else if (p1 == p4 && p4 == p7 && p1 != ' ') {
            winner = p1;
        } else if (p2 == p5 && p5 == p8 && p2 != ' ') {
            winner = p2;
        } else if (p0 == p4 && p4 == p8 && p0 != ' ') {
            winner = p0;
        } else if (p2 == p4 && p4 == p6 && p2 != ' ') {
            winner = p2;
        } else {
            boolean gameFinished = true;

            for (Point p : field.keySet()) {
                if (field.get(p) == ' ') {
                    // Spots open so game not finshed
                    gameFinished = false;
                }
            }
            System.out.println(gameFinished ? "Draw" : "Game not finished");
        }

        if (winner != ' ') {
            System.out.println(winner + " wins");
        }
    }

    public void enterCoordinate(Scanner sc) {
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

}
