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
 * components that can be used in both <i>Swing</i> and <i>SWT</i> GUIs. To create toolkit specific instances of inherited
 * classes {@code SwingComponentFactory} and {@code SWTComponentFactory} from the according <i>TIC</i> modules 
 * can be used.
 * <p>
 * To create a concrete <i>TIC</i> component, the abstract methods {@link #paint(TICPaintEvent)} and 
 * {@link #getSize()} need to be implemented, which is sufficient for creating components that paint their 
 * contents directly. If toolkit specific classes containing subcomponents from <i>Swing</i> or <i>SWT</i> are created instead,
 * {@link #getSwingComponentClassName()} and {@link #getSWTComponentClassName()} must be overwritten respectively
 * and the implementation of {@link #paint(TICPaintEvent)} may remain empty.  
 * <p>
 * Note that depending on your GUI design {@link #assignSize()} might not have the desired effect if 
 * {@link #assignSizeToSWTLayoutData(org.eclipse.swt.graphics.Point, Composite)} is not overwritten with an 
 * respective implementation.
 * <p>
 * <b>Mouse and keyboard events</b>
 * <p>
 * <i>TIC</i> components support adding <i>TIC</i> event listeners for mouse and keyboard events. Due to the implementation of this 
 * behavior the toolkit specific components always have mouse and key listeners attached, no matter if a <i>TIC</i> listener 
 * was attached to the owning <i>TIC</i> component. In the case of <i>Swing</i> components events will not be forwarded to parent
 * components anymore, even if no listener in this component consumed the event. Therefore <i>TIC</i> components automatically
 * forward events to parent <i>Swing</i> components, if no <i>TIC</i> listener for the according event type consumed that event.
 * <i>TIC</i> listener indicate whether they consumed an event by the return value of their handler methods (e.g. 
 * {@link TICMouseWheelListener#mouseWheelMoved(info.bioinfweb.tic.input.TICMouseWheelEvent)}).
 * <p>
 * If more than one listener for the same event type is attached to a <i>TIC</i> component, all listeners will be informed
 * on events, no matter if a previous listener in the list already consumed the event. (Only forwarding to parent events
 * is influenced by the return value of <i>TIC</i> event listeners.)
 * 
 * @author Ben St&ouml;ver
 * @bioinfweb.module info.bioinfweb.tic.core
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
	 * @param graphics - the graphics context used to perform the paint operations in <i>Swing</i> and <i>SWT</i>
	 */
	public abstract void paint(TICPaintEvent event);
	
	
	/**
	 * Returns the size this object needs to be painted completely.
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
	 * @return the toolkit specific component or {@code null} if neither a <i>Swing</i> nor a <i>SWT</i> component has yet been created 
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
	 * Method used by {@code SwingComponentFactory} in the <i>Swing</i> module to create the concrete GUI
	 * object for <i>Swing</i>. Inherited classes providing custom <i>Swing</i> specific implementations should
	 * overwrite this method.
	 * <p>
	 * Note that custom implementations need to provide constructors as defined in the documentation
	 * of {@link ToolkitComponent}.
	 * 
	 * @param parameters an optional list of parameters that were passed to the {@code getSwingComponent()}
	 *        method of {@code SwingComponentFactory}
	 * @return a fully qualified class name for a class used to create the concrete <i>Swing</i> GUI instance
	 *         associated with this instance
	 */
	protected String getSwingComponentClassName(Object... parameters) {
		return "info.bioinfweb.tic.toolkit.DefaultSwingComponent";
	}
	
	
	/**
	 * Inherited classes can overwrite this method if their <i>Swing</i> component needs constructor parameters
	 * in addition to the owning {@link TICComponent}.
	 * <p>
	 * The default implementation of this method returns an empty array an can be left unchanged for all
	 * <i>Swing</i> components that do not need additional constructor parameters.
	 * 
	 * @param parameters an optional list of parameters that were passed to the {@code getSwingComponent()}
	 *        method of {@code SwingComponentFactory}
	 * @return the list of additional parameters to be passed to the constructor of the <i>Swing</i> component 
	 *         defined by the return value of {@link #getSwingComponentClassName(Object...)}
	 * @since 3.0.0 
	 */
	protected Object[] getSwingComponentConstructorParameters(Object... parameters) {
		return new Object[0];
	}
	
	
	/**
	 * Method used by {@code SWTComponentFactory} in the <i>SWT</i> module to create the concrete GUI
	 * object for <i>SWT</i>. Inherited classes providing custom <i>SWT</i> specific implementations should
	 * overwrite this method.
	 * <p>
	 * Note that custom implementations need to provide constructors as defined in the documentation
	 * of {@link ToolkitComponent}.
	 * 
	 * @param parameters an optional list of parameters that were passed to the {@code getSWTComponent()}
	 *        method of {@code SWTComponentFactory}
	 * @return a fully qualified class name for a class used to create the concrete <i>SWT</i> GUI instance
	 *         associated with this instance
	 */
	protected String getSWTComponentClassName(Object... parameters) {
		return "info.bioinfweb.tic.toolkit.DefaultSWTComposite";
	}
	
	
	/**
	 * Inherited classes can overwrite this method if their <i>SWT</i> component needs constructor parameters
	 * in addition to the owning {@link TICComponent}, the parent composite and the <i>SWT</i> style.
	 * <p>
	 * The default implementation of this method returns an empty array an can be left unchanged for all
	 * <i>SWT</i> components that do not need additional constructor parameters.
	 * 
	 * @param parameters an optional list of parameters that were passed to the {@code getSWTComponent()}
	 *        method of {@code SWTComponentFactory}
	 * @return the list of additional parameters to be passed to the constructor of the <i>Swing</i> component 
	 *         defined by the return value of {@link #getSwingComponentClassName(Object...)} 
	 * @since 3.0.0 
	 */
	protected Object[] getSWTComponentConstructorParameters(Object... parameters) {
		return new Object[0];
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
