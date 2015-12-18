package dk.itu.oop.mineSweeper;

import java.awt.Dimension;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Main extends JFrame
{

	private static final long serialVersionUID = 1L;

	public Main()
	{
		super("Minesweeper");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		Board board = new Board();
		add(board);
		setMinimumSize(new Dimension(300,250));
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("Game");
		menuBar.add(mnFile);

		JMenuItem mnStart = new JMenuItem("Start new game");
		mnStart.addActionListener(e ->
		{
			board.removeBlocks();
			board.startGame();
		});
		mnFile.add(mnStart);

		JMenuItem mnSave = new JMenuItem("Save game");
		mnSave.addActionListener(e ->
		{
			final JFileChooser fc = new JFileChooser();
			int rVal = fc.showSaveDialog(mnSave);
			if (rVal == JFileChooser.APPROVE_OPTION)
			{
				File file = fc.getSelectedFile();
				try
				{
					board.SerializeBoard(file.getAbsolutePath());
				} catch (Exception e1)
				{
					e1.printStackTrace();
				}
			}

		});
		mnFile.add(mnSave);

		JMenuItem mnLoad = new JMenuItem("Load game");
		mnLoad.addActionListener(e ->
		{
			// Create a file chooser
			final JFileChooser fc = new JFileChooser();

			// In response to a button click:
			int returnVal = fc.showOpenDialog(mnLoad);
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				File file = fc.getSelectedFile();
				try
				{
					board.DeserializeBoard(file.getAbsolutePath());

				} catch (Exception e1)
				{
					e1.printStackTrace();
				}
			}
		});
		mnFile.add(mnLoad);

		JMenuItem mnQuit = new JMenuItem("Quit");
		mnQuit.addActionListener(e -> System.exit(0));
		mnFile.add(mnQuit);

		pack();
		setVisible(true);
	}

	public static void main(String[] args)
	{
		new Main();
	}
}
