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
package info.bioinfweb.tic.demo.scrollcontainer;


import info.bioinfweb.tic.SWTComponentFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;



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
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));

		// Create TIC component instance:
		scrollContainer = new ScrollContainer();
		
		// Create SWT-specific component instance and add it to the SWT GUI:
		SWTComponentFactory.getInstance().getSWTComponent(scrollContainer, shell, SWT.NONE);
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem mntmScroll = new MenuItem(menu, SWT.CASCADE);
		mntmScroll.setText("Scroll");
		
		Menu menu_1 = new Menu(mntmScroll);
		mntmScroll.setMenu(menu_1);
		
		MenuItem mntmTopLeft = new MenuItem(menu_1, SWT.NONE);
		mntmTopLeft.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				scrollContainer.scrollToTopLeft();
			}
		});
		mntmTopLeft.setText("Top left");
		
		MenuItem mntmCenter = new MenuItem(menu_1, SWT.NONE);
		mntmCenter.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				scrollContainer.scrollToCenter();
			}
		});
		mntmCenter.setText("Center");
	}
}
