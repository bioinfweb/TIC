package info.bioinfweb.tic.demo;


import info.bioinfweb.tic.SWTComponentFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;


/**
 * Example that demonstrates using TIC components in SWT applications.
 * <p>
 * This example is used in the 
 * <a href="http://bioinfweb.info/TIC/Documentation">TIC documentation</a>.
 * 
 * @author Sarah Wiechers
 * @author Ben St&ouml;ver
 */
public class SWTApplication {	
	protected Shell shell;
	private SierpinskiTriangleComponent triangle;
	
	
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
		shell.setSize(450, 300);
		shell.setText("SWT Application");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));

		// Create TIC component instance:
		triangle = new SierpinskiTriangleComponent();
		
		// Create SWT-specific component instance and add it to the SWT GUI:
		SWTComponentFactory.getInstance().getSWTComponent(triangle, shell, SWT.NONE);
	}
}
