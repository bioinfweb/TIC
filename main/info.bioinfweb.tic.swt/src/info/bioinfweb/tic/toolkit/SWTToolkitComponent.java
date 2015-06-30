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


import org.eclipse.swt.graphics.Point;



public interface SWTToolkitComponent extends ToolkitComponent {
	/**
	 * Method called by {@link #assignSize()} to be used to adopt the size of the TIC component to the 
	 * underlying SWT component's layout data. This method is only called if this component is backed
	 * by an SWT composite and not is the Swing version is used or no toolkit specific component has
	 * been created yet.
	 * <p>
	 * It can be overwritten by inherited classes that know the class the layout data will have. This 
	 * default implementation is empty. Note that depending on your GUI design {@link #assignSize()}
	 * might not have the desired if this method is not overwritten with an according implementation.   
	 * 
	 * @param size - the size the composite shall have
	 * @param composite - the SWT composite that backs this TIC component
	 */
	public void assignSizeToSWTLayoutData(Point size);	
}
