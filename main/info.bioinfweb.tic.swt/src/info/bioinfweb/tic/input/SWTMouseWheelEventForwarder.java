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


import info.bioinfweb.tic.TICComponent;
import info.bioinfweb.tic.scrolling.ScrollingTICComponent;

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;



/**
 * Contains a list of {@link TICMouseWheelListener}s and delegates all SWT events to the entries in this list.
 * <p>
 * <b>Warning:</b> This class in meant for internal use by {@link TICComponent} and is not guaranteed to have an unchanged
 * API in future releases with the same major version number.
 * 
 * @author Ben St&ouml;ver
 * @since 2.0.0
 * @bioinfweb.module info.bioinfweb.tic.swt
 */
public class SWTMouseWheelEventForwarder extends AbstractSWTMouseEventForwarder<TICMouseWheelListener> implements MouseWheelListener {
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param listenerSet the set of <i>TIC</i> listeners for forward events to
	 * @param scrolledComponent Optional parameter that allows to specify a scrolled component that will be the
	 *        receiver of forwarded events. If the receiver is not a scrolled component and no mouse coordinate
	 *        transformation shall be performed, {@code null} can be specified here. 
	 */
	public SWTMouseWheelEventForwarder(TICListenerSet<TICMouseWheelListener> listenerSet, ScrollingTICComponent scrolledComponent) {
		super(listenerSet, scrolledComponent);
	}


	private TICMouseWheelEvent createEvent(TICComponent source, MouseEvent swtEvent) {
		return new TICMouseWheelEvent(source, java.awt.event.MouseWheelEvent.MOUSE_WHEEL, 
				SWTSwingEventConversionTools.convertSWTEventTime(swtEvent.time), 
				SWTSwingEventConversionTools.convertSWTStateMask(swtEvent.stateMask, swtEvent.button), 
				swtEvent.button, swtEvent.count, false, transformMouseX(swtEvent.x), transformMouseY(swtEvent.y), 
				-swtEvent.count, -swtEvent.count);
		//TODO Determine popup trigger
	}
	
	
	@Override
	public void mouseScrolled(MouseEvent event) {
		for (TICMouseWheelListener listener: getListenerSet().getListeners()) {
			listener.mouseWheelMoved(createEvent(getListenerSet().getOwner(), event));
		}
	}
}
