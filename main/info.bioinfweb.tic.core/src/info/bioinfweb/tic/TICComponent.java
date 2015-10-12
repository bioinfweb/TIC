/*
 * bioinfweb.commons - Shared components of bioinfweb projects made available in a Java library
 * Copyright (C) 2010-2014  Ben Stöver
 * <http://commons.bioinfweb.info/Java>
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
package info.bioinfweb.tic;


import info.bioinfweb.tic.TICPaintEvent;
import info.bioinfweb.tic.TargetToolkit;
import info.bioinfweb.tic.input.TICListenerSet;
import info.bioinfweb.tic.input.TICKeyListener;
import info.bioinfweb.tic.input.TICMouseListener;
import info.bioinfweb.tic.input.TICMouseWheelListener;
import info.bioinfweb.tic.toolkit.ToolkitComponent;

import java.awt.Dimension;



/**
 * This is the parent class of all GUI components based in TIC. Inherited classes are toolkit independent GUI 
 * components that can be used in both Swing and SWT GUIs. To create toolkit specific instances of inherited
 * classes {@code SwingComponentFactory} and {@code SWTComponentFactory} from the according TIC modules can be used.
 * <p>
 * To create a concrete TIC component, the abstract methods {@link #paint(TICPaintEvent)} and {@link #getSize()} 
 * need to be implemented. Implementing these methods is sufficient for creating components that paint their
 * contents directly. If toolkit specific versions containing subcomponents from Swing or SWT are created instead,
 * {@link #getSwingComponentClassName()} and {@link #getSWTComponentClassName()} must be overwritten accordingly
 * and the implementation of {@link #paint(TICPaintEvent)} may remain empty.  
 * <p>
 * Note that depending on your GUI design {@link #assignSize()} might not have the desired effect if 
 * {@link #assignSizeToSWTLayoutData(org.eclipse.swt.graphics.Point, Composite)} is not overwritten with an according 
 * implementation.
 * <p>
 * <b>Mouse and keyboard events</b>
 * <p>
 * TIC components support adding TIC event listeners for mouse and keyboard events. Due to the implementation of this 
 * behavior the toolkit specific components always have mouse and key listeners attached, no matter if a TIC listener 
 * was attached to the owning TIC component. In the case of Swing components events will not be forwarded to parent
 * components anymore, even if no listener in this component consumed the event. Therefore TIC components automatically
 * forward events to parent Swing components, if no TIC listener for the according event type consumed that event.
 * TIC listener indicate whether they consumed an event by the return value of their handler methods (e.g. 
 * {@link TICMouseWheelListener#mouseWheelMoved(info.bioinfweb.tic.input.TICMouseWheelEvent)}).
 * <p>
 * If more than one listener for the same event type is attached to a TIC component, all listeners will be informed
 * on events, no matter if a previous listener in the list already consumed the event. (Only forwarding to parent events
 * is influenced by the return value of TIC event listeners.)
 * 
 * @author Ben St&ouml;ver
 */
