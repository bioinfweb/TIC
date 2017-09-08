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
package info.bioinfweb.tic;


import info.bioinfweb.tic.toolkit.ToolkitComponent;

import java.awt.Dimension;



/**
 * Interface to be implemented by <i>TIC</i> components that do not define a certain size but are resizable and
 * allow e.g. layout managers of the target toolkits to change their size.
 * <p>
 * This interface acts on the one hand as a tagging interface to mark such implementations of 
 * {@link TICComponent} and on the other hand provides a default implementation of {@link #getSize()} that
 * returns the current size of toolkit component. 
 * 
 * @author Ben St&ouml;ver
 * @since 3.0.0
 */
public interface ResizableTICComponent {
	public boolean hasToolkitComponent();
	
	
	public ToolkitComponent getToolkitComponent();
	
	
	/**
	 * Returns the current size of the toolkit component.
	 * 
	 * @return the size of the toolkit component or (0, 0) if no such component was yet created.
	 */
	public default Dimension getSize() {
		if (hasToolkitComponent()) {
			return getToolkitComponent().getToolkitSize();
		}
		else {
			return new Dimension(0, 0);
		}
	}
}
