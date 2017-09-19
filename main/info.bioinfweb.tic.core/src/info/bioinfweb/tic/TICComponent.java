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
import info.bioinfweb.tic.input.TICInputEvent;
import info.bioinfweb.tic.input.TICKeyEvent;
import info.bioinfweb.tic.input.TICListenerSet;
import info.bioinfweb.tic.input.TICKeyListener;
import info.bioinfweb.tic.input.TICMouseEvent;
import info.bioinfweb.tic.input.TICMouseListener;
import info.bioinfweb.tic.input.TICMouseWheelEvent;
import info.bioinfweb.tic.input.TICMouseWheelListener;
import info.bioinfweb.tic.scrolling.ScrollingTICComponent;
import info.bioinfweb.tic.toolkit.ScrollingToolkitComponent;
import info.bioinfweb.tic.toolkit.ToolkitComponent;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.Collections;
import java.util.EventListener;
import java.util.HashMap;
import java.util.Map;



/**
 * This is the parent class of all GUI components based in <i>TIC</i>. Inherited classes are toolkit independent GUI 
 * components that can be used in both <i>Swing</i> and <i>SWT</i> GUIs. To create toolkit specific instances of inherited
 * classes {@code SwingComponentFactory} and {@code SWTComponentFactory} from the respective <i>TIC</i> modules 
 * can be used.
 * 
 * <h3><a id="create"></a>Creating <i>TIC</i> components</h3> 
 * <p>
 * To create a concrete <i>TIC</i> component either the {@link #paint(TICPaintEvent)} method must be overwritten 
 * with code painting the component or toolkit specific classes for <i>Swing</i> and <i>SWT</i> that do not need
 * a toolkit-independent paint method must be provided by overwriting {@link #getSwingComponentClassName(Object...)} 
 * and {@link #getSWTComponentClassName(Object...)()}.
 * <p>
 * {@link #getSize()} should be overwritten if a component requires a certain (minimal) size.  
 * <p>
 * Note that depending on your GUI design {@link #assignSize()} might not have the desired effect if 
 * {@link #assignSizeToSWTLayoutData(org.eclipse.swt.graphics.Point, Composite)} is not overwritten with an 
 * respective implementation.
 * 
 * <h3><a id="input"></a>Mouse and keyboard events</h3> 
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
 * @see ScrollingTICComponent
 * @see ToolkitComponent
 * @bioinfweb.module info.bioinfweb.tic.core
 */
