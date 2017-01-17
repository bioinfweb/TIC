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

import java.util.EventListener;
import java.util.HashSet;
import java.util.Set;



/**
 * Manages a list of TIC event listeners. The toolkit specific TIC components will forward
 * events from their toolkit to the elements of this list in instances of {@link TICComponent}. 
 * 
 * @author Ben St&ouml;ver
 * @since 2.0.0
 * @bioinfweb.module info.bioinfweb.tic.core
 *
 * @param <L> the listener type
 */
public class TICListenerSet<L extends EventListener> {
	private TICComponent owner;
	private Set<L> listeners = new HashSet<L>();
	
	
	public TICListenerSet(TICComponent owner) {
		super();
		this.owner = owner;
	}


	public TICComponent getOwner() {
		return owner;
	}


	public Set<L> getListeners() {
		return listeners;
	}
}
