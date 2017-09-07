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



/**
 * Scrollable <i>Swing</i> components (implementing {@link ScrollingToolkitComponent}) can implement this
 * interface, if they provide scrolling functionality using a {@link JScrollPane}. It contains
 * default method implementations that delegate the scrolling methods of {@link ScrollingToolkitComponent}
 * to {@link JScrollPane}.
 * <p>
 * Note that this interface is not meant and does not tag a class to provide any functionality in addition
 * to {@link ScrollingToolkitComponent}. <i>Swing</i> components that provide scrolling functionality 
 * directly instead of relying in {@link JScrollPane} should implement 
 * {@link ScrollingToolkitComponent} directly and implement its methods respectively.
 * 
 * @author Ben St&uoml;ver
 * @since 3.0.0
 */
public interface ScrollingSwingToolkitComponent extends ScrollingToolkitComponent, SwingToolkitComponent {
	public JScrollPane getScrollPane();
	
	
	public default void setScrollOffset(int x, int y) {
		getScrollPane().getHorizontalScrollBar().setValue(x);
		getScrollPane().getVerticalScrollBar().setValue(y);
	}
	
	
	public default Rectangle getVisibleRectangle() {
		return getScrollPane().getViewport().getViewRect();
	}
}