public abstract class TICComponent {
	private static interface EventDispatcher {
		public boolean dispatch(TICInputEvent event, EventListener listener);
	}
	
	
	protected static final Map<Integer, EventDispatcher> DISPATCHER_MAP = fillDispatcherMap();
	
	
	private static Map<Integer, EventDispatcher> fillDispatcherMap() {
		Map<Integer, EventDispatcher> result = new HashMap<>();
		result.put(KeyEvent.KEY_PRESSED,        (e, l) -> ((TICKeyListener)l).keyPressed((TICKeyEvent)e));
		result.put(KeyEvent.KEY_RELEASED,       (e, l) -> ((TICKeyListener)l).keyReleased((TICKeyEvent)e));
		result.put(MouseEvent.MOUSE_PRESSED,    (e, l) -> ((TICMouseListener)l).mousePressed((TICMouseEvent)e));
		result.put(MouseEvent.MOUSE_RELEASED,   (e, l) -> ((TICMouseListener)l).mouseReleased((TICMouseEvent)e));
		result.put(MouseEvent.MOUSE_MOVED,      (e, l) -> ((TICMouseListener)l).mouseMoved((TICMouseEvent)e));
		result.put(MouseEvent.MOUSE_ENTERED,    (e, l) -> ((TICMouseListener)l).mouseEntered((TICMouseEvent)e));
		result.put(MouseEvent.MOUSE_EXITED,     (e, l) -> ((TICMouseListener)l).mouseExited((TICMouseEvent)e));
		result.put(MouseEvent.MOUSE_DRAGGED,    (e, l) -> ((TICMouseListener)l).mouseDragged((TICMouseEvent)e));
		result.put(MouseWheelEvent.MOUSE_WHEEL, (e, l) -> ((TICMouseWheelListener)l).mouseWheelMoved((TICMouseWheelEvent)e));
		return Collections.unmodifiableMap(result);
	}
	
	
	private ToolkitComponent toolkitComponent = null;
	private TICListenerSet<TICKeyListener> keyListenersSet = new TICListenerSet<TICKeyListener>(this);
	private TICListenerSet<TICMouseListener> mouseListenersSet = new TICListenerSet<TICMouseListener>(this);
	private TICListenerSet<TICMouseWheelListener> mouseWheelListenersSet = new TICListenerSet<TICMouseWheelListener>(this);
	private boolean updateOngoing = false;
	protected boolean repaintRequested = false;  // Inherited classes may have the need to access this field.
	
	
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
	 * painting method is provided. The coordinates in the provided context are relative to this component, 
	 * (0, 0) represents the top left corner of this area.
	 * <p>
	 * If implementing classes provide custom toolkit specific components by overwriting 
	 * {@link #getSwingComponentClassName(Object...)} and {@link #getSwingComponentClassName(Object...)}
	 * the implementation of this method may remain empty.
	 * <p>
	 * This default implementation is empty. Inherited classes must either overwrite it or overwrite
	 * {@link #getSwingComponentClassName(Object...)} and {@link #getSWTComponentClassName(Object...)}
	 * to provide toolkit components that do not need an implementation of this method.
	 * 
	 * @param event the paint event providing the graphics context and information on which part of the 
	 *        component should be repainted
	 */
	public void paint(TICPaintEvent event) {}
	
	
	/**
	 * Returns the size this object needs to be painted completely.
	 * <p>
	 * Components may specify their minimal size or the only acceptable fixed size here. The respective 
	 * toolkit components should be implemented respectively. Returning {@code null} if the component 
	 * does not prefer any size is also valid.
	 * <p>
	 * Note that this size may differ from the actual size of the toolkit component, e.g. if it specifies
	 * a preferred size.
	 * <p>
	 * This default implementation always returns {@code null}.
	 * 
	 * @return the dimension in pixels needed to completely paint this component or {@code null}
	 * @see ToolkitComponent#getToolkitSize()
	 */
	public Dimension getSize() {
		return null;
	}
	
	
	/**
	 * Determines whether this component specifies a defined size. See {@link #getSize()} for details.
	 * 
	 * @return {@code true} if this component returns a defined size in {@link #getSize()} or {@code false} if
	 *         this component does not have any preferred size and {@link #getSize()} returns {@code null}
	 */
	public boolean hasDefinedSize() {
		return getSize() != null;
	}
	
	
	/**
	 * Determines whether the contents displayed by this component are currently ongoing.
	 * <p> 
	 * {@link #repaint()} makes use of this property to avoid unnecessary repaint operations. 
	 * 
	 * @return {@code true} if an update is currently ongoing, {@code false} otherwise
	 * @since 0.5.0
	 */
	public boolean isUpdateOngoing() {
		return updateOngoing;
	}


	/**
	 * Allows to specify whether the contents displayed by this component are currently ongoing.
	 * <p> 
	 * {@link #repaint()} makes use of this property to avoid unnecessary repaint operations. If
	 * this property is changed from {@code true} to {@code false}, the component will be repainted
	 * directly, if that was previously requested.
	 *  
	 * @param updateOngoing {@code true} if an update is currently ongoing, {@code false} otherwise
	 */
	public void setUpdateOngoing(boolean updateOngoing) {
		if (this.updateOngoing != updateOngoing) {
			this.updateOngoing = updateOngoing;
			if (!updateOngoing && repaintRequested) {
				repaint();
			}
		}
	}


	/**
	 * Forces the underlying toolkit component to be repainted. This method will only have an effect 
	 * if a toolkit specific component has already been assigned ({@link #hasToolkitComponent()} return
	 * {@code true}).
	 * <p>
	 * If {@link #isUpdateOngoing()} is set to {@code true}, calls of this method will not cause a direct
	 * repaint. The request for repainting is stored internally by this instance and it will be repainted
	 * when {@link #setUpdateOngoing(boolean)} is set to {@code false} the next time. (If this method was
	 * called multiple times before that, still only one repaint operation will be performed.)
	 */
	public void repaint() {
		if (hasToolkitComponent()) {
			if (isUpdateOngoing()) {
				repaintRequested = true;
			}
			else {
				getToolkitComponent().repaint();
			}
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
	 * @param listener the object to listen to key events
	 */
	public void addKeyListener(TICKeyListener listener) {
		keyListenersSet.getListeners().add(listener);
	}
	
	
	/**
	 * Removes the specified key listener from this component (and from the underlying toolkit specific component).
	 * 
	 * @param listener the listener to be removed
	 * @return {@code true} if the specified lister was contained in the list, {@code false} otherwise
	 */
	public boolean removeKeyListener(TICKeyListener listener) {
		return keyListenersSet.getListeners().remove(listener);
	}
	
	
	/**
	 * Adds a mouse listener to this component (and automatically to the underlying toolkit specific component).
	 * 
	 * @param listener the object to listen to mouse events
	 * @see #addMouseWheelListener(TICMouseWheelListener)
	 */
	public void addMouseListener(TICMouseListener listener) {
		mouseListenersSet.getListeners().add(listener);
	}
	
	
	/**
	 * Removes the specified mouse listener from this component (and from the underlying toolkit specific component).
	 * 
	 * @param listener the listener to be removed
	 * @return {@code true} if the specified lister was contained in the list, {@code false} otherwise
	 */
	public boolean removeMouseListener(TICMouseListener listener) {
		return mouseListenersSet.getListeners().remove(listener);
	}
	
	
	/**
	 * Adds a mouse wheel listener to this component (and automatically to the underlying toolkit specific component).
	 * 
	 * @param listener the object to listen to mouse wheel events
	 * @see #addMouseListener(TICMouseListener)
	 */
	public void addMouseWheelListener(TICMouseWheelListener listener) {
		mouseWheelListenersSet.getListeners().add(listener);
	}
	
	
	/**
	 * Removes the specified mouse wheel listener from this component (and from the underlying toolkit specific component).
	 * 
	 * @param listener the listener to be removed
	 * @return {@code true} if the specified lister was contained in the list, {@code false} otherwise
	 */
	public boolean removeWheelMouseListener(TICMouseWheelListener listener) {
		return mouseWheelListenersSet.getListeners().remove(listener);
	}
	
	
	public boolean dispatchEvent(TICInputEvent event) {
		TICListenerSet<?> set = null;
		if (event instanceof TICKeyEvent) {
			set = keyListenersSet;
		}
		else if (event instanceof TICMouseWheelEvent) {  // Must be tested before TICMouseEvent.
			set = mouseWheelListenersSet;
		}
		else if (event instanceof TICMouseEvent) {
			set = mouseListenersSet;
		}
		
		if (set != null) {
			boolean consumed = false;
			EventDispatcher d = DISPATCHER_MAP.get(event.getID());
			for (EventListener listener : set.getListeners()) {
				consumed = consumed || d.dispatch(event, listener);
			}
			return consumed;
		}
		else {
			return false;
		}
	}
}
