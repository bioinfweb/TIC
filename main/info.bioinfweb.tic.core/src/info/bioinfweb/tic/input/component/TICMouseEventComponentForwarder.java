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


import info.bioinfweb.tic.TICComponent;
import info.bioinfweb.tic.input.TICMouseEvent;
import info.bioinfweb.tic.input.TICMouseListener;

import java.util.Set;



/**
 * An <i>TIC</i> mouse event listener that forwards all received {@link TICMouseEvent}s to a set {@link TICComponent}s. 
 * 
 * @author Ben St&ouml;ver
 * @since 3.0.0
 * @bioinfweb.module info.bioinfweb.tic.core
 */
public class TICMouseEventComponentForwarder extends AbstractEventComponentForwarder implements TICMouseListener {
	public TICMouseEventComponentForwarder(Set<TICComponent> components) {
		super(components);
	}


	@Override
	public boolean mousePressed(TICMouseEvent event) {
		return dispatchEvent(event);
	}


	@Override
	public boolean mouseReleased(TICMouseEvent event) {
		return dispatchEvent(event);
	}


	@Override
	public boolean mouseEntered(TICMouseEvent event) {
		return dispatchEvent(event);
	}


	@Override
	public boolean mouseExited(TICMouseEvent event) {
		return dispatchEvent(event);
	}


	@Override
	public boolean mouseMoved(TICMouseEvent event) {
		return dispatchEvent(event);
	}


	@Override
	public boolean mouseDragged(TICMouseEvent event) {
		return dispatchEvent(event);
	}
}
