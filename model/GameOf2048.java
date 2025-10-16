package model;

import java.util.Random;

public class GameOf2048 {
	private int[][] board;
	private int size;
	private Random random = new Random();

	public GameOf2048(int size) {
		this.size = size;
		board = new int[size][size];
		addRandomTile();
		addRandomTile();
	}

	public int[][] getBoard() {
		return board;
	}

	public int getSize() {
		return size;
	}

	// Add a new tile (2 or 4) in a random empty position
	public void addRandomTile() {
		int emptyCount = 0;
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				if (board[i][j] == 0)
					emptyCount++;

		if (emptyCount == 0)
			return;

		int pos = random.nextInt(emptyCount);
		int val = random.nextInt(10) < 9 ? 2 : 4; // 90% chance 2, 10% chance 4
		int count = 0;
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if (board[i][j] == 0) {
					if (count == pos) {
						board[i][j] = val;
						return;
					}
					count++;
				}
			}
		}
	}

	// Move tiles in the direction: "W"=up, "A"=left, "S"=down, "D"=right
	public boolean move(String direction) {
		boolean moved = false;
		switch (direction.toUpperCase()) {
			case "W":
				moved = moveUp();
				break;
			case "A":
				moved = moveLeft();
				break;
			case "S":
				moved = moveDown();
				break;
			case "D":
				moved = moveRight();
				break;
		}
		if (moved)
			addRandomTile();
		return moved;
	}

	private boolean moveLeft() {
		boolean moved = false;
		for (int i = 0; i < size; i++) {
			int[] row = board[i];
			int[] newRow = new int[size];
			int pos = 0;
			boolean merged = false;
			for (int j = 0; j < size; j++) {
				if (row[j] != 0) {
					if (pos > 0 && newRow[pos - 1] == row[j] && !merged) {
						newRow[pos - 1] *= 2;
						merged = true;
						moved = true;
					} else {
						newRow[pos++] = row[j];
						if (j != pos - 1)
							moved = true;
						merged = false;
					}
				}
			}
			board[i] = newRow;
		}
		return moved;
	}

	private boolean moveRight() {
		rotate180();
		boolean moved = moveLeft();
		rotate180();
		return moved;
	}

	private boolean moveUp() {
		rotateCounterClockwise();
		boolean moved = moveLeft();
		rotateClockwise();
		return moved;
	}

	private boolean moveDown() {
		rotateClockwise();
		boolean moved = moveLeft();
		rotateCounterClockwise();
		return moved;
	}

	private void rotateClockwise() {
		int[][] rotated = new int[size][size];
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				rotated[j][size - 1 - i] = board[i][j];
		board = rotated;
	}

	private void rotateCounterClockwise() {
		int[][] rotated = new int[size][size];
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				rotated[size - 1 - j][i] = board[i][j];
		board = rotated;
	}

	private void rotate180() {
		rotateClockwise();
		rotateClockwise();
	}

	public boolean isGameOver() {
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size; j++)
				if (board[i][j] == 0)
					return false;

		// Check horizontal and vertical merges
		for (int i = 0; i < size; i++)
			for (int j = 0; j < size - 1; j++)
				if (board[i][j] == board[i][j + 1] || board[j][i] == board[j + 1][i])
					return false;

		return true;
	}
}
