/*
 * Toolkit independent components (TIC) - A Java library for creating GUI components for Swing and SWT
 * Copyright (C) 2014-2016  Ben Stöver
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
package info.bioinfweb.tic.toolkit.layoutdata;


import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;



/**
 * @author Ben St&ouml;ver
 * @bioinfweb.module info.bioinfweb.tic.swt
 */
public class SWTLayoutDataFactory {
	private static SWTLayoutDataFactory firstInstance = null;
	
	
	private Map<Class<? extends Layout>, SingleLayoutDataFactory> factories = 
			new HashMap<Class<? extends Layout>, SingleLayoutDataFactory>();
	
	
	private SWTLayoutDataFactory() {
		super();
		initMap();
	}
	
	
	private void initMap() {
		factories.put(RowLayout.class, new RowLayoutFactory());
	}
	
	
	public static SWTLayoutDataFactory getInstance() {
		if (firstInstance == null) {
			firstInstance = new SWTLayoutDataFactory();
		}
		return firstInstance;
	}


	public Map<Class<? extends Layout>, SingleLayoutDataFactory> getFactories() {
		return factories;
	}
	
	
	public void setLayoutData(Layout layout, Point size, Composite child) {
		SingleLayoutDataFactory factory = getFactories().get(layout.getClass());
		if (factory != null) {
			Object data = factory.createLayoutData(size, child);
			if (data != null) {
				child.setLayoutData(data);
			}
		}
	}
}
