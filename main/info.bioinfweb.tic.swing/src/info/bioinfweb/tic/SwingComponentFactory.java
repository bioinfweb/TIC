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


import info.bioinfweb.tic.exception.ToolkitSpecificInstantiationException;
import info.bioinfweb.tic.input.SwingKeyEventForwarder;
import info.bioinfweb.tic.input.SwingMouseEventForwarder;
import info.bioinfweb.tic.input.SwingMouseWheelEventForwarder;
import info.bioinfweb.tic.toolkit.ToolkitComponent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import javax.swing.JComponent;

import org.apache.commons.lang3.reflect.ConstructorUtils;



/**
 * Factory that allows to create Swing GUI component instances from instances of {@link TICComponent}.
 * <p>
 * The following example shows how to create Swing-specific GUI components from a TIC component.
 * <pre>
 * // Create an instance of a TIC component:
 * TICComponent ticComponent = new SomeComponent();
 * 
 * // Use this factory to create a Swing-specific instance from it:
 * JComponent swingComponent = SwingComponentFactory.getInstance().getSwingComponent(ticComponent);
 * 
 * // swingComponent can now be used in any Swing GUI.
 * </pre>
 * See {@link #getSwingComponent(TICComponent)} for further details.
 * 
 * @author Ben St&ouml;ver
 * @since 2.0.0
 * @bioinfweb.module info.bioinfweb.tic.swing
 */
public class SwingComponentFactory {
	private static final int MINIMAL_NUMBER_OF_CONSTRUCTOR_PARAMETERS = 1;
	
	
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


	private JComponent createSwingComponent(TICComponent ticComponent, Object... factoryParameters) {
		try {
			Class<?> componentClass = Class.forName(ticComponent.getSwingComponentClassName(factoryParameters));
			if (!JComponent.class.isAssignableFrom(componentClass)) {
				throw new ToolkitSpecificInstantiationException("The constructed instance of type " +
						componentClass.getCanonicalName() + " does not inherit from " + JComponent.class.getCanonicalName() + ".");
			}
			else if (!ToolkitComponent.class.isAssignableFrom(componentClass)) {
				throw new ToolkitSpecificInstantiationException("The constructed instance of type " +
						componentClass.getCanonicalName() + " does not implement " + ToolkitComponent.class.getCanonicalName() + ".");
			}
			else {
				// Create list of possible additional constructor parameters:
				Object[] constructorParameters = ticComponent.getSwingComponentConstructorParameters(factoryParameters);
				
				// Create combined array with all constructor parameter values:
				Object[] parameterValues = new Object[constructorParameters.length + MINIMAL_NUMBER_OF_CONSTRUCTOR_PARAMETERS];
				parameterValues[0] = ticComponent;
				for (int i = 0; i < constructorParameters.length; i++) {
					parameterValues[i + MINIMAL_NUMBER_OF_CONSTRUCTOR_PARAMETERS] = constructorParameters[i];
				}
	
				// Create array with all constructor parameter classes:
				Class<?>[] parameterClasses = new Class[parameterValues.length];
				for (int i = 0; i < parameterValues.length; i++) {
					parameterClasses[i] = parameterValues[i].getClass();
				}
				
				// Create component instance:
				try {
					Constructor<?> constructor = ConstructorUtils.getMatchingAccessibleConstructor(componentClass, parameterClasses);
					if (constructor != null) {
						return (JComponent)constructor.newInstance(parameterValues);
					}
					else {
						throw new ToolkitSpecificInstantiationException("No constructor accepting the parameter types " +
								Arrays.toString(parameterClasses) + " or supertypes of these was found in " + 
								componentClass.getCanonicalName() + ".");
					}
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
			}
		}
		catch (ClassNotFoundException e) {
			throw new ToolkitSpecificInstantiationException(e);
		}
	}
	
	
	/**
	 * Creates the Swing component that will be associated with the specified TIC component if it was 
	 * not created before. The created instance will be returned by {@link #getToolkitComponent()} 
	 * from now on. 
	 * <p>
	 * Subsequent calls of this method will return the same instance again. The parameters specified in
	 * additional calls will therefore <b>not</b> be considered.
	 * <p>
	 * Note that this method can only be called if no <i>SWT</i> component has been created for the specified
	 * TIC component before.
	 * 
	 * @param ticComponent the <i>TIC</i> component for which an <i>SWT</i> component shall be created
	 * @param additionalParameters an optional list of parameters to be passed to 
	 *        {@code ticComponent.getSWTComponentClassName(Object...)} and 
	 *        {@code ticComponent.getSWTComponentConstructorParameters(Object...)} 
	 *        (Note that these parameters will not be passed to the component constructor. The parameters passed 
	 *        there are determined by the return value of 
	 *        {@link TICComponent#getSWTComponentConstructorParameters(Object...)}.)
	 * @return the associated <i>Swing</i> component that has been created
	 * @throws IllegalStateException if an <i>SWT</i> component has already been created for {@code ticComponent} 
	 * @throws ToolkitSpecificInstantiationException if an error during the creation of the component occurs 
	 *         (e.g. no constructor matching the necessary parameters was found)
	 */
	public JComponent getSwingComponent(TICComponent ticComponent, Object... additionalParameters) {
		if (!ticComponent.hasToolkitComponent()) {
			JComponent component = createSwingComponent(ticComponent, additionalParameters);
			
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
