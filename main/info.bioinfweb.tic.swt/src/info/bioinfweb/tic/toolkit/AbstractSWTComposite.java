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


import info.bioinfweb.tic.TICComponent;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;



/**
 * The <i>SWT</i> component implementing {@link ToolkitComponent}. Custom <i>SWT</i> components can be 
 * inherited from this class.
 *
 * @author Ben St&ouml;ver
 * @since 1.1.0
 * @bioinfweb.module info.bioinfweb.tic.swt
 */
public class AbstractSWTComposite extends Composite implements SWTToolkitComponent {
	private TICComponent independentComponent;

	
	public AbstractSWTComposite(TICComponent ticComponent, Composite parent, int style) {
		super(parent, style);
		independentComponent = ticComponent;
	}


	@Override
	public Control getSWTComponent() {
		return this;
	}


	@Override
	public TICComponent getIndependentComponent() {
		return independentComponent;
	}
}
