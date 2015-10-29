package info.bioinfweb.tic.demo;


import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import info.bioinfweb.tic.TICComponent;
import info.bioinfweb.tic.TICPaintEvent;


/**
 * Example TIC component in which a Sierpinski triangle is painted.
 * <p>
 * This example is used in the 
 * <a href="http://bioinfweb.info/TIC/Documentation">TIC documentation</a>.
 * 
 * @author Sarah Wiechers
 * @author Ben St&ouml;ver
 */
public class SierpinskiTriangleComponent extends TICComponent {
	
	
	/**
	 * In this method the Sierpinski triangle is painted.
	 */
	@Override
	public void paint(TICPaintEvent event) {
		Graphics2D g = event.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);	
		
		SierpinskiTrianglePainter.paintSierpinskiTriangle(g, getSize());
	}
	

	/**
	 * Returns the current size of the underlying toolkit specific component. This allows the toolkit-specific layout 
	 * manager to determine the size of this TIC component.
	 * 
	 * @see info.bioinfweb.tic.TICComponent#getSize()
	 */
	@Override
  public Dimension getSize() {
	 if (hasToolkitComponent()) {
		 	return getToolkitComponent().getToolkitSize();
	 }
	 else {
		 	return new Dimension(0, 0);
	 }
  }
}
