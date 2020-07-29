package tictactoe;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        final String initialBoard = "_________";

        Board board = new Board();

        board.initializeBoard(initialBoard);
        board.drawBoard();

        while (true) {
            if (board.getxCount() <= board.getoCount()) {
                board.enterCoordinate(sc);  //   Human 'X' turn
            } else {
                board.aiMoveEasy();    // AI 'O' turn
            }
            board.drawBoard();
            if (board.checkStatus()) {
                break;
            }
        }

    }
}
