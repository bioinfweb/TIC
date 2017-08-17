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


import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.EventObject;

import info.bioinfweb.commons.SystemUtils;
import info.bioinfweb.tic.TICComponent;



/**
 * All toolkit independent input events in TIC should be inherited from this class.
 * 
 * @author Ben St&ouml;ver
 * @bioinfweb.module info.bioinfweb.tic.core
 */
public class TICInputEvent extends EventObject {
	private long time;
	private int modifiers;
	
	
	/**
	 * Creates a new instance of this class. Objects of this type should not be instantiated directly but an inherited
	 * class should be used instead.
	 * 
	 * @param source - the TIC component triggering this event
	 * @param time - the time (in milliseconds) when the event happened
	 * @param modifiers - the modifier keys in AWT format (see constants in {@link KeyEvent})
	 */
	public TICInputEvent(TICComponent source, long time, int modifiers) {
		super(source);
		this.time = time;
		this.modifiers = modifiers;
	}
	
	
  @Override
	public TICComponent getSource() {
		return (TICComponent)super.getSource();
	}


	public long getTime() {
		return time;
	}

	
	public int getModifiers() {
		return modifiers;
	}
	
	
	/**
	 * Determines whether a shift button was pressed during this event.
	 * 
	 * @return {@code true} if the button was pressed while this event happened, {@code false otherwise}.
	 */
	public boolean isShiftDown() {
		return (modifiers & InputEvent.SHIFT_DOWN_MASK) != 0;
	}
	
	
	/**
	 * Determines whether a control button was pressed during this event.
	 * 
	 * @return {@code true} if the button was pressed while this event happened, {@code false otherwise}.
	 */
	public boolean isControlDown() {
		return (modifiers & InputEvent.CTRL_DOWN_MASK) != 0;
	}
	
	
	/**
	 * Determines whether the alt button was pressed during this event.
	 * 
	 * @return {@code true} if the button was pressed while this event happened, {@code false otherwise}.
	 */
	public boolean isAltDown() {
		return (modifiers & InputEvent.ALT_DOWN_MASK) != 0;
	}
	
	
	/**
	 * Determines whether the alt graph button was pressed during this event.
	 * <p>
	 * <i>Note that in the current implementation this method will always return {@code false} if this event was triggered
	 * by a SWT component.</i>  
	 * 
	 * @return {@code true} if the button was pressed while this event happened, {@code false otherwise}.
	 */
	public boolean isAltGraphDown() {
		return (modifiers & InputEvent.ALT_GRAPH_DOWN_MASK) != 0;
	}
	
	
	/**
	 * Determines platform independent whether a meta button was pressed during this event.
	 * <p>
	 * <i>Note that in the current implementation this method will always return {@code false} if this event was triggered
	 * by a SWT component.</i>  
	 * 
	 * @return {@code true} if the button was pressed while this event happened, {@code false otherwise}.
	 */
	public boolean isMetaDown() {
		return (modifiers & InputEvent.META_DOWN_MASK) != 0;
	}
	
	
	/**
	 * Determines whether mouse button 1 was pressed during this event.
	 * 
	 * @return {@code true} if the button was pressed while this event happened, {@code false otherwise}.
	 */
	public boolean isMouseButton1Down() {
		return (modifiers & InputEvent.BUTTON1_DOWN_MASK) != 0;
	}
	
	
	/**
	 * Determines whether mouse button 2 was pressed during this event.
	 * 
	 * @return {@code true} if the button was pressed while this event happened, {@code false otherwise}.
	 */
	public boolean isMouseButton2Down() {
		return (modifiers & InputEvent.BUTTON2_DOWN_MASK) != 0;
	}
	
	
	/**
	 * Determines whether mouse button 3 was pressed during this event.
	 * 
	 * @return {@code true} if the button was pressed while this event happened, {@code false otherwise}.
	 */
	public boolean isMouseButton3Down() {
		return (modifiers & InputEvent.BUTTON3_DOWN_MASK) != 0;
	}
	
	
	/**
	 * Tests whether the menu shortcut key is pressed (<i>Command</i> on Mac and <i>Control</i> on the other OS).
	 * 
	 * @return {@code true} if the button was pressed while this event happened, {@code false otherwise}.
	 */
	public boolean isMenuShortcutKeyDown() {
		return (isMetaDown() && SystemUtils.IS_OS_MAC) || (isControlDown()&&  !SystemUtils.IS_OS_MAC);  // This workaround needs to be done, because getMenuShortcutKeyMask() does not return extended modifiers.
	}
}
