package controller;

import model.GameOf2048;
import view.GameOf2048View;

import java.util.Scanner;

public class GameOf2048Controller {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter board size (e.g., 4 for 4x4):");
		int size = sc.nextInt();
		GameOf2048 game = new GameOf2048(size);

		GameOf2048View.printBoard(game);

		while (!game.isGameOver()) {
			System.out.println("Enter move (W=up, A=left, S=down, D=right):");
			String move = sc.next();
			if (game.move(move)) {
				GameOf2048View.printBoard(game);
			} else {
				System.out.println("Invalid move! Try another direction.");
			}
		}

		System.out.println("Game Over!");
		sc.close();
	}
}
