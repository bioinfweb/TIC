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



/**
 * An <i>TIC</i> mouse event listener that forwards all received {@link TICMouseEvent}s to a set of other 
 * {@link TICMouseListener}s.
 * 
 * @author Ben St&ouml;ver
 * @since 3.0.0
 * @bioinfweb.module info.bioinfweb.tic.core
 */
public class TICMouseEventForwarder extends AbstractEventForwarder<TICMouseListener> implements TICMouseListener {
	public TICMouseEventForwarder(TICListenerSet<TICMouseListener> listenerSet) {
		super(listenerSet);
	}


	@Override
	public boolean mousePressed(TICMouseEvent event) {
		boolean consumed = false;
		for (TICMouseListener listener: getListenerSet().getListeners()) {
			consumed = consumed || listener.mousePressed(event);
		}
		return consumed;
	}


	@Override
	public boolean mouseReleased(TICMouseEvent event) {
		boolean consumed = false;
		for (TICMouseListener listener: getListenerSet().getListeners()) {
			consumed = consumed || listener.mouseReleased(event);
		}
		return consumed;
	}


	@Override
	public boolean mouseEntered(TICMouseEvent event) {
		boolean consumed = false;
		for (TICMouseListener listener: getListenerSet().getListeners()) {
			consumed = consumed || listener.mouseEntered(event);
		}
		return consumed;
	}


	@Override
	public boolean mouseExited(TICMouseEvent event) {
		boolean consumed = false;
		for (TICMouseListener listener: getListenerSet().getListeners()) {
			consumed = consumed || listener.mouseExited(event);
		}
		return consumed;
	}


	@Override
	public boolean mouseMoved(TICMouseEvent event) {
		boolean consumed = false;
		for (TICMouseListener listener: getListenerSet().getListeners()) {
			consumed = consumed || listener.mouseMoved(event);
		}
		return consumed;
	}


	@Override
	public boolean mouseDragged(TICMouseEvent event) {
		boolean consumed = false;
		for (TICMouseListener listener: getListenerSet().getListeners()) {
			consumed = consumed || listener.mouseDragged(event);
		}
		return consumed;
	}
}
