/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2010-2014  Ben Stöver
 * <http://commons.bioinfweb.info/Java>
 * 
 * This file is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This file is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package info.bioinfweb.tic.toolkit;


import java.awt.Dimension;

import info.bioinfweb.tic.TICComponent;
import info.bioinfweb.tic.TargetToolkit;

import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Point;
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
 */
public abstract class AbstractSWTWidget extends Canvas implements PaintListener, SWTToolkitComponent {
	private TICComponent independentComponent;

	
	public AbstractSWTWidget(Composite parent, int style, TICComponent ticComponent) {
		super(parent, style);
		independentComponent = ticComponent;
		addPaintListener(this);
	}


	@Override
	public TICComponent getIndependentComponent() {
		return independentComponent;
	}


	@Override
	public void repaint() {
		redraw();
	}
	
	
	@Override
	public void assignSizeToSWTLayoutData(Point size) {}
	
	
	@Override
	public void assignSize() {
		Dimension size = getIndependentComponent().getSize();
		setSize(size.width, size.height);
		assignSizeToSWTLayoutData(getSize());
	}


	@Override
	public TargetToolkit getTargetToolkit() {
		return TargetToolkit.SWT;
	}


	@Override
	public java.awt.Point getLocationInParent() {
		Point location = getLocation();
		return new java.awt.Point(location.x, location.y);
	}
}