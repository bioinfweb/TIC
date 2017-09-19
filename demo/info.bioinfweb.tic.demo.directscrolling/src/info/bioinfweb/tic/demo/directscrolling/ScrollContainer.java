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
package info.bioinfweb.tic.demo.directscrolling;


import info.bioinfweb.tic.scrolling.ScrollingTICComponent;
import info.bioinfweb.tic.toolkit.DirectPaintingSWTScrollContainer;

import org.eclipse.swt.custom.ScrolledComposite;



/**
 * An example implementation of a <i>TIC</i> component that paints its fixed-sized contents on scrollable
 * toolkit components. In contrast to the scroll container demo, the toolkit components associated with
 * this class perform scrolling directly without using a scroll container like {@link ScrolledComposite}.
 * 
 * @author Ben St&ouml;ver
 */
public class ScrollContainer extends ScrollingTICComponent {
	private OutputComponent outputComponent = new OutputComponent();
	
	
	public OutputComponent getOutputComponent() {
		return outputComponent;
	}

	
	@Override
	protected String getSWTComponentClassName(Object... parameters) {
		return "info.bioinfweb.tic.toolkit.DirectPaintingSWTScrollContainer";
	}


	/**
	 * Passes the associated output component instances as the expected additional constructor
	 * parameter to {@link DirectPaintingSWTScrollContainer}.
	 * 
	 * @return an array containing the result of {@link #getOutputComponent()} as its only element.
	 */
	@Override
	protected Object[] getSWTComponentConstructorParameters(Object... parameters) {
		return new Object[]{getOutputComponent()};
	}
}