public abstract class TICComponent {
	private ToolkitComponent toolkitComponent = null;
	private TICListenerSet<TICKeyListener> keyListenersSet = new TICListenerSet<TICKeyListener>(this);
	private TICListenerSet<TICMouseListener> mouseListenersSet = new TICListenerSet<TICMouseListener>(this);
	private TICListenerSet<TICMouseWheelListener> mouseWheelListenersSet = new TICListenerSet<TICMouseWheelListener>(this);
	
	
	/**
	 * Creates a new instance of this class.
	 */
	public TICComponent() {
		super();
	}

	
	/**
	 * Returns the toolkit the associated toolkit class belongs to.  
	 * 
	 * @return the toolkit type or {@link TargetToolkit#UNDEFINED} if no toolkit specific 
	 *         implementation has yet been assigned
	 */
	public TargetToolkit getCurrentToolkit() {
		if (hasToolkitComponent()) {
			return getToolkitComponent().getTargetToolkit();
		}
		else {
			return TargetToolkit.UNDEFINED;
		}
	}

	
	/**
	 * Painting operations of the implementing class should be performed here, if a toolkit independent
	 * painting method is provided. The coordinates in the provided context are relative to this are. 
	 * (0, 0) represents the top left corner of this area.
	 * <p>
	 * If implementing classes provide custom toolkit specific components by overwriting 
	 * {@link #doCreateSwingComponent()} and {@link #doCreateSWTWidget(Composite, int)} the 
	 * implementation of this method can be empty.
	 * 
	 * @param graphics - the graphics context used to perform the paint operations in Swing and SWT
	 */
	public abstract void paint(TICPaintEvent event);
	
	
	/**
	 * Returns the size this objects uses to be painted completely.
	 * <p>
	 * Note that the associated graphical component might be contained in a scroll container and the
	 * actual area displayed in the screen can be smaller than the dimension returned here.
	 * </p>  
	 * 
	 * @return the dimension in pixels
	 */
	public abstract Dimension getSize();
	
	
	/**
	 * Forces the underlying toolkit component to be repainted. This method will only have an effect 
	 * if a toolkit specific component has already been assigned ({@link #hasToolkitComponent()} return
	 * {@code true}).
	 */
	public void repaint() {
		if (hasToolkitComponent()) {
			getToolkitComponent().repaint();
		}
	}
	
	
	/**
	 * Adopts the current component size to the underlying GUI toolkit, if a toolkit specific component
	 * has already been created.
	 * <p>
	 * This methods delegates to {@link ToolkitComponent#assignSize()}, if an underlying toolkit-specific 
	 * component was already created.
	 */
	public void assignSize() {
		if (hasToolkitComponent()) {
			getToolkitComponent().assignSize();
		}
	}
	
	
	/**
	 * Returns a toolkit specific component used to display the contents of this class.
	 * 
	 * @return the toolkit specific component or {@code null} if neither {@link #createSwingComponent()} nor 
	 *         {@link #createSWTWidget(Composite, int)} have been called previously
	 */
	public ToolkitComponent getToolkitComponent() {
		return toolkitComponent;
	}
	
	
	protected void setToolkitComponent(ToolkitComponent toolkitComponent) {
		this.toolkitComponent = toolkitComponent;
	}
	
	
	/**
	 * Checks if an associated toolkit specific component has already been assigned to this instance.
	 * 
	 * @return {@code true} if a component has been assigned, {@code false} otherwise
	 */
	public boolean hasToolkitComponent() {
		return toolkitComponent != null;
	}
	
	
	/**
	 * Method used by {@code SwingComponentFactory} in the Swing module to create the concrete GUI
	 * object for Swing. Inherited classes providing custom Swing specific implementations should
	 * overwrite this method.
	 * <p>
	 * Note that custom implementations need to provider constructors as defined in the documentation
	 * of {@link ToolkitComponent}.
	 * 
	 * @return a fully qualified class name for a class used to create the concrete Swing GUI instance
	 *         associated with this instance
	 */
	protected String getSwingComponentClassName() {
		return "info.bioinfweb.tic.toolkit.DefaultSwingComponent";
	}
	
	
	/**
	 * Method used by {@code SWTComponentFactory} in the SWT module to create the concrete GUI
	 * object for Swing. Inherited classes providing custom Swing specific implementations should
	 * overwrite this method.
	 * <p>
	 * Note that custom implementations need to provider constructors as defined in the documentation
	 * of {@link ToolkitComponent}.
	 * 
	 * @return a fully qualified class name for a class used to create the concrete SWT GUI instance
	 *         associated with this instance
	 */
	protected String getSWTComponentClassName() {
		return "info.bioinfweb.tic.toolkit.DefaultSWTComposite";
	}
	
	
	protected TICListenerSet<TICKeyListener> getKeyListenersSet() {
		return keyListenersSet;
	}


	protected TICListenerSet<TICMouseListener> getMouseListenersSet() {
		return mouseListenersSet;
	}


	protected TICListenerSet<TICMouseWheelListener> getMouseWheelListenersSet() {
		return mouseWheelListenersSet;
	}


	/**
	 * Adds a key listener to this component (and automatically to the underlying toolkit specific component).
	 * 
	 * @param listener - the object to listen to key events
	 */
	public void addKeyListener(TICKeyListener listener) {
		keyListenersSet.getListeners().add(listener);
	}
	
	
	/**
	 * Removes the specified key listener from this component (and from the underlying toolkit specific component).
	 * 
	 * @param listener - the listener to be removed
	 * @return {@code true} if the specified lister was contained in the list, {@code false} otherwise
	 */
	public boolean removeKeyListener(TICKeyListener listener) {
		return keyListenersSet.getListeners().remove(listener);
	}
	
	
	/**
	 * Adds a mouse listener to this component (and automatically to the underlying toolkit specific component).
	 * 
	 * @param listener - the object to listen to mouse events
	 * @see #addMouseWheelListener(TICMouseWheelListener)
	 */
	public void addMouseListener(TICMouseListener listener) {
		mouseListenersSet.getListeners().add(listener);
	}
	
	
	/**
	 * Removes the specified mouse listener from this component (and from the underlying toolkit specific component).
	 * 
	 * @param listener - the listener to be removed
	 * @return {@code true} if the specified lister was contained in the list, {@code false} otherwise
	 */
	public boolean removeMouseListener(TICMouseListener listener) {
		return mouseListenersSet.getListeners().remove(listener);
	}
	
	
	/**
	 * Adds a mouse wheel listener to this component (and automatically to the underlying toolkit specific component).
	 * 
	 * @param listener - the object to listen to mouse wheel events
	 * @see #addMouseListener(TICMouseListener)
	 */
	public void addMouseWheelListener(TICMouseWheelListener listener) {
		mouseWheelListenersSet.getListeners().add(listener);
	}
	
	
	/**
	 * Removes the specified mouse wheel listener from this component (and from the underlying toolkit specific component).
	 * 
	 * @param listener - the listener to be removed
	 * @return {@code true} if the specified lister was contained in the list, {@code false} otherwise
	 */
	public boolean removeWheelMouseListener(TICMouseWheelListener listener) {
		return mouseWheelListenersSet.getListeners().remove(listener);
	}
}
