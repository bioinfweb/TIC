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
package info.bioinfweb.tic.toolkit.scrolling;


import java.util.EventObject;



public class ScrollEvent extends EventObject {
	private boolean verticalChange;
	private boolean horizontalChange;
	
	
	public ScrollEvent(DirectScrollingSWTComposite source, boolean verticalChange, boolean horizontalChange) {
		super(source);
		this.verticalChange = verticalChange;
		this.horizontalChange = horizontalChange;
	}


	@Override
	public DirectScrollingSWTComposite getSource() {
		return (DirectScrollingSWTComposite)super.getSource();
	}


	public boolean isVerticalChange() {
		return verticalChange;
	}


	public boolean isHorizontalChange() {
		return horizontalChange;
	}
}
