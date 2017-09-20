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
package info.bioinfweb.tic.scrolling;



/**
 * Listener interface to be implemented by classes that want to be informed in changes of the scroll position
 * of instances of {@link ScrollingTICComponent}.
 * 
 * @author Ben St&ouml;ver
 * @since 3.0.0
 */
public interface TICScrollListener {
	/**
	 * Fired after the contents of a {@link ScrollingTICComponent} have been scrolled.
	 * 
	 * @param event an object describing the scroll event 
	 */
	public void contentScrolled(TICScrollEvent event);
}
