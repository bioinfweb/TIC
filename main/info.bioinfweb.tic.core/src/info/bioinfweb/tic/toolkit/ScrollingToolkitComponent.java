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

import java.awt.Rectangle;



/**
 * Interface to be implemented by all toolkit component scroll containers associated with scrolling 
 * <i>TIC</i> components. {@link ScrollingTICComponent} delegates to {@link #setScrollOffset(int, int)}
 * and {@link #getVisibleRectangle()}to scroll its content.
 * 
 * @author Ben St&ouml;ver
 * @since 3.0.0
 */
public interface ScrollingToolkitComponent extends ToolkitComponent {
	@Override
  public ScrollingTICComponent getIndependentComponent();
	
	public void setScrollOffset(int x, int y);
	
	public Rectangle getVisibleRectangle();
}
