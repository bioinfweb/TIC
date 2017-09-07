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
	public AbstractSWTMouseEventForwarder(TICListenerSet<L> listenerSet) {
		super(listenerSet);
	}
	
	
	/**
	 * Edits the x-coordinate of a mouse event according to the values of {@link DirectScrollingSWTComposite#getScrollOffsetX()}
	 * if the owner of the listener set implements {@link DirectScrollingSWTComposite}. Otherwise the value remains unchanged. 
	 * 
	 * @param x the x-coordinate to be edited
	 * @return the transformed x-coordinate
	 */
	protected int transformMouseX(int x) {
		if (getListenerSet().getOwner().getToolkitComponent() instanceof ScrollingToolkitComponent) {
			ScrollingToolkitComponent composite = (ScrollingToolkitComponent)getListenerSet().getOwner().getToolkitComponent();
			x -= (int)Math.round(composite.getVisibleRectangle().getMinX());
		}
		return x;
	}
	
	
	/**
	 * Edits the y-coordinate of a mouse event according to the values of {@link DirectScrollingSWTComposite#getScrollOffsetY()}
	 * if the owner of the listener set implements {@link DirectScrollingSWTComposite}. Otherwise the value remains unchanged. 
	 * 
	 * @param y the y-coordinate to be edited
	 * @return the transformed y-coordinate
	 */
	protected int transformMouseY(int y) {
		if (getListenerSet().getOwner().getToolkitComponent() instanceof ScrollingToolkitComponent) {
			ScrollingToolkitComponent composite = (ScrollingToolkitComponent)getListenerSet().getOwner().getToolkitComponent();
			y -= (int)Math.round(composite.getVisibleRectangle().getMinY());
		}
		return y;
	}
}
