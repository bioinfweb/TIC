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


import info.bioinfweb.tic.SwingComponentFactory;
import info.bioinfweb.tic.toolkit.ScrollPaneSwingToolkitComponent;

import javax.swing.JComponent;
import javax.swing.JScrollPane;



/**
 * The toolkit-specific <i>Swing</i> implementation for the scroll container of this demo.
 * <p>
 * This class inherits from {@link JScrollPane} and sets the <i>Swing</i> component created
 * from {@link OutputComponent} as its viewport view to be scrolled. 
 * <p>
 * A number of method implementations are inherited from {@link ScrollPaneSwingToolkitComponent}, which 
 * provides respective default methods. This class only needs to provide references to its 
 * instances by implementing {@link #getSwingComponent()} and {@link #getScrollPane()}.
 * 
 * @author Ben St&ouml;ver
 * @since 3.0.0
 */
public class SwingScrollContainer extends JScrollPane implements ScrollPaneSwingToolkitComponent {
	private ScrollContainer independentComponent;
	
	
	public SwingScrollContainer(ScrollContainer independentComponent) {
		super();
		this.independentComponent = independentComponent;
		init();
	}
	
	
	private void init() {
		setViewportView(SwingComponentFactory.getInstance().getSwingComponent(getIndependentComponent().getOutputComponent()));
	}


	@Override
	public ScrollContainer getIndependentComponent() {
		return independentComponent;
	}


	@Override
	public JComponent getSwingComponent() {
		return this;
	}
	
	
	@Override
	public JScrollPane getScrollPane() {
		return this;
	}
}
