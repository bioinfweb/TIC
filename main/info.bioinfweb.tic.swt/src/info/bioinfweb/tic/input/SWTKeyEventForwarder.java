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


import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;

import info.bioinfweb.tic.TICComponent;



/**
 * Contains a list of {@link TICMouseListener}s and delegates all SWT events to the entries in this list.
 * <p>
 * <b>Warning:</b> This class in meant for internal use by {@link TICComponent} and is not guaranteed to have an unchanged
 * API in future releases with the same major version number.
 * 
 * @author Ben St&ouml;ver
 * @since 2.0.0
 * @bioinfweb.module info.bioinfweb.tic.swt
 */
public class SWTKeyEventForwarder extends AbstractEventForwarder<TICKeyListener>	implements KeyListener {
	public SWTKeyEventForwarder(TICListenerSet<TICKeyListener> listenerSet) {
		super(listenerSet);
	}


	private TICKeyEvent createEvent(TICComponent source, int id, KeyEvent swtEvent) {
		return new TICKeyEvent(source, id, SWTSwingEventConversionTools.convertSWTEventTime(swtEvent.time), 
				SWTSwingEventConversionTools.convertSWTStateMask(swtEvent.stateMask, 0), 
				SWTSwingEventConversionTools.convertSWTKeyCode(swtEvent.keyCode), 
				SWTSwingEventConversionTools.convertSWTKeyLocation(swtEvent.keyLocation),	swtEvent.character);
	}
	

	@Override
	public void keyPressed(KeyEvent event) {
		for (TICKeyListener listener: getListenerSet().getListeners()) {
			listener.keyPressed(createEvent(getListenerSet().getOwner(), java.awt.event.KeyEvent.KEY_PRESSED, event));
		}
	}

	
	@Override
	public void keyReleased(KeyEvent event) {
		for (TICKeyListener listener: getListenerSet().getListeners()) {
			listener.keyReleased(createEvent(getListenerSet().getOwner(), java.awt.event.KeyEvent.KEY_RELEASED, event));
		}
	}
}
