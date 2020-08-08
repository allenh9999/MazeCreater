// import the graphics
import java.awt.*;
// import the JComponent stuff
import javax.swing.JComponent;

// Allows for all of the warnings
import javax.swing.JOptionPane;

// import the data structures
import java.util.ArrayList;
import java.util.Stack;

// import the random generator
import java.util.Random;

// the class that creates the maze
// This is meant for testing purposes
public class MazeCreater extends JComponent {
	
	// for the location of the JComponent
	private static final long serialVersionUID = 1L;
	
	// An enumerator to keep track of directions
	private enum Pos {Empty, None, Up, Down, Left, Right};
	
	// the array that stores the maze
	private Pos[][] maze;
	
	// the variables that keep track of length, width and size
	private int length, width, size;
	
	// the seed used for the random generator
	private long seed;
	
	// a class used to create a pair of ints
	private class Pair {
		// the ints to store together
		public int first;
		public int second;
		/**
		 * Initializes the class with the two integers
		 * @param first the first int to be initialized
		 * @param second the second int to be initialized
		 */
		public Pair(int first, int second) {
			this.first = first;
			this.second = second;
		} // Pair()
		// initializes the class with the default int values
		public Pair() {
			this.first = this.second = 0;
		} // Pair()
	} // Pair
	
	// the variable used to store the location of the player object
	private Pair player = new Pair();
	
	/**
	 * Initializes the class with the parameters given
	 * @param length the length to be used for the board
	 * @param width the width to be used for the board
	 * @param seed the seed used for the random generator
	 * @param size the size of the individual block lengths
	 */
	public MazeCreater(int length, int width, long seed, int size) {
		// Initializes all of the private variables with the values given
		this.length = length;
		this.width = width;
		this.seed = seed;
		this.size = size;
		// creates a new maze and initializes it with empty tiles
		maze = new Pos[length][width];
		for(int i = 0; i < maze.length; ++i) {
			for(int j = 0; j < maze[i].length; ++j) {
				maze[i][j] = Pos.Empty;
			} // for
		} // for
	} // MazeCreater()
	
	// generates the graphics for the maze
	public void paintComponent(Graphics g) {
		// a variable made for speed
		int doubleSize = size * 2;
		// make the maze
		Graphics2D g2 = (Graphics2D) g;
		// first fill the background
		g2.setColor(Color.BLACK);
		g2.fill(new Rectangle(0, 0, length * doubleSize + size, width * doubleSize + size));
		this.setBounds(new Rectangle(0, 0, length * doubleSize + size, width * doubleSize + size));
		// now fill in all the paths
		g2.setColor(Color.WHITE);
		for(int j = 0; j < maze[0].length; ++j) {
			for(int i = 0; i < maze.length; ++i) {
				switch(maze[i][j]) {
				case Left:
					g2.fill(new Rectangle(doubleSize * i - size, doubleSize * j + size, doubleSize + size, size));
					break;
				case Right:
					g2.fill(new Rectangle(doubleSize * i + size, doubleSize * j + size, doubleSize + size, size));
					break;
				case Down:
					g2.fill(new Rectangle(doubleSize * i + size, doubleSize * j + size, size, doubleSize + size));
					break;
				default:
					g2.fill(new Rectangle(doubleSize * i + size, doubleSize * j - size, size, doubleSize + size));
					break;
				} // switch
			} // for
		} // for
		// sets the starting point
		g2.setColor(Color.RED);
		g2.fill(new Rectangle(doubleSize * player.second + size, doubleSize * player.first + size, size, size));
		//g2.fill(new Rectangle(size, 0, size, size));
		g2.setColor(Color.BLUE);
		g2.fill(new Rectangle(length * doubleSize - size, width * doubleSize - size, size, size));
	} // paintComponent()
	
	// creates the maze
	public void createMaze() {
		// Sets the new generator with the seed
		Random generator = new Random();
		generator.setSeed(seed);
		// Creates the stack for unexplored paths
		Stack<Pair> path = new Stack<Pair>();
		path.add(new Pair());
		// run until the stack is empty (every tile has been visited)
		while(!path.empty()) {
			// get the first element
			Pair cell = path.pop();
			// Find all of the neighbors around the cell
			ArrayList<Pos> neighbors = findNeighbors(cell);
			// run until there are no more neighbors
			while(!neighbors.isEmpty()) {
				// get a random index for the neighbors
				int index = Math.abs(generator.nextInt()) % neighbors.size();
				// checks to make sure that the parent cell is not empty
				if(maze[cell.first][cell.second] == Pos.Empty) maze[cell.first][cell.second]= neighbors.get(index);
				// find the position of the given index
				switch(neighbors.get(index)) {
				case Left:
					maze[cell.first - 1][cell.second] = Pos.Right;
					path.push(new Pair(cell.first - 1, cell.second));
					break;
				case Right:
					maze[cell.first + 1][cell.second] = Pos.Left;
					path.push(new Pair(cell.first + 1, cell.second));
					break;
				case Up:
					maze[cell.first][cell.second - 1] = Pos.Down;
					path.push(new Pair(cell.first, cell.second - 1));
					break;
				default:
					maze[cell.first][cell.second + 1] = Pos.Up;
					path.push(new Pair(cell.first, cell.second + 1));
					break;
				} // switch
				// Remove the index given
				neighbors.remove(index);
			} // while
		} // while
	} // createMaze()
	
