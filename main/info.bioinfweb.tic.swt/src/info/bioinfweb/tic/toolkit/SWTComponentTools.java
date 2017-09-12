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


import info.bioinfweb.tic.scrolling.ScrollingTICComponent;
import info.bioinfweb.tic.scrolling.TICScrollEvent;

import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;



public class SWTComponentTools {
	/**
	 * Tool method to be used by implementations of {@link ScrolledCompositeToolkitComponent}. It should
	 * be called within the constructor of such classes in order to ensure correct firing of scroll
	 * events in their associated {@link ScrollingTICComponent}.
	 * 
	 * @param component the toolkit component providing the scroll functionality
	 */
	public static void registerScrollEventForwarders(ScrolledCompositeToolkitComponent component) {
		component.getScrolledComposite().getContent().addControlListener(new ControlAdapter() {
			@Override
			public void controlMoved(ControlEvent e) {
				component.getIndependentComponent().fireControlScrolled(new TICScrollEvent(component));
			}
		});
	}
}
