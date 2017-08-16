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


import java.awt.Dimension;
import java.awt.Point;

import info.bioinfweb.tic.TICComponent;
import info.bioinfweb.tic.TargetToolkit;



/**
 * Interface to be implemented by all toolkit-specific GUI components associated with a {@link TICComponent}.
 * <p>
 * Note that implementing classes must always provide constructors that match the parameters expected by the
 * associated {@link TICComponent}. Additional parameters are specified by 
 * {@link TICComponent#getSwingComponentConstructorParameters(Object...)} or
 * {@link TICComponent#getSWTComponentConstructorParameters(Object...)}. All constructors must have the 
 * following set of parameters, possible followed by additional parameters defined by the <i>TIC</i> component: 
 * <ul>
 *   <li>Swing: {@code SomeSwingComponent(TICComponent owner)}</li> 
 *   <li>SWT: {@code SomeSWTComponent(TICComponent owner, Composite parent, int style)}</li>
 * </ul> 
 * 
 * @author Ben St&ouml;ver
 * @since 1.0.0
 * @bioinfweb.module info.bioinfweb.tic.core
 */
public interface ToolkitComponent {
	/**
	 * Returns the toolkit type this instance can be used with.
	 * 
	 * @return the toolkit type implemented by this instance
	 */
	public TargetToolkit getTargetToolkit();
	
  /**
   * Returns the {@link TICComponent} that is displayed by this GUI element.
   */
  public TICComponent getIndependentComponent();
  
  /**
   * Repaints the contents of this instance.
   */
  public void repaint();
  
	/**
	 * Returns the coordinates of this component relative to its parent component.
	 * 
	 * @return the location in the coordinate system of the parent GUI component
	 */
	public Point getLocationInParent();
	
	/**
	 * Returns the current size of this component.
	 * 
	 * @return the size this component currently has
	 */
	public Dimension getToolkitSize();
	
	/**
	 * Adopts the current size provided by the parent TIC component to this GUI toolkit component.
	 */
	public void assignSize();
}
