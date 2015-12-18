package dk.itu.oop.mineSweeper;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board extends JPanel implements ActionListener, Serializable
{

	private static final long serialVersionUID = 1L;
	private int totalMines = 4;
	private Block[][] blocks;
	private Dimension gridSize = new Dimension(10, 5);;
	private int visibleBlocks;

	public Block[][] getBlocks()
	{
		return blocks;
	}

	public int getVisibleBlocks()
	{
		return visibleBlocks;
	}

	// Block Types - Numbers correspond to mines around them, 9 is a mine.
	public Board()
	{
		setLayout(new GridLayout(gridSize.height, gridSize.width));

		visibleBlocks = gridSize.width * gridSize.height;

		startGame();
	}

	public void startGame()
	{
		setVisible(false);
		blocks = new Block[gridSize.height][gridSize.width];
		for (int i = 0; i < gridSize.height; i++)
		{
			for (int j = 0; j < gridSize.width; j++)
			{
				blocks[i][j] = new Block(i, j);
				blocks[i][j].setText("?");
				blocks[i][j].addActionListener(this);
				add(blocks[i][j]);
			}
		}

		addMines();
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		try
		{
			showBlock((Block) (e.getSource()));
		} catch (Exception exc)
		{
			System.err.println(exc.getMessage()
					+ "Board's ActionListener only for Block objects");
		}
	}

	private void showBlock(Block block)
	{
		block.setEnabled(false);

		if (block.noMines())
			showNeighbours(block);
		else if (block.isMine())
			showGameOver();
		else
			block.setText(Integer.toString(block.getType()));

		visibleBlocks--;

		if (visibleBlocks == totalMines)
			showGameOver();
	}

	private void showMines()
	{
		for (Block[] rowBlocks : blocks)
		{
			for (Block block : rowBlocks)
			{
				if (block.isMine())
					block.setText("X");
			}
		}
	}

	private void showGameOver()
	{
		if (visibleBlocks != totalMines)
		{ // Lose
			showMines();
			JOptionPane.showMessageDialog(this, "Lose");
		} else
		{
			JOptionPane.showMessageDialog(this, "Win");
		}

		System.exit(0);
	}

	private void addMines()
	{
		int mineCount = 0;
		Random random = new Random();

		while (mineCount != totalMines)
		{
			int i = random.nextInt(blocks.length);
			int j = random.nextInt(blocks[0].length);
			if (!blocks[i][j].isMine())
			{
				blocks[i][j].setMine();
				for (int row = i - 1; row <= i + 1; row++)
				{
					for (int col = j - 1; col <= j + 1; col++)
					{
						if (row == i && col == j)
							continue; // Skip Centre
						if (row < 0 || row > gridSize.height - 1 || col < 0
								|| col > gridSize.width - 1)
							continue; // Out of Bounds
						if (!blocks[row][col].isMine())
							blocks[row][col].addMine();
					}
				}

				mineCount++;
			}
		}
	}

	public void showNeighbours(Block block)
	{
		int row = block.getRow();
		int col = block.getColumn();

		for (int i = row - 1; i <= row + 1; i++)
		{
			for (int j = col - 1; j <= col + 1; j++)
			{
				if (i == row && j == col)
					block.setText("");
				if (i < 0 || i > gridSize.height - 1 || j < 0
						|| j > gridSize.width - 1)
					continue; // Out of Bounds
				if (blocks[i][j].isEnabled())
					showBlock(blocks[i][j]);
			}
		}
	}

	public void SerializeBoard(String path) throws IOException
	{
		try (FileOutputStream fileOutputStream = new FileOutputStream(path);
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(
						fileOutputStream);)
		{
			objectOutputStream.writeObject(this);
		}
	}

	public void DeserializeBoard(String path) throws IOException,
			ClassNotFoundException
	{
		try (FileInputStream fileInputStream = new FileInputStream(path);
				ObjectInputStream objectInputStream = new ObjectInputStream(
						fileInputStream);)
		{
			Board board = (Board) objectInputStream.readObject();
			visibleBlocks = board.getVisibleBlocks();
			removeBlocks();
			for (Block[] blocks2 : board.getBlocks())
			{
				for (Block block : blocks2)
				{
					add(block);
					blocks[block.getRow()][block.getColumn()] = block;
					if (!block.isEnabled())
					{
						block.setEnabled(true);
						if (block.getType() == 0)
							block.setText("");
						else
							block.setText(Integer.toString(block.getType()));
						block.setEnabled(false);
					}
				}
			}
			repaint();
		}
	}

	public void removeBlocks()
	{
		for (int i = 0; i < gridSize.height; i++)
		{
			for (int j = 0; j < gridSize.width; j++)
			{
				remove(blocks[i][j]);
			}
		}
	}
}
