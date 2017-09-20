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
package info.bioinfweb.tic.scrolling;


import info.bioinfweb.tic.toolkit.ScrollingToolkitComponent;

import java.util.EventObject;



/**
 * An object describing a scroll event of a {@link ScrollingTICComponent}. 
 * 
 * @author Ben St&ouml;ver
 * @since 3.0.0
 */
public class TICScrollEvent extends EventObject {
	private static final long serialVersionUID = 1L;


	/**
	 * Creates a new instance of this class.
	 * 
	 * @param source the source component triggering the event
	 */
	public TICScrollEvent(ScrollingToolkitComponent source) {
		super(source);
	}


	/**
	 * Returns the {@link ScrollingTICComponent} that triggered the event.
	 * <p>
	 * Use {@link ScrollingTICComponent#getScrollOffsetX()} and {@link ScrollingTICComponent#getScrollOffsetY()}
	 * to determine the new scroll position. 
	 * 
	 * @return a reference to the scroll container where the scrolling took place
	 */
	@Override
	public ScrollingToolkitComponent getSource() {
		return (ScrollingToolkitComponent)super.getSource();
	}
}
