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
package info.bioinfweb.tic.toolkit;


import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import javax.swing.JScrollPane;



public interface ScrollingSwingToolkitComponent extends ScrollingToolkitComponent, SwingToolkitComponent {
	public JScrollPane getScrollPane();
	
	
	public default void setScrollOffset(int x, int y) {
		getScrollPane().getHorizontalScrollBar().setValue(x);
		getScrollPane().getVerticalScrollBar().setValue(y);
	}
	
	
	public default void scrollRectangleToVisible(Rectangle2D rectangle) {
		Rectangle r;
		if (rectangle instanceof Rectangle) {
			r = (Rectangle)rectangle;
		}
		else {
			r = new Rectangle((int)Math.round(rectangle.getMinX()), (int)Math.round(rectangle.getMinY()), 
					(int)Math.round(rectangle.getWidth()), (int)Math.round(rectangle.getHeight()));
		}
		getScrollPane().getViewport().scrollRectToVisible(r);
	}
	
	
	public default Rectangle getVisibleRectangle() {
		return getScrollPane().getViewport().getViewRect();
	}
}
