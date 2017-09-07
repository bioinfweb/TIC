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


import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.Set;

import info.bioinfweb.tic.TICComponent;
import info.bioinfweb.tic.toolkit.ScrollingToolkitComponent;



public abstract class ScrollingTICComponent extends TICComponent {
	private Set<TICScrollListener> scrollListeners = new HashSet<>();

	
	//TODO Should the factories do any checks, if the created class implements ScrollingToolkitComponent?
	@Override
	public ScrollingToolkitComponent getToolkitComponent() {
		return (ScrollingToolkitComponent)super.getToolkitComponent();
	}
	
	
	public void setScrollOffset(int x, int y) {
		getToolkitComponent().setScrollOffset(x, y);
	}
	
	
	public void setScrollOffsetX(int x) {
		getToolkitComponent().setScrollOffset(x, getScrollOffsetY());
	}
	
	
	public void setScrollOffsetY(int y) {
		getToolkitComponent().setScrollOffset(getScrollOffsetX(), y);
	}
	
	
	public int getScrollOffsetX() {
		return getVisibleRectangle().x;
	}
	
	
	public int getScrollOffsetY() {
		return getVisibleRectangle().y;
	}
	
	
	public void scrollRectangleToVisible(Rectangle2D rectangle) {
		getToolkitComponent().scrollRectangleToVisible(rectangle);
	}
	
	
	public Rectangle getVisibleRectangle() {
		return getToolkitComponent().getVisibleRectangle();
	}
	
	
	public Set<TICScrollListener> getScrollListeners() {
		return scrollListeners;
	}
	//TODO Make sure listeners are informed by toolkit components.
}
