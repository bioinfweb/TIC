/*
 * LibrAlign - A GUI library for displaying and editing multiple sequence alignments and attached data
 * Copyright (C) 2014-2015  Ben Stöver
 * <http://bioinfweb.info/LibrAlign>
 * 
 * This file is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This file is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package info.bioinfweb.tic.input;


import info.bioinfweb.tic.TargetToolkit;

import java.awt.event.MouseEvent;
import java.util.EventListener;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;



public abstract class AbstractEventForwarder<L extends EventListener> {
	private TICListenerSet<L> listenerSet;

	
	public AbstractEventForwarder(TICListenerSet<L> listenerSet) {
		super();
		this.listenerSet = listenerSet;
	}


	public TICListenerSet<L> getListenerSet() {
		return listenerSet;
	}
	
	
	protected void forwardMouseEventToParent(MouseEvent event, boolean consumed) {
		if (getListenerSet().getOwner().getCurrentToolkit().equals(TargetToolkit.SWING) && !consumed) {
			JComponent component = (JComponent)getListenerSet().getOwner().getToolkitComponent(); 
			component.getParent().dispatchEvent(SwingUtilities.convertMouseEvent(
					component, event, component.getParent()));
		}
	}
}