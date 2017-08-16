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
import info.bioinfweb.tic.input.SWTKeyEventForwarder;
import info.bioinfweb.tic.input.SWTMouseEventForwarder;
import info.bioinfweb.tic.input.SWTMouseWheelEventForwarder;
import info.bioinfweb.tic.toolkit.ToolkitComponent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import org.apache.commons.lang3.reflect.ConstructorUtils;
import org.eclipse.swt.widgets.Composite;



/**
 * Factory that allows to create <i>SWT</i> GUI component instances from instances of {@link TICComponent}.
 * <p>
 * The following example shows how to create <i>SWT</i>-specific GUI components from a <i>TIC</i> component.
 * <pre>
 * // Create an instance of a <i>TIC</i> component:
 * TICComponent ticComponent = new SomeComponent();
 * 
 * // Use this factory to create a <i>TIC</i>-specific instance from it:
 * Composite swtComponent = SWTComponentFactory.getInstance().getSWTComponent(ticComponent, parentSWTComponent, SWT.NONE);
 * </pre>
 * See {@link #getSWTComponent(TICComponent, Composite, int)} for further details.
 * <p>
 * Note that it is alternatively possible to create a <i>Swing</i> component using {@code info.bioinfweb.tic.SwingComponentFactory}
 * and embed it into an <i>SWT</i> GUI using {@code info.bioinfweb.commons.swt.SWTUtils.embedAWTComponent()}.
 * 
 * @author Ben St&ouml;ver
 * @since 2.0.0
 * @bioinfweb.module info.bioinfweb.tic.swt
 */
public class SWTComponentFactory {
	private static final int MINIMAL_NUMBER_OF_CONSTRUCTOR_PARAMETERS = 3;
	
	
	private static SWTComponentFactory firstInstance = null;


	private SWTComponentFactory() {
		super();
	}


	/**
	 * Returns the shared instance of this factory
	 * 
	 * @return the shared factory instance
	 */
	public static SWTComponentFactory getInstance() {
		if (firstInstance == null) {
			firstInstance = new SWTComponentFactory();
		}
		return firstInstance;
	}

	
	private Composite createSWTComponent(TICComponent ticComponent, Composite parent, int style, Object... factoryParameters) {
		try {
			Class<?> componentClass = Class.forName(ticComponent.getSWTComponentClassName(factoryParameters));
			if (!Composite.class.isAssignableFrom(componentClass)) {
				throw new ToolkitSpecificInstantiationException("The constructed instance of type " +
						componentClass.getCanonicalName() + " does not inherit from " + Composite.class.getCanonicalName() + ".");
			}
			else if (!ToolkitComponent.class.isAssignableFrom(componentClass)) {
				throw new ToolkitSpecificInstantiationException("The constructed instance of type " +
						componentClass.getCanonicalName() + " does not implement " + ToolkitComponent.class.getCanonicalName() + ".");
			}
			else {
				// Create list of possible additional constructor parameters:
				Object[] constructorParameters = ticComponent.getSWTComponentConstructorParameters(factoryParameters);
				
				// Create combined array with all constructor parameter values:
				Object[] parameterValues = new Object[constructorParameters.length + MINIMAL_NUMBER_OF_CONSTRUCTOR_PARAMETERS];
				parameterValues[0] = ticComponent;
				parameterValues[1] = parent;
				parameterValues[2] = style;
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
						return (Composite)constructor.newInstance(parameterValues);
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


	private ToolkitComponent createAndRegisterSWTWidget(TICComponent ticComponent, Composite parent, int style, 
			Object... additionalParameters) {
		
		Composite result = createSWTComponent(ticComponent, parent, style, additionalParameters);
		result.addKeyListener(new SWTKeyEventForwarder(ticComponent.getKeyListenersSet()));
		SWTMouseEventForwarder mouseListeners = new SWTMouseEventForwarder(ticComponent.getMouseListenersSet());
		result.addMouseListener(mouseListeners);
		result.addMouseMoveListener(mouseListeners);
		result.addMouseTrackListener(mouseListeners);
		result.addMouseWheelListener(new SWTMouseWheelEventForwarder(ticComponent.getMouseWheelListenersSet()));
		return (ToolkitComponent)result;
	}


	/**
	 * Creates the <i>SWT</i> component that will be associated with the specified TIC component if it was not
	 * created before. The created instance will be returned by {@link #getToolkitComponent()} from now
	 * on.
	 * <p>
	 * Subsequent calls of this method will return the same instance again. The parameters specified in
	 * additional calls will therefore <b>not</b> be considered.
	 * <p>
	 * Note that this method can only be called if no <i>Swing</i> component has been created for the specified
	 * <i>TIC</i> component before.
	 *
	 * @param ticComponent the <i>TIC</i> component for which an <i>SWT</i> component shall be created
	 * @param parent a widget which will be the parent of the new instance (cannot be null)
	 * @param style the style of widget to construct
	 * @param additionalParameters an optional list of parameters to be passed to 
	 *        {@code ticComponent.getSWTComponentClassName(Object...)} and 
	 *        {@code ticComponent.getSWTComponentConstructorParameters(Object...)} 
	 *        (Note that these parameters will not be passed to the component constructor. The parameters passed 
	 *        there are determined by the return value of 
	 *        {@link TICComponent#getSWTComponentConstructorParameters(Object...)}.)
	 * @return the associated <i>SWT</i> component that has been created
	 * @throws IllegalStateException if an <i>Swing</i> component has already been created for {@code ticComponent}
	 * @throws ToolkitSpecificInstantiationException if an error during the creation of the component occurs 
	 *         (e.g. no constructor matching the necessary parameters was found)
	 */
	public Composite getSWTComponent(TICComponent ticComponent, Composite parent, int style, Object... additionalParameters) {
		if (!ticComponent.hasToolkitComponent()) {
			ticComponent.setToolkitComponent(createAndRegisterSWTWidget(ticComponent, parent, style, additionalParameters));
		}
		else if (!ticComponent.getCurrentToolkit().equals(TargetToolkit.SWT)) {
			throw new IllegalStateException("A non Swing component has already been created.");
		}
		else if (((Composite)ticComponent.getToolkitComponent()).isDisposed()) {  // && getCurrentToolkit().equals(TargetToolkit.SWT)
			ticComponent.setToolkitComponent(createAndRegisterSWTWidget(ticComponent, parent, style, additionalParameters));  // Create new component if previous one was disposed.
			//TODO Does this make sense this way? Anything else to be done about disposing of SWT elements?
		}
		return (Composite)ticComponent.getToolkitComponent();
	}
}
