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
package info.bioinfweb.tic.input.component;

import info.bioinfweb.tic.input.AbstractEventForwarder;
import info.bioinfweb.tic.input.TICKeyEvent;
import info.bioinfweb.tic.input.TICKeyListener;
import info.bioinfweb.tic.input.TICListenerSet;



/**
 * An <i>TIC</i> key event listener that forwards all received {@link TICKeyEvent}s to a set of other 
 * {@link TICKeyListener}s.
 * 
 * @author Ben St&ouml;ver
 * @since 3.0.0
 * @bioinfweb.module info.bioinfweb.tic.core
 */
public class TICKeyEventForwarder extends AbstractEventForwarder<TICKeyListener> implements TICKeyListener {
	public TICKeyEventForwarder(TICListenerSet<TICKeyListener> listenerSet) {
		super(listenerSet);
	}


	@Override
	public boolean keyPressed(TICKeyEvent event) {
		boolean consumed = false;
		for (TICKeyListener listener: getListenerSet().getListeners()) {
			consumed = consumed || listener.keyPressed(event);
		}
		return consumed;
	}

	
	@Override
	public boolean keyReleased(TICKeyEvent event) {
		boolean consumed = false;
		for (TICKeyListener listener: getListenerSet().getListeners()) {
			consumed = consumed || listener.keyReleased(event);
		}
		return consumed;
	}
}
