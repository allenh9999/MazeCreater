/** 
 * @author Allen Ho
 */

// allows for menu inputs
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// allows for keyboard inputs
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

// Importing the JFrame for the class
import javax.swing.JFrame;

// Allows for the menu bar on the top
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

// Allows for all of the warnings
import javax.swing.JOptionPane;

// this is the main class that will run the program
public class MazeMover extends JFrame implements KeyListener {
	// For the location of the class
	private static final long serialVersionUID = 1L;
	
	// the default variables for the maze creater
	int length = 20, width = 20, size = 5;
	long seed = System.currentTimeMillis();
	
	// the MazeCreater object
	MazeCreater maze = new MazeCreater(width, length, seed, size);
	
	// main function
	public static void main(String[] args) {
		// Creates a JFrame 
		MazeMover frame = new MazeMover();
		// set the frame title
		frame.setTitle("maze");
		// make the frame close when the red x button is pressed
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		//frame.setSize(frame.width * frame.size * 2 + frame.size + 16, frame.length * frame.size * 2 + frame.size + 39);
		frame.setVisible(true);
		frame.pack();
		// Show the instructions
		JOptionPane.showMessageDialog(null, "Use the arrow keys to move the red square into the blue target", "Instructions", JOptionPane.INFORMATION_MESSAGE);
	} // main
	
	// creates a new JFrame
	public MazeMover() {
		addKeyListener(this);
		// adds the menu bar to the JFrame
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		menuBar.add(createFileMenu());
		menuBar.add(createDimensionsMenu());
		// creates the maze
		maze.createMaze();
		this.add(maze);
	} // MazeMover()
	
	@Override
	// When the key is pressed
	public void keyPressed(KeyEvent e) { } // keyPressed()
	
	@Override
	// When the key is released
	public void keyReleased(KeyEvent e) {
		maze.movePiece(e.getKeyCode());
	} // keyReleased()
	
	@Override
	public void keyTyped(KeyEvent e) { } // keyTyped()
	
	// Creates the dimensions menu bar
	private JMenu createDimensionsMenu() {
		JMenu menu = new JMenu("Dimensions");
		menu.add(createHeightMenu());
		menu.add(createWidthMenu());
		menu.add(createSizeMenu());
		return menu;
	} // createDimensionsMenu()
	
	// Creates the height menu bar
	private JMenu createHeightMenu() {
		JMenu menu = new JMenu("Height");
		menu.add(increaseHeightMenu());
		menu.add(decreaseHeightMenu());
		menu.add(inputHeightMenu());
		return menu;
	} // createHeightMenu()
	
	// Creates the increment height menu bar
	private JMenuItem increaseHeightMenu() {
		class ItemListener implements ActionListener {
			private MazeCreater maze;
			private MazeMover mover;
			public ItemListener(MazeCreater maze, MazeMover mover) {
				this.maze = maze;
				this.mover = mover;
			} // ItemListener()
			public void actionPerformed(ActionEvent event) {
				maze.resizeHeight(maze.height() + 1);
				mover.resetFrame();
			} // actionPerformed()
		} // ItemListener
		JMenuItem item = new JMenuItem("Increase");
		item.addActionListener(new ItemListener(maze,this));
		return item;
	} // increaseHeightMenu()
	
	// Creates the decrease height menu bar
	private JMenuItem decreaseHeightMenu() {
		class ItemListener implements ActionListener {
			private MazeCreater maze;
			private MazeMover mover;
			public ItemListener(MazeCreater maze, MazeMover mover) {
				this.maze = maze;
				this.mover = mover;
			} // ItemListener()
			public void actionPerformed(ActionEvent event) {
				maze.resizeHeight(maze.height() - 1);
				mover.resetFrame();
			} // actionPerformed()
		} // ItemListener
		JMenuItem item = new JMenuItem("Decrease");
		ActionListener listener = new ItemListener(maze, this);
		item.addActionListener(listener);
		return item;
	} // decreaseHeightMenu()
	
