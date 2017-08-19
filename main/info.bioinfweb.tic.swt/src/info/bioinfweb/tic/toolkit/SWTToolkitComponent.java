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
import org.eclipse.swt.widgets.Composite;



public interface SWTToolkitComponent extends ToolkitComponent {
	@Override
	default public TargetToolkit getTargetToolkit() {
		return TargetToolkit.SWT;
	}


	@Override
	default public void repaint() {
		((Composite)this).redraw();
	}


	@Override
	default public Dimension getToolkitSize() {
		Point point = ((Composite)this).getSize();
		return new Dimension(point.x, point.y);
	}
	
	
	@Override
	default public void assignSize() {
		Composite composite = (Composite)this;
		Dimension size = ((ToolkitComponent)composite).getIndependentComponent().getSize();
		Point point = new Point(size.width, size.height);
		composite.setSize(point);
		if (composite.getParent() != null) {
			SWTLayoutDataFactory.getInstance().setLayoutData(composite.getParent().getLayout(), point, composite);
		}
	}
	
	
	@Override
	default public java.awt.Point getLocationInParent() {
		Point location = ((Composite)this).getLocation();
		return new java.awt.Point(location.x, location.y);
	}

	
	@Override
	default public boolean isFocusOwner() {
		return ((Composite)this).isFocusControl();
	}
}
