/**
 * File name:		SortTask.java
 * Description:		A sort task that will run in its own thread; invokes sort operations.
 * Class:			COMP 455 - AB1 - Winter 2010 - Rushton
 * Date Created:	2010/02/10
 * Date Modified:	2010/02/11
 * @author - Helmut Wollenberg
 */

package hmw;

public class SortTask implements Runnable {
	private int whichSort;			/* Flag indicating the sort performed by this task. */
	private Integer[] intArray;		/* The Integer array to be sorted by this task. */
	private Sorter<Integer> sorter;	/* The Sorter for the Integer array. */
	
	/**
	 * Constructs a SortTask object with specified parameters.
	 * @param animator	SortAnimator
	 * @param array		Integer[]
	 * @param whichSort	int
	 */
	public SortTask(SortAnimator animator, Integer[] array, int whichSort) {
		this.whichSort = whichSort;
		this.intArray = array;
		this.sorter = new Sorter<Integer>(animator);
	}
	
	/**
	 * Run method of this task (thread).
	 */
	public void run() {
		/* Is this thread sorting using the first algorithm - selection? */
		if (this.whichSort == 0) {
			this.sorter.selectionSort(intArray);
			this.sorter.animator.sortsCompleted[0] = true;
		}
		/* Is this thread sorting using the second algorithm - bubble? */
		else if (this.whichSort == 1) {
			this.sorter.bubbleSort(intArray);
			this.sorter.animator.sortsCompleted[1] = true;
		}
		/* Is this thread sorting using the third algorithm - merge? */
		else if (this.whichSort == 2) {
			this.sorter.mergeSort(intArray, 0, (intArray.length - 1));
			this.sorter.animator.sortsCompleted[2] = true;
		}
		
		/* Get the status of all the sorts. */
		boolean[] values = this.sorter.animator.sortsCompleted;
		
		/* If all sorts are completed, enable the shuffle button. */
		if ((values[0] == true) && (values[1] == true) && (values[2] == true)) {
			this.sorter.animator.enableShuffle();
		}
	}
}
