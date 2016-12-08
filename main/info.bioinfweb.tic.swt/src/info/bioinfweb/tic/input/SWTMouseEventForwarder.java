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

import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;



/**
 * Contains a list of {@link TICMouseListener}s and delegates all Swing and SWT events to the entries in this list.
 * <p>
 * <b>Warning:</b> This class in meant for internal use by {@link TICComponent} and is not guaranteed to have an unchanged
 * API in future releases with the same major version number.
 * 
 * @author Ben St&ouml;ver
 */
public class SWTMouseEventForwarder extends AbstractEventForwarder<TICMouseListener> 
		implements MouseListener, MouseMoveListener, MouseTrackListener {
	
	private int lastPressedButton = 0;
	
	
	public SWTMouseEventForwarder(TICListenerSet<TICMouseListener> listenerSet) {
		super(listenerSet);
	}


	private TICMouseEvent createEvent(TICComponent source, MouseEvent swtEvent) {
		return new TICMouseEvent(source, SWTSwingEventConversionTools.convertSWTEventTime(swtEvent.time), 
				SWTSwingEventConversionTools.convertSWTStateMask(swtEvent.stateMask, swtEvent.button), 
				swtEvent.button, swtEvent.count, false, swtEvent.x, swtEvent.y);
		//TODO Determine popup trigger
	}
	
	
	@Override
	public void mouseEnter(MouseEvent event) {
		for (TICMouseListener listener: getListenerSet().getListeners()) {
			listener.mouseEntered(createEvent(getListenerSet().getOwner(), event));
		}
	}


	@Override
	public void mouseExit(MouseEvent event) {
		for (TICMouseListener listener: getListenerSet().getListeners()) {
			listener.mouseExited(createEvent(getListenerSet().getOwner(), event));
		}
	}


	@Override
	public void mouseHover(MouseEvent event) {}  // Event not present in TICMouseListener


	@Override
	public void mouseMove(MouseEvent event) {
		if (lastPressedButton > 0) {
			for (TICMouseListener listener: getListenerSet().getListeners()) {
				listener.mouseDragged(createEvent(getListenerSet().getOwner(), event));
			}
		}
		else {
			for (TICMouseListener listener: getListenerSet().getListeners()) {
				listener.mouseMoved(createEvent(getListenerSet().getOwner(), event));
			}
		}
	}


	@Override
	public void mouseDoubleClick(MouseEvent event) {}  // Event not present in TICMouseListener


	@Override
	public void mouseDown(MouseEvent event) {
		lastPressedButton = event.button;
		for (TICMouseListener listener: getListenerSet().getListeners()) {
			listener.mousePressed(createEvent(getListenerSet().getOwner(), event));
		}
	}


	@Override
	public void mouseUp(MouseEvent event) {
		lastPressedButton = 0;
		for (TICMouseListener listener: getListenerSet().getListeners()) {
			listener.mouseReleased(createEvent(getListenerSet().getOwner(), event));
		}
	}
}
