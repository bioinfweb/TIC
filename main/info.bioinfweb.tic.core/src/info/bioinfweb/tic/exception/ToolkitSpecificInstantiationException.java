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
package info.bioinfweb.tic.exception;


import info.bioinfweb.tic.TICComponent;



/**
 * Exception that indicates that creating a toolkit specific instance related to a {@link TICComponent}
 * failed. The specified cause exception provider further details.
 * <p>
 * Usually this exception will occur, if the according was not found in the class path during runtime
 * (e.g. due to missing libraries) or a custom toolkit specific implementation does not offer a
 * construction that meets the according convention.
 * 
 * @author Ben St&ouml;ver
 * @since 2.0.0
 * @bioinfweb.module info.bioinfweb.tic.core
 */
public class ToolkitSpecificInstantiationException extends RuntimeException {
	public ToolkitSpecificInstantiationException(Throwable cause) {
		super(cause.getLocalizedMessage(), cause);
	}

	
	public ToolkitSpecificInstantiationException(String message) {
		super(message);
	}
}
