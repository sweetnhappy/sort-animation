/**
 * File name:		HPanel.java
 * Description:		A customized JPanel used for painting the animation.
 * Class:			COMP 455 - AB1 - Winter 2010 - Rushton
 * Date Created:	2010/02/09
 * Date Modified:	2010/02/11
 * @author - Helmut Wollenberg
 */

package hmw;

import java.awt.Graphics;

import javax.swing.JPanel;

class HPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private SortAnimator animator;	/* The animator for the sorts. */
	private boolean initPanel;		/* Flag indicating whether init task(s) must be done. */
	
	/**
	 * Creates a customized JPanel that requires init task(s) and references the animator.
	 * @param animator	SortAnimator
	 */
	public HPanel(SortAnimator animator) {
		super();
		
		this.initPanel = true;
		this.animator = animator;
	}
	
	/**
	 * Overriding method to paint this HPanel.
	 * @param g	Graphics
	 */
	protected void paintComponent(Graphics g) {
		/* If this is the initial paint, set the size of the HPanel. */
		if (this.initPanel == true) {
			this.setSize(620, 350);
			this.initPanel = false;
		}

		super.paintComponent(g);

		/* Pass the Graphics2D object and paint this HPanel. */
		this.animator.setGraphics2D(g);
		this.animator.paintPanel();
	}
}