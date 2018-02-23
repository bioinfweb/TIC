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

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JScrollPane;



/**
 * Provides static methods that help in implementing <i>Swing</i> versions of {@link ToolkitComponent}, which
 * cannot be inherited from {@link AbstractSwingComponent}.
 * 
 * @author Ben St&ouml;ver
 * @since 2.0.0
 * @bioinfweb.module info.bioinfweb.tic.swing
 */
public class SwingComponentTools {
	public static void assignSize(JComponent component) {
		ToolkitComponent toolkitComponent = (ToolkitComponent)component; 
		if (toolkitComponent.getIndependentComponent().hasDefinedSize()) {
			Dimension size = toolkitComponent.getIndependentComponent().getSize();
			component.setSize(size);
			component.setPreferredSize(size);
			component.doLayout();
		}
	}
	
	
	public static Dimension getMaximumSize(JComponent component, Dimension superMaxSize) {
		Dimension preferredSize = component.getPreferredSize();
		return new Dimension(Math.max(superMaxSize.width, preferredSize.width), 
				Math.max(superMaxSize.height, preferredSize.height));
	}
	
	
	/**
	 * Tool method to be used by implementations of {@link JScrollPaneToolkitComponent}. It should
	 * be called within the constructor of such classes in order to ensure correct firing of scroll
	 * events in their associated {@link ScrollingTICComponent}.
	 * 
	 * @param component the toolkit component providing the scroll functionality
	 */
	public static void registerScrollEventForwarders(JScrollPaneToolkitComponent component) {
		JScrollPane scrollPane = component.getScrollPane();
		AdjustmentScrollListenerForwarder listener = new AdjustmentScrollListenerForwarder(component);
		scrollPane.getHorizontalScrollBar().addAdjustmentListener(listener);
		scrollPane.getVerticalScrollBar().addAdjustmentListener(listener);
		// Registering a change listener to the model instead does not work, since the event there is fired before the scroll position is updated.
		// Registering a component listener and react to moving events is alternatively possible for ScrollPanes. (Such events are not fired, when the whole container is moved.) This solution would become a problem, if the scrolled component would be switched during runtime.
	}
	
	
	/**
	 * Tool method to be used by implementations of {@link JScrollBarsToolkitComponent}. It should
	 * be called within the constructor of such classes in order to ensure correct firing of scroll
	 * events in their associated {@link ScrollingTICComponent}.
	 * 
	 * @param component the toolkit component providing the scroll functionality
	 */
	public static void registerScrollEventForwarders(JScrollBarsToolkitComponent component) {
		AdjustmentScrollListenerForwarder listener = new AdjustmentScrollListenerForwarder(component);
		component.getHorizontalScrollBar().addAdjustmentListener(listener);
		component.getVerticalScrollBar().addAdjustmentListener(listener);
	}
}
