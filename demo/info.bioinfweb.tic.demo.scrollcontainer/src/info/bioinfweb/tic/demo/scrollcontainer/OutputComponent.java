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
package info.bioinfweb.tic.demo.scrollcontainer;


import info.bioinfweb.tic.TICComponent;
import info.bioinfweb.tic.TICPaintEvent;
import info.bioinfweb.tic.demo.simplecomponent.SierpinskiTrianglePainter;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;



public class OutputComponent extends TICComponent {
	public static final int WIDTH = 1000;
	public static final int HEIGHT = (int)(Math.sin(Math.PI / 3) * WIDTH);  // Set a height that fits the bounding box of the equilateral triangle.
	
	
	@Override
	public void paint(TICPaintEvent event) {
		Graphics2D g = event.getGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);	
		
		SierpinskiTrianglePainter.paintSierpinskiTriangle(g, getSize());
	}

	
	@Override
	public Dimension getSize() {
		return new Dimension(WIDTH, HEIGHT);
	}
}
