package controller;

import model.GameOf2048;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameOf2048GUIController extends JFrame {

    private GameOf2048 game;
    private int size;
    private JLabel[][] tiles;
    private JPanel boardPanel;
    private JLabel scoreLabel;

    public GameOf2048GUIController(int size) {
        this.size = size;
        game = new GameOf2048(size);
        tiles = new JLabel[size][size];

        setTitle("2048 Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(size * 120, size * 140);
        setResizable(false);
        setLayout(new BorderLayout());

        // Score display
        scoreLabel = new JLabel("Score: 0", SwingConstants.CENTER);
        scoreLabel.setFont(new Font("Arial", Font.BOLD, 24));
        scoreLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(scoreLabel, BorderLayout.NORTH);

        // Board panel
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(size, size, 10, 10));
        boardPanel.setBackground(new Color(0xbbada0));
        boardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(boardPanel, BorderLayout.CENTER);

        // Tiles
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tiles[i][j] = new JLabel("", SwingConstants.CENTER);
                tiles[i][j].setFont(new Font("Arial", Font.BOLD, 36));
                tiles[i][j].setOpaque(true);
                tiles[i][j].setBackground(new Color(0xcdc1b4));
                tiles[i][j].setForeground(Color.BLACK);
                tiles[i][j].setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                boardPanel.add(tiles[i][j]);
            }
        }

        updateBoard();

        // Key listener
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                boolean moved = false;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP -> moved = game.move("W");
                    case KeyEvent.VK_DOWN -> moved = game.move("S");
                    case KeyEvent.VK_LEFT -> moved = game.move("A");
                    case KeyEvent.VK_RIGHT -> moved = game.move("D");
                }
                if (moved)
                    updateBoard();
                if (game.isGameOver())
                    JOptionPane.showMessageDialog(null, "Game Over!");
            }
        });

        setVisible(true);
    }

    private void updateBoard() {
        int[][] board = game.getBoard();
        int score = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int value = board[i][j];
                tiles[i][j].setText(value == 0 ? "" : String.valueOf(value));
                tiles[i][j].setBackground(getTileColor(value));
                tiles[i][j].setForeground(value <= 4 ? new Color(0x776e65) : Color.WHITE);
                score += value;
            }
        }
        scoreLabel.setText("Score: " + score);
        repaint();
    }

    private Color getTileColor(int value) {
        return switch (value) {
            case 0 -> new Color(0xcdc1b4);
            case 2 -> new Color(0xeee4da);
            case 4 -> new Color(0xede0c8);
            case 8 -> new Color(0xf2b179);
            case 16 -> new Color(0xf59563);
            case 32 -> new Color(0xf67c5f);
            case 64 -> new Color(0xf65e3b);
            case 128 -> new Color(0xedcf72);
            case 256 -> new Color(0xedcc61);
            case 512 -> new Color(0xedc850);
            case 1024 -> new Color(0xedc53f);
            case 2048 -> new Color(0xedc22e);
            default -> new Color(0x3c3a32);
        };
    }

    public static void main(String[] args) {
        String input = JOptionPane.showInputDialog("Enter board size (e.g., 4 for 4x4):");
        int size = Integer.parseInt(input);
        new GameOf2048GUIController(size);
    }
}
