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


import info.bioinfweb.tic.scrolling.ScrollingTICComponent;
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
	private ScrollingTICComponent scrolledComponent;
	
	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param listenerSet the set of <i>TIC</i> listeners for forward events to
	 * @param scrolledComponent Optional parameter that allows to specify a scrolled component that will be the
	 *        receiver of forwarded events. If the receiver is not a scrolled component and no mouse coordinate
	 *        transformation shall be performed, {@code null} can be specified here. 
	 */
	public AbstractSWTMouseEventForwarder(TICListenerSet<L> listenerSet, ScrollingTICComponent scrolledComponent) {
		super(listenerSet);
		this.scrolledComponent = scrolledComponent;
	}


	/**
	 * Edits the x-coordinate of a mouse event according to the values of {@link DirectPaintingSWTScrollContainer#getScrollOffsetX()}
	 * if the owner of the listener set implements {@link DirectPaintingSWTScrollContainer}. Otherwise the value remains unchanged. 
	 * 
	 * @param x the x-coordinate to be edited
	 * @return the transformed x-coordinate
	 */
	protected int transformMouseX(int x) {
		if (scrolledComponent != null) {
			x += scrolledComponent.getScrollOffsetX();  // Offset is <= 0.
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
		if (scrolledComponent != null) {
			y += scrolledComponent.getScrollOffsetY();  // Offset is <= 0.
		}
		return y;
	}
}
