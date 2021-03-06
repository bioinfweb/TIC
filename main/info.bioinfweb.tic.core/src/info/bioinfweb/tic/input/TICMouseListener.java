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


import java.util.EventListener;

import info.bioinfweb.tic.TICComponent;



/**
 * Implement this interface to listen to mouse events of {@link TICComponent}s.
 * 
 * @author Ben St&ouml;ver
 * @bioinfweb.module info.bioinfweb.tic.core
 */
public interface TICMouseListener extends EventListener {
  /**
   * This method is called when a mouse button is pressed down.
   * 
   * @param event the object containing information about the event 
   * @return {@code true} if the event was consumed by this listener, {@code false otherwise} (See 
   *         {@link TICComponent} for information on how the return value is used.) 
   */
  public boolean mousePressed(TICMouseEvent event);
  
  /**
   * This method is called when a mouse button is released.
   * 
   * @param event - the object containing information about the event 
   * @return {@code true} if the event was consumed by this listener, {@code false otherwise} (See 
   *         {@link TICComponent} for information on how the return value is used.) 
   */
  public boolean mouseReleased(TICMouseEvent event);
  
  /**
   * This method is called when a mouse button is pressed down.
   * 
   * @param event the object containing information about the event 
   * @return {@code true} if the event was consumed by this listener, {@code false otherwise} (See 
   *         {@link TICComponent} for information on how the return value is used.) 
   */
  public boolean mouseEntered(TICMouseEvent event);
  
  /**
   * This method is called when a mouse button is pressed down.
   * 
   * @param event the object containing information about the event 
   * @return {@code true} if the event was consumed by this listener, {@code false otherwise} (See 
   *         {@link TICComponent} for information on how the return value is used.) 
   */
  public boolean mouseExited(TICMouseEvent event);
  
  /**
   * This method is called if the mouse is moved over the component while no mouse button is pressed.
   * If a button is pressed, {@link #mouseDragged(TICMouseEvent)} is called instead.
   * <p>
   * In contrast to drag events this event is only fired, if the mouse is located over the component. 
   * 
   * @param event - the object containing information about the event 
   * @return {@code true} if the event was consumed by this listener, {@code false otherwise} (See 
   *         {@link TICComponent} for information on how the return value is used.) 
   */
  public boolean mouseMoved(TICMouseEvent event);
  
  /**
   * This method is called if the mouse is moved over the component while a mouse button is held down.
   * If no button is pressed, {@link #mouseMoved(TICMouseEvent)} is called instead. 
   * <p>
   * Note that drag events are still fired if the mouse has already left the component until the according mouse 
   * button is released.
   * 
   * @param event - the object containing information about the event 
   * @return {@code true} if the event was consumed by this listener, {@code false otherwise} (See 
   *         {@link TICComponent} for information on how the return value is used.) 
   */
  public boolean mouseDragged(TICMouseEvent event);
}
