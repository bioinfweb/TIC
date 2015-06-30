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
 */
public class SWTKeyEventForwarder extends AbstractEventForwarder<TICKeyListener>	implements KeyListener {
	public SWTKeyEventForwarder(TICListenerSet<TICKeyListener> listenerSet) {
		super(listenerSet);
	}


	private TICKeyEvent createEvent(TICComponent source, org.eclipse.swt.events.KeyEvent swtEvent) {
		return new TICKeyEvent(source, SWTSwingEventConversionTools.convertSWTEventTime(swtEvent.time), 
				SWTSwingEventConversionTools.convertSWTStateMask(swtEvent.stateMask, 0), 
				SWTSwingEventConversionTools.convertSWTKeyCode(swtEvent.keyCode), 
				SWTSwingEventConversionTools.convertSWTKeyLocation(swtEvent.keyLocation),	swtEvent.character);
	}
	

	@Override
	public void keyPressed(KeyEvent event) {
		for (TICKeyListener listener: getListenerSet().getListeners()) {
			listener.keyPressed(createEvent(getListenerSet().getOwner(), event));
		}
	}

	
	@Override
	public void keyReleased(KeyEvent event) {
		for (TICKeyListener listener: getListenerSet().getListeners()) {
			listener.keyReleased(createEvent(getListenerSet().getOwner(), event));
		}
	}
}
