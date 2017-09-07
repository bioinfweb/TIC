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


import info.bioinfweb.tic.TargetToolkit;
import info.bioinfweb.tic.toolkit.layoutdata.SWTLayoutDataFactory;

import java.awt.Dimension;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Control;



/**
 * Extension of {@link ToolkitComponent} that implements a set of default methods. <i>SWT</i> toolkit components
 * should make use of these implementations.
 * 
 * @author Ben St&ouml;ver
 * @bioinfweb.module info.bioinfweb.tic.swt
 */
public interface SWTToolkitComponent extends ToolkitComponent {
	public Control getSWTComponent();
	
	
	@Override
	default public TargetToolkit getTargetToolkit() {
		return TargetToolkit.SWT;
	}


	@Override
	default public void repaint() {
		getSWTComponent().redraw();
	}


	@Override
	default public Dimension getToolkitSize() {
		Point point = getSWTComponent().getSize();
		return new Dimension(point.x, point.y);
	}
	
	
	@Override
	default public void assignSize() {
		Control control = getSWTComponent();
		Dimension size = ((ToolkitComponent)control).getIndependentComponent().getSize();
		Point point = new Point(size.width, size.height);
		control.setSize(point);
		if ((control.getParent() != null) && (control.getParent().getLayout() != null)) {
			SWTLayoutDataFactory.getInstance().setLayoutData(control.getParent().getLayout(), point, control);
		}
	}
	
	
	@Override
	default public java.awt.Point getLocationInParent() {
		Point location = getSWTComponent().getLocation();
		return new java.awt.Point(location.x, location.y);
	}

	
	@Override
	default public boolean isFocusOwner() {
		return getSWTComponent().isFocusControl();
	}
}
