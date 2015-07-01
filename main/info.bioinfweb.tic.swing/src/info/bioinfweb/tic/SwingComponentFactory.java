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
package info.bioinfweb.tic;


import info.bioinfweb.tic.exception.ToolkitSpecificInstantiationException;
import info.bioinfweb.tic.input.SwingKeyEventForwarder;
import info.bioinfweb.tic.input.SwingMouseEventForwarder;
import info.bioinfweb.tic.input.SwingMouseWheelEventForwarder;
import info.bioinfweb.tic.toolkit.ToolkitComponent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;

import javax.swing.JComponent;



/**
 * Factory that allows to create Swing GUI component instances from instances of {@link TICComponent}.
 * 
 * @author Ben St&ouml;ver
 * @since 2.0.0
 */
public class SwingComponentFactory {
	private static SwingComponentFactory firstInstance = null;
	
	
	private SwingComponentFactory() {
		super();
	}
	
	
	public static SwingComponentFactory getInstance() {
		if (firstInstance == null) {
			firstInstance = new SwingComponentFactory();
		}
		return firstInstance;
	}


	private JComponent createSwingComponent(TICComponent ticComponent) {
		try {
			Constructor<?>[] constructors = Class.forName(ticComponent.getSwingComponentClassName()).getConstructors();
			for (int i = 0; i < constructors.length; i++) {
				Parameter[] parameters = constructors[i].getParameters();
				if ((parameters.length == 1) && parameters[0].getType().isAssignableFrom(ticComponent.getClass())) {
					JComponent result = (JComponent)constructors[i].newInstance(ticComponent);
					if (result instanceof ToolkitComponent) {
						return result;
					}
					else {
						throw new ToolkitSpecificInstantiationException("The constructed instance of type " + 
								result.getClass().getCanonicalName() + " does not implement " + ToolkitComponent.class.getCanonicalName() + 
								".");
					}
				}
			}
			throw new ToolkitSpecificInstantiationException("No according constructor found for " + 
					ticComponent.getSwingComponentClassName());
		} 
		catch (InstantiationException e) {
			throw new ToolkitSpecificInstantiationException(e);
		} 
		catch (IllegalAccessException e) {
			throw new ToolkitSpecificInstantiationException(e);
		} 
		catch (IllegalArgumentException e) {
			throw new ToolkitSpecificInstantiationException(e);
		} 
		catch (InvocationTargetException e) {
			throw new ToolkitSpecificInstantiationException(e);
		} 
		catch (SecurityException e) {
			throw new ToolkitSpecificInstantiationException(e);
		} 
		catch (ClassNotFoundException e) {
			throw new ToolkitSpecificInstantiationException(e);
		}
	}
	
	
	/**
	 * Creates the Swing component that will be associated with the specified TIC component if it was 
	 * not created before. The returned instance will be returned by {@link #getToolkitComponent()} 
	 * from now on. Subsequent calls of this method will return the same instance again. 
	 * <p>
	 * Note that this method can only be called if no SWT component has been created for the specified
	 * TIC component before.
	 * 
	 * @return the associated Swing component that has been created
	 * @throws IllegalStateException if an SWT component has already been created for {@code ticComponent} 
	 */
	public JComponent getSwingComponent(TICComponent ticComponent) {
		if (!ticComponent.hasToolkitComponent()) {
			JComponent component = createSwingComponent(ticComponent);
			
			component.addKeyListener(new SwingKeyEventForwarder(ticComponent.getKeyListenersSet()));
			SwingMouseEventForwarder mouseListeners = new SwingMouseEventForwarder(ticComponent.getMouseListenersSet()); 
			component.addMouseListener(mouseListeners);
			component.addMouseMotionListener(mouseListeners);
			component.addMouseWheelListener(new SwingMouseWheelEventForwarder(ticComponent.getMouseWheelListenersSet()));
			
			ticComponent.setToolkitComponent((ToolkitComponent)component);
		}
		else if (!ticComponent.getCurrentToolkit().equals(TargetToolkit.SWING)) {
			throw new IllegalStateException("A non Swing component has already been created.");
		}
		return (JComponent)ticComponent.getToolkitComponent();
	}
}
