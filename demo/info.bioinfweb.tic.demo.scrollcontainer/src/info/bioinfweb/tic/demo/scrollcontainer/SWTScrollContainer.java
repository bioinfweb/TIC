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
package info.bioinfweb.tic.demo.scrollcontainer;


import info.bioinfweb.tic.SWTComponentFactory;
import info.bioinfweb.tic.toolkit.ScrolledCompositeToolkitComponent;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;



public class SWTScrollContainer extends ScrolledComposite implements ScrolledCompositeToolkitComponent {
	private ScrollContainer independentComponent;

	
	public SWTScrollContainer(ScrollContainer independentComponent, Composite parent, int style) {
		super(parent, style | SWT.V_SCROLL | SWT.H_SCROLL);
		this.independentComponent = independentComponent;
		setAlwaysShowScrollBars(true);
		init();
	}
	
	
	private void init() {
		Composite swtOutputComponent = SWTComponentFactory.getInstance().getSWTComponent(
				getIndependentComponent().getOutputComponent(), this, SWT.NO_BACKGROUND);
		setContent(swtOutputComponent);
	}


	@Override
	public ScrollContainer getIndependentComponent() {
		return independentComponent;
	}


	@Override
	public Control getSWTComponent() {
		return this;
	}


	@Override
	public ScrolledComposite getScrolledComposite() {
		return this;
	}
}
