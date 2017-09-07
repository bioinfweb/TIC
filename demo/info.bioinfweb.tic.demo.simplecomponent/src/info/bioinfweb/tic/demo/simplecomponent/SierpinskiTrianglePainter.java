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


import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.SystemColor;
import java.awt.geom.Path2D;



/**
 * This class paints a Sierpinski triangle that is used as an example <i>TIC</i> component content. The painting
 * algorithms in this class is not important for the purpose of this demo application, that is to show how to 
 * create a <i>TIC</i> that paints the same output in <i>Swing</i> and <i>SWT</i>.
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
