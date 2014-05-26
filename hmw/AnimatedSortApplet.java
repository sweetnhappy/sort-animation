/**
 * File name:		AnimatedSortApplet.java
 * Description:		An applet that visually demonstrates 3 sorting algorithms.
 * Class:			COMP 455 - AB1 - Winter 2010 - Rushton
 * Date Created:	2010/02/08
 * Date Modified:	2010/02/11
 * @author - Helmut Wollenberg
 */

package hmw;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JPanel;

public class AnimatedSortApplet extends JApplet implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	private HPanel animationPanel;	/* A customized JPanel on which the animation will occur. */
	private JPanel lowerPanel;		/* The lower panel containing the buttons and labels. */
	private JPanel labelPanel;		/* The label panel (Selection, Bubble, Merge Sort). */
	private JPanel buttonPanel;		/* The button panel (Shuffle, Sort). */
	private JButton shuffleButton;	/* The shuffle button. */
	private JButton sortButton;		/* The sort button. */
	private SortAnimator animator;	/* The SortAnimator that performs the sorts and animation. */

	/**
	 * The init method of the applet.
	 */
	public void init() {
		this.animator = new SortAnimator();
		
		/* Create the HPanel and pass a reference to the animator. */
		this.animationPanel = new HPanel(this.animator);
		this.animator.setPanel(this.animationPanel);
		
		/* Prepare the panels with GridLayouts (contain other components). */
		this.lowerPanel = new JPanel();
		this.lowerPanel.setLayout(new GridLayout(2, 1));
		this.labelPanel = new JPanel();
		this.labelPanel.setLayout(new GridLayout(1, 3));
		this.buttonPanel = new JPanel();
		this.buttonPanel.setLayout(new GridLayout(1, 2));
		
		/* Create the buttons and add this as an ActionListener. */
		this.shuffleButton = new JButton("Shuffle Arrays");
		this.sortButton = new JButton("Sort Arrays");
		this.shuffleButton.addActionListener(this);
		this.sortButton.addActionListener(this);
		
		/* Pass the button references to the animator. */
		this.animator.setButtons(this.shuffleButton, this.sortButton);
		
		/* Add the buttons to the button panel. */
		this.buttonPanel.add(this.shuffleButton);
		this.buttonPanel.add(this.sortButton);
		
		/* Add the sort labels to the label panel. */
		this.labelPanel.add(new Label("Selection Sort", Label.CENTER));
		this.labelPanel.add(new Label("Bubble Sort", Label.CENTER));
		this.labelPanel.add(new Label("Merge Sort", Label.CENTER));
		
		/* Add the button and label panels to the lower panel. */
		this.lowerPanel.add(this.labelPanel);
		this.lowerPanel.add(this.buttonPanel);
		
		/* Set the applet's layout and add the animation and lower panels. */
		setLayout(new BorderLayout());
		add(this.animationPanel, BorderLayout.NORTH);
		add(this.lowerPanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Handler for the button clicks.
	 * @param e	ActionEvent
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.shuffleButton) {
			this.animator.shuffleArray();
		}
		else if (e.getSource() == this.sortButton) {
			this.animator.beginSort();
		}
	}
}
