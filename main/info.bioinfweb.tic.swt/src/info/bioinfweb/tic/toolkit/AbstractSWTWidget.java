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

import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;



/**
 * The SWT widget implementing {@link ToolkitComponent} that can be painted toolkit independent.
 * <p>
 * Custom components should not be inherited from this class, but from {@link AbstractSWTComposite} since that class
 * does not inherit from {@link Canvas}.
 * 
 * @author Ben St&ouml;ver
 * @since 1.0.0
 * @bioinfweb.module info.bioinfweb.tic.swt
 */
public abstract class AbstractSWTWidget extends Canvas implements PaintListener, SWTToolkitComponent {
	private TICComponent independentComponent;

	
	public AbstractSWTWidget(TICComponent ticComponent, Composite parent, int style) {
		super(parent, style);
		independentComponent = ticComponent;
		addPaintListener(this);
	}


	@Override
	public TICComponent getIndependentComponent() {
		return independentComponent;
	}
}
