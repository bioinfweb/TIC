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
package info.bioinfweb.tic.demo.sierpinski;


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
