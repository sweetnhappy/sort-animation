/**
 * File name:		SortAnimator.java
 * Description:		Contains the code for the arrays to sort and the painting of the applet.
 * Class:			COMP 455 - AB1 - Winter 2010 - Rushton
 * Date Created:	2010/02/08
 * Date Modified:	2010/02/11
 * @author - Helmut Wollenberg
 */

package hmw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;

import javax.swing.JButton;

public class SortAnimator {
	private Graphics2D g2d;				/* The Graphics2D object used for painting the HPanel. */
	private Integer[][] intArrays;		/* The Integer arrays for shuffling and sorting. */
	private JButton shuffleButton,		/* A reference to the shuffle button (to enable/disable). */
					sortButton;			/* A reference to the sort button (to enable/disable). */
	protected HPanel animationPanel;	/* The HPanel for painting the visual sorts. */
	protected boolean[] sortsCompleted;	/* Flags indicating if the sorts have been completed. */
	
	/**
	 * Creates a new animator for the applet.
	 */
	public SortAnimator() {
		this.intArrays = new Integer[3][50];
		this.animationPanel = null;
		this.sortsCompleted = new boolean[3];
		
		/* Perform an initial shuffle. */
		this.performShuffle();
	}
	
	/**
	 * Sets the HPanel used for animation as it is not available
	 * until after this animator is created.
	 * @param panel	HPanel
	 */
	protected void setPanel(HPanel panel) {
		this.animationPanel = panel;
	}
	
	/**
	 * Sets the button references.
	 * @param shuffleB	JButton
	 * @param sortB		JButton
	 */
	protected void setButtons(JButton shuffleB, JButton sortB) {
		this.shuffleButton = shuffleB;
		this.sortButton = sortB;
	}
	
	/**
	 * Enables shuffling after the sorts are all complete.
	 */
	protected void enableShuffle() {
		this.shuffleButton.setEnabled(true);
	}
	
	/**
	 * Prepares and performs the shuffle; then updates the panel.
	 */
	protected void shuffleArray() {
		/* Sorts can be started if arrays shuffled. */
		this.sortButton.setEnabled(true);
		
		this.performShuffle();
		
		/* Clear the panel. */
		this.g2d.setColor(Color.WHITE);
		this.g2d.fillRect(0, 0, 620, 338);
		
		/* Draw and paint the panel's contents. */
		for (int j = 0; j < this.intArrays.length; j++) {
			this.paintLines(this.intArrays[j], j);
		}
		
		this.animationPanel.repaint();
	}
	
	/**
	 * Initiate the sort task threads.
	 */
	protected void beginSort() {
		/* Make sure the sorts are not interrupted by button clicks. */
		this.shuffleButton.setEnabled(false);
		this.sortButton.setEnabled(false);
		
		/* Create the sort tasks and threads arrays. */
		Runnable[] sortTasks = new Runnable[3];
		Thread[] sortThreads = new Thread[sortTasks.length];
		
		/* Create the threads and start them. */
		for (int i = 0; i < sortTasks.length; i++) {
			sortTasks[i] = new SortTask(this, this.intArrays[i], i);
			
			sortThreads[i] = new Thread(sortTasks[i]);
			
			sortThreads[i].start();
		}
	}
	
	/**
	 * Sets the Graphics2D object (casts the Graphics object) for painting.
	 * @param g	Graphics
	 */
	protected void setGraphics2D(Graphics g) {
		this.g2d = (Graphics2D) g;
	}
	
	/**
	 * Paints the panel in its entirety.
	 */
	protected void paintPanel() {
		/* Clear the panel. */
		this.g2d.setColor(Color.WHITE);
		this.g2d.fillRect(0, 0, 620, 340);
		
		/* Set the points from which the black lines at the bottom of each "graph". */
		Point[] linePoints = new Point[6];
		linePoints[0] = new Point(6, 340);
		linePoints[1] = new Point(204, 340);
		linePoints[2] = new Point(211, 340);
		linePoints[3] = new Point(409, 340);
		linePoints[4] = new Point(416, 340);
		linePoints[5] = new Point(614, 340);
		
		/* Draw the black lines. */
		this.g2d.setColor(Color.BLACK);
		this.g2d.setStroke(new BasicStroke(0.5f));
		this.g2d.draw(new Line2D.Float(linePoints[0], linePoints[1]));
		this.g2d.draw(new Line2D.Float(linePoints[2], linePoints[3]));
		this.g2d.draw(new Line2D.Float(linePoints[4], linePoints[5]));
		
		/* Paint the "graph's" bars (lines). */
		for (int j = 0; j < this.intArrays.length; j++) {
			this.paintLines(this.intArrays[j], j);
		}
	}
	
	/**
	 * Paints the Integer arrays as a series of bars (lines) on the panel.
	 * @param array		Integer[]
	 * @param arrayID	int
	 */
	protected void paintLines(Integer[] array, int arrayID) {
		int xOffset = 0;
		
		/* Change the offset at which to start drawing the bars based on the array
		 * being painted.  Also change the colour for each array's "graph". */
		if (arrayID == 0) { xOffset = 8; this.g2d.setColor(Color.GREEN); }
		else if (arrayID == 1) { xOffset = 213; this.g2d.setColor(Color.MAGENTA); }
		else if (arrayID == 2) { xOffset = 418; this.g2d.setColor(Color.BLUE); }

		/* Draw the array's "graph" bars (lines). */
		for (int i = 0; i < array.length; i++) {
			this.g2d.setStroke(new BasicStroke(4.0f));

			Point[] linePoints = new Point[2];
			linePoints[0] = new Point((xOffset + (i * 4)), 337);
			linePoints[1] = new Point((xOffset + (i * 4)), (337 - (array[i].intValue() * 6)));

			this.g2d.draw(new Line2D.Float(linePoints[0], linePoints[1]));
		}
	}
	
	/**
	 * Shuffle the Integer array and duplicate the array for each sort.
	 */
	private void performShuffle() {
		/* Reset the sortsCompleted flags. */
		for (int i = 0; i < this.sortsCompleted.length; i++) {
			this.sortsCompleted[i] = false;
		}
		
		/* Create a new ordered Integer array. */
		for (int i = 0; i < this.intArrays[0].length; i++) {
			this.intArrays[0][i] = (i + 1);
		}
		
		/* Shuffles the array's Integers. */
		for (int i = 0; i < 100; i++) {
			int firstPosition = (int) (Math.random() * 50);
			int secondPosition = (int) (Math.random() * 50);
			
			while (firstPosition == secondPosition) {
				secondPosition = (int) (Math.random() * 50);
			}
			
			Integer temp = this.intArrays[0][firstPosition];
			this.intArrays[0][firstPosition] = this.intArrays[0][secondPosition];
			this.intArrays[0][secondPosition] = temp;
		}
		
		/* Duplicate the array (not by reference) for each sort. */
		for (int i = 0; i < this.intArrays[0].length; i++) {
			this.intArrays[1][i] = new Integer(this.intArrays[0][i]);
			this.intArrays[2][i] = new Integer(this.intArrays[0][i]);
		}
	}
}
