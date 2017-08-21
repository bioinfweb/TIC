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
package info.bioinfweb.tic.input;


import info.bioinfweb.tic.TICComponent;

import java.util.EventObject;



/**
 * The base class of all events in the context of a {@link TICComponent}.
 * 
 * @author Ben St&ouml;ver
 * @bioinfweb.module info.bioinfweb.tic.core
 * @since 0.3.0
 */
public class TICEvent extends EventObject implements Cloneable {
	public TICEvent(TICComponent source) {
		super(source);
	}
	
	
  @Override
	public TICComponent getSource() {
		return (TICComponent)super.getSource();
	}
  
  
  /**
   * Creates a copy of this event with all properties set to identical values but with a 
   * different source.
   * 
   * @param source the source component to be used for the created copy
   * @return the modified copy of this instance
   */
  public TICEvent cloneWithNewSource(TICComponent source) {
  	TICEvent result = clone();
  	result.source = source;
  	return result;
  }


	@Override
	public TICEvent clone() {
		try {
			return (TICEvent)super.clone();
		}
		catch (CloneNotSupportedException e) {
			throw new InternalError(e);
		}
	}
}