	// Creates the input height menu bar
	private JMenuItem inputHeightMenu() {
		class ItemListener implements ActionListener {
			private MazeCreater maze;
			private MazeMover mover;
			public ItemListener(MazeCreater maze, MazeMover mover) {
				this.maze = maze;
				this.mover = mover;
			} // ItemListener()
			public void actionPerformed(ActionEvent event) {
				try {
					int ans = Integer.parseInt(
							JOptionPane.showInputDialog(null,"Input new height"));
					maze.resizeHeight(ans);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,"Error: impossible height");
				} // catch
				mover.resetFrame();
			} // actionPerformed()
		} // ItemListener
		JMenuItem item = new JMenuItem("Input");
		item.addActionListener(new ItemListener(maze, this));
		return item;
	} // inputHeightMenu()
	
	// Creates the width menu bar
	private JMenu createWidthMenu() {
		JMenu menu = new JMenu("Width");
		menu.add(increaseWidthMenu());
		menu.add(decreaseWidthMenu());
		menu.add(inputWidthMenu());
		return menu;
	} // createWidthMenu()
	
	// Creates the increase width menu bar
	private JMenuItem increaseWidthMenu() {
		class ItemListener implements ActionListener {
			private MazeCreater maze;
			private MazeMover mover;
			public ItemListener(MazeCreater maze, MazeMover mover) {
				this.maze = maze;
				this.mover = mover;
			} // ItemListener()
			public void actionPerformed(ActionEvent event) {
				maze.resizeWidth(maze.width() + 1);
				mover.resetFrame();
			} // actionPerformed()
		} // ItemListener
		JMenuItem item = new JMenuItem("Increase");
		ActionListener listener = new ItemListener(maze, this);
		item.addActionListener(listener);
		return item;
	} // increaseWidthMenu()
	
	// Creates the decrease width menu bar
	private JMenuItem decreaseWidthMenu() {
		class ItemListener implements ActionListener {
			private MazeCreater maze;
			private MazeMover mover;
			public ItemListener(MazeCreater maze, MazeMover mover) {
				this.maze = maze;
				this.mover = mover;
			} // ItemListener()
			public void actionPerformed(ActionEvent event) {
				maze.resizeWidth(maze.width() - 1);
				mover.resetFrame();
			} // actionPerformed()
		} // ItemListener
		JMenuItem item = new JMenuItem("Decrease");
		ActionListener listener = new ItemListener(maze, this);
		item.addActionListener(listener);
		return item;
	} // decreaseWidthMenu()
	
	// Creates the input width menu bar
	private JMenuItem inputWidthMenu() {
		class ItemListener implements ActionListener {
			private MazeCreater maze;
			private MazeMover mover;
			public ItemListener(MazeCreater maze, MazeMover mover) {
				this.maze = maze;
				this.mover = mover;
			} // ItemListener()
			public void actionPerformed(ActionEvent event) {
				try {
					int ans = Integer.parseInt(
							JOptionPane.showInputDialog(null,"Input new width"));
					maze.resizeWidth(ans);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,"Error: impossible width");
				} // catch
				mover.resetFrame();
			} // actionPerformed()
		} // ItemListener
		JMenuItem item = new JMenuItem("Input");
		item.addActionListener(new ItemListener(maze, this));
		return item;
	} // inputWidthMenu()
	
	// Creates the size menu bar
	private JMenu createSizeMenu() {
		JMenu menu = new JMenu("Block Size");
		menu.add(increaseSizeMenu());
		menu.add(decreaseSizeMenu());
		menu.add(inputSizeMenu());
		return menu;
	} // createSizeMenu()
	
	// Creates the increase width menu bar
	private JMenuItem increaseSizeMenu() {
		class ItemListener implements ActionListener {
			private MazeCreater maze;
			private MazeMover mover;
			public ItemListener(MazeCreater maze, MazeMover mover) {
				this.maze = maze;
				this.mover = mover;
			} // ItemListener()
			public void actionPerformed(ActionEvent event) {
				maze.resizeSize(maze.getBlockSize() + 1);
				mover.resetFrame();
			} // actionPerformed()
		} // ItemListener
		JMenuItem item = new JMenuItem("Increase");
		ActionListener listener = new ItemListener(maze, this);
		item.addActionListener(listener);
		return item;
	} // increaseSizeMenu()
	
	// Creates the decrease width menu bar
	private JMenuItem decreaseSizeMenu() {
		class ItemListener implements ActionListener {
			private MazeCreater maze;
			private MazeMover mover;
			public ItemListener(MazeCreater maze, MazeMover mover) {
				this.maze = maze;
				this.mover = mover;
			} // ItemListener()
			public void actionPerformed(ActionEvent event) {
				maze.resizeSize(maze.getBlockSize() - 1);
				mover.resetFrame();
			} // actionPerformed()
		} // ItemListener
		JMenuItem item = new JMenuItem("Decrease");
		ActionListener listener = new ItemListener(maze, this);
		item.addActionListener(listener);
		return item;
	} // decreaseSizeMenu()
	
	// Creates the input width menu bar
	private JMenuItem inputSizeMenu() {
		class ItemListener implements ActionListener {
			private MazeCreater maze;
			private MazeMover mover;
			public ItemListener(MazeCreater maze, MazeMover mover) {
				this.maze = maze;
				this.mover = mover;
			} // ItemListener()
			public void actionPerformed(ActionEvent event) {
				try {
					int ans = Integer.parseInt(
							JOptionPane.showInputDialog(null,"Input new block size"));
					maze.resizeSize(ans);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null,"Error: impossible block size");
				} // catch
				mover.resetFrame();
			} // actionPerformed()
		} // ItemListener
		JMenuItem item = new JMenuItem("Input");
		item.addActionListener(new ItemListener(maze, this));
		return item;
	} // inputWidthMenu()
	
	// Creates the File menu bar
	private JMenu createFileMenu() {
		JMenu menu = new JMenu("File");
		menu.add(resetPositionMenu());
		menu.add(newMazeMenu());
		menu.add(exitMenu());
		return menu;
	} // createFileMenu()
	
	// Resets the player position
	private JMenuItem resetPositionMenu() {
		class ItemListener implements ActionListener {
			private MazeCreater maze;
			public ItemListener(MazeCreater maze) {
				this.maze = maze;
			} // ItemListener()
			public void actionPerformed(ActionEvent event) {
				int ans = JOptionPane.showConfirmDialog(null,"Warning: this option will reset all of your progress. Continue?", "Reset", JOptionPane.YES_NO_OPTION);
				if(ans == JOptionPane.NO_OPTION) return;
				maze.resetPosition();
			} // actionPerformed()
		} // ItemListener
		JMenuItem item = new JMenuItem("Reset");
		item.addActionListener(new ItemListener(maze));
		return item;
	} // resetPositionMenu()
	
	// Creates a new maze
	private JMenuItem newMazeMenu() {
		class ItemListener implements ActionListener {
			private MazeCreater maze;
			public ItemListener(MazeCreater maze) {
				this.maze = maze;
			} // ItemListener()
			public void actionPerformed(ActionEvent event) {
				maze.newMaze();
			} // actionPerformed()
		} // ItemListener
		JMenuItem item = new JMenuItem("New Maze");
		item.addActionListener(new ItemListener(maze));
		return item;
	} // newMazeMenu()
	
	// Creates the exit menu bar
	private JMenuItem exitMenu() {
		class ItemListener implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				int ans = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?", "Exit", JOptionPane.YES_NO_OPTION);
				if(ans == JOptionPane.YES_OPTION) {
					System.exit(0);
				} // if
			} // actionPerformed()
		} // ItemListener
		JMenuItem item = new JMenuItem("Exit");
		item.addActionListener(new ItemListener());
		return item;
	} // exitMenu()
	
	// Resets the frame
	public void resetFrame() {
		pack();
	} // resetFrame()
} // MazeMover
