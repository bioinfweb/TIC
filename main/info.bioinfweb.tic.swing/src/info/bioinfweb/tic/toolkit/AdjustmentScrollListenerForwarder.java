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
package info.bioinfweb.tic.toolkit;


import info.bioinfweb.tic.scrolling.TICScrollEvent;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;



public class AdjustmentScrollListenerForwarder implements AdjustmentListener {
	private ScrollingToolkitComponent source;

	
	public AdjustmentScrollListenerForwarder(ScrollingToolkitComponent source) {
		super();
		this.source = source;
	}

	
	public ScrollingToolkitComponent getSource() {
		return source;
	}

	
	@Override
	public void adjustmentValueChanged(AdjustmentEvent e) {
		getSource().getIndependentComponent().fireControlScrolled(new TICScrollEvent(getSource()));
	}
}
