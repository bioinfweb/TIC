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

import java.util.Set;



public interface DirectScrollingSWTComposite {
	/**
	 * Returns the current painting offset on x. If this property is e.g. set -10, the component will be painted as if
	 * it would be scrolled to the right by 10 px. (The left-most 10 px are not visible.)
	 * 
	 * @return the shift of the painting coordinate origin on x
	 */
	public int getScrollOffsetX();
	
	/**
	 * Sets the current painting offset on x. If this property is e.g. set -10, the component will be painted as if
	 * it would be scrolled to the right by 10 px. (The left-most 10 px are not visible.)
	 * 
	 * @param scrollOffsetX the new shift of the painting coordinate origin on x
	 */
	public void setScrollOffsetX(int scrollOffsetX);
	
	/**
	 * Returns the current painting offset on y. If this property is e.g. set -10, the component will be painted as if
	 * it would be scrolled downwards by 10 px. (The top-most 10 px are not visible.)
	 * 
	 * @return the shift of the painting coordinate origin on y
	 */
	public int getScrollOffsetY();
	
	/**
	 * Sets the current painting offset on y. If this property is e.g. set -10, the component will be painted as if
	 * it would be scrolled downwards by 10 px. (The top-most 10 px are not visible.)
	 * 
	 * @param scrollOffsetY the new shift of the painting coordinate origin on y
	 */
	public void setScrollOffsetY(int scrollOffsetY);
	
	/**
	 * Sets the current painting offset on x and y. This method should be used, if both coordinates are changed, instead
	 * of calling {@link #setScrollOffsetX(int)} and {@link #setScrollOffsetY(int)} separately to avoid unnecessary
	 * repainting.
	 * 
	 * @param scrollOffsetX the new shift of the painting coordinate origin on x
	 * @param scrollOffsetY the new shift of the painting coordinate origin on y
	 */
	public void setScrollOffset(int scrollOffsetX, int scrollOffsetY);
	
	public Set<ScrollListener> getScrollListeners();
}