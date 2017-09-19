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
package info.bioinfweb.tic.demo.scrollcontainer.swt;


import info.bioinfweb.tic.SWTComponentFactory;
import info.bioinfweb.tic.demo.scrollcontainer.shared.ScrollContainer;
import info.bioinfweb.tic.scrolling.TICScrollEvent;
import info.bioinfweb.tic.scrolling.TICScrollListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;



/**
 * The <i>SWT</i> application class of this demo making use of {@link ScrollContainer} and its subcomponents.
 * 
 * @author Ben St&ouml;ver
 */
public class SWTApplication {	
	protected Shell shell;
	private ScrollContainer scrollContainer;
	
	
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
		scrollContainer = new ScrollContainer();
		
		// Create SWT-specific component instance and add it to the SWT GUI:
		GridData gridData = new GridData();
	  gridData.horizontalAlignment = GridData.FILL;
	  gridData.verticalAlignment = GridData.FILL;
	  gridData.grabExcessHorizontalSpace = true;
	  gridData.grabExcessVerticalSpace = true;
	
		SWTComponentFactory.getInstance().getSWTComponent(scrollContainer, shell, SWT.NONE).setLayoutData(gridData);
		
		// Create status bar below the scroll container to demonstrate listening to scroll events:
		gridData = new GridData();
	  gridData.horizontalAlignment = GridData.FILL;
	  gridData.grabExcessHorizontalSpace = true;
	  gridData.horizontalIndent = 3;
	  
		final Label statusLabel = new Label(shell, SWT.NONE);
		statusLabel.setText(" ");
		statusLabel.setLayoutData(gridData);

		scrollContainer.getScrollListeners().add(new TICScrollListener() {
			@Override
			public void controlScrolled(TICScrollEvent event) {
				statusLabel.setText("Scroll position: (" + scrollContainer.getScrollOffsetX() + ", " + 
						scrollContainer.getScrollOffsetY() + ")");
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
				scrollContainer.scrollToTopLeft();
			}
		});
		mntmTopLeft.setText("Top left");
		
		MenuItem mntmCenter = new MenuItem(menuScroll, SWT.NONE);
		mntmCenter.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				scrollContainer.scrollToCenter();
			}
		});
		mntmCenter.setText("Center");
	}
}
