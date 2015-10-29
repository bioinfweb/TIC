package info.bioinfweb.tic.demo;


import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.SystemColor;
import java.awt.geom.Path2D;



/**
 * This class paints a Sierpinski triangle that is used as an example TIC component content. The detailed
 * contents of this class are not relevant for the TIC usage example.
 * 
 * @author Sarah Wiechers
 * @author Ben St&ouml;ver
 */
public class SierpinskiTrianglePainter {
	public static final double MINIMAL_SIDE_LENGHTH = 5;
	

	public static void paintSierpinskiTriangle(Graphics2D g, Dimension size) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		double sideLength = Math.min(size.getWidth(), size.getHeight()) * 2 / 3;
		double triangleHeight = getTriangleHeight(sideLength);
		
		// start at a point so that triangle fits on screen.
		double startX = (size.getWidth() - sideLength) / 2;
		double startY = (size.getHeight() - triangleHeight) / 2 + triangleHeight;
		
		g.setColor(SystemColor.control);
		g.fill(new Rectangle(size));
		
		g.setColor(SystemColor.controlText);
		drawTriangle(g, startX, startY, sideLength);
		drawSierpinskiTriangle(g, startX, startY, sideLength / 2);
	}
	
	
	private static void drawSierpinskiTriangle (Graphics2D g, double startX, double startY, double sideLength) {
		if (sideLength > MINIMAL_SIDE_LENGHTH) {
			double xDiff = sideLength / 4;
			double yDiff = getTriangleHeight(sideLength) / 2;
			drawInverseTriangle(g, startX + (xDiff * 2), startY - (yDiff * 2), sideLength);
			double startX1 = startX; 
			double startY1 = startY;		
			drawSierpinskiTriangle(g, startX1, startY1, sideLength / 2); // bottom left
			double startX2 = startX + xDiff * 2;
			double startY2 = startY - yDiff * 2;		
			drawSierpinskiTriangle(g, startX2, startY2, sideLength / 2); // top
			double startX3 = startX + xDiff * 4;
			double startY3 = startY;		
			drawSierpinskiTriangle(g, startX3, startY3, sideLength / 2); // bottom right
		}
	}
	
	
	private static void drawInverseTriangle(Graphics2D g, double startX, double startY, double sideLength) { //starting point is top left
		double rightx = startX + sideLength; //right y = start y
		double bottomx = startX + sideLength/2;
		double bottomy = startY + getTriangleHeight(sideLength);
		
		Path2D path = new Path2D.Double();
		path.moveTo(startX, startY);
		path.lineTo(rightx, startY);
		path.lineTo(bottomx, bottomy);
		path.closePath();
		g.draw(path);
	}
	
	
	private static void drawTriangle(Graphics2D g, double startX, double startY, double sideLength) { //starting point is bottom left
		double rightx = startX + sideLength; //right y = start y
		double topx = startX + sideLength/2;
		double topy = startY - getTriangleHeight(sideLength);
		
		Path2D path = new Path2D.Double();
		path.moveTo(startX, startY);
		path.lineTo(topx, topy);
		path.lineTo(rightx, startY);
		path.closePath();
		g.draw(path);
	 }
	
	
	private static double getTriangleHeight(double sideLength) {
		return (sideLength / 2) * Math.sqrt(3);
	}
}