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
package info.bioinfweb.tic.demo.simplecomponent;


import info.bioinfweb.tic.SwingComponentFactory;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JComponent;
import javax.swing.JFrame;



/**
 * Example that demonstrates using <i>TIC</i> components in <i>Swing</i> applications.
 * <p>
 * This example is used in the 
 * <a href="http://bioinfweb.info/TIC/Documentation">TIC documentation</a>.
 * 
 * @author Sarah Wiechers
 * @author Ben St&ouml;ver
 */
public class SwingApplication {
	private JFrame frame;
	private SierpinskiTriangleComponent triangle;

	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SwingApplication window = new SwingApplication();
					window.frame.setVisible(true);
				} 
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	/**
	 * Create the application.
	 */
	public SwingApplication() {
		initialize();
	}

	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Swing Application");

		// Create TIC component instance:
		triangle = new SierpinskiTriangleComponent();
		
		// Create Swing-specific component instance:
		JComponent swingTriangle = SwingComponentFactory.getInstance().getSwingComponent(triangle);
		
		// Add Swing component to GUI:
		frame.getContentPane().add(swingTriangle, BorderLayout.CENTER);
	}
}
