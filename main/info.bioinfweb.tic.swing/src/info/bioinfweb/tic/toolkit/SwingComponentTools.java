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


import java.awt.Dimension;

import javax.swing.JComponent;



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
		Dimension size = ((ToolkitComponent)component).getIndependentComponent().getSize();
		component.setSize(size);
		component.setPreferredSize(size);
	}
	
	
	public static Dimension getMaximumSize(JComponent component, Dimension superMaxSize) {
		Dimension preferredSize = component.getPreferredSize();
		return new Dimension(Math.max(superMaxSize.width, preferredSize.width), 
				Math.max(superMaxSize.height, preferredSize.height));
	}
}
