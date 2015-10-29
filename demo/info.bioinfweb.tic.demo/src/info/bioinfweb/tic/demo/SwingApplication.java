package info.bioinfweb.tic.demo;


import info.bioinfweb.tic.SwingComponentFactory;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JComponent;
import javax.swing.JFrame;


/**
 * Example that demonstrates using TIC components in Swing applications.
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
