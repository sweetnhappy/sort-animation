/**
 * File name:		Sorter.java
 * Description:		Class containing the sort algorithm methods and calls to the animator
 * 					to update the applet.  Includes sleep methods to slow down animation.
 * Class:			COMP 455 - AB1 - Winter 2010 - Rushton
 * Date Created:	2010/02/08
 * Date Modified:	2010/02/11
 * @author - Helmut Wollenberg
 */

package hmw;

public class Sorter<T extends Comparable<T>> {
	private int count = 0; 				/* Counter for number of comparisons. */
	private int speedFactor;			/* Speed factor for animation. */
	protected SortAnimator animator;	/* The animator for the sorts. */
	
	/**
	 * Constructs a Sorter referencing the animator to allow sort to be animated.
	 * @param animator	SortAnimator
	 */
	public Sorter(SortAnimator animator) {
		/* 1 = fast, 4 = medium, 8 = slow; other POSITIVE values permitted. */
		this.speedFactor = 4;
		
		this.animator = animator;
	}
	
	/**
	 * Get the number of comparisons from the most recent operation.
	 * @return count int
	 */
	public int getCount() {
		return this.count;
	}
	
	/**
	 * Reset the counter of comparisons for the next operation.
	 */
	public void resetCount() {
		this.count = 0;
	}
	
	/**
	 * Sorts the specified array of objects using the merge sort algorithm.
	 * @param data T[]
	 * @param min int
	 * @param max int
	 */
	@SuppressWarnings("unchecked")
	public void mergeSort(T[] data, int min, int max) {
		T[] temp;
		int left, right;

		// Continue if list is not length one.
		if (min != max) {
			// Find the length and the midpoint of the list.
			int size = (max - min) + 1;
			int pivot = (min + max) / 2;
			temp = (T[])(new Comparable[size]);

			mergeSort(data, min, pivot);		// Sort left half of list.
			mergeSort(data, (pivot + 1), max);	// Sort right half of list.

			// Copy sorted data into work space.
			for (int i = 0; i < size; i++) {
				temp[i] = data[min + i];
			}

			// Merge the two sorted lists.
			left = 0;
			right = (pivot - min) + 1;
			for (int i = 0; i < size; i++) {
				if (right <= (max - min)) {
					if (left <= (pivot - min)) {
						if (temp[left].compareTo(temp[right]) > 0) {
							this.count++;
							
							data[i + min] = temp[right++];
						}
						else {
							data[i + min] = temp[left++];
						}
					}
					else {
						data[i + min] = temp[right++];
					}
				}
				else {
					data[i + min] = temp[left++];
				}
				
				/* Perform the animation by updating the applet's HPanel. */
				this.animator.animationPanel.repaint();
				
				try {
					Thread.sleep(this.speedFactor * 5);
				}
				catch (InterruptedException ie) {}
			}
		}
	}

	/**
	 * Sorts the specified array of objects using a bubble sort algorithm.
	 * @param data T[]
	 */
	public void bubbleSort(T[] data) {
		T temp;

		for (int p = (data.length - 1); p >= 0; p--) {
			for (int s = 0; s <= (p - 1); s++) {
				if (data[s].compareTo(data[s+1]) > 0) {
					this.count++;
					
					// Swap the values.
					temp = data[s];
					data[s] = data[s + 1];
					data[s + 1] = temp;
				}
				
				/* Perform the animation by updating the applet's HPanel. */
				this.animator.animationPanel.repaint();
				
				try {
					Thread.sleep(this.speedFactor * 2);
				}
				catch (InterruptedException ie) {}
			}
		}
	}
	
	/**
	 * Sorts the specified array of integers using the selection sort algorithm.
	 * @param data T[]
	 */
	public void selectionSort(T[] data) {
		int min;
		T temp;

		for (int i = 0; i < (data.length - 1); i++) {
			min = i;
			for (int s = (i + 1); s < data.length; s++) {
				if (data[s].compareTo(data[min]) < 0) {
					this.count++;
					
					min = s;
				}
			}

			// Swap the values.
			temp = data[min];
			data[min] = data[i];
			data[i] = temp;
			
			/* Perform the animation by updating the applet's HPanel. */
			this.animator.animationPanel.repaint();
			
			try {
				Thread.sleep(this.speedFactor * 50);
			}
			catch (InterruptedException ie) {}
		}
	}
}
