/*
 * LibrAlign - A GUI library for displaying and editing multiple sequence alignments and attached data
 * Copyright (C) 2014-2015  Ben Stöver
 * <http://bioinfweb.info/LibrAlign>
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


import info.bioinfweb.tic.toolkit.layoutdata.SWTLayoutDataFactory;

import java.awt.Dimension;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;



/**
 * Tool collection for shared functionality of {@link AbstractSWTWidget} and {@link AbstractSWTComposite}.
 * 
 * @author Ben St&ouml;ver
 * @since 2.0.0
 */
public class SWTComponentTools {
	public static Dimension getToolkitSize(Composite composite) {
		Point point = composite.getSize();
		return new Dimension(point.x, point.y);
	}
	
	
	public static void assignSize(Composite composite) {
		Dimension size = ((ToolkitComponent)composite).getIndependentComponent().getSize();
		Point point = new Point(size.width, size.height);
		composite.setSize(point);
		if (composite.getParent() != null) {
			SWTLayoutDataFactory.getInstance().setLayoutData(composite.getParent().getLayout(), point, composite);
		}
	}
	
	
	public static java.awt.Point getLocationInParent(Composite composite) {
		Point location = composite.getLocation();
		return new java.awt.Point(location.x, location.y);
	}
}
