package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Board board = new Board();
        System.out.print("Enter cells: ");
        String input = sc.nextLine();

        board.initializeBoard(input);
        board.drawBoard();

        board.enterCoordinate(sc);
        board.drawBoard();
        board.checkStatus();
    }
}
