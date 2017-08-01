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
package info.bioinfweb.tic;


import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.EventObject;



/**
 * Event that notifies implementations of {@link PaintableArea} that a part of their associated
 * components have to be repainted. 
 * 
 * @author Ben St&ouml;ver
 * @since 1.0.0
 * @bioinfweb.module info.bioinfweb.tic.core
 */
public class TICPaintEvent extends EventObject {
  private Graphics2D graphics;
  private Rectangle2D rectangle;
  
  
	/**
	 * Creates a new instance of this class.
	 * 
	 * @param source the object that triggered the event
	 * @param graphics the swing graphics context
	 * @param rectangle the rectangle that has to be repainted
	 * 
	 * @throws IllegalArgumentException if {@code source}, {@code graphics} or {@code rectangle} are {@code null} 
	 */
	public TICPaintEvent(Object source, Graphics2D graphics, Rectangle2D rectangle) {
		super(source);
		if (graphics == null) {
			throw new IllegalArgumentException("The graphics context must not be null.");
		}
		else if (rectangle == null) {
			throw new IllegalArgumentException("The rectangle must not be null.");
		}
		else {
			this.graphics = graphics;
			this.rectangle = rectangle;
		}
	}
	

	/**
	 * Returns the graphic context to paint on. It is guaranteed that this method will always return
	 * a graphic context independent of the toolkit that is currently used.
	 * 
	 * @return the {@link Graphics2D} object of the associated swing component or the adapter class of
	 *         the associated SWT component
	 */
	public Graphics2D getGraphics() {
		return graphics;
	}

	
	/**
	 * Returns the rectangle that needs to be repainted.
	 * 
	 * @return the area to be painted determined the associated <i>Swing</i> or <i>SWT</i> class 
	 */
	public Rectangle2D getRectangle() {
		return rectangle;
	}
}
