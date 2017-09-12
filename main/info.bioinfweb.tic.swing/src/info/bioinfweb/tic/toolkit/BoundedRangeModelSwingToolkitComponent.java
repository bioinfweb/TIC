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


import java.awt.Rectangle;

import javax.swing.BoundedRangeModel;
import javax.swing.JScrollBar;



/**
 * Scrollable <i>Swing</i> components (implementing {@link ScrollingToolkitComponent}) can implement this
 * interface, if they provide scrolling functionality using a horizontal and a vertical {@link BoundedRangeModel},
 * e.g. with {@link JScrollBar}s. It contains default method implementations that delegate the scrolling methods 
 * of {@link ScrollingToolkitComponent} to methods of the two {@link BoundedRangeModel}s.
 * <p>
 * Note that the default methods of this interface assume that value and extend of the models are given in pixels.
 * If there is e.g. a linear factor between the values of the models and pixel coordinate system of the scrolled
 * component, this interface cannot be used.
 * 
 * @author Ben St&ouml;ver
 * @since 3.0.0
 * @see ScrollingToolkitComponent
 * @see ScrollPaneSwingToolkitComponent
 */
public interface BoundedRangeModelSwingToolkitComponent extends ScrollingToolkitComponent {
	public BoundedRangeModel getHorizontalModel();
	
	
	public BoundedRangeModel getVerticalModel();

	
	public default void setScrollOffset(int x, int y) {
		getHorizontalModel().setValue(x);
		getVerticalModel().setValue(y);
	}
	
	
	public default Rectangle getVisibleRectangle() {
		return new Rectangle(getHorizontalModel().getValue(), getVerticalModel().getValue(), 
				getHorizontalModel().getExtent(), getVerticalModel().getExtent());
	}
}
