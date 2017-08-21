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



/**
 * TIC event object that is used to represent a toolkit independent mouse event. 
 * 
 * @author Ben St&ouml;ver
 * @bioinfweb.module info.bioinfweb.tic.core
 */
public class TICMouseEvent extends TICInputEvent {
	private int button;
	private int clickCount;
	private boolean popupTrigger;
	private int componentX;
	private int componentY;
	
	
	public TICMouseEvent(TICComponent source, int id, long time, int modifiers,	int button, int clickCount, boolean popupTrigger, 
			int componentX,	int componentY) {
		
		super(source, id, time, modifiers);
		this.button = button;
		this.clickCount = clickCount;
		this.popupTrigger = popupTrigger;
		this.componentX = componentX;
		this.componentY = componentY;
	}
	
	
	public int getButton() {
		return button;
	}


	public int getClickCount() {
		return clickCount;
	}


	/**
	 * Determines whether is event is the popup trigger of the current platform.
	 * <p>
	 * <i>Note that in the current implementation this method always returns {@code false} in events coming from SWT
	 * components.</i> 
	 * 
	 * @return {@code true} if the current event is a popup trigger, {@code false} otherwise
	 */
	public boolean isPopupTrigger() {
		return popupTrigger;
	}


	public int getComponentX() {
		return componentX;
	}


	public int getComponentY() {
		return componentY;
	}


  /**
   * Creates a copy of this event with all properties set to identical values but with a 
   * different source and shifted x- and y-coordinates.
   * 
   * @param source the source component to be used for the created copy
	 * @param shiftX the shift on x (The new event will have {@code x = getComponentX() + shiftX}.)
	 * @param shiftY the shift on y (The new event will have {@code y = getComponentY() + shiftY}.)
   * @return the modified copy of this instance
	 */
	public TICMouseEvent cloneWithNewSourceTranslated(TICComponent source, int shiftX, int shiftY) {
		TICMouseEvent result = cloneWithNewSource(source);
		result.componentX += shiftX;
		result.componentY += shiftY;
		return result;
	}
	
	
	@Override
	public TICMouseEvent cloneWithNewSource(TICComponent source) {
		return (TICMouseEvent)super.cloneWithNewSource(source);
	}


	@Override
	public TICMouseEvent clone() {
		return (TICMouseEvent)super.clone();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + button;
		result = prime * result + clickCount;
		result = prime * result + componentX;
		result = prime * result + componentY;
		result = prime * result + (popupTrigger ? 1231 : 1237);
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		TICMouseEvent other = (TICMouseEvent) obj;
		if (button != other.button)
			return false;
		if (clickCount != other.clickCount)
			return false;
		if (componentX != other.componentX)
			return false;
		if (componentY != other.componentY)
			return false;
		if (popupTrigger != other.popupTrigger)
			return false;
		return true;
	}
}
