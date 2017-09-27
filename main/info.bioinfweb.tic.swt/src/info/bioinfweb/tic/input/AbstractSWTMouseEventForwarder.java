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
package info.bioinfweb.tic.input;


import info.bioinfweb.tic.toolkit.ScrollingToolkitComponent;

import java.util.EventListener;



/**
 * Abstract base class for all <i>SWT</i> mouse event forwarders.
 * 
 * @author Ben St&ouml;ver
 * @since 3.0.0
 * @bioinfweb.module info.bioinfweb.tic.swt
 *
 * @param <L> the type of lister to forward events to
 */
public class AbstractSWTMouseEventForwarder<L extends EventListener> extends AbstractEventForwarder<L> {
	private boolean transformMouseCoordinates;
	
	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param listenerSet the set of <i>TIC</i> listeners for forward events to
	 * @param transformMouseCoordinates Specify {@code true} if mouse event coordinates shall be transformed
	 *        depending on the scroll position of the toolkit component of the owner of the listener set or 
	 *        {@code false} otherwise. (Specifying {@code true} only makes sense when the toolkit component 
	 *        where the returned instance is registered implements {@link ScrollingToolkitComponent}. Otherwise
	 *        the forwarder methods will throw {@link ClassCastException}s.)
	 */
	public AbstractSWTMouseEventForwarder(TICListenerSet<L> listenerSet, boolean transformMouseCoordinates) {
		super(listenerSet);
		if (transformMouseCoordinates && !(listenerSet.getOwner().getToolkitComponent() instanceof ScrollingToolkitComponent)) {
			throw new IllegalArgumentException("The toolkit component of the owner of the specified listener set must "
					+ "implement ScrollingToolkitComponent, if transformMouseCoordinates is true.");
		}
		else {
			this.transformMouseCoordinates = transformMouseCoordinates;
		}
	}


	/**
	 * Determines whether the associated toolkit component is assumed to be a {@link ScrollingToolkitComponent}
	 * and that mouse event coordinates shall be transformed depending in the scroll position. 
	 * 
	 * @return {@code true} if this instance is set to transform coordinates or {@code false} otherwise
	 */
	public boolean isTransformMouseCoordinates() {
		return transformMouseCoordinates;
	}


	/**
	 * Edits the x-coordinate of a mouse event according to the values of {@link DirectPaintingSWTScrollContainer#getScrollOffsetX()}
	 * if the owner of the listener set implements {@link DirectPaintingSWTScrollContainer}. Otherwise the value remains unchanged. 
	 * 
	 * @param x the x-coordinate to be edited
	 * @return the transformed x-coordinate
	 */
	protected int transformMouseX(int x) {
		if (isTransformMouseCoordinates()) {
			ScrollingToolkitComponent composite = (ScrollingToolkitComponent)getListenerSet().getOwner().getToolkitComponent();
			x -= composite.getIndependentComponent().getScrollOffsetX();
		}
		return x;
	}
	
	
	/**
	 * Edits the y-coordinate of a mouse event according to the values of {@link DirectPaintingSWTScrollContainer#getScrollOffsetY()}
	 * if the owner of the listener set implements {@link DirectPaintingSWTScrollContainer}. Otherwise the value remains unchanged. 
	 * 
	 * @param y the y-coordinate to be edited
	 * @return the transformed y-coordinate
	 */
	protected int transformMouseY(int y) {
		if (isTransformMouseCoordinates()) {
			ScrollingToolkitComponent composite = (ScrollingToolkitComponent)getListenerSet().getOwner().getToolkitComponent();
			y -= composite.getIndependentComponent().getScrollOffsetY();
		}
		return y;
	}
}
