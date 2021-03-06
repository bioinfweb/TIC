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
import info.bioinfweb.tic.input.TICMouseWheelEvent;
import info.bioinfweb.tic.input.TICMouseWheelListener;

import java.util.Set;



/**
 * An <i>TIC</i> mouse wheel event listener that forwards all received {@link TICMouseWheelEvent}s to a set of 
 * {@link TICComponent}s. 
 * 
 * @author Ben St&ouml;ver
 * @since 3.0.0
 * @bioinfweb.module info.bioinfweb.tic.core
 */
public class TICMouseWheelEventComponentForwarder extends AbstractEventComponentForwarder implements TICMouseWheelListener {
	public TICMouseWheelEventComponentForwarder(Set<TICComponent> components) {
		super(components);
	}


	@Override
	public boolean mouseWheelMoved(TICMouseWheelEvent event) {
		return dispatchEvent(event);
	}
}