	/**
	 * Finds all of the empty neighbors of the cell given
	 * @param cell the cell to find all the empty neighbors in
	 * @return all the empty neighbors of the cell
	 */
	ArrayList<Pos> findNeighbors(Pair cell) {
		// creates the ArrayList to store the result in
		ArrayList<Pos> result = new ArrayList<Pos>();
		// check left
		if(cell.first != 0 && maze[cell.first - 1][cell.second] == Pos.Empty) {
			result.add(Pos.Left);
		} // if
		// check right
		if(cell.first != length - 1 && maze[cell.first + 1][cell.second] == Pos.Empty) {
			result.add(Pos.Right);
		} // if
		// check up
		if(cell.second != 0 && maze[cell.first][cell.second - 1] == Pos.Empty) {
			result.add(Pos.Up);
		} // if
		// check down
		if(cell.second != width - 1 && maze[cell.first][cell.second + 1] == Pos.Empty) {
			result.add(Pos.Down);
		} // if
		return result;
	} // findNeighbors()
	
	// moves the player in the direction specified
	public void movePiece(int key) {
		// up
		if(key == 38 && player.first != 0 && (maze[player.second][player.first] == Pos.Up || maze[player.second][player.first - 1] == Pos.Down)) {
			--player.first;
		} // if
		// down
		if(key == 40 && player.first != maze[0].length - 1 && (maze[player.second][player.first] == Pos.Down || maze[player.second][player.first + 1] == Pos.Up)) {
			++player.first;
		} // if
		// left
		if(key == 37 && player.second != 0 && (maze[player.second][player.first] == Pos.Left || maze[player.second - 1][player.first] == Pos.Right)) {
			--player.second;
		} // if
		// right
		if(key == 39 && player.second != maze.length  - 1 && (maze[player.second][player.first] == Pos.Right || maze[player.second + 1][player.first] == Pos.Left)) {
			++player.second;
		} // if
		// sees if the new location is equal to the end
		if(player.first == maze[0].length - 1 && player.second == maze.length - 1) {
			Object[] options = {"Reset","New Maze"};
			Object value = JOptionPane.showInputDialog(null, "Congrats! You finished the maze! Choose whether you want to solve a new maze or reset this maze", "Congrats!", JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
			player = new Pair();
			if(value.equals("New Maze")) {
				seed = System.currentTimeMillis();
				// initializes a new maze layout
				maze = new Pos[length][width];
				for(int i = 0; i < maze.length; ++i) {
					for(int j = 0; j < maze[i].length; ++j) {
						maze[i][j] = Pos.Empty;
					} // for
				} // for
				// create the maze
				createMaze();
			} // if
		} // if
		// updates the frame to represent the new position
		repaint();
	} // movePiece()
	
	// makes a new maze
	public void newMaze() {
		int ans = JOptionPane.showConfirmDialog(null,"Warning: this option will reset all of your progress. Continue?", "Reset", JOptionPane.YES_NO_OPTION);
		if(ans == JOptionPane.NO_OPTION) {
			length = maze.length;
			width = maze[0].length;
			return; 
		}
		seed = System.currentTimeMillis();
		// initializes a new maze layout
		maze = new Pos[length][width];
		for(int i = 0; i < maze.length; ++i) {
			for(int j = 0; j < maze[i].length; ++j) {
				maze[i][j] = Pos.Empty;
			} // for
		} // for
		// resets the player position
		player = new Pair();
		// creates the maze
		createMaze();
		// repaint the item
		repaint();
	} // newMaze()
	
	// resize the height
	public void resizeHeight(int height) {
		if(height == width) return;
		if(height > 100 || height <= 0) {
			JOptionPane.showMessageDialog(null, "Error: dimensions too large/small");
			return;
		} // if
		width = height;
		newMaze();
	} // resizeHeight()
	
	// get the height
	public int height() {
		return width;
	} // height()
	
	// resize the width
	public void resizeWidth(int width) {
		if(width == length) return;
		if(width > 100 || width <= 0) {
			JOptionPane.showMessageDialog(null, "Error: dimensions too large/small");
			return;
		} // if
		length = width;
		newMaze();
	} // resizeWidth()
	
	// get the width
	public int width() {
		return length;
	} // width
	
	// resize the square size
	public void resizeSize(int size) {
		if(size == this.size) return; 
		if(size > 15 || size <= 0) {
			JOptionPane.showMessageDialog(null, "Error: dimensions too large/small");
			return;
		} // if
		this.size = size;
		repaint();
	} // resizeSize()
	
	// get the size
	public int getBlockSize() {
		return size;
	} // getBlockSize()
	
	@Override
	// Sets the height
	public int getHeight() {
		return width * size * 2 + size;
	} // getHeight()
	
	@Override
	// Sets the width
	public int getWidth() {
		return length * size * 2 + size;
	} // getWidth()
	
	@Override
	// Sets the default dimension
	public Dimension getPreferredSize() {
		return new Dimension(length * size * 2 + size, width * size * 2 + size);
	} // getPreferredSize()
	
	// Resets the player location
	public void resetPosition() {
		player = new Pair();
		repaint();
	} // resetPosition()
} // MazeCreater
