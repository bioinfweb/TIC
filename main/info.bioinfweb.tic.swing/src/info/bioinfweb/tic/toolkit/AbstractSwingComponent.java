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
import java.awt.Point;

import info.bioinfweb.tic.TICComponent;
import info.bioinfweb.tic.TargetToolkit;

import javax.swing.JComponent;
import javax.swing.JPanel;



/**
 * The <i>Swing</i> component implementing {@link ToolkitComponent}. Custom <i>Swing</i> components can be inherited from 
 * this class.
 * <p>
 * This class also overwrites {@link #getMaximumSize()} in a way that all values specified for the maximum width or height
 * that are below the returned preferred width or height are ignored.
 * 
 * @author Ben St&ouml;ver
 * @since 1.0.0
 * @bioinfweb.module info.bioinfweb.tic.swing
 */
public abstract class AbstractSwingComponent extends JPanel implements SwingToolkitComponent {  // If JComponent is used as the base class, it won't be be possible to have a width above 16384 (or more with a different max size but less than JPanel) pixels. JPanel allows this.
	private TICComponent independentComponent;

	
	public AbstractSwingComponent(TICComponent ticComponent) {
		super();
		this.independentComponent = ticComponent;
		setFocusable(true);
	}


	@Override
	public JComponent getSwingComponent() {
		return this;
	}


	@Override
	public TICComponent getIndependentComponent() {
		return independentComponent;
	}


	@Override
	public Dimension getMaximumSize() {
		return SwingComponentTools.getMaximumSize(this, super.getMaximumSize());
	}
}
