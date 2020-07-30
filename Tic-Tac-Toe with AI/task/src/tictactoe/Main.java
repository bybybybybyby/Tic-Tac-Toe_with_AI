package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        final String initialBoard = "_________";

        Board board = new Board();

        board.initializeBoard(initialBoard);
        board.drawBoard();

        boolean loop = true;
        while (loop) {
            loop = board.inputCommand(sc);
        }

    }
}
