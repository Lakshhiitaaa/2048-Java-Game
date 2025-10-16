package view;

import model.GameOf2048;

public class GameOf2048View {
	public static void printBoard(GameOf2048 game) {
		int size = game.getSize();
		int[][] board = game.getBoard();
		System.out.println("Current Board:");
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				System.out.printf("%5d", board[i][j]);
			}
			System.out.println();
		}
		System.out.println();
	}
}
