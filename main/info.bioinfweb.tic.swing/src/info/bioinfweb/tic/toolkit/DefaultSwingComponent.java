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
import info.bioinfweb.tic.TICPaintEvent;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.beans.Transient;



/**
 * The default Swing component implementation to be associated with a {@link TICComponent}.
 * <p>
 * It overwrites {@link #paint(Graphics)} using {@link TICComponent#paint(TICPaintEvent)} to paint the component.
 * 
 * @author Ben St&ouml;ver
 * @bioinfweb.module info.bioinfweb.tic.swing
 */
public class DefaultSwingComponent extends AbstractSwingComponent {
	public DefaultSwingComponent(TICComponent ticComponent) {
		super(ticComponent);
	}
	

	@Override
	public void paint(Graphics graphics) {
		getIndependentComponent().paint(new TICPaintEvent(this, (Graphics2D)graphics, getVisibleRect()));
	}


	@Override
	@Transient
	public Dimension getPreferredSize() {
		if (getIndependentComponent().hasDefinedSize()) {
			return getIndependentComponent().getSize();
		}
		else {
			return super.getPreferredSize();
		}
	}
}
