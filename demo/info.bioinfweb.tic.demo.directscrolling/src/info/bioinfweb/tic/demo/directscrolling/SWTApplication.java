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
package info.bioinfweb.tic.demo.directscrolling;


import info.bioinfweb.tic.SWTComponentFactory;
import info.bioinfweb.tic.input.TICMouseAdapter;
import info.bioinfweb.tic.input.TICMouseEvent;
import info.bioinfweb.tic.scrolling.TICScrollEvent;
import info.bioinfweb.tic.scrolling.TICScrollListener;

import java.awt.Dimension;
import java.awt.Rectangle;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;



public class SWTApplication {	
	protected Shell shell;
	private ScrollContainer scrollingComponent;
	private MessageBox messageBox;
	
	
	/**
	 * Launch the application.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			SWTApplication window = new SWTApplication();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
	
	
	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(500, 500);
		shell.setText("SWT Scroll Container Application");
		
		GridLayout layout = new GridLayout(1, false);
		layout.verticalSpacing = 0;
		layout.marginWidth = 0;
		layout.marginHeight = 0;
		layout.horizontalSpacing = 0;
		shell.setLayout(layout);

		// Create TIC component instance:
		scrollingComponent = new ScrollContainer();
		
		// Create SWT-specific component instance and add it to the SWT GUI:
		GridData gridData = new GridData();
	  gridData.horizontalAlignment = GridData.FILL;
	  gridData.verticalAlignment = GridData.FILL;
	  gridData.grabExcessHorizontalSpace = true;
	  gridData.grabExcessVerticalSpace = true;
	
		SWTComponentFactory.getInstance().getSWTComponent(scrollingComponent, shell, SWT.NONE).setLayoutData(gridData);
		
		
		// Create status bar below the scroll container to demonstrate listening to scroll events:
		gridData = new GridData();
	  gridData.horizontalAlignment = GridData.FILL;
	  gridData.grabExcessHorizontalSpace = true;
	  gridData.horizontalIndent = 3;
	  
		final Label statusLabel = new Label(shell, SWT.NONE);
		statusLabel.setText(" ");
		statusLabel.setLayoutData(gridData);

		scrollingComponent.getScrollListeners().add(new TICScrollListener() {
			@Override
			public void contentScrolled(TICScrollEvent event) {
				statusLabel.setText("Scroll position: (" + scrollingComponent.getScrollOffsetX() + ", " + 
						scrollingComponent.getScrollOffsetY() + ")");
			}
		});
		
		
		// Show message box with transformed coordinates on mouse click: 
		messageBox = new MessageBox(shell, SWT.ICON_INFORMATION | SWT.OK);
		messageBox.setText("Mouse Listener Test");
		scrollingComponent.getOutputComponent().addMouseListener(new TICMouseAdapter() {
			@Override
			public boolean mousePressed(TICMouseEvent event) {
				messageBox.setMessage("Mouse clicked at (" + event.getComponentX() + ", " + event.getComponentY() + ").");
				messageBox.open();
				return true;
			}
		});
		
		
		// Create main menu to demonstrate programmatic scrolling:
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem mntmScroll = new MenuItem(menu, SWT.CASCADE);
		mntmScroll.setText("Scroll");
		
		Menu menuScroll = new Menu(mntmScroll);
		mntmScroll.setMenu(menuScroll);
		
		MenuItem mntmTopLeft = new MenuItem(menuScroll, SWT.NONE);
		mntmTopLeft.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				scrollingComponent.setScrollOffset(0, 0);
			}
		});
		mntmTopLeft.setText("Top left");
		
		MenuItem mntmCenter = new MenuItem(menuScroll, SWT.NONE);
		mntmCenter.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Dimension size = scrollingComponent.getSize();
				Rectangle r = scrollingComponent.getVisibleRectangle();
				scrollingComponent.setScrollOffset((size.width - r.width) / 2, (size.height - r.height) / 2);
			}
		});
		mntmCenter.setText("Center");
	}
}
