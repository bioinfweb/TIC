package info.bioinfweb.tic.demo;


import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import info.bioinfweb.tic.TICComponent;
import info.bioinfweb.tic.TICPaintEvent;



public class SierpinskiTriangleComponent extends TICComponent {	
	@Override
	public void paint(TICPaintEvent event) {
		Graphics2D g = event.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);	
		
		SierpinskiTrianglePainter.paintSierpinskiTriangle(g, getSize());
	}

	
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
