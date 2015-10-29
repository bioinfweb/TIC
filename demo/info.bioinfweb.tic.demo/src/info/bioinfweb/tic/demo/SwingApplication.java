package info.bioinfweb.tic.demo;


import info.bioinfweb.tic.SwingComponentFactory;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JComponent;
import javax.swing.JFrame;



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
		
		triangle = new SierpinskiTriangleComponent();
		JComponent swingTriangle = SwingComponentFactory.getInstance().getSwingComponent(triangle);
		frame.getContentPane().add(swingTriangle, BorderLayout.CENTER);
	}
}
