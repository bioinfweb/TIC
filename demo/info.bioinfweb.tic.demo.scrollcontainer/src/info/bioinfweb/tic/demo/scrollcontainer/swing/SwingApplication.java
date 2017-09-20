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
package info.bioinfweb.tic.demo.scrollcontainer.swing;


import info.bioinfweb.tic.SwingComponentFactory;
import info.bioinfweb.tic.demo.scrollcontainer.shared.ScrollContainer;
import info.bioinfweb.tic.scrolling.TICScrollEvent;
import info.bioinfweb.tic.scrolling.TICScrollListener;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.JPanel;

import java.awt.FlowLayout;

import javax.swing.JLabel;



/**
 * The <i>Swing</i> application class of this demo, making use of {@link ScrollContainer} and its subcomponents.
 * 
 * @author Ben St&ouml;ver
 */
public class SwingApplication {
	private JFrame frame;
	private ScrollContainer scrollContainer;

	
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
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e) {}		
		
		frame = new JFrame();
		frame.setSize(500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Swing Scroll Container Application");
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		// Create TIC component instance:
		scrollContainer = new ScrollContainer();
		
		// Create Swing-specific component instance:
		JComponent swingScrollContainer = SwingComponentFactory.getInstance().getSwingComponent(scrollContainer);
		
		// Add Swing component to GUI:
		frame.getContentPane().add(swingScrollContainer, BorderLayout.CENTER);
		
		
		// Create status bar below the scroll container to demonstrate listening to scroll events:
		JPanel statusPanel = new JPanel();
		((FlowLayout)statusPanel.getLayout()).setAlignment(FlowLayout.LEFT);
		frame.getContentPane().add(statusPanel, BorderLayout.SOUTH);
		final JLabel statusLabel = new JLabel();
		statusPanel.add(statusLabel);
		
		scrollContainer.getScrollListeners().add(new TICScrollListener() {
			@Override
			public void contentScrolled(TICScrollEvent event) {
				statusLabel.setText("Scroll position: (" + scrollContainer.getScrollOffsetX() + ", " + 
						scrollContainer.getScrollOffsetY() + ")");
			}
		});
		
		
		// Create main menu to demonstrate programmatic scrolling:
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnScroll = new JMenu("Scroll");
		menuBar.add(mnScroll);
		
		JMenuItem mntmTopLeft = new JMenuItem("Top left");
		mntmTopLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scrollContainer.scrollToTopLeft();
			}
		});
		mnScroll.add(mntmTopLeft);
		
		JMenuItem mntmCenter = new JMenuItem("Center");
		mntmCenter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scrollContainer.scrollToCenter();
			}
		});
		mnScroll.add(mntmCenter);
	}
}
