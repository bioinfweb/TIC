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
package info.bioinfweb.tic.toolkit.scrolling;



import info.bioinfweb.tic.TICComponent;
import info.bioinfweb.tic.toolkit.DefaultSWTComposite;

import java.awt.Dimension;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;



public class DefaultScrolledSWTComposite extends DefaultSWTComposite implements DirectScrollingSWTComposite {
	private Point origin = new Point (0, 0);
	private Set<ScrollListener> scrollListeners = new HashSet<>();
	
	
	public DefaultScrolledSWTComposite(TICComponent ticComponent, Composite parent, int style) {
		super(ticComponent, parent, style | SWT.V_SCROLL | SWT.H_SCROLL);
	
		getHorizontalBar().addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				int hSelection = getHorizontalBar().getSelection();
				int destX = -hSelection - origin.x;
				Dimension dimension = getIndependentComponent().getSize();
				scroll(destX, 0, 0, 0, dimension.width, dimension.height, false);
				origin.x = -hSelection;
				fireControlScrolled(false, true);
			}
		});

		getVerticalBar().addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				int vSelection = getVerticalBar().getSelection();
				int destY = -vSelection - origin.y;
				Dimension dimension = getIndependentComponent().getSize();
				scroll(0, destY, 0, 0, dimension.width, dimension.height, false);
				origin.y = -vSelection;
				fireControlScrolled(true, false);
			}
		});
		
		addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(Event event) {
				Dimension dimension = getIndependentComponent().getSize();
				Rectangle client = getClientArea();
				getHorizontalBar().setMaximum(dimension.width);
				getVerticalBar().setMaximum(dimension.height);
				getHorizontalBar().setThumb(Math.min (dimension.width, client.width));
				getVerticalBar().setThumb(Math.min (dimension.height, client.height));
				int hPage = dimension.width - client.width;
				int vPage = dimension.height - client.height;
				int hSelection = getHorizontalBar().getSelection ();
				int vSelection = getVerticalBar().getSelection ();
				if (hSelection >= hPage) {
					if (hPage <= 0) {
						hSelection = 0;
					}
					origin.x = -hSelection;
				}
				if (vSelection >= vPage) {
					if (vPage <= 0) {
						vSelection = 0;
					}
					origin.y = -vSelection;
				}
				redraw();  //TODO Is this really necessary?
			}
		});
	}
  
	
	@Override
	public int getScrollOffsetX() {
		return origin.x;
	}


	@Override
	public void setScrollOffsetX(int scrollOffsetX) {
		getHorizontalBar().setSelection(-scrollOffsetX);
		origin.x = scrollOffsetX;
		repaint();
		fireControlScrolled(false, true);
}


	@Override
	public int getScrollOffsetY() {
		return origin.y;
	}


	@Override
	public void setScrollOffsetY(int scrollOffsetY) {
		getVerticalBar().setSelection(-scrollOffsetY);
		origin.y = scrollOffsetY;
		repaint();
		fireControlScrolled(true, false);
	}


	@Override
	public void setScrollOffset(int scrollOffsetX, int scrollOffsetY) {
		getHorizontalBar().setSelection(-scrollOffsetX);
		getVerticalBar().setSelection(-scrollOffsetY);
		origin.x = scrollOffsetX;
		origin.y = scrollOffsetY;
		repaint();
		fireControlScrolled(true, true);
	}
	

	public Rectangle getVisibleRectangle() {
		Rectangle client = getClientArea();
		return new Rectangle(-origin.x, -origin.y, client.width, client.height);
	}

	
	public void scrollRectToVisible(Rectangle rectangle) {
		//Rectangle rectangle = new Rectangle(Math.max(0, r.x), Math.max(0, r.y), r.width, r.height);  //TODO Edit values according to upper bound or this automatically done by the scrollbars?
		Rectangle visibleRect = getVisibleRectangle();

		int x = -origin.x;  // Do not scroll
		if (rectangle.x < visibleRect.x) {
			x = rectangle.x;  // Scroll left
		}
		else if (rectangle.x + rectangle.width > visibleRect.x + visibleRect.width) {
			x = rectangle.x + rectangle.width - visibleRect.width;  // Scroll right
		}

		int y = -origin.y;  // Do not scroll
		if (rectangle.y < visibleRect.y) {
			y = rectangle.y;  // Scroll up
		}
		if (rectangle.y + rectangle.height > visibleRect.y + visibleRect.height) {
			y = rectangle.y + rectangle.height - visibleRect.height;  // Scroll down
		}

		if ((x != -origin.x) || (y != -origin.y)) {
			setScrollOffset(-x, -y);
		}
	}


	@Override
	public Set<ScrollListener> getScrollListeners() {
		return scrollListeners;
	}
	
	
	protected void fireControlScrolled(boolean verticalChange, boolean horizontalChange) {
		ScrollEvent event = new ScrollEvent(this, verticalChange, horizontalChange);
		for (ScrollListener listener : scrollListeners) {
			listener.controlScrolled(event);
		}
	}
}
