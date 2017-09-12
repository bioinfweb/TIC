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


import info.bioinfweb.tic.scrolling.ScrollingTICComponent;
import info.bioinfweb.tic.scrolling.TICScrollEvent;

import java.awt.Dimension;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;



public class DirectScrollingSWTComposite extends DefaultSWTComposite implements ScrollingToolkitComponent {
	private Point origin = new Point (0, 0);
	
	
	public DirectScrollingSWTComposite(ScrollingTICComponent ticComponent, Composite parent, int style) {
		super(ticComponent, parent, style | SWT.V_SCROLL | SWT.H_SCROLL);
	
		if (!ticComponent.hasDefinedSize()) {
			throw new IllegalArgumentException("The associated TIC component must have a defined size and must not return null for getSize() in order to be scrolled.");
		}
		
		getHorizontalBar().addListener(SWT.Selection, new Listener() {
			@Override
			public void handleEvent(Event event) {
				int hSelection = getHorizontalBar().getSelection();
				int destX = -hSelection - origin.x;
				Dimension dimension = getIndependentComponent().getSize();
				scroll(destX, 0, 0, 0, dimension.width, dimension.height, false);
				origin.x = -hSelection;
				fireControlScrolled();
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
				fireControlScrolled();
			}
		});
		
		addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(Event event) {
				Dimension dimension = getIndependentComponent().getSize();
				Rectangle client = getClientArea();
				getHorizontalBar().setMaximum(dimension.width);
				getVerticalBar().setMaximum(dimension.height);
				getHorizontalBar().setThumb(Math.min(dimension.width, client.width));
				getVerticalBar().setThumb(Math.min(dimension.height, client.height));
				int hPage = dimension.width - client.width;
				int vPage = dimension.height - client.height;
				int hSelection = getHorizontalBar().getSelection();
				int vSelection = getVerticalBar().getSelection();
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
	public ScrollingTICComponent getIndependentComponent() {
		return (ScrollingTICComponent)super.getIndependentComponent();
	}


	//TODO Refactor superclass so that the algebraic sign of these methods is identical with that of the methods in ScrolledTICCompnent, so that values do not have to be negated in getVisibleRectangle() below.
	@Override
	protected int getScrollOffsetX() {
		return origin.x;
	}


	@Override
	protected int getScrollOffsetY() {
		return origin.y;
	}


	@Override
	public void setScrollOffset(int scrollOffsetX, int scrollOffsetY) {
		getHorizontalBar().setSelection(scrollOffsetX);
		getVerticalBar().setSelection(scrollOffsetY);
		origin.x = -scrollOffsetX;
		origin.y = -scrollOffsetY;
		repaint();
		fireControlScrolled();
	}
	

	public java.awt.Rectangle getVisibleRectangle() {
		Rectangle client = getClientArea();
		return new java.awt.Rectangle(-origin.x, -origin.y, client.width, client.height);
	}


	protected void fireControlScrolled() {
		getIndependentComponent().fireControlScrolled(new TICScrollEvent(this));
	}
}
