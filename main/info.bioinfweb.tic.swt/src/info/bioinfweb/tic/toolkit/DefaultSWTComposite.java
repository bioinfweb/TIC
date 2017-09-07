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


import info.bioinfweb.tic.TICComponent;
import info.bioinfweb.tic.TICPaintEvent;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.PaletteData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;



/**
 * The default <i>SWT</i> widget implementation to be associated with a {@link TICComponent}.
 * <p>
 * It uses the {@link TICComponent#paint(TICPaintEvent)} to draw the widget.
 * <p>
 * Since <i>TIC</i> 3.0.0 optionally shifting the paint coordinate origin is possible to model direct scrolling 
 * (without being nested within a scroll container). This is especially relevant for large components in 
 * <i>SWT</i> since the component size is limited to 32767 (2^15 - 1) on some operating systems. For components 
 * that directly implement scrolling behavior the properties {@link #getScrollOffsetX()} and 
 * {@link #getScrollOffsetY()} can be used.
 * 
 * @author Ben St&ouml;ver
 * @bioinfweb.module info.bioinfweb.tic.swt
 */
public class DefaultSWTComposite extends AbstractSWTWidget {
	public DefaultSWTComposite(TICComponent ticComponent, Composite parent, int style) {
		super(ticComponent, parent, style);
		assignSize();
	}
	
	
	@Override
	public Control getSWTComponent() {
		return this;
	}


	protected int getScrollOffsetX() {
		return 0;
	}


	protected int getScrollOffsetY() {
		return 0;
	}


	/**
	 * Fire a <i>TIC</i> paint event internally to let the implementing class draw on a buffered image and than draws
	 * that image into the <i>SWT</i> graphics context.
	 * <p>
	 * Note that the <i>TIC</i> paint event will always cover a rectangle which is extended by one pixel to the left
	 * and top compared to the <i>SWT</i> paint event. That is done to solve problems with anti-aliased lines passing
	 * the bounds of that rectangle.
	 */
	@Override
	public void paintControl(PaintEvent e) {
	  // Scroll position could be considered here by shifting the event and paint coordinates. This would have to be done in a non-LibrAlign-specific way.
		
		BufferedImage refreshArea = new BufferedImage(e.width + 1, e.height + 1, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D g = refreshArea.createGraphics();
		try {
			double offsetX = 1 + getScrollOffsetX();
			double offsetY = 1 + getScrollOffsetY();
			g.translate(-e.x + offsetX, -e.y + offsetY);
			getIndependentComponent().paint(new TICPaintEvent(this, g, new Rectangle2D.Double(
					e.x - offsetX, e.y - offsetY, e.width + 1, e.height + 1)));
      ImageData data = new ImageData(refreshArea.getWidth(), refreshArea.getHeight(), 24, 
      		new PaletteData(0xff, 0xff00, 0xff0000), 3 * refreshArea.getWidth(), 
      		((DataBufferByte)refreshArea.getRaster().getDataBuffer()).getData());
      e.gc.drawImage(new Image(e.gc.getDevice(), data), e.x - 1, e.y - 1);
		}
		finally {
			g.dispose();
		}
	}


	@Override
	public Point computeSize(int wHint, int hHint) {
		Dimension d = getIndependentComponent().getSize();
		return new Point(d.width, d.height);
	}


	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		return computeSize(wHint, hHint);
	}
}
