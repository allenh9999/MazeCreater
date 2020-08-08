/** 
 * @author Allen Ho
 *
 */
// Importing the JFrame for the class
import javax.swing.JFrame;
// for the user inputs at the beginning
import java.util.Scanner;
// This is the main class that will run the program
public class MazeViewer {
	// main function
	public static void main(String[] args) {
		// creates a JFrame
		JFrame frame = new JFrame();
		// asks users for the dimensions
		Scanner in = new Scanner(System.in);
		System.out.println("Length?");
		int length = in.nextInt();
		System.out.println("Width?");
		int width = in.nextInt();
		/* System.out.println("Size?");
		 * int size = in.nextInt();
		 */
		int size = 5;
		in.close();
		// if the dimensions are impossible to make
		if(length <= 0 || width <= 0 || size <= 0) {
			System.err.println("Error: undefined map");
			return;
		} // if
		// set the frame title
		frame.setTitle("maze");
		// make the frame close when the red x button is pressed
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// create the maze
		MazeCreater maze = new MazeCreater(width, length, System.currentTimeMillis(), size);
		maze.createMaze();
		frame.add(maze);
		// set the frame dimensions
		frame.pack();
		frame.setVisible(true);
	} // main()
} // MazeViewer
