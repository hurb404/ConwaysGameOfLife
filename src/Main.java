import java.awt.*;
import javax.swing.*;
public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Conway's Game of Life");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        LifeBoard board = new LifeBoard(50, 50, 10);
        frame.add(board);
        frame.pack();
        frame.setVisible(true);

        while (true) {
            board.advanceGeneration();
            board.repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class LifeBoard extends JPanel {
    private int rows;
    private int cols;
    private int cellSize;
    private boolean[][] grid;

    public LifeBoard(int rows, int cols, int cellSize) {
        this.rows = rows;
        this.cols = cols;
        this.cellSize = cellSize;
        this.grid = new boolean[rows][cols];
        setPreferredSize(new Dimension(rows * cellSize, cols * cellSize));

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                grid[row][col] = Math.random() < 0.2;
            }
        }
    }

    public void advanceGeneration() {
        boolean[][] newGrid = new boolean[rows][cols];
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int liveNeighbors = countLiveNeighbors(row, col);
                newGrid[row][col] = (grid[row][col] && liveNeighbors == 2) || liveNeighbors == 3;
            }
        }
        grid = newGrid;
    }

    private int countLiveNeighbors(int row, int col) {
        int count = 0;
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                if (dr == 0 && dc == 0) continue;
                int newRow = (row + dr + rows) % rows;
                int newCol = (col + dc + cols) % cols;
                if (grid[newRow][newCol]) count++;
            }
        }
        return count;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                g.setColor(grid[row][col] ? Color.BLACK : Color.WHITE);
                g.fillRect(col * cellSize, row * cellSize, cellSize, cellSize);
                g.setColor(Color.GRAY);
                g.drawRect(col * cellSize, row * cellSize, cellSize, cellSize);
            }
        }
    }
}