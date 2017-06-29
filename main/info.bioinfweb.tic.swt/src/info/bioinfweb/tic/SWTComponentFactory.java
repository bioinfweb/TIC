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

import org.eclipse.swt.widgets.Composite;



/**
 * Factory that allows to create SWT GUI component instances from instances of {@link TICComponent}.
 * <p>
 * The following example shows how to create SWT-specific GUI components from a TIC component.
 * <pre>
 * // Create an instance of a TIC component:
 * TICComponent ticComponent = new SomeComponent();
 * 
 * // Use this factory to create a SWT-specific instance from it:
 * Composite swtComponent = SWTComponentFactory.getInstance().getSWTComponent(ticComponent, parentSWTComponent, SWT.NONE);
 * </pre>
 * See {@link #getSWTComponent(TICComponent, Composite, int)} for further details.
 * 
 * @author Ben St&ouml;ver
 * @since 2.0.0
 * @bioinfweb.module info.bioinfweb.tic.swt
 */
public class SWTComponentFactory {
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


	private Composite createSWTComponent(TICComponent ticComponent, Composite parent, int style) {
		try {
			Constructor<?>[] constructors = Class.forName(ticComponent.getSWTComponentClassName()).getConstructors();
			for (int i = 0; i < constructors.length; i++) {
				Class<?>[] parameters = constructors[i].getParameterTypes();
				if ((parameters.length == 3) && parameters[0].isAssignableFrom(ticComponent.getClass())
						&& parameters[1].equals(Composite.class) && parameters[2].equals(int.class)) {

					Composite result = (Composite)constructors[i].newInstance(ticComponent, parent, style);
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
			throw new ToolkitSpecificInstantiationException("No valid constructor found for " +
					ticComponent.getSWTComponentClassName());
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


	private ToolkitComponent createAndRegisterSWTWidget(TICComponent ticComponent, Composite parent, int style) {
		Composite result = createSWTComponent(ticComponent, parent, style);
		result.addKeyListener(new SWTKeyEventForwarder(ticComponent.getKeyListenersSet()));
		SWTMouseEventForwarder mouseListeners = new SWTMouseEventForwarder(ticComponent.getMouseListenersSet());
		result.addMouseListener(mouseListeners);
		result.addMouseMoveListener(mouseListeners);
		result.addMouseTrackListener(mouseListeners);
		result.addMouseWheelListener(new SWTMouseWheelEventForwarder(ticComponent.getMouseWheelListenersSet()));
		return (ToolkitComponent)result;
	}


	/**
	 * Creates the SWT component that will be associated with the specified TIC component if it was not
	 * created before. The returned instance will be returned by {@link #getToolkitComponent()} from now
	 * on. Subsequent calls of this method will return the same instance again. The specified parameters
	 * will not be considered in that case.
	 * <p>
	 * Note that this method can only be called if no Swing component has been created for the specified
	 * TIC component before.
	 *
	 * @return the associated Swing component that has been created
	 * @throws IllegalStateException if an Swing component has already been created for {@code ticComponent}
	 */
	public Composite getSWTComponent(TICComponent ticComponent, Composite parent, int style) {
		if (!ticComponent.hasToolkitComponent()) {
			ticComponent.setToolkitComponent(createAndRegisterSWTWidget(ticComponent, parent, style));
		}
		else if (!ticComponent.getCurrentToolkit().equals(TargetToolkit.SWT)) {
			throw new IllegalStateException("A non Swing component has already been created.");
		}
		else if (((Composite)ticComponent.getToolkitComponent()).isDisposed()) {  // && getCurrentToolkit().equals(TargetToolkit.SWT)
			ticComponent.setToolkitComponent(createAndRegisterSWTWidget(ticComponent, parent, style));  // Create new component if previous one was disposed.
			//TODO Does this make sense this way? Anything else to be done about disposing of SWT elements?
		}
		return (Composite)ticComponent.getToolkitComponent();
	}
}
