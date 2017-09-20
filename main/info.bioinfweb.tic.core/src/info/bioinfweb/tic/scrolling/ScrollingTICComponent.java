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


import info.bioinfweb.tic.TICComponent;
import info.bioinfweb.tic.exception.UnsupportedToolkitException;
import info.bioinfweb.tic.toolkit.ScrollingToolkitComponent;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.HashSet;
import java.util.Set;



/**
 * All <i>TIC</i> components that contain scrolled content should be inherited from this class. It offer
 * toolkit-independent methods to control and manipulate the scroll position.
 * <p>
 * Note that {@link #getSwingComponentClassName(Object...)} and {@link #getSWTComponentClassName(Object...)}
 * must be overwritten to return names of classes that implement {@link ScrollingToolkitComponent}.
 * The default implementations of this class throw an {@link UnsupportedToolkitException}.
 * 
 * @author Ben St&ouml;ver
 * @since 3.0.0
 */
public abstract class ScrollingTICComponent extends TICComponent {
	private Set<TICScrollListener> scrollListeners = new HashSet<>();

	
	//TODO Should the factories do any checks, if the created class implements ScrollingToolkitComponent?
	@Override
	public ScrollingToolkitComponent getToolkitComponent() {
		return (ScrollingToolkitComponent)super.getToolkitComponent();
	}
	

	/**
	 * Throws an {@link UnsupportedToolkitException}. Inherited classes should overwrite this method to
	 * provide a <i>Swing</i> toolkit component that implements {@link ScrollingToolkitComponent}.
	 */
	@Override
	protected String getSwingComponentClassName(Object... parameters) {
		throw new UnsupportedToolkitException("This class does not provide any Swing toolkit component.");
	}


	/**
	 * Throws an {@link UnsupportedToolkitException}. Inherited classes should overwrite this method to
	 * provide an <i>SWT</i> toolkit component that implements {@link ScrollingToolkitComponent}.
	 */
	@Override
	protected String getSWTComponentClassName(Object... parameters) {
		throw new UnsupportedToolkitException("This class does not provide any SWT toolkit component.");
	}


	/**
	 * Sets the current painting offset on x and y. This method should be used, if both coordinates are changed, instead
	 * of calling {@link #setScrollOffsetX(int)} and {@link #setScrollOffsetY(int)} separately to avoid unnecessary
	 * repainting.
	 * 
	 * @param x the new shift of the painting coordinate origin on x
	 * @param y the new shift of the painting coordinate origin on y
	 */
	public void setScrollOffset(int x, int y) {
		getToolkitComponent().setScrollOffset(x, y);
	}
	
	
	/**
	 * Sets the current painting offset on x. If this property is e.g. set 10, the component will be painted as if
	 * it would be scrolled to the right by 10 px. (The left-most 10 px are not visible.)
	 * 
	 * @param scrollOffsetX the new shift of the painting coordinate origin on x
	 */
	public void setScrollOffsetX(int x) {
		getToolkitComponent().setScrollOffset(x, getScrollOffsetY());
	}
	//TODO Check if the doc here and in the next 3 methods is correct, or if the setted value must -10 instead of 10.
	
	
	/**
	 * Sets the current painting offset on y. If this property is e.g. set 10, the component will be painted as if
	 * it would be scrolled downwards by 10 px. (The top-most 10 px are not visible.)
	 * 
	 * @param y the new shift of the painting coordinate origin on y
	 */
	public void setScrollOffsetY(int y) {
		getToolkitComponent().setScrollOffset(getScrollOffsetX(), y);
	}
	
	
	/**
	 * Returns the current painting offset on x. If this property is e.g. set 10, the component will be painted as if
	 * it would be scrolled to the right by 10 px. (The left-most 10 px are not visible.)
	 * 
	 * @return the shift of the painting coordinate origin on x
	 */
	public int getScrollOffsetX() {
		return getVisibleRectangle().x;
	}
	
	
	/**
	 * Returns the current painting offset on y. If this property is e.g. set 10, the component will be painted as if
	 * it would be scrolled downwards by 10 px. (The top-most 10 px are not visible.)
	 * 
	 * @return the shift of the painting coordinate origin on y
	 */
	public int getScrollOffsetY() {
		return getVisibleRectangle().y;
	}
	
	
  /**
   * Scrolls the contained component so that the specified rectangle of that component is visible.
   * If the rectangle is already fully visible, no scrolling will be performed.
   *  
   * @param rectangle the rectangle that shall become fully visible
   */
	public void scrollRectangleToVisible(Rectangle2D rectangle) {
		Rectangle visibleRect = getVisibleRectangle();

		double x = getScrollOffsetX();  // Do not scroll
		if (rectangle.getMinX() < visibleRect.getMinX()) {
			x = rectangle.getMinX();  // Scroll left
		}
		else if (rectangle.getMaxX()  > visibleRect.getMaxX()) {
			x = rectangle.getMaxX() - visibleRect.getWidth();  // Scroll right
		}

		double y = getScrollOffsetY();  // Do not scroll
		if (rectangle.getMinY() < visibleRect.getMinY()) {
			y = rectangle.getMinY();  // Scroll up
		}
		if (rectangle.getMaxY() > visibleRect.getMaxY()) {
			y = rectangle.getMaxY() - visibleRect.getHeight();  // Scroll down
		}
	
		setScrollOffset((int)Math.round(x), (int)Math.round(y));
	}
	
	
	/**
	 * Returns the rectangle in the coordinate system of the scrolled component that is currently visible. 
	 * 
	 * @return the currently visible rectangle
	 */
	public Rectangle getVisibleRectangle() {
		return getToolkitComponent().getVisibleRectangle();
	}
	
	
	/**
	 * Returns an editable set of scroll listeners that will be notified by on scroll events happening 
	 * within this component.
	 * 
	 * @return the set of registered scroll listeners
	 */
	public Set<TICScrollListener> getScrollListeners() {
		return scrollListeners;
	}
	//TODO Make sure listeners are informed by toolkit components.
	
	
	/**
	 * Informs all listeners that the contained component was scrolled.
	 * <p>
	 * This method is meant to be called by scrolling toolkit components. Application will not have to call 
	 * this method directly, unless it implements custom scrolling toolkit components.  
	 * 
	 * @param event the object describing the scroll event
	 */
	public void fireControlScrolled(TICScrollEvent event) {
		for (TICScrollListener listener : scrollListeners) {
			listener.contentScrolled(event);
		}
	}
}
