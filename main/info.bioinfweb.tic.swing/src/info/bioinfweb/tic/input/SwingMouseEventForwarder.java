/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2014-2015  Ben Stöver
 * <http://commons.bioinfweb.info/Java>
 * 
 * This file is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This file is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package info.bioinfweb.tic.input;


import info.bioinfweb.tic.TICComponent;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;



/**
 * Contains a list of {@link TICMouseListener}s and delegates all Swing events to the entries in this list.
 * <p>
 * <b>Warning:</b> This class in meant for internal use by {@link TICComponent} and is not guaranteed to have an unchanged
 * API in future releases with the same major version number.
 * 
 * @author Ben St&ouml;ver
 * @since 2.0.0
 */
public class SwingMouseEventForwarder extends AbstractEventForwarder<TICMouseListener>	
		implements MouseListener, MouseMotionListener {
	
	public SwingMouseEventForwarder(TICListenerSet<TICMouseListener> listenerSet) {
		super(listenerSet);
	}


	private TICMouseEvent createEvent(TICComponent source, MouseEvent swingEvent) {
		return new TICMouseEvent(source, swingEvent.getWhen(), swingEvent.getModifiersEx(), swingEvent.getButton(), 
				swingEvent.getClickCount(), swingEvent.isPopupTrigger(), swingEvent.getX(), swingEvent.getY());
	}


	@Override
	public void mouseDragged(MouseEvent event) {
		boolean consumed = false;
		for (TICMouseListener listener: getListenerSet().getListeners()) {
			consumed = consumed || listener.mouseDragged(createEvent(getListenerSet().getOwner(), event));
		}
		forwardMouseEventToParent(event, consumed);
	}


	@Override
	public void mouseMoved(MouseEvent event) {
		boolean consumed = false;
		for (TICMouseListener listener: getListenerSet().getListeners()) {
			consumed = consumed || listener.mouseMoved(createEvent(getListenerSet().getOwner(), event));
		}
		forwardMouseEventToParent(event, consumed);
	}


	@Override
	public void mouseClicked(MouseEvent event) {}  // Event not present in TICMouseListener


	@Override
	public void mouseEntered(MouseEvent event) {
		boolean consumed = false;
		for (TICMouseListener listener: getListenerSet().getListeners()) {
			consumed = consumed || listener.mouseEntered(createEvent(getListenerSet().getOwner(), event));
		}
		forwardMouseEventToParent(event, consumed);
	}


	@Override
	public void mouseExited(MouseEvent event) {
		boolean consumed = false;
		for (TICMouseListener listener: getListenerSet().getListeners()) {
			consumed = consumed || listener.mouseExited(createEvent(getListenerSet().getOwner(), event));
		}
		forwardMouseEventToParent(event, consumed);
	}


	@Override
	public void mousePressed(MouseEvent event) {
		boolean consumed = false;
		for (TICMouseListener listener: getListenerSet().getListeners()) {
			consumed = consumed || listener.mousePressed(createEvent(getListenerSet().getOwner(), event));
		}
		forwardMouseEventToParent(event, consumed);
	}


	@Override
	public void mouseReleased(MouseEvent event) {
		boolean consumed = false;
		for (TICMouseListener listener: getListenerSet().getListeners()) {
			consumed = consumed || listener.mouseReleased(createEvent(getListenerSet().getOwner(), event));
		}
		forwardMouseEventToParent(event, consumed);
	}
}
