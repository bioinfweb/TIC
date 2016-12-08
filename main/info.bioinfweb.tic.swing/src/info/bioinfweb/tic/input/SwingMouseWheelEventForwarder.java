/*
 * Toolkit independent components (TIC) - A Java library for creating GUI components for Swing and SWT
 * Copyright (C) 2014-2016  Ben Stöver
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

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;



/**
 * Contains a list of {@link TICMouseWheelListener}s and delegates all Swing events to the entries in this list.
 * <p>
 * <b>Warning:</b> This class in meant for internal use by {@link TICComponent} and is not guaranteed to have an unchanged
 * API in future releases with the same major version number.
 * 
 * @author Ben St&ouml;ver
 * @since 2.0.0
 */
public class SwingMouseWheelEventForwarder extends AbstractEventForwarder<TICMouseWheelListener> 
		implements MouseWheelListener {
	
	
	public SwingMouseWheelEventForwarder(TICListenerSet<TICMouseWheelListener> listenerSet) {
		super(listenerSet);
	}
	
	
	private TICMouseWheelEvent createEvent(TICComponent source, MouseWheelEvent swingEvent) {
		return new TICMouseWheelEvent(source, swingEvent.getWhen(), swingEvent.getModifiersEx(), swingEvent.getButton(), 
				swingEvent.getClickCount(), swingEvent.isPopupTrigger(), swingEvent.getX(), swingEvent.getY(),
				swingEvent.getWheelRotation(), swingEvent.getPreciseWheelRotation());
	}
	
	
	@Override
	public void mouseWheelMoved(MouseWheelEvent event) {
		boolean consumed = false;
		for (TICMouseWheelListener listener: getListenerSet().getListeners()) {
			consumed = consumed || listener.mouseWheelMoved(createEvent(getListenerSet().getOwner(), event));
		}
		forwardMouseEventToParent(event, consumed);
	}
}
