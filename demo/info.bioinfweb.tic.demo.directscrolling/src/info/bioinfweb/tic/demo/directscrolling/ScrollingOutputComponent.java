/*
 * Toolkit independent components (TIC) - A Java library for creating GUI components for Swing and SWT
 * Copyright (C) 2014-2017  Ben Stöver, Sarah Wiechers
 * <http://bioinfweb.info/TIC>
 * 
 * This file is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This file is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package info.bioinfweb.tic.demo.directscrolling;


import info.bioinfweb.tic.TICPaintEvent;
import info.bioinfweb.tic.demo.simplecomponent.SierpinskiTrianglePainter;
import info.bioinfweb.tic.scrolling.ScrollingTICComponent;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import org.eclipse.swt.custom.ScrolledComposite;



/**
 * An example implementation of a <i>TIC</i> component that paints its fixed-sized contents on scrollable
 * toolkit components. In contrast to the scroll container demo, the toolkit components associated with
 * this class perform scrolling directly without using a scroll container like {@link ScrolledComposite}.
 * 
 * @author Ben St&ouml;ver
 */
public class ScrollingOutputComponent extends ScrollingTICComponent {
	public static final int WIDTH = 1000;
	public static final int HEIGHT = (int)(Math.sin(Math.PI / 3) * WIDTH);  // Set a height that fits the bounding box of the equilateral triangle.
	
	
	/**
	 * Paints a Sierpinski triangle as an example component content.
	 * 
	 * @see info.bioinfweb.tic.TICComponent#paint(info.bioinfweb.tic.TICPaintEvent)
	 */
	@Override
	public void paint(TICPaintEvent event) {
		Graphics2D g = event.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);	
		
		SierpinskiTrianglePainter.paintSierpinskiTriangle(g, getSize());
	}

	
	/**
	 * Returns the size this component would need to paint its entire contents. Note that this size differs 
	 * (and may differ) from the actual component size (the size of the respective toolkit component),
	 * if scroll bars are used to scroll the content. 
	 * 
	 * @return the size the component contents would need to be painted
	 * @see info.bioinfweb.tic.TICComponent#getSize()
	 */
	@Override
	public Dimension getSize() {
		return new Dimension(WIDTH, HEIGHT);
	}
}
