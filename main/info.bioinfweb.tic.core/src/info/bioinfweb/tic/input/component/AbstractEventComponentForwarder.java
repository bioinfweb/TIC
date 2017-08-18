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
import info.bioinfweb.tic.input.TICEvent;
import info.bioinfweb.tic.input.TICInputEvent;

import java.util.HashSet;
import java.util.Set;



/**
 * The base class of all event forwarders that forward {@link TICEvent} from one {@link TICComponent} to a
 * set of other {@link TICComponent}s.
 * 
 * @author Ben St&ouml;ver
 * @bioinfweb.module info.bioinfweb.tic.core
 * @since 3.0.0
 */
public class AbstractEventComponentForwarder {
	private Set<TICComponent> components = new HashSet<>();

	
	public AbstractEventComponentForwarder(Set<TICComponent> components) {
		super();
		this.components = components;
	}


	public Set<TICComponent> getComponents() {
		return components;
	}

	
	protected boolean dispatchEvent(TICInputEvent event) {
		boolean consumed = false;
		for (TICComponent component : getComponents()) {
			consumed = consumed || component.dispatchEvent(event);
		}
		return consumed;
	}
}
