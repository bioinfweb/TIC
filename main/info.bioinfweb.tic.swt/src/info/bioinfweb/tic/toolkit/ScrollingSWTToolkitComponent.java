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

import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.graphics.Point;



public interface ScrollingSWTToolkitComponent extends ScrollingToolkitComponent, SWTToolkitComponent {
	public ScrolledComposite getScrolledComposite();
	

	@Override
	public default void setScrollOffset(int x, int y) {
		getScrolledComposite().setOrigin(x, y);
	}
	
	
	@Override
	public default void scrollRectangleToVisible(Rectangle2D rectangle) {
		Rectangle r;
		if (rectangle instanceof Rectangle) {
			r = (Rectangle)rectangle;
		}
		else {
			r = new Rectangle((int)Math.round(rectangle.getMinX()), (int)Math.round(rectangle.getMinY()), 
					(int)Math.round(rectangle.getWidth()), (int)Math.round(rectangle.getHeight()));
		}
		
		Point origin = getScrolledComposite().getOrigin();
		Rectangle visibleRect = getVisibleRectangle();

		double x = origin.x;  // Do not scroll
		if (rectangle.getMinX() < visibleRect.getMinX()) {
			x = rectangle.getMinX();  // Scroll left
		}
		else if (rectangle.getMaxX()  > visibleRect.getMaxX()) {
			x = rectangle.getMaxX() - visibleRect.getWidth();  // Scroll right
		}

		double y = origin.y;  // Do not scroll
		if (rectangle.getMinY() < visibleRect.getMinY()) {
			y = rectangle.getMinY();  // Scroll up
		}
		if (rectangle.getMaxY() > visibleRect.getMaxY()) {
			y = rectangle.getMaxY() - visibleRect.getHeight();  // Scroll down
		}
	
		getScrolledComposite().setOrigin((int)Math.round(x), (int)Math.round(y));
	}
	
	
	@Override
	public default Rectangle getVisibleRectangle() {
		org.eclipse.swt.graphics.Rectangle clientArea = getScrolledComposite().getClientArea();
		Point origin = getScrolledComposite().getOrigin();
		return new Rectangle(origin.x, origin.y, clientArea.width, clientArea.height);
	}
}
