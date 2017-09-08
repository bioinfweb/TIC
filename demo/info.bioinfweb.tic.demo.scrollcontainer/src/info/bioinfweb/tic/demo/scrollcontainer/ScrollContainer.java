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
package info.bioinfweb.tic.demo.scrollcontainer;


import java.awt.Dimension;

import info.bioinfweb.tic.TICPaintEvent;
import info.bioinfweb.tic.scrolling.ScrollingTICComponent;



public class ScrollContainer extends ScrollingTICComponent {
	private OutputComponent outputComponent = new OutputComponent();
	
	
	public OutputComponent getOutputComponent() {
		return outputComponent;
	}


	@Override
	protected String getSwingComponentClassName(Object... parameters) {
		return "info.bioinfweb.tic.demo.scrollcontainer.SwingScrollContainer";
	}


	@Override
	protected String getSWTComponentClassName(Object... parameters) {
		return "info.bioinfweb.tic.demo.scrollcontainer.SWTScrollContainer";
	}


	@Override
	public void paint(TICPaintEvent event) {}  // Nothing to do, since special toolkit-specific components are provided instead.
	

	@Override
	public Dimension getSize() {
		return getOutputComponent().getSize();
	}
}
