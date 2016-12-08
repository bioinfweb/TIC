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
package info.bioinfweb.tic.input;


import info.bioinfweb.tic.TICComponent;



/**
 * TIC event object that is used to represent a toolkit independent key event. 
 * 
 * @author Ben St&ouml;ver
 */
public class TICKeyEvent extends TICInputEvent {
	private int keyCode;
	private int keyLocation;
	private char keyCharacter;
	
	
	public TICKeyEvent(TICComponent source, long time, int modifiers,	int keyCode, int keyLocation, char keyCharacter) {
		super(source, time, modifiers);
		this.keyCode = keyCode;
		this.keyLocation = keyLocation;
		this.keyCharacter = keyCharacter;
	}
	
	
	public int getKeyCode() {
		return keyCode;
	}


	/**
	 * Returns the location of the key that was pressed.
	 * 
	 * @return A Swing constant describing the key location (e.g. {@link KeyEvent#KEY_LOCATION_STANDARD}, 
	 *         {@link KeyEvent#KEY_LOCATION_LEFT}, {@link KeyEvent#KEY_LOCATION_RIGHT}, {@link KeyEvent#KEY_LOCATION_NUMPAD}) 
	 */
	public int getKeyLocation() {
		return keyLocation;
	}


	public char getKeyCharacter() {
		return keyCharacter;
	}
}
