package info.bioinfweb.tic.demo;


import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.SystemColor;
import java.awt.geom.Path2D;



public class SierpinskiTrianglePainter {
	public static final double MINIMAL_SIDE_LENGHTH = 5;
	

	public static void paintSierpinskiTriangle(Graphics2D g, Dimension size) {
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		double sideLength = Math.min(size.getWidth(), size.getHeight()) * 2 / 3;
		double triangleHeight = getTriangleHeight(sideLength);
		// start at a point so that triangle fits on screen.
		double startx = (size.getWidth() - sideLength) / 2;
		double starty = (size.getHeight() - triangleHeight) / 2 + triangleHeight;
		
		g.setColor(SystemColor.control);
		g.fill(new Rectangle(size));
		
		g.setColor(SystemColor.controlText);
		drawTriangle(g, startx, starty, sideLength);
		drawSierpinskiTriangle(g, startx, starty, sideLength / 2);
	}
	
	
	private static void drawSierpinskiTriangle (Graphics2D g, double startx, double starty, double sideLength) {
		if (sideLength > MINIMAL_SIDE_LENGHTH) {
			double xDiff = sideLength / 4;
			double yDiff = getTriangleHeight(sideLength) / 2;
			drawInverseTriangle(g, startx + (xDiff * 2), starty - (yDiff * 2), sideLength);
			double startX1 = startx; 
			double startY1 = starty;		
			drawSierpinskiTriangle(g, startX1, startY1, sideLength / 2); // bottom left
			double startX2 = startx + xDiff * 2;
			double startY2 = starty - yDiff * 2;		
			drawSierpinskiTriangle(g, startX2, startY2, sideLength / 2); // top
			double startX3 = startx + xDiff * 4;
			double startY3 = starty;		
			drawSierpinskiTriangle(g, startX3, startY3, sideLength / 2); // bottom right
		}
	}
	
	
	private static void drawInverseTriangle(Graphics2D g, double startx, double starty, double sideLength) { //starting point is top left
		double rightx = startx + sideLength; //right y = start y
		double bottomx = startx + sideLength/2;
		double bottomy = starty + getTriangleHeight(sideLength);
		
		Path2D path = new Path2D.Double();
		path.moveTo(startx, starty);
		path.lineTo(rightx, starty);
		path.lineTo(bottomx, bottomy);
		path.closePath();
		g.draw(path);
	}
	
	
	private static void drawTriangle(Graphics2D g, double startx, double starty, double sideLength) { //starting point is bottom left
		double rightx = startx + sideLength; //right y = start y
		double topx = startx + sideLength/2;
		double topy = starty - getTriangleHeight(sideLength);
		
		Path2D path = new Path2D.Double();
		path.moveTo(startx, starty);
		path.lineTo(topx, topy);
		path.lineTo(rightx, starty);
		path.closePath();
		g.draw(path);
	 }
	
	
	private static double getTriangleHeight(double sideLength) {
		return (sideLength / 2) * Math.sqrt(3);
	}
}
