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
import info.bioinfweb.tic.toolkit.ScrollingToolkitComponent;

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
 * @bioinfweb.module info.bioinfweb.tic.swt
 */
public class SWTMouseEventForwarder extends AbstractSWTMouseEventForwarder<TICMouseListener> 
		implements MouseListener, MouseMoveListener, MouseTrackListener {
	
	private int lastPressedButton = 0;
	
	
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param listenerSet the set of <i>TIC</i> listeners for forward events to
	 * @param transformMouseCoordinates Specify {@code true} if mouse event coordinates shall be transformed
	 *        depending on the scroll position of the toolkit component of the owner of the listener set or 
	 *        {@code false} otherwise. (Specifying {@code true} only makes sense when working with scrolling 
	 *        components.)
	 * @throws IllegalArgumentException if {@code transformMouseCoordinates = true} was specified and the 
	 *         toolkit component of the owner of {@code listenerSet} does not implement 
	 *         {@link ScrollingToolkitComponent}. 
	 */
	public SWTMouseEventForwarder(TICListenerSet<TICMouseListener> listenerSet, boolean transformMouseCoordinates) {
		super(listenerSet, transformMouseCoordinates);
	}


	private TICMouseEvent createEvent(TICComponent source, int id, MouseEvent swtEvent) {
		return new TICMouseEvent(source, id, SWTSwingEventConversionTools.convertSWTEventTime(swtEvent.time), 
				SWTSwingEventConversionTools.convertSWTStateMask(swtEvent.stateMask, swtEvent.button), 
				swtEvent.button, swtEvent.count, false, transformMouseX(swtEvent.x), transformMouseY(swtEvent.y));
		//TODO Determine popup trigger
	}
	
	
	@Override
	public void mouseEnter(MouseEvent event) {
		for (TICMouseListener listener: getListenerSet().getListeners()) {
			listener.mouseEntered(createEvent(getListenerSet().getOwner(), java.awt.event.MouseEvent.MOUSE_ENTERED, event));
		}
	}


	@Override
	public void mouseExit(MouseEvent event) {
		for (TICMouseListener listener: getListenerSet().getListeners()) {
			listener.mouseExited(createEvent(getListenerSet().getOwner(), java.awt.event.MouseEvent.MOUSE_EXITED, event));
		}
	}


	@Override
	public void mouseHover(MouseEvent event) {}  // Event not present in TICMouseListener


	@Override
	public void mouseMove(MouseEvent event) {
		if (lastPressedButton > 0) {
			for (TICMouseListener listener: getListenerSet().getListeners()) {
				listener.mouseDragged(createEvent(getListenerSet().getOwner(), java.awt.event.MouseEvent.MOUSE_DRAGGED, event));
			}
		}
		else {
			for (TICMouseListener listener: getListenerSet().getListeners()) {
				listener.mouseMoved(createEvent(getListenerSet().getOwner(), java.awt.event.MouseEvent.MOUSE_MOVED, event));
			}
		}
	}


	@Override
	public void mouseDoubleClick(MouseEvent event) {}  // Event not present in TICMouseListener


	@Override
	public void mouseDown(MouseEvent event) {
		lastPressedButton = event.button;
		for (TICMouseListener listener: getListenerSet().getListeners()) {
			listener.mousePressed(createEvent(getListenerSet().getOwner(), java.awt.event.MouseEvent.MOUSE_PRESSED, event));
		}
	}


	@Override
	public void mouseUp(MouseEvent event) {
		lastPressedButton = 0;
		for (TICMouseListener listener: getListenerSet().getListeners()) {
			listener.mouseReleased(createEvent(getListenerSet().getOwner(), java.awt.event.MouseEvent.MOUSE_RELEASED, event));
		}
	}
}
